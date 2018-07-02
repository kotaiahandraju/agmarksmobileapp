package agmark.com.agmarks;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.hbb20.CountryCodePicker;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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
    String rbCheck="",name,surname,company,address,pincode,village,mandal,district,state,email="",mobile,dob,gst;
    MultiSelectSpinner spinnerCW,spinnerVW,spinnerAW,spinnerDW;
    EditText mobileDiaW;
    RelativeLayout relative;
    String strAlert="",str;
    DatePickerDialog datePickerDialog;
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
    CountryCodePicker ccp;
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
                   rbCheck=rb.getText().toString();
                   if(rb.getText().toString().equals("Company")) {
                       clearBox();
                       companyw.requestFocus();
                       companyw.setCompoundDrawablesWithIntrinsicBounds(
                               null, null, getResources().getDrawable(R.drawable.star1), null);
                       emailw.setCompoundDrawablesWithIntrinsicBounds(
                               null, null, getResources().getDrawable(R.drawable.star1), null);
                       namew.setCompoundDrawablesWithIntrinsicBounds(
                               null, null, null, null);
                       surnamew.setCompoundDrawablesWithIntrinsicBounds(
                               null, null, null, null);
                   }
                    if(rb.getText().toString().equals("Individual")) {
                       clearBox();
                       namew.requestFocus();
                        namew.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        surnamew.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        emailw.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        companyw.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);

                    }
                    // Toast.makeText(getActivity().getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
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
        dobw.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobw.getInputType(); // backup the input type
                dobw.setInputType(InputType.TYPE_NULL); // disable soft input
                dobw.onTouchEvent(event); // call native handler
                dobw.setInputType(inType);
                dobw.requestFocus();
                // restore input type
                return true; // consume touch even
            }
        });
        dobw.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobw.getInputType(); // backup the input type
                dobw.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobw.setInputType(inType); // restore input type
                dobw.requestFocus();
                return true; // consume touch even
            }
        });
        dobw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                               final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String month=getMonthFullName(monthOfYear);
                                String date_pick_res = dayOfMonth + "-" + month + "-" + year;
                                // set day of month , month and year value in the edit text
                                dobw.setText(date_pick_res);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

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
        ccp=(CountryCodePicker)alertLayout.findViewById(R.id.countrycode);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Toast.makeText(getContext(), "Code is:"+ccp.getSelectedCountryCode(), Toast.LENGTH_SHORT).show();
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
                if ( !rbCheck.isEmpty()){
                    if(rbCheck.equals("Company")) {
                        if (companyw.getText().toString().trim().equals("")) {
                            companyw.setError("Company Name is required!");
                            companyw.setHint("please enter Company Name");
                        }
                         email = emailw.getText().toString().trim();
                        if (emailw.getText().toString().trim().equals("")) {
                            emailw.setError("Email is required!");
                            emailw.setHint("please enter Email");

                        }

                        if (!email.matches(emailPattern)) {
                            emailw.setError("Please enter correct Email!");

                        }
                    }
                        if(rbCheck.equals("Individual")) {
                            if (namew.getText().toString().trim().equals("")) {
                                namew.setError("Name is required!");
                                namew.setHint("please enter Name");
                            }
                            if (surnamew.getText().toString().trim().equals("")) {
                                surnamew.setError("Surname is required!");
                                surnamew.setHint("please enter Surname");
                            }
                            if (emailw.getText().toString().trim().equals("")) {
                                emailw.setError("Email is required!");
                                emailw.setHint("please enter Email");

                            }

                            if (!email.matches(emailPattern)) {
                                emailw.setError("Please enter correct Email!");

                            }

                        }

                    if (addressw.getText().toString().trim().equals("")) {
                        addressw.setError("Address is required!");
                        addressw.setHint("please enter Address");
                    }
                    if (pincodew.getText().toString().trim().equals("")) {
                        pincodew.setError("Pincode is required!");
                        pincodew.setHint("please enter Pincode");

                    }
                    if (villagew.getText().toString().trim().equals("")) {
                        villagew.setError("Village Name is required!");
                        villagew.setHint("please enter Village name");

                    }
                    if (mandalw.getText().toString().trim().equals("")) {
                        mandalw.setError("Mandal is required!");
                        mandalw.setHint("please enter Mandal");

                    }
                    if (storagew.getText().toString().trim().equals("")) {
                        storagew.setError("Storage Capacity is required!");
                        storagew.setHint("please enter Storage Capacity");

                    }
                    if (spinnerCustomW.getSelectedItem().toString().equals("--Units--")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("please select Units");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();

                    }

                    if(rbCheck.equals("Company")) {
                        if ((companyw.length() > 0) && (addressw.length() > 0) && (pincodew.length() > 0) && (villagew.length() > 0) && ((mandalw.length() > 0))
                                && (emailw.length() > 0) && (storagew.length() > 0) && (!spinnerCustomW.getSelectedItem().toString().equals("--Units--")) && email.matches(emailPattern) && (districtw.length()>0) && (statew.length()>0)) {

                           // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if ((namew.length() > 0) && (addressw.length() > 0) && (pincodew.length() > 0) && (villagew.length() > 0) && ((mandalw.length() > 0))
                                && (surnamew.length() > 0) && (storagew.length() > 0) && (!spinnerCustomW.getSelectedItem().toString().equals("--Units--")) && (districtw.length()>0) && (statew.length()>0) ) {

                           // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(districtw.length()==0 && statew.length()==0)
                    {  AlertDialog alertDialog = new AlertDialog.Builder(
                            getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("please enter Correct Pincode");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                    }
                    }
                    else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "U have to select Company/Individual", Toast.LENGTH_SHORT).show();
                }



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
                Toast.makeText(getContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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

                    // sendDetailsToServer1();
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.custom_dialog, null);
                    final EditText userInput = (EditText) alertLayout.findViewById(R.id.et_input);
                    userInput.setText(code);
                    final Button btnSave = (Button) alertLayout.findViewById(R.id.btnVerify);
                    final Button btnCancel = (Button) alertLayout.findViewById(R.id.btnCancel);
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Enter OTP:");
                    alert.setIcon(R.drawable.ic_launcher);

                    alert.setView(alertLayout);
                    alert.setCancelable(false);
                    final  AlertDialog dialog = alert.create();
                    dialog.show();
                    btnSave.setOnClickListener(new View.OnClickListener() {

                                                   @Override
                                                   public void onClick(View v) {
                                                       try {
                                                           if (response.get("otp").toString()!=null &&userInput.getText().toString().length()>0  &&
                                                                   response.get("otp").toString().contains(userInput.getText().toString())){
                                                               //Toast.makeText(getContext().getApplicationContext(), "OTP Verified", Toast.LENGTH_SHORT).show();
                                                               sendDetailsToServer1();
                                                               dialog.hide();
                                                           }
                                                           else {
                                                               Toast.makeText(getContext().getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                                                               userInput.setText("");
                                                               dialog.show();

                                                           }
                                                       } catch (JSONException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               });
                    btnCancel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            dialog.hide();
                        }
                    });


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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

    private String getMonthFullName(int monthNumber)
    {
        String monthName="";

        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            } catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }

        return monthName;
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

                         if (obj.has("status1")) {
                             str=obj.getString("status1").toString();

                         }
                         if (obj.has("status2")){

                             str=str+","+obj.getString("status2").toString();

                         }
                         if (obj.has("status3")) {
                             str=str+","+obj.getString("status3").toString();

                         }
                         if ((obj.has("status1")) &&
                                 (obj.has("status2")) &&
                                 (obj.has("status3")) &&
                                 (obj.has("status4"))) {
                             Toast.makeText(getActivity(), "You have no chance to register", Toast.LENGTH_SHORT).show();
                             //dialog.hide();
                         }
                         if(!obj.has("status4")) {
                             strAlert = "You are registered as " + str +
                                     " Do you want to continue?";
                             dialog.hide();
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
                                     relative.setVisibility(View.VISIBLE);
                                 }
                             });
                             continueW.setOnClickListener(new View.OnClickListener() {

                                 @Override
                                 public void onClick(View v) {
                                     getrawdatafromserver();
                                     dialog.hide();
                                     relative.setVisibility(View.VISIBLE);
                                 }
                             });
                         }
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
                Toast.makeText(getContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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
                    JSONArray array2 = json.getJSONArray("masAh");
                    animal=new ArrayList<>();
                    for (int i = 0; i < array2.length(); i++) {
                        final JSONObject obj = array2.getJSONObject(i);
                        animal.add(obj.getString("Animal"));
                    }

                    JSONArray array3 = json.getJSONArray("masDairy");
                    dairy=new ArrayList<>();
                    for (int i = 0; i < array3.length(); i++) {
                        final JSONObject obj = array3.getJSONObject(i);
                        dairy.add(obj.getString("Product_name"));
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
                Toast.makeText(getContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaW.getText().toString());
       // postParam.put("mobile", mobilew.getText().toString());
        postParam.put("strdateOfIncorp",dobw.getText().toString());
        postParam.put("gstnumber",gstw.getText().toString());
        postParam.put("crops",crop);
        postParam.put("vegetables",veg);
        postParam.put("aniHusbandry",anim);
        postParam.put("dairy",dai);
           Log.i("crop",""+crop);

        postParam.put("storageCapacity",storagew.getText().toString());
        postParam.put("units",spinnerCustomW.getSelectedItem().toString());
        Log.i("data sedngbn",""+postParam);
       // Toast.makeText(getContext(), spinnerCustomW.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"stroagereg",new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        if (response.toString().contains("success")){
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    getActivity()).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Successfully Registered");
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();

                           // Toast.makeText(getContext().getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            clearBox();
                            Intent intent=new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                            startActivity(intent); // Intent intent=new Intent(getActivity().getApplicationContext(),FarmerDashboard.class);
                            //startActivity(intent);
                        }
                        else if (response.toString().contains("fail")){
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    getActivity()).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Not Registered");
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                            //Toast.makeText(getContext().getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
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
    private void clearBox()
    {
        namew.setText("");
        surnamew.setText("");
        companyw.setText("");
        addressw.setText("");
        pincodew.setText("");
        villagew.setText("");
        mandalw.setText("");
        districtw.setText("");
        statew.setText("");
        emailw.setText("");
        dobw.setText("");
        gstw.setText("");
        storagew.setText("");
        spinnerCustomW.setSelection(0);
        spinnerAW.setAllUncheckedText("Select Animal");
        spinnerCW.setAllUncheckedText("Select Crop");
        spinnerDW.setAllUncheckedText("Select Dairy");
        spinnerVW.setAllUncheckedText("Select Vegetables");

        //namew.requestFocus();

    }

}
