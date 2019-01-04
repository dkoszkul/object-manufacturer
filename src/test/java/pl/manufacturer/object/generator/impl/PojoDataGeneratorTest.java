package pl.manufacturer.object.generator.impl;

import org.junit.Ignore;
import org.junit.Test;
import pl.manufacturer.object.example.pojo.extended.*;
import pl.manufacturer.object.example.pojo.simple.*;
import pl.manufacturer.object.generator.DataGenerator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PojoDataGeneratorTest {

    private DataGenerator dataGenerator = new PojoDataGenerator();

    @Test
    public void shouldGenerateExampleSimpleStringObject() {
        // when
        SimpleStringObject object = dataGenerator.generateObject(SimpleStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObject()).isNotNull().isNotEmpty();
    }

    @Test
    public void shouldGenerateExampleSimpleBooleanObject() {
        // when
        SimpleBooleanObject object = dataGenerator.generateObject(SimpleBooleanObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getBooleanObject()).isNotNull();
        assertThat(object.isBooleanPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleIntegerObject() {
        // when
        SimpleIntegerObject object = dataGenerator.generateObject(SimpleIntegerObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getIntegerObject()).isNotNull();
        assertThat(object.getIntegerPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleDoubleObject() {
        // when
        SimpleDoubleObject object = dataGenerator.generateObject(SimpleDoubleObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getDoubleObject()).isNotNull();
        assertThat(object.getDoublePrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleFloatObject() {
        // when
        SimpleFloatObject object = dataGenerator.generateObject(SimpleFloatObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getFloatObject()).isNotNull();
        assertThat(object.getFloatPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleLongObject() {
        // when
        SimpleLongObject object = dataGenerator.generateObject(SimpleLongObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getLongObject()).isNotNull();
        assertThat(object.getLongPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleCharacterObject() {
        // when
        SimpleCharacterObject object = dataGenerator.generateObject(SimpleCharacterObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getCharacterObject()).isNotNull();
        assertThat(object.getCharacterPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleByteObject() {
        // when
        SimpleByteObject object = dataGenerator.generateObject(SimpleByteObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getByteObject()).isNotNull();
        assertThat(object.getBytePrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleShortObject() {
        // when
        SimpleShortObject object = dataGenerator.generateObject(SimpleShortObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getShortObject()).isNotNull();
        assertThat(object.getShortPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleDateObject() {
        // when
        SimpleDateObject object = dataGenerator.generateObject(SimpleDateObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getDate()).isNotNull();
    }


    @Test
    public void shouldGenerateExampleSimpleLocalDateTimeObject() {
        // when
        SimpleLocalDateTimeObject object = dataGenerator.generateObject(SimpleLocalDateTimeObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getLocalDateTime()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleEnumObject() {
        // when
        SimpleEnumObject object = dataGenerator.generateObject(SimpleEnumObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getSize()).isNotNull();
    }

    @Test
    public void shouldGenerateExtendedObject() {
        // when
        ExtendedBooleanStringObject object = dataGenerator.generateObject(ExtendedBooleanStringObject.class);

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
        ExtendedListOfStringsObject object = dataGenerator.generateObject(ExtendedListOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObjects()).isNotNull().hasSize(2);
        object.getStringObjects().forEach(o -> assertThat(o).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateExtendedSetOfSimpleObjects() {
        // when
        ExtendedSetOfStringsObject object = dataGenerator.generateObject(ExtendedSetOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObjects()).isNotNull().hasSize(2);
        object.getStringObjects().forEach(o -> assertThat(o).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateExtendedArrayOfStringsObject() {
        // when
        ExtendedArrayOfStringsObject object = dataGenerator.generateObject(ExtendedArrayOfStringsObject.class);

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
        ExtendedMapStringStringObject object = dataGenerator.generateObject(ExtendedMapStringStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getMap()).isNotNull().hasSize(2);
        object.getMap().forEach((s, s2) -> {
            assertThat(s).isNotNull().isNotEmpty();
            assertThat(s2).isNotNull().isNotEmpty();
        });
    }

    @Test
    public void shouldGenerateExtendedMixOfEverythingObject() {
        // when
        ExtendedMixOfEverythingObject object = dataGenerator.generateObject(ExtendedMixOfEverythingObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getString1()).isNotNull().isNotEmpty();
        assertThat(object.getObject1()).isNotNull();
        assertThat(object.getObject1().getArray()).hasSize(2);
        assertThat(object.getArray1()).hasSize(2);
        assertThat(object.getList1()).hasSize(2);
        object.getList1().forEach(o -> assertThat(o).isNotNull());
        assertThat(object.getLong1()).isNotNull();

        assertThat(object.getMap1()).hasSize(2);
        object.getMap1().forEach((key, value) -> {
            assertThat(key).isNotNull().isNotEmpty();
            assertThat(value).isNotNull();
            assertThat(value.getStringObjects()).hasSize(2);
        });

        assertThat(object.getMap2()).hasSize(2);
        object.getMap2().forEach((key, value) -> {
            assertThat(key).hasSize(2);
            key.forEach((subkey, subvalue) -> {
                assertThat(subkey).isNotNull().isNotEmpty();
                assertThat(subvalue).isNotNull();
                assertThat(subvalue.getBooleanObject()).isNotNull();
            });
            assertThat(value).isNotNull();
        });

        assertThat(object.getMap3()).hasSize(2);
        object.getMap3().forEach((key, value) -> {
            assertThat(key).hasSize(2);
            key.forEach(item -> assertThat(item).isNotNull());
            assertThat(value).hasSize(2);
            value.forEach((subkey, subvalue) -> {
                assertThat(subkey).isNotNull();
                assertThat(subvalue).isNotNull();
            });
        });

        assertThat(object.getMap4()).hasSize(2);
        object.getMap4().forEach((key, value) -> {
            assertThat(key).hasSize(2);
            key.forEach(item -> assertThat(item).isNotNull());
            assertThat(value).hasSize(2);
            value.forEach((subkey, subvalue) -> {
                assertThat(subkey).isNotNull().hasSize(2);
                subkey.forEach((subsubkey, subsubvalue) -> {
                    assertThat(subsubkey).isNotNull();
                    assertThat(subsubvalue).isNotNull();
                });
                assertThat(subvalue).isNotNull();
            });
        });
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhileInstantiatingClassExceptionOccured() {
        // when
        dataGenerator.generateObject(FunctionalInterface.class);
    }

    @Test
    public void shouldGenerateListOfStrings() {
        // when
        List<String> result = dataGenerator.generateObject(List.class, String.class);

        // then
        assertThat(result).isNotNull().hasSize(2);
        result.forEach(value -> assertThat(value).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateStringArray() {
        // when
        String[] result = dataGenerator.generateObject(String[].class);

        // then
        assertThat(result).isNotNull().hasSize(2);
    }

    @Test
    public void shouldGenerateSimpleMapIntegerString() {
        // when
        Map<Integer, String> result = dataGenerator.generateObject(Map.class, Integer.class, String.class);

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
            dataGenerator.generateObject(ExtendedBooleanStringObject.class);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }
}
