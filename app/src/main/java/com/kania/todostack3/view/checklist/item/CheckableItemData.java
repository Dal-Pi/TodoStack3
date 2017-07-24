package com.kania.todostack3.view.checklist.item;

import android.view.View;

import com.kania.todostack3.data.TodoData;

/**
 * Created by Seonghan Kim on 2017-07-25.
 */

public class CheckableItemData extends AbstractListItemData {

    public CheckableItemData(TodoData todoData) {
        super(todoData, AbstractListItemData.CHECK_ITEM);
    }

    @Override
    public View getItemView() {
        return null;
    }
}
