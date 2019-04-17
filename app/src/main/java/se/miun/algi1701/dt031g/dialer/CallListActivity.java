package se.miun.algi1701.dt031g.dialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

public class CallListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        loadList();
    }

    private void loadList(){
        TextView textView = (TextView) findViewById(R.id.numbers);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Set<String> numbers = sharedPref.getStringSet("numbers", null);

        if (numbers == null){
            textView.setText("No numbers are stored.");
        }

        else{
            for(String number : numbers){
                textView.append(number + "\n");
            }
        }
    }
}
