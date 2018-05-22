package example.com.agmarks;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.apptik.widget.multiselectspinner.MultiSelectSpinner;

/**
 * Created by Admin on 12-04-2018.
 */

public class SupplierActivity extends Fragment {
    public static EditText names,surnames,companys,addresss,
            pincodes,villages,mandals,districts,states,emails,mobiles,dobs,gsts;
    MultiSelectSpinner spinnerBS,spinnerBOS,spinnerFMS,spinnerIS,spinnerOS,spinnerSS;
    RelativeLayout relative;
    AlertDialog dialog;
    FragmentTransaction transaction;
    Config config=new Config();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl;
    String strAlert;
    EditText mobileDiaS;
    Button submit;
    Button continueS,cancelS;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    int bioc,botanicalc,farmc,inorganicc,organicc,seedc=0;
    ArrayList<String> bio,botanical,farm,inorganic,organic,seed;
    String bioS="",botanicalS="",farmS="",inorganicS="",organicS="",seedS="";
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    RadioGroup rbg;
    RadioButton rb;
    String category,input;
    public static SupplierActivity newInstance() {
        SupplierActivity fragment = new SupplierActivity();
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
        v = inflater.inflate(R.layout.activity_supplier, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative2);
        spinnerBS= (MultiSelectSpinner) v.findViewById(R.id.spinnerBS);
        spinnerBOS = (MultiSelectSpinner)v. findViewById(R.id.spinnerBOS);
        spinnerFMS= (MultiSelectSpinner) v.findViewById(R.id.spinnerFMS);
        spinnerIS= (MultiSelectSpinner) v.findViewById(R.id.spinnerIS);
        spinnerOS= (MultiSelectSpinner) v.findViewById(R.id.spinnerOS);
        spinnerSS= (MultiSelectSpinner)v. findViewById(R.id.spinnerSS);

        rbg=(RadioGroup) v.findViewById(R.id.radioGroup);
        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(getActivity().getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        baseUrl= config.get_url();
        smsVerifyCatcher  = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code  = parseCode(message);
                Log.i("Message:--",code);
            }
        });

        names=(EditText)v.findViewById(R.id.nameS);
        names.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        surnames=(EditText)v.findViewById(R.id.surnameS);
        surnames.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        companys=(EditText)v.findViewById(R.id.companyS);
        companys.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        addresss=(EditText)v.findViewById(R.id.addressS);
        addresss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        pincodes=(EditText)v.findViewById(R.id.pincodeS);
        pincodes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (pincodes.getText().toString().trim().length()>0){
                    view.setBackgroundResource( R.drawable.edit_text);
                    if (!b){
                        sendDetailsToServerPin();
                    }

                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
            }
        });
        villages=(EditText)v.findViewById(R.id.villageS);
        villages.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        mandals=(EditText)v.findViewById(R.id.mandalS);
        mandals.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        districts=(EditText)v.findViewById(R.id.districtS);
        districts.setEnabled(false);
        districts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        states=(EditText)v.findViewById(R.id.stateS);
        states.setEnabled(false);
        states.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        emails=(EditText)v.findViewById(R.id.emailS);
        emails.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        mobiles=(EditText)v.findViewById(R.id.mobileS);
        mobiles.setEnabled(false);

        dobs=(EditText)v.findViewById(R.id.dobS);
        dobs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        gsts=(EditText)v.findViewById(R.id.gstS);
        gsts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text);
                }
            }
        });
        relative.setVisibility(View.GONE);
        LayoutInflater inflater1 = getLayoutInflater();
        View alertLayout = inflater1.inflate(R.layout.custom_dialog1, null);
        final ImageView image=(ImageView)alertLayout.findViewById(R.id.close_dialog1);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new ContentDashboard().newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
                dialog.dismiss();
            }
        });
       mobileDiaS=(EditText)alertLayout.findViewById(R.id.editT5);
        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobiles.setText(mobileDiaS.getText().toString());
                String phone = mobileDiaS.getText().toString();
                if (!isPWD(phone)) {
                    mobileDiaS.setError("Invalid Number");
                    return;
                }
                dialog.hide();
                relative.setVisibility(View.VISIBLE);
                sendDetailsToServer();

            }
        });

        submit=(Button)v.findViewById(R.id.submitS);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDetailsForOTP();
            }
        });
        return v;
    }

    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("pincode", pincodes.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"getPincodeData",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                       /* Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Response is:-",response.toString());*/
                try {
                    JSONObject json = null;
                    json = new JSONObject(response.toString());
                    JSONArray array = json.getJSONArray("pincodedata");
                    final JSONObject obj = array.getJSONObject(0);
                    districts.setText(obj.getString("District"));
                    states.setText(obj.getString("State"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:- " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void sendDetailsForOTP() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaS.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"sendOtp",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());
                // sendDetailsToServer1();
                LayoutInflater li = getLayoutInflater();
                View dialogView = li.inflate(R.layout.custom_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setTitle("Enter OTP:");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher);
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView.findViewById(R.id.et_input);
                userInput.setText(code);
                alertDialogBuilder.setCancelable(false).setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            if (response.get("otp").toString()!=null &&
                                    response.get("otp").toString().contains(userInput.getText().toString())){
                                Toast.makeText(getContext().getApplicationContext(), "OTP Verified", Toast.LENGTH_SHORT).show();
                                sendDetailsToServer1();
                            }
                            else {
                                Toast.makeText(getContext().getApplicationContext(), "InvalidFormat", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:- " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void sendDetailsToServer1() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("status",rb.getText().toString());
        postParam.put("firstName", names.getText().toString());
        postParam.put("lastName", surnames.getText().toString());
        postParam.put("companyName",companys.getText().toString());
        postParam.put("address", addresss.getText().toString());
        postParam.put("pincode",pincodes.getText().toString());
        postParam.put("village", villages.getText().toString());
        postParam.put("mandal", mandals.getText().toString());
        postParam.put("district", districts.getText().toString());
        postParam.put("state", states.getText().toString());
        postParam.put("email",emails.getText().toString());
        postParam.put("mobile", mobiles.getText().toString());
        // postParam.put("dob",dobp.getText().toString());
        postParam.put("gstnumber",gsts.getText().toString());
        postParam.put("bio",bioS);
        postParam.put("botanical",botanicalS);
        postParam.put("farmMachinery",farmS);
        postParam.put("inorganic",inorganicS);
        postParam.put("organic",organicS);
        postParam.put("seed",seedS);

        Log.i("SupplierData",""+postParam);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"supplierreg",new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        if (response.toString().contains("success")){
                            transaction=getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame, new FarmerDashboard().newInstance());
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        else if (response.toString().contains("fail")){
                            Toast.makeText(getContext().getApplicationContext(), "Invalid Forms", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void supplierduplicatecheck() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaS.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"supplierduplicatecheck",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());
                if (response.toString().contains("Alredy exist")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Supplier", Toast.LENGTH_SHORT).show();
                }
                else if (response.toString().contains("not exist"))
                {
                    dialog.hide();
                    relative.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:- " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaS.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"mobileduplicate",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());

                if (response.toString().contains("\"userlist\":\"\"")){
                    dialog.hide();
                    relative.setVisibility(View.VISIBLE);
                    getsupplierslistfromserver();
                }
                else if (response.toString().contains("Supplier")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Supplier", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        JSONArray array = object.getJSONArray("userlist");
                        if (array.length() > 0) {
                            JSONObject obj = array.getJSONObject(0);
                            if (!response.toString().contains("\"status1\":\"\"")) {

                                strAlert = "You are registered as " +
                                        obj.getString("status1") + " Do you want to continue?";
                                dialog.hide();
                            } else if (!response.toString().contains("\"status1\":\"\"") &&
                                    !response.toString().contains("\"status2\":\"\"")) {

                                strAlert = "You are registered as " + obj.getString("status1") + " " +
                                        obj.getString("status2") + " Do you want to continue?";
                                dialog.hide();
                            } else if (!response.toString().contains("\"status1\":\"\"") &&
                                    !response.toString().contains("\"status2\":\"\"") &&
                                    !response.toString().contains("\"status3\":\"\"")) {

                                strAlert = "You are registered as " + obj.getString("status1") + " " +
                                        obj.getString("status2") + " " + obj.getString("status3") +
                                        " Do you want to continue?";
                                dialog.hide();
                            }
                            else if (!response.toString().contains("\"status1\":\"\"") &&
                                    !response.toString().contains("\"status2\":\"\"") &&
                                    !response.toString().contains("\"status3\":\"\"") &&
                                    !response.toString().contains("\"status4\":\"\"")) {
                                Toast.makeText(getActivity(), "You have no chance to register", Toast.LENGTH_SHORT).show();
                                dialog.hide();
                            }
                            LayoutInflater inflater1 = getLayoutInflater();
                            View alertLayout = inflater1.inflate(R.layout.alert_dialog1, null);
                            final TextView textAlert = (TextView) alertLayout.findViewById(R.id.textAlert);
                            textAlert.setText(strAlert);
                            continueS = (Button) alertLayout.findViewById(R.id.continueP);
                            cancelS = (Button) alertLayout.findViewById(R.id.cancelP);
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setView(alertLayout);
                            alert.setCancelable(false);
                            dialog = alert.create();
                            dialog.show();
                            cancelS.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    dialog.hide();
                                }
                            });
                            continueS.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.hide();
                                    relative.setVisibility(View.VISIBLE);
                                    getsupplierslistfromserver();
                                }
                            });
                            Log.i("obvsjvbld", obj.getString("status1"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                /*else {
                    supplierduplicatecheck();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:- " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void getsupplierslistfromserver() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"supplierslist",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
               /* Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());*/
                JSONObject json= null;
                try {
                    json = new JSONObject(response.toString());
                    JSONArray array1 = json.getJSONArray("list");
                    bio = new ArrayList<String>();
                    botanical=new ArrayList<>();
                    farm=new ArrayList<>();
                    inorganic=new ArrayList<>();
                    organic=new ArrayList<>();
                    seed=new ArrayList<>();


                    final JSONObject obj1 = array1.getJSONObject(0);
                    String str1=obj1.getString("input").toString();
                    String[] words1=str1.split(",");
                    for(String w:words1){
                        bio.add(w);
                    }
                    final JSONObject obj2 = array1.getJSONObject(1);
                    String str2=obj1.getString("input").toString();
                    String[] words2=str2.split(",");
                    for(String w:words2){
                        botanical.add(w);
                    }
                    final JSONObject obj3 = array1.getJSONObject(2);
                    String str3=obj1.getString("input").toString();
                    String[] words3=str3.split(",");
                    for(String w:words3){
                        farm.add(w);
                    }
                    final JSONObject obj4 = array1.getJSONObject(4);
                    String str4=obj4.getString("input").toString();
                    String[] words4=str4.split(",");
                    for(String w:words4){
                        inorganic.add(w);
                    }
                    final JSONObject obj5 = array1.getJSONObject(5);
                    String str5=obj5.getString("input").toString();
                    String[] words5=str5.split(",");
                    for(String w:words5){
                        organic.add(w);
                    }
                    final JSONObject obj6 = array1.getJSONObject(6);
                    String str6=obj6.getString("input").toString();
                    String[] words6=str6.split(",");
                    for(String w:words6){
                        bio.add(w);
                    }

                    ArrayAdapter<String> adapterC = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, bio);
                    ArrayAdapter<String> adapterV = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, botanical);
                    ArrayAdapter<String> adapterA = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, farm);
                    ArrayAdapter<String> adapterD = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_checked, inorganic);
                    ArrayAdapter<String> adapterO = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, organic);
                    ArrayAdapter<String> adapterS = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_checked, seed);

                    spinnerBS.setListAdapter(adapterC).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            bioc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(bioc==0)
                                        bioS=bio.get(i);
                                    else
                                        bioS= bioS+","+bio.get(i);
                                    bioc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Bio")
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0);

                    spinnerBOS.setListAdapter(adapterV).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            botanicalc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(botanicalc==0)
                                        botanicalS=botanical.get(i);
                                    else
                                        botanicalS= botanicalS+","+botanical.get(i);
                                    botanicalc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Botanical")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

                    spinnerFMS.setListAdapter(adapterA).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            farmc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(farmc==0)
                                        farmS=farm.get(i);
                                    else
                                        farmS= farmS+","+farm.get(i);
                                    farmc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select FarmMachinery")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

                    spinnerIS.setListAdapter(adapterD).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            inorganicc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(inorganicc==0)
                                        inorganicS=inorganic.get(i);
                                    else
                                        inorganicS= inorganicS+","+inorganic.get(i);
                                    inorganicc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Inorganic")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);
                    spinnerOS.setListAdapter(adapterO).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            organicc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(organicc==0)
                                        organicS=organic.get(i);
                                    else
                                        organicS= organicS+","+organic.get(i);
                                    organicc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Organic")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

                    spinnerSS.setListAdapter(adapterS).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            seedc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(seedc==0)
                                        seedS=seed.get(i);
                                    else
                                        seedS= seedS+","+seed.get(i);
                                    seedc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Seed")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:- " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);

    }

    private String parseCode(String message) {
            Pattern p = Pattern.compile("\\b\\d{4}\\b");
            Matcher m = p.matcher(message);
            String code = "";
            while (m.find()) {
                code = m.group(0);
            }
            return code;
    }

    private boolean isPWD(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }
}
