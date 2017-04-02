/*
 * Copyright (C) 2017-2018 Davide Di Battista
 *
 * This file is part of CKM.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.citrus.ckm;


import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Tools.*;

public class CKM extends AppCompatActivity
{
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new Info();
                case 1:
                    if (Exist(GESTURE_DT2W_PATH))
                    {
                        return new Gesture();
                    }
                    else
                    {
                        return new Empty();
                    }
                case 2:
                    if (Exist(DISPLAY_RGB_PATH))
                    {
                        return new Display();
                    }
                    else
                    {
                        return new Empty();
                    }
                case 3:
                    if (Exist(AUDIO_A_PATH))
                    {
                        return new Audio();
                    }
                    else
                    {
                        return new Empty();
                    }
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "INFO";
                case 1:
                    return "GESTURE";
                case 2:
                    return "DISPLAY";
                case 3:
                    return "AUDIO";
            }
            return null;
        }
    }
}
