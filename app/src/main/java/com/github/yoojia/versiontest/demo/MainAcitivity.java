package com.github.yoojia.versiontest.demo;

import android.app.Activity;
import android.os.Bundle;

import com.github.yoojia.versiontest.R;

public class MainAcitivity extends Activity {
    

	private UpdateManager mUpdateManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian2);
        
        mUpdateManager = new UpdateManager(this);
        mUpdateManager.checkUpdateInfo();
    }     
}