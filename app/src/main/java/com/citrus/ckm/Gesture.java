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

import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Tools.*;

public class Gesture extends Fragment
{
    CardView s2w_card, s2s_card, vibe_card;
    Switch dt2w_switch, apply_on_boot_switch;
    TextView dt2w_text, s2w_text, s2s_text, vibe_percentage_text;
    SeekBar vibe_percentage_bar;

    int s2w_status;
    String s2w_string;

    int s2s_status;
    String s2s_string;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_gesture, container, false);

        dt2w_switch = (Switch) rootView.findViewById(R.id.dt2w_switch);
        apply_on_boot_switch = (Switch) rootView.findViewById(R.id.apply_on_boot_gesture_switch);

        s2w_card = (CardView) rootView.findViewById(R.id.s2w_card);
        s2s_card = (CardView) rootView.findViewById(R.id.s2s_card);
        vibe_card = (CardView) rootView.findViewById(R.id.vibe_card);

        dt2w_text = (TextView) rootView.findViewById(R.id.dt2w_text);
        s2w_text = (TextView) rootView.findViewById(R.id.s2w_text);
        s2s_text = (TextView) rootView.findViewById(R.id.s2s_text);
        vibe_percentage_text = (TextView) rootView.findViewById(R.id.vibe_percentage_text);

        vibe_percentage_bar = (SeekBar) rootView.findViewById(R.id.vibe_bar);

        if (GetInt(getActivity(), APPLY_ON_BOOT_GESTURE) == 1)
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
        RefreshDT2W();
        RefreshS2W();
        RefreshS2S();
        RefreshVibe();
    }

    public void RefreshDT2W()
    {
        if (Read(GESTURE_DT2W_PATH).equals("1"))
        {
            PutInt(getActivity(), GESTURE_DT2W, 1);
            dt2w_switch.setChecked(true);
            dt2w_text.setText("Enabled");
        }
        else
        {
            PutInt(getActivity(), GESTURE_DT2W, 0);
            dt2w_switch.setChecked(false);
            dt2w_text.setText("Disabled");
        }
    }

    public void RefreshS2W()
    {
        if (Exist(GESTURE_S2W_PATH))
        {
            switch (Read(GESTURE_S2W_PATH))
            {
                case "1":
                    s2w_string = "Rigth";
                    break;
                case "2":
                    s2w_string = "Left";
                    break;
                case "4":
                    s2w_string = "Up";
                    break;
                case "8":
                    s2w_string = "Down";
                    break;
                case "3":
                    s2w_string = "Rigth + Left";
                    break;
                case "12":
                    s2w_string = "Up + Down";
                    ;
                    break;
                case "15":
                    s2w_string = "All Directions";
                    break;
                default:
                    s2w_string = "Disabled";
            }
            s2w_text.setText(s2w_string);
            PutInt(getActivity(), GESTURE_S2W, Integer.parseInt(Read(GESTURE_S2W_PATH)));
        }
        else
        {
            s2w_card.setVisibility(View.GONE);
        }
    }

    public void RefreshS2S()
    {
        if (Exist(GESTURE_S2W_PATH))
        {
            switch (Read(GESTURE_S2S_PATH))
            {
                case "1":
                    s2s_string = "Rigth";
                    break;
                case "2":
                    s2s_string = "Left";
                    break;
                case "3":
                    s2s_string = "Rigth + Left";
                    break;
                default:
                    s2s_string = "Disabled";
            }
            s2s_text.setText(s2s_string);
            PutInt(getActivity(), GESTURE_S2S, Integer.parseInt(Read(GESTURE_S2S_PATH)));
        }
        else
        {
            s2s_card.setVisibility(View.GONE);
        }
    }

    public void RefreshVibe()
    {
        if (Exist(GESTURE_VIBE_PATH)) {
            String per = Read(GESTURE_VIBE_PATH);
            vibe_percentage_text.setText(per + "%");
            int Percentage = Integer.parseInt(per);
            vibe_percentage_bar.setProgress(Percentage);
            PutInt(getActivity(), GESTURE_VIBE, Integer.parseInt(per));
        }
        else
        {
            vibe_card.setVisibility(View.GONE);
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
                    PutInt(getActivity(), APPLY_ON_BOOT_GESTURE, 1);
                    apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorOrange));
                }
                else
                {
                    PutInt(getActivity(), APPLY_ON_BOOT_GESTURE, 0);
                    apply_on_boot_switch.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        dt2w_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    PutInt(getActivity(), GESTURE_DT2W, 1);
                    dt2w_text.setText("Enabled");
                    Commands.Echo(String.valueOf(1), GESTURE_DT2W_PATH);
                }
                else
                {
                    PutInt(getActivity(), GESTURE_DT2W, 0);
                    dt2w_text.setText("Disabled");
                    Commands.Echo(String.valueOf(0), GESTURE_DT2W_PATH);
                }
            }
        });

        s2w_card.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sweep To Wake");
                CharSequence[] list = {"Rigth", "Left", "Up", "Down", "Rigth + Left", "Up + Down", "All Directions", "Disabled" };
                builder.setItems(list, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case 0:
                                s2w_status = 1;
                                s2w_string = "Rigth";
                                break;
                            case 1:
                                s2w_status = 2;
                                s2w_string = "Left";
                                break;
                            case 2:
                                s2w_status = 4;
                                s2w_string = "Up";
                                break;
                            case 3:
                                s2w_status = 8;
                                s2w_string = "Down";
                                break;
                            case 4:
                                s2w_status = 3;
                                s2w_string = "Rigth + Left";
                                break;
                            case 5:
                                s2w_status = 12;
                                s2w_string = "Up + Down";
                                break;
                            case 6:
                                s2w_status = 15;
                                s2w_string = "All Directions";
                                break;
                            case 7:
                                s2w_status = 0;
                                s2w_string = "Disabled";
                                break;
                        }
                        s2w_text.setText(s2w_string);
                        Commands.Echo(String.valueOf(s2w_status), GESTURE_S2W_PATH);
                        PutInt(getActivity(), GESTURE_S2W, s2w_status);
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        s2s_card.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                s2s_status = GetInt(getActivity(), GESTURE_S2S);
                builder.setTitle("Sweep To Sleep");
                CharSequence[] list = {"Rigth", "Left", "Rigth + Left", "Disabled" };
                builder.setItems(list, new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case 0:
                                s2s_status = 1;
                                s2s_string = "Rigth";
                                break;
                            case 1:
                                s2s_status = 2;
                                s2s_string = "Left";
                                break;
                            case 2:
                                s2s_status = 3;
                                s2s_string = "Rigth + Left";
                                break;
                            case 3:
                                s2s_status = 0;
                                s2s_string = "Disabled";
                                break;
                        }
                        s2s_text.setText(s2s_string);
                        Commands.Echo(String.valueOf(s2s_status), GESTURE_S2W_PATH);
                        PutInt(getActivity(), GESTURE_S2S, s2s_status);
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        vibe_percentage_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int per = 0;

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser)
            {
                per = progresValue;
                String per_string = "" + per;
                vibe_percentage_text.setText(per_string + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                PutInt(getActivity(), GESTURE_VIBE, per);
                Commands.Echo(String.valueOf(per), GESTURE_VIBE_PATH);
            }
        });
    }
}
