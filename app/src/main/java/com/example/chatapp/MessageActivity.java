package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        TextView receiver_name;
        ImageView back_button, send_button;
        EditText message;
        RecyclerView chat_scroll_recyclerView;

        receiver_name=findViewById(R.id.name);
        back_button=findViewById(R.id.imageView2);
        send_button=findViewById(R.id.sendBtn);
        message=findViewById(R.id.messageBox);
        chat_scroll_recyclerView=findViewById(R.id.recyclerView);

        String name= getIntent().getStringExtra("receiver");
        receiver_name.setText(name);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, HomeActivity.class );
                startActivity(intent);
            }
        });

        String receiver_id=getIntent().getStringExtra("receiver_id");
        String sender_id= FirebaseAuth.getInstance().getUid();  //we are senders
        String sender_room= sender_id+receiver_id;
        String receiver_room= receiver_id+sender_id;

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=message.getText().toString();
                Date date=new Date();
                long time=date.getTime();
                Message message = new Message(sender_id, msg, time);

            }
        });


    }
}