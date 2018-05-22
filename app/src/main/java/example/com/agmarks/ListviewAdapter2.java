package example.com.agmarks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 
 * @author anfer
 * 
 */
public class ListviewAdapter2 extends BaseAdapter {
	public ArrayList<Model2> productList;
	Activity activity;

	public ListviewAdapter2(Activity activity, ArrayList<Model2> productList) {
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
		TextView rate;
		TextView date;
		TextView id;
			}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_row2, null);
			holder = new ViewHolder();
			holder.rate = (TextView) convertView.findViewById(R.id.rate);
			holder.date = (TextView) convertView.findViewById(R.id.date1);
			holder.id = (TextView) convertView.findViewById(R.id.id);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Model2 item = productList.get(position);
		holder.rate.setText(item.getRate().toString());
		holder.date.setText(item.getDate().toString());
		holder.id.setText(item.getId().toString());
		return convertView;
	}

}