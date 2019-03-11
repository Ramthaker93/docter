package com.example.rbm.radhegruh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

public class Sub_cat_grid_adapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final String[] Imageid;


    public Sub_cat_grid_adapter(Context c,String[] web,String[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.vertical2, null);
            TextView textView = (TextView) grid.findViewById(R.id.gg);
            TextView price1 = (TextView) grid.findViewById(R.id.price);
            ImageView imageView = (ImageView)grid.findViewById(R.id.scroll_image_view);
            textView.setText(web[position]);
            price1.setVisibility(View.INVISIBLE);
           // price1.setText("\u20B9"+price[position]);
            Picasso.with(mContext).load(Imageid[position]).resize(260, 180).into(imageView);
            //  imageView.setImageResource();
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}