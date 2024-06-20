package me.mrdoc.minecraft.test.paperregistryenchantment;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import org.reflections.Reflections;

public class ReflectionUtils {

    /**
     * Obtiene una lista de instancias usando anotaciones
     * @param packageName Nombre del packete
     * @param annotationClass Anotation que deben tener las clases
     * @return Devuelve un HashSet con todas las clases encontradas.
     */
    public static HashSet<Class<?>> getClasses(String packageName, Class<? extends Annotation> annotationClass) {
        Reflections reflections = new Reflections(packageName);
        return new HashSet<>(reflections.getTypesAnnotatedWith(annotationClass));
    }

}
