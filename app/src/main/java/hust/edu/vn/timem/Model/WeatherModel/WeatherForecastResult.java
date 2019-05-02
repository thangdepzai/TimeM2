package hust.edu.vn.timem.Model.WeatherModel;

import java.util.List;

public class WeatherForecastResult {
    public String cod;
    public double message;
    public int cnt;
    public List<MyList> list;
    public City city ;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<MyList> getList() {
        return list;
    }

    public void setList(List<MyList> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
