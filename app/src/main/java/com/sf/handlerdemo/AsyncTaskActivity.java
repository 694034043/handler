package com.sf.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by form
 * on 2017/3/7.
 * Desc
 */

public class AsyncTaskActivity extends Activity implements View.OnClickListener {

    protected Button btnAsyncTaskCopy; //复制按钮
    protected LinearLayout lltAsyncTaskPercentage;//显示进度的布局
    protected ProgressBar pbAsyncTaskCopy; //复制进度条
    protected TextView tvAsyncTaskPercentage; //进度
    protected int progress; //进度值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        initViews();
        btnAsyncTaskCopy.setOnClickListener(this);
    }

    private void initViews() {
        btnAsyncTaskCopy = (Button) findViewById(R.id.btnAsyncTaskCopy);
        lltAsyncTaskPercentage = (LinearLayout) findViewById(R.id.lltAsyncTaskPercentage);
        pbAsyncTaskCopy = (ProgressBar) findViewById(R.id.pbAsyncTaskCopy);
        tvAsyncTaskPercentage = (TextView) findViewById(R.id.tvAsyncTaskPercentage);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAsyncTaskCopy) {
            MyAsyncTask myAsyncTask = new MyAsyncTask(this,btnAsyncTaskCopy,lltAsyncTaskPercentage,pbAsyncTaskCopy,
                    tvAsyncTaskPercentage,progress);
            myAsyncTask.execute();
        }
    }

}
