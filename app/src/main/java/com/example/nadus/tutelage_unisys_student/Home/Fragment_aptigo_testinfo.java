package com.example.nadus.tutelage_unisys_student.Home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentQuestionAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentResultCardAdapter;
import com.example.nadus.tutelage_unisys_student.Adapters.AssessmentResultItemAdapter;
import com.example.nadus.tutelage_unisys_student.R;

import java.io.File;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by nadus on 21-12-2017.
 */

public class Fragment_aptigo_testinfo extends Fragment {

    Calligrapher calligrapher;
    long totalSeconds = 30;
    long intervalSeconds = 1;
    CountDownTimer timer;
    TextView tv;
    ProgressDialog progressDialog;
    Button start;
    TextView title;

    public static Fragment_aptigo_testinfo newInstance() {
        Fragment_aptigo_testinfo fragment = new Fragment_aptigo_testinfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_aptigo_testinfo, container, false);

        title = (TextView) v.findViewById(R.id.title);
        title.setText(Fragment_aptigo_4.testname);
        start = (Button) v.findViewById(R.id.start);
        tv = (TextView) v.findViewById(R.id.tv);
        calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(),"GlacialIndifference-Regular.ttf",true);

        timer= new CountDownTimer(25000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("You can begin your assessment in : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //tv.setText("All The Best!");
                //getFragmentManager().beginTransaction().replace(R.id.frame_layout,new AssessmentFragment()).commit();
                //view.getContext().startActivity(new Intent(view.getContext(),aaa_Test_Page.class));
            }
        };
        timer.start();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure to attend this assessment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timer.cancel();
                        getFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_aptigo_testpage()).commit();
                        dialogInterface.dismiss();
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

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



    }

}
