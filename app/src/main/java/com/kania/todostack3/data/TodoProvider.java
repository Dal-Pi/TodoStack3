package com.kania.todostack3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.kania.todostack3.data.TodoStackContract.SubjectEntry;
import static com.kania.todostack3.data.TodoStackContract.TodoEntry;

/**
 * Created by user on 2016-01-11.
 */
public class TodoProvider {

    public static TodoProvider instance;

    private Context mContext;

    private TodoStackDbHelper dbHelper;
    private SQLiteDatabase todoStackDb;

    private SparseArray<SubjectData> subjectMap;
    private ArrayList<SubjectData> subjectList;

    private SparseArray<TodoData> todoMap;
    private ArrayList<TodoData> todoTree;
    private ArrayList<TodoData> todoListSorted;

    public static TodoProvider getInstance(Context applicationContext) {
        if (instance == null) {
            Log.d("TodoStack", "In case of creation TodoProvider");
            instance = new TodoProvider(applicationContext);
        }
        return instance;
    }

    private TodoProvider(Context context) {
        mContext = context;

        subjectMap = new SparseArray<>();
        subjectList = new ArrayList<>();
        todoMap = new SparseArray<>();
        todoTree = new ArrayList<>();
        todoListSorted = new ArrayList<>();
    }

    public void initData() {
        dbHelper = new TodoStackDbHelper(mContext);
        todoStackDb = dbHelper.getReadableDatabase();

        getSubjectFromDb();
        getTodoFromDb();

        todoStackDb.close();

        makeSortedTodoList();
    }

    private void getSubjectFromDb() {
        if (subjectMap == null)
            subjectMap = new SparseArray<>();
        if (subjectList == null)
            subjectList = new ArrayList<>();
        subjectMap.clear();
        subjectList.clear();

        final String[] projection = {
                SubjectEntry._ID,
                SubjectEntry.SUBJECT_NAME,
                SubjectEntry.COLOR,
                SubjectEntry.ORDER
        };
        final String sortOrder = SubjectEntry.ORDER + " ASC";
        Cursor subjectCursor = todoStackDb.query(SubjectEntry.TABLE_NAME,
                projection, null, null, null, null, sortOrder);
//        subjectCursor.moveToFirst();
        while (subjectCursor.moveToNext()) {
            SubjectData subject = new SubjectData.Builder(
                    subjectCursor.getInt(subjectCursor.getColumnIndexOrThrow(SubjectEntry._ID)),
                    subjectCursor.getInt(subjectCursor.getColumnIndexOrThrow(SubjectEntry.ORDER)),
                    subjectCursor.getString(subjectCursor.getColumnIndexOrThrow(SubjectEntry.SUBJECT_NAME)))
                    .color(ColorProvider.getDefaultColorString())
                    .build();

            subjectList.add(subject);
            subjectMap.put(subject.order, subject);
        }
    }

    private void getTodoFromDb() {
        if (todoMap == null)
            todoMap = new SparseArray<>();
        if (todoTree == null)
            todoTree = new ArrayList<>();
        todoMap.clear();
        todoTree.clear();

        final String[] projection = {
                TodoEntry._ID,
                TodoEntry.SUBJECT_ORDER,
                TodoEntry.TODO_NAME,
                TodoEntry.PARENT,
                TodoEntry.COMPLETED,
                TodoEntry.TARGET_DATE,
                TodoEntry.TIME_FROM,
                TodoEntry.TIME_TO,
                TodoEntry.CREATED_DATE,
                TodoEntry.LAST_UPDATED_DATE,
                TodoEntry.LOCATION
        };
        Cursor todoCursor = todoStackDb.query(TodoEntry.TABLE_NAME,
                projection, null, null, null, null, null);
//        todoCursor.moveToFirst();
        while (todoCursor.moveToNext()) {
            TodoData todo = new TodoData.Builder(
                    todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry._ID)),
                    todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry.SUBJECT_ORDER)),
                    todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry.PARENT)),
                    todoCursor.getString(todoCursor.getColumnIndexOrThrow(TodoEntry.TODO_NAME)))
                    .completed(todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry.COMPLETED)) > 0)
                    .targetDate(todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry.TARGET_DATE)))
                    .createdDate(todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry.CREATED_DATE)))
                    .updatedDate(todoCursor.getInt(todoCursor.getColumnIndexOrThrow(TodoEntry.LAST_UPDATED_DATE)))
                    .location(todoCursor.getString(todoCursor.getColumnIndexOrThrow(TodoEntry.LOCATION)))
                    .build();

            todoMap.append(todo.getId(), todo);
            if (todo.getParentId() != TodoData.ROOT_PARENT_TODO_ID) {
                TodoData parent = todoMap.get(todo.getParentId());
                if (parent != null)
                    parent.addChild(todo.getId());
                else {
                    Log.e("TodoStack", "error case! parent is not exist!");
                    todoTree.add(todo);
                }
            } else
                todoTree.add(todo);
        }
    }

    private void makeSortedTodoList() {
        todoListSorted.clear();

        Collections.sort(todoTree, new Comparator<TodoData>() {
            @Override
            public int compare(TodoData t1, TodoData t2) {
                if (t1.getCreatedDate() < t2.getCreatedDate())
                    return -1;
                else if (t1.getCreatedDate() > t2.getCreatedDate())
                    return 1;
                else
                    return 0;
            }
        });

        for (TodoData todo : todoTree) {
            todoListSorted.add(todo);
            dspTodoTree(todo.getChildren());
        }
    }

    private void dspTodoTree(ArrayList<Integer> children) {
        for (int key : children) {
            TodoData target = todoMap.get(key);
            if (target != null) {
                todoListSorted.add(target);
                if (target.getChildren().size() > 0) {
                    dspTodoTree(target.getChildren());
                }
            }
        }
    }

    public ArrayList<SubjectData> getSubjectList() {
        ArrayList<SubjectData> allSubjectData = new ArrayList<>();
//        Iterator<Integer> it = subjectMap.keySet().iterator();
//        while(it.hasNext()) {
//            allSubjectData.add(subjectMap.get(it.next()));
//        }
        for (SubjectData subject : subjectList){
            allSubjectData.add(subject);
        }
        return allSubjectData;
    }

    public ArrayList<TodoData> getTodoList() {
        ArrayList<TodoData> allTodoData = new ArrayList<TodoData>();
//        Iterator<Integer> it = todoMap.keySet().iterator();
//        while(it.hasNext()) {
//            allTodoData.add(todoMap.get(it.next()));
//        }
        for (TodoData todo : todoListSorted){
            allTodoData.add(todo);
        }
        return allTodoData;
    }

    public SubjectData getSubjectByOrder(int subOrder) {
//        Log.d("TodoStack", "[getSubjectByOrder] subOrder = " + subOrder);
        return subjectMap.get(subOrder);
    }

    public TodoData getTodoById(int todoId) {
//        Log.d("TodoStack", "[getTodoById] subId = " + todoId);
        return todoMap.get(todoId);
    }

    public int getSubjectCount() {
        return subjectMap.size();
    }

    public int getTodoCount(int subjectOrder) {
        int ret = 0;
        for (TodoData td : todoListSorted) {
            if (td.getSubjectId() == subjectOrder)
                ret++;
        }
        return ret;
    }
}
