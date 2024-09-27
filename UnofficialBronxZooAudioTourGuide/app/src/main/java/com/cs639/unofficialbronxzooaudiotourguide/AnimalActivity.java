package com.cs639.unofficialbronxzooaudiotourguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Starting from scratch
 *
 * @author Tom
 */
public class AnimalActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.cs639.unofficialbronxzooaudiotourguide";
    private Animal myAnimal;
    private String wikipediaurl = "";
    private String eolurl = "";
    private String redlisturl = "";
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_screen);
        Intent myIntent = this.getIntent();
        XMLDataGetter allData = new XMLDataGetter(this);
        int myAnimalId = myIntent.getIntExtra("animalnumber", 0);
        String filter = myIntent.getStringExtra("filter");
        ArrayList<Animal> myAnimals = allData.getAnimals();
        String animalName = allData.getAnimals().get(myAnimalId).getZooName();
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Animal: " + animalName);
        mActionBar.setDisplayShowTitleEnabled(true);

        //Get different items in view
        TextView txtFamily = findViewById(R.id.txtFamily);
        TextView txtClass = findViewById(R.id.txtClass);
        TextView txtBinom = findViewById(R.id.txtBinomialNomenclature);
        TextView txtIUCNConservationStatus = findViewById(R.id.txtIUCNConservationStatus);
        TextView txtBodyText = findViewById(R.id.txtBodyText);
        ImageView animalImage = findViewById(R.id.imgAnimalPic);
        Button btnWikipedia = findViewById(R.id.btnWikipedia);
        Button btnEol = findViewById(R.id.btnEol);
        Button btnRedlist = findViewById(R.id.btnRedlist);

        for(int x = 0; x < myAnimals.size();x++){
            if ( myAnimals.get(x).getZooName().equals(animalName)){
                txtBinom.setText("Binomial Nomenclature: " + myAnimals.get(x).getBinomialNomenclature());
                txtFamily.setText("Family: " + myAnimals.get(x).getFamily());
                txtClass.setText("Class: " + myAnimals.get(x).getAnimalClass());
                txtIUCNConservationStatus.setText("Converation Status: " + myAnimals.get(x).getConservationStatus());
                String bodyText = "";
                ArrayList<String> body = myAnimals.get(x).getDescription();
                String toPost = "Location: " + myAnimals.get(x).getNaturalLocation()
                        .replace("\n", "").replace("\r", "")
                        .replaceAll("\\s+", " ") + "\n\n";
                for(int y = 0; y < body.size(); y++){
                    bodyText += body.get(y);
                    toPost += bodyText.replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                    toPost += "\n\n";
                }
                txtBodyText.setText(toPost);
                wikipediaurl = myAnimals.get(x).getWikiLink();
                eolurl = myAnimals.get(x).getEolLink();
                redlisturl = myAnimals.get(x).getRedlistLink();

            }
            btnWikipedia.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(wikipediaurl));
                    startActivity(i);
                }
            });
            btnEol.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("debugdebug", "eol clicked " + eolurl);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(eolurl));
                    startActivity(i);
                }
            });
            btnRedlist.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(redlisturl));
                    startActivity(i);
                }
            });
            }

        animalImage.setImageResource(animalimages[myAnimalId]);
    }
}