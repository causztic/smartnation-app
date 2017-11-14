package com.example.anweshabiswas.smartnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startButton = (ImageButton) findViewById(R.id.studybutton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startStudy();
            }
        });
    }

    private void startStudy() {
        Intent launchGame = new Intent(this, Main2Activity.class);
        startActivity(launchGame);
    }
}
