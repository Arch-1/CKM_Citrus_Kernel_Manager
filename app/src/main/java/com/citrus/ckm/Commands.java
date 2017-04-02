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


import android.content.Context;
import android.widget.Toast;

import static com.citrus.ckm.Tools.*;

class Commands
{
    static void Echo(String string, String path)
    {
        Run( new String [] { "echo " + string + " > " + path });
    }

    static void Enforce(Context context)
    {
        if (GetCommandOut( new String [] { "getenforce" }).equals("Enforcing"))
        {
            Run( new String [] { "setenforce 0" });
            Toast.makeText(context, "CKM set SELinux to Permessive for work", Toast.LENGTH_SHORT).show();
        }
    }
}
