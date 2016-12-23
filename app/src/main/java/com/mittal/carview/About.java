package com.mittal.carview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kavya.carvie.R;


/**
 * Created by Mittal on 1/12/16.
 */
public class About extends Fragment {
    //Declaration of Variables
    private View parentView;
    private Context context;
    private TextView tvAppVersion,tvEmail,tvDevloperName;

    private String carId = "", modelId = "", reviewId;
    private Bundle bundle;
    Utilities utilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.about, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        setHasOptionsMenu(true);
        utilities=Utilities.getInstance(context);

        initView();
        initToolbar();

    }
    private void initToolbar() {
        android.support.v7.app.ActionBar actionBar = ((MainActivity) context).getSupportActionBar();
        actionBar.show();
        ((MainActivity) getActivity()).createBackButton(getResources().getString(R.string.about));
//        ColorDrawable colorDrawable = new ColorDrawable();
//        colorDrawable.setColor(getResources().getColor(R.color.app_header_color));
//        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    //Method for initializing view
    public void initView() {
        tvAppVersion = (TextView) parentView.findViewById(R.id.tv_app_version);
        tvDevloperName = (TextView) parentView.findViewById(R.id.tv_dev_name);
        tvEmail = (TextView) parentView.findViewById(R.id.tv_email);

        tvEmail.setText("BMITTAL.IT@GMAIL.COM");
        tvDevloperName.setText("Bhanu Mittal");
        tvAppVersion.setText(utilities.getAppVersion());
    }





}
