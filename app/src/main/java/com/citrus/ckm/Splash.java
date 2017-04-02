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


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Commands.*;
import static com.citrus.ckm.Tools.*;


public class Splash extends AppCompatActivity
{

    protected void onCreate(Bundle icicle)
    {
        super.onCreate(icicle );
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Start();
            }
        }, 200);
    }

    public void Start()
    {
        if (IsRoot())
        {
            GetTempItems();
            Enforce(getApplicationContext());

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.d("value", "CKM Root Permission OK");
                    Intent ckm = new Intent(getApplicationContext(), CKM.class);
                    startActivity(ckm);
                    Splash.this.finish();
                }
            }, 1300);
        }
        else
        {
            Log.e("value", "CKM Need Root Permission");
            Toast.makeText(getApplicationContext(), "CKM Need Root Permission", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Splash.this.finish();
                }
            }, 2000);
        }
    }

    public void GetTempItems()
    {
        if (GetString(getApplicationContext(), INFO_TEMP).equals(""))
        {
            File file = new File(INFO_TEMP_PATH);
            String[] directories = file.list(new FilenameFilter()
            {
                @Override
                public boolean accept(File current, String name)
                {
                    return new File(current, name).isDirectory();
                }
            });

            int sensors_num = directories.length;
            StringBuilder temp_items = new StringBuilder();

            for(int i = 0 ; i < sensors_num ; i++)
            {
                temp_items.append(Read(INFO_TEMP_PATH + "/thermal_zone" + i + "/type") + "\n");
            }
            PutString(getApplicationContext(), INFO_TEMP, temp_items.toString());
        }
    }
}


