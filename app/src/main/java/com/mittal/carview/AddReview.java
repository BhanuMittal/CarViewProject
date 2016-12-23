package com.mittal.carview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.kavya.carvie.R;
import com.mittal.carview.dao.DatabaseHandler;
import com.mittal.carview.dto.ReviewDTO;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * Created by Mittal on 1/12/16.
 */
public class AddReview extends Fragment implements View.OnClickListener {
    //Declaration of Variables
    private View parentView;
    private Context context;
    private EditText etReview,etHeading;
    private TextView tvBack, tvSubmit, tvHeader,tvSubHeader, tvBack2, tvHeader2,tvSubHeader2;
    private DatabaseHandler databaseHandler;
    private String StrReviewMsg = "", carId = "", modelId = "",strHeading="",carName="",modelName="";
    private Float ratingValue=0.0f;
    private CardView cvReadReview, cvWriteReview;
    private RatingBar rbRating;
    private Bundle bundle;
    Utilities utilities;
    private LinearLayout ll_header1, ll_header2,ll_write_review;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.add_review, container, false);
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        utilities = Utilities.getInstance(context);
        databaseHandler = new DatabaseHandler(context);
        setHasOptionsMenu(true);
        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("car_id"))
                carId = bundle.getString("car_id");
            if (bundle.containsKey("model_id"))
                modelId = bundle.getString("model_id");


            if (bundle.containsKey("car_name"))
                carName = bundle.getString("car_name");
            if (bundle.containsKey("model_name"))
                modelName = bundle.getString("model_name");

        }
        initView();
    }

    //Method for initializing view
    public void initView() {
        rbRating=(RatingBar)parentView.findViewById(R.id.review_rating_bar);
        cvReadReview = (CardView) parentView.findViewById(R.id.cv_read_review);
        cvWriteReview = (CardView) parentView.findViewById(R.id.cv_write_review);

        cvWriteReview.setOnClickListener(this);
        cvReadReview.setOnClickListener(this);
        tvBack = (TextView) parentView.findViewById(R.id.tv_back);
        tvSubmit = (TextView) parentView.findViewById(R.id.tv_submit);

        tvHeader = (TextView) parentView.findViewById(R.id.tv_header);
        tvSubHeader = (TextView) parentView.findViewById(R.id.tv_sub_header);
        etReview = (EditText) parentView.findViewById(R.id.et_write_review);
        etHeading = (EditText) parentView.findViewById(R.id.et_headings);
        tvSubmit.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvHeader.setOnClickListener(this);
        tvHeader.setText(carName);
        tvSubHeader.setText(modelName);

        tvSubHeader.setText(setheader(modelName).toString());
        tvHeader.setText(setheader(carName).toString());
        tvBack2 = (TextView) parentView.findViewById(R.id.tv_back2);
        tvHeader2 = (TextView) parentView.findViewById(R.id.tv_header2);
        tvSubHeader2 = (TextView) parentView.findViewById(R.id.tv_sub_header2);

        tvBack2.setOnClickListener(this);
        tvHeader2.setOnClickListener(this);
        ll_header1 = (LinearLayout) parentView.findViewById(R.id.ll_header1);

        tvSubHeader2.setText(setheader(modelName).toString());

        tvHeader2.setText(setheader(carName).toString());

        ll_header2 = (LinearLayout) parentView.findViewById(R.id.ll_header);
        ll_header2.setVisibility(View.VISIBLE);
        ll_header1.setVisibility(View.GONE);
        ll_write_review = (LinearLayout) parentView.findViewById(R.id.ll_write_review);
        ll_write_review.setVisibility(View.GONE);


        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue=rating;
            }
        });
    }

private Spannable setheader(String title){
    Spannable text = new SpannableString(title);
    text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.top_bg)),
            0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return text;
}
    @Override
    public void onClick(View v) {
        utilities.hideKeyboard();
        switch (v.getId()) {
            case R.id.tv_back:
                getActivity().onBackPressed();
                break;
            case R.id.tv_back2:
                getActivity().onBackPressed();
                break;
            case R.id.cv_read_review:
                Bundle bundle = new Bundle();
                bundle.putString("car_id", carId);
                bundle.putString("model_id", modelId);
                bundle.putString("car_name", carName);
                bundle.putString("model_name",  modelName);
                ReadReview readReview = new ReadReview();
                readReview.setArguments(bundle);
                showFragment(readReview, "ReadReview");
                break;
            case R.id.cv_write_review:
                etReview.setText("");
                etHeading.setText("");
                rbRating.setRating(0);
                ll_header2.setVisibility(View.GONE);
                ll_header1.setVisibility(View.VISIBLE);
                tvSubmit.setVisibility(View.VISIBLE);
                cvReadReview.setVisibility(View.GONE);
                cvWriteReview.setVisibility(View.GONE);
                ll_write_review.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_submit:
                StrReviewMsg = etReview.getText().toString().trim();
                strHeading = etHeading.getText().toString().trim();
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                // time = getTimeInAMPM(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                int a = calendar.get(Calendar.MONTH) + 1;

                if (strHeading == null || strHeading.equals("")) {

                    Toast.makeText(context,"Please enter heading", Toast.LENGTH_LONG).show();
                }else if (ratingValue==0){
                    Toast.makeText(context,"Please select rating", Toast.LENGTH_LONG).show();

                }else {
                    ReviewDTO reviewDTO = new ReviewDTO();
                    reviewDTO.setReviewMSg(StrReviewMsg);
                    reviewDTO.setReviewHeading(strHeading);
                    reviewDTO.setRating(""+ratingValue);
                    reviewDTO.setDate(""+calendar.get(Calendar.YEAR) + "/" + a + "/" + calendar.get(Calendar.DATE));
                    reviewDTO.setCarId(carId);
                    reviewDTO.setCarModelId(modelId);
                    databaseHandler.insertReviewInfo(reviewDTO);
                    bundle = new Bundle();
                    bundle.putString("car_id", carId);
                    bundle.putString("model_id", modelId);
                    bundle.putString("car_name", carName);
                    bundle.putString("model_name",  modelName);
                    readReview = new ReadReview();
                    readReview.setArguments(bundle);
                    showFragment(readReview, "ReadReview");
                }
                break;
        }
    }

    //Method for Open Fragment
    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commit();
    }

}
