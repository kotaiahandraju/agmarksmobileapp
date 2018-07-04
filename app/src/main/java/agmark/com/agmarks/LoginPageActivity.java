package agmark.com.agmarks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class LoginPageActivity extends Fragment {
    EditText user, pswd,mobileDiaP;
    TextView forgotPin;
    CheckBox rememberMe;
    Button signup;
       AlertDialog dialog;
    Config config = new Config();
    String baseUrl,cropstr="",vegstr="",anistr="",daistr="",usname,pword;
    FragmentTransaction transaction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;
    JSONArray jsonarray1;
    public static LoginPageActivity newInstance() {
        LoginPageActivity fragment = new LoginPageActivity();
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
        v = inflater.inflate(R.layout.activity_loginpage, container, false);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        user = (EditText) v.findViewById(R.id.username);
        pswd = (EditText) v.findViewById(R.id.password);
        forgotPin=(TextView)v.findViewById(R.id.forgotPin);
        rememberMe=(CheckBox)v.findViewById(R.id.rememberMe);
        sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);

        usname=sharedPreferences.getString("uname", "");
        pword=sharedPreferences.getString("pword", "");
        if (usname != null && pword != null) {
            user.setText(usname);
            pswd.setText(pword);

        }

        baseUrl = config.get_url();
        Log.i("URL data is:--", baseUrl);
        signup=(Button)v.findViewById(R.id.btn_signIn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = user.getText().toString();
                if (!isUName(uname)) {
                    user.setError("Invalid Number.");
                    return;
                }
                String pwd = pswd.getText().toString();
                if (!isPWD(pwd)) {
                    pswd.setError("Invalid Password.");
                    return;
                }
                if (uname != null && pwd != null) {

                }
                /*Intent intent=new Intent(LoginPageActivity.this,FarmerDashboard.class);
                startActivity(intent);*/
                sendDetailsToServer();

            }
        });
        rememberMe.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          prefsEditor = sharedPreferences.edit();
                                          prefsEditor.putString("uname", user.getText().toString());
                                          prefsEditor.putString("pword", pswd.getText().toString());
                                          prefsEditor.commit();

                                      }
                                  });
                forgotPin.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
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


                                      }
                                  });

                transaction.addToBackStack(null);
        transaction.commit();
        return v;
    }

    private boolean isUName(String uname) {
        if (uname != null && uname.length() ==10) {
            return true;
        }
        return false;
    }

    private boolean isPWD(String pwd) {
        if (pwd != null && pwd.length() == 4) {
            return true;
        }
        return false;
    }
    private void sendDetailsToServer() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("user_name", user.getText().toString());
        postParam.put("password",pswd.getText().toString());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                baseUrl+"userloggedcheck",
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                //Toast.makeText(LoginPageActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                if (response.toString().contains("success") && response.toString().contains("Farmer")) {
                    try {

                        JSONObject json = new JSONObject(response.toString());
                        JSONArray jsonarray = json.getJSONArray("userbean");

                        if (jsonarray.length() > 0) {
                            JSONObject obj = jsonarray.getJSONObject(0);

                            prefsEditor = sharedPreferences.edit();
                            prefsEditor.putString("userid", obj.getString("userId"));
                            prefsEditor.putString("username", obj.getString("user_name"));
                            prefsEditor.putString("pwd", obj.getString("password"));
                            prefsEditor.putString("mobile", obj.getString("mobile"));
                            prefsEditor.putString("tokenid", obj.getString("tokenId"));
                            //prefsEditor.putString("empid", obj.getString("empId"));
                            //prefsEditor.putString("branchid", obj.getString("branchId"));
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


                            prefsEditor.commit();
                            //Toast.makeText(getActivity().getApplicationContext(), "TaskDone", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity().getApplicationContext(), FarmerDashboard.class);
                            startActivity(intent);
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Alert");
                            // Setting Dialog Message
                            alertDialog.setMessage("User does not exist");
                            // Setting Icon to Dialog
                            alertDialog.setIcon(R.drawable.tick);
                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    user.setText("");
                                    pswd.setText("");
                                    user.requestFocus();
                                }
                            });

                            alertDialog.show();


                        }
                    }catch (JSONException e){e.printStackTrace();}

            }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Your Are Not Registered", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendDetailsToServerPin() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

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
                        Toast.makeText(getContext(), "Your pin is successfully sent to mobile number", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                        Intent intent=new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);

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


}
