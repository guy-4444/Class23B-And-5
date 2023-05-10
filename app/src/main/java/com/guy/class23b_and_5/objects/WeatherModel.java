package com.guy.class23b_and_5.objects;

public class WeatherModel {

    private MainModel main;
    private WindModel wind;
    private SunModel sys;
    private CloudsModel clouds;

    public WeatherModel() {}

    public MainModel getMain() {
        return main;
    }

    public void setMain(MainModel main) {
        this.main = main;
    }

    public WindModel getWind() {
        return wind;
    }

    public void setWind(WindModel wind) {
        this.wind = wind;
    }

    public SunModel getSys() {
        return sys;
    }

    public void setSys(SunModel sys) {
        this.sys = sys;
    }

    public CloudsModel getClouds() {
        return clouds;
    }

    public void setClouds(CloudsModel clouds) {
        this.clouds = clouds;
    }
}
