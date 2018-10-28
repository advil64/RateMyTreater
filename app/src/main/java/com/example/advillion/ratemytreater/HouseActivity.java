package com.example.advillion.ratemytreater;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HouseActivity extends LoginActivity {


    public static String[] info;
    double lat = LoginActivity.latitude;
    double longi = LoginActivity.longitude;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static double average = 4;
    public static int counter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);


        String baseURL = "https://api.opencagedata.com/geocode/v1/json?q=" + lat + "+" + longi + "&key=a7e43cb332c14842869bb456994ee8a4";

        try{

            Data x = new Data();
            info = x.execute(baseURL).get().split("~");

        }
        catch(Exception e){
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();


        for (int i = 0; i < info.length; i++) {

            db.collection(info[i])
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                average = 0;

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    average += Integer.parseInt(document.get("Rating").toString());

                                }
                            }
                        }
                    });


            String picURL = "https://cdn.vox-cdn.com/thumbor/DccFJD7KjfhPCyXZqOugjnAQXdc=/0x0:1920x1080/1200x800/filters:focal(940x240:1246x546)/cdn.vox-cdn.com/uploads/chorus_image/image/55649903/snoopy_pop_art_1920.0.png";
            int rating = (int) (HouseActivity.average/((double) HouseActivity.counter) + 0.5);
            String heading = "";

            for (int j = 0; j < rating; j++)
                heading += (char) 9733;
            for (int j = 0; j < 5 - rating; j++)
                    heading += (char) 9734;

            String candy = info[i];

            ListItem listItem = new ListItem(
                    picURL,
                    heading,
                    candy
            );

            listItems.add(listItem);

            HouseActivity.average = 3.0;
            HouseActivity.counter = 1;
        }

        adapter = new Adapter(listItems, this);

        recyclerView.setAdapter(adapter);

    }





}
