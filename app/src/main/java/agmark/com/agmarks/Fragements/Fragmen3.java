package agmark.com.agmarks.Fragements;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import agmark.com.agmarks.R;
import agmark.com.agmarks.Weather.weather;
import agmark.com.agmarks.Weather.weather_forecost;


public class Fragmen3 extends Fragment {
    public List<weather> list;
    public weather_forecost adpater;
    public String id;
    RecyclerView rv;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sp =getActivity().getSharedPreferences("weather",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("list4",null);
        Type type= new TypeToken<ArrayList<weather>>(){}.getType();
        list=gson.fromJson(json,type);
        if(list== null){
            list= new ArrayList<>();
        }
        adpater = new weather_forecost(getActivity().getApplicationContext(),list);

        //Log.i("fragement 1",""+list.get(0).toString());
        View view= inflater.inflate(R.layout.fragment_fragmen3, container, false);
        rv=(RecyclerView)view.findViewById(R.id.rv03);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adpater);
        return view;
    }

    }
