package br.com.alverad.ecommerce.commons.util;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class ExampleMatcherUtils {

    private ExampleMatcherUtils() {}

    public static <T> Example<T> createExampleMatcherFromObject(T data) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();

        BeanWrapper beanWrapper = new BeanWrapperImpl(data);
        for (String prop : getPropertyNames(data)) {
            Object propValue = beanWrapper.getPropertyValue(prop);
            if (propValue instanceof String) {
                exampleMatcher = exampleMatcher.withMatcher(prop,
                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            }
        }

        return Example.of(data, exampleMatcher);
    }

    private static <T> String[] getPropertyNames(T data) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(data);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        return Arrays.stream(propertyDescriptors).filter(pd -> !"class".equals(pd.getName()))
                .map(PropertyDescriptor::getName).toArray(String[]::new);
    }
}
