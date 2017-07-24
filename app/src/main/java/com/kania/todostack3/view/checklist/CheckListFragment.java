package com.kania.todostack3.view.checklist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kania.todostack3.R;
import com.kania.todostack3.data.TodoData;
import com.kania.todostack3.view.checklist.item.AbstractListItemData;
import com.kania.todostack3.view.checklist.item.CheckableItemData;

import java.util.ArrayList;

public class CheckListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CheckListAdapter mCheckListAdapter;

    public CheckListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CheckListFragment newInstance() {
        CheckListFragment fragment = new CheckListFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //get args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);

        mRecyclerView = view.findViewById(R.id.frag_checklist_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //mLayoutManager.scrollToPosition();

        mRecyclerView.setLayoutManager(mLayoutManager);

        mCheckListAdapter = new CheckListAdapter(getTestList());
        mRecyclerView.setAdapter(mCheckListAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //test code [start]
    ArrayList<AbstractListItemData> getTestList() {
        ArrayList<AbstractListItemData> items = new ArrayList<>();

        TodoData todo1 = new TodoData();
        todo1.id = 1;
        todo1.todoName = "first todo";
        todo1.subjectOrder = 1;
        todo1.complete = false;
        CheckableItemData item1 = new CheckableItemData(todo1);
        items.add(item1);

        TodoData todo2 = new TodoData();
        todo2.id = 2;
        todo2.todoName = "second todo";
        todo2.subjectOrder = 1;
        todo2.complete = true;
        CheckableItemData item2 = new CheckableItemData(todo2);
        items.add(item2);

        TodoData todo3 = new TodoData();
        todo3.id = 3;
        todo3.todoName = "third todo";
        todo3.subjectOrder = 2;
        todo3.complete = false;
        CheckableItemData item3 = new CheckableItemData(todo3);
        items.add(item3);

        return items;
    }
    //test code [end]
}
