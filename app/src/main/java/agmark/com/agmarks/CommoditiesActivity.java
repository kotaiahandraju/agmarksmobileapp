package agmark.com.agmarks;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CommoditiesActivity extends Fragment{
    Spinner spinnerDtC;
    Spinner spinner1,spinner2;
    private ArrayList<Model1> productList;
    FragmentTransaction transaction;
    ListView simpleList;
    ImageView backImg;
    ArrayList<HashMap<String, String>> commName;
    String countryList[] = {"Units", "Variety", "Date", "Modal price","Unit of price","Max price", "Min price"};
    Model1 item1,item2,item3,item4,item5,item6,item7;
    ListView lview;
    Config config = new Config();
    String baseUrl;
    HashMap<String,String>  hm;
    List<String> commodity,com;
    TextView txt_district;

    public static CommoditiesActivity newInstance() {
        CommoditiesActivity fragment = new CommoditiesActivity();
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.activity_commodities, container, false);

        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        spinnerDtC=(Spinner) v.findViewById(R.id.disSpinnerC);
        if (MarketPricesActivity.stateSpinner.getSelectedItem().toString().equals("Andhra Pradesh")){
            ArrayAdapter adapter=new ArrayAdapter<String>
                    (getActivity(),android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.items_ap_districts));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDtC.setAdapter(adapter);
            String string=MarketPricesActivity.districtSpinner.getSelectedItem().toString();
            int pos=adapter.getPosition(string);
            spinnerDtC.setSelection(pos);
        }
        else if (MarketPricesActivity.stateSpinner.getSelectedItem().toString().equals("Telangana")){
            ArrayAdapter adapter1=new ArrayAdapter<String>
                    (getActivity(),android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.items_tn_districts));
            spinnerDtC.setAdapter(adapter1);
            String string=MarketPricesActivity.districtSpinner.getSelectedItem().toString();
            int pos=adapter1.getPosition(string);
            spinnerDtC.setSelection(pos);
        }

       spinnerDtC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               loadSpinnerData1();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        spinner1=(Spinner)v.findViewById(R.id.spinnerCustom1);
        spinner2=(Spinner)v.findViewById(R.id.spinnerCustom2);

        //spinner2.setEnabled(false);
        loadSpinnerData2();
        baseUrl = config.get_url();
        simpleList = (ListView)v.findViewById(R.id.list1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.activity_list1, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);
        backImg=(ImageView) v.findViewById(R.id.back_icon);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new MarketPricesActivity().newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        productList = new ArrayList<Model1>();
        lview = (ListView)v.findViewById(R.id.list2);
        lview.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        });
        transaction.addToBackStack(null);
        transaction.commit();
        return v;
    }

    private void loadSpinnerData1() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("state", MarketPricesActivity.stateSpinner.getSelectedItem().toString());
            jsonBody.put("district",spinnerDtC.getSelectedItem().toString());
            final String mRequestBody = jsonBody.toString();
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() + "getCommoditiesList",new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {

                public void onResponse(JSONObject response) {
                    Log.e("Log1"," response:---"+response);

                    try{
                        JSONObject json=new JSONObject(response.toString());
                        JSONArray array=json.getJSONArray("commodities");
                        List<String> marketName = new ArrayList<String>();
                        commName=new ArrayList<HashMap<String, String>>();
                        for(int i=0; i<array.length(); i++) {

                            final JSONObject obj = array.getJSONObject(i);
                            marketName.add(obj.getString("Market"));
                            //commName.add(obj.getString("Commodity"));

                              /*  HashMap<String,String>  hm1=new HashMap<String, String>();
                                hm1.put("", obj.getString("Market"));
                                MarketName.add(String.valueOf(hm1));
                                */
                            hm=new HashMap<String, String>();
                            hm.put("market",obj.getString("Market"));
                            hm.put("commodity", obj.getString("Commodity"));
                            commName.add(hm);

                            Set<String> uniqueList;
                            uniqueList= new HashSet<String>(marketName);
                            marketName.clear();
                            marketName.addAll(uniqueList);

                            item1 = new Model1(obj.getString("Market"),obj.getString("Commodity"),
                                    obj.getString("Units"),obj.getString("Variety"),
                                    obj.getString("Date"),obj.getString("Modal_price"),
                                    obj.getString("Unit_of_price"),obj.getString("Max_price"),
                                    obj.getString("Min_price")
                            );
                            productList.add(item1);

                            final CustomSpinnerAdapter1 customSpinnerAdapter = new CustomSpinnerAdapter1
                                    (CommoditiesActivity.this, marketName);
                            spinner1.setAdapter(customSpinnerAdapter);
                            // String myString = MarketPricesActivity.districtSpinner.getSelectedItem().toString();
                            // int sp_position = customSpinnerAdapter.getPosition(myString);
                            //spinner1.setSelection(sp_position);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinner2.setSelection(position);
                                    int j=0;
                                    com=new ArrayList<String>();

                                    for (int i=0;i<commName.size();i++){

                                        hm=commName.get(i);
                                        if(hm.get("market").equals(spinner1.getSelectedItem().toString()))
                                        {
                                            com.add(j, hm.get("commodity"));
                                            j++;
                                        }
                                    }
                                    CustomSpinnerAdapter2 customSpinnerAdapter1 = new CustomSpinnerAdapter2
                                            (CommoditiesActivity.this, com);
                                    spinner2.setAdapter(customSpinnerAdapter1);

                                    //Model1 item = productList.get(position);
                                   /* String str="hao"+item.getMarket().toString()+item.getCommodity().toString()
                                            +item.getUnits().toString()+item.getVariety().toString()
                                            +item.getDate().toString()+item.getModalprice().toString()
                                            +item.getUnitofPrice().toString()+item.getMaxprice().toString()
                                            +item.getMinprice().toString();*/
                                    // Toast.makeText(getActivity().getApplicationContext(),str,Toast.LENGTH_LONG).show();
                                    lview.setSelection(position);
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    for (int i=0;i<productList.size();i++){
                                        Model1 item = productList.get(i);
                                        String str=spinner2.getSelectedItem().toString()+item.getMarket().toString()+item.getCommodity().toString()
                                                +item.getUnits().toString()+item.getVariety().toString()
                                                +item.getDate().toString()+item.getModalprice().toString()
                                                +item.getUnitofPrice().toString()+item.getMaxprice().toString()
                                                +item.getMinprice().toString();

                                        if (item.getCommodity().equals(spinner2.getSelectedItem().toString())){
                                            // Toast.makeText(getActivity().getApplicationContext(),str,Toast.LENGTH_LONG).show();
                                            lview.setSelection(i);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ListviewAdapter1 adapter = new ListviewAdapter1(getActivity(), productList);
                            lview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {

                                }
                            });
                        }
                    }catch(JSONException e){}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSpinnerData2() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("state", MarketPricesActivity.stateSpinner.getSelectedItem().toString());
            jsonBody.put("district", MarketPricesActivity.districtSpinner.getSelectedItem().toString());
            final String mRequestBody = jsonBody.toString();
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() + "getCommoditiesList",new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {

                public void onResponse(JSONObject response) {
                    Log.e("Log1"," response:---"+response);

                    try{
                        JSONObject json=new JSONObject(response.toString());
                        JSONArray array=json.getJSONArray("commodities");
                        List<String> marketName = new ArrayList<String>();
                        commName=new ArrayList<HashMap<String, String>>();
                        for(int i=0; i<array.length(); i++) {

                                final JSONObject obj = array.getJSONObject(i);
                                marketName.add(obj.getString("Market"));
                                //commName.add(obj.getString("Commodity"));

                              /*  HashMap<String,String>  hm1=new HashMap<String, String>();
                                hm1.put("", obj.getString("Market"));
                                MarketName.add(String.valueOf(hm1));
                                */
                                hm=new HashMap<String, String>();
                                hm.put("market",obj.getString("Market"));
                                hm.put("commodity", obj.getString("Commodity"));
                                commName.add(hm);

                            Set<String> uniqueList;
                            uniqueList= new HashSet<String>(marketName);
                            marketName.clear();
                            marketName.addAll(uniqueList);

                                    item1 = new Model1(obj.getString("Market"),obj.getString("Commodity"),
                                            obj.getString("Units"),obj.getString("Variety"),
                                            obj.getString("Date"),obj.getString("Modal_price"),
                                            obj.getString("Unit_of_price"),obj.getString("Max_price"),
                                            obj.getString("Min_price")
                                    );
                                    productList.add(item1);

                            final CustomSpinnerAdapter1 customSpinnerAdapter = new CustomSpinnerAdapter1
                                    (CommoditiesActivity.this, marketName);
                            spinner1.setAdapter(customSpinnerAdapter);
                           // String myString = MarketPricesActivity.districtSpinner.getSelectedItem().toString();
                           // int sp_position = customSpinnerAdapter.getPosition(myString);
                            //spinner1.setSelection(sp_position);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinner2.setSelection(position);
                                    int j=0;
                                    com=new ArrayList<String>();

                                    for (int i=0;i<commName.size();i++){

                                        hm=commName.get(i);
                                        if(hm.get("market").equals(spinner1.getSelectedItem().toString()))
                                        {
                                            com.add(j, hm.get("commodity"));
                                            j++;
                                        }
                                    }
                                    CustomSpinnerAdapter2 customSpinnerAdapter1 = new CustomSpinnerAdapter2
                                            (CommoditiesActivity.this, com);
                                    spinner2.setAdapter(customSpinnerAdapter1);

                                    //Model1 item = productList.get(position);
                                   /* String str="hao"+item.getMarket().toString()+item.getCommodity().toString()
                                            +item.getUnits().toString()+item.getVariety().toString()
                                            +item.getDate().toString()+item.getModalprice().toString()
                                            +item.getUnitofPrice().toString()+item.getMaxprice().toString()
                                            +item.getMinprice().toString();*/
                                   // Toast.makeText(getActivity().getApplicationContext(),str,Toast.LENGTH_LONG).show();
                                    lview.setSelection(position);
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    for (int i=0;i<productList.size();i++){
                                        Model1 item = productList.get(i);
                                        String str=spinner2.getSelectedItem().toString()+item.getMarket().toString()+item.getCommodity().toString()
                                                +item.getUnits().toString()+item.getVariety().toString()
                                                +item.getDate().toString()+item.getModalprice().toString()
                                                +item.getUnitofPrice().toString()+item.getMaxprice().toString()
                                                +item.getMinprice().toString();

                                        if (item.getCommodity().equals(spinner2.getSelectedItem().toString())){
                                           // Toast.makeText(getActivity().getApplicationContext(),str,Toast.LENGTH_LONG).show();
                                            lview.setSelection(i);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                             ListviewAdapter1 adapter = new ListviewAdapter1(getActivity(), productList);
                            lview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {

                                }
                            });
                        }
                    }catch(JSONException e){}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CustomSpinnerAdapter1 extends BaseAdapter implements SpinnerAdapter {

        private final CommoditiesActivity activity;
        private List<String> asr;

        public CustomSpinnerAdapter1(CommoditiesActivity context, List<String> asr) {
            this.asr = asr;
            activity = context;
        }

        public int getCount() {
            return asr.size();
        }
        public int getPosition(String str){return asr.indexOf(str);}

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

    private class CustomSpinnerAdapter2 extends BaseAdapter implements SpinnerAdapter {

        private final CommoditiesActivity activity;
        private List<String> asr;

        public CustomSpinnerAdapter2(CommoditiesActivity context, List<String> asr) {
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
}

