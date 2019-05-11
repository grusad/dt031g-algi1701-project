package se.miun.algi1701.dt031g.dialer;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Set;

public class CallListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        newLoadList();

    }

    private void newLoadList(){

        CallDBHandler dbHandler = new CallDBHandler(this);
        Cursor cursor = dbHandler.getRecords();
        MyCursorAdapter adapter = new MyCursorAdapter(this, cursor);

        setListAdapter(adapter);


    }

    class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.call_entry, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView phone = view.findViewById(R.id.call_entry_phone);
            TextView position = view.findViewById(R.id.call_entry_position);
            TextView time = view.findViewById(R.id.call_entry_time);

            String phoneNumber = cursor.getString(1);
            String lat = cursor.getString(2);
            String lon = cursor.getString(3);
            String timeNumber = cursor.getString(4);

            phone.setText(phoneNumber);
            position.setText("[" + lat + ", " + lon + "]");
            time.setText(timeNumber);

        }
    }
}
