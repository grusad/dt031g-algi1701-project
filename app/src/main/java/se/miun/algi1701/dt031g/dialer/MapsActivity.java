package se.miun.algi1701.dt031g.dialer;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera



        CallDBHandler dbHandler = new CallDBHandler(this);
        Cursor cursor = dbHandler.getRecords();

        cursor.moveToFirst();

        while (cursor.moveToNext()){

            double lat = Double.parseDouble(cursor.getString(2));
            double lon = Double.parseDouble(cursor.getString(3));

            LatLng marker = new LatLng(lat, lon);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(marker);
            markerOptions.title(cursor.getString(1));
            markerOptions.snippet(cursor.getString(4));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
            mMap.addMarker(markerOptions);

            LatLng pos = new LatLng(lat, lon);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        }






    }




}
