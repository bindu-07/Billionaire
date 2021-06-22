package com.binduhait.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.binduhait.instagram.Fragments.ChatListFragment;
import com.binduhait.instagram.Fragments.GroupChatFragment;
import com.binduhait.instagram.Fragments.HomeFragment;
import com.binduhait.instagram.Fragments.NotificationFragment;
import com.binduhait.instagram.Fragments.ProfileFragment;
import com.binduhait.instagram.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    Fragment selectedfragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedfragment = new SearchFragment();
                            break;
//                        case R.id.nav_add:
//                            selectedfragment = null;
//                            startActivity(new Intent(MainActivity.this, PostActivity.class));
//                            break;
                        case R.id.nav_chat:
                            //actionBar.setTitle("Chats");
//                            ChatListFragment listFragment=new ChatListFragment();
//                            FragmentTransaction fragmentTransaction3=getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction3.replace(R.id.content,listFragment,"");
//                            fragmentTransaction3.commit();
//                            return true;
                            selectedfragment = null;
                            startActivity(new Intent(MainActivity.this, ChatHome.class));
                            break;

//                        case R.id.nav_grp_chat:
//                            //actionBar.setTitle("Chats");
////                            GroupChatFragment groupChatFragment=new GroupChatFragment();
////                            FragmentTransaction fragmentTransactionb=getSupportFragmentManager().beginTransaction();
////                            fragmentTransactionb.replace(R.id.content,groupChatFragment,"");
////                            fragmentTransactionb.commit();
////                            return true;
//                            selectedfragment = new GroupChatFragment();
//                            break;

                        case R.id.nav_heart:
                            selectedfragment = new NotificationFragment();
                            break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedfragment = new ProfileFragment();
                            break;
                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }

                    return true;
                }
            };
}