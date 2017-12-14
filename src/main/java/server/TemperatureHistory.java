package server;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TemperatureHistory implements Serializable {

    private static final long serialVersionUID = 874984984L;

    @CsvBindByPosition(position = 0, required = true)
    @CsvDate("dd.MM.yyyy")
    private Date date;

    @CsvCustomBindByPosition(position = 1, converter = ConvertListToTemperatures.class)
    private List<String> weatherData;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(List<String> weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public String toString() {
        return "TemperatureHistory{" +
                "date=" + date +
                ", weatherData=" + weatherData +
                '}';
    }
}
