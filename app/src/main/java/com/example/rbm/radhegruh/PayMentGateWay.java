package com.example.rbm.radhegruh;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by varun Kumar on 23/1/16.
 */
public class PayMentGateWay extends Activity {






    private ArrayList<String> post_val = new ArrayList<String>();
    private String post_Data="";
    WebView webView ;
    final Activity activity = this;
    private String tag = "PayMentGateWay";
    private String hash,hashSequence;
    ProgressDialog progressDialog ;

  //  String merchant_key="OVdW8XAE"; // live
    //String salt="yMiMTXS61Z"; // live

    String merchant_key="kYz2vV"; // test
   String salt="zhoXe53j"; // test
	 	String action1 ="";
    String base_url="https://test.payu.in";
     //https://secure.payu.in
    //String base_url="https://secure.payu.in";//
    int error=0;
    String hashString="";
    Map<String,String> params;
    String txnid ="";
    private ProgressDialog loading;
    String SUCCESS_URL = "https://www.payumoney.com/mobileapp/payumoney/success.php" ; // failed
    String FAILED_URL = "https://www.payumoney.com/mobileapp/payumoney/failure.php" ;




    // user profile.............
    public static final String JSON_ARRAY = "result";
    public static final String KEY_name = "name";
    public static final String KEY_lastname = "last_name";
    public static final String KEY_address = "address1";
    public static final String KEY_phone = "phone";
    String user_name="";
    //user profile...end........

    Handler mHandler = new Handler();


    static String getFirstName, getNumber, getEmailAddress, getRechargeAmt,product_id,course_id,chapter_id,product_id1,check;


//    ProgressDialog pDialog ;
   String key_product_id = "product_id";
//    String key_chapter_id = "chapter_id";
//    String key_product_id = "product_id";
//    String key_user_id = "email";
    SessionManager session;
    String email;
    //String send1_url = "http://sunrisewebsolution.in/exo_india_education/create_payment.php";





    String key_user_id1 = "email";
    String key_amount="amount";

    String key_firstname="firstname";
    String key_phone="phone";

    String product_payment_url =Splash_Screen.baseurl+"medical/product_payment.php";




