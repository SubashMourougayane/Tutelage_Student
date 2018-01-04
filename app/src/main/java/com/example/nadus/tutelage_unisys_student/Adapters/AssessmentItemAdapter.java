package com.example.nadus.tutelage_unisys_student.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nadus.tutelage_unisys_student.R;

import java.util.ArrayList;

/**
 * Created by nadus on 02-01-2018.
 */

public class AssessmentItemAdapter extends RecyclerView.Adapter<AssessmentItemAdapter.ViewHolder>{
    private int listItemLayout;
    public ArrayList<AssessmentCardAdapter> itemList;
    public static RecyclerView.RecyclerListener re;
    int pos;

    private static MyClickListener myClickListener;
    public AssessmentItemAdapter(int layoutId, ArrayList<AssessmentCardAdapter> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        TextView t_testname=holder.testname;
        TextView t_classname=holder.classname;
        TextView t_date=holder.date;
        TextView t_duration=holder.duration;
        TextView t_subjectname=holder.subjectname;
        CardView t_card=holder.card;

        t_testname.setText(itemList.get(position).getTestname());
        t_classname.setText(itemList.get(position).getClassname());
        t_duration.setText(itemList.get(position).getDuration());
        t_date.setText(itemList.get(position).getDate());
        t_subjectname.setText(itemList.get(position).getSubjectname());

//        item1.setText(itemList.get(position).getTestCata());
//        item2.setText((itemList.get(position).getTestdura())+" minutes");
//        item3.setText(itemList.get(position).getTestdate());
        pos=position;
        t_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(v.getContext(), "Clicked "+position+" "+itemList.get(position).getTestname(), Toast.LENGTH_SHORT).show();
                myClickListener.onItemClick(position, v);
            }
        });
        //String n=String.valueOf(itemList.get(position).getNq());

    }

    @Override
    public int getItemCount()
    {
        return itemList==null?0:itemList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView classname,subjectname,date,duration,testname,postedby;
        public CardView card;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            card=(CardView)itemView.findViewById(R.id.card);
            classname=(TextView)itemView.findViewById(R.id.classname);
            subjectname=(TextView)itemView.findViewById(R.id.subjectname);
            date=(TextView)itemView.findViewById(R.id.date);
            duration =(TextView)itemView.findViewById(R.id.duration);
            testname=(TextView)itemView.findViewById(R.id.testname);

        }

        @Override
        public void onClick(View v) {


        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
