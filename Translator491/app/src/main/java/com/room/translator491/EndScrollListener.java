package com.room.translator491;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndScrollListener implements OnScrollListener {

	private int visibleThreshold = 6;
	private int currentPage = 0;
	private int previousTotalItemCount = 0;
	private boolean loading = true;
	private int startingPageIndex = 0;

	public EndScrollListener() {
	}

	@Override
	public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount)
        {

		if (totalItemCount < previousTotalItemCount) {
			this.currentPage = this.startingPageIndex;
			this.previousTotalItemCount = totalItemCount;
			if (totalItemCount == 0) { this.loading = true; } 
		}

		if (loading && (totalItemCount > previousTotalItemCount)) {
			loading = false;
			previousTotalItemCount = totalItemCount;
			currentPage++;
		}

		if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold)) {
		    onLoadMore(currentPage + 1, totalItemCount);
		    loading = true;
		}
	}

	public abstract void onLoadMore(int page, int totalItemsCount);

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
}