package com.sf.handlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnHandler; //Handler按钮
    private Button btnAsyncTask; //AsyncTask按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        btnHandler.setOnClickListener(this);
        btnAsyncTask.setOnClickListener(this);
    }

    private void initViews() {
        btnHandler = (Button) findViewById(R.id.btnHandler);
        btnAsyncTask = (Button) findViewById(R.id.btnAsyncTask);
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.btnHandler:
                intent = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAsyncTask:
                intent = new Intent(MainActivity.this, AsyncTaskActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
