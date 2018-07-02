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
public class ListviewAdapter1 extends BaseAdapter {
	public ArrayList<Model1> productList;
	Activity activity;

	public ListviewAdapter1(Activity activity, ArrayList<Model1> productList) {
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
		TextView units,variety,date,modalprice,unitofprice,maxprice,minprice;
			}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_row1, null);
			holder = new ViewHolder();
			holder.units = (TextView) convertView.findViewById(R.id.units);
			holder.variety = (TextView) convertView.findViewById(R.id.variety);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.modalprice = (TextView) convertView.findViewById(R.id.modalprice);
			holder.unitofprice = (TextView) convertView.findViewById(R.id.unitofprice);
			holder.maxprice = (TextView) convertView.findViewById(R.id.maxprice);
			holder.minprice = (TextView) convertView.findViewById(R.id.minprice);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Model1 item = productList.get(position);
		holder.units.setText(item.getUnits().toString());
		holder.variety.setText(item.getVariety().toString());
		holder.date.setText(item.getDate().toString());
		holder.modalprice.setText(item.getModalprice().toString());
		holder.unitofprice.setText(item.getUnitofPrice().toString());
		holder.maxprice.setText(item.getMaxprice().toString());
		holder.minprice.setText(item.getMinprice().toString());
		return convertView;
	}
}