package com.mittal.carview;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kavya.carvie.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlHeader;
    private Toolbar toolbar;
    private Context context;
    private Utilities utilities;
    private TextView mTitle;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getName(), "onCreate.............................................");
        setContentView(R.layout.main_activity);
        context = this;
        utilities = Utilities.getInstance(this);
        intiView();
        initToolBar();
        changeFragment(new CarView(),"CarView");
    }
    void intiView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_splash);
        setSupportActionBar(toolbar);
        mTitle= (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setOnClickListener(this);
    }
    private void initToolBar() {
        android.support.v7.app.ActionBar actionBar = ((HomeActivity) context).getSupportActionBar();
        actionBar.show();
        ColorDrawable colorDrawable = new ColorDrawable();
      //  colorDrawable.setColor(getResources().getColor(R.color.app_green));
       // actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(false);
       // actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");
        Spannable text = new SpannableString(actionBar.getTitle());
//        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)),
//                0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title:
//                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
//                intent.putExtra("welcome","welcome");
//                startActivity(intent);
//                finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(getClass().getName(), "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                  //  mgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                    fragmentManager.popBackStack();
                    //  Toast.makeText(this,"pop",Toast.LENGTH_SHORT).show();
                    Log.i(getClass().getName(),
                            "stack count: " + fragmentManager.getBackStackEntryCount());
                } else {
                   // showFragment(new MenuFragment(), "MenuFragment");
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //Method for Open Fragment
    private void showFragment(Fragment targetFragment, String className) {
   getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commit();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
          // mgr.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            fragmentManager.popBackStack();
        }  else {
            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
            intent.putExtra("welcome","welcome");
            startActivity(intent);
            finish();
        }

    }







    public void createBackButton(String title) {
       // toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        // invalidateOptionsMenu();
        Spannable text = new SpannableString(title);
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.top_bg)),
                0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTitle.setText(text.toString());
    }
    private void changeFragment(final Fragment fragment, final String fragmentName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, fragmentName)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }


}
