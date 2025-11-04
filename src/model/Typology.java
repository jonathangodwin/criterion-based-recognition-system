package model; 

import attribute.*;

import java.util.*;

public class Typology extends  Descriptible{
    private String typologyName;
    private String description;

    public Typology(List<Attribute> attributes, Domain domain,
                    String typologyName, String description) {
        super(attributes, domain);

        this.typologyName = typologyName;
        this.description = description;
    }

    public String getTypologyName() {
        return this.typologyName;
    }

    public String getDescription() {
        return this.description;
    }


}