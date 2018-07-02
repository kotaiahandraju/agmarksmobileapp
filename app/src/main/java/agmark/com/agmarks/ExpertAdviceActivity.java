package agmark.com.agmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 06-05-2018.
 */

public class ExpertAdviceActivity extends Fragment {
    FragmentTransaction transaction;
    public static ExpertAdviceActivity newInstance() {
        ExpertAdviceActivity fragment = new ExpertAdviceActivity();
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
        v = inflater.inflate(R.layout.activity_expertadvice, container, false);

        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.commit();
        return v;
    }
}
