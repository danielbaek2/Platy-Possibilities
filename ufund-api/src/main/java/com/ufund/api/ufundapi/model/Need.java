package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a Need
 */
public class Need {

    @JsonProperty("id") private int id;
    @JsonProperty("title") private String title;
    @JsonProperty("description") private String description;
    @JsonProperty("quantity") private int quantity; //amount of need units
    @JsonProperty("cost") private int cost; //cost per need unit
    @JsonProperty("quantity_funded") private int quantity_funded = 0;

    //To string format
    static final String STRING_FORMAT = "Need [id = %d, title = %s]";

    /**
     * Constructor for creating a need with a given id and title
     * 
     * @param id the id of the need
     * @param title the title of the need
     * @param quantity the amount of units that can be funded for this need
     * @param cost the cost of each unit
     * 
     */
    @JsonCreator
    public Need(@JsonProperty("id") int id, @JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("quantity") int quantity, @JsonProperty("cost") int cost){
        this.id = id;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
    }

    /**
     * Acquire the int id of the need object
     * @return the id of the need
     */
    public int getId() {return id;}

    /**
     * Acquire the string title of the need object
     * @return the title of the need
     */
    public String getTitle() {return title;}

    /**
     * Acquire the int total funding amount of units that have been funded
     * @return the amount of funded units
     */
    public int getQuantityFunded() {return quantity_funded;}

    /**
     * Acquire the int cost per unit of the need
     * @return the cost per unit of the need
     */
    public int getCost() {return cost;}

    
    /**
     * Acquire the int total units the need has available to fund
     * @return the total units of the need
     */
    public int getQuantity() {return quantity;}

    /**
     * Acquire the string description of the need object
     * @return the description of the need
     */
    public String getDescription() {return description;}

    public void setDescription(String description){this.description = description;}

    /**
     * Set the title of the need, for JSON deserialization
     * @param title, the new title for the need
     */
    public void setTitle(String title) {this.title = title;}


    /**
     * fund a need x times, x = 1 by default; 
     * @param amount the int unit amount to fund, default is 1
     */
    public void fundNeed(int amount){
        if(fundable()){
            quantity_funded += amount;
        
            if(quantity_funded > quantity){
                quantity_funded = quantity;
            }
        }
    }

    public void fundNeed(){
        if(fundable()){
            quantity_funded ++;
        
            if(quantity_funded > quantity){
                quantity_funded = quantity;
            }
        }
        
    }

    /**
     * Will calculate the dollar amount of the total funding of this need.
     * 
     * @return int dollar amount of how much funding the ened has gotten.
     */
    public int amountFunded(){
        return quantity_funded * cost;
    }

    public boolean fundable(){
        if(quantity_funded <= quantity){
            return true;
        }

        else{
            return false;
        }
    }

    /**
     * A formatted string representation of the need
     * @return formatted string representation
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Need)) {
            return false;
        } else {
            Need other = (Need) obj;
            return this.id == other.id &&
                    this.title.equals(other.getTitle());
        }
    }
}

