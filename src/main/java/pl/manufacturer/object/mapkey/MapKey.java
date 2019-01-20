package pl.manufacturer.object.mapkey;

import java.util.Objects;

public class MapKey {

    private Class clazz;

    private String mappedByParameter;

    private String fieldName;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getMappedByParameter() {
        return mappedByParameter;
    }

    public void setMappedByParameter(String mappedByParameter) {
        this.mappedByParameter = mappedByParameter;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapKey that = (MapKey) o;
        return Objects.equals(clazz, that.clazz) &&
                Objects.equals(mappedByParameter, that.mappedByParameter);// &&
//                Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, mappedByParameter);
    }

    public static OneToOneMapKeyBuilder builder() {
        return new OneToOneMapKeyBuilder();
    }

    public static final class OneToOneMapKeyBuilder {
        private Class clazz;
        private String mappedByParameter;
        private String fieldName;

        private OneToOneMapKeyBuilder() {
        }

        public OneToOneMapKeyBuilder withClazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public OneToOneMapKeyBuilder withMappedByParameter(String mappedByParameter) {
            this.mappedByParameter = mappedByParameter;
            return this;
        }

        public OneToOneMapKeyBuilder withFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public MapKey build() {
            MapKey mapKey = new MapKey();
            mapKey.setClazz(clazz);
            mapKey.setMappedByParameter(mappedByParameter);
            mapKey.setFieldName(fieldName);
            return mapKey;
        }
    }
}
