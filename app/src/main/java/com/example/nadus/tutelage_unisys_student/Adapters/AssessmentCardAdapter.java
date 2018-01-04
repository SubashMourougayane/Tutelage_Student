package com.example.nadus.tutelage_unisys_student.Adapters;

/**
 * Created by nadus on 02-01-2018.
 */

public class AssessmentCardAdapter {
    String classname, subjectname, date, duration, testname;

    public AssessmentCardAdapter(String classname, String subjectname, String date, String duration, String testname) {
        this.classname = classname;
        this.subjectname = subjectname;
        this.date = date;
        this.duration = duration;
        this.testname = testname;

    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }
}
