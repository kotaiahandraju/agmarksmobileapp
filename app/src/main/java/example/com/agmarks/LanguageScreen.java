package example.com.agmarks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LanguageScreen extends Activity {
    Spinner spinnerCustom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_screen);
        spinnerCustom= (Spinner)findViewById(R.id.spinnerCustom);
        initCustomSpinner();
    }

    private void initCustomSpinner() {

        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("--Select Language--");
        languages.add("English");
        languages.add("Telugu");
        languages.add("Kannada");
        languages.add("Tamil");
        languages.add("Hindi");
//        languages.add("తెలుగు");
//        languages.add("ಕನ್ನಡ");
//        languages.add("தமிழ்");
//        languages.add("हिंदी");
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(LanguageScreen.this, languages);
        spinnerCustom.setAdapter(customSpinnerAdapter);
      /*  spinnerCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("--Select Language--")){
                    //Toast.makeText(LanguageScreen.this, "select prefered Lang", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(LanguageScreen.this,DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });*/
        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("--Select Language--")){
                    //Toast.makeText(LanguageScreen.this, "select prefered Lang", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(LanguageScreen.this,DashboardActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(LanguageScreen.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(LanguageScreen.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

    }
}

