package com.zmm.listviewpaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MyListAdapter.OnListItemClickListener {

    @InjectView(R.id.list_view)
    ListView mListView;

    private List<String> mDeviceNames;
    private MyListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        init();
    }

    private void init() {
        mDeviceNames = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            mDeviceNames.add("TWS01211313_" + i);
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        mListAdapter = new MyListAdapter(getApplicationContext(),mDeviceNames);
        mListView.setAdapter(mListAdapter);
        mListAdapter.setOnListItemClickListener(this);
    }

    @OnClick({R.id.btn_pre, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pre:
                mListAdapter.turnToPre();
                break;
            case R.id.btn_next:
                mListAdapter.turnToNext();
                break;
        }
    }

    @Override
    public void OnItemClickListener(int index) {
        System.out.println("调用接口，当前位置：index = "+index);
    }
}
