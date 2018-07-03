package agmark.com.agmarks;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 11-06-2018.
 */

public class HistoryFragmentF extends Fragment {
    ArrayList<String> productList,productList1;
    ArrayList<HashMap<String,String>>dataList,dataList1;
    RecyclerView recyclerView,recyclerView1,recyclerView2;
    Config config = new Config();
    String baseUrl,tokenid;
    HistoryAdapterF adapter;
    HistoryAdapterF1 adapter1;
    HistoryAdapterF2 adapter2;
    TextView txtNoH0,textView1,textView2;
    SharedPreferences sharedPreferences;
    SearchView searchView,searchView1,searchView2;
    public static HistoryFragmentF newInstance() {
        HistoryFragmentF fragment = new HistoryFragmentF();
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
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_historyf, container, false);
        txtNoH0=(TextView)v.findViewById(R.id.txtNoH0);
        textView1=(TextView)v.findViewById(R.id.txtNoH1);
        textView2=(TextView)v.findViewById(R.id.txtNoH2);
        searchView=(SearchView)v.findViewById(R.id.inputSearch);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        //EditText text = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        EditText editText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setHint("Search");
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                try {
                    adapter.getFilter().filter(query);
                }catch (Exception e){e.printStackTrace();}
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                try {
                    adapter.getFilter().filter(query);
                }catch (Exception e){e.printStackTrace();}
                return false;
            }
        });


        searchView1=(SearchView)v.findViewById(R.id.inputSearch1);
        SearchManager searchManager1 = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        //EditText text = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        EditText editText1 = (EditText) searchView1.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText1.setHint("Search");
        editText1.setTextColor(Color.WHITE);
        editText1.setHintTextColor(Color.WHITE);
        searchView1.setSearchableInfo(searchManager1
                .getSearchableInfo(getActivity().getComponentName()));
        searchView1.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                try {
                    adapter1.getFilter().filter(query);
                }catch (Exception e){e.printStackTrace();}
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //  if(adapter1.getItemCount()>0) {
                try {
                    adapter1.getFilter().filter(query);
                }catch (Exception e){e.printStackTrace();}
                //}
               /* else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
                }*/
                return false;
            }
        });
        searchView2=(SearchView)v.findViewById(R.id.inputSearch2);
        SearchManager searchManager2 = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        //EditText text = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        EditText editText2 = (EditText) searchView2.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText2.setHint("Search");
        editText2.setTextColor(Color.WHITE);
        editText2.setHintTextColor(Color.WHITE);
        searchView2.setSearchableInfo(searchManager2
                .getSearchableInfo(getActivity().getComponentName()));
        searchView2.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                try {
                    adapter2.getFilter().filter(query);
                }catch (Exception e){e.printStackTrace();}
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                try {
                    adapter2.getFilter().filter(query);
                }catch (Exception e){e.printStackTrace();}
                return false;
            }
        });

        baseUrl = config.get_url();
        sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);
        tokenid=sharedPreferences.getString("tokenid", "");
        ImageButton ib_back = (ImageButton) v.findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //getActivity().onBackPressed();
                Intent intent = new Intent(getActivity().getApplicationContext(), FarmerDashboard.class);
                startActivity(intent);


            }
        });

        getDetailsFromServer();
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        getDetailsFromServer1();
        recyclerView1 = (RecyclerView) v.findViewById(R.id.recyclerView5);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        getDetailsFromServer2();
        recyclerView2 = (RecyclerView) v.findViewById(R.id.recyclerView6);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return v;
    }

    private void getDetailsFromServer() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("tokenId",tokenid);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() + "farmerpostingshistory", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());

                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("farmerhistory");
                        productList = new ArrayList<String>();
                        dataList=new ArrayList<HashMap<String, String>>();
                            if (jsonarray.length()>0) {
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    JSONObject obj = jsonarray.getJSONObject(i);
                                    HashMap<String, String> hm = new HashMap<String, String>();
                                    String st = obj.getString("Transaction_type").toString();
                                    String st1 = st.substring(st.lastIndexOf("_") + 1);
                                    String st2="";
                                    if(st1.equalsIgnoreCase("buy"))
                                        st2="Buy";
                                    if(st1.equalsIgnoreCase("sell"))
                                        st2="Sell";
                                    productList.add(i, obj.getString("Crop_name").toString() + ": " + st2 + ",     " + obj.getString("Date").toString());

                                    hm.put("crop", obj.getString("Crop_name").toString());
                                    hm.put("category", obj.getString("Category").toString());
                                    hm.put("input", obj.getString("Input").toString());
                                    hm.put("variety", obj.getString("Variety").toString());
                                    hm.put("quantity", obj.getString("Quantity").toString());
                                    hm.put("units", obj.getString("Units").toString());
                                    hm.put("area", obj.getString("Area").toString());
                                    hm.put("area_units", obj.getString("Area_Units").toString());
                                    hm.put("comment", obj.getString("Comment").toString());
                                    hm.put("status", obj.getString("Status").toString());
                                    dataList.add(hm);

                                }
                                adapter = new HistoryAdapterF(getActivity(), productList, dataList);
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                txtNoH0.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }

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

    private void getDetailsFromServer1() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("tokenId",tokenid);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() + "fdapostingshistory", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());

                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("fdahistory");
                        productList = new ArrayList<String>();
                        dataList = new ArrayList<HashMap<String, String>>();
                        if (jsonarray.length()>0) {

                            for (int i = 0; i < jsonarray.length(); i++) {
                                recyclerView1.setVisibility(View.VISIBLE);
                                JSONObject obj = jsonarray.getJSONObject(i);
                                HashMap<String, String> hm = new HashMap<String, String>();
                                String st = obj.getString("Transaction_type").toString();
                                String st1 = st.substring(st.lastIndexOf("_") + 1);
                                String st2="";
                                if(st1.equalsIgnoreCase("buy"))
                                    st2="Buy";
                                if(st1.equalsIgnoreCase("sell"))
                                    st2="Sell";
                                productList.add(i, obj.getString("Live_stock").toString() + ": " + st2 + ",     " + obj.getString("Date").toString());
                                hm.put("livestock", obj.getString("Live_stock").toString());
                                hm.put("input", obj.getString("Inputs").toString());
                                hm.put("variety", obj.getString("Variety").toString());
                                hm.put("quantity", obj.getString("Quantity").toString());
                                hm.put("unit", obj.getString("Unit").toString());
                                hm.put("milkyield", obj.getString("Milk_yield").toString());
                                hm.put("price", obj.getString("Price").toString());
                                hm.put("nearestmarket", obj.getString("Nearest_market").toString());
                                hm.put("comment", obj.getString("Comment").toString());
                                hm.put("status", obj.getString("Status").toString());
                                dataList.add(hm);

                            }
                            adapter1 = new HistoryAdapterF1(getActivity(), productList, dataList);
                            recyclerView1.setAdapter(adapter1);
                        }
                        else {
                            textView1.setVisibility(View.VISIBLE);
                            recyclerView1.setVisibility(View.GONE);
                        }

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
    private void getDetailsFromServer2() {
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("tokenId",tokenid);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url() +"clinicpostingshistory", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());

                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("clinichistory");
                        JSONArray jsonarrayi = json.getJSONArray("imageList");
                        productList = new ArrayList<String>();
                        dataList=new ArrayList<HashMap<String, String>>();
                        if(!json.toString().isEmpty()){
                        //if (jsonarrayi.length()>0 && jsonarray.length()>0) {
                            for (int i = 0; i < jsonarray.length(); i++) {
                                recyclerView2.setVisibility(View.VISIBLE);
                                JSONObject obj = jsonarray.getJSONObject(i);
                                JSONObject obji = jsonarrayi.getJSONObject(i);
                                //Log.e("image", "image: " + obji.getString(obj.getString("imgName").toString()).toString());
                                HashMap<String, String> hm = new HashMap<String, String>();
                                String st = obj.getString("type").toString();
                                String []st1 = st.split("_");
                                productList.add(i, st1[0]+",     "+obj.getString("strdate").toString()) ;
                                hm.put("sno", obj.getString("SNo").toString());
                                hm.put("comment", obj.getString("comment").toString());
                                hm.put("status", obj.getString("status").toString());
                                hm.put("date", obj.getString("date").toString());
                                hm.put("type", obj.getString("type").toString());
                                hm.put("mobile", obj.getString("mobile").toString());
                                hm.put("imagename", obj.getString("imgName").toString());
                                hm.put("image", obji.getString(obj.getString("imgName").toString()).toString());
                                hm.put("farmername", obj.getString("farmerName").toString());
                                dataList.add(hm);

                            }
                            adapter2 = new HistoryAdapterF2(getActivity(), productList, dataList, tokenid);
                            recyclerView2.setAdapter(adapter2);
                        }
                        else {
                            textView2.setVisibility(View.VISIBLE);
                            recyclerView2.setVisibility(View.GONE);
                        }
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
}
