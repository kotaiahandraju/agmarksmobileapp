package agmark.com.agmarks;

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
public class listviewAdapterF1 extends BaseAdapter {
	public ArrayList<ModelF1> productList;
	Activity activity;

	public listviewAdapterF1(Activity activity, ArrayList<ModelF1> productList) {
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
		TextView price;
		TextView agmarks;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_rowf1, null);
			holder = new ViewHolder();
			holder.rythubazar = (TextView) convertView.findViewById(R.id.string1);
			holder.commodity = (TextView) convertView.findViewById(R.id.string2);
			holder.price = (TextView) convertView.findViewById(R.id.string3);
			holder.agmarks = (TextView) convertView.findViewById(R.id.string4);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ModelF1 item = productList.get(position);
		holder.rythubazar.setText(item.getsNo().toString());
		holder.commodity.setText(item.getProduct().toString());
		holder.price.setText(item.getCategory().toString());
		holder.agmarks.setText(item.getPrice().toString());

		return convertView;
	}

}