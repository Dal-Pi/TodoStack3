package com.kania.todostack3.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by user on 2016-01-13.
 */
public class LoadingTodoTask extends AsyncTask<Void, Void, Boolean> {

    private final int MINIMUM_LOADING_TIME = 500;
    private Context mContext;
    private TodoProvider mTodoProvider;
    private TaskEndCallback mCallback;

    public interface TaskEndCallback {
        void loadFinished();
    }

    public LoadingTodoTask(Context context, TaskEndCallback callback) {
        mContext = context;
        setCallback(callback);
    }

    private void setCallback(TaskEndCallback callback) {
        if (callback != null) {
            mCallback = callback;
        } else {
            mCallback = new TaskEndCallback() {
                //empty callback
                @Override
                public void loadFinished() {
                }
            };
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //for cover loading animation, make whole spent time is over 1 seconds
        long startTime = getCurrentTimeInMillis();
        Log.d("TodoStack", "startTime = " + startTime);

        //just call getInstance for init
        mTodoProvider = TodoProvider.getInstance(mContext);
        mTodoProvider.initData();

        long endTime = getCurrentTimeInMillis();
        Log.d("TodoStack", "endTime = " + endTime);
        long remainTime = MINIMUM_LOADING_TIME - (endTime - startTime);
        if (remainTime > 0) {
            try {
                Thread.sleep(remainTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (mTodoProvider != null);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            //Todo make simple textviews using subject and todo data
            mCallback.loadFinished();
        } else {
            Log.d("TodoStack", "fail to load Todo DB");
        }
    }

    public long getCurrentTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
