package com.github.yoojia.versiontest.demo2;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.yoojia.versiontest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pc on 2017/4/7.
 */

public class MyProgressDialog extends Dialog {
   private Context context;
    private int layoutId;
    private ProgressBar  progressBar;
    private Button cancleButton;
    private TextView titleTextView;
    private String titleString;
    private ClickEvent clickEvent;

    private String apkUrl = "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";
    private static final String savePath = "/sdcard/updatedemo/";
    private static final String saveFileName = savePath + "UpdateDemoRelease.apk";
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;
    private Thread downLoadThread;
    private boolean interceptFlag = false;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    progressBar.setProgress(progress);
                    break;
                case DOWN_OVER:
//                    installApk();
                    clickEvent.finish();
                    break;
                default:
                    break;
            }
        };
    };

    public MyProgressDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public MyProgressDialog(Context context, int theme , int layoutId, String title) {
        super(context, theme);
        this.context = context;
        this.layoutId = layoutId;
        this.titleString=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(layoutId);
        titleTextView=(TextView)findViewById(R.id.dialog_title);
        cancleButton=(Button)findViewById(R.id.dialog_button_cancel);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        titleTextView.setText(titleString);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interceptFlag = true;
                clickEvent.cancle();
            }
        });

    }

   public void  setClickEvent(ClickEvent clickEvent){
       this.clickEvent=clickEvent;
   }

    interface ClickEvent{
        void finish();
        void cancle();
    }


    @Override
    public void show() {
        super.show();
        downloadApk();
    }

    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    };

}
