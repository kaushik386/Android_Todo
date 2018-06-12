package com.todo.kaushik.todolist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyHolder>{

    List<TodoTask> mTodoList;

    Context mContext;
    interface ClickEvent
    {
     void callDetails(int adapterPosition);
    }

 void swap(List<TodoTask> todos)
 {
     mTodoList=todos;
 }

ClickEvent mClickevent;
    public TodoAdapter(Context mContext,ClickEvent clickEvent) {
        this.mContext = mContext;
        mClickevent=clickEvent;
    }
    private static final String DATE_FORMAT = "dd/MM/yyy";

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.listview,viewGroup,false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        if(mTodoList!=null)
        {
           TodoTask todoTask = mTodoList.get(i);

            String description = todoTask.getDescription();
            int priority = todoTask.getPriority();
            String updatedAt = dateFormat.format(todoTask.getUpdatedAt());

            //Set values


            // Programmatically set the text and color for the priority TextView
            String priorityString = "" + priority; // converts int to String
            myHolder.mPriority.setText(priorityString);

            GradientDrawable priorityCircle = (GradientDrawable) myHolder.mPriority.getBackground();
            // Get the appropriate background color based on the priority
            int priorityColor = getPriorityColor(priority);
            priorityCircle.setColor(priorityColor);
           myHolder.mDescription.setText(todoTask.getDescription());
           myHolder.mPriority.setText(todoTask.getPriority());
           myHolder.mDate.setText(updatedAt);

        }

    }
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }
    @Override
    public int getItemCount() {
        if(mTodoList==null)
            return 0;
        else
        return mTodoList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mPriority;
        TextView mDate;
        TextView mDescription;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mPriority = itemView.findViewById(R.id.priorityTextView);
            mDate = itemView.findViewById(R.id.taskUpdatedAt);
            mDescription = itemView.findViewById(R.id.taskDescription);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            
        mClickevent.callDetails(getAdapterPosition());
        }
    }

}
