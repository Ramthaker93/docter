package com.example.rbm.radhegruh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Delete_cart extends AppCompatActivity {
    String id = null;
    ProgressDialog loading;
    String update_url;
    SessionManager session;

    String password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class_a);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        id = (String) b.get("id");

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        password = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to delete item ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete_data();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        finish();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Delete Item");
        alert.show();
        setContentView(R.layout.cart_list__main);


         update_url =  Splash_Screen.baseurl+"medical/delete_cart.php?cart_id="+id;

    }

    public void delete_data() {
        {
            loading = ProgressDialog.show(Delete_cart.this, "Please wait...", "Fetching...", false, false);
            try {


//        getData();;
                StringRequest rs = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //  openProfile();


                        Toast.makeText(Delete_cart.this, response, Toast.LENGTH_LONG).show();
                        loading.dismiss();
                        Intent i = new Intent(Delete_cart.this, Cart_list.class);
                        startActivity(i);

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Delete_cart.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                        loading.dismiss();


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hs = new HashMap<>();


                        return hs;

                    }
                };
                RequestQueue rqt = Volley.newRequestQueue(this);
                rqt.add(rs);
            } catch (Exception ex) {
                Toast.makeText(Delete_cart.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

            }
            loading.dismiss();
        }

    }
}
