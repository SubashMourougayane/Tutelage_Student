package com.example.nadus.tutelage_unisys_student.Adapters;

import java.util.ArrayList;

/**
 * Created by User on 16-09-2017.
 */

public class OverallResult {
    public OverallResult() {
    }

    public ArrayList<String> chosenAns = new ArrayList<>();

    public ArrayList<String> getChosenAns() {
        return chosenAns;
    }

    public void setChosenAns(ArrayList<String> chosenAns) {
        this.chosenAns = chosenAns;
    }

    public String getTot() {
        return tot;
    }

    public void setTot(String tot) {
        this.tot = tot;
    }

    public String tot;
}
