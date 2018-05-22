package example.com.agmarks;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

@SuppressLint("Registered")
public class WarehouseActivity extends Fragment{
    public static EditText namew,surnamew,companyw,addressw,
            pincodew,villagew,mandalw,districtw,statew,emailw,mobilew,dobw,gstw,storagew;
    String name,surname,company,address,pincode,village,mandal,district,state,email,mobile,dob,gst;
    MultiSelectSpinner spinnerCW,spinnerVW,spinnerAW,spinnerDW;
    EditText mobileDiaW;
    RelativeLayout relative;
    String strAlert="";
    AlertDialog dialog;
    Spinner spinnerCustomW;
    RadioGroup rbg;
    RadioButton rb;
    FragmentTransaction transaction;
    Config config=new Config();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl;
    Button submit;
    Button continueW,cancelW;
    int cropc,vegc,animc,daic=0;
    SmsVerifyCatcher smsVerifyCatcher;
    ArrayList<String> crops,vegetables,animal,dairy;
    String code;
    String crop="",veg="",anim="",dai="";
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
   // JSONObject object,obj;
    //JSONArray array;

    public static WarehouseActivity newInstance() {
        WarehouseActivity fragment = new WarehouseActivity();
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
        v = inflater.inflate(R.layout.activity_warehouse, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative3);
        spinnerCustomW= (Spinner)v.findViewById(R.id.spinnerUW);
        initCustomSpinner();
        spinnerCW= (MultiSelectSpinner) v.findViewById(R.id.spinnerCW);
        spinnerVW = (MultiSelectSpinner)v. findViewById(R.id.spinnerVW);
        spinnerAW = (MultiSelectSpinner) v.findViewById(R.id.spinnerAW);
        spinnerDW = (MultiSelectSpinner) v.findViewById(R.id.spinnerDW);
        baseUrl= config.get_url();

        animal=new ArrayList<>();
        animal.add("cow");
        animal.add("buffalo");
        animal.add("sheep");
        animal.add("goat");
        animal.add("cattle");
        animal.add("poultry");

        dairy=new ArrayList<>();
        dairy.add("milk");
        dairy.add("curd");
        dairy.add("butter");
        dairy.add("paneer");
        dairy.add("ghee");
        dairy.add("khowa");

        smsVerifyCatcher  = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code  = parseCode(message);
                Log.i("Message:--",code);
            }
        });
        rbg =(RadioGroup) v.findViewById(R.id.radioGroup);
        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(getActivity().getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        namew=(EditText)v.findViewById(R.id.nameW);
        namew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        surnamew=(EditText)v.findViewById(R.id.surnameW);
        surnamew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        companyw=(EditText)v.findViewById(R.id.companyW);
        companyw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        addressw=(EditText)v.findViewById(R.id.addressW);
        addressw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        pincodew=(EditText)v.findViewById(R.id.pincodeW);
        pincodew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (pincodew.getText().toString().trim().length()>0){
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
        villagew=(EditText)v.findViewById(R.id.villageW);
        villagew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mandalw=(EditText)v.findViewById(R.id.mandalW);
        mandalw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        districtw=(EditText)v.findViewById(R.id.districtW);
        districtw.setEnabled(false);
        districtw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        statew=(EditText)v.findViewById(R.id.stateW);
        statew.setEnabled(false);
        statew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        emailw=(EditText)v.findViewById(R.id.emailW);
        emailw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobilew=(EditText)v.findViewById(R.id.mobileW);
        mobilew.setEnabled(false);

        dobw=(EditText)v.findViewById(R.id.dobW);
        dobw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        gstw=(EditText)v.findViewById(R.id.gstW);
        gstw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        storagew=(EditText)v.findViewById(R.id.storageCapacity);
        storagew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobileDiaW=(EditText)alertLayout.findViewById(R.id.editT5);
        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobilew.setText(mobileDiaW.getText().toString());
                String phone = mobileDiaW.getText().toString();
                if (!isPWD(phone)) {
                    mobileDiaW.setError("Invalid Number");
                    return;
                }
                sendDetailsToServer();
            }
        });
        submit=(Button)v.findViewById(R.id.submitW);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!isspinnerCW()) {

                    return;
                }
                if (!isspinnerVW()) {

                    return;
                }
                if (!isspinnerAW()) {

                    return;
                }
                if (!isspinnerDW()) {

                    return;
                }*/
                sendDetailsToServer2();
            }
        });
        return v;
    }


    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("pincode", pincodew.getText().toString());

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
                    districtw.setText(obj.getString("District"));
                    statew.setText(obj.getString("State"));
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

    private void initCustomSpinner() {
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("--Units--");
        languages.add("Tonnes");
        languages.add("Quintal");
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), languages);
        spinnerCustomW.setAdapter(customSpinnerAdapter);
        spinnerCustomW.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sendDetailsToServer2() {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("mobile", mobileDiaW.getText().toString());

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

    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaW.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"mobileduplicate",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());

                if (response.toString().contains("\"userlist\":\"\"")){
                    dialog.hide();
                    relative.setVisibility(View.VISIBLE);
                    getrawdatafromserver();
                }
                else if (response.toString().contains("Stroage")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Warehouse", Toast.LENGTH_SHORT).show();
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
                         continueW = (Button) alertLayout.findViewById(R.id.continueP);
                         cancelW = (Button) alertLayout.findViewById(R.id.cancelP);
                         AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                         alert.setView(alertLayout);
                         alert.setCancelable(false);
                         dialog = alert.create();
                         dialog.show();
                         cancelW.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                 transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
                                 transaction.addToBackStack(null);
                                 transaction.commit();
                                 dialog.hide();
                             }
                         });
                         continueW.setOnClickListener(new View.OnClickListener() {

                             @Override
                             public void onClick(View v) {
                                 dialog.hide();
                                 relative.setVisibility(View.VISIBLE);
                                 getrawdatafromserver();
                             }
                         });
                         Log.i("obvsjvbld", obj.getString("status1"));
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
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

    private void sendDetailsToServer0() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaW.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"stroageduplicatecheck",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());
                if (response.toString().contains("Alredy exist")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Warehouse", Toast.LENGTH_SHORT).show();
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

    private void getrawdatafromserver() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaW.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"getrawdata",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                /*Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());*/
                sendDetailsToServer0();
                dialog.hide();
                JSONObject json= null;
                try {
                    json = new JSONObject(response.toString());
                    JSONArray array = json.getJSONArray("masCom");
                    crops = new ArrayList<String>();
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject obj = array.getJSONObject(i);
                        crops.add(obj.getString("Commodity"));
                    }
                    JSONArray array1 = json.getJSONArray("masVeg");
                    vegetables=new ArrayList<>();
                    for (int i = 0; i < array1.length(); i++) {
                        final JSONObject obj = array1.getJSONObject(i);
                        vegetables.add(obj.getString("Vegetable"));
                    }

                    ArrayAdapter<String> adapterC = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, crops);
                    ArrayAdapter<String> adapterV = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, vegetables);
                    ArrayAdapter<String> adapterA = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, animal);
                    ArrayAdapter<String> adapterD = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_checked, dairy);

                    spinnerCW.setListAdapter(adapterC).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            cropc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(cropc==0)
                                        crop=crops.get(i);
                                    else
                                        crop= crop+","+crops.get(i);
                                    cropc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Crop")
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0);

                    spinnerVW.setListAdapter(adapterV).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            vegc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(vegc==0)
                                        veg=vegetables.get(i);
                                    else
                                        veg= veg+","+vegetables.get(i);
                                    vegc++;
                                }
                            }
                            Log.i("countVeg:",""+vegc);
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Vegetables")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

                    spinnerAW.setListAdapter(adapterA).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {

                            animc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(animc==0)
                                        anim=animal.get(i);
                                    else
                                        anim= anim+","+animal.get(i);
                                    animc++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Animal")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

                    spinnerDW.setListAdapter(adapterD).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {

                            daic=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    if(daic==0)
                                        dai=dairy.get(i);
                                    else
                                        dai= dai+","+dairy.get(i);
                                    daic++;
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Dairy")
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

    private void sendDetailsToServer1() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("status",rb.getText().toString());
        postParam.put("firstName", namew.getText().toString());
        postParam.put("lastName", surnamew.getText().toString());
        postParam.put("companyName",companyw.getText().toString());
        postParam.put("address", addressw.getText().toString());
        postParam.put("pincode",pincodew.getText().toString());
        postParam.put("village", villagew.getText().toString());
        postParam.put("mandal", mandalw.getText().toString());
        postParam.put("district", districtw.getText().toString());
        postParam.put("state", statew.getText().toString());
        postParam.put("email",emailw.getText().toString());
        postParam.put("mobile", mobilew.getText().toString());
       // postParam.put("dob",dobw.getText().toString());
        postParam.put("gstnumber",gstw.getText().toString());
        postParam.put("crops",crop);
        postParam.put("vegetables",veg);
        postParam.put("dairy",dai);
           Log.i("crop",""+crop);

        postParam.put("storageCapacity",storagew.getText().toString());
        postParam.put("units",spinnerCustomW.getSelectedItem().toString());
        Log.i("data sedngbn",""+postParam);
        Toast.makeText(getContext(), spinnerCustomW.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"stroagereg",new JSONObject(postParam),
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

    private boolean isPWD(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }

    private boolean isspinnerCW() {
        if(spinnerCW.getSelectedItem().toString().equals("Select Crop")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Crop");
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean isspinnerVW(){
        if(spinnerVW.getSelectedItem().toString().equals("Select Vegetables")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Vegetables");
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean isspinnerAW(){
        if(spinnerAW.getSelectedItem().toString().equals("Select Animal") ) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Animal");
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return true;
        }
        return false;
    }

    private boolean isspinnerDW () {
        if (spinnerDW.getSelectedItem().toString().equals("Select Dairy")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Dairy");
            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return true;
        }
        return false;
    }

    private class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(getActivity());
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }
    }
}
