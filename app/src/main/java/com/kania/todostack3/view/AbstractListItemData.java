package com.kania.todostack3.view;

import android.view.View;

import com.kania.todostack3.data.TodoData;

/**
 * Created by Seonghan Kim on 2017-07-14.
 */

public abstract class AbstractListItemData {

    private TodoData mTodoData;
    private int mItemType;

    public AbstractListItemData(TodoData data, int type) {
        mTodoData = data;
        mItemType = type;
    }

    public TodoData getTodoData() {
        return mTodoData;
    }

    public int getItemType() {
        return mItemType;
    }

    public abstract View getItemView();
}
