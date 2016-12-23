package com.mittal.carview;

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
import android.widget.TextView;

import com.kavya.carvie.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  getWindow().setBackgroundDrawableResource(R.drawable.splash);
//        new BaseUrlTask(this, false).execute(WS_BASE_URL);
        intiView();
        Splash splash = new Splash();
        Bundle bundle = new Bundle();
        if (getIntent() != null && getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            showFragment( new  welcome(), "welcome");

        }else {
            splash.setArguments(bundle);
            showFragment( splash, "Splash");
        }

        /** getting registration Id from GcmRegistrationId class*/
//        gri = new GcmRegistrationId(this);
//        gri.getGCMRegistrationId();
    }
    void intiView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_splash);
        setSupportActionBar(toolbar);
        mTitle= (TextView) toolbar.findViewById(R.id.toolbar_title);
    }

    public void createBackButton(String title) {
        // invalidateOptionsMenu();
        Spannable text = new SpannableString(title);
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.top_bg)),
                0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTitle.setText(text.toString());
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragmentLast = fragmentManager.findFragmentById(R.id.container);
        String tag = (String) fragmentLast.getTag();
        if (tag != null && (tag.equals("Verification") || tag.equals("Categories"))) {

        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    public void replaceFragment(int containerViewId, Fragment fragment, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .commit();
    }

    private void showFragment(Fragment targetFragment, String className) {
        FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, targetFragment, className);
        ft.commitAllowingStateLoss();
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
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
