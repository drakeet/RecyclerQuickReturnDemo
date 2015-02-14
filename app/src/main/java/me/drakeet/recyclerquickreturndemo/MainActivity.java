package me.drakeet.recyclerquickreturndemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import com.nineoldandroids.view.ViewPropertyAnimator;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    RecyclerView mRecyclerView;
    View mHeader;

    private int mFlexibleSpaceOffset;
    private MyListAdapter mListAdapter;
    private ArrayList<String> mDataList;
    private boolean mHeaderIsShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlexibleSpaceOffset = getResources().getDimensionPixelSize(R.dimen.header_height);
        initView();
        setUpRecyclerView();
    }

    private void initView() {
        mHeader = findViewById(R.id.view_header);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
    }

    private void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDataList = new ArrayList<>();
        View paddingView = new View(this);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, mFlexibleSpaceOffset
        );
        paddingView.setLayoutParams(params);
        paddingView.setBackgroundColor(Color.WHITE);
        mListAdapter = new MyListAdapter(mDataList);
        mListAdapter.addHeaderView(paddingView);
        mRecyclerView.setAdapter(mListAdapter);

        mRecyclerView.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    boolean isIdle;
                    int mScrollY;

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        isIdle = newState == RecyclerView.SCROLL_STATE_IDLE;
                        if (isIdle) {
                            mScrollY = 0;
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        mScrollY += dy;
                        // show or hide header view
                        if (mScrollY > 12) {
                            hideHeader();
                        } else {
                            showHeader();
                        }
                    }
                }
        );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getData();
    }

    private void getData() {
        // should create a new Thread
        for (int i = 0; i < 512; i++) {
            mDataList.add("This is position " + i);
        }
        // post
        mListAdapter.notifyDataSetChanged();
    }

    private void showHeader() {
        if (!mHeaderIsShown) {
            ViewPropertyAnimator.animate(mHeader).cancel();
            ViewPropertyAnimator.animate(mHeader).translationY(0).setDuration(200).start();
            mHeaderIsShown = true;
        }
    }

    private void hideHeader() {
        if (mHeaderIsShown) {
            ViewPropertyAnimator.animate(mHeader).cancel();
            ViewPropertyAnimator.animate(mHeader).translationY(-mFlexibleSpaceOffset).setDuration(200).start();
            mHeaderIsShown = false;
        }
    }

}
