package com.cs639.unofficialbronxzooaudiotourguide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Nullable;


/**
 * The Recycle Adapter for use with outside Animals
 *
 * @author Tom Cookson
 */
public class OutdoorRecycleAdapter extends RecyclerView.Adapter<OutdoorRecycleAdapter.MyViewHolder> {
    String data1[], data2[], data3[], data4[];
    int images[];
    Context context;
    CompassListFragment theParent;
    int screenWidth;



    AllAppData myAppData;
    View rowView;

    public  OutdoorRecycleAdapter(Context ct, CompassListFragment myDad, AllAppData theAppData, String[] s1, String[] s2, String[] s3, String[] s4, int[] myimages, int myWidth){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        images = myimages;
        screenWidth = myWidth;
        myAppData = theAppData;
        theParent = myDad;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.outside_animal_row, parent, false);
        rowView = view;

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtZooName.setText(data1[position]);
        holder.txtBiNom.setText(data2[position]);
        holder.txtLocation.setText(data3[position]);
        holder.txtDistance.setText(data4[position]);
        holder.imgAnimal.setImageResource(images[position]);
        holder.parentLayout.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                myAppData.compassViewClicked(position);
            }
        });
    }

    public void setImageSize(int newSize){
        screenWidth = newSize;
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView txtZooName, txtBiNom, txtDistance, txtLocation;
        ImageView imgAnimal;
        ConstraintLayout parentLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtZooName = itemView.findViewById(R.id.rowOutsideAnimalNameTxt);
            txtBiNom = itemView.findViewById(R.id.rowOutsideAnimalBiNomTxt);
            txtDistance = itemView.findViewById(R.id.rowOutsideDistanceTxt);
            txtLocation = itemView.findViewById(R.id.rowOutsideLocationTxt);
            imgAnimal = itemView.findViewById(R.id.rowOutsideAnimalImg);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            // Create the observer which updates the UI.
            final Observer<String> locationObserver = new Observer<String>() {
                @Override
                public void onChanged(@Nullable final String newLocation) {
                    // Update the UI, in this case, a TextView.
                    String itemName = txtZooName.getText().toString();
                    Location itemLocation = myAppData.getLocationOf(itemName);
                    Location phoneLocation = myAppData.getCurrentPhoneLocation();

                    if(phoneLocation != null && itemLocation != null){
                        double longDiff =  itemLocation.getLongitude() - phoneLocation.getLongitude();
                        double latDiff = itemLocation.getLatitude() - phoneLocation.getLatitude();
                        double currentAngle = Math.toDegrees(myAppData.getAzimuth());
                        double angle = Math.toDegrees(Math.atan2(longDiff, latDiff));
                        double finalAngle = currentAngle - angle;
                        if(finalAngle >= 180 ){
                            finalAngle = finalAngle - 360;
                        }
                        if(finalAngle <= -180 ){
                            finalAngle = finalAngle + 360;
                        }
                        String myAngle = ("");
                        int myAngle2 = (int) finalAngle;
                        txtDistance.setText(phoneLocation.distanceTo(itemLocation) + "");
                        txtLocation.setText((myAngle + " " + myAngle2));
                    }
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            myAppData.getCurrentLocation().observe(theParent, locationObserver);

        }
    }
    public AllAppData getMyAppData() {
        return myAppData;
    }

    public void setMyAppData(AllAppData myAppData) {
        this.myAppData = myAppData;
    }


}
