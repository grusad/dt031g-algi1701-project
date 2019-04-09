package se.miun.algi1701.dt031g.dialer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class DialActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private final int PERMISSION_REQUEST_PHONE_CALL = 2;

    private Dialpad dialpad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialpad = new Dialpad(this);
        setContentView(dialpad);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

        ImageButton dialButton = (ImageButton) findViewById(R.id.dial_button);

        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DialActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_PHONE_CALL);
                }
                else{
                    makeCall();
                }

            }
        });


    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + Uri.encode(dialpad.getNumber())));
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SoundPlayer.getInstance().destroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {



        switch (requestCode){
            case PERMISSION_REQUEST_READ_EXTERNAL_STORAGE:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted!

                    if(hasExternalFolder()){
                        SoundPlayer.getInstance().loadSounds(Environment.getExternalStorageDirectory() + "/Dialer/Voices/mamacita_us");
                    }
                    else{
                        Toast.makeText(this, "No directory with data exists", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    // Permission denied!
                    Toast.makeText(this, "Permission denied to read the external storage", Toast.LENGTH_SHORT).show();
                }

                break;

            }


            case PERMISSION_REQUEST_PHONE_CALL:{



                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted!
                    makeCall();
                }
                else{
                    // Permission denied!
                    Toast.makeText(this, "Permission denied to make call from phone", Toast.LENGTH_SHORT).show();
                }

                break;

            }

        }

    }

    private boolean hasExternalFolder(){
        File file = new File(Environment.getExternalStorageDirectory() + "/Dialer/Voices");
        return file.exists() && file.isDirectory();
    }

}
