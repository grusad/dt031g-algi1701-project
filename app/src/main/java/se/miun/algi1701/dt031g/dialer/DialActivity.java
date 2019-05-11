package se.miun.algi1701.dt031g.dialer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DialActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private final int PERMISSION_REQUEST_PHONE_CALL = 2;
    private final int PERMISSION_FINE_LOCATION = 3;

    private Dialpad dialpad;
    private CallDBHandler dbHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialpad = new Dialpad(this);
        setContentView(dialpad);
        dbHandler = new CallDBHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton dialButton = (ImageButton) findViewById(R.id.dial_button);

        if(ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DialActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_PHONE_CALL);
        }

        if(ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DialActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
        }



        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                makeCall();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_item: {
                startActivity(new Intent(DialActivity.this, SettingsActivity.class));
                break;
            }
            case R.id.web_item: {
                Intent intent = new Intent(DialActivity.this, WebActivity.class);
                intent.putExtra("destinationFolder", Constants.EXTERNAL_STORAGE_PATH);
                intent.putExtra("url", Constants.DOWNLOAD_URL);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + Uri.encode(dialpad.getNumber())));

        String lat = "??";
        String lon = "??";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = Double.toString(location.getLatitude());
            lon = Double.toString(location.getLongitude());
        }


        dbHandler.put(dialpad.getNumber(), lat, lon);

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
                        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(DialActivity.this);
                        String path = sharedPreferences.getString("voice_sounds", null);
                        if(path.isEmpty()) return;
                        SoundPlayer.getInstance().loadSounds(path);
                    }
                    else{
                        Toast.makeText(this, R.string.no_dir_err, Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    // Permission denied!
                    Toast.makeText(this, R.string.no_read_ex_storage, Toast.LENGTH_SHORT).show();
                }

                break;

            }


            case PERMISSION_REQUEST_PHONE_CALL:{



                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted!
                }
                else{
                    // Permission denied!
                    Toast.makeText(this, R.string.call_err, Toast.LENGTH_SHORT).show();
                }

                break;

            }

            case PERMISSION_FINE_LOCATION: {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted!

                }
                else{
                    // Permission denied!
                    Toast.makeText(this, R.string.loc_err, Toast.LENGTH_SHORT).show();
                }

                break;
            }

        }

    }

    private boolean hasExternalFolder(){
        File file = new File(Constants.EXTERNAL_STORAGE_PATH);
        return file.exists() && file.isDirectory();
    }

}
