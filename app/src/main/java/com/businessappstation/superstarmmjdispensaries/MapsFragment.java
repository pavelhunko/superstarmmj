package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import au.com.bytecode.opencsv.CSVReader;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final float DEFAULT_ZOOM = 9;
    private static String KEY_ID = "id";
    private static String KEY_NAME = "name";
    private static String KEY_ADDRESS = "address";
    private static String KEY_ISDELIVERY = "isdelivery";
    private static String KEY_LICENSETYPE = "licensetype";
    private static String KEY_SLUG = "slug";
    private static String KEY_SUPERSLUG = "superslug";
    private static String KEY_SUBSLUG = "subslug";
    private static String KEY_TYPE = "type";
    private static String KEY_PHONE = "phone";
    private static String KEY_WEBSITE = "website";
    private static String KEY_EMAIL = "email";
    private static String KEY_LAT = "lat";
    private static String KEY_LONG = "long";
    private static String KEY_SINCE = "since";
    private static String KEY_CITY = "city";
    private static String KEY_ZIPCODE = "zipcode";

    private static GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    private static String MAP_FRAGMENT = "MapFragment";
    private final LatLng nyLocation = new LatLng(40.7033127, -73.979681);
    ArrayList<HashMap<String, String>> dispsList = new ArrayList<HashMap<String, String>>();
    SupportMapFragment mapFragment;
    View view;
    Location location;
    private LatLng myLocation;
    private ArrayList<HashMap<String, String>> mDispensariesList = new ArrayList<>();
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

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment, MAP_FRAGMENT).commit();

        //run asynctask to obtain disp list of hashmaps
        DispensariesDownloaderTask dispensariesDownloaderTask = new DispensariesDownloaderTask();
        if (mDispensariesList.size() == 0)

            try {

                mDispensariesList = dispensariesDownloaderTask.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


    }


    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;

        googleMap.setMyLocationEnabled(true);

        location = googleMap.getMyLocation();
        if (location != null) {
            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));
        } else {
            //if my location is not defined yet - use NY as default location
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nyLocation, DEFAULT_ZOOM));
        }
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                location = googleMap.getMyLocation();
                if (location != null) {
                    myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));
                }


                return true;
            }
        });
        googleMap.setOnCameraChangeListener(getCameraChangeListener());
    }

    public GoogleMap.OnCameraChangeListener getCameraChangeListener() {
        return new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                placeDispensariesOnMap(mDispensariesList);
            }
        };
    }

    private void placeDispensariesOnMap(ArrayList<HashMap<String, String>> mDispensariesList) {
        if (googleMap != null) {
            LatLngBounds bounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
            HashMap<Integer, Marker> visibleMarkers = new HashMap<>();
            double lattitude, longtitude;
            int id;
            for (HashMap<String, String> disp : mDispensariesList) {
                id = Integer.parseInt(disp.get("id"));
                lattitude = Double.parseDouble(disp.get("lat"));
                longtitude = Double.parseDouble(disp.get("long"));
                if (bounds.contains(new LatLng(lattitude, longtitude))) {
                    if (!visibleMarkers.containsKey(id)) {
                        visibleMarkers.put(id, googleMap.addMarker(new MarkerOptions().position(new LatLng(lattitude, longtitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))));
                    }
                } else {
                    if (visibleMarkers.containsKey(id)) {
                        visibleMarkers.get(id).remove();
                        visibleMarkers.remove(id);
                    }

                }
                visibleMarkers.size();

            }
        }


    }


    /**
     * * The mapfragment's id must be removed from the FragmentManager
     * *** or else if the same it is passed on the next time then
     * *** app will crash ***
     */
    @Override
    public void onDestroyView() {
        Fragment f = getFragmentManager().findFragmentByTag(MAP_FRAGMENT);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }

        super.onDestroyView();
    }

    public class DispensariesDownloaderTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        public DispensariesDownloaderTask() {

        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
            //download disps here

            ArrayList<HashMap<String, String>> dispensariesList = new ArrayList<HashMap<String, String>>();
            try {
                CSVReader csvReader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("dis.csv")));
                String[] nextLine;
                try {
                    while ((nextLine = csvReader.readNext()) != null) {
                        String id = nextLine[0];
                        String lat = nextLine[1];
                        String lon = nextLine[2];
                        String isdelivery = nextLine[3];
                        String licensetype = nextLine[4];
                        String slug = nextLine[5];
                        String type = nextLine[6];
                        String superSlug = nextLine[7];
                        String subSlug = nextLine[8];
                        String address = nextLine[9];
                        String website = nextLine[10];
                        String email = nextLine[11];
                        String phone = nextLine[12];
                        String since = nextLine[13];
                        String name = nextLine[14];

                        HashMap<String, String> sublist = new HashMap<String, String>();
                        sublist.put(KEY_ID, id);
                        sublist.put(KEY_NAME, name);
                        sublist.put(KEY_ADDRESS, address);
                        sublist.put(KEY_PHONE, phone);
                        sublist.put(KEY_SINCE, since);
                        sublist.put(KEY_WEBSITE, website);
                        sublist.put(KEY_EMAIL, email);
                        sublist.put(KEY_LAT, lat);
                        sublist.put(KEY_LONG, lon);

                        // adding HashList to ArrayList
                        dispensariesList.add(sublist);

                        //return dispensariesList;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return dispensariesList;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //implement progressbar drawer
        }
    }
}
