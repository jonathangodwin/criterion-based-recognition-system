package attribute;


public abstract class Attribute<T> {
    protected String name;
    protected T value;
    protected double weight;

    public Attribute(String name, T value, double weight){
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public Attribute(String name, T value) {
        this(name, value, 1.0);
    }
    

    public String getName(){
        return this.name;
    }
    public T getValue(){
        return this.value;
    }

    public abstract String getType();

    public double getWeight() {
        return this.weight;
    }

    public abstract double distance(Attribute<T> other);
}