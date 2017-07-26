package com.kania.todostack3.data;

import java.util.ArrayList;

/**
 * Created by Seonghan Kim on 2017-07-14.
 */

public class TodoData {

    public static final int NON_SAVED_ID = -1;
    public static final int NOT_DEFINED_DATE = -1;
    public static final int PARENT_TODO_ID = -1;

    //builder member
    private int id;
    private int subjectId;
    private int parentId;
    private String todoName;
    private boolean isCompleted;
    private long created;
    private long lastUpdated;
    private long targetDate;
    private String location;
    //non-builder member
    private ArrayList<Integer> childrenIds;

    private TodoData(Builder builder) {
        this.id = builder.id;
        this.subjectId = builder.subjectId;
        this.parentId = builder.parentId;
        this.todoName = builder.todoName;
        this.isCompleted = builder.isCompleted;
        this.created = builder.created;
        this.lastUpdated = builder.lastUpdated;
        this.targetDate = builder.targetDate;
        this.location = builder.location;
        this.childrenIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public String getTodoName() {
        return todoName;
    }
    public void setId(String todoName) {
        this.todoName = todoName;
    }

    public long getCreatedDate() {
        return created;
    }
    public void setCreatedDate(long created) {
        this.created = created;
    }

    public long getLastUpdatedDate() {
        return lastUpdated;
    }
    public void setLastUpdatedDate(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getTargetDate() {
        return targetDate;
    }
    public void setTargetDate(long targetDate) {
        this.targetDate = targetDate;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public static class Builder {
        private int id;
        private int subjectId;
        private int parentId;
        private String todoName;
        private boolean isCompleted = false;
        private long created = NOT_DEFINED_DATE;
        private long lastUpdated = NOT_DEFINED_DATE;
        private long targetDate = NOT_DEFINED_DATE;
        private String location;

        public Builder(int id, int subjectId, int parentId, String todoName) {
            this.id = id;
            this.subjectId = subjectId;
            this.parentId = parentId;
            this.todoName = todoName;
        }

        public Builder completed(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public Builder createdDate(long created) {
            this.created = created;
            return this;
        }

        public Builder updatedDate(long lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder targetDate(long targetDate) {
            this.targetDate = targetDate;
            return this;
        }

        public Builder targetDate(String location) {
            this.location = location;
            return this;
        }

        public TodoData build() {
            return new TodoData(this);
        }
    }
}
