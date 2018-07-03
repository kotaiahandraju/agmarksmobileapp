package agmark.com.agmarks;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import agmark.com.agmarks.Adapters.PageAdapter;
import agmark.com.agmarks.Weather.Function;
import agmark.com.agmarks.Weather.weather_forecost;
import agmark.com.agmarks.Weather.weather;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;
    EditText pincode;
    ImageView search;
    LocationManager locationManager;
    TextView Latitude1, Longitude1;
    double lat, lon;
    String Latitude = null, Longitude = null;
    String latt, longg;
    public List<weather> list;
    public List<weather> list2;
    public List<weather> list3;
    public List<weather> list4;
    public List<weather> list5;
    TabLayout tabLayout;
    List<String> lst = new ArrayList<String>();
    ViewPager viewPager;
    int tabsize =0;
    //RecyclerView recyclerView;
    public weather_forecost adapter;
    public weather weather ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    Gson gson = new Gson();
    ImageButton zip,loc,back;
    TextView title;
    String json=null ,json2=null,json3=null,json4=null,json5=null;
int count =0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("welcome");
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");




        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        tabLayout=(TabLayout)findViewById(R.id.tablayout1);
        viewPager=(ViewPager)findViewById(R.id.viewpager1);
       zip=(ImageButton)findViewById(R.id.pincode1);
       loc=(ImageButton)findViewById(R.id.loc);
        pincode=(EditText)findViewById(R.id.pincode);
        search =(ImageView)findViewById(R.id.search);
        back=(ImageButton)findViewById(R.id.ib_back);
        title=(TextView)findViewById(R.id.titles);
        pincode.setVisibility(View.INVISIBLE);
        search.setVisibility(View.INVISIBLE);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();

        weatherIcon.setTypeface(weatherFont);
        getLocation();
        Location();
        //perfome();
        //loading(Latitude, Longitude);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opeartion(1);
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opeartion(0);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zipcode(pincode.getText().toString());
                 perfome("http://api.openweathermap.org/data/2.5/forecast?zip="+pincode.getText().toString()+"&appid=990274466483cd969f2a96f84311fb8d&units=metric");
            }
        });




    }
    public void Zipcode(String code){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://maps.googleapis.com/maps/api/geocode/json?components=postal_code:"+code+"&sensor=false",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response",response.toString());
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    JSONArray jsonArray= jsonObject.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject2 = jsonObject1.optJSONObject("geometry");
                        JSONObject jsonObject3 = jsonObject2.getJSONObject("location");

                        String lat=jsonObject3.getString("lat");
                        String lon=jsonObject3.getString("lng");
                        //Log.i("location ",""+lat+"    "+lon);
                        loading(lat, lon);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);


}



    private void loading(String l1, String l2) {
         perfome("http://api.openweathermap.org/data/2.5/forecast?lat="+l1+"&lon="+l2+"&appid=990274466483cd969f2a96f84311fb8d&units=metric");
        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: " + weather_humidity);
                pressure_field.setText("Pressure: " + weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));
                title.setText(cityField.getText());

            }
        });
        //asyncTask.execute("16.4227", "80.5768"); //  asyncTask.execute("Latitude", "Longitude")
        asyncTask.execute("" + l1, "" + l2);



    }

    private void Location() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void onLocationChanged(Location location){
        // locationText.setText("latitude:"+location.getLatitude()+"\n Longitude:"+location.getLongitude());
        lat=location.getLatitude();
        lon=location.getLongitude();
        latt=Double.toString(lat);
        longg =Double.toString(lon);
        //Latitude1.setText(latt.substring(0,8));
        //Longitude1.setText(longg.substring(0,8));
        //loading(Latitude1.getText().toString(),Longitude1.getText().toString());
        loading(latt.substring(0,8),longg.substring(0,8));

        //Toast.makeText(getApplicationContext(),""+Latitude+" "+Longitude,Toast.LENGTH_SHORT).show();
        try{
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            // locationText.setText(locationText.getText()+"\n"+addresses.get(0).getAddressLine(0)+","+addresses.get(0).getAddressLine(1)+","+addresses.get(0).getAddressLine(2));

        }catch(Exception e){

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(),"Please enable gps and Internet",Toast.LENGTH_SHORT).show();

    }

    //http://api.openweathermap.org/data/2.5/forecast?lat=16.4332524&lon=80.5346306&mode=json&appid=990274466483cd969f2a96f84311fb8d&units=metric
    private void perfome(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final int k=0;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            //getSupportActionBar().setTitle(jsonObject.getString("city"));
                           JSONArray jsonArray= jsonObject.getJSONArray("list");
                           for(int i=0;i<jsonArray.length();i++) {

                                      JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                      //Log.i("date",jsonObject1.getString("dt_txt"));
                                      JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonObject1));
                                      JSONArray jsonArray1 = jsonObject2.getJSONArray("weather");
                                      JSONObject jsonObject3 = jsonArray1.getJSONObject(k);
                                      //Log.i("weather",jsonObject3.getString("icon")+"  "+jsonObject3.getString("description"));
                                      JSONObject jsonObject4 = jsonObject1.getJSONObject("main");

                                      lst.add(String.valueOf(jsonObject1.getString("dt_txt").substring(0,10)));
                                      //Log.i("dates",lst.toString());
                               Object[] st = lst.toArray();
                               for (Object s : st) {
                                   if (lst.indexOf(s) != lst.lastIndexOf(s)) {
                                       lst.remove(lst.lastIndexOf(s));

                                   }
                               }
                               dateFuncation(lst,jsonObject1.getLong("dt")*1000,jsonObject1.getString("dt_txt").substring(0,10), jsonObject4.getString("temp"), jsonObject4.getString("pressure"), jsonObject4.getString("humidity"), jsonObject3.getString("description"), jsonObject3.getString("icon"));
                               //store(jsonObject1.getLong("dt")*1000,jsonObject1.getString("dt_txt"), jsonObject4.getString("temp"), jsonObject4.getString("pressure"), jsonObject4.getString("humidity"), jsonObject3.getString("description"), jsonObject3.getString("icon"));//Log.i("main",jsonObject4.getString("temp")+"  "+jsonObject4.getString("humidity")+"  "+jsonObject4.getString("pressure"));
                               //weather = new weather(jsonObject1.getString("dt_txt"), jsonObject4.getString("temp"), jsonObject4.getString("pressure"), jsonObject4.getString("humidity"), jsonObject3.getString("description"), jsonObject3.getString("icon"));
                                //list.add(weather);
                                   //Log.i("dataa",""+list.size());

                           }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        tabstext(lst);



                        editor.apply();
                        //Log.i("date",lst.toString());

                        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });

                        //adapter = new weather_forecost(MainActivity.this,list);
                        //recyclerView.setAdapter(adapter);
                        //json =gson.toJson(list);




                        //editor.putString("list1",json);






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);




    }
