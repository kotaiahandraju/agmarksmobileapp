package agmark.com.agmarks.Fragements;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agmark.com.agmarks.R;


public class Fragment6 extends Fragment {

    public static Fragment6 newInstance(String param1, String param2) {
        Fragment6 fragment = new Fragment6();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment6, container, false);
    }


}
