package pl.manufacturer.object.api;

import org.junit.Ignore;
import org.junit.Test;
import pl.manufacturer.object.example.extended.*;
import pl.manufacturer.object.example.simple.*;
import pl.manufacturer.object.generator.impl.DataGeneratorImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerFactoryTest {

    private ManufacturerFactory manufacturerFactory = new ManufacturerFactory(new DataGeneratorImpl());

    @Test
    public void shouldGenerateExampleSimpleStringObject() {
        // when
        SimpleStringObject object = manufacturerFactory.generateObject(SimpleStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObject()).isNotNull().isNotEmpty();
    }

    @Test
    public void shouldGenerateExampleSimpleBooleanObject() {
        // when
        SimpleBooleanObject object = manufacturerFactory.generateObject(SimpleBooleanObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getBooleanObject()).isNotNull();
        assertThat(object.isBooleanPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleIntegerObject() {
        // when
        SimpleIntegerObject object = manufacturerFactory.generateObject(SimpleIntegerObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getIntegerObject()).isNotNull();
        assertThat(object.getIntegerPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleDoubleObject() {
        // when
        SimpleDoubleObject object = manufacturerFactory.generateObject(SimpleDoubleObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getDoubleObject()).isNotNull();
        assertThat(object.getDoublePrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleFloatObject() {
        // when
        SimpleFloatObject object = manufacturerFactory.generateObject(SimpleFloatObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getFloatObject()).isNotNull();
        assertThat(object.getFloatPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleLongObject() {
        // when
        SimpleLongObject object = manufacturerFactory.generateObject(SimpleLongObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getLongObject()).isNotNull();
        assertThat(object.getLongPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleCharacterObject() {
        // when
        SimpleCharacterObject object = manufacturerFactory.generateObject(SimpleCharacterObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getCharacterObject()).isNotNull();
        assertThat(object.getCharacterPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleByteObject() {
        // when
        SimpleByteObject object = manufacturerFactory.generateObject(SimpleByteObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getByteObject()).isNotNull();
        assertThat(object.getBytePrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleShortObject() {
        // when
        SimpleShortObject object = manufacturerFactory.generateObject(SimpleShortObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getShortObject()).isNotNull();
        assertThat(object.getShortPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExtendedObject() {
        // when
        ExtendedBooleanStringObject object = manufacturerFactory.generateObject(ExtendedBooleanStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getSimpleBoolean()).isNotNull();
        assertThat(object.getSimpleString()).isNotNull();

        assertThat(object.getSimpleBooleanObject()).isNotNull();
        assertThat(object.getSimpleBooleanObject().getBooleanObject()).isNotNull();
        assertThat(object.getSimpleBooleanObject().isBooleanPrimitive()).isNotNull();

        assertThat(object.getSimpleStringObject()).isNotNull();
        assertThat(object.getSimpleStringObject().getStringObject()).isNotNull().isNotEmpty();
    }

    @Test
    public void shouldGenerateExtendedListOfSimpleObjects() {
        // when
        ExtendedListOfStringsObject object = manufacturerFactory.generateObject(ExtendedListOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObjects()).isNotNull().hasSize(2);
        object.getStringObjects().forEach(o -> assertThat(o).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateExtendedSetOfSimpleObjects() {
        // when
        ExtendedSetOfStringsObject object = manufacturerFactory.generateObject(ExtendedSetOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObjects()).isNotNull().hasSize(2);
        object.getStringObjects().forEach(o -> assertThat(o).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateExtendedArrayOfStringsObject() {
        // when
        ExtendedArrayOfStringsObject object = manufacturerFactory.generateObject(ExtendedArrayOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getArray()).isNotNull().hasSize(2);
        for (int i = 0; i < object.getArray().length; i++) {
            assertThat(object.getArray()[i]).isNotNull().isNotEmpty();
        }
    }

    @Test
    public void shouldGenerateExtendedMapStringStringObject() {
        // when
        ExtendedMapStringStringObject object = manufacturerFactory.generateObject(ExtendedMapStringStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getMap()).isNotNull().hasSize(2);
        object.getMap().forEach((s, s2) -> {
            assertThat(s).isNotNull().isNotEmpty();
            assertThat(s2).isNotNull().isNotEmpty();
        });
    }

    @Ignore
    @Test
    public void shouldGenerateExtendedMixOfEverythingObject() {
        // when
        ExtendedMixOfEverythingObject object = manufacturerFactory.generateObject(ExtendedMixOfEverythingObject.class);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhileInstantiatingClassExceptionOccured() {
        // when
        manufacturerFactory.generateObject(LocalDateTime.class);
    }

    @Test
    public void shouldGenerateListOfStrings() {
        // when
        List<String> result = manufacturerFactory.generateObject(List.class, String.class);

        // then
        assertThat(result).isNotNull().hasSize(2);
        result.forEach(value -> assertThat(value).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateStringArray() {
        // when
        String[] result = manufacturerFactory.generateObject(String[].class);

        // then
        assertThat(result).isNotNull().hasSize(2);
    }

    @Test
    public void shouldGenerateSimpleMapIntegerString() {
        // when
        Map<Integer, String> result = manufacturerFactory.generateObject(Map.class, Integer.class, String.class);

        // then
        assertThat(result.size()).isEqualTo(2);
        result.forEach((key, value) -> {
            assertThat(key).isNotNull();
            assertThat(value).isNotNull().isNotEmpty();
        });
    }

    @Ignore
    @Test
    public void checkPerformance() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            manufacturerFactory.generateObject(ExtendedBooleanStringObject.class);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }
}
