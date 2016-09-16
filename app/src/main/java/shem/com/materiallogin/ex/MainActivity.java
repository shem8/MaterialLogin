package shem.com.materiallogin.ex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void defaultView(View view) {
        startActivity(new Intent(this, DefaultActivity.class));
    }

    public void customizedView(View view) {
        startActivity(new Intent(this, CustomizeActivity.class));
    }

    public void customViews(View view) {
        startActivity(new Intent(this, CustomViewsActivity.class));
    }
}
