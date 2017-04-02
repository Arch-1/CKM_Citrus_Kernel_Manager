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


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.citrus.ckm.Strings.*;
import static com.citrus.ckm.Commands.*;

public class BootReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            int gesture = Tools.GetInt(context, APPLY_ON_BOOT_GESTURE);
            if( gesture == 1)
            {
                Gesture(context);
            }
            int display = Tools.GetInt(context, APPLY_ON_BOOT_DISPLAY);
            if( display == 1)
            {
                Display(context);
            }
            int audio = Tools.GetInt(context, APPLY_ON_BOOT_AUDIO);
            if( audio == 1)
            {
                Audio(context);
            }
            if(gesture + display + audio > 0)
            {
                Toast.makeText(context, "CKM Applied Settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Gesture(Context context)
    {
        int dt2w = Tools.GetInt(context, GESTURE_DT2W);
        if( dt2w == 1)
        {
            Echo(String.valueOf(1), GESTURE_DT2W_PATH);
        }
        int s2w = Tools.GetInt(context, GESTURE_S2W);
        if( s2w > 0 )
        {
            Echo(String.valueOf(s2w), GESTURE_S2W_PATH);
        }
        int s2s = Tools.GetInt(context, GESTURE_S2S);
        if( s2s > 0 )
        {
            Echo(String.valueOf(s2s), GESTURE_S2S_PATH);
        }
        int vibe = Tools.GetInt(context, GESTURE_VIBE);
        Echo(String.valueOf(vibe), GESTURE_VIBE_PATH);
    }

    public void Display(Context context)
    {
        int r = Tools.GetInt(context, DISPLAY_RED);
        int g = Tools.GetInt(context, DISPLAY_GREEN);
        int b = Tools.GetInt(context, DISPLAY_BLUE);
        int br = Tools.GetInt(context, DISPLAY_BRIGHTNESS);
        int co = Tools.GetInt(context, DISPLAY_CONTRAST);
        int sa = Tools.GetInt(context, DISPLAY_SATURATION);
        Echo(r + " " + g + " " + b, DISPLAY_RGB_PATH);
        Echo(String.valueOf(br), DISPLAY_BRIGHTNESS_PATH);
        Echo(String.valueOf(co), DISPLAY_CONTRAST_PATH);
        Echo(String.valueOf(sa), DISPLAY_SATURATION_PATH);
    }

    public void Audio(Context context)
    {
        int m = Tools.GetInt(context, AUDIO_MIC);
        int s = Tools.GetInt(context, AUDIO_SPEAKER);
        int la = Tools.GetInt(context, AUDIO_LEFT_A);
        int ra = Tools.GetInt(context, AUDIO_RIGHT_A);
        int ld = Tools.GetInt(context, AUDIO_LEFT_D);
        int rd = Tools.GetInt(context, AUDIO_RIGHT_D);
        Echo(String.valueOf(m), AUDIO_MIC_PATH);
        Echo(String.valueOf(s), AUDIO_SPEAKER_PATH);
        Echo(la + " " + ra, AUDIO_A_PATH);
        Echo(ld + " " + rd, AUDIO_D_PATH);
    }
}
