package com.do4you.do4you.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

@Target(ElementType.TYPE)           // 해당 어노테이션은 annotation, interface, enum, class 타입에 사용할 수 있습니다.
@Retention(RetentionPolicy.SOURCE) // 해당 어노테이션을 컴파일 전까지만 유지도록 설정합니다.
public @interface AutoDto {
    Class targetClass() ;

}
