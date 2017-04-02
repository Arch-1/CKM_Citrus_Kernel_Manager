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
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Tools.*;
import static com.citrus.ckm.Commands.*;

public class Audio extends Fragment
{
    Switch apply_on_boot_switch, link_a_switch, link_d_switch;
    SeekBar mic_bar, speaker_bar, left_a_bar, right_a_bar, left_d_bar, right_d_bar;
    TextView mic_text, speaker_text, left_a_text, right_a_text, left_d_text, right_d_text;
    CardView mic_card, speaker_card, digital_card;

    int mic_int_real, speaker_int_real, left_a_int_real, right_a_int_real, left_d_int_real, right_d_int_real;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_audio, container, false);

        apply_on_boot_switch = (Switch) rootView.findViewById(R.id.apply_on_boot_audio_switch);
        link_a_switch = (Switch) rootView.findViewById(R.id.link_a_switch);
        link_d_switch = (Switch) rootView.findViewById(R.id.link_d_switch);

        mic_bar = (SeekBar) rootView.findViewById(R.id.mic_bar);
        speaker_bar = (SeekBar) rootView.findViewById(R.id.speaker_bar);
        left_a_bar = (SeekBar) rootView.findViewById(R.id.left_a_bar);
        right_a_bar = (SeekBar) rootView.findViewById(R.id.right_a_bar);
        left_d_bar = (SeekBar) rootView.findViewById(R.id.left_d_bar);
        right_d_bar = (SeekBar) rootView.findViewById(R.id.right_d_bar);

        mic_text = (TextView) rootView.findViewById(R.id.mic_value_text);
        speaker_text = (TextView) rootView.findViewById(R.id.speaker_value_text);
        left_a_text = (TextView) rootView.findViewById(R.id.left_a_value_text);
        right_a_text = (TextView) rootView.findViewById(R.id.rigth_a_value_text);
        left_d_text = (TextView) rootView.findViewById(R.id.left_d_value_text);
        right_d_text = (TextView) rootView.findViewById(R.id.rigth_d_value_text);

        mic_card = (CardView) rootView.findViewById(R.id.mic_card);
        speaker_card = (CardView) rootView.findViewById(R.id.speaker_card);
        digital_card = (CardView) rootView.findViewById(R.id.digital_card);

        if (GetInt(getActivity(), APPLY_ON_BOOT_AUDIO) == 1 )
        {
            apply_on_boot_switch.setChecked(true);
            apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorOrange));
        }

        Refresh();

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Refresh();
    }

    public void Refresh()
    {
        RefreshMic();
        RefreshSpeaker();
        RefreshAnalog();
        RefreshDigital();
    }

    public void RefreshMic()
    {
        if (Exist(AUDIO_MIC_PATH))
        {
            String mic_string_real = Read(AUDIO_MIC_PATH);
            int mic_int_virtual = 0;
            mic_int_real = Integer.parseInt(mic_string_real);
            if (mic_int_real >= 0 && mic_int_real <= 20)
            {
                mic_int_virtual = mic_int_real;
            }
            else
            {
                mic_int_virtual = -1 * (256 - mic_int_real);
            }
            String mic_string_virtual = "" + mic_int_virtual;
            mic_text.setText(mic_string_virtual + "dB");
            mic_bar.setProgress(mic_int_virtual + 10);
            PutInt(getActivity(), AUDIO_MIC, mic_int_real);
        }
        else
        {
            mic_card.setVisibility(View.GONE);
        }
    }

    public void RefreshSpeaker()
    {
        if(Exist(AUDIO_SPEAKER_PATH))
        {
            String speaker_string_real = Read(AUDIO_SPEAKER_PATH);
            int speaker_int_virtual = 0;
            speaker_int_real = Integer.parseInt(speaker_string_real);
            if (speaker_int_real >= 0 && speaker_int_real <= 20 ) {
                speaker_int_virtual = speaker_int_real;
            }
            else {
                speaker_int_virtual = -1*(256 - speaker_int_real);
            }
            String speaker_string_virtual = "" + speaker_int_virtual;
            speaker_text.setText(speaker_string_virtual + "dB");
            speaker_bar.setProgress(speaker_int_virtual + 10);
            PutInt(getActivity(), AUDIO_SPEAKER, speaker_int_real);
        }
        else
        {
            speaker_card.setVisibility(View.GONE);
        }
    }

    public void RefreshAnalog()
    {
        String analog_string = Read(AUDIO_A_PATH);
        String[] analog_string_arr = analog_string.split(" ");
        String l_a_string = analog_string_arr[0];
        String r_a_string = analog_string_arr[1];
        int l_a_int_real = Integer.parseInt(l_a_string);
        int r_a_int_real = Integer.parseInt(r_a_string);
        int lx = -1;
        int rx = -1;
        if (l_a_int_real == 1) {
            lx = 1;
        }
        if (r_a_int_real == 1) {
            rx = 1;
        }
        double l_a_double_virtual = (l_a_int_real - 1) * 1.5 * lx;
        double r_a_double_virtual = (r_a_int_real - 1) * 1.5 * rx;
        String l_a_string_virtual = String.valueOf(l_a_double_virtual);
        String r_a_string_virtual = String.valueOf(r_a_double_virtual);
        left_a_text.setText(l_a_string_virtual + "dB");
        right_a_text.setText(r_a_string_virtual + "dB");
        left_a_bar.setProgress(20 - l_a_int_real);
        right_a_bar.setProgress(20 - r_a_int_real);
        PutInt(getActivity(), AUDIO_LEFT_A, l_a_int_real);
        PutInt(getActivity(), AUDIO_RIGHT_A, r_a_int_real);

        if (GetInt(getActivity(), AUDIO_LINK_A) == 1) {
            link_a_switch.setChecked(true);
        }
    }

    public void RefreshDigital()
    {
        if(Exist(AUDIO_D_PATH))
        {
            String digital_string = Read(AUDIO_D_PATH);
            String[] digital_string_arr = digital_string.split(" ");
            String l_d_string = digital_string_arr[0];
            String r_d_string = digital_string_arr[1];
            int l_d_int_real = Integer.parseInt(l_d_string);
            int r_d_int_real = Integer.parseInt(r_d_string);
            int l_d_int_virtual = 0;
            int r_d_int_virtual = 0;
            if (l_d_int_real >= 0 && l_d_int_real <= 20 ) {
                l_d_int_virtual = l_d_int_real;
            }
            else {
                l_d_int_virtual = -1*(256 - l_d_int_real);
            }
            if (r_d_int_real >= 0 && r_d_int_real <= 20 ) {
                r_d_int_virtual = r_d_int_real;
            }
            else {
                r_d_int_virtual = -1*(256 - r_d_int_real);
            }
            String l_d_string_virtual = "" + l_d_int_virtual;
            String r_d_string_virtual = "" + r_d_int_virtual;
            left_d_text.setText(l_d_string_virtual + "dB");
            right_d_text.setText(r_d_string_virtual + "dB");
            left_d_bar.setProgress(l_d_int_virtual + 10);
            right_d_bar.setProgress(r_d_int_virtual + 10);
            PutInt(getActivity(), AUDIO_LEFT_D, l_d_int_real);
            PutInt(getActivity(), AUDIO_RIGHT_D, r_d_int_real);

            if (GetInt(getActivity(), AUDIO_LINK_D) == 1) {
                link_d_switch.setChecked(true);
            }
        }
        else
        {
            digital_card.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        apply_on_boot_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    PutInt(getActivity(), APPLY_ON_BOOT_AUDIO, 1);
                    apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorOrange));
                }
                else {
                    PutInt(getActivity(), APPLY_ON_BOOT_AUDIO, 0);
                    apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        mic_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                int mic_int_virtual = progresValue - 10;
                String mic_int_virtual_string = "" + mic_int_virtual;
                mic_text.setText(mic_int_virtual_string + "dB");
                mic_int_real = progresValue - 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PutInt(getActivity(), AUDIO_MIC, mic_int_real);
                Commands.Echo(String.valueOf(mic_int_real), AUDIO_MIC_PATH);
            }
        });

        speaker_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                int speaker_int_virtual = progresValue - 10;
                String speaker_int_virtual_string = "" + speaker_int_virtual;
                speaker_text.setText(speaker_int_virtual_string + "dB");
                speaker_int_real = progresValue - 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PutInt(getActivity(), AUDIO_SPEAKER, speaker_int_real);
                Commands.Echo(String.valueOf(speaker_int_real), AUDIO_SPEAKER_PATH);
            }
        });


        left_a_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                left_a_int_real = 20 - progresValue;
                int lx = -1;
                if (left_a_int_real == 1) {
                    lx = 1;
                }
                double left_a_double_virtual = (left_a_int_real - 1) * 1.5 * lx;
                String left_a_string_virtual = String.valueOf(left_a_double_virtual);
                left_a_text.setText(left_a_string_virtual + "dB");
                left_a_bar.setProgress(progresValue);

                if (link_a_switch.isChecked()) {
                    double right_a_double_virtual = (left_a_int_real -1) * 1.5 * lx;
                    String right_a_string_virtual = String.valueOf(right_a_double_virtual);
                    right_a_bar.setProgress(progresValue);
                    right_a_text.setText(right_a_string_virtual + "dB");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PutInt(getActivity(), AUDIO_LEFT_A, left_a_int_real);
                PutInt(getActivity(), AUDIO_RIGHT_A, right_a_int_real);
                Commands.Echo(left_a_int_real + " " + right_a_int_real, AUDIO_A_PATH);
            }
        });

        right_a_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                right_a_int_real = 20 - progresValue;
                int rx = -1;
                if (right_a_int_real == 1) {
                    rx = 1;
                }
                double right_a_double_virtual = (right_a_int_real - 1) * 1.5 * rx;
                String right_a_string_virtual = String.valueOf(right_a_double_virtual);
                right_a_text.setText(right_a_string_virtual + "dB");
                right_a_bar.setProgress(progresValue);

                if (link_a_switch.isChecked()) {
                    double left_a_double_virtual = (right_a_int_real -1) * 1.5 * rx;
                    String left_a_string_virtual = String.valueOf(left_a_double_virtual);
                    left_a_bar.setProgress(progresValue);
                    left_a_text.setText(left_a_string_virtual + "dB");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PutInt(getActivity(), AUDIO_LEFT_A, left_a_int_real);
                PutInt(getActivity(), AUDIO_RIGHT_A, right_a_int_real);
                Commands.Echo(left_a_int_real + " " + right_a_int_real, AUDIO_A_PATH);
            }
        });


        link_a_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    PutInt(getActivity(), AUDIO_LINK_A, 1);
                    left_a_int_real = GetInt(getActivity(), AUDIO_LEFT_A);
                    right_a_bar.setProgress(left_a_bar.getProgress());
                    right_a_text.setText(left_a_text.getText());
                    right_a_int_real = left_a_int_real;
                    Commands.Echo(left_a_int_real + " " + right_a_int_real, AUDIO_A_PATH);
                }
                else {
                    PutInt(getActivity(), AUDIO_LINK_A, 0);
                }
            }
        });

        left_d_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                right_d_int_real = GetInt(getActivity(), AUDIO_RIGHT_D);
                left_d_int_real = progresValue - 10;
                String left_d_virtual_string = "" + left_d_int_real;
                left_d_text.setText(left_d_virtual_string + "dB");
                if (link_d_switch.isChecked()) {
                    right_d_int_real = left_d_int_real;
                    right_d_text.setText(left_d_virtual_string + "dB");
                    right_d_bar.setProgress(progresValue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PutInt(getActivity(), AUDIO_LEFT_D, left_d_int_real);
                PutInt(getActivity(), AUDIO_RIGHT_D, right_d_int_real);
                Commands.Echo(left_d_int_real + " " + right_d_int_real, AUDIO_D_PATH);
            }
        });

        right_d_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                left_d_int_real = GetInt(getActivity(), AUDIO_RIGHT_D);
                right_d_int_real = progresValue - 10;
                String right_d_virtual_string = "" + right_d_int_real;
                right_d_text.setText(right_d_virtual_string + "dB");
                if (link_d_switch.isChecked()) {
                    left_d_int_real = right_d_int_real;
                    left_d_text.setText(right_d_virtual_string + "dB");
                    left_d_bar.setProgress(progresValue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PutInt(getActivity(), AUDIO_LEFT_D, left_d_int_real);
                PutInt(getActivity(), AUDIO_RIGHT_D, right_d_int_real);
                Commands.Echo(left_d_int_real + " " + right_d_int_real, AUDIO_D_PATH);
            }
        });

        link_d_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    PutInt(getActivity(), AUDIO_LINK_D, 1);
                    left_d_int_real = GetInt(getActivity(), AUDIO_LEFT_D);
                    right_d_bar.setProgress(left_d_bar.getProgress());
                    right_d_text.setText(left_d_text.getText());
                    right_d_int_real = left_d_int_real;
                    Commands.Echo(left_d_int_real + " " + right_d_int_real, AUDIO_D_PATH);
                }
                else {
                    PutInt(getActivity(), AUDIO_LINK_D, 0);
                }
            }
        });
    }
}