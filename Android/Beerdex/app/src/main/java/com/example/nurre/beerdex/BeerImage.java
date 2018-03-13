package com.example.nurre.beerdex;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by nurre on 2018-03-07.
 */

public class BeerImage implements Parcelable { //Parcelable is used for sending a BeerImage between activities via intents.

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

    public BeerImage(Parcel in){
        this.image_id = in.readInt();
        this.link = in.readString();
        this.userName = in.readString();
        this.beerName = in.readString();
        this.breweryName = in.readString();
        this.country = in.readString();
        this.description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(image_id);
        out.writeString(link);
        out.writeString(userName);
        out.writeString(beerName);
        out.writeString(breweryName);
        out.writeString(country);
        out.writeString(description);
    }

    public static final Parcelable.Creator<BeerImage> CREATOR = new Parcelable.Creator<BeerImage>() {
        public BeerImage createFromParcel(Parcel in) {
            return new BeerImage(in);
        }

        public BeerImage[] newArray(int size) {
            return new BeerImage[size];
        }
    };



}
