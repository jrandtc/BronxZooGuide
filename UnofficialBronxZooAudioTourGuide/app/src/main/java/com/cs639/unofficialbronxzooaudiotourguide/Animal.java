

package com.cs639.unofficialbronxzooaudiotourguide;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Animal {
    private int id;
    private int parentStructure;
    private String searchString;
    private String zooName;
    private String family;
    private String animalClass;
    private String conservationStatus;
    private String naturalLocation;
    private String binomialNomenclature;
    private String eolLink;
    private String wikiLink;
    private String redlistLink;
    private ArrayList<String> otherResourceLinks;
    private ArrayList<String> otherResourceNames;
    private ArrayList<String> commonNames;
    private ArrayList<String> description;
    private ArrayList<Location> viewingPoints;
    private boolean isChecked;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<Location> getViewingPoints() {
        return viewingPoints;
    }

    public void setViewingPoints(ArrayList<Location> viewingPoints) {
        this.viewingPoints = viewingPoints;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public String getZooName() {
        return zooName;
    }

    public void setZooName(String zooName) {
        this.zooName = zooName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
        Log.e("animalfamily", "family is set as " + family);

    }

    public String getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public String getNaturalLocation() {
        return naturalLocation;
    }

    public void setNaturalLocation(String naturalLocation) {
        this.naturalLocation = naturalLocation;
    }

    public String getBinomialNomenclature() {
        return binomialNomenclature;
    }

    public void setBinomialNomenclature(String binomialNomenclature) {
        this.binomialNomenclature = binomialNomenclature;
    }

    public String getEolLink() {
        return eolLink;
    }

    public void setEolLink(String eolLink) {
        this.eolLink = eolLink;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public ArrayList<String> getOtherResourceLinks() {
        return otherResourceLinks;
    }

    public void setOtherResourceLinks(ArrayList<String> otherResourceLinks) {
        this.otherResourceLinks = otherResourceLinks;
    }

    public ArrayList<String> getOtherResourceNames() {
        return otherResourceNames;
    }

    public void setOtherResourceNames(ArrayList<String> otherResourceNames) {
        this.otherResourceNames = otherResourceNames;
    }

    public ArrayList<String> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(ArrayList<String> commonNames) {
        this.commonNames = commonNames;
    }

    public int getParentStructure() {
        return parentStructure;
    }

    public void setParentStructure(int parentStructure) {
        this.parentStructure = parentStructure;
    }
    public Animal(){
        zooName = "error";
        family = "error";
        animalClass = "error";
        conservationStatus = "error";
        naturalLocation = "error";
        binomialNomenclature = "error";
        eolLink = "error";
        isChecked = false;
        wikiLink = "error";
        id = -1;
        parentStructure = 0;
    }

    public boolean matchesFilter(String filter){
        String searchString = "";
        String lowerFilter = filter.toLowerCase();
        boolean ret = false;
        searchString += zooName;
        searchString += family;
        searchString += animalClass;
        searchString += conservationStatus;
        searchString += naturalLocation;
        searchString += binomialNomenclature;
        searchString += eolLink;
        searchString += wikiLink;
        for(int x = 0; x < otherResourceLinks.size(); x++){
            searchString += otherResourceLinks.get(x);
        }
        for(int x = 0; x < commonNames.size(); x++){
            searchString += commonNames.get(x);
        }
        for(int x = 0; x < otherResourceNames.size(); x++){
            searchString += otherResourceNames.get(x);
        }
        for(int x = 0; x < description.size(); x++){
            searchString += description.get(x);
        }
        searchString = searchString.toLowerCase();

        if(searchString.indexOf(lowerFilter) != -1){
           ret = true;
        }
        return ret;
    }
    public String getSearchString() {
        String newSearchString = "";
        Log.i("TOMDEBUG", "is everything really empty: " + zooName);
        newSearchString += zooName;
        newSearchString += family;
        newSearchString += animalClass;
        newSearchString += conservationStatus;
        newSearchString += naturalLocation;
        newSearchString += binomialNomenclature;
        newSearchString += eolLink;
        newSearchString += wikiLink;
        for(int x = 0; x < otherResourceLinks.size(); x++){
            newSearchString += otherResourceLinks.get(x);
        }
        for(int x = 0; x < commonNames.size(); x++){
            newSearchString += commonNames.get(x);
        }
        for(int x = 0; x < otherResourceNames.size(); x++){
            newSearchString += otherResourceNames.get(x);
        }
        for(int x = 0; x < description.size(); x++){
            newSearchString += description.get(x);
        }
        newSearchString = newSearchString.toLowerCase();
        return newSearchString;
    }
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getRedlistLink() {
        return redlistLink;
    }

    public void setRedlistLink(String redlistLink) {
        this.redlistLink = redlistLink;
    }
}
