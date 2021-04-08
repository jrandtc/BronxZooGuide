package com.cs639.unofficialbronxzooaudiotourguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 *
 * This Fragment shows the all outside animals; it's the fragment that is loaded first when the
 * project is loaded, showing a list of outside animals and structures with a compass arrow
 * pointing towards it.
 *
 * @author Tom Cookson
 */
public class CompassListFragment extends Fragment {
    String s1[], s2[];
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
                    R.drawable.a96,
            R.drawable.s1,
            R.drawable.s2};
    RecyclerView recyclerView;
    protected OutdoorRecycleAdapter mAdapter;
    View rootView;
    AllAppData userModel;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        rootView = inflater.inflate(R.layout.fragment_first, container, false);
        // Inflate the layout for this fragment
        recyclerView = rootView.findViewById(R.id.OutdoorRecyclerView);
        userModel = new ViewModelProvider(requireActivity()).get(AllAppData.class);
        int width = userModel.getScreenSize();
        userModel.setCompassList(this);
        s1 = buildNameList(userModel.getAnimals(), userModel.getStructures());
        s2 = buildBinomList(userModel.getAnimals(), userModel.getStructures());
        mAdapter = new OutdoorRecycleAdapter(rootView.getContext(), s1, s2, images, 10);
        mAdapter.setMyAppData(userModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(mAdapter);


        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private String[] buildNameList(ArrayList<Animal> animals, ArrayList<Structure> structures){
        int size = animals.size() + structures.size();
        String[] ret = new String[size];
        for(int i = 0 ; i < animals.size(); i++){
            ret[i] = animals.get(i).getZooName();
        }
        for(int i = animals.size(); i < size; i++){
            int temp = 0;
            ret[i] = structures.get(temp).getStructureName();
            temp++;
        }
        return ret;
    }

    private String[] buildBinomList(ArrayList<Animal> animals, ArrayList<Structure> structures){
        int size = animals.size() + structures.size();
        String[] ret = new String[size];
        for(int i = 0 ; i < animals.size(); i++){
            ret[i] = animals.get(i).getBinomialNomenclature();
        }
        for(int i = animals.size(); i < size; i++){
            int temp = 0;
            ret[i] = "structure";
            temp++;
        }
        return ret;
    }

    public void launchAnimalActivity(int animalNumber){
        Intent intent = new Intent(rootView.getContext(), AnimalActivity.class);
        intent.putExtra("animalnumber", animalNumber);
        Log.i("TOMDEBUG", "Launching animal");
        startActivity(intent);
    }

    public void launchStructureActivity(int structureNumber){
        Intent intent = new Intent(rootView.getContext(), StructureActivity.class);
        Log.i("TOMDEBUG", "Launching structure");
        intent.putExtra("structurenumber", structureNumber);
        startActivity(intent);
    }
    public void launchAnimalsStructureActivity(int structureNumber){
        Intent intent = new Intent(rootView.getContext(), AnimalActivity.class);
        startActivity(intent);
    }
}