package com.example.newsfeeds.TOIpages;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsfeeds.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class TOI extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    MyViewPagerAdaper myViewPagerAdaper;
    ArrayList<String>  titlesArrayList;
    ArrayList<Fragment> fragmentArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_t_o_i,container,false);

        tabLayout=(TabLayout)view.findViewById(R.id.toitab);
        viewPager=(ViewPager)view.findViewById(R.id.toipager);
        titlesArrayList=new ArrayList<>();
        fragmentArrayList=new ArrayList<>();
        getTitles();




        return this.view;
    }

    private void getTitles() {
        //here we get the title from the server
        titlesArrayList.add("Trending Stories");
        titlesArrayList.add("Top Stories");
        titlesArrayList.add("Latest stories");
        titlesArrayList.add("India");
        titlesArrayList.add("World");
        titlesArrayList.add("Business");
        titlesArrayList.add("Movies");
        titlesArrayList.add("Sports");
        titlesArrayList.add("Cricket");
        titlesArrayList.add("Tech");
        //  titlesArrayList.add("Auto");
        titlesArrayList.add("Health");
        titlesArrayList.add("South");
        titlesArrayList.add("People");

        for(int i=0;i<titlesArrayList.size();i++){
            fragmentArrayList.add(Toi_single.getInstance(titlesArrayList.get(i)));
        }
        setUpViewPager();
    }
    private void setUpViewPager() {
        myViewPagerAdaper = new MyViewPagerAdaper(getChildFragmentManager());
        viewPager.setAdapter(myViewPagerAdaper);
        tabLayout.setupWithViewPager(viewPager);

    }


    public class MyViewPagerAdaper extends FragmentPagerAdapter {

        public MyViewPagerAdaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentArrayList.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titlesArrayList.get(position);
        }

        @Override
        public int getCount() {
            return titlesArrayList.size();
        }
    }
}
