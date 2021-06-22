package com.binduhait.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.binduhait.instagram.Adapter.MyAdapter;
import com.google.android.material.tabs.TabLayout;

public class ChatHome extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Messages");


        tabLayout.addTab(tabLayout.newTab().setText("Chats "));
        tabLayout.addTab(tabLayout.newTab().setText("Group Chats"));
        //tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
////        menu.findItem(R.id.add).setVisible(false);
//       // menu.findItem(R.id.search).setVisible(false);
//
//        //menu.findItem(R.id.search).setVisible(false);
//        menu.findItem(R.id.addparticipants).setVisible(false);
//        menu.findItem(R.id.grpinfo).setVisible(false);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.craetegrp){
            startActivity(new Intent(ChatHome.this, CreateGroupActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}