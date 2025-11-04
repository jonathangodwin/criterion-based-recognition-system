package utils.engine;

import model.Typology;

public class TypologyDistance {
    private Typology typologie;
    private double distance;

    public TypologyDistance(Typology typologie, double distance) {
        this.typologie = typologie;
        this.distance = distance;
    }

    public Typology getTypologie() { return typologie; }
    public double getDistance() { return distance; }
    
}