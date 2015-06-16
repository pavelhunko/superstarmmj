package com.businessappstation.superstarmmjdispensaries;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProductsFragment extends Fragment implements View.OnClickListener {
    View CBDLiqRow, PremLiqRow, VaporPenRow, VaporPenAccsRow, ClothRow;

    private static String TAG = "products-fragment";

    public ProductsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_products_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewsInstantiate(view);
        viewsClickListeners();
    }

    private void viewsInstantiate(View v) {
        CBDLiqRow = v.findViewById(R.id.cbd_eliquid_row);
        PremLiqRow = v.findViewById(R.id.premium_liquid_row);
        VaporPenRow = v.findViewById(R.id.vapor_pen_row);
        VaporPenAccsRow = v.findViewById(R.id.vapor_pen_accs_row);
        ClothRow = v.findViewById(R.id.cloth_apprl_row);
    }

    private void viewsClickListeners() {
        CBDLiqRow.setOnClickListener(this);
        PremLiqRow.setOnClickListener(this);
        VaporPenRow.setOnClickListener(this);
        VaporPenAccsRow.setOnClickListener(this);
        ClothRow.setOnClickListener(this);
    }

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = getFragmentManager().findFragmentById(R.id.products_list_layout);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }
    }*/

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick entered");

        Intent webIntent = new Intent();
        Uri webpage = null;

        webIntent.setAction(Intent.ACTION_VIEW);

        switch (v.getId()){
            case R.id.cbd_eliquid_row:
                webpage = Uri.parse(getResources().getString(R.string.web_link_vppen_accs));
            case R.id.premium_liquid_row:
                webpage = Uri.parse(getResources().getString(R.string.web_link_eliq));
            case R.id.vapor_pen_row:
                webpage = Uri.parse(getResources().getString(R.string.web_link_vppen));
            case R.id.vapor_pen_accs_row:
                webpage = Uri.parse(getResources().getString(R.string.web_link_vppen_accs));
            case R.id.cloth_apprl_row:
                webpage = Uri.parse(getResources().getString(R.string.web_link_clothing));

        }

        if (webpage != null && !webpage.equals(Uri.EMPTY)) {
            webIntent.setData(webpage);
            startActivity(webIntent);
        }
    }
}
