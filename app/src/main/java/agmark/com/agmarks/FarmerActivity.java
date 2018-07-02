package agmark.com.agmarks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;

/**
 * Created by Admin on 16-05-2018.
 */

public class FarmerActivity extends Fragment {
   public static EditText namef,surnamef,dobf,aadharf,addressf,villagef,mandalf,districtf,statef;
    public static EditText mobilef,pincodef;
    Button submit;
    RelativeLayout relative;
    String phone,pin,name,surname,dob,aadhar,address,village,mandal;
    AlertDialog dialog,dialog1;
    EditText mobileDiaF,pinDiaF,dobF;
    FragmentTransaction transaction;
    Config config=new Config();
    String baseUrl;
    String strAlert,str="",croptype="",vegtype="",animtype="",dairytype="";
    Button continueF,cancelF;
    int cropc,vegc,animc,daic=0;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    String[] crop,veg,anim,dai;
    MultiSelectSpinner spinnerCF,spinnerVF,spinnerAF,spinnerDF;
    ArrayList<String> crops,vegetables,animal,dairy;
    DatePickerDialog datePickerDialog;
    CountryCodePicker ccp;


    public static FarmerActivity newInstance() {
        FarmerActivity fragment = new FarmerActivity();
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
        v = inflater.inflate(R.layout.activity_farmer, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative);
        spinnerCF= (MultiSelectSpinner) v.findViewById(R.id.spinnerCF);
        spinnerVF = (MultiSelectSpinner)v. findViewById(R.id.spinnerVF);
        spinnerAF = (MultiSelectSpinner) v.findViewById(R.id.spinnerAF);
        spinnerDF = (MultiSelectSpinner) v.findViewById(R.id.spinnerDF);
        baseUrl= config.get_url();

        animal=new ArrayList<>();
        animal.add("Cow");
        animal.add("Buffalo");
        animal.add("Sheep");
        animal.add("Goat");
        animal.add("Cattle");
        animal.add("Poultry");

        dairy=new ArrayList<>();
        dairy.add("Milk");
        dairy.add("Curd");
        dairy.add("Butter");
        dairy.add("Paneer");
        dairy.add("Ghee");
        dairy.add("Khowa");

        smsVerifyCatcher  = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code  = parseCode(message);
                Log.i("Message:--",code);
            }
        });

        namef=(EditText)v.findViewById(R.id.nameF);
       // namef.setHint(Html.fromHtml("Name<sup>*</sup>"));
        namef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        surnamef=(EditText)v.findViewById(R.id.surnameF);
        surnamef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        dobf=(EditText)v.findViewById(R.id.dobF);
        dobf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        dobf.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobf.getInputType(); // backup the input type
                dobf.setInputType(InputType.TYPE_NULL); // disable soft input
                dobf.onTouchEvent(event); // call native handler
                dobf.setInputType(inType); // restore input type
                dobf.requestFocus();
                return true; // consume touch even
            }
        });
        dobf.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobf.getInputType(); // backup the input type
                dobf.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobf.setInputType(inType); // restore input type
                dobf.requestFocus();
                return true; // consume touch even
            }
        });
        dobf.setOnClickListener(new View.OnClickListener() {
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
                                dobf.setText(date_pick_res);
                                //dobf.setEnabled(false);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
        mobilef=(EditText)v.findViewById(R.id.mobileF);
        mobilef.setEnabled(false);
        mobilef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        aadharf=(EditText)v.findViewById(R.id.aadharF);
        aadharf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        aadharf.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });
        pincodef=(EditText)v.findViewById(R.id.pincodeF);
        pincodef.setEnabled(false);
        addressf=(EditText)v.findViewById(R.id.addressF);
        addressf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        villagef=(EditText)v.findViewById(R.id.villageF);
        villagef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mandalf=(EditText)v.findViewById(R.id.mandalF);
        mandalf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        districtf=(EditText)v.findViewById(R.id.districtF);
       // districtf.setEnabled(false);
        districtf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        statef=(EditText)v.findViewById(R.id.stateF);
        //statef.setEnabled(false);
        statef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        View alertLayout = inflater1.inflate(R.layout.custom_dialog2, null);
        final ImageView close=(ImageView)alertLayout.findViewById(R.id.close_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new ContentDashboard().newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
                dialog.dismiss();
            }
        });
        mobileDiaF =(EditText) alertLayout.findViewById(R.id.mobileDialogF);
        pinDiaF=(EditText) alertLayout.findViewById(R.id.pinDialogF);
        ccp=(CountryCodePicker)alertLayout.findViewById(R.id.countrycode);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
              //  Toast.makeText(getContext(), "Code is:"+ccp.getSelectedCountryCode(), Toast.LENGTH_SHORT).show();
            }
        });
        final Button btnDiaF = (Button) alertLayout.findViewById(R.id.btnDialogF);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnDiaF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobilef.setText("+"+ccp.getSelectedCountryCode()+" "+mobileDiaF.getText().toString());
                pincodef.setText(pinDiaF.getText().toString());
                phone= mobileDiaF.getText().toString();
                if (!isMOB(phone)) {
                    mobileDiaF.setError("Invalid Number");
                    return;
                }
                pin = pinDiaF.getText().toString();
                if (!isPIN(pin)) {
                    pinDiaF.setError("Enter Correct Pincode");
                    return;
                }
                pinDiaF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (pinDiaF.getText().toString().trim().length()>0){

                            if (!b){
                                sendDetailsToServerPin();
                            }

                        }

                    }
                });

                sendDetailsToServer0();
                getrawdatafromserver();


            }
        });

        submit=(Button)v.findViewById(R.id.submitF);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( namef.getText().toString().trim().equals(""))
                {
                    namef.setError( "firstName is required!" );
                    namef.setHint("please enter firstname");
                }
                if( surnamef.getText().toString().trim().equals(""))
                {
                    surnamef.setError( "lastName is required!" );
                    surnamef.setHint("please enter surname");
                }
                if( dobf.getText().toString().trim().equals(""))
                {
                    dobf.setError( "Date is required!" );

                }
                if( mandalf.getText().toString().trim().equals(""))
                {
                    mandalf.setError( "Mandal is required!" );

                }

               /* if (!isspinnerCF()) {

                }
                if (!isspinnerVF()) {

                }
                if (!isspinnerAF()) {

                }
                if (!isspinnerDF()) {

                }*/
                if(districtf.length()==0 && statef.length()==0)
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

