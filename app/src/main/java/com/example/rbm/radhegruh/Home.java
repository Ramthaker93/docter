package com.example.rbm.radhegruh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




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


    // user profile.............
    public static final String KEY_name = "name";
    public static final String KEY_lastname = "last_name";
    public static final String KEY_city = "city";
    public static final String KEY_phone = "phone";
String user_name="";
    //user profile...end........

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
    public static String[] offer_price = null;
    public static String[] subid = null;



    // private JSONArray users = null;
    LayoutInflater layoutInflater, layoutInflater1;
    LinearLayout linearLayout, linearLayout2, linearLayout12, linearLayout4, linearLayout5, linearLayout6;
    HorizontalScrollView horizontalScrollView, horizontalScrollView2, horizontalScrollView12, horizontalScrollView4, horizontalScrollView5, horizontalScrollView6;
  ProgressDialog loading;

    public static String[] dataArr = null;


    SearchView searchView;
    SearchView.SearchAutoComplete searchAutoComplete;


    NavigationView navigationView;
    Menu menu;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //session......
        session1 = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session1.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        //session....end...


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            menu = navigationView.getMenu();

        }
        navigationView.setNavigationItemSelectedListener(this);


        // slider   .........................................///////////////////////
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
// slider............................end.............///////////////////////


        // horizontal  .........................................///////////////////////
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        horizontalScrollView12 = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view12);
        linearLayout12 = (LinearLayout) findViewById(R.id.img_gallery12);
       sendRequest12();


        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        linearLayout = (LinearLayout) findViewById(R.id.img_gallery);
        sendRequest1();
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        horizontalScrollView2 = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view1);
        linearLayout2 = (LinearLayout) findViewById(R.id.img_gallery1);
        sendRequest2();

// horizonatal.....................end...................///////////////////////

        // Get ActionBar.................search view menu......autocomplete.................//
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // Set below attributes to add logo in ActionBar.
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.ic_menu_slideshow);

        actionBar.setTitle("Search Item");
        offergridfetch();
        gridfetch();
        shopfetch();
      getuserprofile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the search menu action bar.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_search_example_menua, menu);

        // Get the search menu.
        MenuItem searchMenu = menu.findItem(R.id.app_bar_menu_search);

        // Get SearchView object.
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);

        // Get SearchView autocomplete object.
        searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchAutoComplete.setBackgroundColor(Color.BLUE);
//        searchAutoComplete.setTextColor(Color.GREEN);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.white);

        // Create a new ArrayAdapter and add data to search auto complete object.
        search_item();
        //String [] stockArr = data.toArray(new String[0]);
        // String[] dataa = (String[]) data;



// Get ActionBar.................search view menu.............end..........//
        // cart ican.........................................///////////////////////
        MenuItem cart = menu.findItem(R.id.cart_ican);

        cart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent it=new Intent(getApplicationContext(),Cart_list.class);
//              it.putExtra("key","1");
//              it.putExtra("name",a);
                startActivity(it);
                return false;
            }
        });

