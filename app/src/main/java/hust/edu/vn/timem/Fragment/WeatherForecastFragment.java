package hust.edu.vn.timem.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hust.edu.vn.timem.Adapter.LinePagerIndicatorDecoration;
import hust.edu.vn.timem.Adapter.WeatherForecastAdapter;
import hust.edu.vn.timem.Model.WeatherModel.WeatherForecastResult;
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
public class WeatherForecastFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mServices;
    RecyclerView recycle_forecast;
    static WeatherForecastFragment instance = null;
    public static WeatherForecastFragment getInstance(){
        if(instance==null){
            instance = new WeatherForecastFragment();
        }
        return instance;
    }

    public WeatherForecastFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mServices = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);;
        recycle_forecast = v.findViewById(R.id.recycle_forecast);
        recycle_forecast.setHasFixedSize(true);
        recycle_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        getInforWeatherForecast();

        return v;
    }

    private void getInforWeatherForecast() {
        compositeDisposable.add(mServices.getForecastWeatherByLatLng(
                String.valueOf(Common.currentLocation.getLatitude()) ,
                String.valueOf(Common.currentLocation.getLongitude()),
                Common.API_KEY,
                "metric"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                               @Override
                               public void accept(WeatherForecastResult weatherForecastResult) throws Exception {

                                   display(weatherForecastResult);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                ));


}

    private void display(WeatherForecastResult weatherForecastResult) {
        WeatherForecastAdapter  adapter = new WeatherForecastAdapter(getContext(),weatherForecastResult);
        recycle_forecast.setAdapter(adapter);
        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycle_forecast);
        recycle_forecast.addItemDecoration(new LinePagerIndicatorDecoration());
    }



}
