package pl.manufacturer.object.api;

import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.generator.impl.DataGeneratorImpl;
import pl.manufacturer.object.util.ArgumentTypeUtil;
import pl.manufacturer.object.util.MethodUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerFactory {

    private final DataGenerator dataGenerator = new DataGeneratorImpl();

    public <T> T generatePojo(Class<T> clazz) {
        T object = instantiateClass(clazz);

        List<Method> setterMethods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toList());

        setterMethods.forEach(setterMethod -> {
            Class setterArgumentType = setterMethod.getParameterTypes()[0];
            if (ArgumentTypeUtil.isBaseType(setterArgumentType)) {
                dataGenerator.generateDataForBaseType(object, setterMethod, setterArgumentType);
            } else if (setterArgumentType.isArray()) {
                dataGenerator.generateDataForArray(object, setterMethod, setterArgumentType);
            } else if (Collection.class.isAssignableFrom(setterArgumentType)) {
                dataGenerator.generateDataForCollection(object, setterMethod);
            } else {
                MethodUtil.invokeSetterMethod(object, setterMethod, generatePojo(setterArgumentType));
            }
        });

        return object;
    }

    private <T> T instantiateClass(Class<T> clazz) {
        T object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate class " + clazz);
        }
        return object;
    }
}
