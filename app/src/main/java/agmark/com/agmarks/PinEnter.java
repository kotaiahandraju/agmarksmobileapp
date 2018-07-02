package agmark.com.agmarks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 28-06-2017.
 */

public class PinEnter extends Fragment {
    EditText mobileDiaP;
    LinearLayout linearPIN;
    AlertDialog dialog;
    Config config = new Config();
    String baseUrl;
    EditText etPin1, etPin2, etPin3, etPin4;
    TextView txtPin,txtForgotPin;
    SharedPreferences sharedPreferences;
    String pinver, merge1,merge2,pwd;
    SharedPreferences.Editor prefsEditor;
      Boolean pinenter;
    Drawable originalDrawable;
    ShapeDrawable shape;
    int logged;

    public static PinEnter newInstance() {
        PinEnter fragment = new PinEnter();
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
        v = inflater.inflate(R.layout.pinenter, container, false);

        baseUrl = config.get_url();
        etPin1 = (EditText)v.findViewById(R.id.etPin1);
        etPin2 = (EditText) v.findViewById(R.id.etPin2);
        etPin3 = (EditText) v.findViewById(R.id.etPin3);
        etPin4 = (EditText) v.findViewById(R.id.etPin4);

        originalDrawable = etPin1.getBackground();

        txtForgotPin= (TextView)v.findViewById(R.id.txtForgotPin);
        txtPin = (TextView) v.findViewById(R.id.txtPin);
        etPin2.setFocusable(false);
        etPin3.setFocusable(false);
        etPin4.setFocusable(false);
        etPin1.setCursorVisible(false);
        etPin2.setCursorVisible(false);
        etPin3.setCursorVisible(false);
        etPin4.setCursorVisible(false);


                sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);
        txtForgotPin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater inflater1 = getLayoutInflater();
                View alertLayout = inflater1.inflate(R.layout.custom_dialog1, null);
                final ImageView image=(ImageView)alertLayout.findViewById(R.id.close_dialog1);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                        String phone = mobileDiaP.getText().toString();
                        if (!isPWD(phone)) {
                            mobileDiaP.setError("Invalid Number");
                            return;
                        }
                        sendDetailsToServerPin();
                    }
                });

              //  pwd=sharedPreferences.getString("pwd", "");



            }
        });


        etPin1.addTextChangedListener(new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }


            public void afterTextChanged(Editable s) {
                if(s.length()==1) {


                    // Assign the created border to EditText widget
                   // etPin1.setBackgroundResource(R.drawable.logo);
                    etPin1.setBackgroundResource(R.drawable.circlep);
                    etPin2.setFocusable(true);
                    etPin2.setFocusableInTouchMode(true);
                    etPin2.requestFocus();
                }
                else if(s.length()==0) {
                    etPin1.setBackground(originalDrawable);
                    etPin1.requestFocus();
                }

                // TODO Auto-generated method stub
            }
        });
        etPin2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL && etPin2.getText().length()==0){
                  //  etPin2.setBackground(null);
                   // etPin2.setBackgroundDrawable(originalDrawable);
                    etPin1.requestFocus();
                }
                return false;
            }
        });

        etPin2.addTextChangedListener(new TextWatcher() {
            private int mPreviousLength;
            private boolean mBackSpace;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousLength = s.length();
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()==1) {


                    // Assign the created border to EditText widget
                   // etPin2.setBackgroundResource(R.drawable.logo);
                    //etPin2.setBackgroundResource(R.color.blue);
                    etPin2.setBackgroundResource(R.drawable.circlep);
                    etPin3.setFocusable(true);
                    etPin3.setFocusableInTouchMode(true);
                    etPin3.requestFocus();
                }
                else if(s.length()==0) {
                    etPin2.setBackground(originalDrawable);
                    etPin2.requestFocus();
                }
                mBackSpace = mPreviousLength > s.length();

                if (mBackSpace) {
                    etPin1.requestFocus();
                    // Toast.makeText(getApplicationContext(),"ontext changed"+s,Toast.LENGTH_LONG).show();
                    // do your stuff ...

                }
                // TODO Auto-generated method stub
            }

        });
        etPin3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL && etPin3.getText().length()==0){
                   // etPin3.setBackground(null);
                   // etPin3.setBackground(originalDrawable);
                    etPin2.requestFocus();
                }
                return false;
            }
        });

        etPin3.addTextChangedListener(new TextWatcher() {
            private int mPreviousLength;
            private boolean mBackSpace;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

              /*  if (before - count == 1)
                    {
                    etPin2.requestFocus();
                     Toast.makeText(getApplicationContext(),"ontext changed"+s+start+before+count,Toast.LENGTH_LONG).show();
                    // TODO Auto-generated method stub
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousLength = s.length();
      //          Toast.makeText(getApplicationContext(),"beforeText changed"+s+start+count+after,Toast.LENGTH_LONG).show();
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1) {


                    // Assign the created border to EditText widget
                  //  etPin3.setBackgroundResource(R.drawable.logo);
                    //etPin3.setBackgroundResource(R.color.blue);
                    etPin3.setBackgroundResource(R.drawable.circlep);
                    etPin4.setFocusable(true);
                    etPin4.setFocusableInTouchMode(true);
                    etPin4.requestFocus();
                }
                else if (s.length()==0){
                    etPin3.requestFocus();
                    etPin3.setBackground(originalDrawable);
                    etPin3.setSelection(etPin3.getText().length());
                    //etPin3.requestFocus();
                    // TODO Auto-generated method stub
                }
                mBackSpace = mPreviousLength > s.length();

                if (mBackSpace) {
                    etPin2.requestFocus();
                   // Toast.makeText(getApplicationContext(),"ontext changed"+s,Toast.LENGTH_LONG).show();
                    // do your stuff ...

                }
            }

        });
        etPin4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL ){
                   
                    etPin3.requestFocus();
                }
                return false;
            }
        });
        etPin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              //  mPreviousLength = s.length();
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                    if (s.length() == 1)

                    {
                         // Assign the created border to EditText widget
                        //etPin4.setBackgroundResource(R.drawable.logo);
                        etPin4.setBackgroundResource(R.drawable.circlep);

                        checkDetails();

                    } else if(s.length()==0) {
                        etPin4.setBackground(originalDrawable);
                        etPin3.requestFocus();
                    }

                    // TODO Auto-generated method stub
                }

        });
        linearPIN = (LinearLayout) v.findViewById(R.id.linearPIN);


            txtPin.setText("Enter PIN");
            txtForgotPin.setVisibility(View.VISIBLE);
            return v;
        }


    public void checkDetails() {

                pinver = sharedPreferences.getString("pwd", "");
        merge1 = etPin1.getText().toString().trim() + etPin2.getText().toString().trim() + etPin3.getText().toString().trim() + etPin4.getText().toString().trim();
               // txtPin.setText("Enter PIN");
                if (pinver.equals(merge1)) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), FarmerDashboard.class);
                    startActivity(intent);

                } else {
                    txtPin.setVisibility(View.VISIBLE);
                    txtPin.setTextColor(Color.RED);
                    txtPin.setText("Invalid Pin please Try Again");
                    etPin1.getText().clear();
                    etPin2.getText().clear();
                    etPin3.getText().clear();
                    etPin4.getText().clear();
                    etPin1.requestFocus();
                }

        }

    private boolean isPWD(String pwd) {
        if (pwd != null && pwd.length() > 0) {
            return true;
        }
        return false;
    }
    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("user_name", mobileDiaP.getText().toString().trim());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"forgetpassword",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                Log.i("post is:-",mobileDiaP.getText().toString().trim());
                Log.i("Response is:-",response.toString());
                try {
                    JSONObject json = new JSONObject(response.toString());
                    if(json.getString("status").equalsIgnoreCase("success")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Your Pin has Successfully Send", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                        Intent intent=new Intent(getActivity(),DashboardActivity.class);
                        startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Could not get data from Server ", Toast.LENGTH_SHORT).show();
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

}