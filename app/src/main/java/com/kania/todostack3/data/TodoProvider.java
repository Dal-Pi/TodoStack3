package com.kania.todostack3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;

import static com.kania.todostack3.data.TodoStackContract.SubjectEntry;
import static com.kania.todostack3.data.TodoStackContract.TodoEntry;

/**
 * Created by user on 2016-01-11.
 */
public class TodoProvider {

    public static TodoProvider instance;

    private static Context mContext;

    private static TodoStackDbHelper dbHelper;
    private static SQLiteDatabase todoStackDb;

    private static SparseArray<SubjectData> subjectMap;
    private static SparseArray<TodoData> todoMap;

    private static ArrayList<SubjectData> subjectList;
    private static ArrayList<TodoData> todoTree;

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
    }

    public static void initData() {
        dbHelper = new TodoStackDbHelper(mContext);
        todoStackDb = dbHelper.getReadableDatabase();

        getSubjectFromDb();
        getTodoFromDb();

        todoStackDb.close();
    }

    private static void getSubjectFromDb() {
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

    private static void getTodoFromDb() {
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

    //TODO saved 170928
    public static ArrayList<SubjectData> getAllSubject() {
        ArrayList<SubjectData> allSubjectData = new ArrayList<SubjectData>();
//        Iterator<Integer> it = subjectMap.keySet().iterator();
//        while(it.hasNext()) {
//            allSubjectData.add(subjectMap.get(it.next()));
//        }
        for (SubjectData subject : subjectList){
            allSubjectData.add(subject);
        }
        return allSubjectData;
    }

    public static ArrayList<TodoData> getAllTodo() {
        ArrayList<TodoData> allTodoData = new ArrayList<TodoData>();
//        Iterator<Integer> it = todoMap.keySet().iterator();
//        while(it.hasNext()) {
//            allTodoData.add(todoMap.get(it.next()));
//        }
        for (TodoData todo : todoList){
            allTodoData.add(todo);
        }

        return allTodoData;
    }

    public static ArrayList<TodoData> getTodoList(int subjectOrder) {
        ArrayList<TodoData> todos = new ArrayList<TodoData>();
        for (TodoData todo : todoList){
            if (todo.subjectId == subjectOrder) {
                todos.add(todo);
            }
        }

        return todos;
    }

    public static SubjectData getSubjectByOrder(int subOrder) {
//        Log.d("TodoStack", "[getSubjectByOrder] subOrder = " + subOrder);
        return subjectMap.get(subOrder);
    }

    public static TodoData getTodoById(int todoId) {
//        Log.d("TodoStack", "[getTodoById] subId = " + todoId);
        return todoMap.get(todoId);
    }

    public static int getSubjectCount() {
        return subjectMap.size();
    }

    public static int getTodoCount(int subjectOrder) {
        int ret = 0;
        for (TodoData td : todoList) {
            if (td.subjectId == subjectOrder)
                ret++;
        }

        return ret;
    }
}
