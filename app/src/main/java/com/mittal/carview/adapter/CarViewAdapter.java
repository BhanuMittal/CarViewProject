package com.mittal.carview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kavya.carvie.R;
import com.mittal.carview.Utilities;
import com.mittal.carview.dto.CarInfoDTO;
import com.mittal.carview.util.OnItemClickListener;


import java.util.List;

/**
 * Created by Mittal on 1/12/16.
 */
public class CarViewAdapter extends RecyclerView.Adapter<CarViewAdapter.ViewHolder>{

    private List<CarInfoDTO> carInfolist;
    private Context context;
    private Utilities utilities;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    public CarViewAdapter(Context context, List<CarInfoDTO> carInfolist, OnItemClickListener.OnItemClickCallback onItemClickCallback){
        this.context = context;
        this.carInfolist = carInfolist;
        this.onItemClickCallback = onItemClickCallback;
        utilities = Utilities.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_car_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvAddressName.setText(carInfolist.get(position).getCarName());

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));



    }

    @Override
    public int getItemCount() {
        return carInfolist.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAddressName;


        public ViewHolder(View itemView) {
            super(itemView);

            tvAddressName = (TextView) itemView.findViewById(R.id.tv_car_name);

        }
    }


}
