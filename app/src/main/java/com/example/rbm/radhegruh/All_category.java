package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class All_category extends AppCompatActivity {

    public static final String KEY_TITLE = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_main_id = "main_id";
    public static final String KEY_type_id = "type_cat_id";
    public static final String KEY_sub_cat_id = "sub_id";
    public static final String JSON_ARRAY = "result";

    private JSONArray users = null;

    ProgressDialog loading;
    LayoutInflater layoutInflater;
    LinearLayout linearLayout12;
    HorizontalScrollView horizontalScrollView12;
    //main cat.........
    public static String[] name_main_cat = null;
    public static String[] image_mian_cat = null;
    public static String[] id_main_cat = null;
    //main category...end......

    //type cat........
    ListView list;
    String typeurl="http://sharmacommunications.com/medical/type_cat_fetch.php?main_id=27";
    public static String[] name_type_cat = null;
    public static String[] image_type_cat = null;
    public static String[] id_type_cat = null;

    //type cat ..end...

    //type cat........

    String suburl="http://sharmacommunications.com/medical/sub_cat_fetch.php?type_cat_id=71";
    public static String[] name_sub_cat = null;
    public static String[] image_sub_cat = null;
    public static String[] id_sub_cat = null;

    //type cat ..end...

    String main_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        Intent it = getIntent();
        Bundle b = it.getExtras();
        main_id = (String) b.get("id");

//        // horizontal  .........................................///////////////////////
//        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        horizontalScrollView12 = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view1);
//        linearLayout12 = (LinearLayout) findViewById(R.id.img_gallery1);
       // maincategory();
        typecategory();
      //  subcategory();


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


    // image horizontal.........................................///////////////////////

    //  Main category fetch........................................///////////////////////

//    private void maincategory() {
//        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
//        //     loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);
//
//        StringRequest stringRequest = new StringRequest("https://sharmacommunications.com/medical/category.php",
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        showJSONmaincat(response);
//                        //  loading.dismiss();
//
//
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(All_category.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                        //    loading.dismiss();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//        //   loading.dismiss();
//    }
//
//    private void showJSONmaincat(String json) {
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(json);
//            users = jsonObject.getJSONArray(JSON_ARRAY);
//
//            id_main_cat = new String[users.length()];
//            name_main_cat = new String[users.length()];
//            image_mian_cat = new String[users.length()];
//
//
//            for (int i = 0; i < users.length(); i++) {
//                JSONObject jo = users.getJSONObject(i);
//
//
//                id_main_cat[i] = jo.getString(KEY_main_id);
//                name_main_cat[i] = jo.getString(KEY_TITLE);
//                image_mian_cat[i] = jo.getString(KEY_IMAGE);
//
//
//
//                View view = layoutInflater.inflate(R.layout.category_ican, linearLayout12, false);
//                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout_cat);
//                ImageView imageView = (ImageView) view.findViewById(R.id.img);
//                final TextView item_name = (TextView) view.findViewById(R.id.tv);
//
//
//
//                Picasso.with(this)
//                        .load(image_mian_cat[i])
////                        .placeholder(R.drawable.ic_launcher_background)
////                        .error(R.drawable.ic_launcher_foreground)
////                        .resize(400,400)
//                        .into(imageView);
//               item_name.setText(name_main_cat[i]);
//                Picasso.with(All_category.this).load(image_mian_cat[0]).into(imageView);
//                if (i == (image_mian_cat.length) - 1) {
//                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
//                }
//                linearLayout12.addView(view);
//                // Toast.makeText(Home.this, title[i], Toast.LENGTH_SHORT).show();
//                 final String main_cat_id=id_main_cat[i];
//                final String imageurl=image_mian_cat[i];
//                typecategory();
//                typeurl="http://sharmacommunications.com/medical/type_cat_fetch.php?main_id="+id_main_cat[0];
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                       // Toast.makeText(All_category.this,main_cat_id , Toast.LENGTH_SHORT).show();
//                        typecategory();
//                         typeurl="http://sharmacommunications.com/medical/type_cat_fetch.php?main_id="+main_cat_id;
//                    }
//
//                });
//
////                scroll_item_layout.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        if()
////                        Intent it=new Intent(getApplicationContext(),Medical_list.class);
////
////                        startActivity(it);
////                        startActivity(it);
////
////                    }
////                });
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    // image horizontal.....................end....................///////////////////////

    //  typecategory category fetch........................................///////////////////////

    private void typecategory() {
       // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
            loading = ProgressDialog.show(All_category.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/type_cat_fetch.php?main_id="+main_id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONtypecat(response);
                         loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(All_category.this, error.getMessage(), Toast.LENGTH_LONG).show();
                           loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
           loading.dismiss();
    }

    private void showJSONtypecat(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            id_type_cat = new String[users.length()];
            name_type_cat = new String[users.length()];
            image_type_cat = new String[users.length()];


            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                id_type_cat[i] = jo.getString(KEY_type_id);
                name_type_cat[i] = jo.getString(KEY_TITLE);
                image_type_cat[i] = jo.getString(KEY_IMAGE);

                // sub category .,...............end.........
                CustomList adapter = new
                        CustomList(All_category.this, name_type_cat, image_type_cat);
                list=(ListView)findViewById(R.id.list);
                list.setAdapter(adapter);
                //subcatgridfetch();
               // suburl="http://sharmacommunications.com/medical/sub_cat_fetch.php?type_cat_id=71";

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        subcatgridfetch();
                        suburl="http://sharmacommunications.com/medical/sub_cat_fetch.php?type_cat_id="+id_type_cat[position];
                     //   Toast.makeText(All_category.this, "You Clicked at " +name_type_cat[+ position], Toast.LENGTH_SHORT).show();

                    }
                });

                // sub category .,................end........


//
//                    }
//                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // typecategory fetch..............end....................///////////////////////

    //   subcat fetch grid...............................
    private void subcatgridfetch() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
             loading = ProgressDialog.show(All_category.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(suburl,
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
                        Toast.makeText(All_category.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

            name_sub_cat = new String[users.length()];
            image_sub_cat = new String[users.length()];
            id_sub_cat=new String[users.length()];
          //  subid=new String[users.length()];
            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                name_sub_cat[i] = jo.getString(KEY_TITLE);

                image_sub_cat[i] = jo.getString(KEY_IMAGE);
                id_sub_cat[i]=jo.getString(KEY_sub_cat_id);
              //  subid[i]=jo.getString(KEY_sub_id);
            }
            //////.........grid view fetch...................
            Sub_cat_grid_adapter adapter = new Sub_cat_grid_adapter(All_category.this, name_sub_cat, image_sub_cat);
            GridView grid = (GridView) findViewById(R.id.gridview);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent it=new Intent(getApplicationContext(),Product_grid.class);
                    it.putExtra("sub_id",id_sub_cat[+position]);
                    startActivity(it);
                    // Toast.makeText(Home.this, "You Clicked at " +nameoffergrid[+ position], Toast.LENGTH_SHORT).show();

                }
            });



//    /////////end grid view .....................
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
