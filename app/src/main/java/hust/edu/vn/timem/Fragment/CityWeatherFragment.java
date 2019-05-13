package hust.edu.vn.timem.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

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
public class CityWeatherFragment extends Fragment {
    ImageView img_weather;
    TextView txt_city;
    TextView txt_tem_max;
    TextView txt_tem_min;
    TextView txt_day;
    List<String> lstCity;
    EditText txt_search;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mServices;
    ImageView btn_search;
    CardView card_weather;
    static CityWeatherFragment instance = null;
    public static CityWeatherFragment getInstance(){
        if(instance==null){
            instance = new CityWeatherFragment();
        }
        return instance;
    }

    public CityWeatherFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mServices = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View item = inflater.inflate(R.layout.fragment_city_weather, container, false);
        txt_search = item.findViewById(R.id.txt_search);
        btn_search = item.findViewById(R.id.btn_search);
        txt_city = item.findViewById(R.id.txt_city);
        txt_tem_max = item.findViewById(R.id.txt_tem_max);
        txt_tem_min= item.findViewById(R.id.txt_tem_min);
        txt_day = item.findViewById(R.id.txt_day);
        img_weather = item.findViewById(R.id.img_weather);
        card_weather = item.findViewById(R.id.card_weather);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city_name = txt_search.getText().toString();
                if(!city_name.equals("")){

                    getWeatherInfo(city_name);
                }else{
                    Toast.makeText(getActivity(), "City name not null", Toast.LENGTH_SHORT).show();
                }
            }
        });
       return item;
    }


    private void getWeatherInfo(String cityname) {
        compositeDisposable.add(mServices.getWeatherByCityName(
                cityname,
                Common.API_KEY,
                "metric"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {

                               @Override
                               public void accept(WeatherResult weatherResult) throws Exception {
                                   card_weather.setVisibility(View.VISIBLE);

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
                                   card_weather.setVisibility(View.GONE);
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

