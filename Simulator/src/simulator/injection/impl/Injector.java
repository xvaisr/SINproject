/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.injection.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import simulator.injection.Injection;
import simulator.injection.Named;
import simulator.injection.Singleton;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;
import simulator.logging.impl.SystemLogger;

/**
 * Class implementing on of the parts of a dependency injection framework. Similar functionality to
 * Google Guice framework but much simpler and much less powerful. All binding needs to be done manually.
 * Class is implemented as "true singleton" and you can't create (or get for that matter) it's instance.
 * All access is provided by static methods. First thing you need to do for this to work is provide bindings
 * for interfaces and classes that are supposed to implement them. Implementing classes must have constructor
 * without arguments. Otherwise this dependency injection won't be able to crate new instances. All the binding
 * has to be done in Main method and must be it's first code.
 *
 * @author Roman Vais
 */
public class Injector {

    public static String DEFAULT_NAME = "default";
    private static final Injector instance = new Injector();

    private class NameBind {
        public String name;
        public Class<?> clazz;
    }

    private HashMap<Class<?>, List<NameBind>> bindings;
    private HashMap<Class<?>, Object> instances;
    private SimulationLogger logger;

    private Injector() {
        this.bindings = new HashMap<>();
        this.instances = new HashMap<>();
        this.logger = new SystemLogger(this.getClass().getName(), SystemLogger.DEFAULT_FORMAT,
                SimulationLogger.LogLevel.DEBUG);
    }

    /**
     * Method that is part of a bootstrap and injects binded instance of a logger to Injector class.
     * First bind the interface LoggerFactory with its implementing class. Then you can call this method
     * and continue bootstrapping other interfaces.
     * @return true if successful, false otherwise
     */
    public static boolean selfinjectLogger() {
        LoggerFactory factory = inject(LoggerFactory.class, DEFAULT_NAME);
        if (factory == null) {
            Injector.instance.logger.logDebug("Selfinjecting logger faild");
            return false;
        }
        Injector.instance.logger = factory.getLogger(Injector.class);
        Injector.instance.logger.logDebug("Selfinjecting logger succeded.");
        return true;
    }

    /**
     * Method that serves as main mechanism of the dependency injection to get new instances.
     * Pass class object (Interface.class) of an interface that has been binded by Injector
     * with it's implementation. This method creates instance of implementing class and returns it.
     * @param clazz Class object representing interface that you wish to gain instance of.
     * @return true if successful, false otherwise
     */
    public static <T> T inject(Class<T> clazz) {
        return Injector.instance.getInstance(clazz, Injector.DEFAULT_NAME);
    }

    /**
     * Method that serves as main mechanism of the dependency injection to get new instances.
     * Pass class object (Interface.class) of an interface that has been binded by Injector
     * with it's implementation. This method creates instance of implementing class and returns it.
     * Some interfaces might have multiple implementing classes. @Named annotation in declaration
     * of these implementing classes serves to distinguish between them. If you use have multiple
     * implementations binded or @Named annotation is used, this method provides means to gain the
     * implementation.
     * @param clazz Class object representing interface that you wish to gain instance of.
     * @param named name of the implementation you wish to use (name provided by @Named annotation)
     * @return true if successful, false otherwise
     */
    public static <T> T inject(Class<T> clazz, String named) {
        return Injector.instance.getInstance(clazz, named);
    }

    /**
     * Method that serves as main mechanism of the dependency binding interface with it's implementation.
     * Pass class object (Interface.class) of an interface and of the Implementing class (MyClass.class).
     * If your implementing class uses @Named annotation, it's name is detected through java reflection and
     * multiple implementations can be binded for single interface. If You bind multiple implementations
     * without @Named annotation, behavior is undefined. All implementing classes must have parameter-less
     * constructor. Otherwise Injector wont be able to create their instance.
     * @param inf Class object representing interface that you wish to bind to specific implementation.
     * @param clazz Class object representing interface implementing class.
     * @return true if successful, false otherwise
     */
    public static boolean bind(Class<?> inf, Class<?> clazz) {
        return Injector.instance.bindClass(inf, clazz);
    }

    /**
     * Method that serves as main mechanism of the dependency binding interface with it's implementation and
     * singleton instance. Pass class object (Interface.class) of an interface, of the singleton implementing class
     * (MySingletonClass.class) and existing instance of that class. If your implementing class uses @Named annotation,
     * it's name is detected through java reflection and multiple implementations can be binded for single interface.
     * If You bind multiple implementations without @Named annotation, behavior is undefined. All implementing classes
     * must have parameter-less constructor. Otherwise Injector wont be able to create their instance.
     * If you use @Singleton annotation with the class, you might want to provide already preset instance for
     * Injector to use. This should be method of your choosing.
     * @param inf Class object representing interface that you wish to bind to specific implementation.
     * @param clazz Class object representing interface implementing class.
     * @param inst instance of singleton class that you wish Injector to be providing
     * @return true if successful, false otherwise
     */
    public static boolean bind(Class<?> inf, Class<?> clazz, Object inst) {
        boolean bindedClass = Injector.instance.bindClass(inf, clazz);
        if (bindedClass) {
            bindedClass = (bindedClass && Injector.instance.bindInstance(clazz, inst));
        }
        return bindedClass;
    }

