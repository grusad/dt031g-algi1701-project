package se.miun.algi1701.dt031g.dialer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DialActivity extends AppCompatActivity {

    private Dialpad dialpad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialpad = new Dialpad(this);
        setContentView(dialpad);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SoundPlayer.getInstance(this).destroy();

    }
}
