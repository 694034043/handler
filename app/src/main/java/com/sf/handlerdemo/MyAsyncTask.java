package com.sf.handlerdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by form
 * on 2017/3/7.
 * Desc
 */
class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    private Context context;
    protected Button btnCopy; //复制按钮
    private LinearLayout lltPercentage; //显示进度的布局
    private ProgressBar progressBar; //进度条
    private TextView tvPercentage; //进度
    private int  progress;

    public MyAsyncTask(Context context, Button btnCopy, LinearLayout lltPercentage, ProgressBar progressBar, TextView tvPercentage, int progress) {
        this.context = context;
        this.btnCopy = btnCopy;
        this.lltPercentage = lltPercentage;
        this.progressBar = progressBar;
        this.tvPercentage = tvPercentage;
        this.progress = progress;
    }

    /**
     * 后台任务在这里执行，只有该方法是在子线程中执行
     *
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {

        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = context.getResources().openRawResource(R.raw.image_pictrue4);

            //获取文件总大小
            int totalLen = inputStream.available();

            //创建目的文件
            File file = new File("/sdcard/", "copyAsyncTask.jpg");
            if (!file.exists()) {
                file.createNewFile();
            }

            outputStream = new FileOutputStream(file);
            int len = 0;
            int lenMore = 0;
            byte[] by = new byte[1024];
            while ((len = inputStream.read(by, 0, by.length)) != -1) {
                lenMore += len;
                outputStream.write(by, 0, len);
                //获取写入的百分比
                double d = lenMore / (double) totalLen;
                progress = (int) (d * 100);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("TAG", "doInBackground: " + progress);

                //传递进度值
                publishProgress(progress);

            }
            outputStream.flush();
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
        return null;
    }

    /**
     * 在doInBackground方法中调用publishProgress()会触发该方法调用，可以更新进度条
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        progress = values[0];
        //显示进度条和进度值
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        if (progress >= 0 && progress < 100) { //当进度条为加载完成，显示进度条
            progressBar.setProgress(progress);
            tvPercentage.setText(progress + "");
        } else if (progress == 100) {  //当进度条加载完成，隐藏进度条
            Toast.makeText(context, "复制完成！", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            lltPercentage.setVisibility(View.GONE);
        }
    }

    /**
     * 任务开始前的操作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //在复制完成之前，按钮不可点击
        btnCopy.setEnabled(false);
    }

    /**
     * 任务结束后的设置
     *
     * @param aVoid
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //在复制完成之前，按钮可点击
        btnCopy.setEnabled(true);
    }
}
