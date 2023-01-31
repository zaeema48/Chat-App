package com.example.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Model.Message;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {


    public class SenderViewHolder extends  RecyclerView.ViewHolder{
        TextView send_msg;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            send_msg= itemView.findViewById(R.id.message);

        }
    }

    public class ReceiverViewHolder extends  RecyclerView.ViewHolder{
        TextView receiver_msg;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiver_msg=itemView.findViewById(R.id.message);
        }
    }



    ArrayList<Message> messages;
    Context context;

    public MessageAdapter( Context context, ArrayList<Message> messages) {
        this.messages = messages;
        this.context = context;
    }

    int item_Sent=1;
    int item_received=2;

    @Override
    public int getItemViewType(int position){
        Message message=messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSender_id()))
            return item_Sent;
        else
            return item_received;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==item_Sent){
            View view = LayoutInflater.from(context).inflate(R.layout.sender, parent,  false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiver, parent,  false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder) holder).send_msg.setText(messages.get(position).getMessage());
        }
        else{
            ((ReceiverViewHolder) holder).receiver_msg.setText(messages.get(position).getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
