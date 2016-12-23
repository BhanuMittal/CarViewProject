package com.mittal.carview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.kavya.carvie.R;


/**
 * Created by Mittal on 1/12/16.
 */

public class welcome extends BaseFragment implements View.OnClickListener {
    private TextView tvChooseRide,tvAbout;
    private LinearLayout linearLayout;
   // private CheckBox cbRemeber;
   ImageView imageView;
    Context context;
    Utilities utilities;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        utilities = Utilities.getInstance(context);
        initView(view);

    }

    private void initView(View view) {
        tvChooseRide = (TextView) view.findViewById(R.id.tv_choose_ride);
        tvAbout = (TextView) view.findViewById(R.id.tv_about);
        tvAbout.setOnClickListener(this);
        linearLayout=(LinearLayout) view.findViewById(R.id.ll_placeholder);
        tvChooseRide.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.iv_gif);
        if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int height = displayMetrics.widthPixels;
            int width = displayMetrics.heightPixels;
            linearLayout.getLayoutParams().height = ((696/395)*width)/4;
            linearLayout.getLayoutParams().width =width;
        }else {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            linearLayout.getLayoutParams().height = ((696/395)*width)/2;
            linearLayout.getLayoutParams().width =width;
        }
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.car).into(imageViewTarget);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(getActivity(), "landscape", Toast.LENGTH_SHORT).show();
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int height = displayMetrics.widthPixels;
            int width = displayMetrics.heightPixels;
            linearLayout.getLayoutParams().height = ((696/395)*width)/4;
            linearLayout.getLayoutParams().width =width;
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
            Glide.with(this).load(R.raw.car).into(imageViewTarget);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;

            linearLayout.getLayoutParams().height = ((696/395)*width)/2;
            linearLayout.getLayoutParams().width =width;
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
            Glide.with(this).load(R.raw.car).into(imageViewTarget);
           // Toast.makeText(getActivity(), "Portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_placeholder:
                startActivity(new Intent(getActivity(),HomeActivity.class));
                getActivity().finish();
                break;

            case R.id.tv_choose_ride:
                startActivity(new Intent(getActivity(),HomeActivity.class));
                getActivity().finish();
                break;

            case R.id.tv_about:
                showFragment(new About(),"About");
                break;

         }
    }
    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commit();
    }
}
