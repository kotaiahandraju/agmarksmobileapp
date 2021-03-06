package agmark.com.agmarks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 11-06-2018.
 */

class HistoryAdapterF1 extends RecyclerView.Adapter<HistoryAdapterF1.ProductViewHolder> {
    Config config = new Config();
    String baseUrl, tokenid;
    HashMap<String, String> hm;
    Dialog dialog, dialog1;
    //this context we will use to inflate the layout
    private Activity mCtx;
    FragmentTransaction transaction;

    //we are storing all the products in a list
    private ArrayList<String> productList,prdList;
    private ArrayList<HashMap<String, String>> dataList;
    private ArrayList<ModelF1> productList1;

    //getting the context and product list with constructor
    public HistoryAdapterF1(Activity mCtx, ArrayList<String> productList, ArrayList<HashMap<String, String>> dataList) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.prdList=productList;
        this.dataList = dataList;
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
                dialog.setContentView(R.layout.activity_table2);
                dialog.setTitle("Title...");
                dialog.getWindow().setLayout(700, 550);
                productList1 = new ArrayList<ModelF1>();
                Button cancel=(Button)dialog.findViewById(R.id.btn_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        transaction = ((AppCompatActivity)mCtx).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, new HistoryFragmentF().newInstance());
                        transaction.addToBackStack(null);
                        transaction.commit();
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

    private void populateList(int pos) {

        ModelF1 item1, item2, item3, item4, item5;
        hm = dataList.get(pos);

        item1 = new ModelF1("Live Stock", hm.get("livestock"), "Milk Yield", hm.get("milkyield"));
        productList1.add(item1);

        item2 = new ModelF1("Input", hm.get("input"), "Price", hm.get("price"));
        productList1.add(item2);

        item3 = new ModelF1("Variety", hm.get("variety"), "Nearest_Market", hm.get("nearestmarket"));
        productList1.add(item3);

        item4 = new ModelF1("Quantity", hm.get("quantity"), "Comment", hm.get("comment"));
        productList1.add(item4);

        item5 = new ModelF1("Unit", hm.get("unit"), "Status", hm.get("status"));
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
