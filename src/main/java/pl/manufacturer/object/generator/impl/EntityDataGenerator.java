package pl.manufacturer.object.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.manufacturer.object.exception.ObjectIsNotAnEntityException;
import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.mapkey.MapKey;
import pl.manufacturer.object.mapkey.MapValue;
import pl.manufacturer.object.util.ArgumentTypeUtil;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.manufacturer.object.util.MethodUtil.invokeMethod;

public class EntityDataGenerator extends CommonDataGenerator implements DataGenerator {

    private static final Logger log = LoggerFactory.getLogger(EntityDataGenerator.class);
    private static final String SETTER_KEYWORD = "set";
    private static final int BASE_ARRAY_SIZE = 5;

    private final PojoDataGenerator pojoDataGenerator;

    public EntityDataGenerator(PojoDataGenerator pojoDataGenerator) {
        this.pojoDataGenerator = pojoDataGenerator;
    }

    Map<MapKey, MapValue> objectByFieldNames_OneToOne = new HashMap<>();
    Map<MapKey, MapValue> objectByFieldNames_OneToMany = new HashMap<>();

    @Override
    public <T> T generateObject(Class<T> clazz, Type... classArgsTypes) {
        if (!isEntity(clazz)) {
            throw new ObjectIsNotAnEntityException("Class " + clazz + " is not an entity. Use GenerationMode.POJO instead.");
        }

        T object;
        try {
            object = instantiateClass(clazz);
        } catch (InstantiationException e) {
            return handleInstantiationException(clazz);
        }

        Map<String, Method> setMethodsByNames = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains(SETTER_KEYWORD))
                .collect(Collectors.toMap(Method::getName, Function.identity()));

