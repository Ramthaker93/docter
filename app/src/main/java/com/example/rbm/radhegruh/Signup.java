package com.example.rbm.radhegruh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements View.OnClickListener {
        private static EditText fullName, emailId, mobileNumber, location,
                password, confirmPassword;
        private static TextView login;
        private static Button signUpButton;
        private static CheckBox terms_conditions;
        private static LinearLayout signUpLayout;
        private static Animation shakeAnimation;
        String key_fullname = "fullname";
        String key_email = "email";
        String key_sex = "sex";
        String key_birthday = "birthday";
        String key_mobile= "mobile";
        String key_location= "location";
        String key_password = "password";
        String key_repassword = "repassword";
        String send1_url = Splash_Screen.baseurl+"medical/signup.php";
        String getFullName;
        String getEmailId;
        String getMobileNumber;
        String getLocation;
        String getbirthday;
        String getsex;
        String getPassword;
        String getConfirmPassword;
        private ProgressDialog loading;
        RadioButton c1, c2;
        String sexx;
        TextView birthday;
    SessionManager session;
    String name,email;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            session = new SessionManager(getApplicationContext());


            fullName = (EditText)findViewById(R.id.fullName);
            emailId = (EditText)findViewById(R.id.userEmailId);
//        sex = (EditText)findViewById(R.id.sex);

//        birthday = (TextView)findViewById(R.id.birthday);
            mobileNumber = (EditText)findViewById(R.id.mobileNumber);
//        location = (EditText)findViewById(R.id.location);
            password = (EditText)findViewById(R.id.password);
            confirmPassword = (EditText)findViewById(R.id.confirmPassword);
            signUpButton = (Button)findViewById(R.id.signUpBtn);
            login = (TextView)findViewById(R.id.already_user);
            terms_conditions = (CheckBox)findViewById(R.id.terms_conditions);
            signUpLayout = (LinearLayout)findViewById(R.id.signup);
          initViews();
//        setListeners();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home)
                finish();
            Intent i=new Intent(Signup.this,Login.class);


            startActivity(i);
            return super.onOptionsItemSelected(item);
        }


        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK ) {
                //do your stuff
                Intent i=new Intent(Signup.this,Login.class);

              //  startActivity(i);
            }
            return super.onKeyDown(keyCode, event);
        }

    private void initViews() {



        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);
        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

        } catch (Exception e) {
        }

        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    // Set Listeners
//    private void setListeners() {
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                // Call checkValidation method
            checkValidation();
                break;

            case R.id.already_user:
                already_user();

                break;
        }

    }
    private  void already_user(){
        Intent it=new Intent(Signup.this,Login.class);
        startActivity(it);
    }
    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        getFullName = fullName.getText().toString();
        getEmailId = emailId.getText().toString();
        getMobileNumber = mobileNumber.getText().toString();
//        getLocation = location.getText().toString();
        getPassword = password.getText().toString();
        getConfirmPassword = confirmPassword.getText().toString();
//        getsex = sex.getText().toString();
//        getbirthday = birthday.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not

        if (getFullName!=null && getEmailId!=null && getMobileNumber!=null  && getPassword!=null &&getConfirmPassword!=null )
        {
            if(m.find()){
                if(getConfirmPassword.equals(getPassword))
                {

                    if(terms_conditions.isChecked())
                    {
                        send();
                    }
                    else
                    {

                        Toast.makeText(Signup.this, "Please select Terms and Conditions.", Toast.LENGTH_SHORT).show();


                    }

                }else {
                    Toast.makeText(Signup.this, "Both password doesn't match.", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(Signup.this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();


            }




        }else
        {
            Toast.makeText(Signup.this, "All fields are required.", Toast.LENGTH_SHORT).show();
        }

    }
    public void send() {
        {
            loading = ProgressDialog.show(Signup.this, "Please wait...", "Fetching...", false, false);
            try {
                StringRequest rs = new StringRequest(Request.Method.POST, send1_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        if(response.trim().equals("success")){
//                            openProfile();
//                            loading.dismiss();
//                        }else{
Toast.makeText(Signup.this,response, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Signup.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hs = new HashMap<>();
                        hs.put(key_fullname, fullName.getText().toString());
                        hs.put(key_email, emailId.getText().toString());
                        hs.put(key_mobile, mobileNumber.getText().toString());
//                        hs.put(key_location, location.getText().toString());
                        hs.put(key_password,password.getText().toString());
//                        hs.put(key_repassword, confirmPassword.getText().toString());
//                        hs.put(key_sex, sexx.toString());
//                        hs.put(key_birthday, birthday.getText().toString());

                        return hs;

                    }
                };
                RequestQueue rqt = Volley.newRequestQueue(this);
                rqt.add(rs);
            } catch (Exception ex) {
                Toast.makeText(Signup.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openProfile() {
        // Use user real data
        session.createLoginSession(emailId.getText().toString(), password.getText().toString());
if(session!=null){
        Intent intent = new Intent(Signup.this, Home.class);
//        intent.putExtra("email",emailId.getText().toString());
//        intent.putExtra("password",password.getText().toString());

        startActivity(intent);
          }}


}
