package com.example.chatapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp.ChatFragment;
import com.example.chatapp.ProfileFragment;
import com.example.chatapp.StatusFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    int count; //number of tab items
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        count=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChatFragment();

            case 1:
                return new StatusFragment();

            case 2:
                return new ProfileFragment();

            default:
                return null;



        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
