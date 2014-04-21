package Pension.common.sys.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: Administrator
 * Date: 14-4-20
 * Time: 上午10:04
 */

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {
    boolean value() default true;
}
