package com.sung.noel.demo_histories.util.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.sung.noel.demo_histories.R;
import com.sung.noel.demo_histories.util.SharedPreferenceUtil;
import com.sung.noel.demo_histories.util.window.adapter.HistoryAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryPopupWindow extends PopupWindow implements HistoryAdapter.OnItemClickListener, HistoryAdapter.OnDeleteClickListener {

    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private TextView tvClear;
    private HistoryAdapter historyAdapter;
    private OnHistoriesItemClickListener onHistoriesItemClickListener;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public HistoryPopupWindow(Context context) {
        sharedPreferenceUtil = new SharedPreferenceUtil(context, SharedPreferenceUtil._USER_DEFAULT_NAME);
        historyAdapter = new HistoryAdapter(context);
        historyAdapter.setOnDeleteClickListener(this);
        historyAdapter.setOnItemClickListener(this);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.window_history, null, false);
        ButterKnife.bind(this, view);
        setContentView(view);
        recyclerView = ButterKnife.findById(view, R.id.recycler_view);
        tvClear = ButterKnife.findById(view, R.id.tv_clear);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(historyAdapter);
        tvClear.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    //--------------

    /***
     * 取得關鍵字相關字串
     * @param keyword
     * @return
     */
    private ArrayList<String> getContainKeyword(String keyword) {
        ArrayList<String> histories = sharedPreferenceUtil.getHistories();
        ArrayList<String> containKeywords = new ArrayList<>();
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).contains(keyword)) {
                containKeywords.add(histories.get(i));
            }
        }
        return containKeywords.size() == 0 && keyword.length() < 1 ? histories : containKeywords;
    }


    //-----------------

    /***
     * show
     */
    public void showPopupWindow(EditText editText) {
        ArrayList<String> histories = getContainKeyword(editText.getText().toString());
        setHeight(editText.getWidth());
        setWidth(editText.getWidth());
        historyAdapter.setData(histories);
        if (!isShowing() && histories.size() > 0 && editText.getText().toString().length() > 0) {
            showAsDropDown(editText);
        }
    }

    //-------------

    /***
     * 新增歷史紀錄
     * @param keyword
     */
    public void addHistory(String keyword) {
        sharedPreferenceUtil.addHistory(keyword);
    }


    //-----------------

    /***
     * 清除全部
     * @param view
     */
    @OnClick(R.id.tv_clear)
    public void onClicked(View view) {
        sharedPreferenceUtil.clearHistories();
        dismiss();
    }

    //---------------

    /***
     * 點擊項目
     * @param view
     * @param index
     */
    @Override
    public void onItemClicked(View view, int index, ArrayList<String> histories) {
        if (onHistoriesItemClickListener != null) {
            onHistoriesItemClickListener.onHistoriesItemClicked(histories.get(index));
        }
        dismiss();
    }
    //-------------

    /***
     *  刪除項目
     */
    @Override
    public void onDeleteClicked(int index, ArrayList<String> histories) {
        sharedPreferenceUtil.removeHistory(histories.get(index));
        ArrayList<String> newHistories = sharedPreferenceUtil.getHistories();
        if (newHistories.size() == 0) {
            dismiss();
        }
        historyAdapter.setData(newHistories);
    }

    //-----------------

    public interface OnHistoriesItemClickListener {
        void onHistoriesItemClicked(String keyword);
    }

    public void setOnHistoriesItemClickListener(OnHistoriesItemClickListener onHistoriesItemClickListener) {
        this.onHistoriesItemClickListener = onHistoriesItemClickListener;
    }
}