    private <T> T getInstance(Class<T> inf, String named) {
        boolean knownClass;
        T inst = null;
        knownClass = this.bindings.containsKey(inf);

        if (!knownClass && (inf.isInterface() || inf.isEnum() || inf.isAnnotation() || inf.isArray())) {
            logger.logError("Class '" + inf.getName() + "' is not known class and is not possible to create instance.");
            return null;
        }

        if (!knownClass) {
            if (inf.isAnnotationPresent(Named.class)) {
                Named annot = inf.getAnnotation(Named.class);

                if (annot.name() == null || !annot.name().equals(named)) {
                    logger.logError("Class '" + inf.getName() + "' is not known class and @Named annotation doesn't "
                            + " match with called name.");
                    return null;
                }
            }

            logger.logDebug("Class '" + inf.getName() + "' Does not have any known bindings.");
            inst =  this.createInstance(inf);

            if (inst != null) {
                this.bindClass(inf, inf);
                logger.logInfo("Class '" + inf.getName() + "' was binded to it's own interface (to itself).");

                if (inf.isAnnotationPresent(Singleton.class)) {
                    bindInstance(inf, inst);
                    logger.logDebug("Class '" + inf.getName() + "' is singleton. Instance was binded.");
                }
            }
            else {
                logger.logError("Couldn't create instance of '" + inf.getName() + "' class.");
            }
            return inst;
        }

        if (knownClass) {

            logger.logDebug("Class '" + inf.getName() + "' Has known bindings.");
            List<NameBind> bindList = this.bindings.get(inf);
            for (NameBind bind : bindList) {
                if(!bind.name.equals(named)) {
                    continue;
                }

                Object newInstance = null;
                Class<?> dependenci = bind.clazz;
                newInstance = this.createInstance(dependenci);

                if (newInstance != null) {
                    if (inf.isAnnotationPresent(Singleton.class) || dependenci.isAnnotationPresent(Singleton.class)) {
                        bindInstance(dependenci, newInstance);
                        logger.logDebug("Class '" + inf.getName() + "' is singleton. Instance was binded.");
                    }

                    logger.logDebug("Creating instance for'" + dependenci.getName() + "' class succeeded.");
                    inst = inf.cast(newInstance);
                }
                break;
            }
        }

        if (inst == null) {
            logger.logError("Class '" + inf.getName() + "' has no known matching bindings.");
        }

        return inst;
    }

    private boolean bindClass(Class<?> inf, Class<?> clazz) {

        String named = Injector.DEFAULT_NAME;

        if (!inf.isAssignableFrom(clazz)) {
            logger.logError("Interface '" + inf.getName() + "' and '" + clazz.getName() + "' class sre incompatible.");
            return false;
        }

        if (clazz.isAnnotationPresent(Named.class)) {
            Named annot = clazz.getAnnotation(Named.class);
            if (annot.name() != null && !annot.name().equals("")) {
                named = annot.name();
            }
        }

        List<NameBind> bindlist;

        if (this.bindings.containsKey(inf)) {
            bindlist = this.bindings.get(inf);
            logger.logDebug("Existing binding list found for interface '" + inf.getName() + "'.");
        }
        else {
            bindlist = new LinkedList<>();
            this.bindings.put(inf, bindlist);
            logger.logDebug("New binding list created for interface '" + inf.getName() + "'.");
        }

        for (NameBind b : bindlist) {
            if (b.name.equals(named)) {
                if (clazz != b.clazz) {
                    logger.logError("Binding for interface '" + inf.getName() + "' with anotation Named='" + named +
                            "' already exists. Cannot bind different class to the same name space.");
                    return false;
                }
                else {
                    return true;
                }
            }
        }

        NameBind bind;
        bind = new NameBind();
        bind.clazz = clazz;
        bind.name = named;

        bindlist.add(bind);
        logger.logDebug("New binding of interface '" + inf.getName() + "' and '" + clazz.getName() +
                "' was added to bind list under '" + named + "' name.");
        return true;
    }

    private boolean bindInstance(Class<?> clazz, Object inst) {
        if (this.instances.containsKey(clazz)) {
            return false;
        }

        this.instances.put(clazz, inst);
        return true;
    }

    private <T> T createInstance(Class<T> inf) {
        if (this.instances.containsKey(inf)) {
            logger.logDebug("Found existing instance for  '" + inf.getName() + "' singleton class.");
            try {
                Object inst = this.instances.get(inf);
                return inf.cast(inst);
            }
            catch(ClassCastException ex) {
                return null;
            }
        }

        Field[] allFields = inf.getFields();
        ArrayList<Field> fields = new ArrayList<>(allFields.length);

        for (Field f : allFields) {
            if (f.isAnnotationPresent(Injection.class)) {
                logger.logDebug("Found Injection annotation for '" + f.getName() + "' member.");
                fields.add(f);
            }
        }

        try {
            Object infInstance = null;

            infInstance = inf.getConstructor().newInstance();

            for (Field f : fields) {
                Class clazz =  f.getType();
                f.setAccessible( true );

                Injection annotation = f.getAnnotation(Injection.class);
                logger.logDebug("Trying to get instance for '" + clazz.getName() + "' and '"
                        + f.getName() + "' field.");
                f.set(infInstance, this.getInstance(clazz, annotation.named()));
            }

            return inf.cast(infInstance);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
               | InvocationTargetException | InstantiationException ex)
        {
            // there is nothing I can do about it ...
            logger.logDebug("Exception thrown during attempt to create instance of '" + inf.getName() + "' class.", ex);
        }

        return null;
    }

}
