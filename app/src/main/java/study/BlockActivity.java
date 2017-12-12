package study;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.anweshabiswas.smartnation.R;

import redis.RedisConnect;
import services.MyService;

public class BlockActivity extends AppCompatActivity {

    TextView rec3;
    TextView rec6;
    TextView quiet;
    TextView group4;
    TextView group7;
    CheckBox recc;
    CheckBox quietc;
    CheckBox groupc;
    SharedPreferences preference;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        rec3 = (TextView) findViewById(R.id.Recreation_lvl3);
        rec6 = (TextView) findViewById(R.id.Recreation_lvl6);
        quiet = (TextView) findViewById(R.id.Quiet_Study_lvl2);
        group4 = (TextView) findViewById(R.id.Group_Study_lvl4);
        group7 = (TextView) findViewById(R.id.Group_Study_lvl7);
        recc = (CheckBox) findViewById(R.id.recCheck);
        quietc = (CheckBox) findViewById(R.id.quietCheck);
        groupc = (CheckBox) findViewById(R.id.groupCheck);
        preference = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = preference.edit();

        Intent intent = getIntent();
        int m = intent.getIntExtra(MeetingRoomActivity.KEY, 0);
        final String message = m + "";

        boolean isRecChecked = preference.getBoolean(message + ":rec", false);
        boolean isGroupChecked = preference.getBoolean(message + ":group", false);
        boolean isQuietChecked = preference.getBoolean(message + ":quiet", false);

        recc.setChecked(isRecChecked);
        quietc.setChecked(isQuietChecked);
        groupc.setChecked(isGroupChecked);
        if (!message.equalsIgnoreCase("0")) {

            quietc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) {
                        compoundButton.setChecked(false);
                        editor.putBoolean(message + ":quiet", false);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), MyService.class);
                        startService(i);


                    } else {
                        compoundButton.setChecked(true);
                        editor.putBoolean(message + ":quiet", true);
                        editor.commit();
                        //subscribe to pub sub
                        Intent i = new Intent(getApplicationContext(), MyService.class);
                        // i.putExtra("type",message+":quiet");
                        startService(i);

                    }


                }

            });
            RedisConnect rc;

            switch (m) {
                case 55:
                    rc = new RedisConnect(quiet, "55:quiet");
                    break;
                case 57:
                    rc = new RedisConnect(quiet, "57:quiet");
                    break;
                case 59:
                    rc = new RedisConnect(quiet, "59:quiet");
                    break;
                default:
                    break;


            }
        }
    }
}
