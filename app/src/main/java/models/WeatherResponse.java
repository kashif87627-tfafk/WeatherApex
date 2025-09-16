package models;

public class WeatherResponse {
    public Main main;
    public Wind wind;
    public String name;

    public class Main {
        public float temp;
        public int humidity;
    }

    public class Wind {
        public float speed;
    }
}