// cart ican....................end.....................///////////////////////

        // Get the share menu item.
        MenuItem shareMenuItem = menu.findItem(R.id.app_bar_menu_share);
        // Because it's actionProviderClass is ShareActionProvider, so after below settings
        // when click this menu item A sharable applications list will popup.
        // User can choose one application to share.
        ShareActionProvider shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareMenuItem);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.homea, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.signup) {
            // menu.findItem(R.id.signup).setTitle("SignUp");
            if(name==null) {
                Intent it = new Intent(getApplicationContext(), Login.class);
                 startActivity(it);
            }
        }

        else if (id == R.id.eat) {
            Intent it=new Intent(getApplicationContext(),All_category.class);
            it.putExtra("id","1");
            startActivity(it);
        }
        else if (id == R.id.man) {
            Toast.makeText(this, "UPCOMING....", Toast.LENGTH_SHORT).show();
//            Intent it=new Intent(getApplicationContext(),All_category.class);
//            startActivity(it);
        }
        else if (id == R.id.woman) {
//            Intent it=new Intent(getApplicationContext(),All_category.class);
//            startActivity(it);
            Toast.makeText(this, "UPCOMING....", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.kids) {
//            Intent it=new Intent(getApplicationContext(),All_category.class);
//            startActivity(it);
            Toast.makeText(this, "UPCOMING....", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.profile) {
            //setting sub menus visible state..................................
            NavigationView nv= (NavigationView) findViewById(R.id.nav_view);
            Menu m=nv.getMenu();
            boolean b=!m.findItem(R.id.p_about).isVisible();

            m.findItem(R.id.p_about).setVisible(b);
            m.findItem(R.id.my_order).setVisible(b);
            m.findItem(R.id.my_rewards).setVisible(b);
            m.findItem(R.id.my_cart).setVisible(b);
            m.findItem(R.id.my_wishlist).setVisible(b);
//            m.findItem(R.id.Lifestyle).setVisible(b);
//            m.findItem(R.id.Treatments).setVisible(b);
//            m.findItem(R.id.Devices).setVisible(b);

            return true;

            //setting sub menus visible state.....................end.............
        }
        else if(id == R.id.p_about)
        {
            Intent it=new Intent(getApplicationContext(),User_profile.class);
            startActivity(it);

        }
        else if(id == R.id.my_order)
        {
            Intent it=new Intent(getApplicationContext(),My_order.class);

            startActivity(it);
        }
        else if(id == R.id.my_rewards)
        {

        }
        else if(id == R.id.my_cart)
        {
            Intent it=new Intent(getApplicationContext(),Cart_list.class);
             startActivity(it);
        }
        else if(id == R.id.my_wishlist)
        {
            Intent it=new Intent(getApplicationContext(),Wishlist.class);
            startActivity(it);
        }

        else if (id == R.id.Home) {
            Intent it=new Intent(getApplicationContext(),Home.class);

            startActivity(it);
        }
        else if (id == R.id.Offer) {

            Intent it=new Intent(getApplicationContext(),Offer.class);

            startActivity(it);
        } else if (id == R.id.Rate) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=rbm.fuelvine"));
            startActivity(i);

        }
//        else if (id == R.id.Support ) {
//            Intent it=new Intent(getApplicationContext(),Products.class);
//
//            startActivity(it);
//        } else if (id == R.id.Refer) {
//            Intent it=new Intent(getApplicationContext(),Products.class);
//
//            startActivity(it);
//         }
        else if (id == R.id.logout) {
//            //Intent it=new Intent(getApplicationContext(),Products.class);
          session1.logoutUser();
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                    new ResultCallback<Status>() {
//                        @Override
//                        public void onResult(Status status) {
//                            // ...
//                            Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
//                            Intent i=new Intent(getApplicationContext(),Login.class);
//                            startActivity(i);
//                        }
//                    });

            //startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

// autocomplete item fetch.........................................///////////////////////

    private void search_item() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
       //  loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/new_product.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON3(response);
                      //   loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                      ///   loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        // loading.dismiss();
    }

    private void showJSON3(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            dataArr = new String[users.length()];

            //loading.dismiss();

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                dataArr[i] = jo.getString(KEY_TITLE);

                // dataArr[i]=data[i];

            }
            ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dataArr);
            searchAutoComplete.setAdapter(newsAdapter);

            // Listen to search view item on click event.
            searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                    String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                    searchAutoComplete.setText("" + queryString);
                    Toast.makeText(Home.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
                }
            });

            // Below event is triggered when submit search query.
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();
//                alertDialog.setMessage("Search keyword is " + query);
//                alertDialog.show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //autocomplete iteam ......................end...................///////////////////////




    // type......catogary horizontal.........................................///////////////////////

    private void sendRequest12() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
      //  loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/all_type_cat_fetch.php",//http://sharmacommunications.com/medical/type_cat_fetch.php",
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
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

            name_type_cat = new String[users.length()];
            image_type_cat = new String[users.length()];
            type_cat_id= new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                name_type_cat[i] = jo.getString(KEY_TITLE);

                image_type_cat[i] = jo.getString(KEY_IMAGE);
                type_cat_id[i] = jo.getString(KEY_type_cat_id);

                View view = layoutInflater.inflate(R.layout.category_ican, linearLayout12, false);
                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout_cat);
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                final TextView item_name = (TextView) view.findViewById(R.id.tv);



                Picasso.with(this)
                        .load(image_type_cat[i])
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.ic_launcher_foreground)
//                        .resize(400,400)
                        .into(imageView);
                item_name.setText(name_type_cat[i]);

                if (i == (name_type_cat.length) - 1) {
                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
                }
                linearLayout12.addView(view);
                // Toast.makeText(Home.this, title[i], Toast.LENGTH_SHORT).show();
                final String a=name_type_cat[i];
                final String typeCatId=type_cat_id[i];
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent it=new Intent(getApplicationContext(),Sub_cat_list.class);
                            it.putExtra("type_cat_id",typeCatId);
                            it.putExtra("name",a);
                            startActivity(it);

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

    // category horizontal.....................end....................///////////////////////




    // first horizontal.................sub cat........................///////////////////////

    private void sendRequest1() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
     //   loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("https://sharmacommunications.com/medical/all_sub_cat_fetch.php",//http://sharmacommunications.com/medical/product.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON1(response);
                    //    loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                     //   loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
       // loading.dismiss();
    }

    private void showJSON1(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            name_sub_cat = new String[users.length()];
            image_sub_cat = new String[users.length()];
           sub_id=new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                name_sub_cat[i] = jo.getString(KEY_TITLE);
                sub_id[i]=jo.getString(KEY_sub_id);
                image_sub_cat[i] = jo.getString(KEY_IMAGE);


                View view = layoutInflater.inflate(R.layout.vertical_menu, linearLayout, false);
                LinearLayout scroll_item_layout = (LinearLayout) view.findViewById(R.id.scrol_item_Layout);
                ImageView imageView = (ImageView) view.findViewById(R.id.scroll_image_view);
                final TextView item_name = (TextView) view.findViewById(R.id.gg);



                Picasso.with(this)
                        .load(image_sub_cat[i])
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.ic_launcher_foreground)
//                        .resize(400,400)
                        .into(imageView);
                item_name.setText(name_sub_cat[i]);

                if (i == (name_sub_cat.length) - 1) {
                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
                }
                linearLayout.addView(view);
                // Toast.makeText(Home.this, name_sub_cat[i], Toast.LENGTH_SHORT).show();
                final String a=name_sub_cat[i];
                final String subId=sub_id[i];
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent it=new Intent(getApplicationContext(),product_list.class);
                            it.putExtra("sub_id",subId);
                            it.putExtra("name",a);
                            startActivity(it);

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

    // first horizontal...........sub cat..........end....................///////////////////////

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
                          Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
            offer_grid_adapter adapter = new offer_grid_adapter(Home.this, nameoffergrid, imageoffergrid, offer_price);
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

    // second horizontal............products .............................///////////////////////
    private void sendRequest2() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
      loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/new_product.php",
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
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
     //   loading.dismiss();
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
            p_id = new String[users.length()];
            discou=new String[users.length()];
          //  loading.dismiss();

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                title[i] = jo.getString(KEY_TITLE);

                image[i] = jo.getString(KEY_IMAGE);
                selling_price[i] = jo.getString(KEY_SELLING_PRICE);
                p_id[i] = jo.getString(KEY_id);

                des[i] = jo.getString(KEY_description);
                discou[i] = jo.getString(KEY_discount);
                final String p_id1= p_id[i];
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
                price.setText("Price:-"+selling_price[i]);
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

    // second horizontal................products.....end....................///////////////////////

   //   main catfetch ....................................///////////////////
    private void gridfetch() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
   //     loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("http://sharmacommunications.com/medical/category.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONgrid(response);
                      //  loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    //    loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
     //   loading.dismiss();
    }

    private void showJSONgrid(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            namegrid = new String[users.length()];
            imagegrid = new String[users.length()];
            main_id=new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                namegrid[i] = jo.getString(KEY_TITLE);

                imagegrid[i] = jo.getString(KEY_IMAGE);
                main_id[i] = jo.getString(KEY_main_id);

//                final String mainId = main_id[i];
//                final String name = namegrid[i];
            }
                //////.........grid view fetch...................
                CustomGrid adapter = new CustomGrid(Home.this, namegrid, imagegrid);
                GridView grid = (GridView) findViewById(R.id.gridview);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent it = new Intent(getApplicationContext(), Type_cat_list.class);
                        it.putExtra("main_id", main_id[+position]);
                        it.putExtra("name", namegrid[+position]);
                        startActivity(it);
                        Toast.makeText(Home.this, "You Clicked at " + namegrid[+position], Toast.LENGTH_SHORT).show();

                    }
                });