    @SuppressLint({"JavascriptInterface", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(activity);

        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        webView = new WebView(this);
        setContentView(webView);

        //session......
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        //session....end...

        getuserprofile();


        Intent oIntent  = getIntent();
       product_id=oIntent.getExtras().getString("product_id");
      getRechargeAmt  = oIntent.getExtras().getString("RECHARGE_AMT");
      getEmailAddress = email;
      //  check    = oIntent.getExtras().getString("type");




        //post_val = getIntent().getStringArrayListExtra("post_val");
        //Log.d(tag, "post_val: "+post_val);
        params= new HashMap<String,String>();
        params.put("key", merchant_key);

        params.put("amount", getRechargeAmt);
        params.put("firstname", getFirstName);
        params.put("email", getEmailAddress);
        params.put("phone", getNumber);
        params.put("productinfo", "Recharge Wallet");
        params.put("surl", SUCCESS_URL);
        params.put("furl", FAILED_URL);
        params.put("service_provider", "payu_paisa");
        params.put("lastname", "");
        params.put("address1", "");
        params.put("address2", "");
        params.put("city", "");
        params.put("state", "");
        params.put("country", "");
        params.put("zipcode", "");
        params.put("udf1", "");
        params.put("udf2", "");
        params.put("udf3", "");
        params.put("udf4", "");
        params.put("udf5", "");
        params.put("pg", "");

		/*for(int i = 0;i<post_val.size();){
			params.put(post_val.get(i), post_val.get(i+1));

			i+=2;
		}*/


        if(empty(params.get("txnid"))){
            Random rand = new Random();
            String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
            txnid=hashCal("SHA-256",rndm).substring(0,20);
            params.put("txnid", txnid);
        }
        else
            txnid=params.get("txnid");
        //String udf2 = txnid;
        String txn="abcd";
        hash="";
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        if(empty(params.get("hash")) && params.size()>0)
        {
            if( empty(params.get("key"))
                    || empty(params.get("txnid"))
                    || empty(params.get("amount"))
                    || empty(params.get("firstname"))
                    || empty(params.get("email"))
                    || empty(params.get("phone"))
                    || empty(params.get("productinfo"))
                    || empty(params.get("surl"))
                    || empty(params.get("furl"))
                    || empty(params.get("service_provider"))

                    ){
                error=1;
            }
            else{
                String[] hashVarSeq=hashSequence.split("\\|");

                for(String part : hashVarSeq)
                {
                    hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
                    hashString=hashString.concat("|");
                }
                hashString=hashString.concat(salt);


                hash=hashCal("SHA-512",hashString);
                action1=base_url.concat("/_payment");
            }
        }
        else if(!empty(params.get("hash")))
        {
            hash=params.get("hash");
            action1=base_url.concat("/_payment");
        }

        webView.setWebViewClient(new MyWebViewClient(){

            public void onPageFinished(WebView view, final String url) {
                progressDialog.dismiss();
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //make sure dialog is showing
                if(! progressDialog.isShowing()){
                    progressDialog.show();
                }
            }


			/*@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "SslError! " +  error, Toast.LENGTH_SHORT).show();
				 handler.proceed();
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Page Started! " + url, Toast.LENGTH_SHORT).show();
				if(url.startsWith(SUCCESS_URL)){
					Toast

					.makeText(activity, "Payment Successful! " + url, Toast.LENGTH_SHORT).show();
					 Intent intent = new Intent(PayMentGateWay.this, MainActivity1.class);
					    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
					    startActivity(intent);
					    finish();
					    return false;
				}else if(url.startsWith(FAILED_URL)){
					Toast.makeText(activity, "Payment Failed! " + url, Toast.LENGTH_SHORT).show();
				    return false;
				}else if(url.startsWith("http")){
					return true;
				}
				//return super.shouldOverrideUrlLoading(view, url);
				return false;
			}*/


        });


        webView.setVisibility(0);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearHistory();
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setLoadWithOverviewMode(false);

        //webView.addJavascriptInterface(new PayUJavaScriptInterface(getApplicationContext()), "PayUMoney");
        webView.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("key",merchant_key);
        mapParams.put("hash",PayMentGateWay.this.hash);
        mapParams.put("txnid",(empty(PayMentGateWay.this.params.get("txnid"))) ? "" : PayMentGateWay.this.params.get("txnid"));
        Log.d(tag, "txnid: "+PayMentGateWay.this.params.get("txnid"));
        mapParams.put("service_provider","payu_paisa");

        mapParams.put("amount",(empty(PayMentGateWay.this.params.get("amount"))) ? "" : PayMentGateWay.this.params.get("amount"));
        mapParams.put("firstname",(empty(PayMentGateWay.this.params.get("firstname"))) ? "" : PayMentGateWay.this.params.get("firstname"));
        mapParams.put("email",(empty(PayMentGateWay.this.params.get("email"))) ? "" : PayMentGateWay.this.params.get("email"));
        mapParams.put("phone",(empty(PayMentGateWay.this.params.get("phone"))) ? "" : PayMentGateWay.this.params.get("phone"));

        mapParams.put("productinfo",(empty(PayMentGateWay.this.params.get("productinfo"))) ? "" : PayMentGateWay.this.params.get("productinfo"));
        mapParams.put("surl",(empty(PayMentGateWay.this.params.get("surl"))) ? "" : PayMentGateWay.this.params.get("surl"));
        mapParams.put("furl",(empty(PayMentGateWay.this.params.get("furl"))) ? "" : PayMentGateWay.this.params.get("furl"));
        mapParams.put("lastname",(empty(PayMentGateWay.this.params.get("lastname"))) ? "" : PayMentGateWay.this.params.get("lastname"));

        mapParams.put("address1",(empty(PayMentGateWay.this.params.get("address1"))) ? "" : PayMentGateWay.this.params.get("address1"));
        mapParams.put("address2",(empty(PayMentGateWay.this.params.get("address2"))) ? "" : PayMentGateWay.this.params.get("address2"));
        mapParams.put("city",(empty(PayMentGateWay.this.params.get("city"))) ? "" : PayMentGateWay.this.params.get("city"));
        mapParams.put("state",(empty(PayMentGateWay.this.params.get("state"))) ? "" : PayMentGateWay.this.params.get("state"));

        mapParams.put("country",(empty(PayMentGateWay.this.params.get("country"))) ? "" : PayMentGateWay.this.params.get("country"));
        mapParams.put("zipcode",(empty(PayMentGateWay.this.params.get("zipcode"))) ? "" : PayMentGateWay.this.params.get("zipcode"));
        mapParams.put("udf1",(empty(PayMentGateWay.this.params.get("udf1"))) ? "" : PayMentGateWay.this.params.get("udf1"));
        mapParams.put("udf2",(empty(PayMentGateWay.this.params.get("udf2"))) ? "" : PayMentGateWay.this.params.get("udf2"));

        mapParams.put("udf3",(empty(PayMentGateWay.this.params.get("udf3"))) ? "" : PayMentGateWay.this.params.get("udf3"));
        mapParams.put("udf4",(empty(PayMentGateWay.this.params.get("udf4"))) ? "" : PayMentGateWay.this.params.get("udf4"));
        mapParams.put("udf5",(empty(PayMentGateWay.this.params.get("udf5"))) ? "" : PayMentGateWay.this.params.get("udf5"));
        mapParams.put("pg",(empty(PayMentGateWay.this.params.get("pg"))) ? "" : PayMentGateWay.this.params.get("pg"));
        webview_ClientPost(webView, action1, mapParams.entrySet());
       // payment_create();
    }

	/*public class PayUJavaScriptInterface {

		@JavascriptInterface
        public void success(long id, final String paymentId) {
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler = null;

                    new PostRechargeData().execute();

            		Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from PayUJavaScriptInterface" ,Toast.LENGTH_LONG).show();

                }
            });
        }
	}*/


    private final class PayUJavaScriptInterface {

        PayUJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        @JavascriptInterface
        public void success(long id, final String paymentId) {
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler = null;

	                    /*Intent intent = new Intent();
	                    intent.putExtra(Constants.RESULT, "success");
	                    intent.putExtra(Constants.PAYMENT_ID, paymentId);
	                    setResult(RESULT_OK, intent);
	                    finish();*/
                   // new PostRechargeData().execute();

                    payment_create();
                    Toast.makeText(getApplicationContext(), "Successfully payment", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), " payment  hhh", Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void failure(final String id, String error) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //cancelPayment();
                    Intent it=new Intent(getApplicationContext(),Home.class);
                    startActivity(it);
                    Toast.makeText(getApplicationContext(),"Cancel payment" , Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void failure() {
            failure("");
        }

        @JavascriptInterface
        public void failure(final String params) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Intent it=new Intent(getApplicationContext(),Home.class);
                    startActivity(it);
	                    /*Intent intent = new Intent();
	                    intent.putExtra(Constants.RESULT, params);
	                    setResult(RESULT_CANCELED, intent);
	                    finish();*/
                    Toast.makeText(getApplicationContext(),"Failed payment" , Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    public void webview_ClientPost(WebView webView, String url, Collection< Map.Entry<String, String>> postData){
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));
        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format("<input name='%s' type='hidden' value='%s' />", item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");
        Log.d(tag, "webview_ClientPost called");

        //setup and load the progress bar
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading. Please wait...");
        webView.loadData(sb.toString(), "text/html", "utf-8");
    }


    public void success(long id, final String paymentId) {

        mHandler.post(new Runnable() {
            public void run() {
                mHandler = null;
               // payment_create();
              //  new PostRechargeData().execute();

                Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from Success Function" , Toast.LENGTH_LONG).show();

            }
        });
    }


    public boolean empty(String s)
    {
        if(s== null || s.trim().equals(""))
            return true;
        else
            return false;
    }

    public String hashCal(String type, String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();



            for (int i=0;i<messageDigest.length;i++) {
                String hex= Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }

        }catch(NoSuchAlgorithmException nsae){ }

        return hexString.toString();


    }

    //String SUCCESS_URL = "https://pay.in/sccussful" ; // failed
    //String FAILED_URL = "https://pay.in/failed" ;
    //override the override loading method for the webview client
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

        	/*if(url.contains("response.php") || url.equalsIgnoreCase(SUCCESS_URL)){

        		new PostRechargeData().execute();

        		Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from webview" ,Toast.LENGTH_LONG).show();

                return false;
        	}else  */if(url.startsWith("http")){
                //Toast.makeText(getApplicationContext(),url ,Toast.LENGTH_LONG).show();
                progressDialog.show();
                view.loadUrl(url);
                System.out.println("myresult "+url);
                //return true;
            } else {
                return false;
            }

            return true;
        }
    }

    /******************************************* send record to back end ******************************************/
//    class PostRechargeData extends AsyncTask<String, String, String>
//    {
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(PayMentGateWay.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//
//        }
//        protected String doInBackground(String... args)
//        {
//            String strStatus = null;
//            ProfileSessionManager ProSessionManager = new ProfileSessionManager(PayMentGateWay.this);
//
//            String getUserid   = ProSessionManager.getSpeculatorId();
//            String getSpeculationId  = "0";
//            String rechargeAmt = getRechargeAmt;
//            String postAction = "1";
//            //http://speculometer.com/webService/stockApp/speculationMoneyreports.php?
//            //access_token=ISOFTINCstockAppCheckDevelop&speculator=1&speculation=&amount=1000&action=1
//            ServiceHandler sh = new ServiceHandler();
//            String upLoadServerUri = ServiceList.payment_money_url+"speculator="+getUserid+"&speculation="+getSpeculationId+"&amount="+rechargeAmt+"&action="+postAction;
//
//            try{
//                String jsonStr = sh.makeServiceCall(upLoadServerUri, ServiceHandler.POST);
//                JSONObject jsonObj  = new JSONObject(jsonStr);
//
//                JSONObject jobjDoc = jsonObj.optJSONObject("document");
//                JSONObject jobjRes = jobjDoc.optJSONObject("response");
//
//                strStatus   = jobjRes.getString("status");
//                //strMessage  = jobjRes.getString("message");
//                String strUserId = jobjRes.getString("user_id");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return strStatus;
//        }
//
//        protected void onPostExecute(final String strStatus)
//        {
//
//            runOnUiThread(new Runnable()
//            {
//                public void run()
//                {
//                    pDialog.dismiss();
//                    if(strStatus != null && strStatus.equalsIgnoreCase("0")){
//                        Toast.makeText(getApplicationContext(),"Your recharge amount not added in wallet." ,Toast.LENGTH_LONG).show();
//                    }else if(strStatus != null && strStatus.equalsIgnoreCase("1")){
//
//                        Toast.makeText(getApplicationContext(),"Your recharge amount added in wallet." ,Toast.LENGTH_LONG).show();
//                    }
//                    Intent intent = new Intent(activity, MainActivity1.class);
//                    startActivity(intent);
//                }
//            });
//
//        }
//    }


    /******************************************* closed send record to back end ************************************/

//    public void payment_create1() {
//        {
//            loading = ProgressDialog.show(PayMentGateWay.this, "Please wait...", "Fetching...", false, false);
//            try {
//                StringRequest rs = new StringRequest(Request.Method.POST, send1_url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if(response.trim().equals("success")){
//
//                            loading.dismiss();
//                        }else{
//                            Toast.makeText(PayMentGateWay.this,response, Toast.LENGTH_LONG).show();
//                            loading.dismiss();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Toast.makeText(PayMentGateWay.this, "Check internet connection", Toast.LENGTH_SHORT).show();
//                        loading.dismiss();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String, String> hs = new HashMap<>();
//                        hs.put(key_course_id,  course_id);
////                        hs.put(key_chapter_id,  chapter_id);
////                        hs.put(key_product_id,  product_id);
//                        hs.put(key_user_id,   getEmailAddress);
//
//
//
//                        return hs;
//
//                    }
//                };
//                RequestQueue rqt = Volley.newRequestQueue(this);
//                rqt.add(rs);
//            } catch (Exception ex) {
//                Toast.makeText(PayMentGateWay.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }}

    public void payment_create() {
        {
           // Toast.makeText(this, "payment method", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, product_id1, Toast.LENGTH_SHORT).show();
//
//            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        loading = ProgressDialog.show(PayMentGateWay.this, "Please wait...", "Fetching...", false, false);
            try {
                StringRequest rs = new StringRequest(Request.Method.POST, product_payment_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PayMentGateWay.this,response, Toast.LENGTH_LONG).show();
                       // if(response.trim().matches("successfully\\b")){
                            Toast.makeText(PayMentGateWay.this,response, Toast.LENGTH_LONG).show();
                           loading.dismiss();
                            Intent it=new Intent(getApplication(),Home.class);
                            startActivity(it);
                       // }else{
                          //  Toast.makeText(PayMentGateWay.this,response, Toast.LENGTH_LONG).show();
                         //  loading.dismiss();
                       // }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PayMentGateWay.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                     //  loading.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hs = new HashMap<>();
                        hs.put(key_product_id,  product_id);
//                        hs.put(key_type,  "0");
                        hs.put(key_user_id1,   email);
                        // hs.put(key_txnid, params.get("txnid"));
                        hs.put(key_firstname, params.get("firstname"));
                       hs.put(key_amount, params.get("amount"));
                        hs.put(key_phone, params.get("phone"));
                      //  hs.put(key_productinfo, params.get("productinfo"));
                        return hs;

                    }
                };
                RequestQueue rqt = Volley.newRequestQueue(this);
                rqt.add(rs);
            } catch (Exception ex) {
                Toast.makeText(PayMentGateWay.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }}



    //  user profile fetch..................
    private void getuserprofile() {


        // loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "http://sharmacommunications.com/medical/user_profile.php?email="+email;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PayMentGateWay.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        String name="";
        String phone="";
        String lastname="";
        String address= "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(KEY_name);
            lastname = collegeData.getString(KEY_lastname);
            phone = collegeData.getString(KEY_phone);
            address = collegeData.getString(KEY_address);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        getFirstName=name;
        getNumber=phone;
        getEmailAddress=address;

    }
}

//   user profile end...................

