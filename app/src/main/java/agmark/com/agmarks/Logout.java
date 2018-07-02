package agmark.com.agmarks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 16-06-2017.
 */

public class Logout extends Fragment {

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    FragmentTransaction transaction;
    public static Logout newInstance() {
        Logout fragment = new Logout();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {// Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.logout, container, false);


       final  AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage("Are U Sure U want to Logout");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {         // Write your code here to execute after dialog closed
                // Toast.makeText(getActivity().getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

                sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                editor.clear();
                editor.commit();
                Intent intent=new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                startActivity(intent);

//                transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame, new LoginPageActivity().newInstance());
//                transaction.addToBackStack(null);
//                transaction.commit();

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {         // Write your code here to execute after dialog closed
                // Toast.makeText(getActivity().getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

            }
        });

        // Showing Alert Message
        alertDialog.show();


        return v;
    }

}