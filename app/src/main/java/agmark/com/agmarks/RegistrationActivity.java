package agmark.com.agmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Admin on 07-04-2018.
 */

public class RegistrationActivity extends Fragment {
    ImageView farmer,processor,supplier,warehouse,logistics,trader,vendor;
    FragmentTransaction transaction;
    private Fragment  mFragment;
    public static RegistrationActivity newInstance() {
        RegistrationActivity fragment = new RegistrationActivity();
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
        v = inflater.inflate(R.layout.activity_registration, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();

        farmer=(ImageView)v.findViewById(R.id.farmer);
        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new FarmerActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        processor=(ImageView)v.findViewById(R.id.processor);
        processor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new ProcessorActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        supplier=(ImageView)v.findViewById(R.id.supplier);
        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new SupplierActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        warehouse=(ImageView)v.findViewById(R.id.warehouse);
        warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new WarehouseActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        logistics=(ImageView)v.findViewById(R.id.logistics);
        logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new LogisticsActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        trader=(ImageView)v.findViewById(R.id.trader);
        trader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new TraderActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        vendor=(ImageView)v.findViewById(R.id.vendor);
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment=new VendorActivity();
                transaction.replace(R.id.frame,mFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }
}
