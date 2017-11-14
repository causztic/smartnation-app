package com.example.anweshabiswas.smartnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageButton startButton = (ImageButton)findViewById(R.id.librarybutton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startLibrary();
            }
        });
    }

    private void startLibrary() {
        Intent launchLibrary = new Intent(this, Main3ActivityLibrary.class);
        startActivity(launchLibrary);
    }
    }

