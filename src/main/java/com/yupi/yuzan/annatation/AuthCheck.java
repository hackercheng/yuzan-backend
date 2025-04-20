package com.yupi.yuzan.annatation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AuthCheck {
    String mustRole() default "";
}