private void dateFuncation(List<String> lst,long dt, String dt_txt, String temp, String pressure, String humidity, String description, String icon) {

        SimpleDateFormat df =new SimpleDateFormat();
        String date =df.format(new Date(dt));

        Date today = new Date();
        //String dt1 = today.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        Date dayoftomorrow = new Date(today.getTime() + (2000 * 60 * 60 * 24));
        Date dayoftomorrow2 = new Date(today.getTime() + (3000 * 60 * 60 * 24));
        Date dayoftomorrow3 = new Date(today.getTime() + (4000 * 60 * 60 * 24));
        //simpleDateFormat.format(today);

        String tt = simpleDateFormat.format(today);
        String tt2 = simpleDateFormat.format(tomorrow);
        String tt3 = simpleDateFormat.format(dayoftomorrow);
        String tt4 = simpleDateFormat.format(dayoftomorrow2);
        String tt5 = simpleDateFormat.format(dayoftomorrow3);
        //Log.i("tdy","today"+ date+"   "+tt);
        //Log.i("tdy","today"+ date.substring(0,7)+"   "+tt.substring(0,7));
        for(int i=0;i<lst.size();i++){
        if (tt.substring(0,7).equals(date.substring(0,7))) {
            editor.remove("list");
            if (lst.get(i).toString().equals(dt_txt.substring(0, 10))) {
                weather = new weather(date,dt_txt, temp, pressure, humidity, description, icon);
                //list.add(weather);
                //Log.i("list1", "" + list.size());
                list.add(weather);
                json =gson.toJson(list);
                editor.putString("list",json);
                Log.i("list1",""+list.size());

                //Log.i("today", "today" + date + "    " + tt);
            }
            } else if (tt2.substring(0, 7).equals(date.substring(0, 7))) {

                if(lst.get(i).toString().equals(dt_txt.substring(0,10))){
                    weather = new weather(date,dt_txt,temp,pressure,humidity,description,icon);
                    //list2.add(weather);
                    //Log.i("list2",""+list2.size());
                    list2.add(weather);
                    json2=gson.toJson(list2);
                    editor.putString("list2",json2);
                    Log.i("list2",""+list2.size());
                    listsize(list.size());
               // Log.i("ttomorrow", "tomorrow" + date + "    " + tt2);
                }
            } else if (tt3.substring(0, 7).equals(date.substring(0, 7)))
            {
                if(lst.get(i).toString().equals(dt_txt.substring(0,10))){
                    weather = new weather(date,dt_txt,temp,pressure,humidity,description,icon);
                    //list3.add(weather);
                    //Log.i("list3",""+list3.size());
                    list3.add(weather);
                    json3=gson.toJson(list3);
                    editor.putString("list3",json3);
                    Log.i("list3",""+list3.size());
            //Log.i("day of tomorrow 2 date","day of tomorrow 2 date"+dt_txt.toString());
                } //Log.i("day of tomorrow", "day of tomorrow" + date + "    " + tt3);
            } else if (tt4.substring(0, 7).equals(date.substring(0, 7)))
            {
                if(lst.get(i).toString().equals(dt_txt.substring(0,10))){
                    weather = new weather(date,dt_txt,temp,pressure,humidity,description,icon);
                    //list4.add(weather);
                    //Log.i("list4",""+list4.size());
                    list4.add(weather);
                    json4 = gson.toJson(list4);
                    editor.putString("list4", json4);
                    Log.i("list4",""+list4.size());
                //Log.i("day of tomorrow 2 date","day of tomorrow 2 date"+dt_txt.toString());
            }
                //Log.i("day of tomorrow 2", "day of tomorrow 2" + date + "    " + tt4);
            } else if (tt5.substring(0, 7).equals(date.substring(0, 7)))
            {
                if(lst.get(i).toString().equals(dt_txt.substring(0,10)))
                {
                    weather = new weather(date,dt_txt,temp,pressure,humidity,description,icon);
                    //list5.add(weather);
                    //Log.i("list5",""+list5.size());
                    list5.add(weather);
                    json5=gson.toJson(list5);
                    editor.putString("list5",json5);
                    Log.i("list5",""+list5.size());
                //Log.i("day of tomorrow 3 date","day of tomorrow 3 date"+dt_txt.toString());
                }
                //Log.i("day of tomorrow 3", "day of tomorrow 3" + date + "    " + tt5);
            } else {
                Log.i("other", "otherr days" + date + "    " + simpleDateFormat);
            }
        }

 }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.zipcode:
                Toast.makeText(getApplicationContext(),"pin code option",Toast.LENGTH_SHORT).show();
                opeartion(1);
                return true;
            case R.id.location_base:
                Toast.makeText(getApplicationContext(),"location based",Toast.LENGTH_SHORT).show();
                //loading(Latitude,Longitude);
                opeartion(0);
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }
     private void opeartion(int code) {
        if (code == 0) {
            getLocation();
            loading(Latitude, Longitude);
            pincode.setVisibility(View.INVISIBLE);
            search.setVisibility(View.INVISIBLE);
        }else{
            pincode.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
        }
}
public void tabstext(List<String> lss){

        if(tabsize == 0){
        for (int l=1;l<lss.size();l++)
        {
           tabLayout.addTab(tabLayout.newTab().setText(lss.get(l)));
        }
        tabsize = lss.size();
        }else{
            Log.i("tab size","tab alread set");
}
}
public void listsize(int size){
if(size==8){
    //editor.remove("list2");
}
}
}