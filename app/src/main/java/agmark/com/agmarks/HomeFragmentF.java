package agmark.com.agmarks;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import agmark.com.agmarks.Weather.Function;

/**
 * Created by Admin on 21-05-2018.
 */

public class HomeFragmentF extends Fragment implements LocationListener{
    Bitmap bitmap;
    SharedPreferences.Editor prefsEditor;
    FragmentTransaction transaction;
    JSONArray jsonarray1;
    CustomSpinnerAdapter customSpinnerAdapter,customSpinnerAdapterA,customSpinnerAdapterV,customSpinnerAdapterD;
    DatePickerDialog datePickerDialog;
    LinearLayout linearDairySell,linearDairyBuy,linear1,linear2,linear3,linear4,linearCrops,linearCropBuy,linearCropSell,linearCropStorage,linearCropClinic,linearCropMarket,linearVegMarket,linearAnimBuy,linearAnimSell;
    ImageView image1,image2,image3,image4,iv_clinic;
    TextView text1,text2,text3,text4,txtBuyCB,txtSellCB,txtStorageCB,txtClinicCB,txtAni;
    TextView comm,vege,anim,dairy,txtSellAni,txtDairy,txtDairySell;
    byte img_store[]=null;
    EditText txtAskpriceDaiSell,quantityDaiSell,txtNMarDaiSell,txtCommentDaisell,txtAskpriceDai,quantityDai,txtNMarDai,txtCommentDai,quantityAniSell,txtMYieldSell,txtNMarSell,txtPriceAniSell,txtCommentSell;
    EditText expDate,expDateAnim,expDateDairy,etComment,quantityCB,areaCB,varietyCB,commentCB,txtQuantityCS,txtAskpriceCS,txtVarietyCS,txtCommentCS,quantityAni,txtMYield,txtNMar,txtPriceAni,txtComment;
    Button btnSellPostDai,btnBuyPostDai,btnSellPostAni,btnBuyPostAni,btnBuyCB,btnSellCB,btnStorageCB,btnClinicCB,btnBuyPostCB,btnSellPostCS,btnBrowse,btnClinicPost;
    Config config=new Config();
    String baseUrl,tokenid,username,pwd,encodedImage,mobile,user,select,strStore;
    ListView listview,listveg,listStore;
    List<String> com;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int PICK_IMAGE_REQUEST=3;
    HashMap<String,String>  hm;
    ArrayList<HashMap<String, String>> inputA,mCategoryLists,mCategoryListsveg;
    boolean iscolorc = true,iscolorv=true,iscolora=true,iscolord=true;
    Spinner spCropV,spCropA,spCropD,spCategDaiSell,spBunchesDaiSell,spCategDai,spBunchesDai,spCrop,spCategCB,spInputCB,spBunchesCB,spAcresCB,spBunchesCS,spCategAni,spInputAni,spCategAniSell,spInputAniSell;
    String cropstr="",vegstr="",anistr="",daistr="";
    ArrayList<String> crop,veg,ani,dai;
    SharedPreferences sharedPreferences;

    //----------------------------------------------------------
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;
    LocationManager locationManager;
    TextView Latitude1,Longitude1;
    double lat,lon;
    String Latitude=null,Longitude=null;
    String latt,longg;


    private String url = "https://economictimes.indiatimes.com/news/economy/agriculture";
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBlogImageList = new ArrayList<>();
    private ArrayList<String> mBlogHrefList = new ArrayList<>();

    RecyclerView mRecyclerView;
//------------------------------

    public static HomeFragmentF newInstance() {
        HomeFragmentF fragment = new HomeFragmentF();
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
        v = inflater.inflate(R.layout.farmerlist, container, false);
        sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);
        username=sharedPreferences.getString("username", "");
        mobile=sharedPreferences.getString("mobile", "");
        pwd=sharedPreferences.getString("pwd", "");
        user=sharedPreferences.getString("user", "");
        tokenid=sharedPreferences.getString("tokenid", "");

        baseUrl= config.get_url();
        linear1=(LinearLayout)v.findViewById(R.id.linearlayout1);
        linear2=(LinearLayout)v.findViewById(R.id.linearlayout2);
        linear3=(LinearLayout)v.findViewById(R.id.linearlayout3);
        linear4=(LinearLayout)v.findViewById(R.id.linearlayout4);
        linearAnimBuy=(LinearLayout)v.findViewById(R.id.linearAnimBuy);
        linearDairyBuy=(LinearLayout)v.findViewById(R.id.linearDairyBuy);
        linearDairySell=(LinearLayout)v.findViewById(R.id.linearDairySell);
        linearAnimSell=(LinearLayout)v.findViewById(R.id.linearAnimSell);
        linearCrops=(LinearLayout)v.findViewById(R.id.linearCrops);
        linearCropBuy=(LinearLayout)v.findViewById(R.id.linearCropBuy);
        linearCropSell=(LinearLayout)v.findViewById(R.id.linearCropSell);
        linearCropStorage=(LinearLayout)v.findViewById(R.id.linearCropStorage);
        linearCropClinic=(LinearLayout)v.findViewById(R.id.linearCropClinic);
        linearCropMarket=(LinearLayout)v.findViewById(R.id.linearCropMarket);
        linearVegMarket=(LinearLayout)v.findViewById(R.id.linearVegMarket);
        image1=(ImageView)v.findViewById(R.id.img_crop);
        iv_clinic=(ImageView)v.findViewById(R.id.iv_clinic);
        listview=(ListView)v.findViewById(R.id.listview);
        listveg=(ListView)v.findViewById(R.id.listveg);
        listStore=(ListView)v.findViewById(R.id.listStore);
        image2=(ImageView)v.findViewById(R.id.img_veg);
        image3=(ImageView)v.findViewById(R.id.img_anim);
        image4=(ImageView)v.findViewById(R.id.img_dairy);
        spCrop=(Spinner)v.findViewById(R.id.spCrop);
        spCropV=(Spinner)v.findViewById(R.id.spCropV);
        spCropA=(Spinner)v.findViewById(R.id.spCropA);
        spCropD=(Spinner)v.findViewById(R.id.spCropD);
        spCategDai=(Spinner)v.findViewById(R.id.spCategDai);
        spCategDaiSell=(Spinner)v.findViewById(R.id.spCategDaiSell);
        spBunchesDaiSell=(Spinner)v.findViewById(R.id.spBunchesDaiSell);

        spCategCB=(Spinner)v.findViewById(R.id.spCategCB);
        spBunchesDai=(Spinner)v.findViewById(R.id.spBunchesDai);
        spCategAni=(Spinner)v.findViewById(R.id.spCategAni);
        spInputAni=(Spinner)v.findViewById(R.id.spInputAni);
        spInputCB=(Spinner)v.findViewById(R.id.spInputCB);
        spCategAniSell=(Spinner)v.findViewById(R.id.spCategAniSell);
        spInputAniSell=(Spinner)v.findViewById(R.id.spInputAniSell);
        spBunchesCB=(Spinner)v.findViewById(R.id.spBunchesCB);
        spBunchesCS=(Spinner)v.findViewById(R.id.spBunchesCS);
        spAcresCB=(Spinner)v.findViewById(R.id.spAcresCB);
        txtAni=(TextView)v.findViewById(R.id.txtAni);
        txtSellAni=(TextView)v.findViewById(R.id.txtSellAni);
        txtDairy=(TextView)v.findViewById(R.id.txtDairy);
        txtDairySell=(TextView)v.findViewById(R.id.txtDairySell);
        text1=(TextView)v.findViewById(R.id.txt_crop);
        text2=(TextView)v.findViewById(R.id.txt_veg);
        text3=(TextView)v.findViewById(R.id.txt_anim);
        text4=(TextView)v.findViewById(R.id.txt_dairy);
        quantityCB=(EditText) v.findViewById(R.id.quantityCB);
        areaCB=(EditText) v.findViewById(R.id.areaCB);
        commentCB=(EditText) v.findViewById(R.id.commentCB);
        varietyCB=(EditText) v.findViewById(R.id.varietyCB);
        txtBuyCB=(TextView)v.findViewById(R.id.txtBuyCB);
        txtSellCB=(TextView)v.findViewById(R.id.txtSellCB);
        txtStorageCB=(TextView)v.findViewById(R.id.txtStorageCB);
        txtClinicCB=(TextView)v.findViewById(R.id.txtClinicCB);
        btnBuyPostCB=(Button) v.findViewById(R.id.btnBuyPostCB);
        btnSellPostDai=(Button) v.findViewById(R.id.btnSellPostDai);
        btnSellPostCS=(Button) v.findViewById(R.id.btnSellPostCS);
        txtQuantityCS=(EditText) v.findViewById(R.id.txtQuantityCS);
        txtAskpriceCS=(EditText) v.findViewById(R.id.txtAskpriceCS);
        txtVarietyCS=(EditText) v.findViewById(R.id.txtVarietyCS);
        txtCommentCS=(EditText) v.findViewById(R.id.txtCommentCS);

        txtAskpriceDaiSell=(EditText) v.findViewById(R.id.txtAskpriceDaiSell);
        quantityDaiSell=(EditText) v.findViewById(R.id.quantityDaiSell);
        txtNMarDaiSell=(EditText) v.findViewById(R.id.txtNMarDaiSell);
        txtCommentDaisell=(EditText) v.findViewById(R.id.txtCommentDaisell);

        txtAskpriceDai=(EditText) v.findViewById(R.id.txtAskpriceDai);
        quantityDai=(EditText) v.findViewById(R.id.quantityDai);
        txtNMarDai=(EditText) v.findViewById(R.id.txtNMarDai);
        txtCommentDai=(EditText) v.findViewById(R.id.txtCommentDai);

