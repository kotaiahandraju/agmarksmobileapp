package agmark.com.agmarks;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
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

/*
 * Created by Admin on 12-04-2018.
 */

public class ProcessorActivity extends Fragment {
   public static EditText namep,surnamep,companyp,addressp,pincodep,villagep,
           mandalp,districtp,statep,emailp,mobilep,dobp,gstp,finishedp,packagingp;
    String pro="",pack="",rbCheck="",name,surname,company,address,pincode,village,mandal,district,state,email="",mobile,dob,gst;
    EditText mobileDiaP;
    TextView textView1,textView2,textView3,textView4,textView5,textView6;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6;
    Button submit;
    Button continueP,cancelP;
    Button finishAdd,packageAdd;
    RelativeLayout relative;
    AlertDialog dialog,dialog1;
    Config config=new Config();
    String baseUrl,str;
    MultiSelectSpinner spinnerRF;
    FragmentTransaction transaction;
    ArrayList<String> rawItems;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    String strAlert;
    String[] raw;
    int rawM=0,cpro=0,cpack=0;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    RadioGroup rbg;
    RadioButton rb;
    DatePickerDialog  datePickerDialog;
    CountryCodePicker ccp;

    public static ProcessorActivity newInstance() {
        ProcessorActivity fragment = new ProcessorActivity();
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
        v = inflater.inflate(R.layout.activity_processor, container, false);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        relative=(RelativeLayout)v.findViewById(R.id.relative1);
        spinnerRF= (MultiSelectSpinner) v.findViewById(R.id.spinnerRF);
        baseUrl= config.get_url();
        smsVerifyCatcher  = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code  = parseCode(message);
                Log.i("Message:--",code);
            }
        });
        rbg=(RadioGroup)v.findViewById(R.id.radioGroup);
        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rbCheck=rb.getText().toString();
                    if(rb.getText().toString().equals("Company")) {
                        clearBox();
                        companyp.requestFocus();
                        companyp.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        namep.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                        surnamep.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);
                    }
                    if(rb.getText().toString().equals("Individual")) {
                        clearBox();
                        namep.requestFocus();
                        namep.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        surnamep.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, getResources().getDrawable(R.drawable.star1), null);
                        companyp.setCompoundDrawablesWithIntrinsicBounds(
                                null, null, null, null);

                    }
                }
            }
        });

        namep=(EditText)v.findViewById(R.id.nameP);
        namep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        surnamep=(EditText)v.findViewById(R.id.surnameP);
        surnamep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        companyp=(EditText)v.findViewById(R.id.companyP);
        companyp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        addressp=(EditText)v.findViewById(R.id.addressP);
        addressp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        pincodep=(EditText)v.findViewById(R.id.pincodeP);
        pincodep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (pincodep.getText().toString().trim().length()>0){
                    view.setBackgroundResource( R.drawable.edit_text);
                    if (!b){
                        sendDetailsToServerPin();
                    }

                }
                else {
                    view.setBackgroundResource( R.drawable.edit_text1);
                }
            }

            private void sendDetailsToServerPin() {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                Map<String, String> postParam= new HashMap<String, String>();
                postParam.put("pincode", pincodep.getText().toString());

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
                                districtp.setText(obj.getString("District"));
                                statep.setText(obj.getString("State"));
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
        });
        villagep=(EditText)v.findViewById(R.id.villageP);
        villagep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mandalp=(EditText)v.findViewById(R.id.mandalP);
        mandalp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        districtp=(EditText)v.findViewById(R.id.districtP);
        districtp.setEnabled(false);
        districtp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        statep=(EditText)v.findViewById(R.id.stateP);
        statep.setEnabled(false);
        statep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        emailp=(EditText)v.findViewById(R.id.emailP);
        emailp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobilep=(EditText)v.findViewById(R.id.mobileP);
        mobilep.setEnabled(false);
        dobp=(EditText)v.findViewById(R.id.dobP);
        dobp.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                        v.setBackgroundResource( R.drawable.edit_text1);
                    }
                    else {
                        v.setBackgroundResource( R.drawable.edit_text);
                    }
                }
        });
        dobp.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobp.getInputType(); // backup the input type
                dobp.setInputType(InputType.TYPE_NULL); // disable soft input
                dobp.onTouchEvent(event); // call native handler
                dobp.setInputType(inType); // restore input type
                dobp.requestFocus();
                return true; // consume touch even
            }
        });
        dobp.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v) {
                int inType = dobp.getInputType(); // backup the input type
                dobp.setInputType(InputType.TYPE_NULL); // disable soft input
                //  dobf.onTouchEvent(event); // call native handler
                dobp.setInputType(inType); // restore input type
                dobp.requestFocus();
                return true; // consume touch even
            }
        });
        dobp.setOnClickListener(new View.OnClickListener() {
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
                                dobp.setText(date_pick_res);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();

            }
        });

        gstp=(EditText)v.findViewById(R.id.gstP);
        gstp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        finishedp=(EditText)v.findViewById(R.id.finproP);
        finishedp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
         textView1=(TextView)v.findViewById(R.id.textView1);
         textView2=(TextView)v.findViewById(R.id.textView2);
         textView3=(TextView)v.findViewById(R.id.textView3);
         textView4=(TextView)v.findViewById(R.id.textView4);
         textView5=(TextView)v.findViewById(R.id.textView5);
         textView6=(TextView)v.findViewById(R.id.textView6);
         checkBox1=(CheckBox)v.findViewById(R.id.checkBox1);
         checkBox2=(CheckBox)v.findViewById(R.id.checkBox2);
         checkBox3=(CheckBox)v.findViewById(R.id.checkBox3);
         checkBox4=(CheckBox)v.findViewById(R.id.checkBox4);
         checkBox5=(CheckBox)v.findViewById(R.id.checkBox5);
         checkBox6=(CheckBox)v.findViewById(R.id.checkBox6);
        finishAdd=(Button)v.findViewById(R.id.btn_finishR);
        finishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finishedp.getText().toString().trim().length()>0 ) {
                    cpro++;
                    if (cpro == 1) {
                        textView1.setVisibility(View.VISIBLE);
                        textView1.setText(finishedp.getText().toString().trim());
                        checkBox1.setVisibility(View.VISIBLE);
                        checkBox1.setChecked(true);
                        finishedp.setText("");

                    }
                    if (cpro == 2) {
                        textView2.setVisibility(View.VISIBLE);
                        textView2.setText(finishedp.getText().toString().trim());
                        checkBox2.setVisibility(View.VISIBLE);
                        checkBox2.setChecked(true);
                        finishedp.setText("");

                    }
                    if (cpro == 3) {
                        textView3.setVisibility(View.VISIBLE);
                        textView3.setText(finishedp.getText().toString().trim());
                        checkBox3.setVisibility(View.VISIBLE);
                        checkBox3.setChecked(true);
                        finishedp.setText("");

                    }
                    if (cpro >= 4) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("U can add only 3 Finished Products!!!");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();

                    }
                }


                
            }
        });
        packageAdd=(Button)v.findViewById(R.id.btn_packageR); 
        packageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(packagingp.getText().toString().trim().length()>0) {
                    cpack++;
                    if (cpack == 1) {
                        textView4.setVisibility(View.VISIBLE);
                        textView4.setText(packagingp.getText().toString().trim());
                        checkBox4.setVisibility(View.VISIBLE);
                        checkBox4.setChecked(true);
                        packagingp.setText("");

                    }
                    if (cpack == 2) {
                        textView5.setVisibility(View.VISIBLE);
                        textView5.setText(packagingp.getText().toString().trim());
                        checkBox5.setVisibility(View.VISIBLE);
                        checkBox5.setChecked(true);
                        packagingp.setText("");

                    }
                    if (cpack == 3) {
                        textView6.setVisibility(View.VISIBLE);
                        textView6.setText(packagingp.getText().toString().trim());
                        checkBox6.setVisibility(View.VISIBLE);
                        checkBox6.setChecked(true);
                        packagingp.setText("");

                    }
                    if (cpack >= 4) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("U can add only 3 Packaged Products!!!");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();

                    }
                }


                }
        });
        packagingp=(EditText)v.findViewById(R.id.packingP);
        packagingp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        mobileDiaP=(EditText)alertLayout.findViewById(R.id.editT5);

        final Button btnSubmit = (Button) alertLayout.findViewById(R.id.btn_submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobilep.setText(mobileDiaP.getText().toString());
                String phone = mobileDiaP.getText().toString();
                if (!isPWD(phone)) {
                    mobileDiaP.setError("Invalid Number");
                    return;
                }
                sendDetailsToServer();
            }
        });

        rawItems=new ArrayList<>();
        rawItems.add("chillies");
        rawItems.add("beans");
        rawItems.add("milk");
        ArrayAdapter<String> adapterC = new ArrayAdapter <String>(getContext(),
                android.R.layout.simple_list_item_checked, rawItems);
        spinnerRF.setListAdapter(adapterC).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                raw=new String[selected.length];
                rawM=0;
                for(int i=0; i<selected.length; i++) {

                    if (selected[i] && rawM<=2) {
                        raw[rawM] = rawItems.get(i);
                        rawM++;
                        Log.i("cropc", i + " : " + rawM);
                    }

                }
                if(rawM>2) {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            getActivity()).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("U Have to Select Only 2 Raw Materials");
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }


            }
        }).setAllCheckedText("All Items").setAllUncheckedText("Select Crop")
                .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0).setMaxSelectedItems(2);
        
        submit=(Button)v.findViewById(R.id.submitP);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro="";
                if (checkBox1.isChecked()) {
                    pro = textView1.getText().toString();
                }
                if (checkBox2.isChecked()){
                    pro = pro+","+textView2.getText().toString();
                }
                if (checkBox3.isChecked()){
                    pro = pro+","+textView3.getText().toString();
                }
                Log.i("pro is:-",pro);
                pack="";
                if (checkBox4.isChecked()) {
                    pack = textView4.getText().toString();
                }
                if (checkBox5.isChecked()){
                    pack = pack+","+textView5.getText().toString();
                }
                if (checkBox6.isChecked()){
                    pack = pack+","+textView6.getText().toString();
                }
                Log.i("pack is:-",pack);


                if ( !rbCheck.isEmpty()){
                    if(rbCheck.equals("Company")) {
                        if (companyp.getText().toString().trim().equals("")) {
                            companyp.setError("Company Name is required!");
                            companyp.setHint("please enter Company Name");
                        }

                        if(email.length()>0){
                        if (!email.matches(emailPattern)) {
                            emailp.setError("Please enter correct Email!");
                        }
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if (namep.getText().toString().trim().equals("")) {
                            namep.setError("Name is required!");
                            namep.setHint("please enter Name");
                        }
                        if (surnamep.getText().toString().trim().equals("")) {
                            surnamep.setError("Surname is required!");
                            surnamep.setHint("please enter Surname");
                        }
                    }

                    if (addressp.getText().toString().trim().equals("")) {
                        addressp.setError("Address is required!");
                        addressp.setHint("please enter Address");
                    }
                    if (pincodep.getText().toString().trim().equals("")) {
                        pincodep.setError("Pincode is required!");
                        pincodep.setHint("please enter Pincode");

                    }
                    if (villagep.getText().toString().trim().equals("")) {
                        villagep.setError("Village Name is required!");
                        villagep.setHint("please enter Village name");

                    }
                    if (mandalp.getText().toString().trim().equals("")) {
                        mandalp.setError("Mandal is required!");
                        mandalp.setHint("please enter Mandal");

                    }
                    if(rbCheck.equals("Company")) {
                        if ((companyp.length() > 0) && (addressp.length() > 0) && (pincodep.length() > 0) && (villagep.length() > 0) && ((mandalp.length() > 0))
                                && (districtp.length()>0) && (statep.length()>0)) {

                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                            sendDetailsToServer2();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(rbCheck.equals("Individual")) {
                        if ((namep.length() > 0) && (addressp.length() > 0) && (pincodep.length() > 0) && (villagep.length() > 0) && ((mandalp.length() > 0))
                                && (surnamep.length() > 0)  && (districtp.length()>0) && (statep.length()>0) ) {

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

    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaP.getText().toString());

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
                else if (response.toString().contains("Processor")) {
                    Toast.makeText(getContext().getApplicationContext(), "Already Registered as Processor", Toast.LENGTH_SHORT).show();
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
                                continueP = (Button) alertLayout.findViewById(R.id.continueP);
                                cancelP = (Button) alertLayout.findViewById(R.id.cancelP);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                dialog = alert.create();
                                dialog.show();
                                cancelP.setOnClickListener(new View.OnClickListener() {
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
                                continueP.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        dialog.hide();
                                        relative.setVisibility(View.VISIBLE);
                                        getrawdatafromserver();
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


    private void getrawdatafromserver() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaP.getText().toString());

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
                        rawItems = new ArrayList<String>();
                        for (int i = 0; i < array.length(); i++) {
                            final JSONObject obj = array.getJSONObject(i);
                            rawItems.add(obj.getString("Commodity"));
                        }
                    JSONArray array1 = json.getJSONArray("masVeg");
                    for (int i = 0; i < array1.length(); i++) {
                        final JSONObject obj = array1.getJSONObject(i);
                        rawItems.add(obj.getString("Vegetable"));
                    }
                    JSONArray array2 = json.getJSONArray("masDairy");
                    for (int i = 0; i < array2.length(); i++) {
                        final JSONObject obj = array2.getJSONObject(i);
                        rawItems.add(obj.getString("Product_name"));
                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                ArrayAdapter<String> adapterR = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_checked, rawItems);

                spinnerRF.setListAdapter(adapterR).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        raw = new String[selected.length];
                        rawM = 0;
                        for (int i = 0; i < selected.length; i++) {
                            if (selected[i]) {
                                raw[rawM] = rawItems.get(i);
                                rawM++;
                                //    Log.i("TAG", i + " : "+ selected.length+dairy.get(i)+dai[i]);
                            }
                            //  Log.i("i daic",   " : "+ i+daic);
                        }
                        //Log.i("dairy:-",dai[0]+dai[1]+dai[2]+dai[3] );
                    }
                })
                        .setAllCheckedText("All Items").setAllUncheckedText("Select RawMaterials")
                        .setSelectAll(false).setTitle(R.string.title)
                        .setMinSelectedItems(0).setMaxSelectedItems(2);
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
        postParam.put("firstName", namep.getText().toString());
        postParam.put("lastName", surnamep.getText().toString());
        postParam.put("companyName",companyp.getText().toString());
        postParam.put("address", addressp.getText().toString());
        postParam.put("pincode",pincodep.getText().toString());
        postParam.put("village", villagep.getText().toString());
        postParam.put("mandal", mandalp.getText().toString());
        postParam.put("district", districtp.getText().toString());
        postParam.put("state", statep.getText().toString());
        postParam.put("email",emailp.getText().toString());
        postParam.put("ccode","+"+ccp.getSelectedCountryCode());
        postParam.put("mobile", mobileDiaP.getText().toString());
       // postParam.put("mobile", mobilep.getText().toString());
        postParam.put("strdateOfIncorp",dobp.getText().toString());
        postParam.put("gstnumber",gstp.getText().toString());
        switch (rawM) {
            case 1:
                postParam.put("raw1", raw[0]);
                break;
            case 2:
                postParam.put("raw1", raw[0]);
                postParam.put("raw2", raw[1]);
                break;
        }
        postParam.put("finProduct",pro);
        postParam.put("packaging",pack);
        Log.i("post param is:-",postParam.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"processorreg",new JSONObject(postParam),
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
                                    startActivity(intent); //Intent intent=new Intent(getActivity().getApplicationContext(),FarmerDashboard.class);

                                }
                            });
                            alertDialog.show();
                           // Toast.makeText(getContext().getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);
                        }
                        else if (response.toString().contains("fail")){
                            //Toast.makeText(getContext().getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
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

    private boolean isPWD(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }

    private void sendDetailsToServer2() {
       // Toast.makeText(getContext(),"ClickedSubmit",Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobilep.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"sendOtp",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                /*Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());*/
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
    private boolean isspinnerRF() {
        if(spinnerRF.getSelectedItem().toString().equals("Select RawMaterials")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select RawMaterials");
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
        namep.setText("");
        surnamep.setText("");
        companyp.setText("");
        addressp.setText("");
        pincodep.setText("");
        villagep.setText("");
        mandalp.setText("");
        districtp.setText("");
        statep.setText("");
        emailp.setText("");
        dobp.setText("");
        gstp.setText("");
        finishedp.setText("");
        packagingp.setText("");
        textView1.setText(null);
        textView2.setText(null);
        textView3.setText(null);
        textView4.setText(null);
        textView5.setText(null);
        textView6.setText(null);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);

        checkBox1.setVisibility(View.GONE);
        checkBox2.setVisibility(View.GONE);
        checkBox3.setVisibility(View.GONE);
        checkBox4.setVisibility(View.GONE);
        checkBox5.setVisibility(View.GONE);
        checkBox6.setVisibility(View.GONE);
cpro=0;
cpack=0;
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);
        textView6.setVisibility(View.GONE);

        //namep.requestFocus();
    }


}
