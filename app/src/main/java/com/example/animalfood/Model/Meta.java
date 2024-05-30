package com.example.animalfood.Model;

public class Meta {
    private String genera;
    private String synonyms;
    private String typeSpecies;
    private String binomialName;
    private String conservationStatus;
    private ScientificClassification scientificClassification;

    public Meta() {
    }

    public String getGenera() {
        return genera;
    }

    public void setGenera(String genera) {
        this.genera = genera;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getTypeSpecies() {
        return typeSpecies;
    }

    public void setTypeSpecies(String typeSpecies) {
        this.typeSpecies = typeSpecies;
    }

    public String getBinomialName() {
        return binomialName;
    }

    public void setBinomialName(String binomialName) {
        this.binomialName = binomialName;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public ScientificClassification getScientificClassification() {
        return scientificClassification;
    }

    public void setScientificClassification(ScientificClassification scientificClassification) {
        this.scientificClassification = scientificClassification;
    }
}
