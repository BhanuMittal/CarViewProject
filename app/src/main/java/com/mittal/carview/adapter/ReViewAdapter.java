package com.mittal.carview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kavya.carvie.R;
import com.mittal.carview.Utilities;
import com.mittal.carview.dto.ReviewDTO;
import com.mittal.carview.util.OnItemClickListener;


import java.util.List;

/**
 * Created by Mittal on 1/12/16.
 */
public class ReViewAdapter extends RecyclerView.Adapter<ReViewAdapter.ViewHolder> {

    private List<ReviewDTO> reviewList;
    private Context context;
    private Utilities utilities;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback, onItemClickCallArrow;

    public ReViewAdapter(Context context, List<ReviewDTO> reviewList, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.context = context;
        this.reviewList = reviewList;
        this.onItemClickCallback = onItemClickCallback;

        utilities = Utilities.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_read_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvReviewMsg.setText(reviewList.get(position).getReviewMSg());
        holder.tv_review_msg2.setText(reviewList.get(position).getReviewMSg());
        holder.tvHeading.setText(reviewList.get(position).getReviewHeading());
        holder.tvDate.setText(utilities.formateDateShownew(reviewList.get(position).getDate()));
        holder.rbRating.setRating(Float.parseFloat(reviewList.get(position).getRating()));
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        if (reviewList.get(position).getStatus().equals("1")) {
            holder.tvReviewMsg.setVisibility(View.GONE);
            holder.tv_review_msg2.setVisibility(View.VISIBLE);
        } else {
            holder.tvReviewMsg.setVisibility(View.VISIBLE);
            holder.tv_review_msg2.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvReviewMsg, tv_review_msg2,tvHeading,tvDate;
        ImageView iv_arrow;
        RatingBar rbRating;


        public ViewHolder(View itemView) {
            super(itemView);
            tvReviewMsg = (TextView) itemView.findViewById(R.id.tv_review_msg);
            tv_review_msg2 = (TextView) itemView.findViewById(R.id.tv_review_msg2);
            tvHeading = (TextView) itemView.findViewById(R.id.tv_heading);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            iv_arrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            rbRating = (RatingBar) itemView.findViewById(R.id.review_rating_bar);

        }
    }


}
