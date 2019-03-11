package com.example.rbm.radhegruh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

class Product_grid_adapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final String[] Imageid;
    private final String[] sell_price;
    private final String[] price;
    private final String[] product_discount;
    private final String[] pro_id;
    private final String[] pro_des;
    String wishlist_product_id;
    public static final String KEY_pro_id = "product_id";
    public static final String KEY_email = "email";
    SessionManager session;
    String name,email=null;
//    private final String[] wish_item;
    public Product_grid_adapter(Context c,String[] web,String[] Imageid,String[] sell_price,String[] price,String[] product_discount,String[] pro_id,String[] pro_des) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.sell_price=sell_price;
        this.price=price;
        this.product_discount=product_discount;
        this.pro_id=pro_id;
        this.pro_des=pro_des;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.product_grid_single_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.gg);
            TextView price1 = (TextView) grid.findViewById(R.id.selling_price);
            TextView del_price = (TextView) grid.findViewById(R.id.price);
            final TextView discount=(TextView) grid.findViewById(R.id.discount);
            final ImageView imageView = (ImageView)grid.findViewById(R.id.scroll_image_view);
            ImageButton wish=(ImageButton) grid.findViewById(R.id.wish);
            wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(mContext, "wish", Toast.LENGTH_SHORT).show();
                    wishlist_product_id=pro_id[position];
                    addwish();
                }
            });
            textView.setText(web[position]);
            price1.setText("\u20B9"+sell_price[position]);
            del_price.setText(price[position]);
            discount.setText(product_discount[position]+"% off");
            Picasso.with(mContext).load(Imageid[position]).resize(260, 180).into(imageView);
            //  imageView.setImageResource();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(), Signle_product.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("p_id",pro_id[position]);
                    intent.putExtra("name",web[position]);
                    intent.putExtra("description",pro_des[position]);
                    intent.putExtra("img",Imageid[position]);

                    intent.putExtra("price",sell_price[position]);
                    intent.putExtra("discount",product_discount[position]);
                    mContext.getApplicationContext().startActivity(intent);
                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
    public void addwish() {
        {
            session = new SessionManager(mContext.getApplicationContext());
//
//        //session.checkLogin();
//// get user data from
            HashMap<String, String> user = session.getUserDetails();
//
//
//        // name
            name = user.get(SessionManager.KEY_NAME);
            email = user.get(SessionManager.KEY_EMAIL);
          //  loading = ProgressDialog.show(Signle_product.this, "Please wait...", "Fetching...", false, false);
            try {
                StringRequest rs = new StringRequest(Request.Method.POST, Splash_Screen.baseurl+"medical/add_wishlist.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                       // loading.dismiss();



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Check internet connection", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(product_list.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                      //  loading.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hs = new HashMap<>();
                        hs.put(KEY_pro_id, wishlist_product_id);

                        hs.put(KEY_email,  email);

                        return hs;

                    }
                };
                RequestQueue rqt = Volley.newRequestQueue(mContext);
                rqt.add(rs);
            } catch (Exception ex) {
                Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(product_list.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                //loading.dismiss();
            }
        }
    }

}