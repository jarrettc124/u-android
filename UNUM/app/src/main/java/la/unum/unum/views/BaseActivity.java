package la.unum.unum.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import la.unum.unum.R;
import la.unum.unum.utils.UiUtil;


public abstract class BaseActivity extends AppCompatActivity {
//    private ProgressDialog pd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setSpForApp();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setSpForApp();
    }
    public void setSpForApp() { UiUtil.setSpUnit(this, getSpForApp());
    }
    public float getSpForApp() {
        return UiUtil.getScreenWidth(this) / 400.0f;
    }


    public void HideKeyboard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
