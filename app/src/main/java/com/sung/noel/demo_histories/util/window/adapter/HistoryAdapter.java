package com.sung.noel.demo_histories.util.window.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sung.noel.demo_histories.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {
    private ArrayList<String> histories;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;


    public HistoryAdapter(Context context) {
        this.context = context;
        histories = new ArrayList<>();
    }

    //-------------

    public void setData(ArrayList<String> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    //--------------
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_history, viewGroup, false));
    }
    //--------------

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder viewHolder, int index) {
        String keyWord = histories.get(index);
        viewHolder.tvKeyword.setText(keyWord);
    }

    //--------------

    @Override
    public int getItemCount() {
        return histories.size();
    }


    //-------------


    public interface OnItemClickListener {
        void onItemClicked(View view, int index, ArrayList<String> histories);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //----------------
    public interface OnDeleteClickListener {
        void onDeleteClicked(int index, ArrayList<String> histories);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    //-------------


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_keyword)
        TextView tvKeyword;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClicked(view, getLayoutPosition(),histories);
        }

        @OnClick(R.id.tv_delete)
        public void OnClicked(View view){
            onDeleteClickListener.onDeleteClicked(getLayoutPosition(),histories);
        }
    }
}
