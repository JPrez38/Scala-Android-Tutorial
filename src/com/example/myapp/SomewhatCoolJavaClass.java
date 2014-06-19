package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SomewhatCoolJavaClass extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javaxml);

        final Button scalaButton = (Button)findViewById(R.id.scalaButton);
        scalaButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent scalaIntent = new Intent(v.getContext(),AwesomeScalaClass.class);
                                startActivityForResult(scalaIntent,0);
                    }
                }
        );

        final Button mainButton = (Button)findViewById(R.id.mainButton);
        mainButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainIntent = new Intent(v.getContext(),MyActivity.class);
                        startActivityForResult(mainIntent,0);
                    }
                }
        );

    }
}
