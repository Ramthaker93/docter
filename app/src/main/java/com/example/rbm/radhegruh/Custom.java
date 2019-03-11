package com.example.rbm.radhegruh;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

class Custom  extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;
        private final int[] imageId;




        public Custom(Activity context,String[] web, int[] imageId) {

            super(context, R.layout.list_single, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;


        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.single, null, true);


            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);


            txtTitle.setText(web[position]);
            //Picasso.with(context).load(imageId[position]).into(imageView);
             imageView.setImageResource(imageId[position]);




            return rowView;
        }
    }

