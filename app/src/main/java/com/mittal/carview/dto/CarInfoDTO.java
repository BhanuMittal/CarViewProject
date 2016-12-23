package com.mittal.carview.dto;

/**
 * Created by Mittal on 1/12/16.
 */

public class CarInfoDTO {

    String carName="";
    int carId=0;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
