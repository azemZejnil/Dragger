package com.example.bodhi64.dragger;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView thug, cap, ears;
    Intent returnIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        thug=(ImageView)findViewById(R.id.thug);
        cap=(ImageView)findViewById(R.id.cap);
        ears=(ImageView)findViewById(R.id.ears);

        thug.setOnClickListener(this);
        cap.setOnClickListener(this);
        ears.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cap:
                 returnIntent = new Intent();
                returnIntent.putExtra("result","cap");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            case R.id.thug:
                 returnIntent = new Intent();
                returnIntent.putExtra("result","thug");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            case R.id.ears:
                 returnIntent = new Intent();
                returnIntent.putExtra("result","ears");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
        }
    }
}
