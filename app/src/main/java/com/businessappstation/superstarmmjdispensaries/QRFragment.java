package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.abhi.barcode.frag.libv2.BarcodeFragment;
import com.abhi.barcode.frag.libv2.IScanResultHandler;
import com.abhi.barcode.frag.libv2.ScanResult;

public class QRFragment extends Fragment implements IScanResultHandler{

    BarcodeFragment barcodeFragment;
    Button btn;

    public QRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_qr, container, false);

        barcodeFragment = (BarcodeFragment) MainActivity.fragmentManager.findFragmentById(R.layout.fragment_qr);
        barcodeFragment.setScanResultHandler(this);

        btn.setEnabled(false);

        //should return view
        return null;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void scanResult(ScanResult scanResult) {

    }
}
