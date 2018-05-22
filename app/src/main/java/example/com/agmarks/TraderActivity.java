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

public class TraderActivity extends Fragment{
    public static EditText namet,surnamet,companyt,addresst,
            pincodet,villaget,mandalt,districtt,statet,emailt,mobilet,dobt,gstt;
    String name,surname,company,address,pincode,village,mandal,district,state,email,mobile,dob,gst;
    MultiSelectSpinner spinnerCT,spinnerVT,spinnerAT,spinnerDT;
    ArrayList<String> crops,vegetables,animal,dairy;
    RelativeLayout relative;
    AlertDialog dialog;
    FragmentTransaction transaction;
    Config config=new Config();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String baseUrl;
    String strAlert="";
    EditText mobileDiaT;
    Button submit;
    Button continueT,cancelT;
    int cropc,vegc,animc,daic=0;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    String crop,veg,anim,dai;
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    RadioGroup rbg;
    RadioButton rb;
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
        rbg=(RadioGroup) v.findViewById(R.id.radioGroup);
        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb= (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(getActivity().getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
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
                dialog.hide();
                relative.setVisibility(View.VISIBLE);
                sendDetailsToServer();
            }
        });
        submit=(Button)v.findViewById(R.id.submitT);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!isspinnerCT()) {
                    return;
                }
                if (!isspinnerVT()) {
                    return;
                }
                if (!isspinnerAT()) {
                    return;
                }
                if (!isspinnerDT()) {
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

    private void sendDetailsToServer2() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobileDiaT.getText().toString());

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
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                                }
                            });
                            continueT.setOnClickListener(new View.OnClickListener() {

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

               /* else{
                   sendDetailsToServer0();
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
                            .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0);

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
                            Log.i("countVeg:",""+vegc);
                        }
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Vegetables")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

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
                    }).setAllCheckedText("All Items").setAllUncheckedText("Select Animal")
                            .setSelectAll(false).setTitle(R.string.title)
                            .setMinSelectedItems(0);

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
        postParam.put("mobile", mobilet.getText().toString());
        postParam.put("gstnumber",gstt.getText().toString());
        postParam.put("crops",crop);
        postParam.put("vegetables",veg);
        postParam.put("aniHusbandry",anim);
        postParam.put("dairy",dai);
        /*for(int i=0;i<cropc;i++)
        {
            postParam.put("crop"+(i+1),crop[i]);
            Log.i("TAG", i + "crop"+(i+1)+ crop[i]);
        }
        for(int i=0;i<vegc;i++)
        {
            postParam.put("crop"+i+1,veg[i]);
            Log.i("TAG", i + "veg"+(i+1)+ veg[i]);
        }
        for(int i=0;i<animc;i++)
        {
            postParam.put("crop"+i+1,anim[i]);
            Log.i("TAG", i + "animal"+(i+1)+ anim[i]);
        }
        for(int i=0;i<daic;i++)
        {
            postParam.put("crop"+i+1,dai[i]);
            Log.i("TAG", i + "dairy"+(i+1)+ dai[i]);
        }*/
        Log.i("data sedngbn",""+postParam);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"traderreg",new JSONObject(postParam),
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
}
