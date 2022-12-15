package com.nrmyw.launcher.view;

import android.content.Context;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/9/5 16:42
 * @类描述 ${TODO}recyclerView实现画廊帮助类
 */
public class CardScaleHelper {
    private RecyclerView mRecyclerView;
    private Context mContext;




    private int mCardWidth; // 卡片宽度
    private int mOnePageWidth; // 滑动一页的距离
    private int mCardGalleryWidth;

    private int mCurrentItemPos;
    private int mCurrentItemOffset;

    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();

    public void attachToRecyclerView(final RecyclerView mRecyclerView) {
        // 开启log会影响滑动体验, 调试时才开启

        this.mRecyclerView = mRecyclerView;
        mContext = mRecyclerView.getContext();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNoNeedToScroll = mCurrentItemOffset == 0 || mCurrentItemOffset == getDestItemOffset(mRecyclerView.getAdapter().getItemCount() - 1);
//                    onScrolledChangedCallback();
                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false;
                }
            }


            int x=0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                if(dx != 0){//去掉奇怪的内存疯涨问题
                    mCurrentItemOffset += dx;
                    computeCurrentItemPos();
//                    LogUtils.v(String.format("dx=%s, dy=%s, mScrolledX=%s", dx, dy, mCurrentItemOffset));
                    onScrolledChangedCallback();
                }

                x+=dx;
            }
        });
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });

        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);

        //--------------------------------
//                appLV.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState== RecyclerView.SCROLL_STATE_IDLE ){
//                    if (recyclerView != null && recyclerView.getChildCount() > 0) {
//                        try {
//                            int currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
//                            int needW=w/6;
//                            if(x>0){
//                                if(x>=needW){
//
//
//                                    currentPosition++;
//                                    if(currentPosition>=adapter.getItemCount()){
//                                        currentPosition=adapter.getItemCount()-1;
//                                    }
//                                }
//                            }else {
//                                if(x<=-needW){
//
//                                }else {
//                                    if(currentPosition!=0){
//                                        currentPosition++;
//                                    }
//                                }
//                            }
//                            appLV.scrollToPosition(currentPosition);
//                            x=0;
//                            Log.e("=====currentPosition", "" + currentPosition);
//                            adapter.getItemData(currentPosition);
//                        } catch (Exception e) {
//                        }
//                    }
//
//                }
//            }
//
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                Log.e("=====onScrolled", "" + x);
//            }
//        });

    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mCardGalleryWidth = mRecyclerView.getWidth();
                mCardWidth = mCardGalleryWidth - ScreenUtil.dip2px(mContext, 2 * (SpeedConfig.mPagePadding + SpeedConfig.mShowLeftCardWidth));
                mOnePageWidth = mCardWidth;

                mRecyclerView.smoothScrollToPosition(mCurrentItemPos);
                onScrolledChangedCallback();
            }
        });
    }

    public void setCurrentItemPos(int currentItemPos) {
        if(this.mCurrentItemPos==currentItemPos){
            onScrolledChangedCallback();
            Log.i("kankanjuli","kankanjuli-------------111:");
        }else {
            this.mCurrentItemPos = currentItemPos;


            mRecyclerView.smoothScrollToPosition(mCurrentItemPos);
            Log.i("kankanjuli","kankanjuli-------------222:"+mCurrentItemPos);
        }


    }

    public int getCurrentItemPos() {
        return mCurrentItemPos;
    }

    private int getDestItemOffset(int destPos) {
        return mOnePageWidth * destPos;
    }

    /**
     * 计算mCurrentItemOffset
     */
    private void computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return;
        boolean pageChanged = false;
        // 滑动超过一页说明已翻页
        if (Math.abs(mCurrentItemOffset - mCurrentItemPos * mOnePageWidth) >= mOnePageWidth) {
            pageChanged = true;
        }
        if (pageChanged) {
            int tempPos = mCurrentItemPos;

            mCurrentItemPos = mCurrentItemOffset / mOnePageWidth;
//            LogUtils.d(String.format("=======onCurrentItemPos Changed======= tempPos=%s, mCurrentItemPos=%s", tempPos, mCurrentItemPos));
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    public void onScrolledChangedCallback() {
        int offset = mCurrentItemOffset - mCurrentItemPos * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);

//        LogUtils.d(String.format("offset=%s, percent=%s", offset, percent));
        View leftView = null;
        View currentView;
        View rightView = null;
        if (mCurrentItemPos > 0) {
            leftView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);
        if (mCurrentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
            rightView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos + 1);
        }
        Log.i("lixiaokankan","lixiaokankandaxiaoon bing vie:"+mCurrentItemPos+(leftView == null)+(currentView == null) +(rightView == null));
        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.setScaleY((1 - SpeedConfig.mScale) * percent +SpeedConfig. mScale);

        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.setScaleY((SpeedConfig.mScale - 1) * percent + 1);

        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.setScaleY((1 -SpeedConfig. mScale) * percent + SpeedConfig.mScale);

        }
        setViewAnimation(leftView,percent,ItemViewType.left);
        setViewAnimation(currentView,percent,ItemViewType.middle);
        setViewAnimation(rightView,percent,ItemViewType.right);
    }


    public void setViewAnimation(View view,float percent,ItemViewType itemViewType){
        if(null==itemViewType||null==view){
            return;
        }
        switch (itemViewType){
            case middle:
                view.setScaleY((SpeedConfig.mScale - 1) * percent + 1);
                break;
            case left:
            case right:
               view.setScaleY((1 - SpeedConfig.mScale) * percent +SpeedConfig. mScale);
                break;

        }

    }

    public enum ItemViewType{
        middle,
        left,
        right

    }

}


