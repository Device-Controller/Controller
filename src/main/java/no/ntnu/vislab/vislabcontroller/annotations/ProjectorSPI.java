package no.ntnu.vislab.vislabcontroller.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Documented
@Target(TYPE)
public @interface ProjectorSPI {
    Class<?> value() default void.class;
}