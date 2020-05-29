package com.dictionary.common.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.HashSet;
import java.util.Set;

public class ApplicationReflectionUtil {

    public static <T> Set<Class<T>> getSubClasses(Class<T> clazz, String pack) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(clazz));
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(pack);
        Set<Class<T>> classes = new HashSet<>();
        for (BeanDefinition beanDefinition : candidateComponents) {
            try {
                final Class<? extends T> aClass = Class.forName(beanDefinition.getBeanClassName()).asSubclass(clazz);
                classes.add((Class<T>) aClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class not found");
            }
        }
        return classes;
    }

}
