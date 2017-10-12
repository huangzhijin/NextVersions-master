package com.github.yoojia.versiontest.demo2;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.yoojia.versiontest.R;

/**
 * Created by pc on 2017/4/7.
 */

public class MyDialog extends Dialog {
   private Context context;
    private int layoutId;
    private String titleString,contentString;
    private Button confirmButton;
    private Button cancleButton;
    private TextView titleTextView,contentTextView;
    private ClickEvent clickEvent;


    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public MyDialog(Context context, int theme ,int layoutId,String title,String content) {
        super(context, theme);
        this.context = context;
        this.layoutId = layoutId;
        this.titleString = title;
        this.contentString = content;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(layoutId);
        titleTextView=(TextView)findViewById(R.id.dialog_title);
        contentTextView=(TextView)findViewById(R.id.dialog_content);
        confirmButton=(Button)findViewById(R.id.dialog_button_ok);
        cancleButton=(Button)findViewById(R.id.dialog_button_cancel);

        titleTextView.setText(titleString);
        contentTextView.setText(contentString);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.confirm();
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.cancle();
            }
        });

    }

   public void  setClickEvent(ClickEvent clickEvent){
       this.clickEvent=clickEvent;
   }

    interface ClickEvent{
        void confirm();
        void cancle();
    }

}
