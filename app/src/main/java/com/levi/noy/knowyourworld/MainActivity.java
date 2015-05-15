package com.levi.noy.knowyourworld;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements GoogleMap.OnMarkerDragListener {
    GoogleMap googleMap;
    Marker mMark;
    TextView mLocationTV;
    Geocoder mGcd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationTV = (TextView)findViewById(R.id.locText);
        mGcd = new Geocoder(MainActivity.this, Locale.getDefault());
        createMapView();
        addMarker();
    }


    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }
    /**
     * Adds a marker to the map
     */
    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            mMark = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(0, 0))
                            .title("Marker")
                            .draggable(true)
            );
            googleMap.setOnMarkerDragListener(this);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(final Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng latlng = marker.getPosition();
        List<Address> addresses = null;
        try {
            addresses=mGcd.getFromLocation(latlng.latitude,latlng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0)
            mLocationTV.setText(addresses.get(0).getCountryName());
    }

//    class PostWriter extends AsyncTask<LatLng,Void,String>{
//        @Override
//        protected String doInBackground(LatLng... params) {
//            String thePlace = "";
//            Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
//            List<Address> addresses = null;
//            for (LatLng latlng : params){
//                try {
//                    addresses = gcd.getFromLocation(latlng.latitude, latlng.longitude, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (addresses.size() > 0)
//                    thePlace = (addresses.get(0).getCountryName());
//            }
//            return thePlace;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            mLocationTV.setText(s);
//        }
//    }
}
