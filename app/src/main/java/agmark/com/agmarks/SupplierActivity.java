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
import android.widget.LinearLayout;
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

public class SupplierActivity extends Fragment {
    public static EditText names,surnames,companys,addresss,
            pincodes,villages,mandals,districts,states,emails,mobiles,dobs,gsts;
    String strStatus="",rbCheck="",name,surname,company,address,pincode,village,mandal,district,state,email="",mobile,dob,gst;
    MultiSelectSpinner spinnerBS,spinnerBOS,spinnerFMS,spinnerIS,spinnerOS,spinnerSS;
    RelativeLayout relative;
    AlertDialog dialog,dialog1;
    FragmentTransaction transaction;
    ArrayAdapter<String> adapterC;
    Config config=new Config();
    CountryCodePicker ccp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl;
    String strAlert,str;
    EditText mobileDiaS;
    DatePickerDialog datePickerDialog;
    Button submit;
    LinearLayout linearUnit;
    Button continueS,cancelS;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    EditText etUnit,etMain;
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
        linearUnit=(LinearLayout)v.findViewById(R.id.linearUnit);
        etUnit=(EditText)v.findViewById(R.id.etUnit);
        etMain=(EditText)v.findViewById(R.id.etMain);

        rbg=(RadioGroup) v.findViewById(R.id.radioGroup);
        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rbCheck=rb.getText().toString();
                    clearBox();
                    if(rb.getText().toString().equals("Unit Distributor")) {

                        linearUnit.setVisibility(View.VISIBLE);
                        strStatus="Unit Distributor";

                    }
                    if(rb.getText().toString().equals("Individual")) {
                        linearUnit.setVisibility(View.GONE);
                        strStatus="Individual";

                    }
                    if(rb.getText().toString().equals("Central Distributor")) {

                        linearUnit.setVisibility(View.GONE);
                        strStatus="Central Distributor";
                    }


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
        dobs.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobs.getInputType(); // backup the input type
                dobs.setInputType(InputType.TYPE_NULL); // disable soft input
                dobs.onTouchEvent(event); // call native handler
                dobs.setInputType(inType); // restore input type
                dobs.requestFocus();
                return true; // consume touch even
            }
        });
        dobs.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobs.getInputType(); // backup the input type
                dobs.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobs.setInputType(inType); // restore input type
                dobs.requestFocus();
                return true; // consume touch even
            }
        });
        dobs.setOnClickListener(new View.OnClickListener() {
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
                                dobs.setText(date_pick_res);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

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
        ccp=(CountryCodePicker)alertLayout.findViewById(R.id.countrycode);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Toast.makeText(getContext(), "Code is:"+ccp.getSelectedCountryCode(), Toast.LENGTH_SHORT).show();
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
               // dialog.hide();
               // relative.setVisibility(View.VISIBLE);
                sendDetailsToServer();

            }
        });

        submit=(Button)v.findViewById(R.id.submitS);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !rbCheck.isEmpty()){

                     /*   if (!email.matches(emailPattern)) {
                            emails.setError("Please enter correct Email!");

                        }
*/

                        if (names.getText().toString().trim().equals("")) {
                            names.setError("Name is required!");
                            names.setHint("please enter Name");
                        }
                        if (surnames.getText().toString().trim().equals("")) {
                            surnames.setError("Surname is required!");
                            surnames.setHint("please enter Surname");
                        }


                    if (addresss.getText().toString().trim().equals("")) {
                        addresss.setError("Address is required!");
                        addresss.setHint("please enter Address");
                    }
                    if (pincodes.getText().toString().trim().equals("")) {
                        pincodes.setError("Pincode is required!");
                        pincodes.setHint("please enter Pincode");

                    }
                    if (villages.getText().toString().trim().equals("")) {
                        villages.setError("Village Name is required!");
                        villages.setHint("please enter Village name");

                    }
                    if (mandals.getText().toString().trim().equals("")) {
                        mandals.setError("Mandal is required!");
                        mandals.setHint("please enter Mandal");

                    }
                    if(rbCheck.equals("Unit Distributor")) {
                        if (etMain.getText().toString().trim().equals("")) {
                            etMain.setError("Main Code is required!");
                            etMain.setHint("please enter Main Code");

                        }
                        if (etUnit.getText().toString().trim().equals("")) {
                            etUnit.setError("Unit Code is required!");
                            etUnit.setHint("please enter Unit Code");

                        }
                        //&&   ( email.matches(emailPattern)
                    }
                    if(rbCheck.equals("Unit Distributor")) {
                        if ( (addresss.length() > 0) && (pincodes.length() > 0) && (villages.length() > 0) && ((mandals.length() > 0))
                                 && (districts.length()>0) && (states.length()>0) && (etUnit.length()>0) && (etMain.length()>0)) {
                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsForOTP();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(rbCheck.equals("Individual") || rbCheck.equals("Central Distributor") ) {
                        if ((names.length() > 0) && (addresss.length() > 0) && (pincodes.length() > 0) && (villages.length() > 0) && ((mandals.length() > 0))
                                && (surnames.length() > 0)  && (districts.length()>0) && (states.length()>0) ) {

                           // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsForOTP();
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

    private void sendDetailsForOTP() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaS.getText().toString());

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
        postParam.put("activeStatus","Active");
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
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaS.getText().toString());
       // postParam.put("mobile", mobiles.getText().toString());
        postParam.put("strdateOfIncorp",dobs.getText().toString());
        postParam.put("gstnumber",gsts.getText().toString());
        postParam.put("bio",bioS);
        postParam.put("botanical",botanicalS);
        postParam.put("farmMachinery",farmS);
        postParam.put("inorganic",inorganicS);
        postParam.put("organic",organicS);
        postParam.put("seed",seedS);
        postParam.put("distributorStatus",strStatus);

        if(strStatus.equalsIgnoreCase("Unit Distributor")) {
            postParam.put("branchCode", etUnit.getText().toString().trim());
            postParam.put("masterCode", etMain.getText().toString().trim()+"UD");
        }
        if(strStatus.equalsIgnoreCase("Central Distributor")) {
            postParam.put("masterCode", mobiles.getText().toString()+"CD");
        }
        if(strStatus.equalsIgnoreCase("Individual")) {
            postParam.put("masterCode", mobiles.getText().toString()+"IN");
        }

        Log.i("SupplierData",""+postParam);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"supplierreg",new JSONObject(postParam),
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
                                    startActivity(intent);// Intent intent=new Intent(getActivity().getApplicationContext(),FarmerDashboard.class);

                                }
                            });
                            alertDialog.show();
                           // Toast.makeText(getContext().getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
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
                            clearBox();
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
                                continueS = (Button) alertLayout.findViewById(R.id.continueP);
                                cancelS = (Button) alertLayout.findViewById(R.id.cancelP);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                dialog1 = alert.create();
                                dialog1.show();
                                cancelS.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                        dialog1.hide();
                                        relative.setVisibility(View.VISIBLE);
                                    }
                                });
                                continueS.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        dialog1.hide();
                                        relative.setVisibility(View.VISIBLE);
                                        getsupplierslistfromserver();
                                    }
                                });
                            }
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
                    String str2=obj2.getString("input").toString();
                    String[] words2=str2.split(",");
                    for(String w:words2){
                        botanical.add(w);
                    }
                    final JSONObject obj3 = array1.getJSONObject(2);
                    String str3=obj3.getString("input").toString();
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
                        seed.add(w);
                    }

                     adapterC = new ArrayAdapter <String>(getContext(),
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
        names.setText("");
        surnames.setText("");
        companys.setText("");
        addresss.setText("");
        pincodes.setText("");
        villages.setText("");
        mandals.setText("");
        districts.setText("");
        states.setText("");
        emails.setText("");
        dobs.setText("");
        gsts.setText("");
        etUnit.setText("");
        etMain.setText("");

        spinnerBS.clearFocus();
        spinnerBS.setSelection(0);
       // spinnerBOS.setSelectAll(false);
        spinnerBOS.setAllUncheckedText("Select Botanical");
        spinnerFMS.setAllUncheckedText("Select FarmMachinery");
        spinnerIS.setAllUncheckedText("Select Inorganic");
        spinnerOS.setAllUncheckedText("Select Organic");
        spinnerSS.setAllUncheckedText("Select Seed");
        //names.requestFocus();
    }

}
