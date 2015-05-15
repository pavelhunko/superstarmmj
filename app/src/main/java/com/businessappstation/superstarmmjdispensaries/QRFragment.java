package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.*;
import com.google.zxing.integration.android.IntentResult;

public class QRFragment extends Fragment{

    private static final String QR_TAG = "QR";
    private static final String EXTRA_CODE = "com.example.testingcodereading.code";
    Fragment qrFragment;
    Button btn;
    Uri uri;
    Intent webIntent;

    public static QRFragment newInstance(String code){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CODE, code);

        QRFragment fragment = new QRFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public QRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //qrFragment = Fragment.instantiate(getActivity(),)
        //qrFragment = (Barcod  eFragment) getChildFragmentManager().findFragmentById(R.id.qr_scan_fragment);
        //qrFragment.setScanResultHandler(this);


        /* btn = ((Button) view.findViewById(R.id.scan_button));
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       goToLink();
                                   }
                               }
        );*/


    }

    @Override
    public void onResume() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setScanningRectangle(250, 250);
        intentIntegrator.setCaptureLayout(R.layout.qr_capture);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this should not be called
        /*IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (scanResult != null){
            Toast.makeText(getActivity(), "onActivityResult in QRFragment called", Toast.LENGTH_SHORT).show();
        }

        /*IntentResult scanIntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanIntentResult != null){
            String scanContent = scanIntentResult.getContents();
        } else {
            Toast.makeText(getActivity(), "No Scan Data Received!", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

/*
    @Override
    public void scanResult(ScanResult scanResult) {
        btn.setEnabled(true);
        Toast.makeText(getActivity(), scanResult.getRawResult().getText(), Toast.LENGTH_LONG).show();
        uri = Uri.parse(scanResult.getRawResult().getText());
    }*/

    public void goToLink() {
        webIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(webIntent);
    }

    @Override
    public void onDestroyView() {

       /* Fragment f = getChildFragmentManager().findFragmentById(R.id.qr_scan_fragment);
        if (f!=null){
            getFragmentManager().beginTransaction().remove(f).commit();
        }*/
        super.onDestroyView();
    }
}
