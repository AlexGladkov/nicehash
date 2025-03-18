package com.dev.nicehash.helpers

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import com.dev.nicehash.R
import java.util.*

/**
 * Created by agladkov on 22.12.17.+
 * Import library for RecyclerView Config
 * @link https://github.com/drstranges/DataBinding_For_RecyclerView
 */
class ListConfig(adapter: RecyclerView.Adapter<*>?, layoutManagerProvider: LayoutManagerProvider?,
                 itemAnimator: RecyclerView.ItemAnimator?, itemDecorations: List<RecyclerView.ItemDecoration>?,
                 scrollListeners: List<RecyclerView.OnScrollListener>?, itemTouchHelper: ItemTouchHelper?,
                 hasFixedSize: Boolean, hasNestedScroll: Boolean) {

    private var mAdapter: RecyclerView.Adapter<*>? = adapter
    private var mLayoutManagerProvider: LayoutManagerProvider? = layoutManagerProvider
    private var mItemAnimator: RecyclerView.ItemAnimator? = itemAnimator
    private var mItemDecorations: List<RecyclerView.ItemDecoration> = itemDecorations ?: Collections.emptyList()
    private var mScrollListeners: List<RecyclerView.OnScrollListener>? = scrollListeners ?: Collections.emptyList()
    private var mItemTouchHelper: ItemTouchHelper? = itemTouchHelper
    private var mHasFixedSize: Boolean = hasFixedSize
    private var mHasNestedScroll: Boolean = hasNestedScroll

    /**
     * Applies defined configuration for RecyclerView
     *
     * @param context      the context
     * @param recyclerView the target recycler view for applying the configuration
     */
    fun applyConfig(context: Context, recyclerView: RecyclerView) {
        val layoutManager: RecyclerView.LayoutManager? = mLayoutManagerProvider?.get(context)
        if (mAdapter == null || mLayoutManagerProvider == null || layoutManager == null) return

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(mHasFixedSize)
        recyclerView.isNestedScrollingEnabled = mHasNestedScroll
        recyclerView.adapter = mAdapter

        mItemDecorations.forEach({
            recyclerView.addItemDecoration(it)
        })

        mScrollListeners?.forEach({
            recyclerView.addOnScrollListener(it)
        })

        if (mItemAnimator != null) {
            recyclerView.itemAnimator = mItemAnimator
        }

        mItemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    /**
     * Builder for setting ListConfig
     * Sample:
     * <pre>
     * {@code
     *      ListConfig listConfig = new ListConfig.Builder(mAdapter)
     *          .setLayoutManagerProvider(new SimpleGridLayoutManagerProvider(mSpanCount, getSpanSizeLookup()))
     *          .addItemDecoration(new ColorDividerItemDecoration(color, spacing, SPACE_LEFT|SPACE_TOP, false))
     *          .setDefaultDividerEnabled(true)
     *          .addOnScrollListener(new OnLoadMoreScrollListener(mCallback))
     *          .setItemAnimator(getItemAnimator())
     *          .setHasFixedSize(true)
     *          .setItemTouchHelper(getItemTouchHelper())
     *          .build(context);
     * }
     * </pre>
     * If LinearLayoutManager will be used by default
     */
    class Builder(adapter: RecyclerView.Adapter<*>) {
        private val TAG = Builder::class.java.simpleName
        private var mAdapter: RecyclerView.Adapter<*> = adapter
        private var mLayoutManagerProvider: LayoutManagerProvider? = null
        private var mItemAnimator: RecyclerView.ItemAnimator? = null
        private var mItemDecorations: ArrayList<RecyclerView.ItemDecoration>? = null
        private var mOnScrollListeners: ArrayList<RecyclerView.OnScrollListener>? = null
        private var mItemTouchHelper: ItemTouchHelper? = null
        private var mHasNestedScroll: Boolean = false
        private var mHasFixedSize: Boolean = false
        private var mDefaultDividerSize = -1

        /**
         * Set Layout manager provider. If not set default [LinearLayoutManager] will be applied
         *
         * @param layoutManagerProvider the layout manager provider. Can be custom or one of
         * simple: [SimpleLinearLayoutManagerProvider],
         * [SimpleGridLayoutManagerProvider] or
         * [SimpleStaggeredGridLayoutManagerProvider].
         * @return the builder
         */
        fun setLayoutManagerProvider(layoutManagerProvider: LayoutManagerProvider): Builder {
            Log.e(TAG, "layout manager is set $layoutManagerProvider")
            mLayoutManagerProvider = layoutManagerProvider
            return this
        }

        /**
         * Set [android.support.v7.widget.RecyclerView.ItemAnimator]
         *
         * @param itemAnimator the item animator
         * @return the builder
         */
        fun setItemAnimator(itemAnimator: RecyclerView.ItemAnimator): Builder {
            mItemAnimator = itemAnimator
            return this
        }

        /**
         * Set [android.support.v7.widget.RecyclerView.ItemDecoration]
         *
         * @return the builder
         */
        fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration): Builder {
            if (mItemDecorations == null) {
                mItemDecorations = ArrayList()
            }

            mItemDecorations?.add(itemDecoration)
            return this
        }

        /**
         * Set [android.support.v7.widget.RecyclerView.OnScrollListener]
         *
         * @param onScrollListener the scroll listener.
         * @return the builder
         */
        fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener): Builder {
            if (mOnScrollListeners == null) {
                mOnScrollListeners = ArrayList()
            }

            mOnScrollListeners?.add(onScrollListener)
            return this
        }

        /**
         * Set true if adapter changes cannot affect the size of the RecyclerView.
         * Applied to [RecyclerView.setHasFixedSize]
         *
         * @param isFixedSize true if RecyclerView items have fixed size
         * @return the builder
         */
        fun setHasFixedSize(isFixedSize: Boolean): Builder {
            mHasFixedSize = isFixedSize
            return this
        }

        /**
         * Set true if adapter changes cannot affect the size of the RecyclerView.
         * Applied to [RecyclerView.setNestedScrollingEnabled]
         *
         * @param isNestedScroll true if RecyclerView items have nested scroll
         * @return the builder
         */
        fun setHasNestedScroll(isNestedScroll: Boolean): Builder {
            mHasNestedScroll = isNestedScroll
            return this
        }

        /**
         * Set true to apply default divider with default size of 4dp.
         *
         * @param isEnabled set true to apply default divider.
         * @return the builder
         */
        fun setDefaultDividerEnabled(isEnabled: Boolean): Builder {
            mDefaultDividerSize = if (isEnabled) 0 else -1
            return this
        }

        /**
         * Enables defoult divider with custom size
         *
         * @param size
         * @return the builder
         */
        fun setDefaultDividerSize(size: Int): Builder {
            mDefaultDividerSize = size
            return this
        }

        /**
         * Set [ItemTouchHelper]
         *
         * @param itemTouchHelper the ItemTouchHelper to apply for RecyclerView
         * @return the builder
         */
        fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper): Builder {
            mItemTouchHelper = itemTouchHelper
            return this
        }

        /**
         * Creates new [ListConfig] with defined configuration
         * If LayoutManagerProvider is not set, the [SimpleLinearLayoutManagerProvider]
         * will be used.
         *
         * @param context the context
         * @return the new ListConfig
         */
        fun build(context: Context): ListConfig {
            if (mLayoutManagerProvider == null)
                mLayoutManagerProvider = SimpleLinearLayoutManagerProvider()

            if (mDefaultDividerSize >= 0) {
                if (mDefaultDividerSize == 0)
                    mDefaultDividerSize = context.resources
                            .getDimensionPixelSize(R.dimen.default_separator_size)
                /*if (mDefaultDividerSize == 0) mDefaultDividerSize = context.getResources()
                        .getDimensionPixelSize(R.dimen.rvdb_list_divider_size_default);
                addItemDecoration(new DividerItemDecoration(mDefaultDividerSize));*/
            }

            return ListConfig(
                    mAdapter,
                    mLayoutManagerProvider!!,
                    mItemAnimator, mItemDecorations,
                    mOnScrollListeners,
                    mItemTouchHelper,
                    mHasFixedSize,
                    mHasNestedScroll)
        }
    }

    /**
     * The provider of LayoutManager for RecyclerView
     */
    interface LayoutManagerProvider {
        operator fun get(context: Context): RecyclerView.LayoutManager
    }

    /**
     * The simple LayoutManager provider for [LinearLayoutManager]
     */
    class SimpleLinearLayoutManagerProvider : LayoutManagerProvider {
        override fun get(context: Context): RecyclerView.LayoutManager {
            return LinearLayoutManager(context)
        }
    }

    class RefreshLinearLayoutManagerProvider : LayoutManagerProvider {
        override fun get(context: Context): RecyclerView.LayoutManager {
            return LinearLayoutManager(context)
        }
    }

    /**
     * The simple LayoutManager provider for [LinearLayoutManager]
     */
    class ReversedLinearLayoutManagerProvider : LayoutManagerProvider {
        override fun get(context: Context): RecyclerView.LayoutManager {
            val mLayoutManager = LinearLayoutManager(context)
            mLayoutManager.reverseLayout = true
            mLayoutManager.stackFromEnd = true
            return mLayoutManager
        }
    }

    /**
     * The simple LayoutManager provider for [GridLayoutManager]
     */
    class SimpleGridLayoutManagerProvider : LayoutManagerProvider {
        private val mSpanCount: Int
        private var mSpanSizeLookup: GridLayoutManager.SpanSizeLookup? = null

        constructor(mSpanCount: Int) {
            this.mSpanCount = mSpanCount
        }

        constructor(spanCount: Int, spanSizeLookup: GridLayoutManager.SpanSizeLookup) {
            mSpanCount = spanCount
            mSpanSizeLookup = spanSizeLookup
        }

        override fun get(context: Context): RecyclerView.LayoutManager {
            val layoutManager = GridLayoutManager(context, mSpanCount)
            if (mSpanSizeLookup != null) layoutManager.spanSizeLookup = mSpanSizeLookup
            return layoutManager
        }
    }

    /**
     * The simple LayoutManager provider for [StaggeredGridLayoutManager]
     */
    class SimpleStaggeredGridLayoutManagerProvider @JvmOverloads constructor(
            private val mSpanCount: Int,
            private val mOrientation: Int = StaggeredGridLayoutManager.VERTICAL) : LayoutManagerProvider {

        override fun get(context: Context): RecyclerView.LayoutManager {
            return StaggeredGridLayoutManager(mSpanCount, mOrientation)
        }
    }

    class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View,
                                    parent: RecyclerView, state: RecyclerView.State) {

            // Add top margin only for the first item to avoid double space between
            if (parent.layoutManager?.itemCount == 4) {
                when (parent.getChildLayoutPosition(view)) {
                    0 -> {
                        outRect.top = 0
                        outRect.right = space
                        outRect.left = 0
                        outRect.bottom = space
                    }

                    1 -> {
                        outRect.top = 0
                        outRect.right = 0
                        outRect.left = space
                        outRect.bottom = space
                    }

                    2 -> {
                        outRect.top = space
                        outRect.right = space
                        outRect.left = 0
                        outRect.bottom = 0
                    }

                    3 -> {
                        outRect.top = space
                        outRect.right = 0
                        outRect.left = space
                        outRect.bottom = 0
                    }
                }
            } else if (parent.layoutManager?.itemCount == 3) {
                when (parent.getChildLayoutPosition(view)) {
                    0 -> {
                        outRect.top = 0
                        outRect.right = 0
                        outRect.left = 0
                        outRect.bottom = 0
                    }

                    1 -> {
                        outRect.top = space * 2
                        outRect.right = space
                        outRect.left = 0
                        outRect.bottom = 0
                    }

                    2 -> {
                        outRect.top = space * 2
                        outRect.right = 0
                        outRect.left = space
                        outRect.bottom = 0
                    }
                }
            } else if (parent.layoutManager?.itemCount == 2) {
                when (parent.getChildLayoutPosition(view)) {
                    0 -> {
                        outRect.top = 0
                        outRect.right = space
                        outRect.left = 0
                        outRect.bottom = 0
                    }

                    1 -> {
                        outRect.top = 0
                        outRect.right = 0
                        outRect.left = space
                        outRect.bottom = 0
                    }
                }
            } else if (parent.layoutManager?.itemCount == 3) {
                outRect.top = 0
                outRect.right = 0
                outRect.left = 0
                outRect.bottom = 0
            } else {
                val position = parent.getChildLayoutPosition(view)
                if (position > 1) {
                    if (position % 2 == 0) {
                        outRect.top = 0
                        outRect.right = space
                        outRect.left = 0
                        outRect.bottom = space * 2
                    } else {
                        outRect.top = 0
                        outRect.right = 0
                        outRect.left = space
                        outRect.bottom = space * 2
                    }
                } else {
                    outRect.top = 0
                    outRect.right = if (position == 0) space else 0
                    outRect.left = if (position == 0) 0 else space
                    outRect.bottom = space * 2
                }
            }
        }
    }
}