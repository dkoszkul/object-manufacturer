package pl.manufacturer.object.example.pojo.extended;

import pl.manufacturer.object.example.pojo.simple.SimpleBooleanObject;
import pl.manufacturer.object.example.pojo.simple.SimpleStringObject;

public class ExtendedBooleanStringObject {

    private String simpleString;

    private Boolean simpleBoolean;

    private SimpleStringObject simpleStringObject;

    private SimpleBooleanObject simpleBooleanObject;

    public String getSimpleString() {
        return simpleString;
    }

    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }

    public Boolean getSimpleBoolean() {
        return simpleBoolean;
    }

    public void setSimpleBoolean(Boolean simpleBoolean) {
        this.simpleBoolean = simpleBoolean;
    }

    public SimpleStringObject getSimpleStringObject() {
        return simpleStringObject;
    }

    public void setSimpleStringObject(SimpleStringObject simpleStringObject) {
        this.simpleStringObject = simpleStringObject;
    }

    public SimpleBooleanObject getSimpleBooleanObject() {
        return simpleBooleanObject;
    }

    public void setSimpleBooleanObject(SimpleBooleanObject simpleBooleanObject) {
        this.simpleBooleanObject = simpleBooleanObject;
    }
}
