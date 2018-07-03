package agmark.com.agmarks.Weather;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import agmark.com.agmarks.Adapters.DownLoadImageTask;
import agmark.com.agmarks.R;

public class weather_forecost extends RecyclerView.Adapter<weather_forecost.myViewHolder>{
    public Context context;
    public List<weather> list;


    public weather_forecost(Context context,List<weather> list){
        this.context = context;
        this.list=list;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {


        holder.detailsField.setText(list.get(position).dtt);
        holder.currentTemperatureField.setText(list.get(position).temp);
        holder.humidity_field.setText("HUmidity: "+list.get(position).humidity+"%");
        holder.pressure_field.setText("Perssure "+list.get(position).pressure+"hPa");
        holder.updatedField.setText(list.get(position).description);
        new DownLoadImageTask(holder.weatherIcon).execute("http://openweathermap.org/img/w/"+list.get(position).icon+".png");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView detailsField, currentTemperatureField, humidity_field, pressure_field,  updatedField;
        ImageView weatherIcon;
        public myViewHolder(View itemView) {
            super(itemView);
            updatedField = (TextView)itemView.findViewById(R.id.updated_field1);
            detailsField = (TextView)itemView.findViewById(R.id.details_field1);
            currentTemperatureField = (TextView)itemView.findViewById(R.id.current_temperature_field1);
            humidity_field = (TextView)itemView.findViewById(R.id.humidity_field1);
            pressure_field = (TextView)itemView.findViewById(R.id.pressure_field1);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon1);
        }
    }
}
