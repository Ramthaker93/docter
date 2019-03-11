package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_profile extends AppCompatActivity {

    // user profile.............
    public static final String JSON_ARRAY = "result";
    public static final String KEY_name = "name";
    public static final String KEY_lastname = "last_name";
    public static final String KEY_address1 = "address1";
    public static final String KEY_phone = "phone";
    public static final String KEY_password = "password";
    //user profile...end........

    //  update  profile.....

    String key_name = "name";
    String key_lastname = "lastname";
    String key_phone = "phone";
    String key_address = "address";
    String key_password = "password";
    String key_email= "email";

    //update profile ......end...
    SessionManager session1;
    String email;
    EditText name,lastname,emailaddress,phone,adddress,password;
    ImageView profile_img;
    ImageView edit;
    Button done;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //session......
        session1 = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session1.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        //session....end...

        name=findViewById(R.id.name);
        lastname=findViewById(R.id.lastname);
        emailaddress=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        adddress=findViewById(R.id.address);
        password=findViewById(R.id.password);
        edit=findViewById(R.id.edit);
        done=findViewById(R.id.done);

        done.setVisibility(View.INVISIBLE);
        name.setEnabled(false);
        lastname.setEnabled(false);
        emailaddress.setEnabled(false);
        password.setEnabled(false);
        phone.setEnabled(false);
        adddress.setEnabled(false);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                lastname.setEnabled(true);
                password.setEnabled(true);
                phone.setEnabled(true);
                adddress.setEnabled(true);
                done.setVisibility(View.VISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile();
            }
        });
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getuserprofile();
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
            Intent i=new Intent(User_profile.this,Home.class);

            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
    //  user profile fetch..................
    private void getuserprofile() {


          loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "http://sharmacommunications.com/medical/user_profile.php?email="+email;

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
                        Toast.makeText(User_profile.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        String uname="";
        String uphone="";
        String ulastname="";
        String uaddress= "";
        String upassword= "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            uname = collegeData.getString(KEY_name);
            ulastname = collegeData.getString(KEY_lastname);
            upassword = collegeData.getString(KEY_password);
            uphone = collegeData.getString(KEY_phone);
            uaddress = collegeData.getString(KEY_address1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        name.setText(uname);
        lastname.setText(ulastname);
        phone.setText(uphone);
        adddress.setText(uaddress);
        emailaddress.setText(email);
        password.setText(upassword);

    }
    //   user profile end...................



    //update profile..........

    public void update_profile() {
        {
            String url="http://sharmacommunications.com/medical/update_profile.php";
            loading = ProgressDialog.show(User_profile.this, "Please wait...", "Fetching...", false, false);
            try {
                StringRequest rs = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        if(response.trim().equals("success")){
//                            openProfile();
//                            loading.dismiss();
//                        }else{
                        Toast.makeText(User_profile.this,response, Toast.LENGTH_LONG).show();
//                            loading.dismiss();
//                        }

                        String ss=response.toString();
                        if(ss.trim().equals("successfully")) {
                            loading.dismiss();
                            openProfile();

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(User_profile.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hs = new HashMap<>();
                        hs.put(key_email, email);
                        hs.put(key_name, name.getText().toString());
                        hs.put(key_lastname, lastname.getText().toString());
                        hs.put(key_password,password.getText().toString());
                        hs.put(key_phone, phone.getText().toString());
                        hs.put(key_address,adddress.getText().toString());


                        return hs;

                    }
                };
                RequestQueue rqt = Volley.newRequestQueue(this);
                rqt.add(rs);
            } catch (Exception ex) {
                Toast.makeText(User_profile.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openProfile() {
        // Use user real data

       // session1.createLoginSession(email, name.getText().toString());
        if(session1!=null){
            Intent intent = new Intent(User_profile.this, User_profile.class);
            startActivity(intent);
        }}
    //  update profile.......end............
}



