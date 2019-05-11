package se.miun.algi1701.dt031g.dialer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CallDBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "DialerDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Numbers";

    public CallDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "number TEXT NOT NULL, "
                + "lat TEXT, "
                + "lon TEXT, "
                + "time DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    public void put(String number, String lat, String lon){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("lat", lat);
        values.put("lon", lon);
        database.insert(TABLE_NAME, null, values);

        close();
    }

    public Cursor getRecords(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT _id, number, lat, lon, time FROM Numbers;";
        cursor = database.rawQuery(query, null);

        return cursor;
    }
}
