package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

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
    private static String KEY_URl = "url";
    private static String KEY_EMAIL = "email";
    private static String KEY_LAT = "lat";
    private static String KEY_LONG = "long";
    private static String KEY_SINCE = "since";
    private static String KEY_CITY = "city";
    private static String KEY_ZIPCODE = "zipcode";

    private static String MAP_FRAGMENT = "MapFragment";
    private static GoogleApiClient mGoogleAPIClient;
    private static String TAG = "maps-fragment";
    private final LatLng nyLocation = new LatLng(40.7033127, -73.979681);
    SupportMapFragment mapFragment;
    View view;
    Location mLocation;
    private LatLng myLocation;
    private ArrayList<HashMap<String, String>> mDispensariesList = new ArrayList<>();
    private HashMap<Integer, Marker> visibleMarkers = new HashMap<>();

    private static Object getIdFromValue(HashMap hashMap, Object s) {
        for (Object o : hashMap.keySet()) {
            //?? String str = (String) hashMap.get(o);
            if (hashMap.get(o).equals(s))
                return o;
        }

        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach() entered");
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView() entered");
        setRetainInstance(true);
        if (container == null) {
            return null;
        }
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated entered");
        super.onViewCreated(view, savedInstanceState);
        buildGoogleAPIClient();

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment, MAP_FRAGMENT).commit();

        //run asynctask to obtain disp list of hashmaps
        DispensariesDownloaderTask dispensariesDownloaderTask = new DispensariesDownloaderTask();
        if (mDispensariesList.size() == 0)

            try {

                mDispensariesList = dispensariesDownloaderTask.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }


    }

    @Override
    public void onMapReady(final GoogleMap gMap) {
        Log.i(TAG, "onMapReady() entered");

        gMap.setMyLocationEnabled(true);
        gMap.setOnMarkerClickListener(this);
        gMap.setOnInfoWindowClickListener(this);


        if (mLocation != null) {
            myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));
        } else {
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nyLocation, DEFAULT_ZOOM));
        }

        gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.i(TAG, "onMyLocationButtonClick() is processed");
                if (mLocation != null) {
                    myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_ZOOM));
                }
                return false;
            }
        });

        gMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                placeDispensariesOnMap(mDispensariesList, gMap);
            }
        });


    }

    private void buildGoogleAPIClient() {
        Log.i(TAG, "buildGoogleAPIClient entered");
        mGoogleAPIClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void placeDispensariesOnMap(ArrayList<HashMap<String, String>> mDispensariesList, GoogleMap map) {
        if (map != null) {
            LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;

            double lattitude, longtitude;
            int id;
            for (HashMap<String, String> disp : mDispensariesList) {
                id = Integer.parseInt(disp.get("id"));
                lattitude = Double.parseDouble(disp.get("lat"));
                longtitude = Double.parseDouble(disp.get("long"));
                if (bounds.contains(new LatLng(lattitude, longtitude))) {
                    if (!visibleMarkers.containsKey(id)) {
                        visibleMarkers.put(id, map.addMarker(
                                new MarkerOptions().position(new LatLng(lattitude, longtitude))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                        .title(disp.get("name")))); //edit snippet

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

    @Override
    public void onResume() {
        super.onResume();
        mGoogleAPIClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleAPIClient.disconnect();
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected() entered");
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleAPIClient);
        //onMapReady(googleMap);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed entered");

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i(TAG, "onMarkerClick() entered");
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //create Dialog, that proposes to "call", "go to webpage", "drop a message" to email
        //perform nullcheck

        String dispID = getIdFromValue(visibleMarkers, marker).toString();
        HashMap<String, String> markerHashMap = mDispensariesList.get(Integer.parseInt(dispID) - 1);
        Bundle args = new Bundle();

        args.putString("name", markerHashMap.get("name"));
        args.putString("email", markerHashMap.get("email"));
        args.putString("url", markerHashMap.get("url"));
        args.putString("phone", markerHashMap.get("phone"));

        DialogFragment dialogFragment = AlertDialogFragment.newInstance(args);
        dialogFragment.show(getFragmentManager(), "dialog");//??


    }


    //class for handling dialog events
    public static class AlertDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

        public static AlertDialogFragment newInstance(Bundle args) {
            AlertDialogFragment dFragment = new AlertDialogFragment();
            dFragment.setArguments(args);
            return dFragment;
        }


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.marker)
                    .setTitle((CharSequence) getArguments().get("name"))
                    .setItems(R.array.map_dialog_action_chooser, this)
                    .setNegativeButton(R.string.map_fragment_close_dialog_button, this);
            return builder.create();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent();
            String actionDesc = null;
            Uri action = null;
            switch (which) {
                case 0:
                    action = Uri.parse(getArguments().getString("url"));


                    if (!action.equals(Uri.EMPTY) && !action.equals(Uri.parse("NA"))) {
                        action = Uri.parse("http://" + action);
                        intent.setAction(Intent.ACTION_VIEW);

                    } else actionDesc = "Website";
                    break;
                case 1:
                    action = Uri.parse(getArguments().getString("email"));


                    if (!action.equals(Uri.EMPTY) && !action.equals(Uri.parse("NA"))) {

                        //String emailcomposer = "?subject=" + Uri.encode("Request From SuperstarDispensary app") + "&body=" + Uri.encode("");
                        action = Uri.parse("mailto:" + action + "?subject=" + Uri.encode("Request From SuperstarDispensary app") + "&body=" + Uri.encode(""));
                        intent.setAction(Intent.ACTION_SENDTO);

                    } else actionDesc = "Email";
                    break;
                case 2:
                    action = Uri.parse(getArguments().getString("phone"));


                    if (!action.equals(Uri.EMPTY) && !action.equals(Uri.parse("NA"))) {
                        action = Uri.parse("tel:" + action);
                        intent.setAction(Intent.ACTION_DIAL);

                    } else actionDesc = "Phone";
                    break;


            }
            if (actionDesc != null && !actionDesc.isEmpty()) {
                Toast.makeText(getActivity(), actionDesc + " is not available!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (action != null) {
                intent.setData(action);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }


            }


        }

    }

    public class DispensariesDownloaderTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        public DispensariesDownloaderTask() {

        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
            //download disps here

            ArrayList<HashMap<String, String>> dispensariesList = new ArrayList<>();
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
                        String url = nextLine[10];
                        String email = nextLine[11];
                        String phone = nextLine[12];
                        String since = nextLine[13];
                        String name = nextLine[14];

                        HashMap<String, String> sublist = new HashMap<>();
                        sublist.put(KEY_ID, id);
                        sublist.put(KEY_NAME, name);
                        sublist.put(KEY_ADDRESS, address);
                        sublist.put(KEY_PHONE, phone);
                        sublist.put(KEY_SINCE, since);
                        sublist.put(KEY_URl, url);
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

        }
    }

}
