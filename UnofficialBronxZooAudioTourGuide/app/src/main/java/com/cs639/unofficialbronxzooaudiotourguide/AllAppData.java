package com.cs639.unofficialbronxzooaudiotourguide;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AllAppData extends ViewModel {
    private final MutableLiveData<String> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> currentLocation;
    private Location currentPhoneLocation;
    private int screenSize;
    private String userFilter;
    private Context context;
    private float azimuth = 0;
    private ArrayList<Animal> animals;
    private ArrayList<Structure> structures;
    private ArrayList<AnimalContainerStructure> animalContainerStructures;
    private Animal currentlySelectedAnimal;
    private Structure currentlySelectedStructure;
    private CompassListFragment compassList;
    private boolean isMetric;
    public Animal getCurrentlySelectedAnimal() {
        return currentlySelectedAnimal;
    }

    public MutableLiveData<String> getCurrentLocation() {
        if (currentLocation == null) {
            currentLocation = new MutableLiveData<String>();
        }
        return currentLocation;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void setCurrentlySelectedAnimal(Animal currentlySelectedAnimal) {
        this.currentlySelectedAnimal = currentlySelectedAnimal;
    }

    public Structure getCurrentlySelectedStructure() {
        return currentlySelectedStructure;
    }

    public void setCurrentlySelectedStructure(Structure currentlySelectedStructure) {
        this.currentlySelectedStructure = currentlySelectedStructure;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }


    public int getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(int screenSize) {
        this.screenSize = screenSize;
    }
    public String getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        this.userFilter = userFilter;
    }
    public MutableLiveData<String> getUser() {
        return userLiveData;
    }

    public boolean isAnimalSelected(){
        return false;
    }
    public boolean isAnimalStub(){
        return false;
    }
    public void UserModel() {
        // trigger user load.
    }

    void doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }

    public void compassViewClicked(int numberClicked){
            if(numberClicked < animals.size()) {
                currentlySelectedAnimal = animals.get(numberClicked);
            }
            compassList.listClickedOn(numberClicked);
    }

    public CompassListFragment getCompassList() {
        return compassList;
    }

    public void setCompassList(CompassListFragment compassList) {
        this.compassList = compassList;
    }
    public ArrayList<AnimalContainerStructure> getAnimalContainerStructures() {
        return animalContainerStructures;
    }

    public void setAnimalContainerStructures(ArrayList<AnimalContainerStructure> animalContainerStructures) {
        this.animalContainerStructures = animalContainerStructures;
    }

    /**
     * Given the title of an item on a list, returns its location
     *
     * @param myThing
     * @return
     */
    public Location getLocationOf(String myThing){
        Location ret = null;
        for(int iter = 0; iter < animals.size(); iter++){
            if(animals.get(iter).getZooName().equals(myThing)){
                ret = animals.get(iter).getViewingPoints().get(0);
            }
        }
        for(int iter = 0; iter < structures.size(); iter++){
            if(structures.get(iter).getStructureName().equals(myThing)){
                ret = structures.get(iter).getViewingPoints().get(0);
            }
        }
        for(int iter = 0; iter < animalContainerStructures.size(); iter++) {
            if (animalContainerStructures.get(iter).getContainerName().equals(myThing)) {
                ret = animalContainerStructures.get(iter).getViewingPoints();
            }
        }
        return ret;

    }

    public Location getCurrentPhoneLocation() {
        return currentPhoneLocation;
    }

    public void setCurrentPhoneLocation(Location currentPhoneLocation) {
        Location emptyLocation = new Location("");
        if(currentPhoneLocation !=null) {
            if (!currentPhoneLocation.equals(emptyLocation)) {
                this.currentPhoneLocation = currentPhoneLocation;
            }
        }
    }
    public float getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
    }

    public void clickClear(){
        compassList.sortListByLocation(getCurrentPhoneLocation());

    }
    public boolean isMetric() {
        return isMetric;
    }

    public void setMetric(boolean metric) {
        isMetric = metric;
    }


}
