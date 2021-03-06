package agmark.com.agmarks;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class LogisticsActivity extends Fragment{
    public static EditText namel,surnamel,companyl,addressl,
            pincodel,villagel,mandall,districtl,statel,emaill,mobilel,dobl,gstl,capacityl;
    String rbCheck="",name,surname,company,address,pincode,village,mandal,district,state,email="",mobile,dob,gst;
    EditText mobileDiaL;
    RelativeLayout relative;
    AlertDialog dialog;
    Spinner spinnerCustomVTL,spinnerCustomUL;
    RadioGroup rbg;
    RadioButton rb;
    String code;
    FragmentTransaction transaction;
    Config config=new Config();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl;
    Button submit;
    String strAlert,str;
    Button continueL,cancelL;
    SmsVerifyCatcher smsVerifyCatcher;
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    DatePickerDialog datePickerDialog;
    CountryCodePicker ccp;

    public static LogisticsActivity newInstance() {
        LogisticsActivity fragment = new LogisticsActivity();
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
        v = inflater.inflate(R.layout.activity_logistics, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative4);
        baseUrl= config.get_url();
        spinnerCustomVTL= (Spinner)v.findViewById(R.id.spinnerVTL);
        initCustomSpinnerVTL();
        spinnerCustomUL= (Spinner)v.findViewById(R.id.spinnerUL);
        initCustomSpinnerUL();
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
                        companyl.requestFocus();
                        companyl.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        namel.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        surnamel.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                    }
                    if(rb.getText().toString().equals("Individual")) {
                        clearBox();
                        namel.requestFocus();
                        namel.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        surnamel.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        companyl.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);

                    }
                }
            }
        });
        namel=(EditText)v.findViewById(R.id.nameL);
        namel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        surnamel=(EditText)v.findViewById(R.id.surnameL);
        surnamel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        companyl=(EditText)v.findViewById(R.id.companyL);
        companyl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        addressl=(EditText)v.findViewById(R.id.addressL);
        addressl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        pincodel=(EditText)v.findViewById(R.id.pincodeL);
        pincodel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (pincodel.getText().toString().trim().length()>0){
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
        villagel=(EditText)v.findViewById(R.id.villageL);
        villagel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mandall=(EditText)v.findViewById(R.id.mandalL);
        mandall.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        districtl=(EditText)v.findViewById(R.id.districtL);
        districtl.setEnabled(false);
        districtl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        statel=(EditText)v.findViewById(R.id.stateL);
        statel.setEnabled(false);
        statel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        emaill=(EditText)v.findViewById(R.id.emailL);
        emaill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobilel=(EditText)v.findViewById(R.id.mobileL);
        mobilel.setEnabled(false);

        dobl=(EditText)v.findViewById(R.id.dobL);
        dobl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        dobl.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobl.getInputType(); // backup the input type
                dobl.setInputType(InputType.TYPE_NULL); // disable soft input
                dobl.onTouchEvent(event); // call native handler
                dobl.setInputType(inType); // restore input type
                dobl.requestFocus();
                return true; // consume touch even
            }
        });

        dobl.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobl.getInputType(); // backup the input type
                dobl.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobl.setInputType(inType); // restore input type
                dobl.requestFocus();
                return true; // consume touch even
            }
        });
        dobl.setOnClickListener(new View.OnClickListener() {
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
                                dobl.setText(date_pick_res);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

            }
        });

        capacityl=(EditText)v.findViewById(R.id.capacityL);
        capacityl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobileDiaL=(EditText)alertLayout.findViewById(R.id.editT5);
        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobilel.setText(mobileDiaL.getText().toString());
                String phone = mobileDiaL.getText().toString();
                if (!isPWD(phone)) {
                    mobileDiaL.setError("Invalid Number");
                    return;
                }
                sendDetailsToServer();
            }
        });
        submit=(Button)v.findViewById(R.id.submitL);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !rbCheck.isEmpty()){
                    if(rbCheck.equals("Company")) {
                        if (companyl.getText().toString().trim().equals("")) {
                            companyl.setError("Company Name is required!");
                            companyl.setHint("please enter Company Name");
                        }
                        email = emaill.getText().toString().trim();
                        if (emaill.getText().toString().trim().equals("")) {
                            emaill.setError("Email is required!");
                            emaill.setHint("please enter Email");

                        }

                        if (!email.matches(emailPattern)) {
                            emaill.setError("Please enter correct Email!");

                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if (namel.getText().toString().trim().equals("")) {
                            namel.setError("Name is required!");
                            namel.setHint("please enter Name");
                        }
                        if (surnamel.getText().toString().trim().equals("")) {
                            surnamel.setError("Surname is required!");
                            surnamel.setHint("please enter Surname");
                        }
                    }
                    if (addressl.getText().toString().trim().equals("")) {
                        addressl.setError("Address is required!");
                        addressl.setHint("please enter Address");
                    }
                    if (pincodel.getText().toString().trim().equals("")) {
                        pincodel.setError("Pincode is required!");
                        pincodel.setHint("please enter Pincode");

                    }
                    if (villagel.getText().toString().trim().equals("")) {
                        villagel.setError("Village Name is required!");
                        villagel.setHint("please enter Village name");

                    }
                    if (mandall.getText().toString().trim().equals("")) {
                        mandall.setError("Mandal is required!");
                        mandall.setHint("please enter Mandal");

                    }
                    if(spinnerCustomVTL.getSelectedItem().toString().equals("Vehicle Type")) {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog1.setTitle("Alert");
                        alertDialog1.setMessage("please select Vehicle Type");
                        alertDialog1.setIcon(R.drawable.tick);
                        alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog1.show();
                    }


                    if (capacityl.getText().toString().trim().equals("")) {
                        capacityl.setError("Capacity is required!");
                        capacityl.setHint("please enter Capacity");
                    }
                        if (spinnerCustomUL.getSelectedItem().toString().equals("--Units--")) {
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
                        if ((companyl.length() > 0) && (addressl.length() > 0) && (pincodel.length() > 0) && (villagel.length() > 0) && ((mandall.length() > 0))
                                && (emaill.length() > 0)  && ( email.matches(emailPattern) && (districtl.length()>0) && (statel.length()>0)) && (!spinnerCustomVTL.getSelectedItem().toString().equals("Vehicle Type")) && (!spinnerCustomUL.getSelectedItem().toString().equals("--Units--")) && (capacityl.length()>0)) {
                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if ((namel.length() > 0) && (addressl.length() > 0) && (pincodel.length() > 0) && (villagel.length() > 0) && ((mandall.length() > 0))
                                && (surnamel.length() > 0)  && (districtl.length()>0) && (statel.length()>0) && (!spinnerCustomVTL.getSelectedItem().toString().equals("Vehicle Type")) && (!spinnerCustomUL.getSelectedItem().toString().equals("--Units--")) && (capacityl.length()>0)) {

                            //Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
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

    private void sendDetailsToServer2() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaL.getText().toString());

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

    private void sendDetailsToServer1() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("status",rb.getText().toString());
        postParam.put("firstName", namel.getText().toString());
        postParam.put("lastName", surnamel.getText().toString());
        postParam.put("companyName",companyl.getText().toString());
        postParam.put("address", addressl.getText().toString());
        postParam.put("pincode",pincodel.getText().toString());
        postParam.put("village", villagel.getText().toString());
        postParam.put("mandal", mandall.getText().toString());
        postParam.put("district", districtl.getText().toString());
        postParam.put("state", statel.getText().toString());
        postParam.put("email",emaill.getText().toString());
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaL.getText().toString());
       // postParam.put("mobile", mobilel.getText().toString());
        postParam.put("dateOfIncorp",dobl.getText().toString());
        postParam.put("vehicleType",spinnerCustomVTL.getSelectedItem().toString());
        postParam.put("vehicleCapacity",capacityl.getText().toString());
        postParam.put("vehicleCapcityUnits",spinnerCustomUL.getSelectedItem().toString());

        Log.i("data sedngbn",""+postParam);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"logisticsreg",new JSONObject(postParam),
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
                                    clearBox();

                                    Intent intent=new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                                    startActivity(intent);
                                }
                            });
                            alertDialog.show();

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
                          //  Toast.makeText(getContext().getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
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

    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaL.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"mobileduplicate",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());

                if (response.toString().contains("\"userlist\":\"\"")){
                    dialog.hide();
                    relative.setVisibility(View.VISIBLE);
                }
                else if (response.toString().contains("Logistics")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Logistics", Toast.LENGTH_SHORT).show();
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
                                continueL = (Button) alertLayout.findViewById(R.id.continueP);
                                cancelL = (Button) alertLayout.findViewById(R.id.cancelP);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                dialog = alert.create();
                                dialog.show();
                                cancelL.setOnClickListener(new View.OnClickListener() {
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
                                continueL.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        dialog.hide();
                                        relative.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                            Log.i("obvsjvbld", obj.getString("status1"));
                        }                    } catch (JSONException e) {
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
        postParam.put("mobile", mobileDiaL.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"logisticsduplicatecheck",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());
                if (response.toString().contains("Alredy exist")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
                    transaction=getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if (response.toString().contains("not exist")){
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

    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("pincode", pincodel.getText().toString());

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
                    districtl.setText(obj.getString("District"));
                    statel.setText(obj.getString("State"));
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


    private void initCustomSpinnerVTL() {
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Vehicle Type");
        languages.add("Lorry");
        languages.add("Tractor");
        languages.add("Truck");
        languages.add("Ape/TataAce");
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), languages);
        spinnerCustomVTL.setAdapter(customSpinnerAdapter);
        spinnerCustomVTL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initCustomSpinnerUL() {
            ArrayList<String> languages = new ArrayList<String>();
            languages.add("--Units--");
            languages.add("Tonnes");
            languages.add("Quintal");
            CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(), languages);
            spinnerCustomUL.setAdapter(customSpinnerAdapter);
            spinnerCustomUL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    private boolean isPWD(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }
    private void clearBox()
    {
        namel.setText("");
        surnamel.setText("");
        companyl.setText("");
        addressl.setText("");
        pincodel.setText("");
        villagel.setText("");
        mandall.setText("");
        districtl.setText("");
        statel.setText("");
        emaill.setText("");
        dobl.setText("");
        //gstl.setText("");
        spinnerCustomVTL.setSelection(0);
        capacityl.setText("");
        spinnerCustomUL.setSelection(0);
       // namel.requestFocus();
    }


}
