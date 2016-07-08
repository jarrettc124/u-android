package la.unum.unum.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;
import la.unum.unum.R;
import la.unum.unum.utils.AppConstant;
import la.unum.unum.utils.HttpUtils;
import la.unum.unum.utils.MultiUser;
import la.unum.unum.utils.UiUtil;
import la.unum.unum.utils.widget.GifMovieView;

/**
 * Created by franklin-pierce on 01/07/16.
 */
public class LoginActivity extends BaseActivity {

    private static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    private static final String TOKENURL = "https://api.instagram.com/oauth/access_token";

    public static final String APIURL = "https://api.instagram.com/v1";

    public  String CALLBACKURL = "";
    private String client_id ="";
    private String client_secret = "";
    private String tokenURLString;
    private String authURLString;
    private String request_token;
    WebView webView;
    private String username;
    private String profile_picture;
    private String bio;
    private String full_name;
    private String accessTokenString;
    private String id;
    private int user_numposts;
    private int user_numFollowers;
    private int user_numFollowing;
    ImageButton imgbtn_back;
    GifMovieView gif_loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        client_id = "abdbf3d78cd44879a4e029aac620d890";
        client_secret = "e5bbe704694744de829102b7437ba865";
        CALLBACKURL = "https://unum-api.herokuapp.com/v1/login/instagram/callback";

        authURLString = AUTHURL + "?client_id=" + client_id + "&redirect_uri=" + CALLBACKURL + "&response_type=code&display=touch&scope=basic+likes+relationships";
        tokenURLString = TOKENURL + "?client_id=" + client_id + "&client_secret=" + client_secret + "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";

        gif_loader =   (GifMovieView) findViewById(R.id.gif_loader);
//        gif_loader.setMovieResource(R.drawable.loader);

        webView = (WebView) findViewById(R.id.web);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled( false );
        webView.getSettings().setCacheMode( WebSettings.LOAD_NO_CACHE );

        imgbtn_back = (ImageButton)findViewById(R.id.btn_login_back);

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(LoginActivity.this, description, Toast.LENGTH_SHORT).show();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("error")){
                    Toast.makeText(LoginActivity.this,"User Cancelled",Toast.LENGTH_LONG).show();
                    finish();
                    return true;
                }
                if (url.contains("success")){
                    Toast.makeText(LoginActivity.this,"Logged in as"+ username,Toast.LENGTH_LONG).show();
                    finish();
                    return true;
                }
                if (url.startsWith(CALLBACKURL)) {
                    System.out.println(url);
                    String parts[] = url.split("=");
                    for (int i = 0; i < parts.length; i++) {
                        String part = parts[i];
                        if (part.contains("code") && i < parts.length - 1) {
                            request_token = parts[i + 1];  //This is your request token.
                            break;
                        }
                    }

                    new AsyncTask<Void,Void,Void>(){
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }
                        @Override
                        protected Void doInBackground(Void... params) {
                            try
                            {
                                URL url = new URL(tokenURLString);
                                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                                httpsURLConnection.setRequestMethod("POST");
                                httpsURLConnection.setDoInput(true);
                                httpsURLConnection.setDoOutput(true);
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
                                outputStreamWriter.write("client_id="+client_id+
                                        "&client_secret="+ client_secret +
                                        "&grant_type=authorization_code" +
                                        "&redirect_uri="+CALLBACKURL+
                                        "&code=" + request_token);

                                outputStreamWriter.flush();
                                String response = streamToString(httpsURLConnection.getInputStream());
                                JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                                profile_picture = jsonObject.getJSONObject("user").getString("profile_picture");
                                bio = jsonObject.getJSONObject("user").getString("bio");
                                full_name = jsonObject.getJSONObject("user").getString("full_name");
                                accessTokenString = jsonObject.getString("access_token"); //Here is your ACCESS TOKEN
                                id = jsonObject.getJSONObject("user").getString("id");
                                username = jsonObject.getJSONObject("user").getString("username");
                                //This is how you can get the user info. You can explore the JSON sent by Instagram as well to know what info you got in a response


                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(LoginActivity.this,full_name + ": "+ username, Toast.LENGTH_LONG).show();
                            fetchUserInfo_Instagram();
                        }
                    }.execute();
                    return true;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                gif_loader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                gif_loader.setVisibility(View.GONE);

            }
        });
        webView.loadUrl(authURLString);
        UiUtil.applyImageButtonEffect(imgbtn_back, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void fetchUserInfo_Instagram(){
        gif_loader.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(APIURL + "/users/" + id
                            + "/?access_token=" + accessTokenString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url
                            .openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    String response = streamToString(urlConnection
                            .getInputStream());
                    System.out.println(response);
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response)
                            .nextValue();

                    JSONObject data_obj = jsonObj.getJSONObject("data");
                    JSONObject counts_obj = data_obj.getJSONObject("counts");
                    user_numposts = counts_obj.getInt("media");
                    user_numFollowing = counts_obj.getInt("follows");
                    user_numFollowers = counts_obj.getInt("followed_by");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gif_loader.setVisibility(View.GONE);
                        }
                    });
                    login_WithuserInfo();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private String streamToString(InputStream inputStream) {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }
    public void login_WithuserInfo(){
        RequestParams rp = new RequestParams();
        rp.put("type","android");
        rp.put("instagramId",id);
        rp.put("instagramToken",accessTokenString);
        rp.put("username",username);
        rp.put("fullName",full_name);
//        rp.put("numPosts", user_numposts);
//        rp.put("numFollowers", user_numFollowers);
//        rp.put("numFollowing", user_numFollowing);
        rp.put("numPosts", Integer.valueOf(user_numposts));
        rp.put("numFollowers", Integer.valueOf(user_numFollowers));
        rp.put("numFollowing", Integer.valueOf(user_numFollowing));
        rp.setUseJsonStreamer(true);
        HttpUtils.post(AppConstant.LoginAPI, rp, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif_loader.setVisibility(View.VISIBLE);
                    }
                });
                Log.d("Tag","Start");
            }
            @Override
            public void onSuccess(int status, Header[] headers, JSONObject response){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif_loader.setVisibility(View.GONE);
                    }
                });
                try{
                    Log.d("Tag","Success");
                    JSONObject serverResp = new JSONObject(response.toString());
                    MultiUser.User user =new MultiUser.User();
                    user.n_numPosts = serverResp.getInt("numPosts");
                    user.n_numFollowers = serverResp.getInt("numFollowers");
                    user.n_numFollowing = serverResp.getInt("numFollowing");
                    user.str_username = serverResp.getString("username");
                    user.str_fullName = serverResp.getString("fullName");
                    user.str_instagramId = serverResp.getString("instagramId");
                    user.str_instagramToken = serverResp.getString("instagramToken");
                    user.str_authToken = serverResp.getString("authToken");
                    user.str_braintreeCustomerId = serverResp.getString("braintreeCustomerId");
                    user.b_liveGridEnabled = serverResp.getBoolean("liveGridEnabled");
                    user.str_userid = serverResp.getString("_id");
                    MultiUser.addUser(user);
                    goto_DraftActivity();
                }catch (JSONException e){
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                Log.d("Tag","failure");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif_loader.setVisibility(View.GONE);
                    }
                });
                Toast.makeText(LoginActivity.this,"User not found", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable t){
                Log.d("Tag","failure");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif_loader.setVisibility(View.GONE);
                    }
                });
                Toast.makeText(LoginActivity.this, string,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goto_DraftActivity(){
        Intent intent = new Intent(LoginActivity.this, DraftActivity.class);
        startActivity(intent);
        finish();
    }
}