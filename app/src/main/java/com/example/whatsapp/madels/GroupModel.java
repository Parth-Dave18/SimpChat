package com.example.whatsapp.madels;

import java.util.ArrayList;

public class GroupModel {
    ArrayList<Users> memberUsers;
    String groupName;
    String profileImage;
    String lastMessage;

    public GroupModel() {
    }

    String admin;

    public GroupModel(ArrayList<Users> memberUsers, String groupName, String profileImage, String lastMessage, String admin) {
        this.memberUsers = memberUsers;
        this.groupName = groupName;
        this.profileImage = profileImage;
        this.lastMessage = lastMessage;
        this.admin = admin;
    }

    public GroupModel(ArrayList<Users> memberUsers, String groupName, String admin) {
        this.memberUsers = memberUsers;
        this.groupName = groupName;
        this.admin = admin;
    }

    public GroupModel(ArrayList<Users> memberUsers, String admin) {
        this.memberUsers = memberUsers;
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public ArrayList<Users> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(ArrayList<Users> memberUsers) {
        this.memberUsers = memberUsers;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
