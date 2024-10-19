package com.example.whatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.GroupChatDetailActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.madels.GroupModel;
import com.example.whatsapp.madels.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    ArrayList<GroupModel> groups;
    Context context;

    public GroupAdapter(ArrayList<GroupModel> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupModel group = groups.get(position);
        Picasso.get().load(group.getProfileImage()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.groupName.setText(group.getGroupName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groupDetailIntent = new Intent(context, GroupChatDetailActivity.class);
                groupDetailIntent.putExtra("com.example.whatsapp.activity_group_chat_detail.groupName",group.getGroupName());
                groupDetailIntent.putExtra("com.example.whatsapp.activity_group_chat_detail.groupProfileImage",group.getProfileImage());
                context.startActivity(groupDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView groupName,lastMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profileImage);
            groupName = itemView.findViewById(R.id.userNameList);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}
