package com.example.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
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
        User user= users.get(position);
        holder.name.setText(user.getUser_name());
        Glide.with(context).load(user.getImage_link()).placeholder(R.drawable.profile).into(holder.contact_dp);

        holder.chatContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("receiver",user.getUser_name() );
                intent.putExtra("receiver_id", user.getUser_id());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
