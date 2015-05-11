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

import com.abhi.barcode.frag.libv2.BarcodeFragment;
import com.abhi.barcode.frag.libv2.IScanResultHandler;
import com.abhi.barcode.frag.libv2.ScanResult;
import com.google.zxing.BarcodeFormat;

import java.util.EnumSet;

public class QRFragment extends Fragment implements IScanResultHandler {

    BarcodeFragment barcodeFragment;
    Button btn;
    Uri uri;
    Intent webIntent;

    public QRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qr, container, false);

        //should return view
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barcodeFragment = (BarcodeFragment) getChildFragmentManager().findFragmentById(R.id.qr_scan_fragment);
        barcodeFragment.setScanResultHandler(this);
        //barcodeFragment.getView().setLayoutParams();

        btn = ((Button) view.findViewById(R.id.scan_button));
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       goToLink(v);
                                   }
                               }
        );

        barcodeFragment.setDecodeFor(EnumSet.of(BarcodeFormat.QR_CODE));
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
        btn.setEnabled(true);
        Toast.makeText(getActivity(), scanResult.getRawResult().getText(), Toast.LENGTH_LONG).show();
        uri = Uri.parse(scanResult.getRawResult().getText());
    }

    public void goToLink(View v) {
        webIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(webIntent);
    }
}
