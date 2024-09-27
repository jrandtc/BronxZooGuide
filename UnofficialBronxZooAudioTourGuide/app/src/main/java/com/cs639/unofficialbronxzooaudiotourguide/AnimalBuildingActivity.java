package com.cs639.unofficialbronxzooaudiotourguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AnimalBuildingActivity extends AppCompatActivity {
    String s1[], s2[];
    int i1[];
    private String filter;
    private final String CHECKMARK_BUILDING_KEY = "checkmarksinside";
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.cs639.unofficialbronxzooaudiotourguide";
    int visible[];
    private String checkmarkStr;
    XMLDataGetter allData;
    protected BuildingRecycleAdapter mAdapter;
    private LinearLayoutManager myLinearLayoutManager;
    private TextView txtInfoText;
    private int myStructureId;
    private RecyclerView recyclerView;
    private ArrayList<Animal> animalsWorking;
    int images[] =
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Animal> animalsListed = new ArrayList<Animal>();
        setContentView(R.layout.activity_animal_building);
        txtInfoText = findViewById(R.id.txtBuildingInfo);
        allData = new XMLDataGetter(this);
        ArrayList<AnimalContainerStructure> parentStructures = allData.getAnimalContainerStructures();
        ArrayList<Integer> imagesOfItems = new ArrayList<Integer>();
        Intent myIntent = this.getIntent();
        myStructureId = myIntent.getIntExtra("parentstructure", 0);
        Log.i("debugdebug","Structure Id is " + myStructureId);
        filter = myIntent.getStringExtra("filter");
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        checkmarkStr = mPreferences.getString(CHECKMARK_BUILDING_KEY, "uuuuuuuuuuuuuuuuu" +
                        "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
                        "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");

        recyclerView = findViewById(R.id.recBuildingRecyclerView);
        String parentStructureName = allData.getAnimalContainerStructures().get(myStructureId).getContainerName();
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Building: " + parentStructureName);
        mActionBar.setDisplayShowTitleEnabled(true);
        String addToTop = "";
        if(!filter.equals("")){
            addToTop += "filtering results by '" + filter + "'";
        }
        txtInfoText.setText(addToTop);
        animalsWorking = allData.getAnimals();
        Log.i("debugdebug", "printing animals");
        for(int iter = 0; iter < animalsWorking.size(); iter++) {
            Log.i("debugdebug", "animal: " + animalsWorking.get(iter).getZooName());
            if (animalsWorking.get(iter).getParentStructure() == myStructureId + 2) {
                if (filter.equals("") || animalsWorking.get(iter).matchesFilter(filter)) {
                    animalsListed.add(animalsWorking.get(iter));
                    imagesOfItems.add(images[iter]);
                }
            }
        }
        int[] imagesRecycleData = new int[imagesOfItems.size()];
        String[] recycleData = new String[animalsListed.size()];
        String[] recycleData2 = new String[animalsListed.size()];
        for (int iter = 0; iter < animalsListed.size(); iter++){
            recycleData[iter] = animalsListed.get(iter).getZooName();
            recycleData2[iter] = animalsListed.get(iter).getBinomialNomenclature();
            imagesRecycleData[iter] = imagesOfItems.get(iter);
        }
        int[] newNewChecked = new int[recycleData.length];
        for(int it = 0; it < newNewChecked.length; it++){
            newNewChecked[it] = View.INVISIBLE;
        }
        /*
         * This code makes all rendering of checkbox possible. IMPORTANT
         */
        for(int x = 0; x < recycleData.length; x++) {
            String animalName = recycleData[x];
            for (int j = 0; j < animalsListed.size(); j++) {
                if (animalsListed.get(j).getZooName().equals(animalName)) {
                    int animalId = animalsListed.get(j).getId();
                    String statusString = checkmarkStr.substring(animalId, animalId + 1);
                    if (statusString.equals("x")) {
                        newNewChecked[x] = View.VISIBLE;
                    }
                }
            }
        }
        Log.i("TOMDEBUG","SIZES ARE " + recycleData.length + " " + recycleData2.length + " " + imagesRecycleData.length);
        mAdapter = new BuildingRecycleAdapter(this, recycleData, recycleData2, imagesRecycleData, newNewChecked, this);
        s1 = recycleData;
        s2 = recycleData2;
        i1 = imagesRecycleData;
        myLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }

    public void listClickedOn(int animalNumber) {
        int animalToLaunch = animalNumber;
        String animalName = s1[animalNumber];
        for(int i = 0; i < animalsWorking.size(); i++){
            if(animalsWorking.get(i).getZooName().equals(animalName)){
                animalToLaunch = i;
            }

        }
        Intent intent = new Intent(this, AnimalActivity.class);
        setAnimalCheck(animalNumber);
        intent.putExtra("animalnumber", animalToLaunch);
        startActivity(intent);
    }

    /*
     * This method works correctly. String is edited and saved correctly for animals. It is read wrong.
     */
    public void setAnimalCheck(int clickedOnNumber){
        Log.i("TOMDEBUG", "Before: " + checkmarkStr);
        String animalName = s1[clickedOnNumber];
        ArrayList<Animal> animalList = allData.getAnimals();
        int animalSpot = 0;
        for(int i = 0; i < animalList.size(); i++){
            if(animalName.equals(animalList.get(i).getZooName())){
                animalSpot = i +1;
            }
        }
        String workingS = checkmarkStr;
        String workingS2 = workingS.substring(0, animalSpot) + "x" + workingS.substring(animalSpot + 1, workingS.length());
        checkmarkStr = workingS2;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(CHECKMARK_BUILDING_KEY, workingS2);
        preferencesEditor.apply();
        Log.i("TOMDEBUG", "After: " +  animalName + " " + checkmarkStr);

    }

    @Override
    protected void onRestart() {
        ArrayList<Animal> animalsListed = allData.getAnimals();
        int[] newNewChecked = new int[s1.length];
        for(int it = 0; it < newNewChecked.length; it++){
            newNewChecked[it] = View.INVISIBLE;
        }
        /*
         * This code makes all rendering of checkbox possible. IMPORTANT
         */
        for(int x = 0; x < s1.length; x++) {
            String animalName = s1[x];
            for (int j = 0; j < animalsListed.size(); j++) {
                if (animalsListed.get(j).getZooName().equals(animalName)) {
                    int animalId = animalsListed.get(j).getId();
                    String statusString = checkmarkStr.substring(animalId, animalId + 1);
                    if (statusString.equals("x")) {
                        newNewChecked[x] = View.VISIBLE;
                    }
                }
            }
        }
        mAdapter = new BuildingRecycleAdapter(this, s1, s2, i1, newNewChecked, this);
        myLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        super.onRestart();
    }

}