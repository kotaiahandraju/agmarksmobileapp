package agmark.com.agmarks;

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
import android.widget.ImageButton;
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

/**
 * Created by Admin on 13-04-2018.
 */

public class VegetablesActivity extends Fragment{
    Spinner spinnerDtV;
    Spinner spinner1,spinner2;
    ImageView backImg;
    private ArrayList<Model2> productList;
    FragmentTransaction transaction;
    TextView txtDistrict;
    ArrayList<String> MarketName=new ArrayList<String>();
    ArrayList<String> VegetableName=new ArrayList<String>();
    ListView simpleList;
    String countryList[] = {"Local rate", "Date", "Id"};
    Model2 item2;
    ListView lview;
        ArrayList<HashMap<String, String>> vegName;
    HashMap<String,String>  hm;
    List<String> veg;
    Config config = new Config();
    String baseUrl;
    public static VegetablesActivity newInstance() {
        VegetablesActivity fragment = new VegetablesActivity();
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
        v = inflater.inflate(R.layout.activity_vegetables, container, false);

        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        spinnerDtV=(Spinner) v.findViewById(R.id.disSpinnerV);
        spinner1=(Spinner)v.findViewById(R.id.spinnerCustom1);
        spinner2=(Spinner)v.findViewById(R.id.spinnerCustom2);

        if (MarketPricesActivity.stateSpinner.getSelectedItem().toString().equals("Andhra Pradesh")){
            ArrayAdapter adapter=new ArrayAdapter<String>
                    (getActivity(),android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.items_ap_districts));
            spinnerDtV.setAdapter(adapter);
            String string=MarketPricesActivity.districtSpinner.getSelectedItem().toString();
            int pos=adapter.getPosition(string);
            spinnerDtV.setSelection(pos);
        }
        else if (MarketPricesActivity.stateSpinner.getSelectedItem().toString().equals("Telangana")){
            ArrayAdapter adapter1=new ArrayAdapter<String>
                    (getActivity(),android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.items_tn_districts));
            spinnerDtV.setAdapter(adapter1);
            String string=MarketPricesActivity.districtSpinner.getSelectedItem().toString();
            int pos=adapter1.getPosition(string);
            spinnerDtV.setSelection(pos);
        }

        spinnerDtV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadSpinnerData1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadSpinnerData2();
        baseUrl = config.get_url();
        simpleList = (ListView)v.findViewById(R.id.list1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.activity_list2, R.id.textView2, countryList);
        simpleList.setAdapter(arrayAdapter);

        productList = new ArrayList<Model2>();
        lview = (ListView)v.findViewById(R.id.list2);
        lview.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }

                return false;
            }
        });
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


        return v;
    }

    private void loadSpinnerData1() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("state", MarketPricesActivity.stateSpinner.getSelectedItem().toString());
            jsonBody.put("district", spinnerDtV.getSelectedItem().toString());
            final String mRequestBody = jsonBody.toString();
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() + "getvegpriceList",new JSONObject(mRequestBody),
                    new Response.Listener<JSONObject>() {

                        public void onResponse(JSONObject response) {
                            Log.e("Log1"," response:---"+response);

                            try{
                                JSONObject json=new JSONObject(response.toString());
                                JSONArray array=json.getJSONArray("vegpriceList");
                                List<String> marketName = new ArrayList<String>();
                                vegName=new ArrayList<HashMap<String, String>>();
                                for(int i=0; i<array.length(); i++) {

                                    final   JSONObject obj = array.getJSONObject(i);
                                    marketName.add(obj.getString("Market"));
                                    hm=new HashMap<String, String>();
                                    hm.put("market",obj.getString("Market"));
                                    hm.put("vegetables",obj.getString("Vegetables"));
                                    vegName.add(hm);

                                    Set<String> uniqueList;
                                    uniqueList=new HashSet<String>(marketName);
                                    marketName.clear();
                                    marketName.addAll(uniqueList);

                                    item2 = new Model2(obj.getString("Market"),obj.getString("Vegetables"),
                                            obj.getString("Local_rate"),obj.getString("Date"),
                                            obj.getString("Id")
                                    );
                                    productList.add(item2);

                                    final CustomSpinnerAdapter1 customSpinnerAdapter = new CustomSpinnerAdapter1
                                            (VegetablesActivity.this, marketName);
                                    spinner1.setAdapter(customSpinnerAdapter);
                                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            spinner2.setSelection(position);
                                            int j=0;
                                            veg=new ArrayList<String>();
                                            for (int i=0;i<vegName.size();i++){
                                                hm=vegName.get(i);
                                                if (hm.get("market").equals(spinner1.getSelectedItem().toString())){
                                                    veg.add(j,hm.get("vegetables"));
                                                    j++;
                                                }
                                            }
                                            CustomSpinnerAdapter2 customSpinnerAdapter1 = new CustomSpinnerAdapter2
                                                    (VegetablesActivity.this, veg);
                                            spinner2.setAdapter(customSpinnerAdapter1);
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
                                                Model2 item=productList.get(i);
                                                String str=spinner2.getSelectedItem().toString()+item.getMarket().toString()+
                                                        item.getVegetables().toString()+ item.getDate().toString()+
                                                        item.getRate().toString()+item.getId().toString();
                                                if (item.getVegetables().equals(spinner2.getSelectedItem().toString())){
                                                    // Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                                    lview.setSelection(i);
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    ListviewAdapter2 adapter = new ListviewAdapter2(getActivity(), productList);
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
                    config.get_url() + "getvegpriceList",new JSONObject(mRequestBody),
                    new Response.Listener<JSONObject>() {

                public void onResponse(JSONObject response) {
                    Log.e("Log1"," response:---"+response);

                    try{
                        JSONObject json=new JSONObject(response.toString());
                        JSONArray array=json.getJSONArray("vegpriceList");
                        List<String> marketName = new ArrayList<String>();
                        vegName=new ArrayList<HashMap<String, String>>();
                        for(int i=0; i<array.length(); i++) {

                          final   JSONObject obj = array.getJSONObject(i);
                          marketName.add(obj.getString("Market"));
                          hm=new HashMap<String, String>();
                          hm.put("market",obj.getString("Market"));
                          hm.put("vegetables",obj.getString("Vegetables"));
                          vegName.add(hm);

                            Set<String> uniqueList;
                            uniqueList=new HashSet<String>(marketName);
                            marketName.clear();
                            marketName.addAll(uniqueList);

                            item2 = new Model2(obj.getString("Market"),obj.getString("Vegetables"),
                                    obj.getString("Local_rate"),obj.getString("Date"),
                                    obj.getString("Id")
                            );
                            productList.add(item2);

                            final CustomSpinnerAdapter1 customSpinnerAdapter = new CustomSpinnerAdapter1
                                    (VegetablesActivity.this, marketName);
                            spinner1.setAdapter(customSpinnerAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinner2.setSelection(position);
                                   int j=0;
                                    veg=new ArrayList<String>();
                                    for (int i=0;i<vegName.size();i++){
                                        hm=vegName.get(i);
                                        if (hm.get("market").equals(spinner1.getSelectedItem().toString())){
                                            veg.add(j,hm.get("vegetables"));
                                            j++;
                                        }
                                    }
                                    CustomSpinnerAdapter2 customSpinnerAdapter1 = new CustomSpinnerAdapter2
                                            (VegetablesActivity.this, veg);
                                    spinner2.setAdapter(customSpinnerAdapter1);
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
                                        Model2 item=productList.get(i);
                                        String str=spinner2.getSelectedItem().toString()+item.getMarket().toString()+
                                                item.getVegetables().toString()+ item.getDate().toString()+
                                                item.getRate().toString()+item.getId().toString();
                                        if (item.getVegetables().equals(spinner2.getSelectedItem().toString())){
                                           // Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                            lview.setSelection(i);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            ListviewAdapter2 adapter = new ListviewAdapter2(getActivity(), productList);
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

        private final VegetablesActivity activity;
        private List<String> asr;

        public CustomSpinnerAdapter1(VegetablesActivity context, List<String> asr) {
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

    private class CustomSpinnerAdapter2 extends BaseAdapter implements SpinnerAdapter {

        private final VegetablesActivity activity;
        private List<String> asr;

        public CustomSpinnerAdapter2(VegetablesActivity context, List<String> asr) {
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

