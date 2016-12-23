package com.mittal.carview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kavya.carvie.R;
import com.mittal.carview.adapter.ReViewAdapter;
import com.mittal.carview.dao.DatabaseHandler;
import com.mittal.carview.dto.ReviewDTO;
import com.mittal.carview.util.OnItemClickListener;

import java.util.ArrayList;


/**
 * Created by Mittal on 1/12/16.
 */
public class ReadReview extends Fragment implements View.OnClickListener {
    //Declaration of Variables
    private View parentView;
    private Context context;
    private RecyclerView rvItemList;
    private LinearLayoutManager linearLayoutManager;
    private ReViewAdapter itemListAdapter;
    private TextView tvBack, tvHeader, tv_empty_view,tvSubHeader;
    private ArrayList<ReviewDTO> reviewList = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    private String carId = "", modelId = "",carName="",modelName="";
    private Bundle bundle;
    int page = 1;

    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    boolean loading = true, isFirst = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.read_review, container, false);
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
        databaseHandler = new DatabaseHandler(context);
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
        page = 1;
        loading = true;
        setHasOptionsMenu(true);
        initView();
        setupRecyclerView();
    }

    //Method for initializing view
    public void initView() {
        tvBack = (TextView) parentView.findViewById(R.id.tv_back);
        rvItemList = (RecyclerView) parentView.findViewById(R.id.rv_review__list);
        tvBack.setOnClickListener(this);
        tvHeader = (TextView) parentView.findViewById(R.id.tv_header);
        tv_empty_view = (TextView) parentView.findViewById(R.id.tv_empty_view);
        tvSubHeader = (TextView) parentView.findViewById(R.id.tv_sub_header);
        tvHeader.setOnClickListener(this);
        tvSubHeader.setText(setheader(modelName).toString());
        tvHeader.setText(setheader(carName).toString());

    }

    //Method for setting values of recycleview
    private void setupRecyclerView() {
        reviewList = databaseHandler.getCarRiview(carId, modelId, "",page);
        if (reviewList != null && reviewList.size() > 0) {
            tv_empty_view.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            rvItemList.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            itemListAdapter = new ReViewAdapter(getActivity(), reviewList, onItemClickCallback);
            rvItemList.setAdapter(itemListAdapter);
        } else {
            tv_empty_view.setVisibility(View.VISIBLE);
        }

        rvItemList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if ((lastVisibleItem == totalItemCount - 1) && loading) {

                    // End has been reached
                    // Do something
                    if (reviewList.size() > 0) {
                        ArrayList<ReviewDTO> arrayList=new ArrayList<ReviewDTO>();
                        page = page + 1;
                        arrayList = databaseHandler.getCarRiview(carId, modelId, "",page);
                        loading = false;
                        if (arrayList.size() > 0 ) {
                            reviewList.addAll(arrayList);
                            itemListAdapter.notifyDataSetChanged();
                            if (arrayList.size() < 10) {
                                loading = false;
                                page = 1;
                            }else {
                                loading=true;
                            }

                        } else {
                            loading = false;
                            page = 1;
                        }
                    } else {


                        // loading = true;
                    }
                }
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
        switch (v.getId()) {
            case R.id.tv_back:
                getActivity().onBackPressed();
                break;
            case R.id.tv_header:
//                Intent intent=new Intent(getActivity(),MainActivity.class);
//                intent.putExtra("welcome","welcome");
//                startActivity(intent);
//                getActivity().finish();
                break;

        }
    }


    //List item click of adapter
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            if (reviewList.get(position).getStatus().equals("0")){
                reviewList.get(position).setStatus("1");
            }else{
                reviewList.get(position).setStatus("0");
            }

            itemListAdapter.notifyDataSetChanged();
        }
    };




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
