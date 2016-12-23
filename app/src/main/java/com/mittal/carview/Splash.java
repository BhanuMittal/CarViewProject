package com.mittal.carview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kavya.carvie.R;


public class Splash extends Fragment {
    //    AppSession appSession = null;
    public static final int SPLASH_DELAY = 3; // in second
    RelativeLayout rlMain;
    Context context;

    Utilities utilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        utilities = Utilities.getInstance(getContext());
        intiView(view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Bundle bundle = getArguments();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showFragment(new welcome(), "welcome");


            }
        }, SPLASH_DELAY * 1000);

    }

    void intiView(View view) {

    }

    //Imterface for checking success on base task
    public interface OnTaskResult {
        void onSuccess();
    }

    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }
}
