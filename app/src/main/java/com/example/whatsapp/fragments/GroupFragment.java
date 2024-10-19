package com.example.whatsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp.Adapters.GroupAdapter;
import com.example.whatsapp.R;
import com.example.whatsapp.databinding.FragmentGroupBinding;
import com.example.whatsapp.madels.GroupModel;
import com.example.whatsapp.madels.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroupFragment extends Fragment {
    FragmentGroupBinding binding;
    FirebaseDatabase database;
    ArrayList<GroupModel> groupList = new ArrayList<>();
    public GroupFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGroupBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();

        GroupAdapter groupAdapter = new GroupAdapter(groupList,getContext());
        binding.groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.groupRecyclerView.setAdapter(groupAdapter);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("MyGroups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear();
                for (DataSnapshot dataSnapshot  : snapshot.getChildren()){
                    GroupModel groupModel = dataSnapshot.getValue(GroupModel.class);
                    groupModel.setGroupName(dataSnapshot.getKey());
                    groupList.add(groupModel);
                }
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}