        quantityAniSell=(EditText) v.findViewById(R.id.quantityAniSell);
        txtMYieldSell=(EditText) v.findViewById(R.id.txtMYieldSell);
        txtNMarSell=(EditText) v.findViewById(R.id.txtNMarSell);
        txtPriceAniSell=(EditText) v.findViewById(R.id.txtPriceAniSell);
        txtCommentSell=(EditText) v.findViewById(R.id.txtCommentSell);
        expDate=(EditText) v.findViewById(R.id.expDate);
        expDateAnim=(EditText) v.findViewById(R.id.expDateAnim);
        expDateDairy=(EditText) v.findViewById(R.id.expDateDairy);
        btnBrowse=(Button) v.findViewById(R.id.btnBrowse);
        etComment=(EditText)v.findViewById(R.id.etComment);
        quantityAni=(EditText)v.findViewById(R.id.quantityAni);
        txtMYield=(EditText)v.findViewById(R.id.txtMYield);
        txtNMar=(EditText)v.findViewById(R.id.txtNMar);
        txtPriceAni=(EditText)v.findViewById(R.id.txtPriceAni);
        txtComment=(EditText)v.findViewById(R.id.txtComment);
        btnBuyPostDai=(Button) v.findViewById(R.id.btnBuyPostDai);
        btnClinicPost=(Button) v.findViewById(R.id.btnClinicPost);
        btnBuyCB=(Button) v.findViewById(R.id.btnBuyCB);
        btnSellCB=(Button) v.findViewById(R.id.btnSellCB);
        btnStorageCB=(Button) v.findViewById(R.id.btnStorageCB);
        btnClinicCB=(Button) v.findViewById(R.id.btnClinicCB);
        btnBuyPostAni=(Button) v.findViewById(R.id.btnBuyPostAni);
        btnSellPostAni=(Button) v.findViewById(R.id.btnSellPostAni);

        //---------------------------------------------
        weatherFont = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        cityField = (TextView)v.findViewById(R.id.city_field2);
        updatedField = (TextView)v.findViewById(R.id.updated_field2);
        detailsField = (TextView)v.findViewById(R.id.details_field2);
        currentTemperatureField = (TextView)v.findViewById(R.id.current_temperature_field2);
        humidity_field = (TextView)v.findViewById(R.id.humidity_field2);
        pressure_field = (TextView)v.findViewById(R.id.pressure_field2);
        weatherIcon = (TextView)v.findViewById(R.id.weather_icon2);
        weatherIcon.setTypeface(weatherFont);

        getLocation();
        Location();
        loading(Latitude,Longitude);
        new news().execute();

        //newfeed();
        //--------------------------------------------

       mRecyclerView = (RecyclerView)v.findViewById(R.id.act_recyclerview1);

        getDetailsFromServerLog();

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spCrop.setVisibility(View.VISIBLE);
                spCropV.setVisibility(View.GONE);
                spCropA.setVisibility(View.GONE);
                spCropD.setVisibility(View.GONE);

                getDetailsFromServerLog();

