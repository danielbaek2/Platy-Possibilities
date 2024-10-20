package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a Need
 */
public class Need {

    @JsonProperty("id") private int id;
    @JsonProperty("title") private String title;
    @JsonProperty("description") private String description;
    @JsonProperty("total_funding") private int total_funding = 0;

    //To string format
    static final String STRING_FORMAT = "Need [id = %d, title = %s]";

    /**
     * Constructor for creating a need with a given id and title
     * 
     * @param id the id of the need
     * @param title the title of the need
     * 
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("title") String title){
        this.id = id;
        this.title = title;
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
     * Acquire the int total funding amount of the need object
     * @return the total funding of the need
     */
    public int getTotalFunding() {return total_funding;}

    /**
     * Acquire the string description of the need object
     * @return the description of the need
     */
    public String getDescription() {return description;}

    /**
     * Set the title of the need, for JSON deserialization
     * @param title, the new title for the need
     */
    public void setTitle(String title) {this.title = title;}


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

