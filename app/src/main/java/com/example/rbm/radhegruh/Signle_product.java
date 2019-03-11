package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class Signle_product extends AppCompatActivity {
    String key_product_id = "product_id";
    String key_name= "name";
    String key_amount = "amount";
    String key_email = "email";
    String key_quantity= "quantity";
    String pro_id,pro_name,pro_price,pro_quantity;
    SessionManager session;
    String name,email=null;
    ProgressDialog loading;
    //the hero list that will be displayed
    private List<Product_list_geter> heroList;

    //the context object
    TextView qunty;
    //    private Context context;
    Button more,buy;
    String p_id=null,p_name,price=null,des1,discount,img;
int p=0;
    int  q=1;


    //horizontal

    public static final String KEY_TITLE = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_IMAGE1 = "image1";
    public static final String KEY_IMAGE2 = "image2";
    public static final String KEY_IMAGE3 = "image3";
    public static final String KEY_SELLING_PRICE = "selling_price";
    public static final String KEY_description = "description";
    public static final String KEY_discount = "discount";
    public static final String KEY_id = "product_id";
    public static final String KEY_type_cat_id = "type_cat_id";
    public static final String JSON_ARRAY = "result";

    //offer grid....
    public static final String KEY_PRICE = "price";
   public static final String KEY_sub_id = "sub_id";


    private JSONArray users = null;

    public static String[] title=null;
    public static String[] image = null;
    public static String[] selling_price = null;
    public static String[] p_id2=null;
    public static String[] des = null;
    public static String[] discou = null;

    public static String[] image1 = null;
    public static String[] image2 = null;
    public static String[] image3 = null;

    //offer grid.....
    public static String[] nameoffergrid = null;
    public static String[] imageoffergrid = null;
    public static String[] offer_price = null;
    public static String[] subid = null;
    //end offer grid...
    String JSON_URL4 = Splash_Screen.baseurl+"medical/category.php";
    String JSON_URL = Splash_Screen.baseurl+"medical/new_product.php";
    // private JSONArray users = null;
    LayoutInflater layoutInflater,layoutInflater1;
    LinearLayout linearLayout,linearLayout2,linearLayout12,linearLayout4,linearLayout5,linearLayout6;
    HorizontalScrollView horizontalScrollView,horizontalScrollView2,horizontalScrollView12,horizontalScrollView4,horizontalScrollView5,horizontalScrollView6;

    // horizontal ............end.

    TextView tv2;
    ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signle_product);


       session = new SessionManager(getApplicationContext());
//
//        //session.checkLogin();
//// get user data from
             HashMap<String, String> user = session.getUserDetails();
//
//
//        // name
       name = user.get(SessionManager.KEY_NAME);
email = user.get(SessionManager.KEY_EMAIL);
        // horizontal  .........................................///////////////////////
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        horizontalScrollView12 = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view12);
        linearLayout12 = (LinearLayout) findViewById(R.id.img_gallery12);


        // horizontal  .........................................///////////////////////
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        linearLayout = (LinearLayout) findViewById(R.id.img_gallery);
        sendRequest1();
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        horizontalScrollView2 = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view1);
        linearLayout2 = (LinearLayout) findViewById(R.id.img_gallery1);
        sendRequest2();

// horizonatal.....................end...................///////////////////////

        Intent it = getIntent();
        Bundle b = it.getExtras();
        p_id= (String) b.get("p_id");
        p_name= (String) b.get("name");
        des1   = (String) b.get("description");
        price  = (String) b.get("price");
        discount = (String) b.get("discount");
          p_name = (String) b.get("name");
         img= (String) b.get("img");
         qunty = (TextView) findViewById(R.id.quntity);

         img1=(ImageView)findViewById(R.id.img);
        TextView tv=(TextView)findViewById(R.id.name);
        tv.setText(p_name);
        tv2=(TextView)findViewById(R.id.price);
        tv2.setText("\u20B9"+price);
        TextView tv3=(TextView)findViewById(R.id.discount);
        tv3.setText(discount+"% OFF");
        TextView tv4=(TextView)findViewById(R.id.des);
        tv4.setText(des1);


        tv2.setText(price);


        qunty.setText("1");
