package agmark.com.agmarks.Weather;

public class weather {

    String dt;
    String temp;
    String pressure;
    String humidity;
    String description;
    String icon;
    String dtt;



    public weather(String dtt,String dt, String temp, String pressure, String humidity, String description, String icon) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.description = description;
        this.icon = icon;
        this.dtt=dtt;
    }

}