import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements org.testng.internal.annotations.IAnnotationTransformer{
    public void transform(ITestAnnotation annotation, Class aClass, Constructor constructor, Method method) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
