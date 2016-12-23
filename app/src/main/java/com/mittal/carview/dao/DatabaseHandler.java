package com.mittal.carview.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.mittal.carview.dto.CarInfoDTO;
import com.mittal.carview.dto.CarModelInfoDTO;
import com.mittal.carview.dto.ReviewDTO;

import java.util.ArrayList;


/**
 * Created by Mittal on 1/12/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "car_review";
    public static final int DATABASE_VERSION = 1;
    public static final int PAGE_SIZE = 10;

    public static final String TABLE_NAME_CAR_NAME = "car_name";
    public static final String TABLE_NAME_CAR_MODEL = "car_model";
    public static final String TABLE_NAME_CAR_REVIEW = "car_review";
    //colum name for all  table
    private static final String ID = "id";
    private static final String CAR_NAME = "car_name";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";
    private static final String CAR_MODEL_NAME = "car_model_name";
    private static final String CAR_ID = "car_id";
    private static final String CAR_MODEL_ID = "car_model_id";
    public static final String CAR_REVIEW_MSG = "car_review_msg";
    public static final String CAR_REVIEW_HEADING = "car_review_heading";
    public static final String CAR_REVIEW_RATING = "car_review_rating";

    SQLiteDatabase db;
    String TAG = getClass().getSimpleName();
    public static SQLiteDatabase database;
    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CAR_NAME = "CREATE TABLE " + TABLE_NAME_CAR_NAME + " (" + ID + " INTEGER PRIMARY KEY," + CAR_NAME + " TEXT," + CREATED_AT + " TEXT)";
        String CREATE_TABLE_CAR_MODEL = "CREATE TABLE " + TABLE_NAME_CAR_MODEL + " (" + ID + " INTEGER PRIMARY KEY," + CAR_ID + " TEXT," + CAR_MODEL_NAME + " TEXT," + CREATED_AT + " TEXT)";
        String CREATE_TABLE_CAR_REVIEW = "CREATE TABLE " + TABLE_NAME_CAR_REVIEW + " (" + ID + " INTEGER PRIMARY KEY," + CAR_ID + " TEXT," + CAR_MODEL_ID + " TEXT," + CAR_REVIEW_MSG + " TEXT," + CAR_REVIEW_HEADING + " TEXT," + CAR_REVIEW_RATING + " TEXT,"+ CREATED_AT + " TEXT)";
        db.execSQL(CREATE_TABLE_CAR_NAME);
        db.execSQL(CREATE_TABLE_CAR_MODEL);
        db.execSQL(CREATE_TABLE_CAR_REVIEW);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertCarInfo(CarInfoDTO carInfoDTO) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAR_NAME, carInfoDTO.getCarName());
        long id = db.insert(TABLE_NAME_CAR_NAME, null, values);
        db.close();
        return id;
    }

    public long insertCarModelInfo(CarModelInfoDTO carModelInfoDTO) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAR_ID, carModelInfoDTO.getCarId());
        values.put(CAR_MODEL_NAME, carModelInfoDTO.getCarModelName());
        long id = db.insert(TABLE_NAME_CAR_MODEL, null, values);
        db.close();
        return id;
    }

    public long insertReviewInfo(ReviewDTO reviewDTO) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAR_ID, reviewDTO.getCarId());
        values.put(CAR_MODEL_ID, reviewDTO.getCarModelId());
        values.put(CAR_REVIEW_MSG, reviewDTO.getReviewMSg());
        values.put(CAR_REVIEW_HEADING, reviewDTO.getReviewHeading());
        values.put(CAR_REVIEW_RATING, reviewDTO.getRating());
        values.put(CREATED_AT, reviewDTO.getDate());
        long id = db.insert(TABLE_NAME_CAR_REVIEW, null, values);
        db.close();
        return id;
    }

    public ArrayList<CarInfoDTO> getAllCar(String carName, String carId, int page) {
        ArrayList<CarInfoDTO> carInfoDTOList = new ArrayList<CarInfoDTO>();
        int offset = (page - 1) * PAGE_SIZE;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_CAR_NAME;
        // SELECT *FROM Student LIMIT 9, 10


        if (!carName.equals("")) {
            selectQuery = selectQuery + " WHERE " + CAR_NAME + " LIKE '%" + carName + "%'";
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        } else if (!carId.equals("")) {
            //        SELECT * FROM table ORDER BY column DESC LIMIT 1;
            // selectQuery = selectQuery + " ORDER BY " + ID + " DESC";

            selectQuery = selectQuery + " WHERE " + ID + " = '" + carId + "'";
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        } else {
             selectQuery = selectQuery + " ORDER BY " + ID + " DESC" + " LIMIT " + offset +","+ PAGE_SIZE;
            //selectQuery = selectQuery + " LIMIT " + offset + 10;
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    CarInfoDTO carInfoDTO = new CarInfoDTO();
                    carInfoDTO.setCarId(cursor.getInt(cursor.getColumnIndex(ID)));
                    carInfoDTO.setCarName(cursor.getString(cursor.getColumnIndex(CAR_NAME)));
                    carInfoDTOList.add(carInfoDTO);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return carInfoDTOList;
    }


    public ArrayList<CarModelInfoDTO> getCarModel(String carId, String modelName, String modelId, int page) {
        ArrayList<CarModelInfoDTO> carModelInfoList = new ArrayList<CarModelInfoDTO>();

        int offset = (page - 1) * PAGE_SIZE;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_CAR_MODEL + " WHERE " + CAR_ID + " = '" + carId + "'";
        if (!modelName.equals("")) {
            selectQuery = selectQuery + " AND " + CAR_MODEL_NAME + " LIKE '%" + modelName + "%'";
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        } else if (!modelId.equals("")) {
            //        SELECT * FROM table ORDER BY column DESC LIMIT 1;
            selectQuery = selectQuery + " AND " + ID + " = '" + modelId + "'";
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        } else {
            selectQuery = selectQuery + " ORDER BY " + ID + " DESC" + " LIMIT " + offset +","+ PAGE_SIZE;

            // selectQuery = selectQuery +" LIMIT " + offset + 5 ;
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);
        }

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        CarModelInfoDTO carModelInfoDTO = new CarModelInfoDTO();
                        carModelInfoDTO.setCarId(cursor.getString(cursor.getColumnIndex(CAR_ID)));
                        carModelInfoDTO.setCarModelId(cursor.getInt(cursor.getColumnIndex(ID)));
                        carModelInfoDTO.setCarModelName(cursor.getString(cursor.getColumnIndex(CAR_MODEL_NAME)));
                        carModelInfoList.add(carModelInfoDTO);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return carModelInfoList;
    }


    public ArrayList<ReviewDTO> getCarRiview(String carId, String modelId, String reviewId, int page) {
        ArrayList<ReviewDTO> reviewDTOList = new ArrayList<ReviewDTO>();

        int offset = (page - 1) * PAGE_SIZE;
//        SELECT * FROM table ORDER BY column DESC LIMIT 1;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_CAR_REVIEW + " WHERE " + CAR_MODEL_ID + " = '" + modelId + "'";
        if (!reviewId.equals("")) {
            selectQuery = selectQuery + " AND " + ID + " = '" + reviewId + "'";
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        } else {
            //       SELECT * FROM table ORDER BY column DESC LIMIT 1;
            selectQuery = selectQuery + " ORDER BY " + ID + " DESC" + " LIMIT " + offset +","+ PAGE_SIZE;
            Log.i(getClass().getName(), ">>>>>>" + selectQuery);

        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setReviewID(cursor.getInt(cursor.getColumnIndex(ID)));
                reviewDTO.setReviewMSg(cursor.getString(cursor.getColumnIndex(CAR_REVIEW_MSG)));
                reviewDTO.setCarId(cursor.getString(cursor.getColumnIndex(CAR_ID)));
                reviewDTO.setCarModelId(cursor.getString(cursor.getColumnIndex(CAR_MODEL_ID)));
                reviewDTO.setRating(cursor.getString(cursor.getColumnIndex(CAR_REVIEW_RATING)));
                reviewDTO.setReviewHeading(cursor.getString(cursor.getColumnIndex(CAR_REVIEW_HEADING)));
                reviewDTO.setDate(cursor.getString(cursor.getColumnIndex(CREATED_AT)));

                reviewDTOList.add(reviewDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reviewDTOList;
    }


    public void BMW(String id) {
        inmodel("BMW X1", id);
        inmodel("BMW 1 Series", id);
        inmodel("BMW 3 Series", id);

        inmodel("BMW 3 Series GT", id);
        inmodel("BMW 5 Series", id);
        inmodel("BMW x3", id);
        inmodel("BMW x4", id);

        inmodel("BMW z4", id);
    }

    public void GMC(String id) {
        inmodel("BMW X1", id);
        inmodel("CANYON", id);
        inmodel("SIERRA 1500", id);
        inmodel("ACADIA", id);


    }

    public void Buick(String id) {


        inmodel(" Buick LaCrosse", id);
        inmodel("Buick Wildcat", id);
        inmodel("Buick LeSabre", id);
        inmodel("Buick Gran", id);

        inmodel("Buick Enclave", id);
        inmodel("Buick Lucerne", id);
        inmodel("Buick LeSabre", id);
        inmodel("Buick Regal", id);


    }


    public void Honda(String id) {

        inmodel("New Brio", id);
        inmodel("Amaze", id);
        inmodel("Jazz", id);
        inmodel("Mobilio", id);

        inmodel("Accord Hybrid", id);
    }

    public void Hyundai(String id) {


        inmodel("Hyundai Eon", id);
        inmodel("Hyundai i10", id);
        inmodel("Hyundai Grand i10", id);
        inmodel("Hyundai Xcent", id);

        inmodel("Hyundai Elite", id);
        inmodel("Hyundai i20", id);
        inmodel("Hyundai Verna", id);
        inmodel(" Hyundai Creta", id);


    }


    public void Ford(String id) {

        inmodel("Ford Figo", id);

        inmodel("Ford Aspire", id);


        inmodel("Ford EcoSpor", id);


        inmodel("Ford Endeavour", id);

        inmodel("Ford Mustang", id);

    }


    public void Toyota(String id) {

        inmodel("Toyota Prius", id);

        inmodel("Toyota Platinum Etios", id);
        inmodel("Toyota Etios Cross", id);
        inmodel("Toyota Corolla Altis", id);
        inmodel("Toyota Innova Crysta", id);
        inmodel("Toyota Fortuner", id);


    }

    public void Chevrolet(String id) {
        //10
        inmodel("Caprice", id);
        inmodel("Cruze", id);
        inmodel("Impala", id);
        inmodel("Malibu", id);
        inmodel("Matiz", id);
        inmodel("Onix/Prisma", id);
    }


    public void Jaguar(String id) {
        //11
        inmodel("XE", id);
        inmodel("XF", id);
        inmodel("F-PACE", id);
        inmodel(" F-TYPE", id);
        inmodel("XJ", id);


    }


    public void Chrysler(String id) {
//12
        inmodel("Chrysler Pacifica", id);
        inmodel("Chrysler Prowler", id);
        inmodel(" Chrysler PT Cruiser", id);
        inmodel("Chrysler Pacifica", id);
        inmodel("Chrysler Regal", id);

    }


    private void inmodel(String name, String id) {
        CarModelInfoDTO carInfoDTO = new CarModelInfoDTO();
        carInfoDTO.setCarId(id);
        carInfoDTO.setCarModelName(name);
        insertCarModelInfo(carInfoDTO);
    }
}
