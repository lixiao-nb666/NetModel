package com.nrmyw.launcher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.view.reflected.ReflectedUtil;
import com.nrmyw.launcher.R;
import com.nrmyw.launcher.config.LauncherConfig;
import com.nrmyw.launcher.glide.MyGlide;
import com.nrmyw.launcher.view.AdapterMeasureHelper;
import com.nrmyw.launcher.view.CardScaleHelper;
import com.nrmyw.launcher.view.SpeedConfig;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainLauncherIconAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;

    private LayoutInflater layoutInflater;
    private ItemClick itemClick;
    private AdapterMeasureHelper mCardAdapterHelper = new AdapterMeasureHelper();
    private int w,h;
    private int llNeedW;
    private int iconW;
    private int textMTop;
    private int textSize;
    private CardScaleHelper mCardScaleHelper ;
    public MainLauncherIconAdapter(Context context, ItemClick itemClick,int w,int h,CardScaleHelper mCardScaleHelper) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.apps = new ArrayList<>();
        this.w=w;
        this.h=h;
        llNeedW= (int) (w/ LauncherConfig.Icon_Distance_Base);
        int iconW1=w/5;
        int iconW2=w/3;
        if(iconW1<iconW2){
            iconW=iconW1;
        }else {
            iconW=iconW2;
        }
        textMTop=iconW/25;
        textSize=iconW/10;
        this.mCardScaleHelper=mCardScaleHelper;
    }

    public void setData(List<SystemAppInfoBean> apps) {
        if (apps == null) {
            this.apps = new ArrayList<>();
            return;
        }
        this.apps = apps;
        this.notifyDataSetChanged();
//        getItemData(0);
    }

    public void  setItemSelectData(int index){
        if(null==apps||apps.size()==0){
            return;
        }
        SystemAppInfoBean systemAppInfoBean=apps.get(index);
        itemClick.nowSelect(systemAppInfoBean);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.adapter_lv_apps_layout, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Log.i(tag, "on bing view " + position);
        if(position==mCardScaleHelper.getCurrentItemPos()){
                //在中间位置不用处理
        }else if(position> mCardScaleHelper.getCurrentItemPos()){
            //在右边
            mCardScaleHelper.setViewAnimation(holder.itemView,0, CardScaleHelper.ItemViewType.right);
        }else {
            //在左边
            mCardScaleHelper.setViewAnimation(holder.itemView,0, CardScaleHelper.ItemViewType.left);
        }
        final ViewHodler viewHodler = (ViewHodler) holder;
        final SystemAppInfoBean app = apps.get(position);
        viewHodler.bgLL.setPadding(llNeedW,0,llNeedW,0);
        Drawable initBm=PackageManagerUtil.getInstance().getIcon(app.getPakeageName());
        viewHodler.appIconIV.setImageDrawable(initBm);
        viewHodler.appNameTV.setText(app.getName());
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.nowSelect(app);
            }
        };
        viewHodler.iconLL.setOnClickListener(onClickListener);
        viewHodler.appNameTV.setOnClickListener(onClickListener);
        viewHodler.appIconIV.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if(null==apps){
            return 0;
        }
        return apps.size();
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private LinearLayout bgLL;
        private LinearLayout iconLL;
        private ImageButton appIconIV;
        private TextView appNameTV;

        public ViewHodler(View itemView) {
            super(itemView);
            bgLL = itemView.findViewById(R.id.ll_bg);
            iconLL =itemView.findViewById(R.id.ll_app);
            appIconIV=new ImageButton(bgLL.getContext());
            LinearLayout.LayoutParams imageLP= new LinearLayout.LayoutParams(iconW,iconW);
            appIconIV.setLayoutParams(imageLP);
            appIconIV.setScaleType(ImageView.ScaleType.FIT_XY);
            appIconIV.setBackground(null);
            iconLL.addView(appIconIV);
            appNameTV=new TextView(bgLL.getContext());
            LinearLayout.LayoutParams textLP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            textLP.gravity= Gravity.CENTER;
            textLP.topMargin=textMTop;
            appNameTV.setLines(1);
            appNameTV.setTextColor(Color.WHITE);
            appNameTV.setTextSize(textSize);
            appNameTV.setLayoutParams(textLP);
            iconLL.addView(appNameTV);

        }
    }

    public interface ItemClick {

            void nowSelect(SystemAppInfoBean systemAppInfoBean);

    }
}