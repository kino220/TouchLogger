package com.example.TouchLogger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Satoru on 13/12/10.
 */
public class LayerService extends Service{
    View view;
    WindowManager wm;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Viewからインフレータを作成する
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        // 重ね合わせするViewの設定を行う
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        // WindowManagerを取得する
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        // レイアウトファイルから重ね合わせするViewを作成する
        view = layoutInflater.inflate(R.layout.overlay, null);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.overlay_main);
        FrameLayout.LayoutParams  layoutParams = new FrameLayout.LayoutParams(
                wm.getDefaultDisplay().getWidth(),
                wm.getDefaultDisplay().getHeight());

        linearLayout.setLayoutParams(layoutParams);
        TextView textView = (TextView)view.findViewById(R.id.textView1);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setText("テストだよ！");
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Viewを画面上に重ね合わせする
        wm.addView(view, params);


        return super.onStartCommand(intent, flags, startId);

    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // サービスが破棄されるときには重ね合わせしていたViewを削除する
        wm.removeView(view);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
