package com.mittal.carview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.kavya.carvie.R;
import com.mittal.carview.adapter.CarViewAdapter;
import com.mittal.carview.dao.DatabaseHandler;
import com.mittal.carview.dto.CarInfoDTO;
import com.mittal.carview.util.OnItemClickListener;

import java.util.ArrayList;


/**
 * Created by Mittal on 1/12/16.
 */
public class CarView extends Fragment implements View.OnClickListener {
    //Declaration of Variables
    private View parentView;
    private static Context context;
    private EditText etSearch;
    private RecyclerView rvItemList;
    private LinearLayoutManager linearLayoutManager;
    private CarViewAdapter itemListAdapter;
    private TextView tvBack, tvSearch, tvEmtyView;
    Utilities utilities;
    ArrayList<CarInfoDTO> carInfoList = new ArrayList<>();
    private MenuItem mFilterAction;
    private DatabaseHandler databaseHandler;
    private String StrSearchValue = "";
    int page = 1;

    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    boolean loading = true, isFirst = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.carview_list, container, false);
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
        utilities = Utilities.getInstance(context);
        databaseHandler = new DatabaseHandler(context);
        setHasOptionsMenu(true);
        page = 1;
        loading = true;
        initView();
        initToolbar();
        setValues();
        setupRecyclerView();
    }

    private void initToolbar() {
        android.support.v7.app.ActionBar actionBar = ((HomeActivity) context).getSupportActionBar();
        actionBar.show();
        ((HomeActivity) getActivity()).createBackButton(getResources().getString(R.string.select_a_make));
//        ColorDrawable colorDrawable = new ColorDrawable();
//        colorDrawable.setColor(getResources().getColor(R.color.app_header_color));
//        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }


    //Method for initializing view
    public void initView() {
        utilities.hideKeyboard();
        tvBack = (TextView) parentView.findViewById(R.id.tv_back);
        tvEmtyView = (TextView) parentView.findViewById(R.id.tv_empty_view);
        tvSearch = (TextView) parentView.findViewById(R.id.tv_search);
        etSearch = (EditText) parentView.findViewById(R.id.et_search);
        rvItemList = (RecyclerView) parentView.findViewById(R.id.rv_car__list);
        tvSearch.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        etSearch.addTextChangedListener(searchWatcher);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    carInfoList.clear();
                    utilities.hideKeyboard();
                    page=1;
                    loading=true;
                    StrSearchValue = etSearch.getText().toString().trim();
                    if (StrSearchValue != null && !StrSearchValue.equals("")) {
                        carInfoList = databaseHandler.getAllCar(StrSearchValue, "", page);
                        if (carInfoList != null && carInfoList.size() > 0) {
                            tvEmtyView.setVisibility(View.GONE);
                            itemListAdapter = new CarViewAdapter(getActivity(), carInfoList, onItemClickCallback);
                            rvItemList.setAdapter(itemListAdapter);
                        } else {
                            tvEmtyView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        if (carInfoList != null && carInfoList.size() > 0) {
                        }
                        carInfoList = databaseHandler.getAllCar("", "", page);
                        itemListAdapter = new CarViewAdapter(getActivity(), carInfoList, onItemClickCallback);
                        rvItemList.setAdapter(itemListAdapter);
                    }
                }
                return false;
            }
        });
    }

    //Method for setting list values
    private void setValues() {
        carInfoList = databaseHandler.getAllCar("", "", page);
        if (carInfoList != null && carInfoList.size() > 0) {
        } else {

            CarInfoDTO carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Acura");
            databaseHandler.insertCarInfo(carInfoDTO);


            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Audi");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("BMW");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("GMC");
            databaseHandler.insertCarInfo(carInfoDTO);
            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Buick");

            databaseHandler.insertCarInfo(carInfoDTO);
            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Honda");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Hyundai");
            databaseHandler.insertCarInfo(carInfoDTO);


            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Ford");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Toyota");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Chevrolet");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Jaguar");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoDTO = new CarInfoDTO();
            carInfoDTO.setCarName("Chrysler");
            databaseHandler.insertCarInfo(carInfoDTO);

            carInfoList = databaseHandler.getAllCar("", "", page);
        }


    }


    //Method for setting values of recycleview
    private void setupRecyclerView() {
        if (carInfoList != null && carInfoList.size() > 0) {
            tvEmtyView.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            rvItemList.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            itemListAdapter = new CarViewAdapter(getActivity(), carInfoList, onItemClickCallback);
            rvItemList.setAdapter(itemListAdapter);
        } else {
            tvEmtyView.setVisibility(View.VISIBLE);
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
                    if (carInfoList.size() >= 10) {
                        ArrayList<CarInfoDTO> arrayList=new ArrayList<CarInfoDTO>();
                        page = page + 1;
                        arrayList = databaseHandler.getAllCar("", "", page);
                        loading = false;
                        if (arrayList.size() > 0 ) {
                            carInfoList.addAll(arrayList);
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

    private final TextWatcher searchWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            if (s == null)
                return;
            if (s.toString().trim().equals("")) {
                page=1;
                loading=true;
                utilities.hideKeyboard();
                carInfoList = databaseHandler.getAllCar("", "", page);
                if (carInfoList.size()>0){

                    tvEmtyView.setVisibility(View.GONE);
                    itemListAdapter = new CarViewAdapter(getActivity(), carInfoList, onItemClickCallback);
                    rvItemList.setAdapter(itemListAdapter);
                }else {
                    tvEmtyView.setVisibility(View.VISIBLE);
                }


            }

        }
    };

    @Override
    public void onClick(View v) {
        utilities.hideKeyboard();
        switch (v.getId()) {
            case R.id.tv_back:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("welcome", "welcome");
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.tv_search:
                carInfoList.clear();
                StrSearchValue = etSearch.getText().toString().trim();
                if (StrSearchValue != null && !StrSearchValue.equals("")) {
                    carInfoList = databaseHandler.getAllCar(StrSearchValue, "", page);
                    if (carInfoList != null && carInfoList.size() > 0) {
                        tvEmtyView.setVisibility(View.GONE);
                        itemListAdapter = new CarViewAdapter(getActivity(), carInfoList, onItemClickCallback);
                        rvItemList.setAdapter(itemListAdapter);
                    } else {
                        tvEmtyView.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (carInfoList != null && carInfoList.size() > 0) {
                    }
                    carInfoList = databaseHandler.getAllCar("", "", page);
                    itemListAdapter = new CarViewAdapter(getActivity(), carInfoList, onItemClickCallback);
                    rvItemList.setAdapter(itemListAdapter);
                }
                break;


        }
    }


    //List item click of adapter
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Bundle bundle = new Bundle();
            bundle.putString("car_id", "" + carInfoList.get(position).getCarId());
            bundle.putString("car_name", "" + carInfoList.get(position).getCarName());
            CarModelView modelView = new CarModelView();
            modelView.setArguments(bundle);
            showFragment(modelView, "CarModelView");
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
