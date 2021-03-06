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
import android.widget.AdapterView;
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

/**
 * Created by Admin on 12-04-2018.
 */

public class TraderActivity extends Fragment{
    public static EditText namet,surnamet,companyt,addresst,
            pincodet,villaget,mandalt,districtt,statet,emailt,mobilet,dobt,gstt;
    String rbCheck="",name,surname,company,address,pincode,village,mandal,district,state,email="",mobile,dob,gst;
    MultiSelectSpinner spinnerCT,spinnerVT,spinnerAT,spinnerDT;
    ArrayList<String> crops,vegetables,animal,dairy;
    RelativeLayout relative;
    AlertDialog dialog;
    FragmentTransaction transaction;
    Config config=new Config();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl,str;
    String strAlert="";
    EditText mobileDiaT;
    Button submit;
    Button continueT,cancelT;
    int cropc,vegc,animc,daic=0;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    String crop="",veg="",anim="",dai="";
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    RadioGroup rbg;
    RadioButton rb;
   DatePickerDialog datePickerDialog;
   CountryCodePicker ccp;

    public static TraderActivity newInstance() {
        TraderActivity fragment = new TraderActivity();
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
        v = inflater.inflate(R.layout.activity_trader, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative5);
        spinnerCT= (MultiSelectSpinner) v.findViewById(R.id.spinnerCT);
        spinnerVT = (MultiSelectSpinner)v. findViewById(R.id.spinnerVT);
        spinnerAT = (MultiSelectSpinner) v.findViewById(R.id.spinnerAT);
        spinnerDT = (MultiSelectSpinner) v.findViewById(R.id.spinnerDT);
        baseUrl= config.get_url();

        smsVerifyCatcher  = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code  = parseCode(message);
                Log.i("Message:--",code);
            }
        });
        rbg=(RadioGroup) v.findViewById(R.id.radioGroup);
        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb= (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rbCheck=rb.getText().toString();
                    if(rb.getText().toString().equals("Company")) {
                        clearBox();
                        companyt.requestFocus();
                        companyt.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        emailt.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        namet.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        surnamet.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                    }
                    if(rb.getText().toString().equals("Individual")) {
                        clearBox();
                        namet.requestFocus();
                        namet.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        surnamet.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        emailt.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        companyt.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);

                    }
                }
            }
        });
        namet=(EditText)v.findViewById(R.id.nameT);
        namet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        surnamet=(EditText)v.findViewById(R.id.surnameT);
        surnamet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        companyt=(EditText)v.findViewById(R.id.companyT);
        companyt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        addresst=(EditText)v.findViewById(R.id.addressT);
        addresst.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        pincodet=(EditText)v.findViewById(R.id.pincodeT);
        pincodet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (pincodet.getText().toString().trim().length()>0){
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
        villaget=(EditText)v.findViewById(R.id.villageT);
        villaget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mandalt=(EditText)v.findViewById(R.id.mandalT);
        mandalt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        districtt=(EditText)v.findViewById(R.id.districtT);
        districtt.setEnabled(false);
        districtt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        statet=(EditText)v.findViewById(R.id.stateT);
        statet.setEnabled(false);
        statet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        emailt=(EditText)v.findViewById(R.id.emailT);
        emailt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobilet=(EditText)v.findViewById(R.id.mobileT);
        mobilet.setEnabled(false);

        dobt=(EditText)v.findViewById(R.id.dobT);
        dobt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        dobt.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobt.getInputType(); // backup the input type
                dobt.setInputType(InputType.TYPE_NULL); // disable soft input
                dobt.onTouchEvent(event); // call native handler
                dobt.setInputType(inType); // restore input type
                dobt.requestFocus();
                return true; // consume touch even
            }
        });
        dobt.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobt.getInputType(); // backup the input type
                dobt.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobt.setInputType(inType); // restore input type
                dobt.requestFocus();
                return true; // consume touch even
            }
        });
        dobt.setOnClickListener(new View.OnClickListener() {
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
                                dobt.setText(date_pick_res);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

            }
        });
        gstt=(EditText)v.findViewById(R.id.gstT);
        gstt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobileDiaT=(EditText)alertLayout.findViewById(R.id.editT5);
        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobilet.setText(mobileDiaT.getText().toString());
                String phone = mobileDiaT.getText().toString();
                if (!isPWD(phone)) {
                    mobileDiaT.setError("Invalid Number");
                    return;
                }
                //dialog.hide();
                //relative.setVisibility(View.VISIBLE);
                sendDetailsToServer();
            }
        });
        submit=(Button)v.findViewById(R.id.submitT);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !rbCheck.isEmpty()){
                    if(rbCheck.equals("Company")) {
                        if (companyt.getText().toString().trim().equals("")) {
                            companyt.setError("Company Name is required!");
                            companyt.setHint("please enter Company Name");
                        }
                        email = emailt.getText().toString().trim();
                        if (emailt.getText().toString().trim().equals("")) {
                            emailt.setError("Email is required!");
                            emailt.setHint("please enter Email");

                        }

                        if (!email.matches(emailPattern)) {
                            emailt.setError("Please enter correct Email!");

                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if (namet.getText().toString().trim().equals("")) {
                            namet.setError("Name is required!");
                            namet.setHint("please enter Name");
                        }
                        if (surnamet.getText().toString().trim().equals("")) {
                            surnamet.setError("Surname is required!");
                            surnamet.setHint("please enter Surname");
                        }
                    }

                    if (addresst.getText().toString().trim().equals("")) {
                        addresst.setError("Address is required!");
                        addresst.setHint("please enter Address");
                    }
                    if (pincodet.getText().toString().trim().equals("")) {
                        pincodet.setError("Pincode is required!");
                        pincodet.setHint("please enter Pincode");

                    }
                    if (villaget.getText().toString().trim().equals("")) {
                        villaget.setError("Village Name is required!");
                        villaget.setHint("please enter Village name");

                    }
                    if (mandalt.getText().toString().trim().equals("")) {
                        mandalt.setError("Mandal is required!");
                        mandalt.setHint("please enter Mandal");

                    }
                    if(rbCheck.equals("Company")) {
                        if ((companyt.length() > 0) && (addresst.length() > 0) && (pincodet.length() > 0) && (villaget.length() > 0) && ((mandalt.length() > 0))
                                && (emailt.length() > 0)  && ( email.matches(emailPattern) && (districtt.length()>0) && (statet.length()>0))) {

                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if ((namet.length() > 0) && (addresst.length() > 0) && (pincodet.length() > 0) && (villaget.length() > 0) && ((mandalt.length() > 0))
                                && (surnamet.length() > 0)  && (districtt.length()>0) && (statet.length()>0) ) {

                           // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
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

    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("pincode", pincodet.getText().toString());

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
                    districtt.setText(obj.getString("District"));
                    statet.setText(obj.getString("State"));
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

    private void sendDetailsToServer2() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaT.getText().toString());

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

    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaT.getText().toString());

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
                else if (response.toString().contains("Trader")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Trader", Toast.LENGTH_SHORT).show();
                    dialog.show();
                    relative.setVisibility(View.INVISIBLE);
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
                                continueT = (Button) alertLayout.findViewById(R.id.continueP);
                                cancelT = (Button) alertLayout.findViewById(R.id.cancelP);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                dialog = alert.create();
                                dialog.show();
                                cancelT.setOnClickListener(new View.OnClickListener() {
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
                                continueT.setOnClickListener(new View.OnClickListener() {

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

    private void sendDetailsToServer0() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaT.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"tradeduplicatecheck",
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
                    getrawdatafromserver();
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
        postParam.put("mobile", mobileDiaT.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"getrawdata",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                /*Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());*/
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

                    spinnerCT.setListAdapter(adapterC).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0).setMaxSelectedItems(5);
                    spinnerCT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerCT.setSelection(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerCT.setSelection(0);
                        }
                    });

                    spinnerVT.setListAdapter(adapterV).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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


                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Vegetables")
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0).setMaxSelectedItems(8);
                    spinnerVT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerVT.setSelection(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerVT.setSelection(0);
                        }
                    });


                    spinnerAT.setListAdapter(adapterA).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select AnimalHusbandry")
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0).setMaxSelectedItems(3);
                    spinnerAT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerAT.setSelection(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerAT.setSelection(0);
                        }
                    });


                    spinnerDT.setListAdapter(adapterD).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
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
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0).setMaxSelectedItems(3);
                    spinnerDT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerDT.setSelection(position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            spinnerDT.setSelection(0);
                        }
                    });
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
        postParam.put("firstName", namet.getText().toString());
        postParam.put("lastName", surnamet.getText().toString());
        postParam.put("companyName",companyt.getText().toString());
        postParam.put("address", addresst.getText().toString());
        postParam.put("pincode",pincodet.getText().toString());
        postParam.put("village", villaget.getText().toString());
        postParam.put("mandal", mandalt.getText().toString());
        postParam.put("district", districtt.getText().toString());
        postParam.put("state", statet.getText().toString());
        postParam.put("email",emailt.getText().toString());
        postParam.put("strdateOfIncorp",dobt.getText().toString());
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaT.getText().toString());
      //  postParam.put("mobile", mobilet.getText().toString());
        postParam.put("gstnumber",gstt.getText().toString());
        postParam.put("crops",crop);
        postParam.put("vegetables",veg);
        postParam.put("aniHusbandry",anim);
        postParam.put("dairy",dai);

        Log.i("data sedngbn",""+postParam);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"traderreg",new JSONObject(postParam),
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
                                    startActivity(intent);// Intent intent=new Intent(getActivity().getApplicationContext(),FarmerDashboard.class);

                                }
                            });
                            alertDialog.show();
                            //Toast.makeText(getContext().getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
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
                           // Toast.makeText(getContext().getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
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

    private boolean isspinnerCT() {
        if(spinnerCT.getSelectedItem().toString().equals("Select Crop")) {
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

    private boolean isspinnerVT(){
        if(spinnerVT.getSelectedItem().toString().equals("Select Vegetables")) {
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

    private boolean isspinnerAT(){
        if(spinnerAT.getSelectedItem().toString().equals("Select Animal") ) {
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

    private boolean isspinnerDT () {
        if (spinnerDT.getSelectedItem().toString().equals("Select Dairy")) {
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
    private void clearBox()
    {
        namet.setText("");
        surnamet.setText("");
        companyt.setText("");
        addresst.setText("");
        pincodet.setText("");
        villaget.setText("");
        mandalt.setText("");
        districtt.setText("");
        statet.setText("");
        emailt.setText("");
        dobt.setText("");
        gstt.setText("");
        spinnerAT.setAllCheckedText("Select Animal");
        spinnerCT.setAllCheckedText("Select Crop");
        spinnerDT.setAllCheckedText("Select Dairy");
        spinnerVT.setAllCheckedText("Select Vegetables");

        //namet.requestFocus();
    }


}