        List<Field> nonStaticFields = Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> !Modifier.isStatic(field.getModifiers()) || !field.getName().startsWith("$"))
                .collect(Collectors.toList());

        for (Field field : nonStaticFields) {
            Optional<Column> columnAnnotation = getAnnotation(Column.class, field.getAnnotations());

            if (columnAnnotation.isPresent()) {
                log.debug("[{}][@Column] Found annotation over field.", field.getName());
                handleColumnAnnotation(object, setMethodsByNames, field, columnAnnotation.get());

            } else {
                Optional<OneToOne> oneToOneAnnotation = getAnnotation(OneToOne.class, field.getAnnotations());
                Optional<OneToMany> oneToManyAnnotation = getAnnotation(OneToMany.class, field.getAnnotations());
                Optional<ManyToOne> manyToOneAnnotation = getAnnotation(ManyToOne.class, field.getAnnotations());
                Optional<ManyToMany> manyToManyAnnotation = getAnnotation(ManyToMany.class, field.getAnnotations());

                if (oneToOneAnnotation.isPresent()) {
                    log.debug("[{}][@OneToOne] Found annotation over field.", field.getName());

                    ////////////////////////////
                    MapKey.OneToOneMapKeyBuilder oneToOneMapKey = MapKey.builder().withClazz(field.getType())
                            .withFieldName(field.getName());
                    if (oneToOneAnnotation.get().mappedBy() != null && !oneToOneAnnotation.get().mappedBy().isEmpty()) {
                        log.debug("@OneToOne - mappedBy found [{}]", oneToOneAnnotation.get().mappedBy());
                        oneToOneMapKey.withMappedByParameter(oneToOneAnnotation.get().mappedBy());

                        MapValue v = MapValue.builder().withReferenceObject(object).build();
                        objectByFieldNames_OneToOne.put(oneToOneMapKey.build(), v);
                        Object o = generateObject(field.getType());
                        String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
                        log.debug("{} / {}", field.getType(), setterMethodName);
                        invokeMethod(object, setMethodsByNames.get(setterMethodName), o);
                    } else {
                        log.debug("@OneToOne - mappedBy NOT found. It is the main mapping. ");
                        MapKey.OneToOneMapKeyBuilder oneToOneMapKey2 = MapKey.builder()
                                .withClazz(field.getDeclaringClass())
                                .withMappedByParameter(field.getName());

                        MapValue mapValue = objectByFieldNames_OneToOne.get(oneToOneMapKey2.build());
                        if (mapValue != null) {
                            log.debug("Value is not null. Field name: {}", field.getName());
                            String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
                            log.debug("{}", object);
                            log.debug("{}", setMethodsByNames.get(setterMethodName));
                            log.debug("{}", mapValue.getReferenceObject());
                            invokeMethod(object, setMethodsByNames.get(setterMethodName), mapValue.getReferenceObject());
                        }
                    }
                    ///////////////////////////

                } else if (oneToManyAnnotation.isPresent()) {
                    log.debug("[{}][@OneToMany] Found annotation over field.", field.getName());
                    if (oneToManyAnnotation.get().mappedBy() != null && !oneToManyAnnotation.get().mappedBy().isEmpty()) {
                        log.debug("@OneToMany - mappedBy found [{}]", oneToManyAnnotation.get().mappedBy());

                        if (Collection.class.isAssignableFrom(field.getType())) {
                            log.debug("{} field is a collection.", field.getName());
                            String setterMethodName = generateSetterMethodNameByFieldName(field.getName());

                            Class collectionArgumentType = ArgumentTypeUtil.getCollectionArgumentTypeFromSetterMethod(setMethodsByNames.get(setterMethodName));

                            MapKey mapKey = MapKey.builder()
                                    .withClazz(collectionArgumentType)
                                    .withFieldName(field.getName())
                                    .withMappedByParameter(oneToManyAnnotation.get().mappedBy()).build();
                            MapValue mapValue = MapValue.builder().withReferenceObject(object).build();
                            objectByFieldNames_OneToMany.put(mapKey, mapValue);

                            Stream valuesStream = IntStream.range(0, BASE_ARRAY_SIZE)
                                    .mapToObj(i -> generateObject(collectionArgumentType));

                            log.debug("{} / {}", field.getType(), setterMethodName);
                            invokeMethod(object, setMethodsByNames.get(setterMethodName), valuesStream.collect(Collectors.toList()));
                        }


                    }

                } else if (manyToOneAnnotation.isPresent()) {
                    log.debug("[{}][@ManyToOne] Found annotation over field.", field.getName());

                    MapKey.OneToOneMapKeyBuilder mapKey = MapKey.builder()
                            .withClazz(field.getDeclaringClass())
                            .withMappedByParameter(field.getName());

                    MapValue mapValue = objectByFieldNames_OneToMany.get(mapKey.build());
                    if (mapValue != null) {
                        log.debug("Value is not null. Field name: {}", field.getName());
                        String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
                        invokeMethod(object, setMethodsByNames.get(setterMethodName), mapValue.getReferenceObject());
                    } else {
                        log.debug("There is no objects with @OneToMany annotation registered for this field.");
                    }

                } else if (manyToManyAnnotation.isPresent()) {
                    log.debug("[{}][@ManyToMany] Found annotation over field.", field.getName());

                } else {
                    log.debug("Not found any specific annotation over field {}.", field.getName());
                    String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
                    Object value = pojoDataGenerator.generateObject(field.getType());
                    invokeMethod(object, setMethodsByNames.get(setterMethodName), value);
                }
            }
        }

        return object;
    }

    private <T> void handleColumnAnnotation(T object,
                                            Map<String, Method> setMethodsByNames,
                                            Field field,
                                            Column columnAnnotation) {
        if (String.class.equals(field.getType())) {
            log.debug("Type of the field is String.");
            log.debug("Column has set length to {} characters.", columnAnnotation.length());
            String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
            Object value = generateBaseTypeValue(field.getType(), columnAnnotation.length());
            invokeMethod(object, setMethodsByNames.get(setterMethodName), value);

        } else {
            log.debug("Type of the field is {}.", field.getType());
            String setterMethodName = generateSetterMethodNameByFieldName(field.getName());
            Object value = pojoDataGenerator.generateObject(field.getType());
            invokeMethod(object, setMethodsByNames.get(setterMethodName), value);
        }
    }

    private <T> Optional<T> getAnnotation(Class<T> annotationType, Annotation[] availableAnnotations) {
        return (Optional<T>) Arrays.stream(availableAnnotations)
                .filter(annotation -> annotationType.equals(annotation.annotationType()))
                .findFirst();
    }

    private String generateSetterMethodNameByFieldName(String name) {
        String capitalName = name.substring(0, 1).toUpperCase() + name.substring(1);
        return SETTER_KEYWORD + capitalName;
    }

    private <T> boolean isEntity(Class<T> clazz) {
        return Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Entity.class))
                .count() == 1;
    }
}
