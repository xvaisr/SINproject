/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Roman Vais
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.FIELD)
public @interface Injection {

    String named() default "default";

}
