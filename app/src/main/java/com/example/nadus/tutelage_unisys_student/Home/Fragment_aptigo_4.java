package com.example.nadus.tutelage_unisys_student.Home;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentQuestionAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.ClassAdapter;
import com.example.nadus.tutelage_unisys_student.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nadus on 21-12-2017.
 */

public class Fragment_aptigo_4  extends Fragment {

    ArrayList<String> resultNodes = new ArrayList<String>();
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
    String classname = "";
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

    Button viewresults;
    public static Fragment_aptigo_4 newInstance() {
        Fragment_aptigo_4 fragment = new Fragment_aptigo_4();
        return fragment;
    }

    ProgressDialog progressDialog;
    AssessmentItemAdapter assessmentItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_aptigo, container, false);

        empty_text = (TextView) v.findViewById(R.id.empty_text);
        viewresults = (Button) v.findViewById(R.id.viewresults);
        calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(),"GlacialIndifference-Regular.ttf",true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Tests...");
        progressDialog.show();

        SharedPreferences prefs = getActivity().getSharedPreferences("Tutelage_Student", MODE_PRIVATE);
        univ_name = prefs.getString("univ_name", "");
        u_regno = prefs.getString("u_regno", "");
        mailsplit = prefs.getString("mailsplit","");
        classname = prefs.getString("u_class", "");


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
        new CheckTask().execute();
       // new MyTask().execute();

        assessmentItemAdapter=new AssessmentItemAdapter(R.layout.assessment_card,list);
        recyclerView.setAdapter(assessmentItemAdapter);
        progressDialog.dismiss();

        viewresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_aptigo_results()).addToBackStack(null).commit();
            }
        });



    }

    @Override
    public void onResume()
    {
        super.onResume();
        assessmentItemAdapter.setOnItemClickListener(new AssessmentItemAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(getActivity(), "Clicked "+position, Toast.LENGTH_SHORT).show();
                testname = list.get(position).getTestname();
                System.out.println("Test name is "+testname);
                getFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_aptigo_testinfo()).addToBackStack(null).commit();
            }
        });

    }

    private class CheckTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            System.out.println("Inside Check Task");
            databaseReference_bef = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Students").child(u_regno);
            System.out.println("Path is "+databaseReference_bef);
            databaseReference_bef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        String value = dataSnapshot1.getKey();
                        resultNodes.add(value);
                    }
                    System.out.println("Results node ---> "+resultNodes);
                    new MyTask().execute();
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
            System.out.println("Inside MyTask");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(univ_name).child("Assessments").child("Teachers");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int i;
                    Boolean flag=true;

                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                        {
                            if(dataSnapshot2.getKey().contains(classname))
                            {
                                staff_name = dataSnapshot1.getKey();
                                String detail = dataSnapshot2.getKey();
                                for(i=0;i<resultNodes.size();i++)
                                {
                                    if(detail.equals(resultNodes.get(i))) {
                                        System.out.println("Result node is "+resultNodes.get(i)+" Detail is "+detail);
                                        flag = false;
                                    }
                                }
                                if(flag)
                                {
                                    String detail_split[] = detail.split("@");
                                    list.add(new AssessmentCardAdapter(detail_split[0],detail_split[1],detail_split[2],detail_split[3],detail_split[4]));
                                }
                                else
                                {
                                    flag=true;
                                }
                            }
                        }
                    }
                    assessmentItemAdapter=new AssessmentItemAdapter(R.layout.assessment_card,list);
                    recyclerView.setAdapter(assessmentItemAdapter);
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
