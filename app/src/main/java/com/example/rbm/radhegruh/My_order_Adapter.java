package com.example.rbm.radhegruh;

import android.content.Context;
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

import java.util.List;

/**
 * Created by Rohit Kumar on 2/6/2018.
 */

class My_order_Adapter extends ArrayAdapter<My_order_geter> {

    //the hero list that will be displayed
    private List<My_order_geter> heroList;
String id1=null,name1=null,birthday1=null,sex1=null,phone1=null,blood_group1=null,address1=null,email1=null;
    //the context object
    private Context mCtx;
//    private Context context;
Button play,more1,viewr;
    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public My_order_Adapter(List<My_order_geter> heroList, Context mCtx) {
        super(mCtx, R.layout.my_order_single_item, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;

    }

    //this method will return the list item
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);
TextView name,price,selling_price,discount;
        //creating a view with our xml layout
        convertView= inflater.inflate(R.layout.my_order_single_item, null, true);

          name = (TextView) convertView.findViewById(R.id.name);
      price = (TextView) convertView.findViewById(R.id.price);
        selling_price = (TextView) convertView.findViewById(R.id.selling_price);
        discount = (TextView) convertView.findViewById(R.id.discount);

        // final TextView des = convertView.findViewById(R.id.des);
        final My_order_geter hero = heroList.get(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        name.setText(hero.gettv2());
        price.setText("\u20B9"+hero.gettv5());
        selling_price.setText("\u20B9"+hero.gettv6());
        discount.setText(hero.gettv7()+" %");
//        viewr=(Button) convertView.findViewById(R.id.view);
//     more1=(Button) convertView.findViewById(R.id.more1);
//
//
//        more1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             //   Toast.makeText(mCtx, hero.getid(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mCtx.getApplicationContext(), Batch.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("course_name",hero.gettv1());
//                intent.putExtra("course_id",hero.getid());
//                intent.putExtra("price",hero.gettv2());
//                intent.putExtra("value","buy");
//                mCtx.getApplicationContext().startActivity(intent);
//            }
//        });

//        viewr.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//        Intent intent = new Intent(mCtx.getApplicationContext(), Recipt.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//      intent.putExtra("course_id",hero.getid());
//      intent.putExtra("course_name",hero.gettv1());
//        // Toast.makeText(mCtx, hero.gettv6(), Toast.LENGTH_SHORT).show();
//        mCtx.getApplicationContext().startActivity(intent);
//    }
//});

//        name.setText(hero.gettv1());
//        price.setText(hero.gettv2());
//        batchname.setText(hero.gettv3());
//        duration.setText(hero.gettv4());
       // des.setText(hero.gettv3());
        Picasso.with(mCtx).load(hero.gettv4()).resize(260, 180).into(img);
        return convertView;

    }
}