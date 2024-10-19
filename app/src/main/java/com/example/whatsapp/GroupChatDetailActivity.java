package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.Adapters.GroupAdapter;
import com.example.whatsapp.Adapters.GroupChatAdapter;
import com.example.whatsapp.databinding.ActivityGroupChatDetailBinding;
import com.example.whatsapp.madels.MessageModel;
import com.example.whatsapp.madels.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatDetailActivity extends AppCompatActivity {
    ActivityGroupChatDetailBinding binding;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityGroupChatDetailBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();

        final Users[] currUser = new Users[1];

        String groupName = getIntent().getStringExtra("com.example.whatsapp.activity_group_chat_detail.groupName");
        String groupProfileImage = getIntent().getStringExtra("com.example.whatsapp.activity_group_chat_detail.groupProfileImage");

        Picasso.get().load(groupProfileImage).placeholder(R.drawable.avatar3).into(binding.chatDetailProfileImage);
        binding.chatDetailUserName.setText(groupName);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser[0] = snapshot.getValue(Users.class);
                currUser[0].setUserId(snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<MessageModel> messageModelArrayList = new ArrayList<>();
        GroupChatAdapter groupChatAdapter = new GroupChatAdapter(messageModelArrayList,this,groupName);
        binding.groupChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupChatRecyclerView.setAdapter(groupChatAdapter);

        database.getReference().child("GroupChats").child(groupName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModelArrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    model.setMessageId(snapshot1.getKey());
                    messageModelArrayList.add(model);
                }
                groupChatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.enterMessage.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(GroupChatDetailActivity.this, "Please enter some message", Toast.LENGTH_SHORT).show();
                }
                else{
                    final MessageModel messageModel = new MessageModel(FirebaseAuth.getInstance().getUid(),message,currUser[0].getUserName());
                    messageModel.setTimeStamp(new Date().getTime());
                    binding.enterMessage.setText("");
                    database.getReference().child("GroupChats").child(groupName).push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }
            }
        });
        setContentView(binding.getRoot());
    }
}