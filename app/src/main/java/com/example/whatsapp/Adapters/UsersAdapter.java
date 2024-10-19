package com.example.whatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.ChatsDetailActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.fragments.CreateNewGroupFragment;
import com.example.whatsapp.madels.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context context;
    String parentClass;
    public UsersAdapter(ArrayList<Users> list, Context context,String parentClass) {
        this.list = list;
        this.context = context;
        this.parentClass = parentClass;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        if(parentClass == "CreateNewGroupFragment") {
            view.findViewById(R.id.contactListCheckBox).setVisibility(View.VISIBLE);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.userName.setText(users.getUserName());
        holder.lastMessage.setText("Last Message");

        if(parentClass == "CreateNewGroupFragment") {
            if(holder.checkBox.isChecked()){
                if(!CreateNewGroupFragment.selectedMembers.contains(users))
                    CreateNewGroupFragment.selectedMembers.add(users);
            }
            else if(!holder.checkBox.isChecked()) {
                CreateNewGroupFragment.selectedMembers.remove(users);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parentClass == "ChatsFragment") {
                    Intent intent = new Intent(context, ChatsDetailActivity.class);
                    intent.putExtra("com.example.whatsapp.activity_chat_detail.userId", users.getUserId());
                    intent.putExtra("com.example.whatsapp.activity_chat_detail.userName", users.getUserName());
                    intent.putExtra("com.example.whatsapp.activity_chat_detail.userProfilePic", users.getProfilePic());
                    context.startActivity(intent);
                }
                else if(parentClass == "CreateNewGroupFragment"){
                    if(holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(false);
                        CreateNewGroupFragment.selectedMembers.remove(users);
                    }
                    else if(!holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(true);
                        if(!CreateNewGroupFragment.selectedMembers.contains(users))
                            CreateNewGroupFragment.selectedMembers.add(users);
                    }
                }
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        if(parentClass == "CreateNewGroupFragment")
            holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView userName,lastMessage;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userNameList);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            checkBox = itemView.findViewById(R.id.contactListCheckBox);
        }
    }
}
