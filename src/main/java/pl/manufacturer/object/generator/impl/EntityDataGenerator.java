package pl.manufacturer.object.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.manufacturer.object.exception.ObjectIsNotAnEntityException;
import pl.manufacturer.object.generator.DataGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.manufacturer.object.util.MethodUtil.invokeMethod;

public class EntityDataGenerator extends CommonDataGenerator implements DataGenerator {

    private static final Logger log = LoggerFactory.getLogger(EntityDataGenerator.class);

    private final PojoDataGenerator pojoDataGenerator;

    public EntityDataGenerator(PojoDataGenerator pojoDataGenerator) {
        this.pojoDataGenerator = pojoDataGenerator;
    }

    @Override
    public <T> T generateObject(Class<T> clazz, Type... classArgsTypes) {
        if (!isEntity(clazz)) {
            throw new ObjectIsNotAnEntityException("Class " + clazz + " is not an entity. Use GenerationMode.POJO instead.");
        }

        T object = instantiateClass(clazz);

        Map<String, Method> setMethodsByNames = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toMap(Method::getName, Function.identity()));

        List<Field> nonStaticFields = Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());

        for (Field field : nonStaticFields) {
            Optional<Column> columnAnnotation = getAnnotation(Column.class, field.getAnnotations());
            if (columnAnnotation.isPresent()) {
                log.debug("Found Column annotation over {} field.", field.getName());
                log.debug("Column has set length to {} characters.", columnAnnotation.get().length());

                String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
                Object value = generateBaseTypeValue(field.getType(), columnAnnotation.get().length());
                invokeMethod(object, setMethodsByNames.get(setterMethodName), value);
            } else {
                log.debug("\"{}\" has no @Column annotation", field.getName());
                String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
                Object value = generateBaseTypeValue(field.getType());
                invokeMethod(object, setMethodsByNames.get(setterMethodName), value);
            }
        }

        return object;
    }

    private <T> Optional<T> getAnnotation(Class<T> annotationType, Annotation[] availableAnnotations) {
        return (Optional<T>) Arrays.stream(availableAnnotations)
                .filter(annotation -> annotationType.equals(annotation.annotationType()))
                .findFirst();
    }

    private String generateSetterMethodNameByFieldName(String name) {
        String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
        return "set" + cap;
    }

    private <T> boolean isEntity(Class<T> clazz) {
        return Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Entity.class))
                .count() == 1;
    }
}
