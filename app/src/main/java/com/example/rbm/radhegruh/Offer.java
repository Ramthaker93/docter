package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Offer extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    //type category


    //Login lo;
    ///  private GoogleApiClient mGoogleApiClient;
    public static final String KEY_TITLE = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_SELLING_PRICE = "selling_price";
    public static final String KEY_PRICE = "price";
    public static final String KEY_description = "description";
    public static final String KEY_discount = "discount";
    public static final String KEY_id = "product_id";
    public static final String KEY_type_cat_id = "type_cat_id";
    public static final String KEY_main_id = "main_id";
    public static final String KEY_sub_id = "sub_id";
    public static final String JSON_ARRAY = "result";

    private JSONArray users = null;

    public static String[] title = null;
    public static String[] image = null;
    public static String[] selling_price = null;
    public static String[] p_id = null;
    public static String[] des = null;
    public static String[] discou = null;
    public static String[] type_cat_id = null;
    public static String[] main_id = null;
    public static String[] sub_id = null;
    SessionManager session1;
    String name = "SignUp", email;

    public static String[] name_type_cat = null;
    public static String[] image_type_cat = null;
    public static String[] name_sub_cat = null;
    public static String[] image_sub_cat = null;
    public static String[] namegrid = null;
    public static String[] imagegrid = null;

    public static String[] nameshopgrid = null;
    public static String[] imageshopgrid = null;

    public static String[] nameoffergrid = null;
    public static String[] imageoffergrid = null;
    public static String[] price = null;
    public static String[] offer_price = null;
    public static String[] subid = null;
    String subId;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        //session .........

        session1 = new SessionManager(getApplicationContext());
        //  session1.checkLogin();
        HashMap<String, String> user = session1.getUserDetails();

        // name
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);

        //session .... end...



        offergridfetch();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        Intent i=new Intent(Offer.this,Login.class);


        startActivity(i);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //do your stuff
            Intent i=new Intent(Offer.this,Home.class);

            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
    //   offer fetch grid...............................
    private void offergridfetch() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        //     loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/today_offer.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONoffergrid(response);
                        //  loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Offer.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        //    loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //   loading.dismiss();
    }

    private void showJSONoffergrid(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            nameoffergrid = new String[users.length()];
            imageoffergrid = new String[users.length()];
            offer_price=new String[users.length()];
            subid=new String[users.length()];
            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                nameoffergrid[i] = jo.getString(KEY_TITLE);

                imageoffergrid[i] = jo.getString(KEY_IMAGE);
                offer_price[i]=jo.getString(KEY_PRICE);
                subid[i]=jo.getString(KEY_sub_id);
            }
            //////.........grid view fetch...................
            offer_grid_adapter adapter = new offer_grid_adapter(Offer.this, nameoffergrid, imageoffergrid, offer_price);
            GridView grid = (GridView) findViewById(R.id.offergridview);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent it=new Intent(getApplicationContext(),Product_grid.class);
                    it.putExtra("sub_id",subid[+position]);
                    startActivity(it);
                    // Toast.makeText(Home.this, "You Clicked at " +nameoffergrid[+ position], Toast.LENGTH_SHORT).show();

                }
            });



//    /////////end grid view .....................
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // offer grid .....................................end................///////////
}
