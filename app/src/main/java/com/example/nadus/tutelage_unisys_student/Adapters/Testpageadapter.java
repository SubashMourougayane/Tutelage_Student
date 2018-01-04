package com.example.nadus.tutelage_unisys_student.Adapters;

/**
 * Created by HP on 9/13/2017.
 */

public class Testpageadapter {
    public Testpageadapter() {
    }

    public Testpageadapter(String question, String op1, String op2, String op3, String op4, String correctOP)
    {
        ques=question;
        option1=op1;
        option2=op2;
        option3=op3;
        option4=op4;
        answer=correctOP;
    }
    String ques;
    String option1;
    String option2;
    String option3;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String answer;

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    String option4;
}
