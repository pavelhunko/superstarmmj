package com.businessappstation.superstarmmjdispensaries;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProductsFragment extends Fragment {
    View CBDLiqRow, PremLiqRow, VaporPenRow, VaporPenAccsRow, ClothRow;
    //ListView lView; create listView instead
    Uri webpage;
    Intent webIntent;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_products_list, container, false);
        //lView = rootView.findViewById(R.id.)
        viewsInstantiate(rootView);
        viewsClickListeners();

        return rootView;
    }

    private void viewsInstantiate(View v){
        CBDLiqRow = v.findViewById(R.id.cbd_eliquid_row);
        PremLiqRow = v.findViewById(R.id.premium_liquid_row);
        VaporPenRow = v.findViewById(R.id.vapor_pen_row);
        VaporPenAccsRow = v.findViewById(R.id.vapor_pen_accs_row);
        ClothRow = v.findViewById(R.id.cloth_apprl_row);
    }

    private void viewsClickListeners(){

        CBDLiqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = Uri.parse(getResources().getString(R.string.web_link_cbd));
                webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        PremLiqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = Uri.parse(getResources().getString(R.string.web_link_eliq));
                webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        VaporPenRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = Uri.parse(getResources().getString(R.string.web_link_vppen));
                webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        VaporPenAccsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = Uri.parse(getResources().getString(R.string.web_link_vppen_accs));
                webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        ClothRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webpage = Uri.parse(getResources().getString(R.string.web_link_clothing));
                webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = getFragmentManager().findFragmentById(R.id.products_list_layout);
        if (f != null){
            getFragmentManager().beginTransaction().remove(f).commit();
        }
    }
}
