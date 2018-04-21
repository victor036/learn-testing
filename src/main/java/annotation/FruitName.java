package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 水果名称注解
 * 
 * 注解本质是一个继承了Annotation的特殊接口，
 * 其具体实现类是Java运行时生成的动态代理类。
 * 而我们通过反射获取注解时，
 * 返回的是Java运行时生成的动态代理对象$Proxy1。通过代理对象调用自定义注解（接口）的方法，
 * 会最终调用AnnotationInvocationHandler的invoke方法。
 * 该方法会从memberValues这个Map中索引出对应的值。而memberValues的来源是Java常量池
 * 
 * 
 * 

 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}