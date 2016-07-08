package la.unum.unum.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import la.unum.unum.R;

public class WelcomeActivity extends BaseActivity {

    Button btn_connect_instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        getSupportActionBar().hide();
        btn_connect_instagram = (Button)findViewById(R.id.connect_instagram);
        btn_connect_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_LoginActivity();
            }
        });
    }

    public void goto_LoginActivity(){
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
