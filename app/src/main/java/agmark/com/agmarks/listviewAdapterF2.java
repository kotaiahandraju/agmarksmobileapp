package agmark.com.agmarks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 14-06-2018.
 */

public class listviewAdapterF2 extends BaseAdapter {
    public ArrayList<ModelF2> productList;
    Activity activity;

    public listviewAdapterF2(Activity activity, ArrayList<ModelF2> productList) {
        super();
        this.activity = activity;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView rythubazar;
        TextView commodity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_rowf2, null);
            holder = new ViewHolder();
            holder.rythubazar = (TextView) convertView.findViewById(R.id.string1);
            holder.commodity = (TextView) convertView.findViewById(R.id.string2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModelF2 item = productList.get(position);
        holder.rythubazar.setText(item.getsNo().toString());
        holder.commodity.setText(item.getProduct().toString());
        return convertView;
    }
}