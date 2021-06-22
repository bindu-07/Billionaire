package com.binduhait.instagram.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.binduhait.instagram.Fragments.ChatListFragment;
import com.binduhait.instagram.Fragments.GroupChatFragment;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ChatListFragment chatListFragment = new ChatListFragment();
                return chatListFragment;
            case 1:
                GroupChatFragment groupChatFragment = new GroupChatFragment();
                return groupChatFragment;
//            case 2:
//                MovieFragment movieFragment = new MovieFragment();
//                return movieFragment;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
