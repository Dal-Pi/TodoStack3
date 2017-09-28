package com.kania.todostack3.data;

import android.provider.BaseColumns;

/**
 * Created by Seonghan Kim on 2016-01-10.
 * Defines all names that using on this App
 */
public final class TodoStackContract {

    public TodoStackContract() {}

    public static class SubjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "subject";

        public static final String SUBJECT_NAME = "name";
        public static final String COLOR = "color";
        public static final String ORDER = "sequence";
    }

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";

        public static final String TODO_NAME = "name";
        public static final String SUBJECT_ORDER = "sub_id";
        public static final String TARGET_DATE = "target_date";
        public static final String PARENT = "date";
        public static final String COMPLETED = "completed";
        public static final String TIME_FROM = "time_from";
        public static final String TIME_TO = "time_to";
        public static final String LOCATION = "location";
        public static final String CREATED_DATE = "created_date";
        public static final String LAST_UPDATED_DATE = "last_updated";

    }
}