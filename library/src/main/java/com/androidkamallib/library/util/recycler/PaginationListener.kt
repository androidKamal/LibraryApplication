package com.androidkamallib.library.util.recycler

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


//TODO move to lib
abstract class PaginationListener : RecyclerView.OnScrollListener {
    companion object {
        val PAGE_START = 1
        val PAGE_SIZE = 20
    }

    var layoutManager: LinearLayoutManager
    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */


    /**
     * Supporting only LinearLayoutManager for now.
     */
    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}