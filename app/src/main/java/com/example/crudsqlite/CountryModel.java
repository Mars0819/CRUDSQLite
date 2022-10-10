package com.example.crudsqlite;

public class CountryModel {
    public static CountryModel selectedCountry;
    private int id;
    private String name;
    private int population;

    public CountryModel(int id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }


    public int getId() {return id;}

    public String getName() {return name;}

    public int getPopulation() {return population;}
}
