package com.cs639.unofficialbronxzooaudiotourguide;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;

/**
 *
 * This Fragment shows the all outside animals; it's the fragment that is loaded first when the
 * project is loaded, showing a list of outside animals and structures with a compass arrow
 * pointing towards it.
 *
 * @author Tom Cookson
 */
public class CompassListFragment extends Fragment  implements SensorEventListener {
    String s1[], s2[], s3[], s4[];
    private String filter;
    private final String CHECKMARK_KEY = "checkmarks";
    private final String ISMETRIC_KEY = "ismetric";
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.cs639.unofficialbronxzooaudiotourguide";

    int retainPosition;
    private String newS1[];
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private String checkmarkData;
    float azimuth;
    Button btnSearch;
    Button btnClear;
    TextView txtSearch;
    int images[];
    int visible[];
    public int animalimages[] =
            {
                    R.drawable.a1,
                    R.drawable.a2,
                    R.drawable.a3,
                    R.drawable.a4,
                    R.drawable.a5,
                    R.drawable.a6,
                    R.drawable.a7,
                    R.drawable.a8,
                    R.drawable.a9,
                    R.drawable.a10,
                    R.drawable.a11,
                    R.drawable.a12,
                    R.drawable.a13,
                    R.drawable.a14,
                    R.drawable.a15,
                    R.drawable.a16,
                    R.drawable.a17,
                    R.drawable.a18,
                    R.drawable.a19,
                    R.drawable.a20,
                    R.drawable.a21,
                    R.drawable.a22,
                    R.drawable.a23,
                    R.drawable.a24,
                    R.drawable.a25,
                    R.drawable.a26,
                    R.drawable.a27,
                    R.drawable.a28,
                    R.drawable.a29,
                    R.drawable.a30,
                    R.drawable.a31,
                    R.drawable.a32,
                    R.drawable.a33,
                    R.drawable.a34,
                    R.drawable.a35,
                    R.drawable.a36,
                    R.drawable.a37,
                    R.drawable.a38,
                    R.drawable.a39,
                    R.drawable.a40,
                    R.drawable.a41,
                    R.drawable.a42,
                    R.drawable.a43,
                    R.drawable.a44,
                    R.drawable.a45,
                    R.drawable.a46,
                    R.drawable.a47,
                    R.drawable.a48,
                    R.drawable.a49,
                    R.drawable.a50,
                    R.drawable.a51,
                    R.drawable.a52,
                    R.drawable.a53,
                    R.drawable.a54,
                    R.drawable.a55,
                    R.drawable.a56,
                    R.drawable.a57,
                    R.drawable.a58,
                    R.drawable.a59,
                    R.drawable.a60,
                    R.drawable.a61,
                    R.drawable.a62,
                    R.drawable.a63,
                    R.drawable.a64,
                    R.drawable.a65,
                    R.drawable.a66,
                    R.drawable.a67,
                    R.drawable.a68,
                    R.drawable.a69,
                    R.drawable.a70,
                    R.drawable.a71,
                    R.drawable.a72,
                    R.drawable.a73,
                    R.drawable.a74,
                    R.drawable.a75,
                    R.drawable.a76,
                    R.drawable.a77,
                    R.drawable.a78,
                    R.drawable.a79,
                    R.drawable.a80,
                    R.drawable.a81,
                    R.drawable.a82,
                    R.drawable.a83,
                    R.drawable.a84,
                    R.drawable.a85,
                    R.drawable.a86,
                    R.drawable.a87,
                    R.drawable.a88,
                    R.drawable.a89,
                    R.drawable.a90,
                    R.drawable.a91,
                    R.drawable.a92,
                    R.drawable.a93,
                    R.drawable.a94,
                    R.drawable.a95,
                    R.drawable.a96
            };
    int structureImages[] =
            {
                    R.drawable.s1,
                    R.drawable.s2,
            };

