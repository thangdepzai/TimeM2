package hust.edu.vn.timem.WeatherUtil.Retrofit;

import hust.edu.vn.timem.Model.WeatherModel.WeatherForecastResult;
import hust.edu.vn.timem.Model.WeatherModel.WeatherResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);
    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                                @Query("lon") String lng,
                                                                @Query("appid") String appid,
                                                                @Query("units") String unit);
    @GET("weather")
    Observable<WeatherResult> getWeatherByCityName (@Query("q") String cityName,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);
}
