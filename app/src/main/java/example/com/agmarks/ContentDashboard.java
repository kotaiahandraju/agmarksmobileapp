package example.com.agmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Home on 4/28/2018.
 */

public class ContentDashboard extends Fragment {
    FragmentTransaction transaction;
    private Fragment  mFragment;

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

        layout1=(LinearLayout)v.findViewById(R.id.linear1);
        layout2=(LinearLayout)v.findViewById(R.id.linear2);
        layout3=(LinearLayout)v.findViewById(R.id.linear3);
        layout4=(LinearLayout)v.findViewById(R.id.linear4);
        layout5=(LinearLayout)v.findViewById(R.id.linear5);
        layout6=(LinearLayout)v.findViewById(R.id.linear6);

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
          transaction.replace(R.id.frame, new LoginPageActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
      else if (view==layout2){
         // mFragment=new RegistrationActivity();
          transaction.replace(R.id.frame, new RegistrationActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
      else if (view==layout3){
          //mFragment=new WeatherActivity();
          transaction.replace(R.id.frame, new WeatherActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
      else if(view==layout4){
         // mFragment=new MarketPricesActivity();
          transaction.replace(R.id.frame, new MarketPricesActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
      }
      else if (view==layout5){
          //mFragment=new NewsFeedActivity();
          transaction.replace(R.id.frame, new NewsFeedActivity().newInstance());
          transaction.addToBackStack(null);
          transaction.commit();
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