package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rohit Kumar on 2/6/2018.
 */

class Product_list_ViewAdapter extends ArrayAdapter<Product_list_geter> {

//   SessionManager session;
   String name,email;
    ProgressDialog loading;
    //the hero list that will be displayed
    private List<Product_list_geter> heroList;

    private Context mCtx;
//    private Context context;
Button more,buy;
    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public Product_list_ViewAdapter(List<Product_list_geter> heroList, Context mCtx) {
        super(mCtx, R.layout.product_list_single_item, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
//        session = new SessionManager(getContext());
//
//        //session.checkLogin();
//// get user data from session
//        HashMap<String, String> user = session.getUserDetails();
//
//
//        // name
//        name = user.get(SessionManager.KEY_NAME);
//        email = user.get(SessionManager.KEY_EMAIL);


    }

    //this method will return the list item
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        convertView= inflater.inflate(R.layout.product_list_single_item, null, true);

        final TextView name = (TextView) convertView.findViewById(R.id.name);

       final TextView price = (TextView) convertView.findViewById(R.id.price);
        final TextView price_old = (TextView) convertView.findViewById(R.id.price_old);
        final TextView discount = (TextView) convertView.findViewById(R.id.discount);

        ImageView img = (ImageView) convertView.findViewById(R.id.image);


        more=(Button) convertView.findViewById(R.id.play);
        //buy=(Button) convertView.findViewById(R.id.buy);
        final Product_list_geter hero = heroList.get(position);

more.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        pro_id=hero.gettv1();
//        pro_name=hero.gettv2();
//        pro_price=hero.gettv6();

   //send();
        //Toast.makeText(mCtx, "cart add", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mCtx.getApplicationContext(), Signle_product.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("p_id",hero.gettv1());
                intent.putExtra("name",hero.gettv2());
                intent.putExtra("description",hero.gettv3());
                intent.putExtra("img",hero.gettv4());

                intent.putExtra("price",hero.gettv6());
                  intent.putExtra("discount",hero.gettv7());
                mCtx.getApplicationContext().startActivity(intent);
    }
});
        name.setText(hero.gettv2());
        price.setText("\u20B9"+hero.gettv6());
        price_old.setText("\u20B9"+hero.gettv5());
        discount.setText(hero.gettv7()+" % OFF");
        Picasso.with(mCtx).load(hero.gettv4()).resize(260, 180).into(img);


//        buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(mCtx.getApplicationContext(), PayMentGateWay.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                intent.putExtra("RECHARGE_AMT",hero.gettv5());
//
//                mCtx.getApplicationContext().startActivity(intent);
//                 //Toast.makeText(mCtx, id1, Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;

    }

}