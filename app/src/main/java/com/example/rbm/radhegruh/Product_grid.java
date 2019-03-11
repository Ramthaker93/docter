package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.security.Key;
import java.util.HashMap;

public class Product_grid extends AppCompatActivity {

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
    String subId;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_grid);

        //session .........

        session1 = new SessionManager(getApplicationContext());
        //  session1.checkLogin();
        HashMap<String, String> user = session1.getUserDetails();

        // name
        name = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);

        //session .... end...


        Intent it = getIntent();
        Bundle b = it.getExtras();
        subId = (String) b.get("sub_id");
        productgridfetch();
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
    //   product fetch grid...............................
    private void productgridfetch() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
           loading = ProgressDialog.show(Product_grid.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/product.php?sub_id="+subId,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONoffergrid(response);
                        loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Product_grid.this, error.getMessage(), Toast.LENGTH_LONG).show();
                           loading.dismiss();
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
            p_id=new String[users.length()];
            nameoffergrid = new String[users.length()];
            des=new String[users.length()];
            imageoffergrid = new String[users.length()];
            selling_price=new String[users.length()];
            price=new String[users.length()];
            discou=new String[users.length()];
            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);

                p_id[i]=jo.getString(KEY_id);
                nameoffergrid[i] = jo.getString(KEY_TITLE);
                des[i]=jo.getString(KEY_description);
                imageoffergrid[i] = jo.getString(KEY_IMAGE);
                selling_price[i] = jo.getString(KEY_SELLING_PRICE);
                price[i]=jo.getString(KEY_PRICE);
                discou[i] = jo.getString(KEY_discount);
            }
            //////.........grid view fetch...................
            Product_grid_adapter adapter = new Product_grid_adapter(Product_grid.this, nameoffergrid, imageoffergrid,selling_price, price,discou,p_id,des);
            GridView grid = (GridView) findViewById(R.id.gridview);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(Product_grid.this, "You Clicked at " +nameoffergrid[+ position], Toast.LENGTH_SHORT).show();

                }
            });



//    /////////end grid view .....................
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // product grid .....................................end................///////////
}
