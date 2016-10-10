package com.zonesciences.flashcardanatomy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mUpperLimbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUpperLimbButton = (Button) findViewById(R.id.upperLimb);
    }

    public void openLowerLimb(View view){
        Intent intent = new Intent(this, LowerLimb.class);
        startActivity(intent);
    }
}
