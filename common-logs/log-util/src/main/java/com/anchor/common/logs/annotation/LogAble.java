package com.anchor.common.logs.annotation;

import java.lang.annotation.*;

/**
 * Created by anchor on 2016/1/8.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAble {
    String name() default "";
    String description() default "";
}