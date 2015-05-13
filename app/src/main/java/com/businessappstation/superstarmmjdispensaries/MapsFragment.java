package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback{
    private static GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static String MAP_FRAGMENT = "map";
    SupportMapFragment mapFragment;
    View view;
    //lookthrough example project

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    /**
     * * The mapfragment's id must be removed from the FragmentManager
     * *** or else if the same it is passed on the next time then
     * *** app will crash ***
     */
    @Override
    public void onDestroyView() {
        Fragment f = getChildFragmentManager().findFragmentByTag(MAP_FRAGMENT);
        if (f!=null){
            getChildFragmentManager().beginTransaction().remove(f).commit();
        }
        super.onDestroyView();
    }
}
