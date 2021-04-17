package com.cs639.unofficialbronxzooaudiotourguide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

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
    private FusedLocationProviderClient fusedLocationClient;
    private String filter;
    private String newS1[];

    Button btnSearch;
    Button btnClear;
    TextView txtSearch;
    int images[];
    int animalimages[] =
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
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        rootView = inflater.inflate(R.layout.fragment_first, container, false);
        filter = "";
        mSensorManager = (SensorManager)rootView.getContext().getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(rootView.getContext());
        btnClear = rootView.findViewById(R.id.btnClear);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        txtSearch = rootView.findViewById(R.id.editTextSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = txtSearch.getText().toString();
                getCurrentLocation();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = "";
                txtSearch.setText("");
                getCurrentLocation();            }
        });
        // Inflate the layout for this fragment
        recyclerView = rootView.findViewById(R.id.OutdoorRecyclerView);
        userModel = new ViewModelProvider(requireActivity()).get(AllAppData.class);
        int width = userModel.getScreenSize();
        userModel.setCompassList(this);

        String[][] toSend = buildItemList(userModel.getAnimals(), userModel.getStructures(), userModel.getAnimalContainerStructures());
        s1 = toSend[0];
        s2 = toSend[1];
        s3 = toSend[2];
        s4 = toSend[3];
        newS1 = s1;
        mAdapter = new OutdoorRecycleAdapter(rootView.getContext(), s1, s2, s3, s4, images, 10);
        mAdapter.setMyAppData(userModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(mAdapter);
        getCurrentLocation();

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
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getParentStructure() == 0) {
                namesOfItems.add(animals.get(i).getZooName());
                BinomOrOtherItems.add(animals.get(i).getBinomialNomenclature());
                imagesOfItems.add(animalimages[i]);
                DistanceOfItems.add(animals.get(i).getViewingPoints().get(0));
                locationOfItems.add(animals.get(i).getViewingPoints().get(0).getLatitude()
                        + " " + animals.get(i).getViewingPoints().get(0).getLongitude());

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
                oldString += animals.get(i).getSearchString();
                animalContainerStructures.get(parentStructureId).setSearchString(oldString);
            }
        }
        for (int i = 0; i < animalContainerStructures.size(); i++) {
            namesOfItems.add(animalContainerStructures.get(i).getContainerName());
            BinomOrOtherItems.add("Structure: Tap for Animals Inside");
            DistanceOfItems.add(animalContainerStructures.get(i).getViewingPoints());
            locationOfItems.add(animalContainerStructures.get(i).getViewingPoints().getLatitude()
                    + " " + animalContainerStructures.get(i).getViewingPoints().getLongitude());
            imagesOfItems.add(animalStructureImages[i]);
        }
        for (int i = 0; i < structures.size(); i++) {
            namesOfItems.add(structures.get(i).getStructureName());
            locationOfItems.add(structures.get(i).getViewingPoints().get(0).getLatitude()
                    + " " + structures.get(i).getViewingPoints().get(0).getLongitude());
            BinomOrOtherItems.add("Structure: Tap for History");
            DistanceOfItems.add(structures.get(i).getViewingPoints().get(0));
            imagesOfItems.add(structureImages[i]);
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


        //Figures out distance between standing person and item
        String s4[] = new String[DistanceOfItems.size()];

        for (int i = 0; i < DistanceOfItems.size(); i++) {
            Location itemLocation = DistanceOfItems.get(i);
            String toAdd = "empty";
            if(itemLocation != null && phoneLocation != null) {
                toAdd = phoneLocation.distanceTo(itemLocation) + "";
            }
            if(phoneLocation != null){
                toAdd = phoneLocation.getLatitude() + " " + phoneLocation.getLongitude();
            }

            s4[i] = toAdd;
        }

        //Using private var instead of return since different type. Not good code.
        images = new int[imagesOfItems.size()];
        for (int i = 0; i < imagesOfItems.size(); i++) {
            images[i] = imagesOfItems.get(i);
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
        int idOfItem = 0;
        for(int iter = 0; iter < userModel.getAnimals().size(); iter++){
            if(userModel.getAnimals().get(iter).getZooName().equals(nameOfItemClicked)){
                isAnimal = true;
                idOfItem = iter;
            }
        }
        for(int iter = 0; iter < userModel.getStructures().size(); iter++) {
            if(userModel.getStructures().get(iter).getStructureName().equals(nameOfItemClicked)){
                isStructure = true;
                idOfItem = iter;
            }
        }
        for(int iter = 0; iter < userModel.getAnimalContainerStructures().size(); iter++) {
            if(userModel.getAnimalContainerStructures().get(iter).getContainerName().equals(nameOfItemClicked)){
                isAnimalContainingStructure = true;
                idOfItem = iter;
            }
        }
            if(isAnimal) {
            Intent intent = new Intent(rootView.getContext(), AnimalActivity.class);
            intent.putExtra("animalnumber", idOfItem);
            startActivity(intent);
        }
        if(isStructure) {
            Intent intent = new Intent(rootView.getContext(), StructureActivity.class);
            intent.putExtra("structurenumber", idOfItem);
            startActivity(intent);
        }
        if(isAnimalContainingStructure) {
            Intent intent = new Intent(rootView.getContext(), AnimalContainerActivity.class);
            intent.putExtra("parentstructure", idOfItem);
            intent.putExtra("filter", filter);

            Log.i("TOMDEBUG", "Launching animal");
            startActivity(intent);
        }
//        Intent intent = new Intent(rootView.getContext(), AnimalContainerActivity.class);
//        intent.putExtra("animalstructurenumber", animalstructurenumber);
//        intent.putExtra("filter", filter);
//        startActivity(intent);
    }



    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        final Location[] loc = new Location[1];
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(rootView.getContext());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            loc[0] = location;
                            Log.i("TOMDEBUG", "Location is " + location.toString());
                            Toast.makeText(rootView.getContext().getApplicationContext(),"Location is " + location.toString(),Toast.LENGTH_SHORT).show();
                            sortListByLocation(location);


                        }
                    }
                });
        phoneLocation = loc[0];
    }
    public void sortListByLocation(Location location){
        Log.i("TOMDEBUG", "Location is " + location.getLongitude() + " " + location.getLatitude());
        int size = 0;
        String[][] ret;
        ArrayList<Animal> animals = userModel.getAnimals();
        ArrayList<AnimalContainerStructure> animalContainerStructures = userModel.getAnimalContainerStructures();
        ArrayList<Structure> structures = userModel.getStructures();
        ArrayList<String> namesOfItems = new ArrayList<String>();
        ArrayList<String> BinomOrOtherItems = new ArrayList<String>();
        ArrayList<String> locationOfItems = new ArrayList<String>();
        ArrayList<Location> DistanceOfItems = new ArrayList<Location>();
        ArrayList<Integer> imagesOfItems = new ArrayList<Integer>();
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getParentStructure() == 0) {
                if( filter.equals("") || animals.get(i).matchesFilter(filter) ) {
                    namesOfItems.add(animals.get(i).getZooName());
                    BinomOrOtherItems.add(animals.get(i).getBinomialNomenclature());
                    imagesOfItems.add(animalimages[i]);
                    DistanceOfItems.add(animals.get(i).getViewingPoints().get(0));
                    locationOfItems.add(animals.get(i).getViewingPoints().get(0).getLatitude()
                            + " " + animals.get(i).getViewingPoints().get(0).getLongitude());
                }
            }
        }
        for (int i = 0; i < animalContainerStructures.size(); i++) {
            if( filter.equals("") || animalContainerStructures.get(i).matchesFilter(filter) ) {
                namesOfItems.add(animalContainerStructures.get(i).getContainerName());
                BinomOrOtherItems.add("Structure: Tap for Animals Inside");
                DistanceOfItems.add(animalContainerStructures.get(i).getViewingPoints());
                locationOfItems.add(animalContainerStructures.get(i).getViewingPoints().getLatitude()
                        + " " + animalContainerStructures.get(i).getViewingPoints().getLongitude());
                imagesOfItems.add(animalStructureImages[i]);
            }
        }
        for (int i = 0; i < structures.size(); i++) {
            if( filter.equals("") || structures.get(i).matchesFilter(filter) ) {
                namesOfItems.add(structures.get(i).getStructureName());
                locationOfItems.add(structures.get(i).getViewingPoints().get(0).getLatitude()
                        + " " + structures.get(i).getViewingPoints().get(0).getLongitude());
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

        //Using private var instead of return since different type. Not good code.
        images = new int[imagesOfItems.size()];
        for (int i = 0; i < imagesOfItems.size(); i++) {
            images[i] = imagesOfItems.get(i);
        }

        //Puts everything in a a sortable List
        List<ComparableDataItem> comparableItemList = new ArrayList<ComparableDataItem>();
        for(int it = 0; it < s1.length; it++){
            ComparableDataItem myItem = new
                    ComparableDataItem(s1[it], s2[it], s3[it], images[it], myD[it], it );
            comparableItemList.add(myItem);
        }
        //Sort the list
        Collections.sort(comparableItemList);

        //put everything back in sorted order
        newS1 = new String[s1.length];
        String newS2[] = new String[s1.length];
        String newS3[] = new String[s1.length];
        int newImages[] = new int[s1.length];
        String newS4[] = new String[s1.length];
        for(int it = 0; it < comparableItemList.size(); it++){
            newS1[it] = comparableItemList.get(it).getS1();
            newS2[it] = comparableItemList.get(it).getS2();
            newS3[it] = comparableItemList.get(it).getS3();
            newS4[it] = comparableItemList.get(it).getD() + "";
            newImages[it] = comparableItemList.get(it).getI();
        }

        mAdapter = new OutdoorRecycleAdapter(rootView.getContext(), newS1, newS2, newS3, newS4, newImages, 10);
        mAdapter.setMyAppData(userModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(mAdapter);
    }
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}