    int animalStructureImages[] =
            {
                    R.drawable.acs1,
                    R.drawable.acs2,
                    R.drawable.acs3,
                    R.drawable.acs4,
                    R.drawable.acs5,
                    R.drawable.acs6,
                    R.drawable.acs7,
                    R.drawable.acs8,
                    R.drawable.acs9,
                    R.drawable.acs10,
                    R.drawable.acs11
            };
    RecyclerView recyclerView;
    protected OutdoorRecycleAdapter mAdapter;
    View rootView;
    AllAppData userModel;
    Location phoneLocation;
    Location lastLocationWhenItemsSorted;
    private LinearLayoutManager myLinearLayoutManager;
    private SensorManager mSensorManager;
    private Sensor sensorMag;
    private Sensor sensorAcc;
    private ArrayList<AnimalContainerStructure> originalAnimalContainers;
    private ArrayList<Animal> originalAnimals;
    private ArrayList<Structure> originalStructures;

    public static final int LOCATION_REQUEST = 1000;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_first, container, false);
        checkmarkData = "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
                "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"; //u = unknown, x = checked, o = unchecked
        //Each position char is 1 item
        visible = new int[checkmarkData.length()];


        mGravity = new float[3];
        mGeomagnetic = new float[3];
//        azimuth = 0f;
        filter = "";
        lastLocationWhenItemsSorted = new Location("");
        mSensorManager = (SensorManager)rootView.getContext().getSystemService(SENSOR_SERVICE);
        sensorMag = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        btnClear = rootView.findViewById(R.id.btnClear);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        txtSearch = rootView.findViewById(R.id.editTextSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = txtSearch.getText().toString();
                sortListByLocation(userModel.getCurrentPhoneLocation());
                InputMethodManager mgr = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = "";
                txtSearch.setText("");
                sortListByLocation(userModel.getCurrentPhoneLocation());
                InputMethodManager mgr = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
            }
        });

        // Inflate the layout for this fragment
        recyclerView = rootView.findViewById(R.id.OutdoorRecyclerView);
        userModel = new ViewModelProvider(requireActivity()).get(AllAppData.class);
        int width = userModel.getScreenSize();
        retainPosition = 0;
        userModel.setCompassList(this);
        originalAnimalContainers= userModel.getAnimalContainerStructures();
        originalAnimals = userModel.getAnimals();
        originalStructures = userModel.getStructures();
        Log.i("debugdebug", "This is how many animals are " + originalAnimals.size());
        String[][] toSend = buildItemList(userModel.getAnimals(), userModel.getStructures(),
                userModel.getAnimalContainerStructures());
        s1 = toSend[0];
        s2 = toSend[1];
        s3 = toSend[2];

        newS1 = s1;
        addChecksToAnimals();
        mAdapter = new OutdoorRecycleAdapter(rootView.getContext(), this, userModel, s1, s2, s3, images, visible, 10);
        mAdapter.setMyAppData(userModel);
        myLinearLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(myLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * This function decides what goes in the compass view when it first loads
     *
     * @param animals
     * @param structures
     * @param animalContainerStructures
     * @return
     */
    private String[][] buildItemList(ArrayList<Animal> animals, ArrayList<Structure> structures,
                                     ArrayList<AnimalContainerStructure> animalContainerStructures) {
        int size = 0;
        String[][] ret;
        ArrayList<String> namesOfItems = new ArrayList<String>();
        ArrayList<String> BinomOrOtherItems = new ArrayList<String>();
        ArrayList<String> locationOfItems = new ArrayList<String>();
        ArrayList<Location> DistanceOfItems = new ArrayList<Location>();
        ArrayList<Integer> imagesOfItems = new ArrayList<Integer>();
        ArrayList<Integer> checkboxstatusOfItem = new ArrayList<Integer>();
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getParentStructure() == 0) {
                namesOfItems.add(animals.get(i).getZooName());
                BinomOrOtherItems.add(animals.get(i).getBinomialNomenclature());
                imagesOfItems.add(animalimages[i]);
                DistanceOfItems.add(animals.get(i).getViewingPoints().get(0));
                locationOfItems.add("");
                checkboxstatusOfItem.add(View.INVISIBLE);


            }
            //Code to make parent containers searchable by animal data
            if(animals.get(i).getParentStructure() != 0){
                int parentStructureId = 0;
                for(int iter = 0; iter < animalContainerStructures.size(); iter++){
                    if(animalContainerStructures.get(iter).getId() == animals.get(i).getParentStructure()){
                        parentStructureId = iter;
                    }
                }
                String oldString = animalContainerStructures.get(parentStructureId).getSearchString();
                oldString += animals.get(i).getSearchString() + animalContainerStructures.get(parentStructureId).getContainerName();
                animalContainerStructures.get(parentStructureId).setSearchString(oldString);
            }
        }
        for (int i = 0; i < animalContainerStructures.size(); i++) {
            namesOfItems.add(animalContainerStructures.get(i).getContainerName());
            BinomOrOtherItems.add("Structure: Tap for Animals Inside");
            DistanceOfItems.add(animalContainerStructures.get(i).getViewingPoints());
            locationOfItems.add("");
            imagesOfItems.add(animalStructureImages[i]);

                checkboxstatusOfItem.add(View.INVISIBLE);

        }
        for (int i = 0; i < structures.size(); i++) {
            namesOfItems.add(structures.get(i).getStructureName());
            locationOfItems.add("");
            BinomOrOtherItems.add("Structure: Tap for History");
            DistanceOfItems.add(structures.get(i).getViewingPoints().get(0));
            imagesOfItems.add(structureImages[i]);

                checkboxstatusOfItem.add(View.INVISIBLE);

        }

        String s1[] = new String[namesOfItems.size()];
        for (int i = 0; i < namesOfItems.size(); i++) {
            s1[i] = namesOfItems.get(i);
        }
        String s2[] = new String[BinomOrOtherItems.size()];
        for (int i = 0; i < BinomOrOtherItems.size(); i++) {
            s2[i] = BinomOrOtherItems.get(i);
        }
        String s3[] = new String[locationOfItems.size()];
        for (int i = 0; i < locationOfItems.size(); i++) {
            s3[i] = "";
        }


        //Figures out distance between standing person and item
        String s4[] = new String[DistanceOfItems.size()];

        for (int i = 0; i < DistanceOfItems.size(); i++) {
            s4[i] = "";
        }

        //Using private var instead of return since different type. Not good code.
        images = new int[imagesOfItems.size()];
        for (int i = 0; i < imagesOfItems.size(); i++) {
            images[i] = imagesOfItems.get(i);
        }
        //Using private var instead of return since different type. Not good code.
        visible = new int[checkboxstatusOfItem.size()];
        for (int i = 0; i < checkboxstatusOfItem.size(); i++) {
            visible[i] = checkboxstatusOfItem.get(i);
        }

        ret = new String[4][s1.length];
        ret[0] = s1;
        ret[1] = s2;
        ret[2] = s3;
        ret[3] = s4;

        return ret;
    }

    public void listClickedOn(int animalNumber) {
        String nameOfItemClicked = newS1[animalNumber];
        boolean isAnimal = false;
        boolean isStructure = false;
        boolean isAnimalContainingStructure = false;
        int checkboxId = 0;
        int idOfItem = 0;
        for(int iter = 0; iter < userModel.getAnimals().size(); iter++){
            if(userModel.getAnimals().get(iter).getZooName().equals(nameOfItemClicked)){
                isAnimal = true;
                idOfItem = iter;
                checkboxId = userModel.getAnimals().get(iter).getId();
            }
        }
        for(int iter = 0; iter < userModel.getStructures().size(); iter++) {
            if(userModel.getStructures().get(iter).getStructureName().equals(nameOfItemClicked)){
                isStructure = true;
                idOfItem = iter;
                checkboxId = userModel.getStructures().get(iter).getId();

            }
        }
        for(int iter = 0; iter < userModel.getAnimalContainerStructures().size(); iter++) {
            if(userModel.getAnimalContainerStructures().get(iter).getContainerName().equals(nameOfItemClicked)){
                isAnimalContainingStructure = true;
                idOfItem = iter;
                checkboxId = userModel.getAnimalContainerStructures().get(iter).getId();

            }
        }
        if(isAnimal) {
            Intent intent = new Intent(rootView.getContext(), AnimalActivity.class);
            setAnimalCheck(idOfItem);
            intent.putExtra("animalnumber", idOfItem);
            startActivity(intent);
        }
        if(isStructure) {
            Intent intent = new Intent(rootView.getContext(), StructureActivity.class);
            setStructureCheck(checkboxId);
            intent.putExtra("structurenumber", idOfItem);
            startActivity(intent);
        }
        if(isAnimalContainingStructure) {
            Intent intent = new Intent(rootView.getContext(), AnimalBuildingActivity.class);
            setAnimalContainerCheck(checkboxId);
            Log.i("debugdebug", "name of item clicked is: " + nameOfItemClicked + "; id is: " + idOfItem);
            intent.putExtra("parentstructure", (idOfItem));
            intent.putExtra("filter", filter);
            startActivity(intent);
        }
    }



    public void sortListByLocation(Location location){
        int size = 0;
        String[][] ret;
        ArrayList<Animal> animals = userModel.getAnimals();
        ArrayList<AnimalContainerStructure> animalContainerStructures = userModel.getAnimalContainerStructures();
        ArrayList<Structure> structures = userModel.getStructures();
        addChecksToAnimals();
        ArrayList<String> namesOfItems = new ArrayList<String>();
        ArrayList<String> BinomOrOtherItems = new ArrayList<String>();
        ArrayList<String> locationOfItems = new ArrayList<String>();
        ArrayList<Location> DistanceOfItems = new ArrayList<Location>();
        ArrayList<Integer> imagesOfItems = new ArrayList<Integer>();
        ArrayList<Boolean> checkedStatusOfItems = new ArrayList<Boolean>();



        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getParentStructure() == 0) {
                if( filter.equals("") || animals.get(i).matchesFilter(filter) ) {
                    namesOfItems.add(animals.get(i).getZooName());
                    BinomOrOtherItems.add(animals.get(i).getBinomialNomenclature());
                    imagesOfItems.add(animalimages[i]);
                    DistanceOfItems.add(animals.get(i).getViewingPoints().get(0));
                    locationOfItems.add("");

                }
            }
        }
        for (int i = 0; i < animalContainerStructures.size(); i++) {
            if( filter.equals("") || animalContainerStructures.get(i).matchesFilter(filter) ) {
                namesOfItems.add(animalContainerStructures.get(i).getContainerName());
                BinomOrOtherItems.add("Structure: Tap for Animals Inside");
                DistanceOfItems.add(animalContainerStructures.get(i).getViewingPoints());
                locationOfItems.add("");
                imagesOfItems.add(animalStructureImages[i]);

            }
        }
        for (int i = 0; i < structures.size(); i++) {
            if( filter.equals("") || structures.get(i).matchesFilter(filter) ) {
                namesOfItems.add(structures.get(i).getStructureName());
                locationOfItems.add("");
                BinomOrOtherItems.add("Structure: Tap for History");
                DistanceOfItems.add(structures.get(i).getViewingPoints().get(0));
                imagesOfItems.add(structureImages[i]);
            }
        }

        String s1[] = new String[namesOfItems.size()];
        for (int i = 0; i < namesOfItems.size(); i++) {
            s1[i] = namesOfItems.get(i);
        }
        String s2[] = new String[BinomOrOtherItems.size()];
        for (int i = 0; i < BinomOrOtherItems.size(); i++) {
            s2[i] = BinomOrOtherItems.get(i);
        }
        String s3[] = new String[locationOfItems.size()];
        for (int i = 0; i < locationOfItems.size(); i++) {
            s3[i] = locationOfItems.get(i);
        }
        Double myD[] = new Double[DistanceOfItems.size()];
        for (int i = 0; i < DistanceOfItems.size(); i++) {
            Location itemLocation = DistanceOfItems.get(i);
            double toAdd = 0;
            if(itemLocation != null && location != null) {
                toAdd = location.distanceTo(itemLocation);
            }
            myD[i] = toAdd;
        }
        int[] newCheckedStatus = new int[originalAnimals.size() + originalAnimalContainers.size() + originalStructures.size()];
        for(int i = 0; i < originalAnimals.size() + originalAnimalContainers.size() + originalStructures.size(); i++){
                newCheckedStatus[i] = View.INVISIBLE;
        }


        //Using private var instead of return since different type. Not good code.
        images = new int[imagesOfItems.size()];
        for (int i = 0; i < imagesOfItems.size(); i++) {
            images[i] = imagesOfItems.get(i);
        }

        //Puts everything in a a sortable List
        List<ComparableDataItem> comparableItemList = new ArrayList<ComparableDataItem>();
        for(int it = 0; it < s1.length; it++){
            ComparableDataItem myItem = new
                    ComparableDataItem(s1[it], s2[it], s3[it], images[it], myD[it], it, newCheckedStatus[it]);
            comparableItemList.add(myItem);
        }
        //Sort the list
        Collections.sort(comparableItemList);

        //put everything back in sorted order
        newS1 = new String[s1.length];
        String newS2[] = new String[s1.length];
        String newS3[] = new String[s1.length];
        int newImages[] = new int[s1.length];
        int newNewChecked[] = new int[s1.length];
        for(int it = 0; it < comparableItemList.size(); it++){
            newS1[it] = comparableItemList.get(it).getS1();
            newS2[it] = comparableItemList.get(it).getS2();
            newS3[it] = "";
            newImages[it] = comparableItemList.get(it).getI();
            newNewChecked[it] = comparableItemList.get(it).getChecked();
        }

        /*
         * This code makes all rendering of checkbox possible. IMPORTANT
         */
        for(int i = 0; i < newS1.length; i++){
            String animalName = newS1[i];
            for(int j = 0; j < originalAnimals.size(); j++){
                if(originalAnimals.get(j).getZooName().equals(animalName)){
                    int animalId = originalAnimals.get(j).getId();
                    String statusString = checkmarkData.substring(animalId, animalId + 1);
                    if(statusString.equals("x")){
                        newNewChecked[i] = View.VISIBLE;
                    }
                }
            }
            for(int j = 0; j < originalStructures.size(); j++){
                if(originalStructures.get(j).getStructureName().equals(animalName)){
                    int structId = originalStructures.get(j).getId() + originalAnimals.size();
                    String statusString = checkmarkData.substring(structId, structId + 1);
                    if(statusString.equals("x")){
                        newNewChecked[i] = View.VISIBLE;
                    }
                }
            }
            for(int j = 0; j < originalAnimalContainers.size(); j++){
                if(originalAnimalContainers.get(j).getContainerName().equals(animalName)){
                    int structId = originalAnimalContainers.get(j).getId()
                            + originalAnimals.size() + originalStructures.size();
                    String statusString = checkmarkData.substring(structId, structId + 1);
                    if(statusString.equals("x")){
                        newNewChecked[i] = View.VISIBLE;
                    }
                }
            }
        }
        mAdapter = new OutdoorRecycleAdapter(rootView.getContext(), this, userModel, newS1, newS2, newS3, newImages, newNewChecked,10);
        mAdapter.setMyAppData(userModel);
        recyclerView.setLayoutManager(myLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, sensorMag, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
        sortListByLocation(userModel.getCurrentPhoneLocation());

    }

    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        sortListByLocation(userModel.getCurrentPhoneLocation());

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;
        float myTestFloat = 0f;
        synchronized(this){
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mGravity = event.values;
            }
            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                mGeomagnetic = event.values;
            }
            float R[] = new float[9];
            float I[] = new float[9];
            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
                    // orientation contains azimut, pitch and roll
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    azimuth = orientation[0];
                    userModel.setAzimuth(azimuth);
                    userModel.getCurrentLocation().setValue(Math.toDegrees(orientation[0]) + "");
            }
                    Location workingLoc = userModel.getCurrentPhoneLocation();
                    if(workingLoc.distanceTo(lastLocationWhenItemsSorted) > 10){
                        retainPosition = myLinearLayoutManager.findFirstVisibleItemPosition();
                        sortListByLocation(userModel.getCurrentPhoneLocation());
                        myLinearLayoutManager.scrollToPosition(retainPosition);
                        lastLocationWhenItemsSorted = workingLoc;
                    }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public boolean getChecked(int position){
        String spot = checkmarkData.substring(position, position + 1);
        boolean ret = false;
        if(spot.equals("x")){
            ret = true;
        }
        return ret;
    }

    public String setPoint(int point, String character, String toAddTo){
        String ret = toAddTo.substring(0, point) + character + toAddTo.substring(point);
        return ret;
    }

    /**
     * Adds whether or not items are checked to all 3 data structures, since
     * such information must come from SharedPreferences rather than XML
     */
    public void addChecksToAnimals(){
        mPreferences = rootView.getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        checkmarkData = mPreferences.getString(CHECKMARK_KEY, checkmarkData);
        int loopMax = originalAnimals.size() + originalStructures.size() + originalAnimalContainers.size();
        for (int iter = 0; iter < loopMax ; iter++){
            if(iter < originalAnimals.size()){
                int spotDisplacement = 2;
                int id = originalAnimals.get(iter).getId();
                originalAnimals.get(iter).setChecked(getChecked(id + spotDisplacement));
            }
            if(iter >= originalAnimals.size() && iter < originalAnimals.size() + originalStructures.size()){
                int spotDisplacement = originalAnimals.size();
                int id = originalStructures.get(iter - spotDisplacement).getId();
                originalStructures.get(iter - spotDisplacement).setChecked(getChecked(id + spotDisplacement));
            }
            if(iter >= originalAnimals.size() + originalStructures.size() ){
                int spotDisplacement = originalAnimals.size() + originalStructures.size();
                int id = originalAnimalContainers.get(iter - spotDisplacement).getId();
                originalAnimalContainers.get(iter - spotDisplacement).setChecked(getChecked(id + spotDisplacement));
            }
        }

        //        int counterMax = userModel.getAnimals().size() +
//                userModel.getStructures().size() +
//                userModel.getAnimalContainerStructures().size();
//        int counterHalf = userModel.getAnimals().size() +
//                userModel.getStructures().size();
//        int counterNone = userModel.getAnimals().size();
//        int counter = 0;
//        while(counter < counterMax){
//            if(counter < counterNone){
//                userModel.getAnimals().get(counter).setChecked(getChecked(counter));
//            } else if(counter < counterHalf){
//                int tempPos = counter - counterNone;
//                userModel.getStructures().get(tempPos).setChecked(getChecked(counter));
//
//            } else {
//                int tempPos = counter - counterHalf;
//                userModel.getAnimalContainerStructures().get(tempPos).setChecked(getChecked(counter));
//            }
//            counter++;
//        }

    }

    /*
     * This method works correctly. String is edited and saved correctly for animals. It is read wrong.
     */
    public void setAnimalCheck(int animalId){
        String animalName = originalAnimals.get(animalId).getZooName();
        int animalSpot = originalAnimals.get(animalId).getId();
        String workingS = checkmarkData;
        String workingS2 = workingS.substring(0, animalSpot) + "x" + workingS.substring(animalSpot + 1, workingS.length());
        checkmarkData = workingS2;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(CHECKMARK_KEY, workingS2);
        preferencesEditor.apply();
    }
    public void setStructureCheck(int structId){
        String structName = originalStructures.get(structId -1).getStructureName();
        int structSpot = originalStructures.get(structId-1).getId() + originalAnimals.size();
        String workingS = checkmarkData;
        String workingS2 = workingS.substring(0, structSpot) + "x" + workingS.substring(structSpot + 1, workingS.length());
        checkmarkData = workingS2;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(CHECKMARK_KEY, workingS2);
        preferencesEditor.apply();
    }
    public void setAnimalContainerCheck(int animalContainerId){
        String structName = originalAnimalContainers.get(animalContainerId - 2).getContainerName();
        Log.i("TOMDEBUG", "This is containerID :" + structName);
        int structSpot = originalAnimalContainers.get(animalContainerId - 2).getId()
                + originalAnimals.size() + originalStructures.size();
        String workingS = checkmarkData;
        String workingS2 = workingS.substring(0, structSpot) + "x" + workingS.substring(structSpot + 1, workingS.length());
        checkmarkData = workingS2;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(CHECKMARK_KEY, workingS2);
        preferencesEditor.apply();
    }
}