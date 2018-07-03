package agmark.com.agmarks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import agmark.com.agmarks.WeatherActivity;

/**
 * Created by Home on 4/28/2018.
 */

public class ContentDashboard extends Fragment {
    FragmentTransaction transaction;
    private Fragment  mFragment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;
    String username,pwd;
    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6;
    public static ContentDashboard newInstance() {
        ContentDashboard fragment = new ContentDashboard();
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
        v = inflater.inflate(R.layout.content_dashboard, container, false);
        sharedPreferences = getActivity().getSharedPreferences("agmarks", Context.MODE_PRIVATE);
        username=sharedPreferences.getString("username", "");
        pwd=sharedPreferences.getString("pwd", "");
        layout1=(LinearLayout)v.findViewById(R.id.linear1);
        layout2=(LinearLayout)v.findViewById(R.id.linear2);
        layout3=(LinearLayout)v.findViewById(R.id.linear3);
        layout4=(LinearLayout)v.findViewById(R.id.linear4);
        layout5=(LinearLayout)v.findViewById(R.id.linear5);
        layout6=(LinearLayout)v.findViewById(R.id.linear6);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        ViewGroup.LayoutParams params1 = layout1.getLayoutParams();
        params1.height = height/4+10;
        //params.width = width;
        layout1.setLayoutParams(params1);
        ViewGroup.LayoutParams params2 = layout2.getLayoutParams();
        params2.height = height/4+10;
        //params.width = width;
        layout2.setLayoutParams(params2);
        ViewGroup.LayoutParams params3 = layout3.getLayoutParams();
        params3.height = height/4+10;
        //params.width = width;
        layout3.setLayoutParams(params3);
        ViewGroup.LayoutParams params4 = layout4.getLayoutParams();
        params4.height = height/4+10;
        //params.width = width;
        layout4.setLayoutParams(params4);
        ViewGroup.LayoutParams params5 = layout5.getLayoutParams();
        params5.height = height/4+10;
        //params.width = width;
        layout5.setLayoutParams(params5);
        ViewGroup.LayoutParams params6 = layout6.getLayoutParams();
        params6.height = height/4+10;
        //params.width = width;
        layout6.setLayoutParams(params6);




        Log.e("height","ht::"+width+" "+height+" "+params1.height+" "+params2.height);
        layout1.setOnClickListener(onClickListener);
        layout2.setOnClickListener(onClickListener);
        layout3.setOnClickListener(onClickListener);
        layout4.setOnClickListener(onClickListener);
        layout5.setOnClickListener(onClickListener);
        layout6.setOnClickListener(onClickListener);

        return v;
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transaction = getActivity().getSupportFragmentManager().beginTransaction();
      if (view==layout1){
        //mFragment=new LoginPageActivity();
          if (!username.isEmpty() && (!pwd.isEmpty())) {
              transaction.replace(R.id.frame, new PinEnter().newInstance());
              transaction.addToBackStack(null);
              transaction.commit();
          }
          else
          {
              prefsEditor = sharedPreferences.edit();
              prefsEditor.clear();
              prefsEditor.commit();
              transaction.replace(R.id.frame, new LoginPageActivity().newInstance());
              transaction.addToBackStack(null);
              transaction.commit();
          }
      }
      else if (view==layout2){
         // mFragment=new RegistrationActivity();
          transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
      else if (view==layout3){
          //mFragment=new WeatherActivity();
//          transaction.replace(R.id.frame, new WeatherActivity().newInstance());
//          transaction.addToBackStack(null);
//          transaction.commit();
          startActivity(new Intent(getActivity(),MainActivity.class));

      }
      else if(view==layout4){
         // mFragment=new MarketPricesActivity();
          transaction.replace(R.id.frame, new MarketPricesActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
      else if (view==layout5){
          //mFragment=new NewsFeedActivity();
          /*transaction.replace(R.id.frame, new NewsFeedActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();*/
          startActivity(new Intent(getActivity(),NewFeed.class));
      }
      else if (view==layout6){
         // mFragment=new ExpertAdviceActivity();
          transaction.replace(R.id.frame, new ExpertAdviceActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
        }

    };

}