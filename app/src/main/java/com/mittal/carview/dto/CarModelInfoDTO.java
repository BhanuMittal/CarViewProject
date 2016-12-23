package com.mittal.carview.dto;

/**
 * Created by Mittal on 1/12/16.
 */

public class CarModelInfoDTO {
    String carModelName="",carId="";
    int carModelId=0;

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }
}