                if(iscolorc) {

                    Log.e("cropstr",""+cropstr);
                    image1.setColorFilter(getResources().getColor(R.color.blue));
                    image2.setColorFilter(Color.BLACK);
                    image3.setColorFilter(Color.BLACK);
                    image4.setColorFilter(Color.BLACK);
                    text1.setTextColor(getResources().getColor(R.color.blue));
                    text2.setTextColor(Color.BLACK);
                    text3.setTextColor(Color.BLACK);
                    text4.setTextColor(Color.BLACK);
                    linearAnimBuy.setVisibility(View.GONE);
                    linearAnimSell.setVisibility(View.GONE);
                    linearDairyBuy.setVisibility(View.GONE);
                    linearDairySell.setVisibility(View.GONE);
                    linearCrops.setVisibility(View.VISIBLE);

                    //String[] words1 = cropstr.split(",");
                    Log.e("crop",""+cropstr);
                /*    crop = new ArrayList<String>();
                    crop.add("Selected Crops");
                    for (String w : words1) {
                        crop.add(w);
                    }

                    select="crop";
                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), crop);
                    spCrop.setAdapter(customSpinnerAdapter);*/

                    spCrop.setAdapter(customSpinnerAdapter);
                    spCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            String item = parent.getItemAtPosition(position).toString();

                            if(item.equals("Selected Crops")){
                                // Toast.makeText(getActivity().getApplicationContext(), "Please Select Crops", Toast.LENGTH_SHORT).show();
                                btnSellCB.setBackgroundResource(R.color.red);
                                btnStorageCB.setBackgroundResource(R.color.red);
                                btnClinicCB.setBackgroundResource(R.color.red);
                                btnBuyCB.setBackgroundResource(R.color.red);
                                linearAnimBuy.setVisibility(View.GONE);
                                linearAnimSell.setVisibility(View.GONE);
                                linearDairyBuy.setVisibility(View.GONE);
                                linearDairySell.setVisibility(View.GONE);

                                linearCropBuy.setVisibility(View.GONE);
                                linearCropSell.setVisibility(View.GONE);
                                linearCropStorage.setVisibility(View.GONE);
                                linearCropClinic.setVisibility(View.GONE);
                            }
                            else
                            {
                                if(2131099681==R.color.blue)
                                {

                                    txtBuyCB.setText("Buy::"+item);
                                    txtSellCB.setText("Sell::"+item);
                                    txtStorageCB.setText("Storage::"+item);
                                    txtClinicCB.setText("Plant Clinic::"+item);


                                }
                            }

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    expDate.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            int inType = expDate.getInputType(); // backup the input type
                            expDate.setInputType(InputType.TYPE_NULL); // disable soft input
                            expDate.onTouchEvent(event); // call native handler
                            expDate.setInputType(inType); // restore input type
                            expDate.requestFocus();
                            return true; // consume touch even
                        }
                    });
                    expDate.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        public boolean onLongClick(View v) {
                            int inType = expDate.getInputType(); // backup the input type
                            expDate.setInputType(InputType.TYPE_NULL); // disable soft input
                            //  dobf.onTouchEvent(event); // call native handler
                            expDate.setInputType(inType); // restore input type
                            expDate.requestFocus();
                            return true; // consume touch even
                        }
                    });
                    expDate.setOnClickListener(new View.OnClickListener() {
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
                                            expDate.setText(date_pick_res);

                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.setTitle("Please select date");
                            // TODO Hide Future Date Here
                            // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                            datePickerDialog.show();

                        }
                    });

                    getDetailsFromServer();

                    btnBuyCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            linearCropSell.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCrop.getSelectedItem().toString().trim().equals("Selected Crops")) {
                                linearCropBuy.setVisibility(View.VISIBLE);
                                txtBuyCB.setText("Buy::" + spCrop.getSelectedItem().toString());
                                btnBuyPostCB.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!isspinnerCateg(spCategCB)) {

                                        }
                                        if (!isspinnerInput(spInputCB)) {

                                        }

                                        if (quantityCB.getText().toString().trim().equals("")) {
                                            quantityCB.setError("Quantity is required!");
                                            quantityCB.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerUnit(spBunchesCB)) {

                                        }

                                        if (areaCB.getText().toString().trim().equals("")) {
                                            areaCB.setError("Area is required!");
                                            areaCB.setHint("please enter Area");
                                        }
                                        if (expDate.getText().toString().trim().equals("")) {
                                            expDate.setError("Expected Date is required!");
                                            expDate.setHint("please enter Expected Date");
                                        }
                                        if (!isspinnerUnit(spAcresCB)) {

                                        }
                                        if ((quantityCB.length() > 0) && (areaCB.length() > 0) && ((!spCategCB.getSelectedItem().toString().equals("Select Category")) && ((!spInputCB.getSelectedItem().toString().equals("Select Input")))) && ((!spBunchesCB.getSelectedItem().toString().equals("Unit"))) && (expDate.length()>0) && ((!spAcresCB.getSelectedItem().toString().equals("Unit")))) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerCB();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });
                    btnSellCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            btnSellCB.setBackgroundResource(R.color.blue);
                            linearCropBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCrop.getSelectedItem().toString().trim().equals("Selected Crops")) {
                                linearCropSell.setVisibility(View.VISIBLE);
                                txtSellCB.setText("Sell::" + spCrop.getSelectedItem().toString());
                                btnSellPostCS.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (txtQuantityCS.getText().toString().trim().equals("")) {
                                            txtQuantityCS.setError("Quantity is required!");
                                            txtQuantityCS.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerUnit(spBunchesCS)) {

                                        }

                                        if (txtAskpriceCS.getText().toString().trim().equals("")) {
                                            txtAskpriceCS.setError("Ask Price is required!");
                                            txtAskpriceCS.setHint("please enter Ask Price");
                                        }
                                        if ((txtQuantityCS.length() > 0) && (txtAskpriceCS.length() > 0) && ((!spBunchesCS.getSelectedItem().toString().equals("Unit")))) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerCS();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                            }
                        }

                    });
                    btnStorageCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);

                            btnStorageCB.setBackgroundResource(R.color.blue);
                            linearCropBuy.setVisibility(View.GONE);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            select="cropstore";

                            if(!spCrop.getSelectedItem().toString().trim().equals("Selected Crops")) {
                                linearCropStorage.setVisibility(View.VISIBLE);
                                txtStorageCB.setText("Storage::" + spCrop.getSelectedItem().toString());
                                getDetailsFromServerStore();
                            }
                        }

                    });
                    btnClinicCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);

                            btnClinicCB.setBackgroundResource(R.color.blue);
                            linearCropBuy.setVisibility(View.GONE);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            linearCropStorage.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            select="crop";

                            if(!spCrop.getSelectedItem().toString().trim().equals("Selected Crops")) {
                                linearCropClinic.setVisibility(View.VISIBLE);
                                txtClinicCB.setText("Plant Clinic::" + spCrop.getSelectedItem().toString());
                                btnBrowse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        img_store = null;
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


                                    }

                                });
                                btnClinicPost.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendDetailsToServerClinic();

                                    }
                                });
                            }

                        }
                    });


                }
                else
                {
                    image1.setColorFilter(Color.BLACK);
                    text1.setTextColor(Color.BLACK);
                    iscolorc = true;
                }
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spCropV.setVisibility(View.VISIBLE);
                spCrop.setVisibility(View.GONE);
                spCropA.setVisibility(View.GONE);
                spCropD.setVisibility(View.GONE);
                getDetailsFromServerLog();
                if(iscolorv)
                {
                    image2.setColorFilter(getResources().getColor(R.color.blue));
                    image1.setColorFilter(Color.BLACK);
                    image3.setColorFilter(Color.BLACK);
                    image4.setColorFilter(Color.BLACK);
                    text2.setTextColor(getResources().getColor(R.color.blue));
                    text1.setTextColor(Color.BLACK);
                    text3.setTextColor(Color.BLACK);
                    text4.setTextColor(Color.BLACK);

                    linearAnimBuy.setVisibility(View.GONE);
                    linearAnimSell.setVisibility(View.GONE);
                    linearDairyBuy.setVisibility(View.GONE);
                    linearDairySell.setVisibility(View.GONE);

                    linearCrops.setVisibility(View.VISIBLE);
                    spCropV.setAdapter(customSpinnerAdapterV);

                    spCropV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            String item = parent.getItemAtPosition(position).toString();

                            if(item.equals("Selected Vegetables")){
                                // Toast.makeText(getActivity().getApplicationContext(), "Please Select Crops", Toast.LENGTH_SHORT).show();
                                btnSellCB.setBackgroundResource(R.color.red);
                                btnStorageCB.setBackgroundResource(R.color.red);
                                btnClinicCB.setBackgroundResource(R.color.red);
                                btnBuyCB.setBackgroundResource(R.color.red);
                                linearAnimBuy.setVisibility(View.GONE);
                                linearAnimSell.setVisibility(View.GONE);
                                linearDairyBuy.setVisibility(View.GONE);
                                linearDairySell.setVisibility(View.GONE);

                                linearCropBuy.setVisibility(View.GONE);
                                linearCropSell.setVisibility(View.GONE);
                                linearCropStorage.setVisibility(View.GONE);
                                linearCropClinic.setVisibility(View.GONE);
                            }
                            else
                            {
                                if(2131099681==R.color.blue)
                                {

                                    txtBuyCB.setText("Buy::"+item);
                                    txtSellCB.setText("Sell::"+item);
                                    txtStorageCB.setText("Storage::"+item);
                                    txtClinicCB.setText("Plant Clinic::"+item);

                                }
                            }

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    getDetailsFromServer();

                    btnBuyCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.blue);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            if(!spCropV.getSelectedItem().toString().trim().equals("Selected Vegetables")) {
                                linearCropBuy.setVisibility(View.VISIBLE);
                                txtBuyCB.setText("Buy::" + spCropV.getSelectedItem().toString());
                                btnBuyPostCB.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!isspinnerCateg(spCategCB)) {

                                        }
                                        if (!isspinnerInput(spInputCB)) {

                                        }

                                        if (quantityCB.getText().toString().trim().equals("")) {
                                            quantityCB.setError("Quantity is required!");
                                            quantityCB.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerUnit(spBunchesCB)) {

                                        }

                                        if (areaCB.getText().toString().trim().equals("")) {
                                            areaCB.setError("Area is required!");
                                            areaCB.setHint("please enter Area");
                                        }
                                        if (expDate.getText().toString().trim().equals("")) {
                                            expDate.setError("Expected Date is required!");
                                            expDate.setHint("please enter Expected Date");
                                        }
                                        if (!isspinnerUnit(spAcresCB)) {

                                        }
                                        if ((quantityCB.length() > 0) && (areaCB.length() > 0) && ((!spCategCB.getSelectedItem().toString().equals("Select Category")) && ((!spInputCB.getSelectedItem().toString().equals("Select Input")))) && ((!spBunchesCB.getSelectedItem().toString().equals("Unit"))) && (expDate.length()>0) && ((!spAcresCB.getSelectedItem().toString().equals("Unit")))) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerCB();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });
                    btnSellCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);

                            btnSellCB.setBackgroundResource(R.color.blue);
                            linearCropBuy.setVisibility(View.GONE);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropV.getSelectedItem().toString().trim().equals("Selected Vegetables")) {
                                linearCropSell.setVisibility(View.VISIBLE);
                                txtSellCB.setText("Sell::" + spCropV.getSelectedItem().toString());
                                btnSellPostCS.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (txtQuantityCS.getText().toString().trim().equals("")) {
                                            txtQuantityCS.setError("Quantity is required!");
                                            txtQuantityCS.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerUnit(spBunchesCS)) {

                                        }

                                        if (txtAskpriceCS.getText().toString().trim().equals("")) {
                                            txtAskpriceCS.setError("Ask Price is required!");
                                            txtAskpriceCS.setHint("please enter Ask Price");
                                        }
                                        if ((txtQuantityCS.length() > 0) && (txtAskpriceCS.length() > 0) && ((!spBunchesCS.getSelectedItem().toString().equals("Unit")))) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerCS();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                            }
                        }

                    });
                    btnStorageCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);

                            btnStorageCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            select="vegstore";
                            linearCropBuy.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);

                            linearCropStorage.setVisibility(View.GONE);
                            if(!spCropV.getSelectedItem().toString().trim().equals("Selected Vegetables")) {
                                linearCropStorage.setVisibility(View.VISIBLE);
                                txtStorageCB.setText("Storage::" + spCropV.getSelectedItem().toString());
                                getDetailsFromServerStore();
                            }
                        }

                    });
                    btnClinicCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);

                            btnClinicCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            linearCropBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            select="veg";
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropV.getSelectedItem().toString().trim().equals("Selected Vegetables")) {
                                linearCropClinic.setVisibility(View.VISIBLE);
                                txtClinicCB.setText("Plant Clinic::" + spCropV.getSelectedItem().toString());
                                btnBrowse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        img_store = null;
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                                    }

                                });
                                btnClinicPost.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendDetailsToServerClinic();

                                    }
                                });
                            }

                        }
                    });

                    //iscolorv = false;
                }
                else
                {
                    image2.setColorFilter(Color.BLACK);
                    text2.setTextColor(Color.BLACK);
                    iscolorv = true;
                }

            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spCropA.setVisibility(View.VISIBLE);
                spCrop.setVisibility(View.GONE);
                spCropV.setVisibility(View.GONE);
                spCropD.setVisibility(View.GONE);
                getDetailsFromServerLog();
                if(iscolora)
                {
                    image3.setColorFilter(getResources().getColor(R.color.blue));
                    image1.setColorFilter(Color.BLACK);
                    image2.setColorFilter(Color.BLACK);
                    image4.setColorFilter(Color.BLACK);
                    text3.setTextColor(getResources().getColor(R.color.blue));
                    text1.setTextColor(Color.BLACK);
                    text2.setTextColor(Color.BLACK);
                    text4.setTextColor(Color.BLACK);
                    linearAnimBuy.setVisibility(View.GONE);
                    linearAnimSell.setVisibility(View.GONE);
                    linearDairyBuy.setVisibility(View.GONE);
                    linearDairySell.setVisibility(View.GONE);

                    linearCrops.setVisibility(View.GONE);
                    linearCropBuy.setVisibility(View.GONE);
                    linearCropSell.setVisibility(View.GONE);
                    linearCropStorage.setVisibility(View.GONE);
                    linearCropClinic.setVisibility(View.GONE);

                    //iscolora = false;
                    linearCrops.setVisibility(View.VISIBLE);
                    spCropA.setAdapter(customSpinnerAdapterA);
                    spCropA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            String item = parent.getItemAtPosition(position).toString();

                            if(item.equals("Selected Animals")){
                                // Toast.makeText(getActivity().getApplicationContext(), "Please Select Crops", Toast.LENGTH_SHORT).show();
                                btnSellCB.setBackgroundResource(R.color.red);
                                btnStorageCB.setBackgroundResource(R.color.red);
                                btnClinicCB.setBackgroundResource(R.color.red);
                                btnBuyCB.setBackgroundResource(R.color.red);

                                linearCropBuy.setVisibility(View.GONE);

                                linearAnimBuy.setVisibility(View.GONE);
                                linearAnimSell.setVisibility(View.GONE);
                                linearDairyBuy.setVisibility(View.GONE);
                                linearDairySell.setVisibility(View.GONE);

                                linearCropBuy.setVisibility(View.GONE);
                                linearCropSell.setVisibility(View.GONE);
                                linearCropStorage.setVisibility(View.GONE);
                                linearCropClinic.setVisibility(View.GONE);
                            }
                            else
                            {
                                if(2131099681==R.color.blue)
                                {

                                    txtAni.setText("Buy::"+item);
                                    txtSellAni.setText("Sell::"+item);
                                    txtStorageCB.setText("Storage::"+item);
                                    txtClinicCB.setText("Plant Clinic::"+item);

                                }
                            }

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    expDateAnim.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            int inType = expDateAnim.getInputType(); // backup the input type
                            expDateAnim.setInputType(InputType.TYPE_NULL); // disable soft input
                            expDateAnim.onTouchEvent(event); // call native handler
                            expDateAnim.setInputType(inType); // restore input type
                            expDateAnim.requestFocus();
                            return true; // consume touch even
                        }
                    });
                    expDateAnim.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        public boolean onLongClick(View v) {
                            int inType = expDateAnim.getInputType(); // backup the input type
                            expDateAnim.setInputType(InputType.TYPE_NULL); // disable soft input
                            //  dobf.onTouchEvent(event); // call native handler
                            expDateAnim.setInputType(inType); // restore input type
                            expDateAnim.requestFocus();
                            return true; // consume touch even
                        }
                    });
                    expDateAnim.setOnClickListener(new View.OnClickListener() {
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
                                            expDateAnim.setText(date_pick_res);

                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.setTitle("Please select date");
                            // TODO Hide Future Date Here
                            // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                            datePickerDialog.show();

                        }
                    });


                    btnBuyCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            select="buyani";
                            getDetailsFromServerAni();
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.blue);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropA.getSelectedItem().toString().trim().equals("Selected Animals")) {
                                linearAnimBuy.setVisibility(View.VISIBLE);
                                txtAni.setText("Buy::" + spCropA.getSelectedItem().toString());
                                btnBuyPostAni.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!isspinnerCategA(spCategAni)) {

                                        }
                                        if (!isspinnerInputA(spInputAni)) {

                                        }

                                        if (quantityAni.getText().toString().trim().equals("")) {
                                            quantityAni.setError("Quantity is required!");
                                            quantityAni.setHint("please enter Quantity");
                                        }
                                        if (txtPriceAni.getText().toString().trim().equals("")) {
                                            txtPriceAni.setError("Area is required!");
                                            txtPriceAni.setHint("please enter Area");
                                        }
                                        if (expDateAnim.getText().toString().trim().equals("")) {
                                            expDateAnim.setError("Expected Date is required!");
                                            expDateAnim.setHint("please enter Expected Date");
                                        }
                                        if ((quantityAni.length() > 0) && (txtPriceAni.length() > 0) && ((!spCategAni.getSelectedItem().toString().equals("Select Animal")) && (expDateAnim.length()>0) && ((!spInputAni.getSelectedItem().toString().equals("Select Type of Breed")))) ) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerAni();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });
                    btnSellCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            select="sellani";
                            getDetailsFromServerAni();
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);
                            btnSellCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            linearCropBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropA.getSelectedItem().toString().trim().equals("Selected Animals")) {
                                linearCropSell.setVisibility(View.GONE);
                                linearAnimSell.setVisibility(View.VISIBLE);
                                txtSellAni.setText("Sell::" + spCropA.getSelectedItem().toString());
                                btnSellPostAni.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (quantityAniSell.getText().toString().trim().equals("")) {
                                            quantityAniSell.setError("Quantity is required!");
                                            quantityAniSell.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerCateg(spCategAniSell)) {

                                        }
                                        if (!isspinnerInput(spInputAniSell)) {

                                        }

                                        if (txtPriceAniSell.getText().toString().trim().equals("")) {
                                            txtPriceAniSell.setError("Ask Price is required!");
                                            txtPriceAniSell.setHint("please enter Ask Price");
                                        }
                                        if ((quantityAniSell.length() > 0) && (txtPriceAniSell.length() > 0) && ((!spCategAniSell.getSelectedItem().toString().equals("Select Category")) && ((!spInputAniSell.getSelectedItem().toString().equals("Select Input"))))) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerAni();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                            }
                        }

                    });
                    btnStorageCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);

                            btnStorageCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            select="animstore";
                            linearCropBuy.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);

                            linearCropStorage.setVisibility(View.GONE);
                            if(!spCropA.getSelectedItem().toString().trim().equals("Selected Animals")) {
                                linearCropStorage.setVisibility(View.VISIBLE);
                                txtStorageCB.setText("Storage::" + spCropA.getSelectedItem().toString());
                                getDetailsFromServerStore();
                            }
                        }

                    });
                    btnClinicCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);

                            btnClinicCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            linearCropBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            select="ani";
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropA.getSelectedItem().toString().trim().equals("Selected Animals")) {
                                linearCropClinic.setVisibility(View.VISIBLE);
                                txtClinicCB.setText("Plant Clinic::" + spCropA.getSelectedItem().toString());
                                btnBrowse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        img_store = null;
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                                    }

                                });
                                btnClinicPost.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendDetailsToServerClinic();

                                    }
                                });
                            }

                        }
                    });

                }
                else
                {
                    image3.setColorFilter(Color.BLACK);
                    text3.setTextColor(Color.BLACK);
                    iscolora = true;
                }
            }
        });
        linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spCropD.setVisibility(View.VISIBLE);
                spCrop.setVisibility(View.GONE);
                spCropA.setVisibility(View.GONE);
                spCropV.setVisibility(View.GONE);
                getDetailsFromServerLog();
                if(iscolord)
                {
                    image4.setColorFilter(getResources().getColor(R.color.blue));
                    image1.setColorFilter(Color.BLACK);
                    image2.setColorFilter(Color.BLACK);
                    image3.setColorFilter(Color.BLACK);
                    text4.setTextColor(getResources().getColor(R.color.blue));
                    text1.setTextColor(Color.BLACK);
                    text2.setTextColor(Color.BLACK);
                    text3.setTextColor(Color.BLACK);
                    linearCrops.setVisibility(View.GONE);
                    linearCropBuy.setVisibility(View.GONE);
                    linearCropSell.setVisibility(View.GONE);
                    linearCropStorage.setVisibility(View.GONE);
                    linearCropClinic.setVisibility(View.GONE);
                    linearAnimBuy.setVisibility(View.GONE);
                    linearAnimSell.setVisibility(View.GONE);
                    linearDairyBuy.setVisibility(View.GONE);
                    linearDairySell.setVisibility(View.GONE);


                    // iscolord = false;
                    linearCrops.setVisibility(View.VISIBLE);

                    spCropD.setAdapter(customSpinnerAdapterD);
                    select="dairy";
                    spCropD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            String item = parent.getItemAtPosition(position).toString();

                            if(item.equals("Selected Dairy")){
                                // Toast.makeText(getActivity().getApplicationContext(), "Please Select Crops", Toast.LENGTH_SHORT).show();
                                btnSellCB.setBackgroundResource(R.color.red);
                                btnStorageCB.setBackgroundResource(R.color.red);
                                btnClinicCB.setBackgroundResource(R.color.red);
                                btnBuyCB.setBackgroundResource(R.color.red);
                                linearDairyBuy.setVisibility(View.GONE);
                                linearCropBuy.setVisibility(View.GONE);
                                linearCropSell.setVisibility(View.GONE);
                                linearCropStorage.setVisibility(View.GONE);
                                linearCropClinic.setVisibility(View.GONE);
                                linearAnimBuy.setVisibility(View.GONE);
                                linearAnimSell.setVisibility(View.GONE);
                                linearDairyBuy.setVisibility(View.GONE);
                                linearDairySell.setVisibility(View.GONE);


                            }
                            else
                            {
                                if(2131099681==R.color.blue)
                                {

                                    txtDairy.setText("Buy::"+item);
                                    txtDairySell.setText("Sell::"+item);
                                    txtStorageCB.setText("Storage::"+item);
                                    txtClinicCB.setText("Plant Clinic::"+item);

                                }
                            }

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    getDetailsFromServerDai();
                    expDateDairy.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            int inType = expDateDairy.getInputType(); // backup the input type
                            expDateDairy.setInputType(InputType.TYPE_NULL); // disable soft input
                            expDateDairy.onTouchEvent(event); // call native handler
                            expDateDairy.setInputType(inType); // restore input type
                            expDateDairy.requestFocus();
                            return true; // consume touch even
                        }
                    });
                    expDateDairy.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        public boolean onLongClick(View v) {
                            int inType = expDateDairy.getInputType(); // backup the input type
                            expDateDairy.setInputType(InputType.TYPE_NULL); // disable soft input
                            //  dobf.onTouchEvent(event); // call native handler
                            expDateDairy.setInputType(inType); // restore input type
                            expDateDairy.requestFocus();
                            return true; // consume touch even
                        }
                    });
                    expDateDairy.setOnClickListener(new View.OnClickListener() {
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
                                            expDateDairy.setText(date_pick_res);

                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.setTitle("Please select date");
                            // TODO Hide Future Date Here
                            // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                            datePickerDialog.show();

                        }
                    });

                    btnBuyCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);

                            linearDairySell.setVisibility(View.GONE);

                            linearCropSell.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            select="buydai";
                            if(!spCropD.getSelectedItem().toString().trim().equals("Selected Dairy")) {
                                linearCropBuy.setVisibility(View.GONE);
                                linearDairyBuy.setVisibility(View.VISIBLE);
                                txtDairy.setText("Buy::" + spCropD.getSelectedItem().toString());
                                btnBuyPostDai.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!isspinnerCategD(spCategDai)) {

                                        }

                                        if (quantityDai.getText().toString().trim().equals("")) {
                                            quantityDai.setError("Quantity is required!");
                                            quantityDai.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerUnit(spBunchesDai)) {

                                        }

                                        if (txtAskpriceDai.getText().toString().trim().equals("")) {
                                            txtAskpriceDai.setError("Ask Price is required!");
                                            txtAskpriceDai.setHint("please enter Ask Price");
                                        }
                                        if (expDateDairy.getText().toString().trim().equals("")) {
                                            expDateDairy.setError("Expected Date is required!");
                                            expDateDairy.setHint("please enter Expected Date");
                                        }
                                        if ((quantityDai.length() > 0) && (txtAskpriceDai.length() > 0) && ((!spCategDai.getSelectedItem().toString().equals("Select Dairy")) && (expDateDairy.length()>0) &&((!spBunchesDai.getSelectedItem().toString().equals("Unit"))) )) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerDai();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });
                    btnSellCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);

                            btnSellCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            select="selldai";
                            linearCropBuy.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropD.getSelectedItem().toString().trim().equals("Selected Dairy")) {
                                linearCropSell.setVisibility(View.GONE);
                                linearDairySell.setVisibility(View.VISIBLE);
                                txtDairySell.setText("Sell::" + spCropD.getSelectedItem().toString());
                                btnSellPostDai.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!isspinnerCateg(spCategDaiSell)) {

                                        }

                                        if (quantityDaiSell.getText().toString().trim().equals("")) {
                                            quantityDaiSell.setError("Quantity is required!");
                                            quantityDaiSell.setHint("please enter Quantity");
                                        }

                                        if (!isspinnerUnit(spBunchesDaiSell)) {

                                        }

                                        if (txtAskpriceDaiSell.getText().toString().trim().equals("")) {
                                            txtAskpriceDaiSell.setError("Ask Price is required!");
                                            txtAskpriceDaiSell.setHint("please enter Ask Price");
                                        }

                                        if ((quantityDaiSell.length() > 0) && (txtAskpriceDaiSell.length() > 0) && ((!spCategDaiSell.getSelectedItem().toString().equals("Select Category")) &&((!spBunchesDaiSell.getSelectedItem().toString().equals("Unit"))) )) {

                                            // Toast.makeText(getActivity().getApplicationContext(), "valid  Details", Toast.LENGTH_SHORT).show();
                                            sendDetailsToServerDai();
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid  Details", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });
                    btnStorageCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);
                            btnClinicCB.setBackgroundResource(R.color.red);

                            btnStorageCB.setBackgroundResource(R.color.blue);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);
                            select="dairystore";
                            linearCropBuy.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearCropClinic.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            if(!spCropD.getSelectedItem().toString().trim().equals("Selected Dairy")) {
                                linearCropStorage.setVisibility(View.VISIBLE);
                                txtStorageCB.setText("Storage::" + spCropD.getSelectedItem().toString());
                                getDetailsFromServerStore();
                            }
                        }

                    });
                    btnClinicCB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnSellCB.setBackgroundResource(R.color.red);
                            btnStorageCB.setBackgroundResource(R.color.red);
                            btnBuyCB.setBackgroundResource(R.color.red);

                            btnClinicCB.setBackgroundResource(R.color.blue);
                            linearCropBuy.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearCropStorage.setVisibility(View.GONE);
                            linearCropSell.setVisibility(View.GONE);
                            linearAnimBuy.setVisibility(View.GONE);
                            linearAnimSell.setVisibility(View.GONE);
                            linearDairyBuy.setVisibility(View.GONE);
                            linearDairySell.setVisibility(View.GONE);

                            select="dairy";
                            linearCropClinic.setVisibility(View.GONE);
                            if(!spCropD.getSelectedItem().toString().trim().equals("Selected Dairy")) {
                                linearCropClinic.setVisibility(View.VISIBLE);
                                txtClinicCB.setText("Plant Clinic::" + spCropD.getSelectedItem().toString());
                                btnBrowse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        img_store = null;
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                                    }

                                });
                                btnClinicPost.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sendDetailsToServerClinic();

                                    }
                                });
                            }

                        }
                    });

                }
                else
                {
                    image3.setColorFilter(Color.BLACK);
                    text3.setTextColor(Color.BLACK);
                    iscolord = true;
                }
            }
        });
        linearCropMarket.setVisibility(View.VISIBLE);
        getDetailsFromServerCrop();
        comm=(TextView)v.findViewById(R.id.commodities);
        comm.setTextColor(Color.BLUE);
        comm.setTextSize(15);


        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.setTextColor(Color.BLUE);
                comm.setTextSize(15);
                vege.setTextColor(Color.BLACK);
                vege.setTextSize(10);
                anim.setTextColor(Color.BLACK);
                anim.setTextSize(10);
                dairy.setTextColor(Color.BLACK);
                dairy.setTextSize(10);
                linearVegMarket.setVisibility(View.GONE);
                linearCropMarket.setVisibility(View.VISIBLE);
                getDetailsFromServerCrop();

            }
        });
        vege=(TextView)v.findViewById(R.id.vegetables);
        vege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.setTextColor(Color.BLACK);
                comm.setTextSize(10);
                vege.setTextColor(Color.BLUE);
                vege.setTextSize(15);
                anim.setTextColor(Color.BLACK);
                anim.setTextSize(10);
                dairy.setTextColor(Color.BLACK);
                dairy.setTextSize(10);
                linearCropMarket.setVisibility(View.GONE);
                linearVegMarket.setVisibility(View.VISIBLE);
                getDetailsFromServerVeg();

            }
        });
        anim=(TextView)v.findViewById(R.id.animalHus);
        anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.setTextColor(Color.BLACK);
                comm.setTextSize(10);
                vege.setTextColor(Color.BLACK);
                vege.setTextSize(10);
                anim.setTextColor(Color.BLUE);
                anim.setTextSize(15);
                dairy.setTextColor(Color.BLACK);
                dairy.setTextSize(10);
                linearVegMarket.setVisibility(View.GONE);
                linearCropMarket.setVisibility(View.GONE);
            }
        });
        dairy=(TextView)v.findViewById(R.id.dairyProd);
        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.setTextColor(Color.BLACK);
                comm.setTextSize(10);
                vege.setTextColor(Color.BLACK);
                vege.setTextSize(10);
                anim.setTextColor(Color.BLACK);
                anim.setTextSize(10);
                dairy.setTextColor(Color.BLUE);
                dairy.setTextSize(15);
                linearVegMarket.setVisibility(View.GONE);
                linearCropMarket.setVisibility(View.GONE);
            }
        });

        return v;

    }

    private void getDetailsFromServerStore() {
        // Instantiate the RequestQueue.
        try {
            if( select.equals("cropstore")){
                strStore="commoditiesStorage";
            }
            else  if( select.equals("vegstore")){
                strStore="vegetablesStorage";
            }
            else  if( select.equals("animstore")){
                strStore="animalstorage";
            }
            else if( select.equals("dairystore")){
                strStore="dairystorage";

            }
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_name", username);

            final String mRequestBody = jsonBody.toString();
            Log.e("url", " url:::" + config.get_url()+strStore+" "+username);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url()+strStore, new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("vegetable", " vegetable" + response.toString());
                    mCategoryLists = new ArrayList<HashMap<String, String>>();


                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray(strStore);

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject obj = jsonarray.getJSONObject(i);

                            hm = new HashMap<String, String>();
                            hm.put("cname", obj.getString("companyName"));
                            hm.put("address", obj.getString("address"));
                            hm.put("mobile", obj.getString("mobile"));
                            hm.put("scapacity", obj.getString("storageCapacity"));
                            hm.put("distance", obj.getString("distance"));
                            mCategoryLists.add(hm);
                        }
                        // int totalHeight = 0;
                        CustomAdapterStore ca=new CustomAdapterStore(mCategoryLists);
                       /* for (int i = 0; i < ca.getCount(); i++) {
                            View listItem = ca.getView(i, null, listview);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }

                        ViewGroup.LayoutParams params = listview.getLayoutParams();
                        params.height = totalHeight + (listview.getDividerHeight() * (ca.getCount() - 1));
                        listview.setLayoutParams(params);
                        listview.requestLayout();*/

                        listStore.setAdapter(ca);


                    } catch (JSONException e) {
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not get Data from Online Server"  , Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(stringRequest);
        }catch(JSONException e){}
    }



    public class CustomAdapterStore extends BaseAdapter {

        private LayoutInflater inflater=null;
        ArrayList<HashMap<String, String>> alData1;
        public CustomAdapterStore(ArrayList<HashMap<String, String>> al ) {
            alData1=al;
            inflater = ( LayoutInflater )getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return alData1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView = inflater.inflate(R.layout.crop_store, null);
            }
            double dis=Double.parseDouble(hm.get("distance"));
            if(dis<20) {
                final HashMap<String, String> hm = alData1.get(position);
                TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
                txtName.setText(hm.get("cname"));
                TextView txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
                txtAddress.setText(hm.get("address"));
                TextView txtContact = (TextView) convertView.findViewById(R.id.txtContact);
                txtContact.setText(hm.get("mobile"));
                TextView txtCapacity = (TextView) convertView.findViewById(R.id.txtCapacity);
                txtCapacity.setText(hm.get("scapacity"));
                TextView txtSUnit = (TextView) convertView.findViewById(R.id.txtSUnit);
                txtSUnit.setText("");
                TextView txtDistance = (TextView) convertView.findViewById(R.id.txtDistance);
                txtDistance.setText(hm.get("distance"));
            }
            return convertView;

        }
    }



    private void getDetailsFromServerCrop() {
        // Instantiate the RequestQueue.
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_name", username);
            jsonBody.put("password", pwd);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url()+"userloggedcheck", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());
                    mCategoryLists = new ArrayList<HashMap<String, String>>();

                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("commoditiespriceslist");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject obj = jsonarray.getJSONObject(i);

                            hm = new HashMap<String, String>();
                            hm.put("market", obj.getString("Market"));
                            hm.put("commodity", obj.getString("Commodity"));
                            hm.put("variety", obj.getString("Variety"));
                            hm.put("unit", obj.getString("Unit_of_price"));
                            hm.put("max", obj.getString("Max_price"));
                            hm.put("modal", obj.getString("Modal_price"));
                            // if (!json.isNull("comment"))
                            hm.put("date", obj.getString("Date"));

                            // else
                            //     hm.put("comment", "");
                            hm.put("id", obj.getString("Id"));
                            mCategoryLists.add(hm);
                        }
                        // int totalHeight = 0;
                        CustomAdapterCrop ca=new CustomAdapterCrop(mCategoryLists);
                       /* for (int i = 0; i < ca.getCount(); i++) {
                            View listItem = ca.getView(i, null, listview);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }

                        ViewGroup.LayoutParams params = listview.getLayoutParams();
                        params.height = totalHeight + (listview.getDividerHeight() * (ca.getCount() - 1));
                        listview.setLayoutParams(params);
                        listview.requestLayout();*/

                        listview.setAdapter(ca);


                    } catch (JSONException e) {
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not get Data from Online Server"  , Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(stringRequest);
        }catch(JSONException e){}
    }

    public class CustomAdapterCrop extends BaseAdapter {

        private LayoutInflater inflater=null;
        ArrayList<HashMap<String, String>> alData1;
        public CustomAdapterCrop(ArrayList<HashMap<String, String>> al ) {
            alData1=al;
            inflater = ( LayoutInflater )getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return alData1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView = inflater.inflate(R.layout.crop_market, null);
            }
            final HashMap<String, String> hm=alData1.get(position);
            TextView txtMarket=(TextView) convertView.findViewById(R.id.txtMarket);
            txtMarket.setText(hm.get("market"));
            TextView txtCommodity=(TextView) convertView.findViewById(R.id.txtCommodity);
            txtCommodity.setText(hm.get("commodity"));
            TextView txtVariety=(TextView) convertView.findViewById(R.id.txtVariety);
            txtVariety.setText(hm.get("variety"));
            TextView txtUnitPrice=(TextView) convertView.findViewById(R.id.txtUnitPrice);
            txtUnitPrice.setText(hm.get("unit"));
            TextView txtYardMax=(TextView) convertView.findViewById(R.id.txtYardMax);
            txtYardMax.setText(hm.get("max"));
            TextView txtModalPrice=(TextView) convertView.findViewById(R.id.txtModalPrice);
            txtModalPrice.setText(hm.get("modal"));
            TextView txtMxpp=(TextView) convertView.findViewById(R.id.txtMxpp);
            txtMxpp.setText(hm.get(""));
            TextView txtMsp=(TextView) convertView.findViewById(R.id.txtMsp);
            txtMsp.setText(hm.get(""));
            TextView txtDate=(TextView) convertView.findViewById(R.id.txtDate);
            txtDate.setText(hm.get("date"));

            return convertView;

        }
    }

    private void getDetailsFromServerVeg() {
        // Instantiate the RequestQueue.
        try {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_name", username);
            jsonBody.put("password", pwd);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url()+"userloggedcheck", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());
                    mCategoryListsveg = new ArrayList<HashMap<String, String>>();

                    try {
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("vegitablespriceslist");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject obj = jsonarray.getJSONObject(i);

                            hm = new HashMap<String, String>();
                            hm.put("market", obj.getString("Market"));
                            hm.put("veg", obj.getString("Vegetables"));
                            hm.put("local", obj.getString("Local_rate"));
                            // hm.put("unit", obj.getString("Unit_of_price"));
                            hm.put("date", obj.getString("Date"));
                            hm.put("id", obj.getString("Id"));
                            mCategoryListsveg.add(hm);
                        }
                        // int totalHeight = 0;
                        CustomAdapterVeg ca=new CustomAdapterVeg(mCategoryListsveg);
                       /* for (int i = 0; i < ca.getCount(); i++) {
                            View listItem = ca.getView(i, null, listview);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }

                        ViewGroup.LayoutParams params = listview.getLayoutParams();
                        params.height = totalHeight + (listview.getDividerHeight() * (ca.getCount() - 1));
                        listview.setLayoutParams(params);
                        listview.requestLayout();*/

                        listveg.setAdapter(ca);


                    } catch (JSONException e) {
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not get Data from Online Server"  , Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(stringRequest);
        }catch(JSONException e){}
    }

    public class CustomAdapterVeg extends BaseAdapter {

        private LayoutInflater inflater=null;
        ArrayList<HashMap<String, String>> alData1;
        public CustomAdapterVeg(ArrayList<HashMap<String, String>> al ) {
            alData1=al;
            inflater = ( LayoutInflater )getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return alData1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView = inflater.inflate(R.layout.veg_market, null);
            }
            final HashMap<String, String> hm=alData1.get(position);
            TextView txtMarket=(TextView) convertView.findViewById(R.id.txtMarket);
            txtMarket.setText(hm.get("market"));
            TextView txtVegetable=(TextView) convertView.findViewById(R.id.txtVegetable);
            txtVegetable.setText(hm.get("veg"));
            TextView txtLocal=(TextView) convertView.findViewById(R.id.txtLocal);
            txtLocal.setText(hm.get("local"));
            TextView txtPrice=(TextView) convertView.findViewById(R.id.txtPrice);
            txtPrice.setText("----");
            TextView txtDate=(TextView) convertView.findViewById(R.id.txtDate);
            txtDate.setText(hm.get("date"));

            return convertView;

        }
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
    public void onBackPressed() {
        Intent intent=new Intent(getActivity(),DashboardActivity.class);
        startActivity(intent);

//        transaction=getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
//        transaction.addToBackStack(null);
//        transaction.commit();

    }
    private void getDetailsFromServer() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"buy",new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Log1"," response"+response);
                //  ArrayList<HashMap<String, String>> mCategoryLists=new ArrayList<HashMap<String,String>>();
                // HashMap<String,String> hm;
                try{
                    JSONObject json=new JSONObject(response.toString());
                    JSONArray jsonarrayu = json.getJSONArray("units");

                    List<String> unit1 = new ArrayList<String>();
                    List<String> unit3 = new ArrayList<String>();
                    unit1.add("Unit");
                    unit3.add("Unit");
                    for(int i=0; i<jsonarrayu.length(); i++) {
                        JSONObject obju = jsonarrayu.getJSONObject(i);
                        if(obju.getString("Category").equals("1"))
                            unit1.add(obju.getString("Unit"));
                        if(obju.getString("Category").equals("3"))
                            unit3.add(obju.getString("Unit"));

                    }
                    CustomSpinnerAdapter customSpinnerAdapter1 = new CustomSpinnerAdapter(getActivity(), unit1);
                    spBunchesCB.setAdapter(customSpinnerAdapter1);
                    spBunchesCS.setAdapter(customSpinnerAdapter1);
                    CustomSpinnerAdapter customSpinnerAdapter2 = new CustomSpinnerAdapter(getActivity(), unit3);
                    spAcresCB.setAdapter(customSpinnerAdapter2);


                    JSONArray jsonarray = json.getJSONArray("buylist");

                    List<String> category = new ArrayList<String>();
                    inputA=new ArrayList<HashMap<String, String>>();
                    for(int i=0; i<jsonarray.length(); i++) {
                        JSONObject obj = jsonarray.getJSONObject(i);
                        category.add(obj.getString("Category"));

                        hm=new HashMap<String, String>();
                        hm.put("category",obj.getString("Category"));
                        hm.put("input", obj.getString("Input"));
                        inputA.add(hm);

                    }
                    Set<String> uniqueList;
                    uniqueList= new HashSet<String>(category);
                    category.clear();
                    category.add("Select Category");
                    category.addAll(uniqueList);
                    String myString = "Select Category";
                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), category);
                    spCategCB.setAdapter(customSpinnerAdapter);
                    int sp_position = customSpinnerAdapter.getPosition(myString);
                    spCategCB.setSelection(sp_position);
                    spCategCB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            com=new ArrayList<String>();
                            String item = parent.getItemAtPosition(position).toString();

                            if(item.equals("Select Category")){
                                com.add(0,"Select Input");
                                // Toast.makeText(getActivity().getApplicationContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                spInputCB.setSelection(position);
                                int j=1;

                                com.add(0,"Select Input");
                                for (int i=0;i<inputA.size();i++){

                                    hm=inputA.get(i);
                                    if(hm.get("category").equals(spCategCB.getSelectedItem().toString()))
                                    {
                                        //  Toast.makeText(getActivity().getApplicationContext(),"i:"+i+hm.get("commodity"),Toast.LENGTH_LONG).show();
                                        com.add(j, hm.get("input"));
                                        j++;
                                    }
                                }

                            }
                            CustomSpinnerAdapter customSpinnerAdapter3 = new CustomSpinnerAdapter(getActivity(), com);
                            spInputCB.setAdapter(customSpinnerAdapter3);


                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });





                }catch(JSONException e){}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        queue.add(stringRequest);
    }


    private void getDetailsFromServerAni() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"animalbuy",new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Log1"," response"+response);
                //  ArrayList<HashMap<String, String>> mCategoryLists=new ArrayList<HashMap<String,String>>();
                // HashMap<String,String> hm;
                try{
                    JSONObject json=new JSONObject(response.toString());
                    JSONArray jsonarray = json.getJSONArray("animalbuylist");

                    List<String> category = new ArrayList<String>();
                    inputA=new ArrayList<HashMap<String, String>>();
                    for(int i=0; i<jsonarray.length(); i++) {
                        JSONObject obj = jsonarray.getJSONObject(i);
                        category.add(obj.getString("Animal"));

                        hm=new HashMap<String, String>();
                        hm.put("category",obj.getString("Animal"));
                        hm.put("input", obj.getString("Breed"));
                        inputA.add(hm);

                    }
                    Set<String> uniqueList;
                    uniqueList= new HashSet<String>(category);
                    category.clear();
                    category.add("Select Animal");
                    category.addAll(uniqueList);
                    String myString = "Select Animal";
                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), category);
                    if(select.equals("buyani")) {
                        spCategAni.setAdapter(customSpinnerAdapter);
                        int sp_position = customSpinnerAdapter.getPosition(myString);
                        spCategAni.setSelection(sp_position);
                        spCategAni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                com=new ArrayList<String>();
                                String item = parent.getItemAtPosition(position).toString();

                                if(item.equals("Select Animal")){
                                    com.add(0," Select Type of Breed");
                                    // Toast.makeText(getActivity().getApplicationContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    spInputAni.setSelection(position);
                                    int j=1;

                                    com.add(0," Select Type of Breed");
                                    for (int i=0;i<inputA.size();i++){

                                        hm=inputA.get(i);
                                        if(hm.get("category").equals(spCategAni.getSelectedItem().toString()))
                                        {
                                            //  Toast.makeText(getActivity().getApplicationContext(),"i:"+i+hm.get("commodity"),Toast.LENGTH_LONG).show();
                                            com.add(j, hm.get("input"));
                                            j++;
                                        }
                                    }

                                }
                                CustomSpinnerAdapter customSpinnerAdapter3 = new CustomSpinnerAdapter(getActivity(), com);
                                spInputAni.setAdapter(customSpinnerAdapter3);


                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }
                    else {
                        spCategAniSell.setAdapter(customSpinnerAdapter);
                        int sp_position = customSpinnerAdapter.getPosition(myString);
                        spCategAniSell.setSelection(sp_position);
                        spCategAniSell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                com=new ArrayList<String>();
                                String item = parent.getItemAtPosition(position).toString();

                                if(item.equals("Select Animal")){
                                    com.add(0," Select Type of Breed");
                                    // Toast.makeText(getActivity().getApplicationContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    spInputAniSell.setSelection(position);
                                    int j=1;

                                    com.add(0," Select Type of Breed");
                                    for (int i=0;i<inputA.size();i++){

                                        hm=inputA.get(i);
                                        if(hm.get("category").equals(spCategAniSell.getSelectedItem().toString()))
                                        {
                                            //  Toast.makeText(getActivity().getApplicationContext(),"i:"+i+hm.get("commodity"),Toast.LENGTH_LONG).show();
                                            com.add(j, hm.get("input"));
                                            j++;
                                        }
                                    }

                                }
                                CustomSpinnerAdapter customSpinnerAdapter3 = new CustomSpinnerAdapter(getActivity(), com);
                                spInputAniSell.setAdapter(customSpinnerAdapter3);


                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }






                }catch(JSONException e){}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        queue.add(stringRequest);
    }
    private void getDetailsFromServerDai() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Map<String, String> postParam= new HashMap<String, String>();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"dairyproductsbuy",new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Log1"," response"+response);
                //  ArrayList<HashMap<String, String>> mCategoryLists=new ArrayList<HashMap<String,String>>();
                // HashMap<String,String> hm;
                try{
                    JSONObject json=new JSONObject(response.toString());
                    JSONArray jsonarrayu = json.getJSONArray("units");

                    List<String> unit1 = new ArrayList<String>();
                    unit1.add("Unit");

                    for(int i=0; i<jsonarrayu.length(); i++) {
                        JSONObject obju = jsonarrayu.getJSONObject(i);
                        if(obju.getString("Category").equals("2"))
                            unit1.add(obju.getString("Unit"));

                    }
                    CustomSpinnerAdapter customSpinnerAdapter1 = new CustomSpinnerAdapter(getActivity(), unit1);
                    spBunchesDai.setAdapter(customSpinnerAdapter1);
                    spBunchesDaiSell.setAdapter(customSpinnerAdapter1);


                    JSONArray jsonarray = json.getJSONArray("dairyproductsbuylist");

                    List<String> category = new ArrayList<String>();
                    category.add("Select Dairy");
                    for(int i=0; i<jsonarray.length(); i++) {
                        JSONObject obj = jsonarray.getJSONObject(i);
                        category.add(obj.getString("Product_name"));

                    }
                    // Set<String> uniqueList;
                    //uniqueList= new HashSet<String>(category);
                    //category.clear();

                    //category.addAll(uniqueList);
                    String myString = "Select Dairy";
                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), category);
                    spCategDai.setAdapter(customSpinnerAdapter);
                    spCategDaiSell.setAdapter(customSpinnerAdapter);
                    int sp_position = customSpinnerAdapter.getPosition(myString);
                    spCategDai.setSelection(sp_position);
                    spCategDaiSell.setSelection(sp_position);


                }catch(JSONException e){}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        queue.add(stringRequest);
    }


    private void sendDetailsToServerCB() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String ttype="";
        if(select.equals("crop"))
        {
            ttype="crop_status_buy";
        }
        else if(select.equals("vegetable"))
        {
            ttype="vegetable_status_buy";
        }

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("cropName", spCropV.getSelectedItem().toString());
        postParam.put("tokenId", tokenid);
        postParam.put("category", spCategCB.getSelectedItem().toString());
        postParam.put("input", spInputCB.getSelectedItem().toString());
        postParam.put("variety", varietyCB.getText().toString().trim());
        postParam.put("quantity", quantityCB.getText().toString().trim());
        postParam.put("units", spBunchesCB.getSelectedItem().toString());
        postParam.put("area", areaCB.getText().toString().trim());
        postParam.put("areaUnits", spAcresCB.getSelectedItem().toString());
        postParam.put("comment", commentCB.getText().toString().trim());
        postParam.put("strEDD", expDate.getText().toString().trim());
        postParam.put("transactionType", ttype);
        Log.e("post param"," "+postParam.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"savefarmertransaction",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                Log.i("Response is:-",response.toString());
                try {
                    JSONObject json = new JSONObject(response.toString());
                    if(json.getString("status").equalsIgnoreCase("success")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your Post Successfully Sent");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(getContext(), "Your Post Successfully  Send", Toast.LENGTH_LONG).show();
                        clearBoxCB();
                    }
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
    private void sendDetailsToServerDai() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("liveStock", spCropD.getSelectedItem().toString());
        postParam.put("tokenId", tokenid);

        if(select.equals("buydai"))
        {
            postParam.put("inputs", spCategDai.getSelectedItem().toString());
            postParam.put("variety", "");
            postParam.put("quantity", quantityDai.getText().toString().trim());
            postParam.put("unit", spBunchesDai.getSelectedItem().toString());
            postParam.put("milkYield", "");
            postParam.put("price", txtAskpriceDai.getText().toString().trim());
            postParam.put("nearestMarket", txtNMarDai.getText().toString().trim());
            postParam.put("comment", txtCommentDai.getText().toString().trim());
            postParam.put("strEDD", expDateDairy.getText().toString().trim());
            postParam.put("transactionType", "dairy_status_buy");

        }
        else if(select.equals("selldai"))
        {
            postParam.put("inputs", spCategDaiSell.getSelectedItem().toString());
            postParam.put("variety", "");
            postParam.put("quantity", quantityDaiSell.getText().toString());
            postParam.put("unit", spBunchesDaiSell.getSelectedItem().toString());
            postParam.put("milkYield", "");
            postParam.put("price", txtAskpriceDaiSell.getText().toString().trim());
            postParam.put("nearestMarket", txtNMarDaiSell.getText().toString().trim());
            postParam.put("comment", txtCommentDaisell.getText().toString().trim());
            postParam.put("transactionType", "dairy_status_sell");

        }

        Log.e("post param"," "+postParam.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"savefdatransaction",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                Log.i("Response is:-",response.toString());
                try {
                    JSONObject json = new JSONObject(response.toString());
                    if(json.getString("status").equalsIgnoreCase("success")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your Post Successfully Sent");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();


                        if (select.equals("buydai"))
                            clearBoxDai();
                        else if (select.equals("selldai"))
                            clearBoxDaiSell();
                    }
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

    private void sendDetailsToServerAni() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("liveStock", spCropA.getSelectedItem().toString());
        postParam.put("tokenId", tokenid);

        if(select.equals("buyani"))
        {
            postParam.put("inputs", spCategAni.getSelectedItem().toString());
            postParam.put("variety", spInputAni.getSelectedItem().toString());
            postParam.put("quantity", quantityAni.getText().toString().trim());
            postParam.put("unit", "None");
            postParam.put("milkYield", txtMYield.getText().toString().trim());
            postParam.put("price", txtPriceAni.getText().toString().trim());
            postParam.put("nearestMarket", txtNMar.getText().toString().trim());
            postParam.put("comment", txtComment.getText().toString().trim());
            postParam.put("strEDD", expDateAnim.getText().toString().trim());
            postParam.put("transactionType", "animal_status_buy");

        }
        else if(select.equals("sellani"))
        {
            postParam.put("inputs", spCategAniSell.getSelectedItem().toString());
            postParam.put("variety", spInputAniSell.getSelectedItem().toString());
            postParam.put("quantity", quantityAniSell.getText().toString().trim());
            postParam.put("unit", "None");
            postParam.put("milkYield", txtMYieldSell.getText().toString().trim());
            postParam.put("price", txtPriceAniSell.getText().toString().trim());
            postParam.put("nearestMarket", txtNMarSell.getText().toString().trim());
            postParam.put("comment", txtCommentSell.getText().toString().trim());
            postParam.put("transactionType", "animal_status_sell");

        }

        Log.e("post param"," "+postParam.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"savefdatransaction",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                Log.i("Response is:-",response.toString());
                try {
                    JSONObject json = new JSONObject(response.toString());
                    if(json.getString("status").equalsIgnoreCase("success")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your Post Successfully Sent");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();

                        if (select.equals("buyani"))
                            clearBoxAni();
                        else if (select.equals("sellani"))
                            clearBoxAniSell();
                    }
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

    private void sendDetailsToServerCS() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String ttype="";
        if(select.equals("crop"))
        {
            ttype="crop_status_sell";
        }
        else if(select.equals("vegetable"))
        {
            ttype="vegetable_status_sell";
        }

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("category", "None");
        postParam.put("input", "None");
        postParam.put("area", "None");
        postParam.put("areaUnits", "None");
        postParam.put("cropName", spCropV.getSelectedItem().toString());
        postParam.put("tokenId", tokenid);
        postParam.put("quantity", txtQuantityCS.getText().toString().trim());
        postParam.put("units", spBunchesCS.getSelectedItem().toString());
        postParam.put("variety", txtVarietyCS.getText().toString().trim());
        postParam.put("askPrice", txtAskpriceCS.getText().toString().trim());
        postParam.put("comment", txtCommentCS.getText().toString().trim());
        postParam.put("strEDD", expDate.getText().toString().trim());
        postParam.put("transactionType", ttype);
        Log.e("post param"," "+postParam.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"savefarmertransaction",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                Log.i("Response is:-",response.toString());
                try {
                    JSONObject json = new JSONObject(response.toString());
                    if(json.getString("status").equalsIgnoreCase("success")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your Post Successfully Sent");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();

                        clearBoxCS();
                    }
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

    private void sendDetailsToServerClinic() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        encodedImage = Base64.encodeToString(img_store, Base64.DEFAULT);


        String ttype="";
        if(select.equalsIgnoreCase("crop")) {
            ttype = spCrop.getSelectedItem().toString() + "_" + "Status_Clinic";
        }

        else if(select.equalsIgnoreCase("veg")) {
            ttype = spCropV.getSelectedItem().toString() + "_" + "Status_Clinic";
        }
        else if(select.equalsIgnoreCase("ani")) {
            ttype = spCropA.getSelectedItem().toString() + "_" + "Status_Clinic";
        }
        else if(select.equalsIgnoreCase("dairy")) {
            ttype = spCropD.getSelectedItem().toString() + "_" + "Status_Clinic";
        }
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("farmerName", user);
        postParam.put("mobile", mobile);
        postParam.put("tokenId", tokenid);
        postParam.put("type", ttype);
        postParam.put("imgName", encodedImage);
        postParam.put("comment", etComment.getText().toString().trim());
        Log.i("post is:-",postParam.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"saveplantclinic",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                Log.i("Response is:-",response.toString());
                try {
                    JSONObject json = new JSONObject(response.toString());
                    if(json.getString("status").equalsIgnoreCase("success")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your Post Successfully Sent");
                        alertDialog.setIcon(R.drawable.tick);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();


                        clearBoxClinic();
                    }
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

    private void clearBoxCB()
    {
        quantityCB.setText("");
        areaCB.setText("");
        varietyCB.setText("");
        commentCB.setText("");
        expDate.setText("");
        spCategCB.setSelection(0);
        spInputCB.setSelection(0);
        spBunchesCB.setSelection(0);
        spAcresCB.setSelection(0);

    }

    private void clearBoxAni()
    {
        quantityAni.setText("");
        txtMYield.setText("");
        txtNMar.setText("");
        txtPriceAni.setText("");
        expDateAnim.setText("");
        //txtExpected.setText("");
        txtComment.setText("");
        spCategAni.setSelection(0);
        spInputAni.setSelection(0);

    }
    private void clearBoxDai()
    {
        txtAskpriceDai.setText("");
        quantityDai.setText("");
        txtNMarDai.setText("");
        txtCommentDai.setText("");
        expDateDairy.setText("");
        spCategDai.setSelection(0);
        spBunchesDai.setSelection(0);

    }

    private void clearBoxDaiSell()
    {
        txtAskpriceDaiSell.setText("");
        quantityDaiSell.setText("");
        txtNMarDaiSell.setText("");
        txtCommentDaisell.setText("");
        spCategDaiSell.setSelection(0);
        spBunchesDaiSell.setSelection(0);
    }
    private void clearBoxAniSell()
    {
        quantityAniSell.setText("");
        txtMYieldSell.setText("");
        txtNMarSell.setText("");
        txtPriceAniSell.setText("");
        txtCommentSell.setText("");
        spCategAniSell.setSelection(0);
        spInputAniSell.setSelection(0);
    }

    private void clearBoxCS()
    {
        txtCommentCS.setText("");
        txtVarietyCS.setText("");
        txtAskpriceCS.setText("");
        txtQuantityCS.setText("");
        expDate.setText("");
        spBunchesCS.setSelection(0);
    }
    private void clearBoxClinic()
    {
        etComment.setText("");
        iv_clinic.setImageResource(R.mipmap.ic_launcher);

    }


    private boolean isspinnerCateg(Spinner spn){
        if(spn.getSelectedItem().toString().equals("Select Category")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Category");
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
    private boolean isspinnerCategA(Spinner spn){
        if(spn.getSelectedItem().toString().equals("Select Animal")) {
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
    private boolean isspinnerCategD(Spinner spn){
        if(spn.getSelectedItem().toString().equals("Select Dairy")) {
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
    private boolean isspinnerInput(Spinner spn){
        if(spn.getSelectedItem().toString().equals("Select Input")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Input");
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

    private boolean isspinnerInputA(Spinner spn){
        if(spn.getSelectedItem().toString().equals("Select Type of Breed")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please Select Type of Breed");
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

    private boolean isspinnerUnit(Spinner spn){
        if(spn.getSelectedItem().toString().equals("Unit")) {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("please select Unit");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            try {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 65, stream);
                    img_store = stream.toByteArray();
                    iv_clinic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getDetailsFromServerLog() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("user_name", username);
        postParam.put("password",pwd);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"userloggedcheck",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                //    Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                if (response.toString().contains("success") && response.toString().contains("Farmer")) {
                    try {

                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("userbean");

                        if (jsonarray.length() > 0) {
                            JSONObject obj = jsonarray.getJSONObject(0);

                            prefsEditor = sharedPreferences.edit();
                            if(obj.has("status1")) {
                                if (obj.getString("status1").equals("Farmer")) {

                                    jsonarray1 = json.getJSONArray("fstatus1");
                                }
                            }
                            if(obj.has("status2")) {
                                if (obj.getString("status2").equals("Farmer")) {
                                    jsonarray1 = json.getJSONArray("fstatus2");
                                }
                            }
                            if(obj.has("status3")) {
                                if (obj.getString("status3").equals("Farmer")) {
                                    jsonarray1 = json.getJSONArray("fstatus3");
                                }
                            }
                            if(obj.has("status4")) {
                                if (obj.getString("status4").equals("Farmer")) {
                                    jsonarray1 = json.getJSONArray("fstatus4");
                                }
                            }
                            JSONObject obj1 = jsonarray1.getJSONObject(0);
                            prefsEditor.putString("fid", obj1.getString("id"));
                            prefsEditor.putString("user", (obj1.getString("firstName")+ " "+obj1.getString("lastName")));
                            for(int i=1;i<6;i++)
                            {
                                if(obj1.has("crop"+i))
                                {
                                    if(i==1) {
                                        cropstr = obj1.getString("crop"+i);
                                    }
                                    else
                                    {
                                        cropstr = cropstr+","+obj1.getString("crop"+i);
                                    }
                                }

                            }
                            String[] words1 = cropstr.split(",");
                            crop = new ArrayList<String>();
                            crop.add("Selected Crops");
                            for (String w : words1) {
                                crop.add(w);
                            }

                            select="crop";
                            customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), crop);
                            spCrop.setAdapter(customSpinnerAdapter);
                            Log.i("crop data:--", cropstr);

                            for(int i=1;i<9;i++)
                            {
                                if(obj1.has("veg"+i))
                                {
                                    if(i==1) {
                                        vegstr = obj1.getString("veg"+i);
                                    }
                                    else
                                    {
                                        vegstr = vegstr+","+obj1.getString("veg"+i);
                                    }
                                }

                            }
                            String[] words2 = vegstr.split(",");
                            veg = new ArrayList<String>();
                            veg.add("Selected Vegetables");
                            for (String w : words2) {
                                veg.add(w);
                            }

                            select="vegetable";
                            customSpinnerAdapterV = new CustomSpinnerAdapter(getActivity(), veg);
                            spCropV.setAdapter(customSpinnerAdapterV);

                            for(int i=1;i<4;i++)
                            {
                                if(obj1.has("aniHus"+i))
                                {
                                    if(i==1) {
                                        anistr = obj1.getString("aniHus"+i);
                                    }
                                    else
                                    {
                                        anistr = anistr+","+obj1.getString("aniHus"+i);
                                    }
                                }

                            }


                            String[] words3 = anistr.split(",");
                            ani = new ArrayList<String>();
                            ani.add("Selected Animals");
                            for (String w : words3) {
                                ani.add(w);
                            }

                            //select="animal";
                            customSpinnerAdapterA = new CustomSpinnerAdapter(getActivity(), ani);

                            spCropA.setAdapter(customSpinnerAdapterA);
                            for(int i=1;i<4;i++)
                            {
                                if(obj1.has("dairy"+i))
                                {
                                    if(i==1) {
                                        daistr = obj1.getString("dairy"+i);
                                    }
                                    else
                                    {
                                        daistr = daistr+","+obj1.getString("dairy"+i);
                                    }
                                }

                            }
                            String[] words4 = daistr.split(",");
                            dai = new ArrayList<String>();
                            dai.add("Selected Dairy");
                            for (String w : words4) {
                                dai.add(w);
                            }
                            customSpinnerAdapterD = new CustomSpinnerAdapter(getActivity(), dai);
                            spCropD.setAdapter(customSpinnerAdapterD);

                            prefsEditor.commit();

                        }
                    }catch (JSONException e){e.printStackTrace();}

                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Could not get data from Server", Toast.LENGTH_SHORT).show();
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
    private void loading(String l1,String l2) {
        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: "+weather_humidity);
                pressure_field.setText("Pressure: "+weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        //asyncTask.execute("16.4227", "80.5768"); //  asyncTask.execute("Latitude", "Longitude")
        asyncTask.execute(""+l1,""+l2);
    }

    private void Location() {
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);
        }
    }
    void getLocation(){
        try{
            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,5,this);

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
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
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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
        Toast.makeText(getActivity().getApplicationContext(),"Please enable gps and Internet",Toast.LENGTH_SHORT).show();

    }

    public void newfeed(){
        try {
            // Connect to the web site
            Document mBlogDocument = Jsoup.connect(url).get();
            // Using Elements to get the Meta data
            Elements mElementDataSize = mBlogDocument.select("div[class=eachStory]");
            // Locate the content attribute
            int mElementSize = mElementDataSize.size();

            for (int i = 0; i < mElementSize; i++) {
                Elements mElementAuthorName = mBlogDocument.select("h3").select("a").eq(i);
                String mAuthorName = mElementAuthorName.text();

                Elements mElementBlogUploadDate = mBlogDocument.select("time[class=date-format]").eq(i);
                String mBlogUploadDate = mElementBlogUploadDate.text();

                Elements mElementBlogTitle = mBlogDocument.select("div[class=eachStory]").select("p").eq(i);
                String mBlogTitle = mElementBlogTitle.text();
                Elements mElementBlogImage = mBlogDocument.select("img[class=lazy]").eq(i);
                // String mBlogImage = mElementBlogImage.text();
                String mBlogImage = mElementBlogImage.attr("data-original");
                // Download image from URL
                String mBlogHref = mElementAuthorName.attr("href");
                // Download image from URL
                Log.i("p",mBlogTitle);
                Log.i("image",mBlogImage);
                Log.i("anchor",mBlogHref);
                //Log.i("AuthorName",)


                mAuthorNameList.add(mAuthorName);
                mBlogUploadDateList.add(mBlogUploadDate);
                mBlogTitleList.add(mBlogTitle);
                mBlogImageList.add(mBlogImage);
                mBlogHrefList.add(mBlogHref);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataAdapter mDataAdapter = new DataAdapter(getActivity(), mBlogTitleList, mAuthorNameList, mBlogUploadDateList,mBlogImageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mDataAdapter);
        //mDataAdapter.setOnItemClickListener(getActivity());


    }


public class news extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Connect to the web site
            Document mBlogDocument = Jsoup.connect(url).get();
            // Using Elements to get the Meta data
            Elements mElementDataSize = mBlogDocument.select("div[class=eachStory]");
            // Locate the content attribute
            int mElementSize = mElementDataSize.size();

            for (int i = 0; i < mElementSize; i++) {
                Elements mElementAuthorName = mBlogDocument.select("h3").select("a").eq(i);
                String mAuthorName = mElementAuthorName.text();

                Elements mElementBlogUploadDate = mBlogDocument.select("time[class=date-format]").eq(i);
                String mBlogUploadDate = mElementBlogUploadDate.text();

                Elements mElementBlogTitle = mBlogDocument.select("div[class=eachStory]").select("p").eq(i);
                String mBlogTitle = mElementBlogTitle.text();
                Elements mElementBlogImage = mBlogDocument.select("img[class=lazy]").eq(i);
                // String mBlogImage = mElementBlogImage.text();
                String mBlogImage = mElementBlogImage.attr("data-original");
                // Download image from URL
                String mBlogHref = mElementAuthorName.attr("href");
                // Download image from URL
                Log.i("p",mBlogTitle);
                Log.i("image",mBlogImage);
                Log.i("anchor",mBlogHref);
                //Log.i("AuthorName",)


                mAuthorNameList.add(mAuthorName);
                mBlogUploadDateList.add(mBlogUploadDate);
                mBlogTitleList.add(mBlogTitle);
                mBlogImageList.add(mBlogImage);
                mBlogHrefList.add(mBlogHref);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        // Set description into TextView



        DataAdapter mDataAdapter = new DataAdapter(getActivity(), mBlogTitleList, mAuthorNameList, mBlogUploadDateList,mBlogImageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mDataAdapter);

    }
}
}