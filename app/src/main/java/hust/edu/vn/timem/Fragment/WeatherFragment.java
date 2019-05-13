package hust.edu.vn.timem.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import hust.edu.vn.timem.Model.WeatherModel.WeatherResult;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.WeatherUtil.Common.Common;
import hust.edu.vn.timem.WeatherUtil.Retrofit.IOpenWeatherMap;
import hust.edu.vn.timem.WeatherUtil.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    ImageView img_weather;
    TextView txt_city;
    TextView txt_tem_max;
    TextView txt_tem_min;
    TextView txt_day;
    ProgressBar loading;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mServices;
    static WeatherFragment instance = null;
    public static WeatherFragment getInstance(){
        if(instance==null){
            instance = new WeatherFragment();
        }
        return instance;
    }

    public WeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mServices = retrofit.create(IOpenWeatherMap.class);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View item = inflater.inflate(R.layout.weather_card, container, false);
        txt_city = item.findViewById(R.id.txt_city);
        txt_tem_max = item.findViewById(R.id.txt_tem_max);
        txt_tem_min= item.findViewById(R.id.txt_tem_min);
        txt_day = item.findViewById(R.id.txt_day);
        img_weather = item.findViewById(R.id.img_weather);
        getInfoWeather();
        return item;
    }

    private void getInfoWeather() {
        compositeDisposable.add(mServices.getWeatherByLatLng(
                String.valueOf(Common.currentLocation.getLatitude()) ,
                String.valueOf(Common.currentLocation.getLongitude()),
                Common.API_KEY,
                "metric"
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                               @Override
                               public void accept(WeatherResult weatherResult) throws Exception {
                                    Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                                            .append(weatherResult.weather.get(0).getIcon()).append(".png").toString())
                                                .into(img_weather);
                                    txt_city.setText(weatherResult.getName());
                                    txt_day.setText(Common.convertUnixToDate(weatherResult.getDt()));
                                    txt_tem_max.setText(weatherResult.getMain().getTemp_max()+"°C/");
                                    txt_tem_min.setText(weatherResult.getMain().getTemp_min()+"°C");
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }
    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

}
