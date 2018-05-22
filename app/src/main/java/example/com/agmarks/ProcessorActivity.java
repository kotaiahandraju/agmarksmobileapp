package example.com.agmarks;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

/*
 * Created by Admin on 12-04-2018.
 */

public class ProcessorActivity extends Fragment {
   public static EditText namep,surnamep,companyp,addressp,pincodep,villagep,
           mandalp,districtp,statep,emailp,mobilep,dobp,gstp,finishedp,packagingp;
    String name,surname,company,address,pincode,village,mandal,email,mobile,dob,gst,finished,packaging;
    EditText mobileDiaP;
    Button submit;
    Button continueP,cancelP;
    Button finishAdd,packageAdd;
    RelativeLayout relative;
    AlertDialog dialog,dialog1;
    Config config=new Config();
    String baseUrl;
    MultiSelectSpinner spinnerRF;
    FragmentTransaction transaction;
    ArrayList<String> rawItems;
    SmsVerifyCatcher smsVerifyCatcher;
    String code;
    String strAlert;
    String[] raw;
    int rawM=0;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    RadioGroup rbg;
    RadioButton rb;

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
                    Toast.makeText(getActivity().getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
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
        dobp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");
                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;
                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));
                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }
                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));
                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dobp.setText(current);
                    dobp.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
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
        finishAdd=(Button)v.findViewById(R.id.btn_finishR);
        finishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        packageAdd=(Button)v.findViewById(R.id.btn_packageR); 
        packageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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
                /*raw=new String[selected.length];
                for(int i=0; i<selected.length; i++) {
                    if(selected[i]) {
                        Log.i("TAG", i + " : "+ rawItems.get(i));
                        raw[i]= rawItems.get(i);
                    }
                }*/
            }
        }).setAllCheckedText("All Items").setAllUncheckedText("Select Crop")
                .setSelectAll(false).setTitle(R.string.title).setMinSelectedItems(0);
        
        submit=(Button)v.findViewById(R.id.submitP);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!isspinnerRF()) {
//
//                    return;
//                }
                sendDetailsToServer2();
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
                            } else if (!response.toString().contains("\"status1\":\"\"") &&
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
                        .setMinSelectedItems(0);
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
        postParam.put("mobile", mobilep.getText().toString());
       // postParam.put("dob",dobp.getText().toString());
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
        postParam.put("finProduct",finishedp.getText().toString());
        postParam.put("packaging",packagingp.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"processorreg",new JSONObject(postParam),
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

    private void sendDetailsToServer2() {
        Toast.makeText(getContext(),"ClickedSubmit",Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", mobilep.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"sendOtp",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Response is:-",response.toString());
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




}
