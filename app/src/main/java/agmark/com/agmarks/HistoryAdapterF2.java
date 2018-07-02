package agmark.com.agmarks;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 14-06-2018.
 */

public class HistoryAdapterF2 extends RecyclerView.Adapter<HistoryAdapterF2.ProductViewHolder> {
    Config config = new Config();
    String baseUrl, tokenid;
    HashMap<String, String> hm;
    Dialog dialog, dialog1;

    private Activity mCtx;
    FragmentTransaction transaction;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9;
    ImageView imageView;

    private ArrayList<String> productList;
    private ArrayList<HashMap<String, String>> dataList;
    private ArrayList<ModelF2> productList1;


    public HistoryAdapterF2(Activity mCtx, ArrayList<String> productList, ArrayList<HashMap<String, String>> dataList, String tokenid) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.dataList = dataList;
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
        holder.textViewTitle.setText(productList.get(position));
       // holder.textDate.setText(hm.get("date"));

        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(mCtx);
                dialog.setContentView(R.layout.clinic_history);
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


                Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                       /* transaction = ((AppCompatActivity) mCtx).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, new HistoryFragmentF().newInstance());
                        transaction.addToBackStack(null);
                        transaction.commit();*/
                    }
                });
                populateList(position);

                dialog.show();

            }
        });
    }

    private void populateList(int pos) {

        hm = dataList.get(pos);

        text1.setText("Clinic Type");
        text2.setText(hm.get("type"));
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
        }catch (Exception e){e.printStackTrace();}
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