//                Log.i("crop value:-",crop.length+"");
                if ((namef.length()>0) && (surnamef.length()>0)&& (dobf.length()>0)&&((mandalf.length()>0))
                         && (districtf.length()>0) && (statef.length()>0) )
                {

                   // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                    sendDetailsToServer1();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        transaction.addToBackStack(null);
        transaction.commit();
        return v;
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

    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", phone);
        postParam.put("pincode",pin);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"getmobileandpincode",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response ",response.toString());
                if (response.toString().contains("Farmer")){

                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Farmer", Toast.LENGTH_SHORT).show();
                }
                //if (response.toString().contains("\"userlist\":\"\""))
                else {
                    dialog.hide();
                    relative.setVisibility(View.VISIBLE);
                    getrawdatafromserver();

                }}
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
        postParam.put("mobile", mobileDiaF.getText().toString());
        postParam.put("pincode",pinDiaF.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"mobileduplicate",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response is:-",response.toString());
                if (response.toString().contains("\"userlist\":\"\"")){
                    dialog.hide();
                    relative.setVisibility(View.VISIBLE);
                    sendDetailsToServer();
                }
                else if (response.toString().contains("Farmer")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Farmer", Toast.LENGTH_SHORT).show();
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
                                continueF = (Button) alertLayout.findViewById(R.id.continueP);
                                cancelF = (Button) alertLayout.findViewById(R.id.cancelP);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                dialog = alert.create();
                                dialog.show();
                                cancelF.setOnClickListener(new View.OnClickListener() {
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
                                continueF.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        //sendDetailsToServer();

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
                Toast.makeText(getContext(), "Could not get data from Server "  , Toast.LENGTH_SHORT).show();
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
        postParam.put("firstName", namef.getText().toString());
        postParam.put("lastName", surnamef.getText().toString());
        postParam.put("dob", dobf.getText().toString());
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaF.getText().toString());
        postParam.put("aadhar",aadharf.getText().toString());
        postParam.put("pincode",pincodef.getText().toString());
        postParam.put("address", addressf.getText().toString());
        postParam.put("village", villagef.getText().toString());
        postParam.put("mandal", mandalf.getText().toString());
        postParam.put("district", districtf.getText().toString());
        postParam.put("state", statef.getText().toString());
        postParam.put("cropType", croptype);
        postParam.put("vegetables", vegtype);
        postParam.put("dairy", dairytype);
        postParam.put("aniHus", animtype);
        switch (cropc){
            case 1:
                postParam.put("crop1",crop[0]);
                break;
            case 2:
                postParam.put("crop1",crop[0]);
                postParam.put("crop2",crop[1]);
                break;
            case 3:
                postParam.put("crop1",crop[0]);
                postParam.put("crop2",crop[1]);
                postParam.put("crop3",crop[2]);
                break;
            case 4:
                postParam.put("crop1",crop[0]);
                postParam.put("crop2",crop[1]);
                postParam.put("crop3",crop[2]);
                postParam.put("crop4",crop[3]);
                break;
            case 5:
                postParam.put("crop1",crop[0]);
                postParam.put("crop2",crop[1]);
                postParam.put("crop3",crop[2]);
                postParam.put("crop4",crop[3]);
                postParam.put("crop5",crop[4]);
                break;
        }
        switch (vegc){
            case 1:
                postParam.put("veg1",veg[0]);
                break;
            case 2:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                break;
            case 3:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                postParam.put("veg3",veg[2]);
                break;
            case 4:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                postParam.put("veg3",veg[2]);
                postParam.put("veg4",veg[3]);
                break;
            case 5:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                postParam.put("veg3",veg[2]);
                postParam.put("veg4",veg[3]);
                postParam.put("veg5",veg[4]);
                break;
            case 6:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                postParam.put("veg3",veg[2]);
                postParam.put("veg4",veg[3]);
                postParam.put("veg5",veg[4]);
                postParam.put("veg6",veg[5]);
                break;
            case 7:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                postParam.put("veg3",veg[2]);
                postParam.put("veg4",veg[3]);
                postParam.put("veg5",veg[4]);
                postParam.put("veg6",veg[5]);
                postParam.put("veg7",veg[6]);
                break;
            case 8:
                postParam.put("veg1",veg[0]);
                postParam.put("veg2",veg[1]);
                postParam.put("veg3",veg[2]);
                postParam.put("veg4",veg[3]);
                postParam.put("veg5",veg[4]);
                postParam.put("veg6",veg[5]);
                postParam.put("veg7",veg[6]);
                postParam.put("veg8",veg[7]);
                break;
        }
        switch (animc){
            case 1:
                postParam.put("aniHus1",anim[0]);
                break;
            case 2:
                postParam.put("aniHus1",anim[0]);
                postParam.put("aniHus2",anim[1]);
                break;
            case 3:
                postParam.put("aniHus1",anim[0]);
                postParam.put("aniHus2",anim[1]);
                postParam.put("aniHus3",anim[2]);
                break;
        }
        switch (daic){
            case 1:
                postParam.put("dairy1",dai[0]);
                break;
            case 2:
                postParam.put("dairy1",dai[0]);
                postParam.put("dairy2",dai[1]);
                break;
            case 3:
                postParam.put("dairy1",dai[0]);
                postParam.put("dairy2",dai[1]);
                postParam.put("dairy3",dai[2]);
                break;
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"farRegistation",new JSONObject(postParam),
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
                            //Toast.makeText(getContext().getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            clearBox();

                            Intent intent=new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                            startActivity(intent);
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

    private boolean isMOB(String mobC) {
        if (mobC != null && mobC.length() == 10) {
            return true;
        }
        return false;
    }
    private boolean isPIN (String pinC) {
        if (pinC != null && pinC.length() == 6) {
            return true;
        }
        return false;
    }



    private boolean isspinnerVF(){
        if(spinnerVF.getSelectedItem().toString().equals("Select Vegetables")) {
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

    private boolean isspinnerAF(){
        if(spinnerAF.getSelectedItem().toString().equals("Select Animal") ) {
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

    private boolean isspinnerCF(){
        if(spinnerCF.getSelectedItem().toString().equals("Select Crop")) {
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


    private boolean isspinnerDF () {
        if(spinnerDF.getSelectedItem().toString().equals("Select Dairy")) {
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

    @Override
    public void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        postParam.put("pincode", pincodef.getText().toString());

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
                   // mandalf.setText(obj.getString("District"));
                    districtf.setText(obj.getString("District"));
                    statef.setText(obj.getString("State"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        getActivity()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Could not get data from Server ");
                alertDialog.setIcon(R.drawable.tick);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

                //Toast.makeText(getContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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
    private void clearBox()
    {
        namef.setText("");
        surnamef.setText("");
        aadharf.setText("");
        addressf.setText("");
        mobilef.setText("");
        villagef.setText("");
        mandalf.setText("");
        districtf.setText("");
        statef.setText("");
        pincodef.setText("");
        dobf.setText("");
        spinnerAF.setAllCheckedText("Select Animal");
        spinnerCF.setAllCheckedText("Select Crop");
        spinnerDF.setAllCheckedText("Select Dairy");
        spinnerVF.setAllCheckedText("Select Vegetables");

        //namew.requestFocus();

    }

    private void getrawdatafromserver() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", phone);

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

                    spinnerCF.setListAdapter(adapterC).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            cropc=0;
                            for(int i=0; i<selected.length; i++) {
                                if (selected[i] ) {
                                    crop[cropc] = crops.get(i);
                                    if(cropc==0)
                                        croptype=crops.get(i);
                                    else
                                        croptype= croptype+","+crops.get(i);
                                    cropc++;

                                    //  Log.i("cropc", i + " : "+ crop[cropc-1]+crop.length+ cropc);
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Crop")
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0).setMaxSelectedItems(5);

                    spinnerVF.setListAdapter(adapterV).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {
                            vegc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    veg[vegc]= vegetables.get(i);
                                    if(vegc==0)
                                        vegtype=vegetables.get(i);
                                    else
                                        vegtype= vegtype+","+vegetables.get(i);

                                    vegc++;
                                }
                            }
                            Log.i("countVeg:",""+vegc);
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Vegetables")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0).setMaxSelectedItems(8);

                    spinnerAF.setListAdapter(adapterA).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {

                            animc=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    anim[animc]= animal.get(i);
                                    if(animc==0)
                                        animtype=animal.get(i);
                                    else
                                        animtype= animtype+","+animal.get(i);
                                    animc++;
                                    //Log.i("TAG", i + " : "+ selected.length+animal.get(i)+anim[i]);
                                }
                            }
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Animal")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0).setMaxSelectedItems(3);

                    spinnerDF.setListAdapter(adapterD).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected) {

                            daic=0;
                            for(int i=0; i<selected.length; i++) {
                                if(selected[i]) {
                                    dai[daic]= dairy.get(i);
                                    if(daic==0)
                                        dairytype=dairy.get(i);
                                    else
                                        dairytype= dairytype+","+dairy.get(i);

                                    daic++;

                                }
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

}
