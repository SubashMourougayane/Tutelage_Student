package com.example.nadus.tutelage_unisys_student.Home;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentQuestionAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.OverallResult;
import com.example.nadus.tutelage_unisys_student.Adapters.Result;
import com.example.nadus.tutelage_unisys_student.Adapters.TestAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.ViewansAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.ViewansItemAdapter;
import com.example.nadus.tutelage_unisys_student.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nadus on 04-01-2018.
 */

public class Fragment_aptigo_results_page extends Fragment
{
    ProgressDialog progressDialog;

    DatabaseReference databaseReference,databaseReference_ref;
    RecyclerView rc3;
    ViewansItemAdapter via2;
    ArrayList<String> ov_list = new ArrayList<>();
    private ArrayList<AssessmentQuestionAdapter> viewansAdapters=new ArrayList<AssessmentQuestionAdapter>();
    ArrayList<TestAdapter> testAdapters=new ArrayList<TestAdapter>();
    ArrayList<Result> results=new ArrayList<Result>();
    int j=0,k=0;
    TextView scored;
    private String univ_name;
    private String u_regno;
    private String mailsplit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_aptigo_viewanswer, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching test results...");

        SharedPreferences prefs = getActivity().getSharedPreferences("Tutelage_Student", MODE_PRIVATE);
        univ_name = prefs.getString("univ_name", "");
        u_regno = prefs.getString("u_regno", "");
        mailsplit = prefs.getString("mailsplit","");

        System.out.println("Selected testname is "+Fragment_aptigo_results.testname_selected);
        scored = (TextView) v.findViewById(R.id.scored);
        rc3=(RecyclerView)v.findViewById(R.id.viewansRecycler);
        rc3.setLayoutManager(new LinearLayoutManager(getActivity()));
        new MyTaskbef().execute();
        new MyTask().execute();

        //progressDialog.show();
        //int i=6;
//        while(i>0)
//        {
//            viewansAdapters.add(new ViewansAdapter("","","","","","",""));
//            i--;
//        }
        via2=new ViewansItemAdapter(R.layout.testresultcard,viewansAdapters,ov_list);
        rc3.setAdapter(via2);
        return v;
    }

    private class MyTaskbef extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            databaseReference_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Students").child(u_regno).child(Fragment_aptigo_results.testname_selected);
            databaseReference_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    OverallResult overallResult = dataSnapshot.getValue(OverallResult.class);
                    System.out.println("Chosen ans ---> "+overallResult.getChosenAns());
                    System.out.println("Tot ---> "+overallResult.getTot());

                    for(int i=0;i<overallResult.getChosenAns().size();i++)
                    {
                        String temp = overallResult.getChosenAns().get(i);
                        ov_list.add(temp);
                    }
                    scored.setText("You scored : "+overallResult.getTot()+"/"+overallResult.getChosenAns().size());
                    System.out.println("Overall result is ---> "+ov_list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Teachers");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                        {
                            if(dataSnapshot2.getKey().equals(Fragment_aptigo_results.testname_selected))
                            {
                                for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren())
                                {
                                    System.out.println("Data retrieved is "+dataSnapshot3.getKey());
                                    AssessmentQuestionAdapter assessmentQuestionAdapter = dataSnapshot3.getValue(AssessmentQuestionAdapter.class);
                                    //System.out.println("Retrieved results are ---> "+assessmentQuestionAdapter.getQuestion());
                                    viewansAdapters.add(new AssessmentQuestionAdapter(assessmentQuestionAdapter.getOp1(),assessmentQuestionAdapter.getOp2(),assessmentQuestionAdapter.getOp3(),assessmentQuestionAdapter.getOp4(),assessmentQuestionAdapter.getCorrectOP(),assessmentQuestionAdapter.getQuestion()));
                                }

                            }
                        }
                    }
                    via2=new ViewansItemAdapter(R.layout.testresultcard,viewansAdapters,ov_list);
                    rc3.setAdapter(via2);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}
