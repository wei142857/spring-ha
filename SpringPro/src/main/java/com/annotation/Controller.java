package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//只能放在类上
@Retention(RetentionPolicy.RUNTIME)//生命周期 运行时都能拿到
public @interface Controller {

}
