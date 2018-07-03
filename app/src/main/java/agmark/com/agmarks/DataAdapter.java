package agmark.com.agmarks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Created by Admin on 05-06-2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    public Context context;
    OnItemClickListener mlistener;

    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogImageList = new ArrayList<>();
    private Activity mActivity;
    private int lastPosition = -1;
    public interface OnItemClickListener{
        public void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }


    public DataAdapter(Activity activity, ArrayList<String> mBlogTitleList, ArrayList<String> mAuthorNameList, ArrayList<String> mBlogUploadDateList, ArrayList<String> mBlogImageList) {
        this.mActivity = activity;
        this.mBlogTitleList = mBlogTitleList;
        this.mAuthorNameList = mAuthorNameList;
        this.mBlogUploadDateList = mBlogUploadDateList;
        this.mBlogImageList=mBlogImageList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_blog_title, tv_blog_author, tv_blog_upload_date;
        public ImageView imgDis;


        public MyViewHolder(View view) {
            super(view);
            tv_blog_title = (TextView) view.findViewById(R.id.row_tv_blog_title);
            tv_blog_author = (TextView) view.findViewById(R.id.row_tv_blog_author);
            tv_blog_upload_date = (TextView) view.findViewById(R.id.row_tv_blog_upload_date);
            imgDis=(ImageView)view.findViewById(R.id.imgDis);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mlistener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_blog_title.setText(mAuthorNameList.get(position));
        holder.tv_blog_author.setText(mBlogTitleList.get(position));
        holder.tv_blog_upload_date.setText(mBlogUploadDateList.get(position));
        //String url = mBlogImageList.get(position).toString();
        Picasso.with(mActivity).load(""+ mBlogImageList.get(position).toString()).into(holder.imgDis);

        // Decode Bitmap

    }

    @Override
    public int getItemCount() {
        return mBlogTitleList.size();
    }



}

