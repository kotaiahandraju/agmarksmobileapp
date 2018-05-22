package example.com.agmarks;

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

/**
 * Created by Admin on 13-04-2018.
 */

public class VegetablesActivity extends Fragment{
    Spinner spinner1,spinner2;
    private ArrayList<Model2> productList;
    FragmentTransaction transaction;
    ArrayList<String> MarketName=new ArrayList<String>();
    ArrayList<String> VegetableName=new ArrayList<String>();
    ListView simpleList;
    String countryList[] = {"Local_rate", "Date", "Id"};
    Model2 item1,item2,item3,item4,item5,item6,item7;
    ListView lview;

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
        spinner1=(Spinner)v.findViewById(R.id.spinnerCustom1);
        spinner2=(Spinner)v.findViewById(R.id.spinnerCustom2);
        spinner2.setEnabled(false);
        loadSpinnerData2();

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

        transaction.addToBackStack(null);
        transaction.commit();
        return v;
    }



    private void loadSpinnerData2() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("state", "Andhra Pradesh");
            jsonBody.put("district", "GUNTUR");
            final String mRequestBody = jsonBody.toString();
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    "http://charvikent.in/agmarks/rest/getvegpriceList",new JSONObject(mRequestBody),
                    new Response.Listener<JSONObject>() {

                public void onResponse(JSONObject response) {
                    Log.e("Log1"," response:---"+response);

                    try{
                        JSONObject json=new JSONObject(response.toString());
                        JSONArray array=json.getJSONArray("vegpriceList");
                        for(int i=0; i<array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);
                            HashMap<String,String> hm1=new HashMap<String, String>();
                            hm1.put(" ", obj.getString("Market"));
                            MarketName.add(String.valueOf(hm1));

                            HashMap<String,String>  hm2=new HashMap<String, String>();
                            hm2.put(" ", obj.getString("Vegetables"));
                            VegetableName.add(String.valueOf(hm2));

                            item1 = new Model2(obj.getString("Market"),obj.getString("Vegetables"),
                                    obj.getString("Local_rate"),obj.getString("Date"),
                                    obj.getString("Id")
                            );
                            productList.add(item1);

                            final CustomSpinnerAdapter1 customSpinnerAdapter = new CustomSpinnerAdapter1
                                    (VegetablesActivity.this, MarketName);
                            spinner1.setAdapter(customSpinnerAdapter);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinner2.setSelection(position);
                                    //Toast.makeText(getActivity(), "selected", Toast.LENGTH_SHORT).show();
                                    Model2 item = productList.get(position);
                                    String str="hao"+item.getMarket().toString()+item.getRate().toString()
                                            +item.getDate().toString()+item.getId().toString()
                                            +item.getDate().toString();
                                    //Toast.makeText(getActivity().getApplicationContext(),str,Toast.LENGTH_LONG).show();
                                    lview.setSelection(position);

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

                            CustomSpinnerAdapter2 customSpinnerAdapter1 = new CustomSpinnerAdapter2
                                (VegetablesActivity.this, VegetableName);
                        spinner2.setAdapter(customSpinnerAdapter1);

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
        private ArrayList<String> asr;

        public CustomSpinnerAdapter1(VegetablesActivity context, ArrayList<String> asr) {
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
        private ArrayList<String> asr;

        public CustomSpinnerAdapter2(VegetablesActivity context, ArrayList<String> asr) {
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

