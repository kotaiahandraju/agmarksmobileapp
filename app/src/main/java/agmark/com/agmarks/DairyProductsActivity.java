package agmark.com.agmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 13-04-2018.
 */

public class DairyProductsActivity extends Fragment{


    FragmentTransaction transaction;
    public static DairyProductsActivity newInstance() {
        DairyProductsActivity fragment = new DairyProductsActivity();
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
        v = inflater.inflate(R.layout.dairy_products, container, false);

        transaction=getActivity().getSupportFragmentManager().beginTransaction();


        transaction.addToBackStack(null);
        transaction.commit();
        return v;
    }


}
