package agmark.com.agmarks;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.MissingResourceException;

public class NewFeed extends AppCompatActivity implements DataAdapter.OnItemClickListener {

    private ProgressDialog mProgressDialog;
    // private String url = "http://www.yudiz.com/blog/";
    private String url = "https://economictimes.indiatimes.com/news/economy/agriculture";
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBlogImageList = new ArrayList<>();
    private ArrayList<String> mBlogHrefList = new ArrayList<>();
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new Description().execute();

    }



    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewFeed.this);
            mProgressDialog.setTitle("News Feed ");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

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

            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);
            DataAdapter mDataAdapter = new DataAdapter(NewFeed.this, mBlogTitleList, mAuthorNameList, mBlogUploadDateList,mBlogImageList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);
            mDataAdapter.setOnItemClickListener(NewFeed.this);


            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onItemClick(int position) {
        int item = position;
        String url="https://economictimes.indiatimes.com"+mBlogHrefList.get(item).toString();
        Intent i = new Intent(getApplicationContext(),web.class);
        i.putExtra("url",url);
        startActivity(i);
    }
}


/*public class NewFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
    }
}*/
