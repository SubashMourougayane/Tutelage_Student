package com.example.nadus.tutelage_unisys_student.Home;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentItemAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.ClassAdapter;
import com.example.nadus.tutelage_unisys_student.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nadus on 21-12-2017.
 */

public class Fragment_aptigo_results extends Fragment {

    Calligrapher calligrapher;
    TextView empty_text;
    RecyclerView recyclerView;
    ArrayList<AssessmentCardAdapter> list = new ArrayList<AssessmentCardAdapter>();
    private String[] FilePathString;
    private String[] FileNameString;
    private File[] listFile;
    File file;
    String[] subjects;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference_bef;
    Button onsdCard,updir;
    String classname = "IV_A";
    static String testname;
    static String staff_name;
    ArrayList<ClassAdapter> list1 = new ArrayList<ClassAdapter>();

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;
    ArrayList<String> stringArrayList = new ArrayList<>();

    ArrayList<ClassAdapter> uploadData=new ArrayList<>();
    private String univ_name;
    private String mailsplit;
    private String u_regno;
    public static String testname_selected;

    Button viewresults;
    public static Fragment_aptigo_results newInstance() {
        Fragment_aptigo_results fragment = new Fragment_aptigo_results();
        return fragment;
    }

    ArrayList<String> resultNodes2 = new ArrayList<>();

    ProgressDialog progressDialog;
    AssessmentItemAdapter assessmentItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_aptigo_results, container, false);

        empty_text = (TextView) v.findViewById(R.id.empty_text);
        calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(),"GlacialIndifference-Regular.ttf",true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Results...");
        progressDialog.show();

        SharedPreferences prefs = getActivity().getSharedPreferences("Tutelage_Student", MODE_PRIVATE);
        univ_name = prefs.getString("univ_name", "");
        u_regno = prefs.getString("u_regno", "");
        mailsplit = prefs.getString("mailsplit","");

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog.show();
       // new MyTask().execute();

//        list.add(new AssessmentCardAdapter("Unit Test 1","Data Structures"+" | "+"IV-A","02:30","12/08/18","40"+" \\ "+"60","Open"));
//        list.add(new AssessmentCardAdapter("Unit Test 2","Platform Technology"+" | "+"III-A","03:00","13/10/18","47"+" \\ "+"60","Open"));
//        list.add(new AssessmentCardAdapter("Unit Test 3","Mathematics 4"+" | "+"II-C","01:30","12/11/18","49"+" \\ "+"60","Open"));
//        list.add(new AssessmentCardAdapter("Unit Test 4","Communicative English"+" | "+"III-B","03:00","12/11/18","56"+" \\ "+"60","Open"));

        list.clear();

        new MyTask().execute();

        assessmentItemAdapter=new AssessmentItemAdapter(R.layout.assessment_card,list);
        recyclerView.setAdapter(assessmentItemAdapter);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        assessmentItemAdapter.setOnItemClickListener(new AssessmentItemAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(getActivity(), "Your results are loading... ", Toast.LENGTH_SHORT).show();
                testname_selected = resultNodes2.get(position);
                getFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_aptigo_results_page()).addToBackStack(null).commit();
            }
        });

    }

    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            System.out.println("Inside MyTask");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Students").child(u_regno);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        String value = dataSnapshot1.getKey();
                        resultNodes2.add(value);

                        String detail = dataSnapshot1.getKey();
                        String detail_split[] = detail.split("@");
                        list.add(new AssessmentCardAdapter(detail_split[0],detail_split[1],detail_split[2],detail_split[3],detail_split[4]));
                    }
                    assessmentItemAdapter=new AssessmentItemAdapter(R.layout.assessment_card,list);
                    recyclerView.setAdapter(assessmentItemAdapter);

                    System.out.println("Result nodes2 ---> "+resultNodes2);
                    progressDialog.dismiss();

                    if(list.isEmpty())
                    {
                        recyclerView.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty_text.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}
