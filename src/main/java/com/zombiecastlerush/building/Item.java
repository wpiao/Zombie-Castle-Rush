package com.zombiecastlerush.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "description", "price"})
public class Item {
    private String name, description;
    private double price;

    @JsonCreator
    public Item(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") double price) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "name= " + name + " description= " + description;
    }
}