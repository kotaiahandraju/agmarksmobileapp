package agmark.com.agmarks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import static agmark.com.agmarks.WarehouseActivity.namew;

public class VendorActivity extends Fragment {
    public static EditText namev,surnamev,companyv,addressv,
            pincodev,villagev,mandalv,districtv,statev,emailv,
            mobilev,dobv,gstv;
    String rbCheck="",name,surname,company,address,pincode,village,mandal,district,state,email="",mobile,dob,gst;
    MultiSelectSpinner spinnerVV,spinnerAV,spinnerDV;
    EditText mobileDiaV;
    RelativeLayout relative;
    AlertDialog dialog;
    RadioGroup rbg;
    RadioButton rb;
    String strAlert,str;
    FragmentTransaction transaction;
    Config config=new Config();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl;
    Button submit;
    Button continueV,cancelV;
    int vegc,animc,daic=0;
    SmsVerifyCatcher smsVerifyCatcher;
    ArrayList<String> vegetables,animal,dairy;
    String code;
    String veg="",anim="",dai="";
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    DatePickerDialog datePickerDialog;
    CountryCodePicker ccp;

    public static VendorActivity newInstance() {
        VendorActivity fragment = new VendorActivity();
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
        v = inflater.inflate(R.layout.activity_vendor, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative6);
        spinnerVV = (MultiSelectSpinner)v. findViewById(R.id.spinnerVV);
        spinnerAV = (MultiSelectSpinner) v.findViewById(R.id.spinnerAV);
        spinnerDV = (MultiSelectSpinner) v.findViewById(R.id.spinnerDV);
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
                        companyv.requestFocus();
                        companyv.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        emailv.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        namev.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        surnamev.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);

                    }
                    if(rb.getText().toString().equals("Individual")) {
                        clearBox();
                        namev.requestFocus();
                        namev.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        surnamev.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        emailv.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        companyv.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);

                    }
                }
            }
        });
        namev=(EditText)v.findViewById(R.id.nameV);
        namev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        surnamev=(EditText)v.findViewById(R.id.surnameV);
        surnamev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        companyv=(EditText)v.findViewById(R.id.companyV);
        companyv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        addressv=(EditText)v.findViewById(R.id.addressV);
        addressv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        pincodev=(EditText)v.findViewById(R.id.pincodeV);
        pincodev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (pincodev.getText().toString().trim().length()>0){
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
        villagev=(EditText)v.findViewById(R.id.villageV);
        villagev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mandalv=(EditText)v.findViewById(R.id.mandalV);
        mandalv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        districtv=(EditText)v.findViewById(R.id.districtV);
        districtv.setEnabled(false);
        districtv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        statev=(EditText)v.findViewById(R.id.stateV);
        statev.setEnabled(false);
        statev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        emailv=(EditText)v.findViewById(R.id.emailV);
        emailv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobilev=(EditText)v.findViewById(R.id.mobileV);
        mobilev.setEnabled(false);

        dobv=(EditText)v.findViewById(R.id.dobV);
        dobv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        dobv.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobv.getInputType(); // backup the input type
                dobv.setInputType(InputType.TYPE_NULL); // disable soft input
                dobv.onTouchEvent(event); // call native handler
                dobv.setInputType(inType); // restore input type
                dobv.requestFocus();
                return true; // consume touch even
            }
        });
        dobv.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobv.getInputType(); // backup the input type
                dobv.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobv.setInputType(inType); // restore input type
                dobv.requestFocus();
                return true; // consume touch even
            }
        });
        dobv.setOnClickListener(new View.OnClickListener() {
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
                                dobv.setText(date_pick_res);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

            }
        });
        gstv=(EditText)v.findViewById(R.id.gstV);
        gstv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobileDiaV=(EditText)alertLayout.findViewById(R.id.editT5);
        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobilev.setText(mobileDiaV.getText().toString());
                String phone = mobileDiaV.getText().toString();
                if (!isPWD(phone)) {
                    mobileDiaV.setError("Invalid Number");
                    return;
                }
                sendDetailsToServer();
            }
        });
        submit=(Button)v.findViewById(R.id.submitV);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !rbCheck.isEmpty()){
                    if(rbCheck.equals("Company")) {
                        if (companyv.getText().toString().trim().equals("")) {
                            companyv.setError("Company Name is required!");
                            companyv.setHint("please enter Company Name");
                        }
                        email = emailv.getText().toString().trim();
                        if (emailv.getText().toString().trim().equals("")) {
                            emailv.setError("Email is required!");
                            emailv.setHint("please enter Email");

                        }
                        if (!email.matches(emailPattern)) {
                            emailv.setError("Please enter correct Email!");
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if (namev.getText().toString().trim().equals("")) {
                            namev.setError("Name is required!");
                            namev.setHint("please enter Name");
                        }
                        if (surnamev.getText().toString().trim().equals("")) {
                            surnamev.setError("Surname is required!");
                            surnamev.setHint("please enter Surname");
                        }
                        email = emailv.getText().toString().trim();
                        if (emailv.getText().toString().trim().equals("")) {
                            emailv.setError("Email is required!");
                            emailv.setHint("please enter Email");

                        }
                        if (!email.matches(emailPattern)) {
                            emailv.setError("Please enter correct Email!");
                        }
                    }

                    if (addressv.getText().toString().trim().equals("")) {
                        addressv.setError("Address is required!");
                        addressv.setHint("please enter Address");
                    }
                    if (pincodev.getText().toString().trim().equals("")) {
                        pincodev.setError("Pincode is required!");
                        pincodev.setHint("please enter Pincode");

                    }
                    if (villagev.getText().toString().trim().equals("")) {
                        villagev.setError("Village Name is required!");
                        villagev.setHint("please enter Village name");
                    }
                    if (mandalv.getText().toString().trim().equals("")) {
                        mandalv.setError("Mandal is required!");
                        mandalv.setHint("please enter Mandal");
                    }
                    if(rbCheck.equals("Company")) {
                        if ((companyv.length() > 0) && (addressv.length() > 0) && (pincodev.length() > 0) && (villagev.length() > 0) && ((mandalv.length() > 0))
                                && (emailv.length() > 0)  && ( email.matches(emailPattern) && (districtv.length()>0) && (statev.length()>0))) {
                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if ((namev.length() > 0) && (addressv.length() > 0) && (pincodev.length() > 0) && (villagev.length() > 0) && ((mandalv.length() > 0))
                                && (surnamev.length() > 0)  && (districtv.length()>0) && (statev.length()>0) ) {
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
        postParam.put("mobile", mobileDiaV.getText().toString());

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
                               // Toast.makeText(getContext().getApplicationContext(), "OTP Verified", Toast.LENGTH_SHORT).show();
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
        postParam.put("firstName", namev.getText().toString());
        postParam.put("lastName", surnamev.getText().toString());
        postParam.put("companyName",companyv.getText().toString());
        postParam.put("address", addressv.getText().toString());
        postParam.put("pincode",pincodev.getText().toString());
        postParam.put("village", villagev.getText().toString());
        postParam.put("mandal", mandalv.getText().toString());
        postParam.put("district", districtv.getText().toString());
        postParam.put("state", statev.getText().toString());
        postParam.put("email",emailv.getText().toString());
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaV.getText().toString());
       // postParam.put("mobile", mobilev.getText().toString());
        postParam.put("dateOfIncorp",dobv.getText().toString());
        postParam.put("gstnumber",gstv.getText().toString());
        postParam.put("vegetables",veg);
        postParam.put("aniHus",anim);
        postParam.put("dairy",dai);

        Log.i("data sedngbn",""+postParam);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"vendorreg",new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //  Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
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
                                    startActivity(intent);//Intent intent=new Intent(getActivity().getApplicationContext(),FarmerDashboard.class);

                                }
                            });
                            alertDialog.show();

                            //Toast.makeText(getContext().getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);
                        }
                        else if (response.toString().contains("fail")){
                          //  Toast.makeText(getContext().getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
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
        postParam.put("mobile", mobileDiaV.getText().toString());

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
                else if (response.toString().contains("Vendor")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Vendor", Toast.LENGTH_SHORT).show();
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
                                continueV = (Button) alertLayout.findViewById(R.id.continueP);
                                cancelV = (Button) alertLayout.findViewById(R.id.cancelP);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                dialog = alert.create();
                                dialog.show();
                                cancelV.setOnClickListener(new View.OnClickListener() {
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
                                continueV.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        getrawdatafromserver();
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

    private void getrawdatafromserver() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaV.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"getrawdata",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                /*Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());*/
                sendDetailsToServer0();
                JSONObject json= null;
                try {
                    json = new JSONObject(response.toString());

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

                    ArrayAdapter<String> adapterV = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, vegetables);
                    ArrayAdapter<String> adapterA = new ArrayAdapter <String>(getContext(),
                            android.R.layout.simple_list_item_checked, animal);
                    ArrayAdapter<String> adapterD = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_checked, dairy);

                    spinnerVV.setListAdapter(adapterV).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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
                            if(vegc>8) {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        getActivity()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("U Have to Select Only 8 Vegetables");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }


                            Log.i("countVeg:",""+vegc);
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Vegetables")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0).setMaxSelectedItems(8);

                    spinnerAV.setListAdapter(adapterA).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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
                            if(animc>3) {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        getActivity()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("U Have to Select Only 3 Animals");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }

                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Animal")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0).setMaxSelectedItems(3);

                    spinnerDV.setListAdapter(adapterD).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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
                            if(daic>3) {
                                AlertDialog alertDialog = new AlertDialog.Builder(
                                        getActivity()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("U Have to Select Only 3 Dairy");
                                alertDialog.setIcon(R.drawable.tick);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }

                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Dairy")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0).setMaxSelectedItems(3);

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

    private void sendDetailsToServer0() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaV.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"vendorduplicatecheck",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());
                if (response.toString().contains("Alredy exist")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Vendor", Toast.LENGTH_SHORT).show();
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

    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("pincode", pincodev.getText().toString());

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
                    districtv.setText(obj.getString("District"));
                    statev.setText(obj.getString("State"));
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


    private boolean isPWD(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }

    private void clearBox()
    {
        namev.setText("");
        surnamev.setText("");
        companyv.setText("");
        addressv.setText("");
        pincodev.setText("");
        villagev.setText("");
        mandalv.setText("");
        districtv.setText("");
        statev.setText("");
        emailv.setText("");
        dobv.setText("");
        gstv.setText("");

        spinnerDV.setAllCheckedText("Select Dairy");
        spinnerAV.setAllCheckedText("Select Animal");
        spinnerVV.setAllCheckedText("Select Vegetables");

//        namew.requestFocus();

    }

}
