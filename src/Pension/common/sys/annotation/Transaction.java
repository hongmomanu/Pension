package Pension.common.sys.annotation;

/**
 * User: Administrator
 * Date: 14-4-20
 * Time: 上午10:05
 */
public @interface Transaction {
    boolean value() default true;
}
