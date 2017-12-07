package com.example.anweshabiswas.smartnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MeetingRoomActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_room);

        ImageButton startButton55 = (ImageButton)findViewById(R.id.button55);
        ImageButton startButton57 = (ImageButton)findViewById(R.id.button57);
        ImageButton startButton59 = (ImageButton)findViewById(R.id.button59);

        startButton55.setOnClickListener(this);
        startButton57.setOnClickListener(this);
        startButton59.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button55:
                start55();
                break;
            case R.id.button57:
                start57();
                break;
            case R.id.button59:
                start59();
                break;
            default:
                break;

        }
    }
    public static final String KEY="Block";
    private void start55() {
        Intent launch55 = new Intent(this, BlockActivity.class);
        launch55.putExtra(KEY,55);
        startActivity(launch55);
        return;
    }
    private void start57() {
        Intent launch57 = new Intent(this, BlockActivity.class);
        launch57.putExtra(KEY,57);
        startActivity(launch57);
        return;
    }
    private void start59() {
        Intent launch59 = new Intent(this, BlockActivity.class);
        launch59.putExtra(KEY,59);
        startActivity(launch59);
        return;
    }
}
