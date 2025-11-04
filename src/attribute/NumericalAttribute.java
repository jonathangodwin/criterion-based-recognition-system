package attribute;

public class NumericalAttribute extends Attribute<Double>{
    private Double min, max;
    
    public NumericalAttribute(String name, Double value, double weight,
                                Double min, Double max){
        super(name, value, weight);

        this.min = min;
        this.max = max;

        
    }

    @Override
    public String getType() {
        return "numerical";
    }

    @Override
    public double distance(Attribute<Double>  other){
        if(other == null) return 1.0;

        double denom = this.max - this.min;
        if(denom == 0) return 0;
        
        return Math.abs(this.value - other.getValue()) / denom;
    }


}