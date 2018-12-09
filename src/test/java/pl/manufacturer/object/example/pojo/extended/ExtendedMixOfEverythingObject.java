package pl.manufacturer.object.example.pojo.extended;

import pl.manufacturer.object.example.pojo.simple.SimpleBooleanObject;

import java.util.List;
import java.util.Map;

public class ExtendedMixOfEverythingObject {

    private Map<String, ExtendedSetOfStringsObject> map1;

    private Map<Map<String, SimpleBooleanObject>, String> map2;

    private Map<List<Integer>, Map<Long, Integer>> map3;

    private Map<List<Integer>, Map<Map<Integer, Integer>, Integer>> map4;

    private List<ExtendedListOfStringsObject> list1;

    private String string1;

    private SimpleBooleanObject[] array1;

    private long long1;

    private ExtendedArrayOfStringsObject object1;

    public Map<String, ExtendedSetOfStringsObject> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, ExtendedSetOfStringsObject> map1) {
        this.map1 = map1;
    }

    public Map<Map<String, SimpleBooleanObject>, String> getMap2() {
        return map2;
    }

    public void setMap2(Map<Map<String, SimpleBooleanObject>, String> map2) {
        this.map2 = map2;
    }

    public Map<List<Integer>, Map<Long, Integer>> getMap3() {
        return map3;
    }

    public void setMap3(Map<List<Integer>, Map<Long, Integer>> map3) {
        this.map3 = map3;
    }

    public Map<List<Integer>, Map<Map<Integer, Integer>, Integer>> getMap4() {
        return map4;
    }

    public void setMap4(Map<List<Integer>, Map<Map<Integer, Integer>, Integer>> map4) {
        this.map4 = map4;
    }

    public List<ExtendedListOfStringsObject> getList1() {
        return list1;
    }

    public void setList1(List<ExtendedListOfStringsObject> list1) {
        this.list1 = list1;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public SimpleBooleanObject[] getArray1() {
        return array1;
    }

    public void setArray1(SimpleBooleanObject[] array1) {
        this.array1 = array1;
    }

    public long getLong1() {
        return long1;
    }

    public void setLong1(long long1) {
        this.long1 = long1;
    }

    public ExtendedArrayOfStringsObject getObject1() {
        return object1;
    }

    public void setObject1(ExtendedArrayOfStringsObject object1) {
        this.object1 = object1;
    }
}
