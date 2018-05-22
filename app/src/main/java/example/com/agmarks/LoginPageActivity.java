package example.com.agmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginPageActivity extends Fragment {
    Button button;
    FragmentTransaction transaction;
    public static LoginPageActivity newInstance() {
        LoginPageActivity fragment = new LoginPageActivity();
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
        v = inflater.inflate(R.layout.activity_loginpage, container, false);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        button=(Button)v.findViewById(R.id.btn_signIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        transaction.addToBackStack(null);
        transaction.commit();
        return v;
    }
}
