package com.yanhui.qktx.Interface;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

import com.sogou.feedads.AdListener;
import com.sogou.feedads.AdView;
import com.yanhui.qktx.adapter.NewsAdapter;

/**
 * Created by liupanpan on 2017/12/20.
 */

public class SouGouLinsenter extends RecyclerView.OnScrollListener {
    private int oldFirstVisibleItem = -1;
    private int oldLastVisibleItem = -1;
    private int mTotalCount = 0;
    private AdListener adListener;
    private NewsAdapter newsAdapter;

    public SouGouLinsenter(NewsAdapter newsAdapter) {
        super();
        oldFirstVisibleItem = -1;
        oldLastVisibleItem = -1;
    }

    private void resetFirstAndLastVisibleItem() {
        oldFirstVisibleItem = -1;
        oldLastVisibleItem = -1;
        mTotalCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int firstVisibleItem, int visibleItemCount) {
        if (recyclerView == null || recyclerView.getAdapter() == null) {
            return;
        }
        final int totalCount = recyclerView.getAdapter().getItemCount();
        if (mTotalCount != totalCount) {
            if (totalCount > mTotalCount) {
                resetFirstAndLastVisibleItem();
            }
            mTotalCount = totalCount;
        }
        int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
        if (firstVisibleItem < oldFirstVisibleItem) {
            for (int i = firstVisibleItem; i < Math.min(oldFirstVisibleItem, lastVisibleItem); i++) {
                Log.d("SogouTest", "onAdImpression");
                if (newsAdapter != null) {
                    AdView adView = (AdView) newsAdapter.getViewHolderMap();
                    adView.onAdImpression(recyclerView);
                }
            }
        }
        if (lastVisibleItem > oldLastVisibleItem) {
            for (int i = Math.max(oldLastVisibleItem + 1, firstVisibleItem); i <= lastVisibleItem; i++) {
                Log.d("SogouTest", "onAdImpression");
                if (newsAdapter != null) {
                    AdView adView = (AdView) newsAdapter.getViewHolderMap().get(i);
                    adView.onAdImpression(recyclerView);
                }
            }
        }

        oldFirstVisibleItem = firstVisibleItem;
        oldLastVisibleItem = lastVisibleItem;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//       adListener.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                break;
        }
    }
}
