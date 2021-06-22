package com.binduhait.instagram.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.binduhait.instagram.Adapter.AdapterChatList;
import com.binduhait.instagram.CreateGroupActivity;
import com.binduhait.instagram.MainActivity;
import com.binduhait.instagram.Model.ModelChat;
import com.binduhait.instagram.Model.ModelChatList;
import com.binduhait.instagram.Model.User;
import com.binduhait.instagram.R;
import com.binduhait.instagram.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<ModelChatList> chatListList;
    List<User> usersList;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    AdapterChatList adapterChatList;
    List<ModelChat> chatList;

    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.chatlistrecycle);
        chatListList = new ArrayList<>();
        chatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatListList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelChatList modelChatList = ds.getValue(ModelChatList.class);
                    if (!modelChatList.getId().equals(firebaseUser.getUid())) {
                        chatListList.add(modelChatList);
                    }

                }
                loadChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;

    }

    private void loadChats() {
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    for (ModelChatList chatList : chatListList) {
                        if (user.getId() != null && user.getId().equals(chatList.getId())) {
                            usersList.add(user);
                            break;
                        }
                    }
                    adapterChatList = new AdapterChatList(getActivity(), usersList);
                    recyclerView.setAdapter(adapterChatList);
                    for (int i = 0; i < usersList.size(); i++) {
                        lastMessage(usersList.get(i).getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void lastMessage(final String uid) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lastmess = "default";
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelChat chat = dataSnapshot1.getValue(ModelChat.class);
                    if (chat == null) {
                        continue;
                    }
                    String sender = chat.getSender();
                    String receiver = chat.getReceiver();
                    if (sender == null || receiver == null) {
                        continue;
                    }
                    if (chat.getReceiver().equals(firebaseUser.getUid()) &&
                            chat.getSender().equals(uid) ||
                            chat.getReceiver().equals(uid) &&
                                    chat.getSender().equals(firebaseUser.getUid())) {
                        if (chat.getType().equals("images")) {
                            lastmess = "Sent a Photo";
                        } else {
                            lastmess = chat.getMessage();
                        }
                    }

                }
                adapterChatList.setlastMessageMap(uid, lastmess);
                adapterChatList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User modelUsers = dataSnapshot1.getValue(User.class);
                    if (!modelUsers.getId().equals(firebaseUser.getUid())) {
                        usersList.add(modelUsers);
                    }
                    adapterChatList = new AdapterChatList(getActivity(), usersList);
                    recyclerView.setAdapter(adapterChatList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchusers(final String s) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User modelUsers = dataSnapshot1.getValue(User.class);
                    if (!modelUsers.getId().equals(firebaseUser.getUid())) {
                        if (modelUsers.getFullname().toLowerCase().contains(s.toLowerCase()) ||
                                modelUsers.getUsername().toLowerCase().contains(s.toLowerCase())) {
                            usersList.add(modelUsers);
                        }
                    }
                    adapterChatList = new AdapterChatList(getActivity(), usersList);
                    adapterChatList.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterChatList);
                    for (int i = 0; i < usersList.size(); i++) {
                        lastMessage(usersList.get(i).getId());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        firebaseAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        //menu.findItem(R.id.search).setVisible(false);

        //menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.addparticipants).setVisible(false);
        menu.findItem(R.id.grpinfo).setVisible(false);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (!TextUtils.isEmpty(query.trim())) {
//                    searchusers(query);
//                } else {
//                    getAllUsers();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (!TextUtils.isEmpty(newText.trim())) {
//                    searchusers(newText);
//                } else {
//                    getAllUsers();
//                }
//                return false;
//            }
//        });


        super.onCreateOptionsMenu(menu, inflater);
    }
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
////        if(item.getItemId()==R.id.logout){
////            firebaseAuth.signOut();
////            checkUserStatus();
////        }
////        else if(item.getItemId()==R.id.settings){
////            startActivity(new Intent(getActivity(), SettingsActivity.class));
////        }
//         if(item.getItemId()==R.id.craetegrp){
//            startActivity(new Intent(getActivity(), CreateGroupActivity.class));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//    private void checkUserStatus(){
//        FirebaseUser user=firebaseAuth.getCurrentUser();
//        if(user!=null){
//
//        }
//        else {
//            startActivity(new Intent(getActivity(), StartActivity.class));
//            getActivity().finish();
//        }
//    }


}