package com.businessappstation.superstarmmjdispensaries;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    private static final String QR_TAG = "QR";
    BarcodeFragment qrFragment;
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
       // View rootView =

        //should return view
        return inflater.inflate(R.layout.fragment_qr, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qrFragment = (BarcodeFragment) getChildFragmentManager().findFragmentById(R.id.qr_scan_fragment);
        //qrFragment = BarcodeFragment.instantiate()
        qrFragment.setScanResultHandler(this);
        //FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        //fragmentTransaction.add(qrFragment, QR_TAG).commit();


        btn = ((Button) view.findViewById(R.id.scan_button));
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       goToLink();
                                   }
                               }
        );

        qrFragment.setDecodeFor(EnumSet.of(BarcodeFormat.QR_CODE));
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

    public void goToLink() {
        webIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(webIntent);
    }

    @Override
    public void onDestroyView() {

        Fragment f = getChildFragmentManager().findFragmentById(R.id.qr_scan_fragment);
        if (f!=null){
            getFragmentManager().beginTransaction().remove(f).commit();
        }
        super.onDestroyView();
    }
}
