package se.miun.algi1701.dt031g.dialer;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dialButton = (Button) findViewById(R.id.dial);
        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DialActivity.class));
            }
        });

        Button callListButton = (Button) findViewById(R.id.call_list);
        callListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CallListActivity.class));
            }
        });

        Button settingsButton = (Button) findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        Button mapButton = (Button) findViewById(R.id.map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        Button aboutButton = (Button) findViewById(R.id.about);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this).setTitle("About").setMessage(
                        "This app is supposed to mimic the keypad on a phone. The app will consist of activities to:\n" +
                                "- Enter numbers to dial\n" +
                                "- See previously dialed numbers\n" +
                                "- Change the keypad settings\n" +
                                "Sho on a Map where previous calls are dialed from"


                )

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.ok, null)
                        .show();

            }
        });


    }

}
