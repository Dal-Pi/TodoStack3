package com.kania.todostack3.view.checklist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kania.todostack3.R;
import com.kania.todostack3.data.TodoData;
import com.kania.todostack3.view.checklist.item.AbstractListItemData;

import java.util.ArrayList;

/**
 * Created by Seonghan Kim on 2017-07-14.
 */

public class CheckListAdapter extends RecyclerView.Adapter {

    private ArrayList<AbstractListItemData> mItems;

    public CheckListAdapter(ArrayList<AbstractListItemData> items) {
        this.mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_checkable, parent, false);

        if (viewType == AbstractListItemData.CHECK_ITEM) {
            return new CheckableViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TodoData todoData = mItems.get(position).getTodoData();
        if (getItemViewType(position) == AbstractListItemData.CHECK_ITEM) {
            CheckableViewHolder checkableHolder = (CheckableViewHolder)holder;
            checkableHolder.mCheckBox.setChecked(todoData.complete);
            checkableHolder.mTextView.setText(todoData.todoName);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getItemType();
    }

    public class CheckableViewHolder extends RecyclerView.ViewHolder {

        public CheckBox mCheckBox;
        public ViewGroup mContent;
        public TextView mTextView;

        public CheckableViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.item_checkable_check);
            mContent = itemView.findViewById(R.id.item_checkable_content);
            mTextView = itemView.findViewById(R.id.item_checkable_name);
        }
    }
}
