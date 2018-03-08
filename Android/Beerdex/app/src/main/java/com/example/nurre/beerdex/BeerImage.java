package com.example.nurre.beerdex;

/**
 * Created by nurre on 2018-03-07.
 */

public class BeerImage {

    private int image_id;
    private String link, userName, beerName, beerType, breweryName, country, description;

    public BeerImage(int image_id, String link, String user_name, String beer_name, String beer_type, String brewery_name, String country, String description){
        this.image_id = image_id;
        this.link = link;
        this.userName = user_name;
        this.beerName = beer_name;
        this.beerType = beer_type;
        this.breweryName = brewery_name;
        this.country = country;
        this.description = description;
    }

    public BeerImage(String userName, String description) {
        this.userName = userName;
        this.description = description;
    }

    public int getImageId(){
        return this.image_id;
    }

    public String getLink(){
        return this.link;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getBeerName(){
        return this.beerName;
    }

    public String getBeerType(){
        return this.beerType;
    }

    public String getBreweryName(){
        return this.breweryName;
    }

    public String getCountry(){
        return this.country;
    }

    public String getDescription(){
        return this.description;
    }

    public void setImage_id(int newImageId){
        this.image_id = newImageId;
    }

    public void setLink(String newLink){
        this.link = newLink;
    }

    public void setUserName(String newUserName){
        this.link = newUserName;
    }

    public void setBeerName(String newBeerName){
        this.link = newBeerName;
    }

    public void setBreweryName(String newBreweryName){
        this.link = newBreweryName;
    }

    public void setCountry(String newCountry){
        this.link = newCountry;
    }

    public void setDescription(String newDescription){
        this.link = newDescription;
    }
}
