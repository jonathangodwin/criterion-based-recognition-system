package model;

public class Domain {
    private String domainName; 

    public Domain(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return this.domainName;
    }

    @Override
    public String toString() {
        return "Domain : " + this.domainName;
    }
}