package com.example.dashan.googlemapsapi;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
            initMap();

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps_frament);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't Connect to play services", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if(mGoogleMap!=null){
            mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc=new Geocoder(MainActivity.this);
                    LatLng ll=marker.getPosition();
                    List<Address> list=null;
                    try {
                         list=gc.getFromLocation(ll.latitude,ll.longitude,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                        Address address = list.get(0);
                        marker.setTitle(address.getLocality());
                        marker.showInfoWindow();


                }
            });
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v=getLayoutInflater().inflate(R.layout.info_window,null);

                    TextView tvlocality=v.findViewById(R.id.tv_lacallity);
                    TextView tvLat=v.findViewById(R.id.tv_lat);
                    TextView tvlng=v.findViewById(R.id.tv_lng);
                    TextView tvSnippt=v.findViewById(R.id.tv_snippt);

                    LatLng ll=marker.getPosition();
                    tvlocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: "+ll.latitude);
                    tvlng.setText("Longitude: "+ll.longitude);
                    tvSnippt.setText(marker.getSnippet());

                    return v;
                }
            });
        }
          goToLoactionZoom(13.0103454,74.7842627,15);
       // mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        //mGoogleApiClient.connect();

    }

    private void goToLoaction(double lat, double lon) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLoactionZoom(double lat, double lon, float zoom) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }


    Marker marker;
    public void geoLocate(View view) throws IOException {
        EditText et = (EditText) findViewById(R.id.text_where);
        String location = et.getText().toString().trim();
        if (location.isEmpty()) {
            et.setError("Please Enter a location");
            et.requestFocus();
            return;
        }
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        if (list.isEmpty()) {
            Toast.makeText(this, "Error NO Location found", Toast.LENGTH_SHORT).show();
            et.setError("Invalid Loaction");
            et.requestFocus();
            return;
        } else {
            Address address = list.get(0);
            String locality = address.getLocality();

            Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

            double lat = address.getLatitude();
            double lon = address.getLongitude();
            goToLoactionZoom(lat, lon, 15);
            setMarker(locality, lat, lon);
        }
    }
//Circle circle;
    Marker marker1;
    Polyline line;
    Marker marker2;
    private void setMarker(String locality, double lat, double lon) {
       // if(marker!=null){
         //  marker.remove();
            //RemoveEverything();
        //}
        MarkerOptions options=new MarkerOptions()
                .title(locality)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker))
                .position(new LatLng(lat,lon))
                .snippet("Iam here");
        marker= mGoogleMap.addMarker(options);
        if(marker1==null){
            marker1=mGoogleMap.addMarker(options);
        }
        else if(marker2==null){
            marker2=mGoogleMap.addMarker(options);
            drawLine();
        }
        else{
            RemoveEverything();
        }

       // circle=drawCicle(new LatLng(lat,lon));

    }

    private void drawLine() {
        PolylineOptions options=new PolylineOptions()
                .add(marker1.getPosition())
                .add(marker2.getPosition())
                .color(Color.BLUE)
                .width(3);

        line=mGoogleMap.addPolyline(options);
    }

    /*private Circle drawCicle(LatLng latLng) {

        CircleOptions options=new CircleOptions()
                .center(latLng)
                .radius(1000)
                .fillColor(0x30FF0000)
                .strokeColor(Color.GRAY)
                .strokeWidth(3);
        return mGoogleMap.addCircle(options);
    }*/
    private void RemoveEverything(){
        marker1.remove();
        marker1=null;
        marker2.remove();
        marker2=null;
        //circle.remove();
        //circle=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone: {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            }
            case R.id.mapTypeNormal: {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            }
            case R.id.mapTypeSatellite: {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            }
            case R.id.mapTypeTerrain: {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            }
            case R.id.mapTypeHybrid: {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
if(location==null){
    Toast.makeText(this,"Can't get Current Loaction",Toast.LENGTH_SHORT).show();
}
else{
    LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
    CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,15);
    mGoogleMap.animateCamera(update);
}
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
