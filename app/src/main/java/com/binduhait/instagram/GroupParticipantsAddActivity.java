package com.binduhait.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.binduhait.instagram.Adapter.AdapterParticipantsAd;
import com.binduhait.instagram.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroupParticipantsAddActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ActionBar actionBar;
    FirebaseAuth firebaseAuth;
    String groupid,mygrprole;
    ArrayList<User> modelUsers;
    AdapterParticipantsAd participantsAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_participants_add);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Add Participants");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.usersadd);
        groupid=getIntent().getStringExtra("groupId");
        loadGroupInfo();

    }

    private void getAllUsers() {
        modelUsers=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelUsers.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    User users=dataSnapshot1.getValue(User.class);
                    if(!firebaseAuth.getUid().equals(users.getId())){
                        modelUsers.add(users);
                    }
                    participantsAd=new AdapterParticipantsAd(GroupParticipantsAddActivity.this,modelUsers,""+groupid,""+mygrprole);
                    recyclerView.setAdapter(participantsAd);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchusers(final String s)
    {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelUsers.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    User user=dataSnapshot1.getValue(User.class);
                    if(!user.getId().equals(firebaseUser.getUid())){
                        if(user.getFullname().toLowerCase().contains(s.toLowerCase())||
                                user.getUsername().toLowerCase().contains(s.toLowerCase())) {
                            modelUsers.add(user);
                        }
                    }
                    participantsAd=new AdapterParticipantsAd(GroupParticipantsAddActivity.this,modelUsers,""+groupid,""+mygrprole);
                    participantsAd.notifyDataSetChanged();
                    recyclerView.setAdapter(participantsAd);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadGroupInfo() {
        final DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Groups");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Groups");
        reference.orderByChild("grpId").equalTo(groupid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String groupId=""+ ds.child("grpId").getValue();
                    final String grptit=""+ds.child("grptitle").getValue();
                    String grpdesc=""+ds.child("grpdesc").getValue();
                    String grpicon=""+ds.child("grpicon").getValue();
                    String createdBy=""+ds.child("createBy").getValue();
                    String timestamp=""+ds.child("timestamp").getValue();
                    actionBar.setTitle("Add Participants");
                    reference1.child(groupId).child("Participants").child(firebaseAuth.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mygrprole=""+dataSnapshot.child("role").getValue();
                                    actionBar.setTitle(grptit + "(" + mygrprole + ")");
                                    getAllUsers();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.addparticipants).setVisible(false);
        menu.findItem(R.id.grpinfo).setVisible(false);
        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!TextUtils.isEmpty(query.trim())){
                    searchusers(query);
                }
                else {
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText.trim())){
                    searchusers(newText);
                }
                else {
                    getAllUsers();
                }
                return false;
            }
        });

        return true;
    }
}