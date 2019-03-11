package com.example.rbm.radhegruh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String[] title1={ "Romania to Uk","Uk to Romania", "London to Romania","Romaina to London", "Romania to Europe","Europe to Romania", "Romania to Italy / Spain","Italy/Spain to Romaina", "Romania to Nordics","Nordics to Romaina", "Romania to Hungary","Hungary to Romania","Search Other Offer"};
    public static String[] image1={"http://rbmtec.com/GodAarti/uk/german.jpg", "http://rbmtec.com/GodAarti/uk/uk.jpg", "http://rbmtec.com/GodAarti/uk/spain.jpg", "http://rbmtec.com/GodAarti/uk/Copenhagen.jpg", "http://rbmtec.com/GodAarti/uk/Madrid.png", "http://rbmtec.com/GodAarti/uk/Budapest.jpg","http://rbmtec.com/image/more.png"};;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////.........grid view fetch...................
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<AndroidVersion> androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);


        adapter.setOnRecyclerViewItemClickListener(new DataAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(String text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

            }
        });

////..........end grid view...............end...

    }

    //..............grid view...........
    private ArrayList<AndroidVersion> prepareData(){

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<title1.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(title1[i]);
            androidVersion.setAndroid_image_url(image1[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }
    /////////end grid view .....................

}
