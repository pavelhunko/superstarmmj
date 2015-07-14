package com.businessappstation.superstarmmjdispensaries;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.abhi.barcode.frag.libv2.BarcodeFragment;
import com.abhi.barcode.frag.libv2.IScanResultHandler;
import com.abhi.barcode.frag.libv2.ScanResult;
import com.google.zxing.BarcodeFormat;

import java.util.EnumSet;

public class QRFragment extends Fragment implements IScanResultHandler, View.OnClickListener{

    private static final String TAG = "QR";
    BarcodeFragment qrFragment;
    Button buttonFollow, buttonRescan;
    Uri uri;
    Intent webIntent;

    public QRFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonFollow = (Button) view.findViewById(R.id.follow_button);
        buttonRescan = (Button) view.findViewById(R.id.rescan_button);
        disableButtons();

        instantiateBarcodeFragment();
        setButtonsClickListener();
    }

    private void setButtonsClickListener() {
        buttonFollow.setOnClickListener(this);
        buttonRescan.setOnClickListener(this);
    }


    private void instantiateBarcodeFragment() {
        qrFragment = (BarcodeFragment) getChildFragmentManager().findFragmentById(R.id.qr_scan_fragment);
        qrFragment.setScanResultHandler(this);
        qrFragment.setDecodeFor(EnumSet.of(BarcodeFormat.QR_CODE));
    }

    private void enableButtons(){
        buttonFollow.setEnabled(true);
        buttonRescan.setEnabled(true);
    }

    private void disableButtons(){
        buttonFollow.setEnabled(false);
        buttonRescan.setEnabled(false);
    }


    @Override
    public void scanResult(ScanResult scanResult) {
        enableButtons();
        uri = Uri.parse(scanResult.getRawResult().getText());
    }

    public void goToLink() {
        //check, if qrcode is valid
        webIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(webIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.follow_button:
                goToLink();
                break;
            case R.id.rescan_button:
                disableButtons();
                qrFragment.restart();
                break;
        }


    }
}
