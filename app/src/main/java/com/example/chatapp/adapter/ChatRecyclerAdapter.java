package com.example.chatapp.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Model.User;
import com.example.chatapp.R;

import java.util.ArrayList;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout chatContent;
        TextView name, last_msg, msg_time;
        ImageView contact_dp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatContent=itemView.findViewById(R.id.chatContent);
            contact_dp=itemView.findViewById(R.id.getUserImageinImageView);
            name=itemView.findViewById(R.id.username);
            last_msg=itemView.findViewById(R.id.lastMsg);
            msg_time=itemView.findViewById(R.id.msgTime);
        }
    }

    Context context;
    ArrayList <User> users;
    public ChatRecyclerAdapter(Context context, ArrayList<User> users) {
        this.context=context;
        this.users=users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chats, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ;
    }

}
