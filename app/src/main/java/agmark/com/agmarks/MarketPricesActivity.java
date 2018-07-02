package agmark.com.agmarks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MarketPricesActivity extends Fragment {
    public static Spinner stateSpinner,districtSpinner;
    String selectedState,selectedDistrict;
    ImageView comm, veg, animal, dairy;
    FragmentTransaction transaction;
    private Fragment mFragment;

    public static MarketPricesActivity newInstance() {
        MarketPricesActivity fragment = new MarketPricesActivity();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.activity_marketprices, container, false);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        stateSpinner=(Spinner)v.findViewById(R.id.stateSpinner);
        districtSpinner=(Spinner)v.findViewById(R.id.districtSpinner);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState=parent.getItemAtPosition(position).toString();
                switch (selectedState){
                    case"Andhra Pradesh":
                        districtSpinner.setAdapter(new ArrayAdapter<String>
                                (getActivity(),android.R.layout.simple_spinner_dropdown_item,
                                        getResources().getStringArray(R.array.items_ap_districts)));
                        break;
                    case"Telangana":
                        districtSpinner.setAdapter(new ArrayAdapter<String>
                                (getActivity(),android.R.layout.simple_spinner_dropdown_item,
                                        getResources().getStringArray(R.array.items_tn_districts)));
                        break;
                }
                districtSpinner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict=parent.getItemAtPosition(position).toString();
               // Toast.makeText(getActivity(), "\n Class: \t"+ selectedState +"\n Div: \t"+selectedDistrict, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        comm = (ImageView) v.findViewById(R.id.img_comm);
        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment = new CommoditiesActivity();
                transaction.replace(R.id.frame, mFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        veg = (ImageView) v.findViewById(R.id.img_veg);
        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment = new VegetablesActivity();
                transaction.replace(R.id.frame, mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        animal = (ImageView) v.findViewById(R.id.img_animal);
        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment = new AnimalHusbandryActivity();
                transaction.replace(R.id.frame, mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        dairy = (ImageView) v.findViewById(R.id.img_dairy);
        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment = new DairyProductsActivity();
                transaction.replace(R.id.frame, mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }



}
