/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.injection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import simulator.injection.Injection;
import simulator.injection.Named;
import simulator.injection.Singleton;

/**
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

    private Injector() {
        this.bindings = new HashMap<>();
        this.instances = new HashMap<>();
    }

    public static <T> T inject(Class<T> clazz) {
        return Injector.instance.getInstance(clazz, Injector.DEFAULT_NAME);
    }

    public static <T> T inject(Class<T> clazz, String named) {
        return Injector.instance.getInstance(clazz, named);
    }

    public static boolean bind(Class<?> inf, Class<?> clazz) {
        return Injector.instance.bindClass(inf, clazz);
    }

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
            return null;
        }

        if (!knownClass) {
            if (inf.isAnnotationPresent(Named.class)) {
                Named annot = inf.getAnnotation(Named.class);

                if (annot.name() == null || !annot.name().equals(named)) {
                    return null;
                }
            }

            inst =  this.createInstance(inf);

            if (inst != null) {
                if (!inf.isAnnotationPresent(Named.class)) {
                    named = Injector.DEFAULT_NAME;
                }
                this.bindClass(inf, inf);

                if (inf.isAnnotationPresent(Singleton.class)) {
                    bindInstance(inf, inst);
                }
            }
            return inst;
        }

        if (knownClass) {

            List<NameBind> bindList = this.bindings.get(inf);
            for (NameBind bind : bindList) {
                if(!bind.name.equals(named)) {
                    continue;
                }

                Object newInstance = null;
                Class<?> dependenci = bind.clazz;

                if (dependenci.isAnnotationPresent(Singleton.class)) {
                    if (this.instances.containsKey(dependenci)) {
                        newInstance = this.instances.get(dependenci);
                    }
                    else {
                        newInstance = this.createInstance(dependenci);
                        if (newInstance != null) {
                            this.bindInstance(dependenci, newInstance);
                        }
                    }
                }
                else {
                    newInstance = this.createInstance(dependenci);
                }
                if (newInstance != null) {
                    inst = inf.cast(newInstance);
                }
                break;
            }
        }

        return inst;
    }

    private boolean bindClass(Class<?> inf, Class<?> clazz) {

        String named = Injector.DEFAULT_NAME;

        if (!inf.isAssignableFrom(clazz)) {
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
        }
        else {
            bindlist = new LinkedList<>();
            this.bindings.put(inf, bindlist);
        }

        for (NameBind b : bindlist) {
            if (b.name.equals(named)) {
                if (clazz != b.clazz) {
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

                f.set(infInstance, this.getInstance(clazz, annotation.named()));
            }

            return inf.cast(infInstance);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
               | InvocationTargetException | InstantiationException ex)
        {
            // there is nothing I can do about it ...
        }

        return null;
    }

}
