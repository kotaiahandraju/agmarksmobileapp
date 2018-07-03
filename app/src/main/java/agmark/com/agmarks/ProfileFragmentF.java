package agmark.com.agmarks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragmentF extends Fragment {
    EditText name, surname, dob, contact, aadhar, pincode, address, village, mandal, district, state;
    Button editP, saveP;
    Spinner comm1, comm2, comm3, comm4, comm5;
    Spinner vege1, vege2, vege3, vege4, vege5, vege6, vege7, vege8;
    Spinner anim1, anim2, anim3;
    Spinner dair1, dair2, dair3;
    Button editA, saveA;
    String myString,str,croptype,vegtype,animtype,dairytype,ccode;
    CustomSpinnerAdapter customCrop,customAnimal,customVegetable,customDairy;
    SharedPreferences sharedPreferences;
    ArrayList<String> crops1;
    ArrayList<String> vegetables1;
    ArrayList<String> animal1;
    ArrayList<String> dairy1;
    Config config = new Config();
    String baseUrl, tokenid, username, pwd,fid;
    String[] crop1,crop2,crop3,crop4,crop5;
    String[] veget1,veget2,veget3,veget4,veget5,veget6,veget7,veget8;
    String[] anima1,anima2,anima3;
    String[] daip1,daip2,daip3;
    JSONArray jsonarray;
    int cropc1=0,cropc2=0,cropc3=0,cropc4=0,cropc5=0;
    int vegc1=0,vegc2=0,vegc3=0,vegc4=0,vegc5=0,vegc6=0,vegc7=0,vegc8=0;
    int animc1=0,animc2=0,animc3=0;
    int daic1=0,daic2=0,daic3=0;
    HashMap<String, String> hm;
    ListView listview;
    ArrayList<HashMap<String, String>> mCategoryLists;
    LinearLayout linearLayout1,linearLayout2;
    JSONObject obj;
    JSONObject obj1;
    DatePickerDialog datePickerDialog;
    CountryCodePicker ccp;

    public ProfileFragmentF() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_profilef, container, false);

        sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);
        username=sharedPreferences.getString("username", "");
        pwd=sharedPreferences.getString("pwd", "");
        tokenid=sharedPreferences.getString("tokenid", "");
        fid=sharedPreferences.getString("fid","");
        baseUrl = config.get_url();

        linearLayout2=(LinearLayout)v.findViewById(R.id.linearlayout_Act2);

        Log.i("URL data is:--", baseUrl);
        name = (EditText) v.findViewById(R.id.namePF);
        surname = (EditText) v.findViewById(R.id.surnamePF);
        dob = (EditText) v.findViewById(R.id.dobPF);
        dob.setOnClickListener(new View.OnClickListener() {
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
                                dob.setText(date_pick_res);
                                //dobf.setEnabled(false);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Please select date");
                // TODO Hide Future Date Here
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
        contact = (EditText) v.findViewById(R.id.contactPF);
        aadhar = (EditText) v.findViewById(R.id.aadharPF);
        pincode = (EditText) v.findViewById(R.id.pincodePF);
        address = (EditText) v.findViewById(R.id.addressPF);
        village = (EditText) v.findViewById(R.id.villagePF);
        mandal = (EditText) v.findViewById(R.id.mandalPF);
        district = (EditText) v.findViewById(R.id.districtPF);
        state = (EditText) v.findViewById(R.id.statePF);

//        editA = (Button) v.findViewById(R.id.edit_act);
//        saveA = (Button) v.findViewById(R.id.save_act);

        comm1 = (Spinner) v.findViewById(R.id.spComm1);
        comm2 = (Spinner) v.findViewById(R.id.spComm2);
        comm3 = (Spinner) v.findViewById(R.id.spComm3);
        comm4 = (Spinner) v.findViewById(R.id.spComm4);
        comm5 = (Spinner) v.findViewById(R.id.spComm5);

        vege1 = (Spinner) v.findViewById(R.id.spVeg1);
        vege2 = (Spinner) v.findViewById(R.id.spVeg2);
        vege3 = (Spinner) v.findViewById(R.id.spVeg3);
        vege4 = (Spinner) v.findViewById(R.id.spVeg4);
        vege5 = (Spinner) v.findViewById(R.id.spVeg5);
        vege6 = (Spinner) v.findViewById(R.id.spVeg6);
        vege7 = (Spinner) v.findViewById(R.id.spVeg7);
        vege8 = (Spinner) v.findViewById(R.id.spVeg8);

        anim1 = (Spinner) v.findViewById(R.id.spAnim1);
        anim2 = (Spinner) v.findViewById(R.id.spAnim2);
        anim3 = (Spinner) v.findViewById(R.id.spAnim3);

        dair1 = (Spinner) v.findViewById(R.id.spDai1);
        dair2 = (Spinner) v.findViewById(R.id.spDai2);
        dair3 = (Spinner) v.findViewById(R.id.spDai3);
        getrawDatafromServer();
        getDetailsFromServer();
        editP = (Button) v.findViewById(R.id.edit_per);
        editP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    editP.setVisibility(View.GONE);
                    saveP.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                surname.setEnabled(true);
                dob.setEnabled(true);
                contact.setEnabled(true);
                aadhar.setEnabled(true);
                pincode.setEnabled(true);
                address.setEnabled(true);
                village.setEnabled(true);
                mandal.setEnabled(true);
                district.setEnabled(true);
                state.setEnabled(true);
              //  getDetailsFromServer();

                comm1.setEnabled(true);
                comm2.setEnabled(true);
                comm3.setEnabled(true);
                comm4.setEnabled(true);
                comm5.setEnabled(true);

                vege1.setEnabled(true);
                vege2.setEnabled(true);
                vege3.setEnabled(true);
                vege4.setEnabled(true);
                vege5.setEnabled(true);
                vege6.setEnabled(true);
                vege7.setEnabled(true);
                vege8.setEnabled(true);

                anim1.setEnabled(true);
                anim2.setEnabled(true);
                anim3.setEnabled(true);

                dair1.setEnabled(true);
                dair2.setEnabled(true);
                dair3.setEnabled(true);
                name.requestFocus();

            }
        });
        saveP = (Button) v.findViewById(R.id.save_per);
        saveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDetailsToServer();
                editP.setVisibility(View.VISIBLE);
                saveP.setVisibility(View.GONE);
                name.setEnabled(false);
                surname.setEnabled(false);
                dob.setEnabled(false);
                contact.setEnabled(false);
                aadhar.setEnabled(false);
                pincode.setEnabled(false);
                address.setEnabled(false);
                village.setEnabled(false);
                mandal.setEnabled(false);
                district.setEnabled(false);
                state.setEnabled(false);

                getDetailsFromServer();
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

    private void getDetailsFromServer() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_name", username);
            jsonBody.put("password", pwd);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() + "userloggedcheck", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());
                    mCategoryLists = new ArrayList<HashMap<String, String>>();

                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonf = json.getJSONArray("userbean");
                        if (jsonf.length() > 0) {
                            JSONObject obj2 = jsonf.getJSONObject(0);

                            if(obj2.has("status1")) {
                                if (obj2.getString("status1").equals("Farmer")) {

                                    jsonarray = json.getJSONArray("fstatus1");
                                }
                            }
                            if(obj2.has("status2")) {
                                if (obj2.getString("status2").equals("Farmer")) {
                                    jsonarray = json.getJSONArray("fstatus2");
                                }
                            }
                            if(obj2.has("status3")) {
                                if (obj2.getString("status3").equals("Farmer")) {
                                    jsonarray = json.getJSONArray("fstatus3");
                                }
                            }
                            if(obj2.has("status4")) {
                                if (obj2.getString("status4").equals("Farmer")) {
                                    jsonarray = json.getJSONArray("fstatus4");
                                }
                            }

                        }

                       // for (int i = 0; i < jsonarray.length(); i++) {
                            obj = jsonarray.getJSONObject(0);
                            name.setText(obj.getString("firstName"));
                            name.setEnabled(false);
                            surname.setText(obj.getString("lastName"));
                            surname.setEnabled(false);
                            dob.setText(obj.getString("dob"));
                            dob.setEnabled(false);
                            contact.setText(obj.getString("mobile"));
                            contact.setEnabled(false);
                            aadhar.setText(obj.getString("aadhar"));
                            aadhar.setEnabled(false);
                            pincode.setText(obj.getString("pincode"));
                            pincode.setEnabled(false);
                            address.setText(obj.getString("address"));
                            address.setEnabled(false);
                            village.setText(obj.getString("village"));
                            village.setEnabled(false);
                            mandal.setText(obj.getString("mandal"));
                            mandal.setEnabled(false);
                            district.setText(obj.getString("district"));
                            district.setEnabled(false);
                            state.setText(obj.getString("state"));
                            state.setEnabled(false);
                            ccode=obj.getString("ccode");
                      /*  if(obj.has("crop1")){
                            for(int i=0;i<jsonarray.length();i++) {
                                myString = obj.getString("crop1").toString().trim();
                                if(myString.equalsIgnoreCase(customCrop.getItem(i).toString().trim())) {
                                   // int sp_position = customCrop.getPosition(myString);
                                    comm1.setSelection(i);
                                    break;
                                }
                            }

                        }*/
                        if(obj.has("crop1")){
                            myString = obj.getString("crop1").toString().trim();
                            int sp_position = customCrop.getPosition(myString);
                            comm1.setSelection(sp_position);

                        }
                        comm1.setEnabled(false);
                        if(obj.has("crop2")){
                              myString = obj.getString("crop2").toString().trim();
                            int sp_position = customCrop.getPosition(myString);
                            comm2.setSelection(sp_position);

                        }
                        comm2.setEnabled(false);
                        if(obj.has("crop3")){
                              myString = obj.getString("crop3").toString().trim();
                            int sp_position = customCrop.getPosition(myString);
                            comm3.setSelection(sp_position);
                        }
                        comm3.setEnabled(false);
                        if(obj.has("crop4")){
                              myString = obj.getString("crop4").toString().trim();
                            int sp_position = customCrop.getPosition(myString);
                            comm4.setSelection(sp_position);
                        }
                        comm4.setEnabled(false);
                        if(obj.has("crop5")){
                             myString = obj.getString("crop5").toString().trim();
                            int sp_position = customCrop.getPosition(myString);
                            comm5.setSelection(sp_position);
                        }
                        comm5.setEnabled(false);
                        if(obj.has("veg1")){
                             myString = obj.getString("veg1").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege1.setSelection(sp_position);
                        }
                        vege1.setEnabled(false);
                        if(obj.has("veg2")){
                            myString = obj.getString("veg2").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege2.setSelection(sp_position);
                        }
                        vege2.setEnabled(false);
                        if(obj.has("veg3")){
                            myString = obj.getString("veg3").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege3.setSelection(sp_position);
                        }
                        vege3.setEnabled(false);
                        if(obj.has("veg4")){
                            myString = obj.getString("veg4").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege4.setSelection(sp_position);
                        }
                        vege4.setEnabled(false);
                        if(obj.has("veg5")){
                            myString = obj.getString("veg5").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege5.setSelection(sp_position);
                        }
                        vege5.setEnabled(false);
                        if(obj.has("veg6")){
                            myString = obj.getString("veg6").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege6.setSelection(sp_position);
                        }
                        vege6.setEnabled(false);
                        if(obj.has("veg7")){
                            myString = obj.getString("veg7").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege7.setSelection(sp_position);
                        }
                        vege7.setEnabled(false);
                        if(obj.has("veg8")){
                            myString = obj.getString("veg8").toString().trim();
                            int sp_position = customVegetable.getPosition(myString);
                            vege8.setSelection(sp_position);
                        }
                        vege8.setEnabled(false);
                        if(obj.has("aniHus1")){
                            myString = obj.getString("aniHus1").toString().trim();
                            int sp_position = customAnimal.getPosition(myString);
                            anim1.setSelection(sp_position);
                        }
                        anim1.setEnabled(false);
                        if(obj.has("aniHus2")){
                            myString = obj.getString("aniHus2").toString().trim();
                            int sp_position = customAnimal.getPosition(myString);
                            anim2.setSelection(sp_position);
                        }
                        anim2.setEnabled(false);
                        if(obj.has("aniHus3")){
                            myString = obj.getString("aniHus3").toString().trim();
                            int sp_position = customAnimal.getPosition(myString);
                            anim3.setSelection(sp_position);
                        }
                        anim3.setEnabled(false);
                        if(obj.has("dairy1")){
                            myString = obj.getString("dairy1").toString().trim();
                            int sp_position = customDairy.getPosition(myString);
                            dair1.setSelection(sp_position);
                        }
                        dair1.setEnabled(false);
                        if(obj.has("dairy2")){
                            myString = obj.getString("dairy2").toString().trim();
                            int sp_position = customDairy.getPosition(myString);
                            dair2.setSelection(sp_position);
                        }
                        dair2.setEnabled(false);
                        if(obj.has("dairy3")){
                            myString = obj.getString("dairy3").toString().trim();
                            int sp_position = customDairy.getPosition(myString);
                            dair3.setSelection(sp_position);
                        }
                        dair3.setEnabled(false);

                    } catch (JSONException e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not get Data from Online Server", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        } catch (JSONException e) {
        }
    }

    private void getrawDatafromServer() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("mobile", username);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"getrawdata",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("Response ", response.toString());

                JSONObject json= null;
                try {
                    json = new JSONObject(response.toString());
                    JSONArray array = json.getJSONArray("masCom");
                    crops1 = new ArrayList<String>();
                    crops1.add("Select Crop");
                    for (int j = 0; j < array.length(); j++) {
                        obj1 = array.getJSONObject(j);
                        crops1.add(obj1.getString("Commodity").toString().trim());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject json1= null;
                try {
                    json1 = new JSONObject(response.toString());
                    JSONArray array1 = json1.getJSONArray("masVeg");
                    vegetables1 = new ArrayList<String>();
                    vegetables1.add("Select Vegetable");
                   for (int k = 0; k < array1.length(); k++) {
                        final JSONObject obj2 = array1.getJSONObject(k);
                        vegetables1.add(obj2.getString("Vegetable").toString().trim());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject json2= null;
                try {
                    json2 = new JSONObject(response.toString());
                    JSONArray array2 = json2.getJSONArray("masAh");
                    animal1 = new ArrayList<String>();
                    animal1.add("Select Animal");
                    for (int l = 0; l < array2.length(); l++) {
                        final JSONObject obj2 = array2.getJSONObject(l);
                        animal1.add(obj2.getString("Animal").toString().trim());

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                JSONObject json3= null;
                try {
                    json3 = new JSONObject(response.toString());
                    JSONArray array3 = json3.getJSONArray("masDairy");
                    dairy1 = new ArrayList<String>();
                    dairy1.add("Select Dairy");
                    for (int m = 0; m < array3.length(); m++) {
                        final JSONObject obj2 = array3.getJSONObject(m);
                        dairy1.add(obj2.getString("Product_name").toString().trim());
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
 customCrop = new CustomSpinnerAdapter(getActivity(), crops1);


                comm1.setAdapter(customCrop);
                comm2.setAdapter(customCrop);
                comm3.setAdapter(customCrop);
                comm4.setAdapter(customCrop);
                comm5.setAdapter(customCrop);
                customVegetable = new CustomSpinnerAdapter(getActivity(), vegetables1);

                vege1.setAdapter(customVegetable);
                vege2.setAdapter(customVegetable);
                vege3.setAdapter(customVegetable);
                vege4.setAdapter(customVegetable);
                vege5.setAdapter(customVegetable);
                vege6.setAdapter(customVegetable);
                vege7.setAdapter(customVegetable);
                vege8.setAdapter(customVegetable);
                customAnimal = new CustomSpinnerAdapter(getActivity(), animal1);

                anim1.setAdapter(customAnimal);
                anim2.setAdapter(customAnimal);
                anim3.setAdapter(customAnimal);
                customDairy = new CustomSpinnerAdapter(getActivity(), dairy1);
                dair1.setAdapter(customDairy);
                dair2.setAdapter(customDairy);
                dair3.setAdapter(customDairy);

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
        postParam.put("tokenId",tokenid);
        postParam.put("id",fid);
        postParam.put("firstName", name.getText().toString());
        postParam.put("lastName", surname.getText().toString());
        postParam.put("dob", dob.getText().toString());
        postParam.put("ccode",ccode);
        postParam.put("mobile", contact.getText().toString());
        postParam.put("aadhar",aadhar.getText().toString());
        postParam.put("pincode",pincode.getText().toString());
        postParam.put("address", address.getText().toString());
        postParam.put("village", village.getText().toString());
        postParam.put("mandal", mandal.getText().toString());
        postParam.put("district", district.getText().toString());
        postParam.put("state", state.getText().toString());

        if(!comm1.getSelectedItem().toString().trim().equals("Select Crop")) {
            postParam.put("crop1",comm1.getSelectedItem().toString().trim());
            croptype=comm1.getSelectedItem().toString().trim();
        }
        if(!comm2.getSelectedItem().toString().trim().equals("Select Crop")) {
            postParam.put("crop2",comm2.getSelectedItem().toString().trim());
            croptype= croptype+","+comm2.getSelectedItem().toString().trim();
        }
        if(!comm3.getSelectedItem().toString().trim().equals("Select Crop")) {
            postParam.put("crop3",comm3.getSelectedItem().toString().trim());
            croptype= croptype+","+comm3.getSelectedItem().toString().trim();
        }
        if(!comm4.getSelectedItem().toString().trim().equals("Select Crop")) {
            postParam.put("crop4",comm4.getSelectedItem().toString().trim());
            croptype= croptype+","+comm4.getSelectedItem().toString().trim();
        }

        if(!comm5.getSelectedItem().toString().trim().equals("Select Crop")) {
            postParam.put("crop5",comm5.getSelectedItem().toString().trim());
            croptype= croptype+","+comm5.getSelectedItem().toString().trim();
        }
        if(!vege1.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg1",vege1.getSelectedItem().toString().trim());
            vegtype=vege1.getSelectedItem().toString().trim();
        }

        if(!vege2.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg2",vege2.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege2.getSelectedItem().toString().trim();
        }
        if(!vege3.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg3",vege3.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege3.getSelectedItem().toString().trim();
        }
        if(!vege4.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg4",vege4.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege4.getSelectedItem().toString().trim();
        }

        if(!vege5.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg5",vege5.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege5.getSelectedItem().toString().trim();
        }
        if(!vege6.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg6",vege6.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege6.getSelectedItem().toString().trim();
        }
        if(!vege7.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg7",vege7.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege7.getSelectedItem().toString().trim();
        }
        if(!vege8.getSelectedItem().toString().trim().equals("Select Vegetable")) {
            postParam.put("veg8",vege8.getSelectedItem().toString().trim());
            vegtype=vegtype+","+vege8.getSelectedItem().toString().trim();
        }
        if(!anim1.getSelectedItem().toString().trim().equals("Select Animal")) {
            postParam.put("aniHus1",anim1.getSelectedItem().toString().trim());
            animtype=anim1.getSelectedItem().toString().trim();
        }
        if(!anim2.getSelectedItem().toString().trim().equals("Select Animal")) {
            postParam.put("aniHus2",anim2.getSelectedItem().toString().trim());
            animtype=animtype+","+anim2.getSelectedItem().toString().trim();
        }
        if(!anim3.getSelectedItem().toString().trim().equals("Select Animal")) {
            postParam.put("aniHus3",anim3.getSelectedItem().toString().trim());
            animtype=animtype+","+anim3.getSelectedItem().toString().trim();
        }
        if(!dair1.getSelectedItem().toString().trim().equals("Select Dairy")) {
            postParam.put("dairy1",dair1.getSelectedItem().toString().trim());
            dairytype=dair1.getSelectedItem().toString().trim();
        }
        if(!dair2.getSelectedItem().toString().trim().equals("Select Dairy")) {
            postParam.put("dairy2",dair2.getSelectedItem().toString().trim());
            dairytype=dairytype+","+dair2.getSelectedItem().toString().trim();
        }
        if(!dair3.getSelectedItem().toString().trim().equals("Select Dairy")) {
            postParam.put("dairy3",dair3.getSelectedItem().toString().trim());
            dairytype=dairytype+","+dair3.getSelectedItem().toString().trim();
        }

        postParam.put("cropType", croptype);
        postParam.put("vegetables", vegtype);
        postParam.put("dairy", dairytype);
        postParam.put("aniHus", animtype);

        Log.i("Response Saving---",""+postParam);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"farRegistation",new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response Saving---",""+response);
                        // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        if (response.toString().contains("success")){
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    getActivity()).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Your Data Updated Successfully");
                            alertDialog.setIcon(R.drawable.tick);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();

                            getDetailsFromServer();

                        }
                        else if (response.toString().contains("fail")){
                            AlertDialog alertDialog = new AlertDialog.Builder(
                                    getActivity()).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Your Data Not Updates");
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
    private class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private List<String> asr;

        public CustomSpinnerAdapter(Context context, List<String> asr) {
            this.asr = asr;
            activity = context;
        }

        public int getPosition(String str){return asr.indexOf(str);}
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
            txt.setTextSize(16);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(14);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

    }

}




