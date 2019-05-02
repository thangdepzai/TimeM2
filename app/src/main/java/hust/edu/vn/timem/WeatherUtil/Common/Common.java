package hust.edu.vn.timem.WeatherUtil.Common;

import android.location.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by reale on 05/10/2016.
 */

public class Common {
    public static final String API_KEY = "88f432ce43a1426732637adbff1c2952";
    public static Location currentLocation = null;
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

//    @NonNull
//    public static String apiRequest(String lat, String lng){
//        StringBuilder sb = new StringBuilder(API_LINK);
//        sb.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metric",lat,lng,API_KEY));
//        return sb.toString();
//    }
//
//    public static String unixTimeStampToDateTime(double unixTimeStamp){
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        Date date = new Date();
//        date.setTime((long)unixTimeStamp*1000);
//        return dateFormat.format(date);
//    }
//
//    public static String getImage(String icon){
//        return String.format("http://openweathermap.org/img/w/%s.png",icon);
//    }
//
//    public static String getDateNow(){
//        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
//        Date date = new Date();
//        return dateFormat.format(date);
//    }

    public static String convertUnixToDate(long dt) {
        DateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
        Date date = new Date();
        date.setTime((long)dt*1000);
        return dateFormat.format(date);
    }
}
