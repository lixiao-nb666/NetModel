package com.newbee.lixiaomodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;
import com.newbee.lixiaomodel.service.event.NewBeeTtsSubscriptionSubject;

public class CameraReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor = context.getContentResolver().query(intent.getData(),
                null, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        Toast.makeText(context, "收到广播系统:"+path, Toast.LENGTH_SHORT).show();
        NewBeeTtsSubscriptionSubject.getInstence().ttsImagePath(path);
    }
}
