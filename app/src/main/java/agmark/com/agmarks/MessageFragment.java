package agmark.com.agmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public class MessageFragment  extends Fragment{

    public static MessageFragment newInstance() {
    MessageFragment fragment = new MessageFragment();
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
            v = inflater.inflate(R.layout.fragment_message, container, false);
            return v;
        }

}
