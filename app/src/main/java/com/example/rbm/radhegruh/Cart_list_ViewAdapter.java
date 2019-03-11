package com.example.rbm.radhegruh;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rohit Kumar on 2/6/2018.
 */

class Cart_list_ViewAdapter extends ArrayAdapter<Cart_list_geter> {
    int p;
    int total=0,amu;
    int aa=0,amount1;
    String amoun1;
    String url=null;
    public static String tot=null;
    //the hero list that will be displayed
    SessionManager session;
    private List<Cart_list_geter> heroList;
String id1=null,name1=null,birthday1=null,sex1=null,phone1=null,blood_group1=null,address1=null,email=null;
    //the context object
    private Context mCtx;
//    private Context context;
Button caseon,buy;
    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public Cart_list_ViewAdapter(List<Cart_list_geter> heroList, Context mCtx) {
        super(mCtx, R.layout.cart_list_single_item, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        convertView= inflater.inflate(R.layout.cart_list_single_item, null, true);

        final TextView name = (TextView) convertView.findViewById(R.id.name);
        final TextView sub_tot = (TextView) convertView.findViewById(R.id.sub_total);
       final TextView price = (TextView) convertView.findViewById(R.id.price);
        final TextView discount = (TextView) convertView.findViewById(R.id.discount);
        final TextView shipchrg = (TextView) convertView.findViewById(R.id.shiping_chrg);
        final TextView discount1 = (TextView) convertView.findViewById(R.id.discount1);
        final TextView amount = (TextView) convertView.findViewById(R.id.amount);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        ImageButton del = (ImageButton) convertView.findViewById(R.id.del);


       buy=(Button) convertView.findViewById(R.id.online);
       //caseon=(Button) convertView.findViewById(R.id.caseon);
        final Cart_list_geter hero = heroList.get(position);

        // Spinner element
        //final Spinner qunty = (Spinner) convertView.findViewById(R.id.quntity);

        // Spinner click listener
      //  qunty.setOnItemSelectedListener(this);
     ;




        name.setText(hero.gettv2());//+":-" + System.lineSeparator()+hero.gettv3());
        price.setText("\u20B9"+hero.gettv6());
        sub_tot.setText("\u20B9"+hero.gettv6());
        discount.setText(hero.gettv7()+" %");
        shipchrg.setText("\u20B9"+hero.gettv7());
        discount1.setText("\u20B9"+hero.gettv7());
amount.setText(hero.gettv6());

        Picasso.with(mCtx).load(hero.gettv4()).resize(260, 180).into(img);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, "aaaaaaaaaaaa", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mCtx.getApplicationContext(), Delete_cart.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("id",hero.gettv8());

                mCtx.getApplicationContext().startActivity(intent);

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCtx.getApplicationContext(), PayMentGateWay.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("product_id",hero.gettv1());
                intent.putExtra("RECHARGE_AMT",hero.gettv6());
                mCtx.getApplicationContext().startActivity(intent);
                 //Toast.makeText(mCtx, "working state.............", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;


    }



    }