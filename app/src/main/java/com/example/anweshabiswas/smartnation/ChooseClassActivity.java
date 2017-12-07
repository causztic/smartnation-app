package com.example.anweshabiswas.smartnation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ChooseClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RadioButton f01 = (RadioButton) findViewById(R.id.radioButton1);
        final RadioButton f02 = (RadioButton) findViewById(R.id.radioButton2);
        final RadioButton f03 = (RadioButton) findViewById(R.id.radioButton3);
        final RadioButton f04 = (RadioButton) findViewById(R.id.radioButton4);
        final RadioButton f05 = (RadioButton) findViewById(R.id.radioButton5);
        final RadioButton f06 = (RadioButton) findViewById(R.id.radioButton6);
        final RadioButton f07 = (RadioButton) findViewById(R.id.radioButton7);
        final RadioButton f08 = (RadioButton) findViewById(R.id.radioButton8);
        final RadioButton f09 = (RadioButton) findViewById(R.id.radioButton9);


        SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
        final SharedPreferences.Editor Ed=sp.edit();
        if (sp.getBoolean("my_first_time", true)) {

            sp.edit().putBoolean("my_first_time", false).commit();
        }

        final Button go = (Button) findViewById(R.id.DoneButton);
        go.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (f01.isChecked()) {
                    Ed.putString("Class",f01.getText().toString() );
                    Ed.commit();
                 }
                else if (f02.isChecked()) {
                    Ed.putString("Class","F02" );
                    Ed.commit();
                }
                else if (f03.isChecked()) {
                    Ed.putString("Class","F03" );
                    Ed.commit();
                }
                else if (f04.isChecked()) {
                    Ed.putString("Class","F04" );
                    Ed.commit();
                }
                else if (f05.isChecked()) {
                    Ed.putString("Class","F05" );
                    Ed.commit();
                }
                else if (f06.isChecked()) {
                    Ed.putString("Class","F06" );
                    Ed.commit();
                }
                else if (f07.isChecked()) {
                    Ed.putString("Class","F07" );
                    Ed.commit();
                }
                else if (f08.isChecked()) {
                    Ed.putString("Class","F08" );
                    Ed.commit();
                }
                else if (f09.isChecked()) {
                    Ed.putString("Class", "F09");
                    Ed.commit();
                }

                Intent launchGame = new Intent(ChooseClassActivity.this, FirstMenuMainActivity.class);
                startActivity(launchGame);
            }

        });

    }
}
