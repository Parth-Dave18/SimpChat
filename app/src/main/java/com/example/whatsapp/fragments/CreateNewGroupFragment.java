package com.example.whatsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adapters.UsersAdapter;
import com.example.whatsapp.FrameActivity;
import com.example.whatsapp.MainActivity;
import com.example.whatsapp.databinding.FragmentChatsBinding;
import com.example.whatsapp.databinding.FragmentCreateNewGroupBinding;
import com.example.whatsapp.fragments.ChatsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.madels.GroupModel;
import com.example.whatsapp.madels.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class CreateNewGroupFragment extends Fragment {
    public CreateNewGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCreateNewGroupBinding binding;
    FirebaseDatabase database;
    ArrayList<Users> list = new ArrayList<>();
    public static ArrayList<Users> selectedMembers = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateNewGroupBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();
        UsersAdapter adapter = new UsersAdapter(list,getContext(),"CreateNewGroupFragment");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.createGroupContactList.setLayoutManager(layoutManager);
        binding.createGroupContactList.setAdapter(adapter);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.createGroupImgViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = binding.createGroupName.getText().toString();
                GroupModel group = new GroupModel(selectedMembers,FirebaseAuth.getInstance().getUid());
                binding.createGroupUserCount.setText(selectedMembers.size() + " no of users selected");
                if(groupName.isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Group Name",Toast.LENGTH_SHORT).show();
                }
                else if(selectedMembers.isEmpty()){
                    Toast.makeText(getActivity(), "No member selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    GroupModel groupAdmin = new GroupModel(selectedMembers,FirebaseAuth.getInstance().getUid());
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("MyGroups").child(groupName).setValue(group);
                    for (Users user : selectedMembers) {
                        database.getReference().child("Users").child(user.getUserId()).child("MyGroups").child(groupName).setValue(group);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        return binding.getRoot();
    }
//    public void refreshCount(){
//        binding.createGroupUserCount.setText(selectedMembers.size() + " no of users selected");
//    }


}