//    /////////end grid view .....................
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //   main catfetch ..................end..................///////////////////

    //  shoop fetch grid.........................................///////////////////////

    private void shopfetch() {
        // final String JSON_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        //     loading = ProgressDialog.show(Home.this, "Please wait...", "Fetching...", false, false);

        StringRequest stringRequest = new StringRequest("https://sharmacommunications.com/medical/shop.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSONshopgrid(response);
                        //  loading.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        //    loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //   loading.dismiss();
    }

    private void showJSONshopgrid(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            nameshopgrid = new String[users.length()];
            imageshopgrid = new String[users.length()];


            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);


                nameshopgrid[i] = jo.getString(KEY_TITLE);

                imageshopgrid[i] = jo.getString(KEY_IMAGE);

            }
            //////.........grid view fetch...................
            CustomGrid adapter = new CustomGrid(Home.this, nameshopgrid, imageshopgrid);
            GridView grid = (GridView) findViewById(R.id.shopgrid);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(Home.this, "You Clicked at " +nameshopgrid[+ position], Toast.LENGTH_SHORT).show();

                }
            });



//    /////////end grid view .....................
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //  shoop fetch grid.....................end....................///////////////////////

//  user profile fetch..................
private void getuserprofile() {


  //  loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

    String url = "http://sharmacommunications.com/medical/user_profile.php?email="+email;

    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
           // loading.dismiss();
            showJSON(response);
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Home.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

    private void showJSON(String response){

        String name="";
        String phone="";
        String lastname="";
        String city= "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(KEY_name);
            lastname = collegeData.getString(KEY_lastname);
            phone = collegeData.getString(KEY_phone);
            city = collegeData.getString(KEY_city);


        } catch (JSONException e) {
            e.printStackTrace();
        }
       // user_name=name;
        if (email != null) {
            menu.findItem(R.id.signup).setTitle(name);
            menu.findItem(R.id.signup).setIcon(R.drawable.ic_mood_black_24dp);
        } else {
            menu.findItem(R.id.signup).setTitle("SignUp");
        }


    }
}

//   user profile end...................



