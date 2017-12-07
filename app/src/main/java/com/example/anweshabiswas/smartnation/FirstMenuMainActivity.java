package com.example.anweshabiswas.smartnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.content.Intent;

public class FirstMenuMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu_main);

        ImageButton StudyButton = (ImageButton) findViewById(R.id.studybutton);
        StudyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startStudy();
            }
        });

        ImageButton EatButton = (ImageButton) findViewById(R.id.eatbutton);
        EatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startEat();
            }
        });
        Intent i= new Intent(getApplicationContext(), MyService.class);
        startService(i);
       // RedisConnect rc=new RedisConnect();
    }

    private void startStudy() {
        Intent launchGame = new Intent(this, StudyMainActivity.class);
        startActivity(launchGame);
    }

    private void startEat()
    {
        Intent launchGame = new Intent(this, FoodActivity.class);
        startActivity(launchGame);

    }

}
