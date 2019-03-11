package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;

public class Cart_list extends AppCompatActivity {
    Button student;
    Array array;
    Spinner spinner;
SessionManager session;
    ProgressDialog loading;
String password,email;
    //the URL having the json data

    public static final String KEY_tot = "total";
    public static final String JSON_ARRAY = "result";

    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Cart_list_geter> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list__main);
//        student = (Button) findViewById(R.id.add_student);
        listView = (ListView) findViewById(R.id.listview);
        heroList = new ArrayList<>();
     /*  Button play=(Button) findViewById(R.id.total);
//         spinner = (Spinner) findViewById(R.id.spinner);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
//                Intent it=new Intent(Cart_list.this,Add_subject_t.class);
//                startActivity(it);
            }
        });*/
        session = new SessionManager(getApplicationContext());

        session.checkLogin();
// get user data from session
        HashMap<String, String> user = session.getUserDetails();
//
//
//        // name
        password = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        student();



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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //do your stuff
            Intent i=new Intent(Cart_list.this,Home.class);

            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
    private void student() {

            //getting the progressbar

            loading = ProgressDialog.show(Cart_list.this, "Please wait...", "Fetching...", false, false);

            //making the progressbar visible


            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Splash_Screen.baseurl+"medical/fetch_cart.php?email="+email,
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
                                    Cart_list_geter hero = new Cart_list_geter(heroObject.getString("product_id"), heroObject.getString("name"), heroObject.getString("description"),
                                            heroObject.getString("image"),heroObject.getString("price"),heroObject.getString("selling_price"),heroObject.getString("discount"),heroObject.getString("cart_id"),heroObject.getString("total"));
                                    //adding the hero to herolist
                                    heroList.add(hero);

                                    loading.dismiss();
                                }

                                //creating custom adapter object
                                Cart_list_ViewAdapter adapter = new Cart_list_ViewAdapter(heroList, getApplicationContext());

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
    private void getData() {
        try {
            loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
//            String url ="http://codingworld.online/rbm/fuelapi.php?type=" + Fuelname + "&city=" + City;//Alldataconnection.DATA_URL+City;
            String url =Splash_Screen.baseurl+"medical/total_amount.php?email="+email;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Intent it=new Intent(Profile.this,MainActivity.class);
//                            startActivity(it);
                            Toast.makeText(Cart_list.this, "checkyour connection", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch (Exception e){

            Toast.makeText(this, (CharSequence) e, Toast.LENGTH_SHORT).show();
        }
    }

    private void showJSON(String response) {

        String totle = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);

            totle = collegeData.getString(KEY_tot);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    }
