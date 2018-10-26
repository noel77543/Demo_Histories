package com.sung.noel.demo_histories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sung.noel.demo_histories.util.SharedPreferenceUtil;
import com.sung.noel.demo_histories.util.window.HistoryPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, HistoryPopupWindow.OnHistoriesItemClickListener {

    @BindView(R.id.button)
     Button button;
    @BindView(R.id.edit_text)
    EditText editText;
    private HistoryPopupWindow historyPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        historyPopupWindow =new HistoryPopupWindow(this);
        historyPopupWindow.setOnHistoriesItemClickListener(this);
        button.setOnClickListener(this);
        editText.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        historyPopupWindow.addHistory(editText.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        historyPopupWindow.showPopupWindow(editText);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onHistoriesItemClicked(String keyword) {
        editText.setText(keyword);
    }
}
