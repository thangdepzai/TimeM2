package hust.edu.vn.timem.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hust.edu.vn.timem.Model.WeatherModel.WeatherForecastResult;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.WeatherUtil.Common.Common;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewholder> {
    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.weather_card,viewGroup, false);

        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder myViewholder, int i) {
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherForecastResult.list.get(i).weather.get(0).getIcon()).append(".png").toString())
                .into(myViewholder.img_weather);
        myViewholder.txt_city.setText(weatherForecastResult.getCity().getName());
        myViewholder.txt_day.setText(Common.convertUnixToDate(weatherForecastResult.list.get(i).getDt()));
        myViewholder.txt_tem_max.setText(weatherForecastResult.list.get(i).getMain().getTemp_max()+"°C/");
        myViewholder.txt_tem_min.setText(weatherForecastResult.list.get(i).getMain().getTemp_min()+"°C");

    }

    @Override
    public int getItemCount() {
        if(weatherForecastResult==null) return 0;
        return weatherForecastResult.list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{
        ImageView img_weather;
        TextView txt_city;
        TextView txt_tem_max;
        TextView txt_tem_min;
        TextView txt_day;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            txt_city = itemView.findViewById(R.id.txt_city);
            txt_tem_max = itemView.findViewById(R.id.txt_tem_max);
            txt_tem_min= itemView.findViewById(R.id.txt_tem_min);
            txt_day = itemView.findViewById(R.id.txt_day);
            img_weather = itemView.findViewById(R.id.img_weather);
        }
    }
}
