package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivitySettingsBinding;
import com.example.whatsapp.madels.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                binding.settingsProfileStatus.setText(user.getStatus());
                binding.settingsProfileEditName.setText(user.getUserName());
                Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(binding.settingsProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.settingsProfileUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String settingsUserName = binding.settingsProfileEditName.getText().toString();
                String settingsStatus = binding.settingsProfileStatus.getText().toString();
                if(settingsUserName.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Please Input Your Name !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("userName").setValue(settingsUserName);
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("status").setValue(settingsStatus);
                    finish();
                }
            }
        });

    }

}