package agmark.com.agmarks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
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
import java.util.List;

/**
 * Created by Admin on 07-06-2018.
 */

public class PostingsAdapterF extends RecyclerView.Adapter<PostingsAdapterF.ProductViewHolder> {
    Config config = new Config();
    String baseUrl,tokenid;
    HashMap<String, String> hm;
    Dialog dialog,dialog1;
    //this context we will use to inflate the layout
    private Activity mCtx;
    TextView title;
    FragmentTransaction transaction;

    //we are storing all the products in a list
    private ArrayList<String> productList,prdList;
    private ArrayList<HashMap<String,String >>dataList;
    private ArrayList<ModelF1> productList1;

    //getting the context and product list with constructor
    public PostingsAdapterF(Activity mCtx, ArrayList<String> productList, ArrayList<HashMap<String, String>> dataList, String tokenid) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.prdList=productList;
        this.dataList=dataList;
        this.tokenid=tokenid;
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
        holder.textViewTitle.setText(prdList.get(position));

        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    dialog = new Dialog(mCtx);
                    dialog.setContentView(R.layout.activity_table);
                    dialog.setTitle("Title...");
                    dialog.getWindow().setLayout(700, 600);
                    productList1 = new ArrayList<ModelF1>();
                    title=(TextView)dialog.findViewById(R.id.txt_title);
                    Button receive=(Button)dialog.findViewById(R.id.btn_received);
                    receive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1= new Dialog(mCtx);
                            dialog1.setContentView(R.layout.activity_table1);
                            dialog1.getWindow().setLayout(570, 330);
                            TextView text=(TextView)dialog1.findViewById(R.id.txt_text);
                            Button confirm=(Button)dialog1.findViewById(R.id.btn_confirm);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    confirmDetails();
                                }
                            });
                            Button back=(Button)dialog1.findViewById(R.id.btn_back);
                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });
                            dialog1.show();
                        }
                    });
                    Button cancel=(Button)dialog.findViewById(R.id.btn_cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    ListView lview = (ListView) dialog.findViewById(R.id.listview);
                    listviewAdapterF1 adapter = new listviewAdapterF1(mCtx, productList1);
                    lview.setAdapter(adapter);

                    populateList(position);

                    adapter.notifyDataSetChanged();

                    lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            String sno = ((TextView) view.findViewById(R.id.string1)).getText().toString();
                            String product = ((TextView) view.findViewById(R.id.string2)).getText().toString();
                            String category = ((TextView) view.findViewById(R.id.string3)).getText().toString();
                            String price = ((TextView) view.findViewById(R.id.string4)).getText().toString();

                        }
                    });
                    dialog.show();

            }
        });
    }

    private void confirmDetails() {
        try {
            RequestQueue queue = Volley.newRequestQueue(mCtx);
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("tokenId",tokenid);
            jsonBody.put("sno",hm.get("sno"));
            jsonBody.put("cropName",hm.get("crop"));
            jsonBody.put("category",hm.get("category"));
            jsonBody.put("input",hm.get("input"));
            jsonBody.put("variety",hm.get("variety"));
            jsonBody.put("quantity",hm.get("quantity"));
            jsonBody.put("units",hm.get("units"));
            jsonBody.put("area",hm.get("area"));
            jsonBody.put("areaUnits",hm.get("area_units"));
            jsonBody.put("comment",hm.get("comment"));
            jsonBody.put("ask_price",hm.get("askPrice"));
            jsonBody.put("transactionType",hm.get("ttype"));
            jsonBody.put("date",hm.get("Date"));
            jsonBody.put("status",hm.get("status"));
            Log.i("Log123---",jsonBody.toString());
            final String mRequestBody = jsonBody.toString();

            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                    config.get_url()+"changepostingsstatus", new JSONObject(mRequestBody), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Log1", " response" + response.toString());
//                    try {
//                        JSONObject json = new JSONObject();
//                        Toast.makeText(mCtx, json.getString("status").toString(), Toast.LENGTH_SHORT).show();
//                    }catch (JSONException e){}
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

        ModelF1 item1, item2, item3, item4, item5;
        hm =dataList.get(pos);
        title.setText(hm.get("crop")+"        "+hm.get("date"));
        item1 = new ModelF1("Crop", hm.get("crop"), "Unit", hm.get("units"));
        productList1.add(item1);

        item2 = new ModelF1("Category", hm.get("category"), "Area", hm.get("area"));
        productList1.add(item2);

        item3 = new ModelF1("Input", hm.get("input"), "Area Unit", hm.get("area_units"));
        productList1.add(item3);

        item4 = new ModelF1("Variety", hm.get("variety"), "Comment", hm.get("comment"));
        productList1.add(item4);

        item5 = new ModelF1("Quantity", hm.get("quantity"), "Status", hm.get("status"));
        productList1.add(item5);

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    prdList = productList;
                } else {
                    ArrayList<String> filteredList = new ArrayList<>();
                    for (String row : productList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.toString().toLowerCase().contains(charString.toLowerCase()) || row.toString().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    prdList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = prdList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                prdList = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    @Override
    public int getItemCount() {
        return prdList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        public ProductViewHolder(final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView);
        }
    }

}