package com.example.rbm.radhegruh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class product_list extends AppCompatActivity {
    Button student;
    Array array;
    Spinner spinner;
    String s=null;
    ProgressDialog loading;
    //String pric="1000000",dis="100";

     String JSON_URL = "http://sharmacommunications.com/medical/product.php?sub_id=";
String sub_id;
    //listview object
    ListView listView;
    AlertDialog.Builder builder;
    //the hero list where we will store all the hero objects after parsing json
    List<Product_list_geter> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list__main);
//        student = (Button) findViewById(R.id.add_student);
        listView = (ListView) findViewById(R.id.listview);
        heroList = new ArrayList<>();
//        Button add_student=(Button) findViewById(R.id.add_student);
//         spinner = (Spinner) findViewById(R.id.spinner);
//        add_student.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it=new Intent(product_list.this,Add_subject_t.class);
//                startActivity(it);
//            }
//        });
        Intent it = getIntent();
        Bundle b = it.getExtras();
        sub_id = (String) b.get("sub_id");
        String name = (String) b.get("name");
        TextView tv=(TextView)findViewById(R.id.name);
        tv.setText(name);
        student();


        builder = new AlertDialog.Builder(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


//    }

    // Show how to add custom view in android alert dialog.

    private void student() {

            //getting the progressbar

            loading = ProgressDialog.show(product_list.this, "Please wait...", "Fetching...", false, false);

            //making the progressbar visible


            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL+sub_id,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion


                            try {

                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONArray heroArray = obj.getJSONArray("result");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < heroArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject heroObject = heroArray.getJSONObject(i);

                                    //creating a hero object and giving them the values from json object
                                    Product_list_geter hero = new Product_list_geter(heroObject.getString("product_id"), heroObject.getString("name"), heroObject.getString("description"),
                                            heroObject.getString("image"),heroObject.getString("price"),heroObject.getString("selling_price"),heroObject.getString("discount"));
                                    //adding the hero to herolist
                                    heroList.add(hero);

                                    loading.dismiss();
                                }

                                //creating custom adapter object
                                Product_list_ViewAdapter adapter = new Product_list_ViewAdapter(heroList, getApplicationContext());

                                //adding the adapter to listview
                                listView.setAdapter(adapter);
                                loading.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                loading.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "check internet", Toast.LENGTH_SHORT).show();
                            loading.dismiss();


                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }


    }
