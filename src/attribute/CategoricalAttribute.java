package attribute;

public class CategoricalAttribute extends Attribute<String> {

    public CategoricalAttribute(String name, String value, double weight){
        super(name, value, weight);
    }

    public CategoricalAttribute(String name, String value){
        super(name, value, 1.0);
    }

    @Override
    public String getType() {
        return "categorical";
    }

    @Override
    public double distance(Attribute<String> other){
        if(other == null) return 1.0;
        if(!(other instanceof CategoricalAttribute)) return 1.0;

        CategoricalAttribute o = (CategoricalAttribute) other;
        return this.value.equals(o.getValue()) ? 0.0 : 1.0;
    }
}
