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


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Tools.*;


public class Info extends Fragment
{
    TextView cpu0_current_text, cpu1_current_text, cpu2_current_text, cpu3_current_text, cpu4_current_text, cpu5_current_text, cpu6_current_text, cpu7_current_text;
    TextView cpu4_text, cpu5_text, cpu6_text, cpu7_text;
    TextView kernel_text, gpu_freq_current_text, mem_info_list_text, mem_info_value_text, temp_list_text, temp_list_current_text;
    ProgressBar cpu0_bar, cpu1_bar, cpu2_bar, cpu3_bar, cpu4_bar, cpu5_bar, cpu6_bar, cpu7_bar;
    int possible_cpus_int;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        cpu0_current_text = (TextView) rootView.findViewById(R.id.cpu0_current_text);
        cpu1_current_text = (TextView) rootView.findViewById(R.id.cpu1_current_text);
        cpu2_current_text = (TextView) rootView.findViewById(R.id.cpu2_current_text);
        cpu3_current_text = (TextView) rootView.findViewById(R.id.cpu3_current_text);
        cpu4_current_text = (TextView) rootView.findViewById(R.id.cpu4_current_text);
        cpu5_current_text = (TextView) rootView.findViewById(R.id.cpu5_current_text);
        cpu6_current_text = (TextView) rootView.findViewById(R.id.cpu6_current_text);
        cpu7_current_text = (TextView) rootView.findViewById(R.id.cpu7_current_text);
        cpu4_text = (TextView) rootView.findViewById(R.id.cpu4);
        cpu5_text = (TextView) rootView.findViewById(R.id.cpu5);
        cpu6_text = (TextView) rootView.findViewById(R.id.cpu6);
        cpu7_text = (TextView) rootView.findViewById(R.id.cpu7);

        kernel_text = (TextView) rootView.findViewById(R.id.kernel_text);

        gpu_freq_current_text = (TextView) rootView.findViewById(R.id.gpu_current_text);

        temp_list_text = (TextView) rootView.findViewById(R.id.temp_list_text);
        temp_list_current_text = (TextView) rootView.findViewById(R.id.temp_list_current_text);

        mem_info_list_text = (TextView) rootView.findViewById(R.id.mem_info_list_text);
        mem_info_value_text = (TextView) rootView.findViewById(R.id.mem_info_value_text);

        cpu0_bar = (ProgressBar) rootView.findViewById(R.id.cpu0_bar);
        cpu1_bar = (ProgressBar) rootView.findViewById(R.id.cpu1_bar);
        cpu2_bar = (ProgressBar) rootView.findViewById(R.id.cpu2_bar);
        cpu3_bar = (ProgressBar) rootView.findViewById(R.id.cpu3_bar);
        cpu4_bar = (ProgressBar) rootView.findViewById(R.id.cpu4_bar);
        cpu5_bar = (ProgressBar) rootView.findViewById(R.id.cpu5_bar);
        cpu6_bar = (ProgressBar) rootView.findViewById(R.id.cpu6_bar);
        cpu7_bar = (ProgressBar) rootView.findViewById(R.id.cpu7_bar);

        kernel_text.setText(ReadMulti(INFO_KERNEL_PATH));

        possible_cpus_int = CpuNumber();

        if ( possible_cpus_int <= 5 )
        {
            cpu6_bar.setVisibility(View.GONE);
            cpu6_current_text.setVisibility(View.GONE);
            cpu6_text.setVisibility(View.GONE);
            cpu7_bar.setVisibility(View.GONE);
            cpu7_current_text.setVisibility(View.GONE);
            cpu7_text.setVisibility(View.GONE);

            if ( possible_cpus_int <= 3 )
            {
                cpu4_bar.setVisibility(View.GONE);
                cpu4_current_text.setVisibility(View.GONE);
                cpu4_text.setVisibility(View.GONE);
                cpu5_bar.setVisibility(View.GONE);
                cpu5_current_text.setVisibility(View.GONE);
                cpu5_text.setVisibility(View.GONE);
            }
        }

        temp_list_text.setText(GetString(getContext(), INFO_TEMP));

        String mem_info = ReadMulti(INFO_RAM_PATH);
        String [] mem_info_arr = mem_info.trim().split("(:)|(\n)");
        int lnt1 = mem_info_arr.length;
        StringBuilder sb1 = new StringBuilder();

        for ( int i = 0 ; i < lnt1 ; i += 2 )
        {
            sb1.append(mem_info_arr[i] + "\n");
        }
        String mem_info_list = sb1.toString();
        mem_info_list_text.setText(mem_info_list);


        mHandler = new Handler();
        mHandler.post(Refresh);

