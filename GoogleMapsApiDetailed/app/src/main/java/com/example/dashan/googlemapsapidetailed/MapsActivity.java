package com.example.dashan.googlemapsapidetailed;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by dashan on 1/4/18.
 */

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Map is Ready");
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }
    }

    private static final String TAG="MapsActivity";
    private static final String FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION= Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOACTION_PERMISSION_REQUEST_CODE=1234;
    private static final float DEFAULT_ZOOM=15f;

    //vars
    private Boolean mLocationPermissionGranted=false;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        GetLocationPermission();
    }

    private void getDeviceLocation(){
        Log.d(TAG,"Geting the device current Location");
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted){
                final Task location=mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()&&task.getResult() != null){
                            Log.d(TAG,"onComplete :found Location");
                            Location currentlocation=(Location) task.getResult();
                                moveCamera(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()), DEFAULT_ZOOM);

                        }
                        else{
                            Log.d(TAG,"onCOmplete: can'not found current location");
                            Toast.makeText(MapsActivity.this,"unable to find Current location",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }catch(SecurityException e){
            e.printStackTrace();
            Log.e(TAG,"get Device Location :Security Exception: "+e.getMessage());
        }

    }


    private void moveCamera(LatLng latLng,float zoom){

        Log.d(TAG,"MoveCamera:moving the camera to :lat:"+latLng.latitude+"Logitude"+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }


    private void initMap(){
        Log.d(TAG,"init map:intilaizing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_main);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void GetLocationPermission(){
        Log.d(TAG,"Getting Location permissions");

        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                //set
                mLocationPermissionGranted=true;
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,permissions,LOACTION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,permissions,LOACTION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"Called onrequest permissionResult");
        mLocationPermissionGranted=false;
        switch (requestCode){
            case LOACTION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0 ){
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted=false;
                            Log.d(TAG,"permission failed");
                            return;
                        }
                    }
                    Log.d(TAG,"permission granted");
                    mLocationPermissionGranted=true;
                    //initlize our map
                    initMap();
                }
                break;
            }
        }
    }


}
