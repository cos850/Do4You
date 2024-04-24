package com.do4you.do4you.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("AutoDto")    // 처리할 어노테이션 지정
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AutoDtoProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    /**
     * [인수]
     * - annotations: 어노테이션 타입 목록
     * - roundEnv: 현재 및 이전 라운드 정보를 위한 환경
     *
     * [반환값]
     * - true: 해당 어노테이션에 대한 처리를 끝냈으므로 다음 프로세서에게 넘기지 않음
     * - false: 다음 프로세서에게 해당 어노테이션에 대한 처리를 요청함
      */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "프린트 테스트 1 ");

        for(TypeElement annotation : annotations) {
            for(Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "프린트 테스트 2");
            }
        }
        return true;
    }
}
