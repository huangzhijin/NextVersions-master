package com.github.yoojia.versiontest.demo2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.github.yoojia.versiontest.R;

import java.io.File;

/**
 * Created by pc on 2017/4/7.
 */

    public class MyDialogActivity extends Activity {
        /** Called when the activity is first created. */

    private String updateMsg = "有新版本，是否更新？";
    private MyDialog noticeDialog;
    private MyProgressDialog downloadDialog;
    private static final String savePath = "/sdcard/updatedemo/";
    private static final String saveFileName = savePath + "UpdateDemoRelease.apk";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main3);
            Button button = (Button) findViewById(R.id.button1);
            button.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    init();

                }
            });

        }


    private void init(){
        //初始化一个自定义的Dialog
        noticeDialog = new MyDialog(MyDialogActivity.this,
                R.style.MyDialog,R.layout.dialog,"更新提示","发现新版本是否要更新？");
        noticeDialog.setClickEvent(new MyDialog.ClickEvent() {
            @Override
            public void confirm() {
                noticeDialog.dismiss();
                showDownDialog();
            }

            @Override
            public void cancle() {
                noticeDialog.dismiss();
            }
        });
        noticeDialog.show();
    }

    private void showDownDialog(){
        //初始化一个自定义的Dialog
        downloadDialog = new MyProgressDialog(MyDialogActivity.this,
                R.style.MyDialog,R.layout.progressdialog,"下载提示");
        downloadDialog.setClickEvent(new MyProgressDialog.ClickEvent() {

            @Override
            public void finish() {
                downloadDialog.dismiss();
                Toast.makeText(MyDialogActivity.this,"已下载完成，正在安装",Toast.LENGTH_SHORT).show();
                installApk();
            }

            @Override
            public void cancle() {
                downloadDialog.dismiss();
            }
        });
        downloadDialog.setCancelable(false);
        downloadDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(noticeDialog!=null){
            noticeDialog.dismiss();
        }
        if(downloadDialog!=null){
            downloadDialog.dismiss();
        }
    }

    private void installApk(){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        startActivity(i);

    }

}
