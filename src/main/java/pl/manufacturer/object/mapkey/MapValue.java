package pl.manufacturer.object.mapkey;

public class MapValue {

    private Object referenceObject;

    private Object generatedObject;

    public Object getReferenceObject() {
        return referenceObject;
    }

    public void setReferenceObject(Object referenceObject) {
        this.referenceObject = referenceObject;
    }

    public Object getGeneratedObject() {
        return generatedObject;
    }

    public void setGeneratedObject(Object generatedObject) {
        this.generatedObject = generatedObject;
    }

    public static MapValue.OneToOnaMapValueBuilder builder() {
        return new MapValue.OneToOnaMapValueBuilder();
    }

    public static final class OneToOnaMapValueBuilder {
        private Object referenceObject;
        private Object generatedObject;

        private OneToOnaMapValueBuilder() {
        }

        public OneToOnaMapValueBuilder withReferenceObject(Object referenceObject) {
            this.referenceObject = referenceObject;
            return this;
        }

        public OneToOnaMapValueBuilder withGeneratedObject(Object generatedObject) {
            this.generatedObject = generatedObject;
            return this;
        }

        public MapValue build() {
            MapValue mapValue = new MapValue();
            mapValue.setReferenceObject(referenceObject);
            mapValue.setGeneratedObject(generatedObject);
            return mapValue;
        }
    }
}
