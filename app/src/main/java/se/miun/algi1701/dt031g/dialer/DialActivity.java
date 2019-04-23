package se.miun.algi1701.dt031g.dialer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    private Dialpad dialpad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialpad = new Dialpad(this);
        setContentView(dialpad);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("store_numbers", true)){
           storeNumber();
        }

        startActivity(intent);

    }

    private void storeNumber(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> numbers = sharedPref.getStringSet("numbers", null);
        if(numbers == null){
            numbers = new HashSet<>();
        }

        numbers.add(dialpad.getNumber());
        editor.putStringSet("numbers", numbers);
        editor.apply();
        editor.commit();
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
                    makeCall();
                }
                else{
                    // Permission denied!
                    Toast.makeText(this, R.string.call_err, Toast.LENGTH_SHORT).show();
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
