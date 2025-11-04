package model;

import attribute.*;

import java.util.*;


public abstract class Descriptible {
    protected List<Attribute> attributes;
    protected Domain domain;

    public Descriptible(List<Attribute> attributes, Domain domain){
        this.attributes = attributes;
        this.domain = domain;
    }

    public void addAttribute(Attribute a){
        this.attributes.add (a);
    }
   
    public void dropAttribute(String name) {
        int i = 0;
        boolean found = false;
        Attribute attribute = null;
        
        while (i < this.attributes.size() && !found){
            
            if (this.attributes.get(i).getName().equals(name)) {
                attribute = this.attributes.get(i);
                found = true;
            } else i++;
        }

        if (attribute != null) this.attributes.remove(attribute);

        
    }
    
    public Attribute getAttribute(String name) {
        int i = 0;
        boolean found = false;
        Attribute attribute = null;
        
        while (i < this.attributes.size() && !found){
            
            if (this.attributes.get(i).getName().equals(name)) {
                attribute = this.attributes.get(i);
                found = true;
            } else i++;
        }

        return attribute;

    }
    
    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    public int nbAttributes() {
        return this.attributes.size();
    }

    public Domain getAssociatedDomain() {
        return this.domain;
    }

}