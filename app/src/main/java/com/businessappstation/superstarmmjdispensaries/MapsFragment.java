package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    private static GoogleMap mMap; // Might be null if Google Play services APK is not available.
   // private static Double latitude, longitude;
    //private MainActivity mapsContext;



    @Override
    public void onAttach(Activity activity) {
       // mapsContext = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view;
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        //latitude = 26.78;
        //longitude = 72.56;
        setUpMapIfNeeded();
        return view;
    }

    private void setUpMapIfNeeded(){
        if( mMap == null ){
            SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.location_map);

            if( smf != null ){
                Toast.makeText(getActivity(), "Let's go!!", Toast.LENGTH_SHORT).show();
                mMap = smf.getMap();
            }else{
                Toast.makeText(getActivity(), "SMF is null...", Toast.LENGTH_SHORT).show();
            }

            if( mMap != null ){

                setUpMap();

            }

        }
    }
    private static void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    /**
     * * The mapfragment's id must be removed from the FragmentManager
     * *** or else if the same it is passed on the next time then
     * *** app will crash ***
     */
    @Override
    public void onDestroyView() {
        //SupportMapFragment
        Fragment f = getFragmentManager().findFragmentById(R.id.location_map);
        if (f!=null){
            getFragmentManager().beginTransaction().remove(f).commit();
        }
        super.onDestroyView();
    }
}
