package carpetfixes.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Any field in this class annotated with this class is interpreted as a BUGFIX associated to a carpet rule
 * The field must be static and have a type of one of:
 * - boolean
 * - int
 * - double
 * - String
 * - a subclass of Enum
 * The default value of the rule will be the initial value of the field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bug {
    /**
     * The bug report id's of this bug
     */
    String[] reports() default {};

    /**
     * The bug report id's of bugs related to this bug
     */
    String[] related() default {};

    /**
     * Categories associated to this bug, usually taken from mojira
     */
    String[] categories() default {};

    /**
     * Resolution of the main bug report listed. If it's special
     */
    String resolution() default "";

    /**
     * If this rule reIntroduces the bug report instead of fixing it
     */
    boolean reIntroduce() default false;
}
