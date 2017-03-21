package com.sf.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by form
 * on 2017/3/7.
 * Desc
 */

public class HandlerActivity extends Activity implements View.OnClickListener {

    private Button btnHandlerCopy; //复制按钮
    private LinearLayout lltHandlerPercentage; //复制按钮
    private ProgressBar pbHandlerCopy; //复制进度条
    private TextView tvHandlerPercentage; //进度

    private Handler mHandler = new Handler() {  //主线程中的Handler对象

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int progress = (int) msg.obj;
            if (msg.what == 1) {
                //显示进度条
                pbHandlerCopy.setVisibility(View.VISIBLE);
                lltHandlerPercentage.setVisibility(View.VISIBLE);
                if (progress >= 0 && progress < 100) { //当进度条为加载完成，显示进度条
                    pbHandlerCopy.setProgress(progress);
                    tvHandlerPercentage.setText(progress + "");
                } else if (progress == 100) {  //当进度条加载完成，隐藏进度条
                    Toast.makeText(HandlerActivity.this, "复制完成！", Toast.LENGTH_SHORT).show();
                    pbHandlerCopy.setVisibility(View.GONE);
                    lltHandlerPercentage.setVisibility(View.GONE);
                    btnHandlerCopy.setEnabled(true);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        initViews();
        btnHandlerCopy.setOnClickListener(this);
    }

    private void initViews() {
        btnHandlerCopy = (Button) findViewById(R.id.btnHandlerCopy);
        lltHandlerPercentage = (LinearLayout) findViewById(R.id.lltHandlerPercentage);
        pbHandlerCopy = (ProgressBar) findViewById(R.id.pbHandlerCopy);
        tvHandlerPercentage = (TextView) findViewById(R.id.tvHandlerPercentage);
    }

    @Override
    public void onClick(View v) {
        //点击按钮 执行线程操作
        if (v.getId() == R.id.btnHandlerCopy) {
            MyThread myThread = new MyThread();
            myThread.start();
            //设置复制按钮不可点击，直至复制完成
            btnHandlerCopy.setEnabled(false);
        }
    }
    class MyThread extends Thread {

        @Override
        public void run() {
            super.run();

            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {

                inputStream = getResources().openRawResource(R.raw.image_pictrue2);

                //获取文件的总大小
                int totalLen = inputStream.available();
                System.out.println("totalLen 文件总大小" + totalLen + "");

                //创建复制的目的文件
                File file = new File("/sdcard/", "copyHandler.jpg");  //文件目录
                if (!file.exists()) {
                    file.createNewFile();
                }

                outputStream = new FileOutputStream(file);

                int len = 0;
                int lenMore = 0;
                byte[] by = new byte[1024];
                while ((len = inputStream.read(by, 0, by.length)) != -1) {
                    outputStream.write(by, 0, len);

                    //获取复制的百分比
                    lenMore += len;
                    double d = lenMore / (double) totalLen;
                    int percentage = (int) (d * 100);

                    //发送消息
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = percentage;
                    mHandler.sendMessage(msg);

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
