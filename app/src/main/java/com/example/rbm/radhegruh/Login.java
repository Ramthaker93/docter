package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.OptionalPendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static EditText emailid, password,skip;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;

    String key_email = "email";
    String key_pass = "password";
    String key_name="full_name";

    String getPassword;
    String getEmailId;
    String getname;
    private ProgressDialog loading;
   SessionManager session;
    String name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       session = new SessionManager(getApplicationContext());


        emailid = (EditText)findViewById(R.id.login_emailid);
        password = (EditText)findViewById(R.id.login_password);
        loginButton = (Button)findViewById(R.id.login);
        forgotPassword = (TextView)findViewById(R.id.forgot_password);
        signUp = (TextView)findViewById(R.id.createAccount);
        show_hide_password =(CheckBox) findViewById(R.id.show_hide_password);


        setListeners();



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        Intent i=new Intent(Login.this,Login.class);


        startActivity(i);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //do your stuff
            Intent i=new Intent(Login.this,Home.class);

            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }
    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        getEmailId = emailid.getText().toString();
        getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);
//        Pattern pp = Pattern.compile(Utils.PASSWORD_PATTERN);

        Matcher m = p.matcher(getEmailId);
//        Matcher mm = pp.matcher(getPassword);

        // Check for both field is empty or not
        if (getEmailId.equals(getEmailId)
                && getPassword.equals(getPassword)) {
            if(m.find()){
                send1();
            }

            else {
                Toast.makeText(Login.this, "invaild data", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void send1(){
//		getEmailId = emailid.getText().toString().trim();
//		getPassword= password.getText().toString().trim();

        String send1_url = Splash_Screen.baseurl+"medical/login.php";
        loading = ProgressDialog.show(Login.this, "Please wait...", "Fetching...", false, false);

        try{
            StringRequest rs=new StringRequest(Request.Method.POST,send1_url, new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    String ss=response.toString();

                    if(ss.trim().equals("success")) {
                        if (getEmailId != null) {
                            openProfile();
                            loading.dismiss();
                        }
                    }

                    else{
                        Toast.makeText(Login.this,response, Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Login.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hs=new HashMap<>();

                    hs.put(key_email,getEmailId);
                    hs.put(key_pass,getPassword);
                    // hs.put(key_name,getname);

                    return hs;
                }
            };
            RequestQueue rqt= Volley.newRequestQueue(this);
            rqt.add(rs);

        }catch (Exception ex){

//            Toast.makeText(getActivity(),ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void openProfile(){
        // Use user real data
       session.createLoginSession(getEmailId, getPassword);

        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);


    }


    @Override
    public void onClick(View v) {
        if (v == loginButton) {
          //  FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
            checkValidation();
        }
        else if (v == signUp) {
            signup();
        }
        else if (v == forgotPassword) {
            forgot_password();
        }

    }





    private void signup(){
        Intent it =new Intent(Login.this,Signup.class);
        startActivity(it);

    }
    private void forgot_password(){
        Intent it =new Intent(Login.this,forget_password.class);
        startActivity(it);

    }
    public void skip(View v){
        Intent it =new Intent(Login.this,Home.class);
        startActivity(it);
    }
}
