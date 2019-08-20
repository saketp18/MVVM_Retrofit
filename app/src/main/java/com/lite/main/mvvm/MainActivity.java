package com.lite.main.mvvm;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lite.main.mvvm.adapter.CustomAdapter;
import com.lite.main.mvvm.models.ErrorResponse;
import com.lite.main.mvvm.models.NewsModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mErrorView;
    private CustomAdapter mCustomAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsModel mNewsModel = null;
    private int visibleItems, totalItemCount, pastVisibleItems, previousTotalItem = 0, viewThreshold = 10;
    private boolean isLoading;
    private int PAGE_COUNT = 1, PAGE_SIZE = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsModel = ViewModelProviders.of(this).get(NewsModel.class);
        mNewsModel.init(PAGE_COUNT, PAGE_SIZE);
        mErrorView = (TextView)findViewById(R.id.textinput_error);
        initRecyclerView();
        observeData();
    }

    private void initRecyclerView(){
        mCustomAdapter = new CustomAdapter(getApplicationContext());
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mCustomAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                visibleItems = mLinearLayoutManager.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                if(dy>0){
                    if(isLoading && totalItemCount>previousTotalItem){
                        isLoading = false;
                        previousTotalItem = totalItemCount;
                    }
                    if(!isLoading && totalItemCount-visibleItems <= pastVisibleItems + viewThreshold){
                        isLoading = true;
                        PAGE_COUNT++;
                        mNewsModel.loadData(PAGE_COUNT, PAGE_SIZE);
                    }
                }
            }
        });
    }

    private void observeData(){
        mNewsModel.getData().observe(this, mCustomAdapter);
        mNewsModel.getError().observe(this, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                mErrorView.setVisibility(View.VISIBLE);
                mErrorView.setText(errorResponse.getMessage());
                Toast.makeText(getApplicationContext(), errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
