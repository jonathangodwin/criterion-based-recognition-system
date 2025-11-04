package attribute;

public class BooleanAttribute extends Attribute<Boolean> {

    public BooleanAttribute(String name, Boolean value, double weight){
        super(name, value, weight);
    }

    @Override
    public String getType() {
        return "boolean";
    }

    public BooleanAttribute(String name, Boolean value){
        super(name, value, 1.0);
    }

    @Override
    public double distance(Attribute<Boolean> other){
        if(other == null) return 1.0;
        if(!(other instanceof BooleanAttribute)) return 1.0;

        BooleanAttribute o = (BooleanAttribute) other;
        return this.value.equals(o.getValue()) ? 0.0 : 1.0;
    }
}