//        // Initializing an ArrayAdapter
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,plants
//        );
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        qunty.setAdapter(spinnerArrayAdapter);
//        qunty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView)
//            {
//
//            }
//        });

        sendRequest12();
        sendRequest2();
        sendRequest1();
        offergridfetch();
        Button cart=(Button)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.checkLogin();
               send();
              //  Toast.makeText(Signle_product.this, price, Toast.LENGTH_SHORT).show();
            }
        });
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
    public void send() {
        {

            loading = ProgressDialog.show(Signle_product.this, "Please wait...", "Fetching...", false, false);
            try {
                StringRequest rs = new StringRequest(Request.Method.POST, Splash_Screen.baseurl+"medical/add_cart.php?email="+email, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//
                        openProfile();
                        loading.dismiss();



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      Toast.makeText(Signle_product.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(product_list.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                     loading.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hs = new HashMap<>();
                        hs.put(key_product_id, p_id);
                        hs.put(key_name, p_name);
                        hs.put(key_amount, price);
                        hs.put(key_email,  email);
                        hs.put(key_quantity,  qunty.getText().toString());
//                        hs.put(key_repassword, confirmPassword.getText().toString());
//                        hs.put(key_sex, sexx.toString());
//                        hs.put(key_birthday, birthday.getText().toString());

                        return hs;

                    }
                };
                RequestQueue rqt = Volley.newRequestQueue(this);
                rqt.add(rs);
            } catch (Exception ex) {
                Toast.makeText(Signle_product.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(product_list.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }
    }
    private void openProfile(){
        Toast.makeText(Signle_product.this, "cart add", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(product_list.this, Login.class);
////        intent.putExtra("email",emailId.getText().toString());
////        intent.putExtra("password",password.getText().toString());
//
//        startActivity(intent);
    }



    // image horizontal.........................................///////////////////////

    private void sendRequest12() {
       final String JSON_URL = "http://sharmacommunications.com/medical/product_image.php?product_id="+p_id;
        //  loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);
//        Toast.makeText(this, p_id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(JSON_URL,//http://sharmacommunications.com/medical/type_cat_fetch.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON12(response);
                        //loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signle_product.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        //  loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        // loading.dismiss();
    }

    private void showJSON12(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            image1 = new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                image1[i] = jo.getString(KEY_IMAGE);



                View view = layoutInflater.inflate(R.layout.category_ican, linearLayout12, false);
                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout_cat);
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                final TextView item_name = (TextView) view.findViewById(R.id.tv);



                Picasso.with(this)
                        .load(image1[i])
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.ic_launcher_foreground)
//                        .resize(400,400)
                        .into(imageView);
                item_name.setVisibility(View.INVISIBLE);
                Picasso.with(Signle_product.this).load(image1[0]).into(img1);
                if (i == (image1.length) - 1) {
                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
                }
                linearLayout12.addView(view);
                // Toast.makeText(Home.this, title[i], Toast.LENGTH_SHORT).show();
               // final String a=name_type_cat[i];
                final String imageurl=image1[i];
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Picasso.with(this).load(image1[i]) .into(imageView);
                        Picasso.with(Signle_product.this).load(imageurl).into(img1);
                    }
                });

//                scroll_item_layout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if()
//                        Intent it=new Intent(getApplicationContext(),Medical_list.class);
//
//                        startActivity(it);
//                        startActivity(it);
//
//                    }
//                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // image horizontal.....................end....................///////////////////////



    // first horizontal.........................................///////////////////////

    private void sendRequest1() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        loading = ProgressDialog.show(Signle_product.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON1(response);
                        loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signle_product.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        loading.dismiss();
    }

    private void showJSON1(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            title = new String[users.length()];
            image = new String[users.length()];
            selling_price=new String[users.length()];
            des = new String[users.length()];
            p_id2 = new String[users.length()];
            discou=new String[users.length()];
            loading.dismiss();

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                title[i] = jo.getString(KEY_TITLE);

                image[i] = jo.getString(KEY_IMAGE);
                selling_price[i] = jo.getString(KEY_SELLING_PRICE);
                p_id2[i] = jo.getString(KEY_id);

                des[i] = jo.getString(KEY_description);
                discou[i] = jo.getString(KEY_discount);
                final String p_id1= p_id2[i];
                final String p_name= title[i];
                final String p_des= des[i];
                final String p_price= selling_price[i];
                final String p_img= image[i];
                final String p_discount= discou[i];


//                View view = layoutInflater.inflate(R.layout.vertical_menu, linearLayout, false);
//                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout);
//                ImageView imageView = (ImageView) view.findViewById(R.id.scroll_image_view);
//                final TextView item_name = (TextView) view.findViewById(R.id.gg);

                View view = layoutInflater.inflate(R.layout.vertical2, linearLayout, false);
                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout1);
                ImageView imageView = (ImageView) view.findViewById(R.id.scroll_image_view);
                final TextView item_name = (TextView) view.findViewById(R.id.gg);
                final TextView price = (TextView) view.findViewById(R.id.price);


                Picasso.with(this)
                        .load(image[i])
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.ic_launcher_foreground)
//                        .resize(400,400)
                        .into(imageView);
                item_name.setText(title[i]);
                price.setText(selling_price[i]);
                if (i == (title.length) - 1) {
                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
                }
                linearLayout.addView(view);
                //   Toast.makeText(Home.this, title[i], Toast.LENGTH_SHORT).show();
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(Home.this, a, Toast.LENGTH_SHORT).show();
//                    }
//                });

                scroll_item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplication(), Signle_product.class);
                        intent.putExtra("p_id",p_id1);
                        intent.putExtra("name",p_name);
                        intent.putExtra("description",p_des);
                        intent.putExtra("img",p_img);

                        intent.putExtra("price",p_price);
                        intent.putExtra("discount",p_discount);
                        startActivity(intent);

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // first horizontal.....................end....................///////////////////////

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
                        Toast.makeText(Signle_product.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
            offer_grid_adapter adapter = new offer_grid_adapter(Signle_product.this, nameoffergrid, imageoffergrid, offer_price);
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

    // second horizontal.........................................///////////////////////
    private void sendRequest2() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        loading = ProgressDialog.show(Signle_product.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON2(response);
                        loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signle_product.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        loading.dismiss();
    }

    private void showJSON2(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            title = new String[users.length()];
            image = new String[users.length()];
            selling_price=new String[users.length()];
            des = new String[users.length()];
            p_id2 = new String[users.length()];
            discou=new String[users.length()];
            loading.dismiss();

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                title[i] = jo.getString(KEY_TITLE);

                image[i] = jo.getString(KEY_IMAGE);
                selling_price[i] = jo.getString(KEY_SELLING_PRICE);
                p_id2[i] = jo.getString(KEY_id);

                des[i] = jo.getString(KEY_description);
                discou[i] = jo.getString(KEY_discount);
                final String p_id1= p_id2[i];
                final String p_name= title[i];
                final String p_des= des[i];
                final String p_price= selling_price[i];
                final String p_img= image[i];
                final String p_discount= discou[i];



                View view = layoutInflater.inflate(R.layout.vertical2, linearLayout2, false);
                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout1);
                ImageView imageView = (ImageView) view.findViewById(R.id.scroll_image_view);
                final TextView item_name = (TextView) view.findViewById(R.id.gg);
                final TextView price = (TextView) view.findViewById(R.id.price);


                Picasso.with(this)
                        .load(image[i])
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.ic_launcher_foreground)
//                        .resize(400,400)
                        .into(imageView);
                item_name.setText(title[i]);
                price.setText(selling_price[i]);
                if (i == (title.length) - 1) {
                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
                }
                linearLayout2.addView(view);
                //   Toast.makeText(Home.this, title[i], Toast.LENGTH_SHORT).show();
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(Home.this, a, Toast.LENGTH_SHORT).show();
//                    }
//                });

                scroll_item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplication(), Signle_product.class);
                        intent.putExtra("p_id",p_id1);
                        intent.putExtra("name",p_name);
                        intent.putExtra("description",p_des);
                        intent.putExtra("img",p_img);

                        intent.putExtra("price",p_price);
                        intent.putExtra("discount",p_discount);
                        startActivity(intent);

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    ////uopload button............gallery and camrea................
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    // Prescription_image.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //  Prescription_image.setImageURI(selectedImage);
                }
                break;
        }
    }

    public void add(View view) {
        if (q > 0) {
            q=(Integer)parseInt(qunty.getText().toString());


            //=(Integer)parseInt(pro_quantity);

            q++;
            int pq = 0;
            pq = p * q;
            price = valueOf(pq).toString();
            tv2.setText(price);
            qunty.setText(valueOf(q).toString());
        }
    }
    public void decre(View view) {

     if(q>0){

        // q=(Integer)parseInt(qunty.getText().toString());
         qunty.setText(valueOf(q).toString());

        //=(Integer)parseInt(pro_quantity);
        q--;
        int pq=0;
        pq=p*q;
        price=valueOf(pq).toString();

        tv2.setText(price);
    }}
//uopload button............gallery and camrea................end...
    // second horizontal.....................end....................///////////////////////
}
