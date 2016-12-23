package com.mittal.carview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kavya.carvie.R;
import com.mittal.carview.adapter.CarModelViewAdapter;
import com.mittal.carview.dao.DatabaseHandler;
import com.mittal.carview.dto.CarModelInfoDTO;
import com.mittal.carview.util.OnItemClickListener;

import java.util.ArrayList;


/**
 * Created by Mittal on 1/12/16.
 */
public class CarModelView extends Fragment implements View.OnClickListener {
    //Declaration of Variables
    private View parentView;
    private Context context;
    private EditText etSearch;
    private RelativeLayout rlFilter;
    private RecyclerView rvItemList;
    private LinearLayoutManager linearLayoutManager;
    private CarModelViewAdapter itemListAdapter;
    private TextView tvBack, tvSearch, tvEmtyView;
    Utilities utilities;
    ArrayList<CarModelInfoDTO> modelInfoList = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    String carId = "", StrSearchValue = "",carName="";
    Bundle bundle;
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
        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("car_id"))
                carId = bundle.getString("car_id");
            if (bundle.containsKey("car_name"))
                carName = bundle.getString("car_name");

        }
        page = 1;
        loading = true;
        setHasOptionsMenu(true);
        initView();
        initToolbar();
        setValues();
        //setupRecyclerView();
    }

    private void initToolbar() {
        android.support.v7.app.ActionBar actionBar = ((HomeActivity) context).getSupportActionBar();
        actionBar.show();
        ((HomeActivity) getActivity()).createBackButton(carName);
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
                    modelInfoList.clear();
                    utilities.hideKeyboard();
                    page=1;
                    loading=true;
                    StrSearchValue = etSearch.getText().toString().trim();
                    if (StrSearchValue != null && !StrSearchValue.equals("")) {
                        modelInfoList = databaseHandler.getCarModel(carId, StrSearchValue, "",page);
                        if (modelInfoList != null && modelInfoList.size() > 0) {
                            tvEmtyView.setVisibility(View.GONE);
                            itemListAdapter = new CarModelViewAdapter(getActivity(), modelInfoList, onItemClickCallback);
                            rvItemList.setAdapter(itemListAdapter);
                        } else {
                            tvEmtyView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        modelInfoList = databaseHandler.getCarModel(carId, "", "",page);
                        if (modelInfoList != null && modelInfoList.size() > 0) {
                            tvEmtyView.setVisibility(View.GONE);
                            itemListAdapter = new CarModelViewAdapter(getActivity(), modelInfoList, onItemClickCallback);
                            rvItemList.setAdapter(itemListAdapter);
                        } else {
                            tvEmtyView.setVisibility(View.VISIBLE);
                        }

                    }
                }
                return false;
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
                modelInfoList = databaseHandler.getCarModel(carId, "", "",page);
                if (modelInfoList.size()>0){
                    tvEmtyView.setVisibility(View.GONE);
                    itemListAdapter = new CarModelViewAdapter(getActivity(), modelInfoList, onItemClickCallback);
                    rvItemList.setAdapter(itemListAdapter);
                }else {
                    tvEmtyView.setVisibility(View.VISIBLE);
                }

            }

        }
    };

    //Method for setting list values
    private void setValues() {
        modelInfoList = databaseHandler.getCarModel(carId, "", "",page);
        if (modelInfoList != null && modelInfoList.size() > 0) {


        } else {
            if (carId.equals("1")) {
                CarModelInfoDTO carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Acura ILX");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Acura MDX");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Acura NSX");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Acura RDX");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Acura RLX");
                databaseHandler.insertCarModelInfo(carInfoDTO);


                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Acura TLX");
                databaseHandler.insertCarModelInfo(carInfoDTO);
            } else if (carId.equals("2")) {

                CarModelInfoDTO carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2016 Audi A3");
                databaseHandler.insertCarModelInfo(carInfoDTO);


                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2016 Audi A3 e-tron");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2016 Audi A3 Sedan");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi A4");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi A5");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2016 Audi A5");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2016 Audi A5 Cabriolet");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi A6");
                databaseHandler.insertCarModelInfo(carInfoDTO);
                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi A7");
                databaseHandler.insertCarModelInfo(carInfoDTO);


                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi A8 L");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi Q3");
                databaseHandler.insertCarModelInfo(carInfoDTO);


                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi Q5");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi Q7");
                databaseHandler.insertCarModelInfo(carInfoDTO);

                carInfoDTO = new CarModelInfoDTO();
                carInfoDTO.setCarId(carId);
                carInfoDTO.setCarModelName("2017 Audi R8");
                databaseHandler.insertCarModelInfo(carInfoDTO);


            } else if (carId.equals("3")) {
                databaseHandler.BMW(carId);
            } else if (carId.equals("4")) {
                databaseHandler.GMC(carId);
            } else if (carId.equals("5")) {
                databaseHandler.Buick(carId);
            } else if (carId.equals("6")) {
                databaseHandler.Honda(carId);
            } else if (carId.equals("7")) {
                databaseHandler.Hyundai(carId);
            } else if (carId.equals("8")) {
                databaseHandler.Ford(carId);
            } else if (carId.equals("9")) {
                databaseHandler.Toyota(carId);
            } else if (carId.equals("10")) {
                databaseHandler.Chevrolet(carId);
            } else if (carId.equals("11")) {
                databaseHandler.Jaguar(carId);
            } else if (carId.equals("12")) {
                databaseHandler.Chrysler(carId);
            }
            modelInfoList = databaseHandler.getCarModel(carId, "", "",page);

        }

        setupRecyclerView();
    }

    //Method for setting values of recycleview
    private void setupRecyclerView() {
        if (modelInfoList != null && modelInfoList.size() > 0) {
            tvEmtyView.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            rvItemList.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            itemListAdapter = new CarModelViewAdapter(getActivity(), modelInfoList, onItemClickCallback);
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
                    if (modelInfoList.size() >= 10) {
                        ArrayList<CarModelInfoDTO> arrayList=new ArrayList<CarModelInfoDTO>();
                        page = page + 1;
                        arrayList = databaseHandler.getCarModel(carId, "", "",page);
                        loading = false;
                        if (arrayList.size() > 0 ) {
                            modelInfoList.addAll(arrayList);
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

    @Override
    public void onClick(View v) {
        utilities.hideKeyboard();
        switch (v.getId()) {
            case R.id.tv_back:
                getActivity().onBackPressed();
                break;
            case R.id.tv_search:
                Log.i("dfdgfd", "size>>" + modelInfoList.size());
                modelInfoList.clear();
                StrSearchValue = etSearch.getText().toString().trim();
                if (StrSearchValue != null && !StrSearchValue.equals("")) {
                    modelInfoList = databaseHandler.getCarModel(carId, StrSearchValue, "",page);
                    if (modelInfoList != null && modelInfoList.size() > 0) {
                        tvEmtyView.setVisibility(View.GONE);
                        itemListAdapter = new CarModelViewAdapter(getActivity(), modelInfoList, onItemClickCallback);
                        rvItemList.setAdapter(itemListAdapter);
                    } else {
                        tvEmtyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    modelInfoList = databaseHandler.getCarModel(carId, "", "",page);
                    if (modelInfoList != null && modelInfoList.size() > 0) {
                        tvEmtyView.setVisibility(View.GONE);
                        itemListAdapter = new CarModelViewAdapter(getActivity(), modelInfoList, onItemClickCallback);
                        rvItemList.setAdapter(itemListAdapter);
                    } else {
                        tvEmtyView.setVisibility(View.VISIBLE);
                    }

                }
                break;


        }
    }


    //List item click of adapter
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Bundle bundle = new Bundle();
            bundle.putString("car_id", carId);
            bundle.putString("model_id", "" + modelInfoList.get(position).getCarModelId());
            bundle.putString("car_name", carName);
            bundle.putString("model_name",  modelInfoList.get(position).getCarModelName());
            AddReview addReview = new AddReview();
            addReview.setArguments(bundle);
            showFragment(addReview, "AddReview");
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
