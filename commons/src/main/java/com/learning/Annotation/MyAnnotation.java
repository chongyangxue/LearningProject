package com.learning.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //元注解，表示注解的生命周期；override，suppresswarning在source阶段
@Target({ElementType.METHOD, ElementType.TYPE}) //注解放置的位置
public @interface MyAnnotation {
	String color() default "blue";
	String value();
	int[] array() default {3, 4, 5};
	MetaAnnotation annotationAttr() default @MetaAnnotation("hello");
	Class clazz() default String.class;
}
