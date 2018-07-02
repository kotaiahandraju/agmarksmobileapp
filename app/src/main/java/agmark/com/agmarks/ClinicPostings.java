package agmark.com.agmarks;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 17-06-2018.
 */

public class ClinicPostings extends RecyclerView.Adapter<ClinicPostings.ProductViewHolder> {
    Config config = new Config();
    String baseUrl, tokenid;
    HashMap<String, String> hm;
    Dialog dialog, dialog1;
    private Activity mCtx;
    FragmentTransaction transaction;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,txt_text;
    ImageView imageView;

    private ArrayList<String> productList;
    private ArrayList<HashMap<String, String>> dataList;


    public ClinicPostings(Activity mCtx, ArrayList<String> productList, ArrayList<HashMap<String, String>> dataList, String tokenid) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.dataList = dataList;
        this.tokenid= tokenid;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        baseUrl = config.get_url();
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_cards, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        holder.textViewTitle.setText(productList.get(position));
       // holder.textDate.setText(hm.get("date"));

        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(mCtx);
                dialog.setContentView(R.layout.clinic_postings);
                dialog.setTitle("Title...");
                dialog.getWindow().setLayout(650, 700);

                text1 =(TextView)dialog.findViewById(R.id.clinicT1);
                text2=(TextView)dialog.findViewById(R.id.clinicT2);
                text3=(TextView)dialog.findViewById(R.id.clinicT3);
                text4=(TextView)dialog.findViewById(R.id.clinicT4);
                text5=(TextView)dialog.findViewById(R.id.clinicT5);
                text6=(TextView)dialog.findViewById(R.id.clinicT6);
                text7=(TextView)dialog.findViewById(R.id.clinicT7);
                text8=(TextView)dialog.findViewById(R.id.clinicT8);
                text9=(TextView)dialog.findViewById(R.id.clinicT9);
                imageView=(ImageView)dialog.findViewById(R.id.clinicI);

                Button receive = (Button) dialog.findViewById(R.id.btn_received);
                receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1 = new Dialog(mCtx);
                        dialog1.setContentView(R.layout.activity_table1);
                        dialog1.getWindow().setLayout(570, 330);
                        txt_text = (TextView) dialog1.findViewById(R.id.txt_text);
                        txt_text.setText(text2.getText().toString()+", have you received your order? If yes click Confirm to continue");
                        Button confirm = (Button) dialog1.findViewById(R.id.btn_confirm);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirmDetails();
                            }
                        });
                        Button back = (Button) dialog1.findViewById(R.id.btn_back);
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                populateList(position);

                dialog.show();

            }
        });
    }

    private void confirmDetails() {
        try {
            RequestQueue queue = Volley.newRequestQueue(mCtx);
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("tokenId", tokenid);
            jsonBody.put("sno", hm.get("sno"));
            jsonBody.put("comment", hm.get("comment"));
            jsonBody.put("type", hm.get("type"));
            jsonBody.put("status", hm.get("status"));
            jsonBody.put("mobile",hm.get("mobile"));
            jsonBody.put("farmerName",hm.get("farmername"));

            Log.i("clinic---", jsonBody.toString());
            final String mRequestBody = jsonBody.toString();

            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url()+"changeclinicpostingsstatus", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());
                    //Toast.makeText(mCtx, response.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dialog1.dismiss();
                    transaction = ((AppCompatActivity)mCtx).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, new PostingsFragmentF().newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mCtx, "Could not get Data from Online Server", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(stringRequest);
        } catch (JSONException e) {
        }
    }

    private void populateList(int pos) {

        hm = dataList.get(pos);

        text1.setText("Clinic Type");
        text2.setText(hm.get("title"));
        text3.setText("Comment");
        text4.setText(hm.get("comment"));
        text5.setText("Status");
        text6.setText(hm.get("status"));
        text7.setText("Date");
        text8.setText(hm.get("date"));
        text9.setText("ClinicImage");
        try {
            byte[] imageBytes = Base64.decode(hm.get("image"), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(decodedImage);
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textDate;

        public ProductViewHolder(final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView);

        }
    }
}
