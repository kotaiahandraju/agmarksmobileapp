package agmark.com.agmarks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;


public class ContactFragment extends Fragment {
    TextView mob1,mob2,mob3,mob4;
    TextView mail;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
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
        v = inflater.inflate(R.layout.fragment_contact, container, false);
        mob1=(TextView)v.findViewById(R.id.mobi1);
        mob1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number= "08645 - 380800";
                String numberToDial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
            }
        });
        mob2=(TextView)v.findViewById(R.id.mobi2);
        mob2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number= "08645 - 380800";
                String numberToDial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
            }
        });
        mob3=(TextView)v.findViewById(R.id.mobi3);
        mob3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number= "+91 - 9851 222 888";
                String numberToDial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
            }
        });
        mob4=(TextView)v.findViewById(R.id.mobi4);
        mob4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number= "+91 - 9851 222 999";
                String numberToDial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
            }
        });
        mail=(TextView)v.findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@agmarks.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject");
                i.putExtra(Intent.EXTRA_TEXT   , "body");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity().getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }



}
