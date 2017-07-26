package com.kania.todostack3.data;

/**
 * Created by Seonghan Kim on 2017-07-14.
 */

public class SubjectData {

    public static final int NON_SAVED_ID = -1;
    public static final String DEFAULT_COLOR = "#FF555555";

    //builder member
    public int id;
    public int order;
    public String subjectName;
    //non-builder member
    public String color;

    public SubjectData(Builder builder) {
        this.id = builder.id;
        this.order = builder.order;
        this.subjectName = builder.subjectName;
        this.color = builder.color;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getColorString() {
        return color;
    }
    public void setColorString(String color) {
        this.color = color;
    }

    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    public static class Builder {
        public int id;
        public int order;
        public String subjectName;
        public String color;

        public Builder(int id, int order, String subjectName) {
            this.id = id;
            this.order = order;
            this.subjectName = subjectName;
            this.color = "#FF555555";
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public SubjectData build() {
            return new SubjectData(this);
        }
    }
}
