package com.kania.todostack3.view.checklist.item;

import android.view.View;

import com.kania.todostack3.data.TodoData;

/**
 * Created by Seonghan Kim on 2017-07-14.
 */

public abstract class AbstractListItemData {

    public static final int CHECK_ITEM = 0;
    public static final int ADD_ITEM = 1;
    public static final int REPEAT_OPTION_ITEM = 2;

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
