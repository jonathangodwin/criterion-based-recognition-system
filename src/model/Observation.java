package model;

import attribute.*;

import java.util.*;

public class Observation extends Descriptible{
    private int idObservation;
    private int idObservator;
    private String notes;
    
    //private Domaine domaine;

    public Observation(List<Attribute> attributes, Domain domain,
                       int idObservation, int idObservator, String notes){
        
        super(attributes, domain);

        this.idObservation = idObservation;
        this.idObservator = idObservator;
        this.notes = notes;
    }
}