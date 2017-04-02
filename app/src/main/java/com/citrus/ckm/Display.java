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


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import static com.citrus.ckm.Commands.*;
import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Tools.*;


public class Display extends Fragment
{

    CardView display_profile_card, brightness_card, contrast_card, saturation_card;
    Switch apply_on_boot_switch;
    TextView display_profile_text, red_value_text, green_value_text, blue_value_text, brigthness_text, saturation_text, contrast_text;
    SeekBar red_bar, green_bar, blue_bar, brigthness_bar, saturation_bar, contrast_bar;

    String display_profile_string;
    int display_profile_status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_display, container, false);

        apply_on_boot_switch = (Switch) rootView.findViewById(R.id.apply_on_boot_display_switch);

        display_profile_card = (CardView) rootView.findViewById(R.id.display_profile_card);
        brightness_card = (CardView) rootView.findViewById(R.id.brightness_card);
        contrast_card = (CardView) rootView.findViewById(R.id.contrast_card);
        saturation_card = (CardView) rootView.findViewById(R.id.saturation_card);

        display_profile_text = (TextView) rootView.findViewById(R.id.display_profile_text);
        red_value_text = (TextView) rootView.findViewById(R.id.red_value_text);
        green_value_text = (TextView) rootView.findViewById(R.id.green_value_text);
        blue_value_text = (TextView) rootView.findViewById(R.id.blue_value_text);
        brigthness_text = (TextView) rootView.findViewById(R.id.brightness_value_text);
        contrast_text = (TextView) rootView.findViewById(R.id.contrast_value_text);
        saturation_text = (TextView) rootView.findViewById(R.id.saturation_value_text);

        red_bar = (SeekBar) rootView.findViewById(R.id.red_bar);
        green_bar = (SeekBar) rootView.findViewById(R.id.green_bar);
        blue_bar = (SeekBar) rootView.findViewById(R.id.blue_bar);
        brigthness_bar = (SeekBar) rootView.findViewById(R.id.brightness_bar);
        saturation_bar = (SeekBar) rootView.findViewById(R.id.saturation_bar);
        contrast_bar = (SeekBar) rootView.findViewById(R.id.contrast_bar);

        if (GetInt(getActivity(), APPLY_ON_BOOT_DISPLAY) == 1 )
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
        RefreshDisplayProfile();
        RefreshRGB();
        RefreshValue();
        RefreshContrast();
        RefrshSaturation();
    }

    public void RefreshDisplayProfile()
    {
        switch (GetInt(getActivity(), DISPLAY_PROFILE))
        {
            case 1:
                display_profile_string = "Cold";
                break;
            case 2:
                display_profile_string = "Warm";
                break;
            case 3:
                display_profile_string = "Custom";
                break;
            default:
                display_profile_string = "Stock";
        }
        display_profile_text.setText(display_profile_string);
    }

    public void RefreshRGB()
    {
        String rgb = Read(DISPLAY_RGB_PATH);
        String[] r_g_b = rgb.split(" ");
        String r_string = r_g_b[0];
        String g_string = r_g_b[1];
        String b_string = r_g_b[2];
        red_value_text.setText(r_string);
        green_value_text.setText(g_string);
        blue_value_text.setText(b_string);
        int r_int = Integer.parseInt(r_string);
        int g_int = Integer.parseInt(g_string);
        int b_int = Integer.parseInt(b_string);
        red_bar.setProgress(r_int - 50);
        green_bar.setProgress(g_int - 50);
        blue_bar.setProgress(b_int - 50);
        PutInt(getActivity(), DISPLAY_RED, r_int);
        PutInt(getActivity(), DISPLAY_GREEN, g_int);
        PutInt(getActivity(), DISPLAY_BLUE, b_int);
    }

    public void RefreshValue()
    {
        if(Exist(DISPLAY_SATURATION_PATH))
        {
            String brightness_string = Read(DISPLAY_BRIGHTNESS_PATH);
            brigthness_text.setText(brightness_string);
            int brightness_int = Integer.parseInt(brightness_string);
            brigthness_bar.setProgress(brightness_int - 230);
            PutInt(getActivity(), DISPLAY_BRIGHTNESS, brightness_int);
        }
        else
        {
            brightness_card.setVisibility(View.GONE);
        }
    }

    public void RefreshContrast()
    {
        if(Exist(DISPLAY_CONTRAST_PATH))
        {
            String contrast_string = Read(DISPLAY_CONTRAST_PATH);
            contrast_text.setText(contrast_string);
            int contrast_int = Integer.parseInt(contrast_string);
            contrast_bar.setProgress(contrast_int - 230);
            PutInt(getActivity(), DISPLAY_CONTRAST, contrast_int);
        }
        else
        {
            contrast_card.setVisibility(View.GONE);
        }
    }

    public void RefrshSaturation()
    {
        if(Exist(DISPLAY_SATURATION_PATH))
        {
            String saturation_string = Read(DISPLAY_SATURATION_PATH);
            saturation_text.setText(saturation_string);
            int saturation_int = Integer.parseInt(saturation_string);
            saturation_bar.setProgress(saturation_int - 224);
            PutInt(getActivity(), DISPLAY_SATURATION, saturation_int);
        }
        else
        {
            saturation_card.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        apply_on_boot_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    PutInt(getActivity(), APPLY_ON_BOOT_DISPLAY, 1);
                    apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorOrange));
                }
                else
                {
                    PutInt(getActivity(), APPLY_ON_BOOT_DISPLAY, 0);
                    apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        display_profile_card.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                display_profile_status = GetInt(getActivity(), DISPLAY_PROFILE);

                builder.setTitle("Display Profile");
                CharSequence[] list = { "Stock", "Cold", "Warm" };
                builder.setItems(list, new DialogInterface.OnClickListener()
                {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                switch (which)
                                {
                                    case 0:
                                        display_profile_status = 0;
                                        display_profile_string = "Stock";
                                        break;
                                    case 1:
                                        display_profile_status = 1;
                                        display_profile_string = "Cold";
                                        break;
                                    case 2:
                                        display_profile_status = 2;
                                        display_profile_string = "Warm";
                                        break;
                                }
                                display_profile_text.setText(display_profile_string);
                                PutInt(getActivity(), DISPLAY_PROFILE, display_profile_status);
                                SetProfile(display_profile_status);

                                new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Refresh();
                                    }
                                }, 500);
                                Refresh();
                                dialog.dismiss();
                            }
                });
                builder.create().show();
            }
        });

        red_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int r = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                r = progresValue + 50;
                String r_string = "" + r;
                red_value_text.setText(r_string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), DISPLAY_RED, r);
                int g = GetInt(getActivity(), DISPLAY_GREEN);
                int b = GetInt(getActivity(), DISPLAY_BLUE);
                Echo(r + " " + g + " " + b, DISPLAY_RGB_PATH);
                display_profile_text.setText("Custom");
                PutInt(getActivity(), DISPLAY_PROFILE, 3);
            }
        });

        green_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int g = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                g = progresValue + 50;
                String g_string = "" + g;
                green_value_text.setText(g_string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), DISPLAY_GREEN, g);
                int r = GetInt(getActivity(), DISPLAY_RED);
                int b = GetInt(getActivity(), DISPLAY_BLUE);
                Echo(r + " " + g + " " + b, DISPLAY_RGB_PATH);
                display_profile_text.setText("Custom");
                PutInt(getActivity(), DISPLAY_PROFILE, 3);
            }
        });

        blue_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int b = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                b = progresValue + 50;
                String b_string = "" + b;
                blue_value_text.setText(b_string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), DISPLAY_BLUE, b);
                int r = GetInt(getActivity(), DISPLAY_RED);
                int g = GetInt(getActivity(), DISPLAY_GREEN);
                Echo(r + " " + g + " " + b, DISPLAY_RGB_PATH);
                display_profile_text.setText("Custom");
                PutInt(getActivity(), DISPLAY_PROFILE, 3);
            }
        });

        brigthness_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int br = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                br = progresValue + 230;
                String br_string = "" + br;
                brigthness_text.setText(br_string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), DISPLAY_BRIGHTNESS, br);
                Echo(String.valueOf(br), DISPLAY_BRIGHTNESS_PATH);
                display_profile_text.setText("Custom");
                PutInt(getActivity(), DISPLAY_PROFILE, 3);
            }
        });

        contrast_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int co = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                co = progresValue +230;
                String co_string = "" + co;
                contrast_text.setText(co_string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), DISPLAY_CONTRAST, co);
                Echo(String.valueOf(co), DISPLAY_CONTRAST_PATH);
                display_profile_text.setText("Custom");
                PutInt(getActivity(), DISPLAY_PROFILE, 3);
            }
        });

        saturation_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int sa = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                sa = progresValue +224;
                String sa_string = "" + sa;
                saturation_text.setText(sa_string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), DISPLAY_SATURATION, sa);
                Echo(String.valueOf(sa), DISPLAY_SATURATION_PATH);
                display_profile_text.setText("Custom");
                PutInt(getActivity(), DISPLAY_PROFILE, 3);
            }
        });
    }

    public void SetProfile(int display_profile_status)
    {
        int r = 0;
        int g = 0;
        int b = 0;
        int br = 0;
        int co = 0;
        int sa = 0;

        switch (display_profile_status)
        {
            case 0:
                r = b = g = 256;
                br = co = sa = 255;
                break;
            case 1:
                r = 226;
                g = 236;
                b = 256;
                br = co = 255;
                sa = 265;
                break;
            case 2:
                r = 256;
                g = 236;
                b = 226;
                br = co = 255;
                sa = 265;
                break;
        }
        Echo(r + " " + g + " " + b, DISPLAY_RGB_PATH);
        Echo(String.valueOf(br), DISPLAY_BRIGHTNESS_PATH);
        Echo(String.valueOf(co), DISPLAY_CONTRAST_PATH);
        Echo(String.valueOf(sa), DISPLAY_SATURATION_PATH);
    }
}
