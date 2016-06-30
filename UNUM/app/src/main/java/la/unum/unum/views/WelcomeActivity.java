package la.unum.unum.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import la.unum.unum.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
    }

    public void connect_Instagram(View view) {
        Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show();
    }
}
