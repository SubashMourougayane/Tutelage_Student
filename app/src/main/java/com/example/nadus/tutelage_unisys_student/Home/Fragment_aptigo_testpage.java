package com.example.nadus.tutelage_unisys_student.Home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentCardAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.OverallResult;
import com.example.nadus.tutelage_unisys_student.Adapters.TestAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.Testpageadapter;
import com.example.nadus.tutelage_unisys_student.Adapters.dummy;
import com.example.nadus.tutelage_unisys_student.Adapters.TestItemAdapter;

import com.example.nadus.tutelage_unisys_student.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nadus on 21-12-2017.
 */

public class Fragment_aptigo_testpage extends Fragment {

    String temp_testname;
    Calligrapher calligrapher;

    ProgressDialog progressDialog;
    String duration_split[];
    String duration[];
    TextView timer_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    public int c=0;
    public ArrayList<Testpageadapter> testadap=new ArrayList<Testpageadapter>();
    public ArrayList<String> correctanswer=new ArrayList<String>();
    private String univ_name;
    private String mailsplit;
    String testname;
    Button submit;
    int duration_test;
    String staff_name;
    private String u_regno;

    public static Fragment_aptigo_testpage newInstance() {
        Fragment_aptigo_testpage fragment = new Fragment_aptigo_testpage();
        return fragment;
    }

    int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_aptigo_testpage, container, false);


        SharedPreferences prefs = getActivity().getSharedPreferences("Tutelage_Student", MODE_PRIVATE);
        univ_name = prefs.getString("univ_name", "");
        u_regno = prefs.getString("u_regno", "");
        mailsplit = prefs.getString("mailsplit","");


        timer_text = (TextView) v.findViewById(R.id.timer_text);
        submit = (Button) v.findViewById(R.id.submit);

        calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(),"GlacialIndifference-Regular.ttf",true);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView =(RecyclerView)view.findViewById(R.id.testRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        staff_name = Fragment_aptigo_4.staff_name;
        testname = Fragment_aptigo_4.testname;
        new MyTask().execute();


//        testadap.add(new Testpageadapter("edfdf","er","er","er","er","er","er"));
//        testadap.add(new Testpageadapter("edfdf","er1","er2","er3","e4","er1","er7"));
//        testadap.add(new Testpageadapter("edfdf","er12","er13","er14","er15","er16","er17"));


        //recyclerView.setAdapter(ta);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure to finish the test?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new MyTask2().execute();
                        dialogInterface.dismiss();
                        Toast.makeText(getActivity(),"Assessment Done",Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getActivity(),HomeActivity.class);
                        startActivity(i2);
                        getActivity().finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void startTimer(int noOfMinutes) {
        CountDownTimer countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                timer_text.setText(hms);//set text
            }
            public void onFinish() {
                timer_text.setText("TIME'S UP!!"); //On finish change timer text
                Toast.makeText(getActivity(),"Assessment Done",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(),HomeActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }.start();

    }

    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Teachers").child(staff_name);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count1=0;
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        temp_testname = dataSnapshot1.getKey();
                        duration_split = dataSnapshot1.getKey().split("@");
                        duration = duration_split[3].split("_");
                        duration_test = ((Integer.parseInt(duration[0])*60)+Integer.parseInt(duration[1]));
                        System.out.println("Duration is "+duration_test+" minutes");

                        if(dataSnapshot1.getKey().contains(testname))
                        {
                            System.out.println("Test name is ---> "+testname);
                            count = (int)dataSnapshot1.getChildrenCount();
                            System.out.println("Count is ---> "+count);
                            System.out.println("Printing 1 ---> "+dataSnapshot1.getKey());

                            for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                            {
                                System.out.println("Printing 2 ---> "+dataSnapshot2.getKey());

                                TestAdapter test=dataSnapshot2.getValue(TestAdapter.class);
                                //System.out.println("bow"+child.getKey());
                                System.out.println("Sample is "+test.getQuestion());
                                testadap.add(new Testpageadapter(test.getQuestion(),test.getOp1(),test.getOp2(),test.getOp3(),test.getOp4(),test.getCorrectOP()));
                                correctanswer.add(test.getCorrectOP());
//                                TestItemAdapter ta=new TestItemAdapter(R.layout.aptigo_testcard,testadap);
//                                recyclerView.setAdapter(ta);

                                    //count1++;
//                                Testpageadapter testpageadapter = dataSnapshot2.getValue(Testpageadapter.class);
//                                System.out.println("Values are "+testpageadapter.getQues()+" "+testpageadapter.getAnswer());
//                                testadap.add(new Testpageadapter(testpageadapter.getQues(),testpageadapter.getOption1(),testpageadapter.getOption2(),testpageadapter.getOption3(),testpageadapter.getOption4(),testpageadapter.getAnswer()));
                            }
                            break;
                        }
                    }
                    startTimer(duration_test*60*1000);
                    dummy.setCount1(count);
                    System.out.println("dummy.setCount is ---> "+count);
                   // System.out.println("count="+count1+"<---->"+testadap.get(4).getOption1());
                    TestItemAdapter ta=new TestItemAdapter(R.layout.aptigo_testcard,testadap);
                    recyclerView.setAdapter(ta);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }


    private class MyTask2 extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Students").child(u_regno).child(temp_testname);
            OverallResult or=new OverallResult();
            int count = 0;
            for(int i=0;i<dummy.getCount1();i++)
            {
                if((dummy.getAns().get(i)).equals(correctanswer.get(i)))
                {
                    count++;
                }
            }
            System.out.println("Total ---> "+count);
            or.setChosenAns(dummy.getAns());
            or.setTot(String.valueOf(count));
            databaseReference2.setValue(or);
            return null;
        }
    }

}