        return rootView;
    }

    private Runnable Refresh = new Runnable()
    {
        public void run()
        {
            Refresh();
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mHandler.removeCallbacks(Refresh);
    }

    public void Refresh()
    {
        RefreshCPU();
        RefreshGPU();
        RefreshTemp();
        RefreshMem();
    }

    public void RefreshCPU()
    {
        cpu0_bar.setMax(GetMaxCPUFreq("0"));
        cpu1_bar.setMax(GetMaxCPUFreq("1"));
        cpu2_bar.setMax(GetMaxCPUFreq("2"));
        cpu3_bar.setMax(GetMaxCPUFreq("3"));
        cpu0_bar.setProgress(GetCPUFreqInt("0"));
        cpu0_current_text.setText(GetCPUFreqString("0"));
        cpu1_bar.setProgress(GetCPUFreqInt("1"));
        cpu1_current_text.setText(GetCPUFreqString("1"));
        cpu2_bar.setProgress(GetCPUFreqInt("2"));
        cpu2_current_text.setText(GetCPUFreqString("2"));
        cpu3_bar.setProgress(GetCPUFreqInt("3"));
        cpu3_current_text.setText(GetCPUFreqString("3"));

        if ( possible_cpus_int == 5 )
        {
            cpu4_bar.setMax(GetMaxCPUFreq("4"));
            cpu5_bar.setMax(GetMaxCPUFreq("5"));
            cpu4_bar.setProgress(GetCPUFreqInt("4"));
            cpu4_current_text.setText(GetCPUFreqString("4"));
            cpu5_bar.setProgress(GetCPUFreqInt("5"));
            cpu5_current_text.setText(GetCPUFreqString("5"));
        }
        if ( possible_cpus_int == 7 )
        {
            cpu6_bar.setMax(GetMaxCPUFreq("6"));
            cpu7_bar.setMax(GetMaxCPUFreq("7"));
            cpu6_bar.setProgress(GetCPUFreqInt("6"));
            cpu6_current_text.setText(GetCPUFreqString("6"));
            cpu7_bar.setProgress(GetCPUFreqInt("7"));
            cpu7_current_text.setText(GetCPUFreqString("7"));
        }
    }

    public void RefreshGPU()
    {
        String gpu = Read(INFO_GPU_PATH);
        double gpu_d = Integer.parseInt(gpu) / 1000000;
        int gpu_i = (int) gpu_d;
        gpu_freq_current_text.setText(gpu_i + "MHz");
    }

    public void RefreshTemp()
    {
        String [] sensors_list_arr = GetString(getContext(),INFO_TEMP).split("\n");
        int sensors_num = sensors_list_arr.length -1;
        StringBuilder sb_temp = new StringBuilder();

        for (int i = 0 ; i < sensors_num ; i++)
        {
            String temp = Read(INFO_TEMP_PATH + "/thermal_zone" + i + "/temp");
            sb_temp.append(ConvertTemp(temp) + "\n");
        }
        temp_list_current_text.setText(sb_temp.toString().trim());
    }

    public void RefreshMem()
    {
        String mem_info = ReadMulti(INFO_RAM_PATH);
        String [] mem_info_arr = mem_info.trim().split("(:)|(\n)");
        int lnt = mem_info_arr.length;
        StringBuilder sb_mem = new StringBuilder();

        for ( int i = 1 ; i < lnt ; i += 2 )
        {
            sb_mem.append(mem_info_arr[i] + "\n");
        }
        mem_info_value_text.setText(sb_mem.toString());
    }

    public int CpuNumber()
    {
        String possible_cpus = Read(INFO_POSSIBLE_CPUS_PATH);
        String[] possible_cpus_arr = possible_cpus.split("-");
        String max_cpus_string = possible_cpus_arr[1];

        return Integer.parseInt(max_cpus_string);
    }

    public String GetCPUFreqString(String n)
    {
        String current_string;
        int current_int = GetCPUFreqInt(n);

        if (current_int == 0)
        {
            current_string = "Offline";
        }
        else
        {
            current_string = current_int + "MHz";
        }
        return current_string;
    }

    public int GetCPUFreqInt(String n)
    {
        String current;
        int current_i;

        if (Read(INFO_CPU_PATH + "cpu" + n + "/online").equals("1"))
        {
            current = Read(INFO_CPU_PATH + "cpu" + n + "/cpufreq/scaling_cur_freq");
            double current_d = Integer.parseInt(current) / 1000;
            current_i = (int) current_d;
        }
        else
        {
            current_i = 0;
        }
        return current_i;
    }

    public int GetMaxCPUFreq(String n)
    {
        String freq_list;
        int max_i;

        if (Read(INFO_CPU_PATH + "cpu" + n + "/online").equals("1"))
        {
            freq_list = Read(INFO_CPU_PATH + "cpu" + n + "/cpufreq/scaling_available_frequencies");
            String [] freq_list_arr = freq_list.split(" ");
            String max_s = freq_list_arr[freq_list_arr.length -1];
            double max_d = Integer.parseInt(max_s) / 1000;
            max_i = (int) max_d;
        }
        else
        {
            max_i = 0;
        }
        return max_i;
    }

    public String ConvertTemp(String temp)
    {
        int temp_i;
        double temp_d;
        int scale;

        switch (temp.length())
        {
            case 3:
                scale = 10;
                break;
            case 4:
                scale = 100;
                break;
            case 5:
                scale = 1000;
                break;
            default:
                scale = 1;
        }
        temp_d = Integer.parseInt(temp) / scale;
        temp_i = (int) temp_d;

        return temp_i + "° C";
    }
}
