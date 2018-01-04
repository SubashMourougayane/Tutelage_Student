package com.example.nadus.tutelage_unisys_student.Adapters;

/**
 * Created by HP on 9/16/2017.
 */

public class ViewansAdapter {
    public ViewansAdapter() {
    }

    public ViewansAdapter(String jj, String j1, String j2, String j3, String j4, String jc)
    {
      this.ques=jj;
        this.ch1=j1;
        this.ch2=j2;
        this.ch3=j3;
        this.ch4=j4;
        this.correct=jc;

    }
    public static String ques,ch1,ch2,ch3,ch4,correct,chosenans;
    public boolean vall;

    public String getChosenans() {
        return chosenans;
    }

    public void setChosenans(String chosenans) {
        this.chosenans = chosenans;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getCh1() {
        return ch1;
    }

    public void setCh1(String ch1) {
        this.ch1 = ch1;
    }

    public String getCh2() {
        return ch2;
    }

    public void setCh2(String ch2) {
        this.ch2 = ch2;
    }

    public String getCh3() {
        return ch3;
    }

    public void setCh3(String ch3) {
        this.ch3 = ch3;
    }

    public String getCh4() {
        return ch4;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public boolean isVall() {
        return vall;
    }

    public void setVall(boolean vall) {
        this.vall = vall;
    }
}
