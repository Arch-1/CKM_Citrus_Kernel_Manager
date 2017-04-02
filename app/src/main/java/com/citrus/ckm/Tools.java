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
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Tools extends CKM
{

    static String TAG = "CKM";

    public static void Run(String[] command)
    {
        Process p = null;

        try
        {
            p = Runtime.getRuntime().exec( new String[] { "su", " -c " } );

        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e(TAG, " Error during SU execution ", e);
        }
        DataOutputStream os = null;

        if (p != null)
        {
            os = new DataOutputStream(p.getOutputStream());
        }
        for (String tmpCmd : command)
        {
            try
            {
                assert os != null;
                os.writeBytes(tmpCmd+"\n");
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Log.e(TAG, "Error during " + command +" Execution ", e);
            }
        }
        try
        {
            assert os != null;
            os.writeBytes("exit\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            os.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean IsRoot()
    {
        boolean is_root = false;
        Process suProcess;
        try
        {
            suProcess = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream osRes = new DataInputStream(suProcess.getInputStream());

            if (null != os && null != osRes)
            {
                os.writeBytes("id\n");
                os.flush();
                String currUid = osRes.readLine();
                boolean exitSu = false;

                if (null == currUid)
                {
                    is_root = false;
                    exitSu = false;
                    Log.d(TAG, "Can't get root access or denied by user");
                }
                else if (true == currUid.contains("uid=0"))
                {
                    is_root = true;
                    exitSu = true;
                    Log.d(TAG, "Root access granted");
                }
                else
                {
                    is_root = false;
                    exitSu = true;
                    Log.d(TAG, "Root access rejected: " + currUid);
                }
                if (exitSu)
                {
                    os.writeBytes("exit\n");
                    os.flush();
                }
            }
        }
        catch (Exception e)
        {
            is_root = false;
            Log.d(TAG, "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
        }
        return is_root;
    }

    public static String GetCommandOut(String[] command)
    {
        StringBuffer output = new StringBuffer();
        Process p;

        try
        {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            Thread.sleep(100);

            while ((line = reader.readLine())!= null)
            {
                output.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

    public static boolean Exist(String Path)
    {
        boolean exist = false;
        File file = new File(Path);

        if (file.exists())
        {
            exist = true;
        }
        return exist;
    }

    public static String ReadMulti(String Path)
    {
        String read = "Error";
        File file = new File(Path);

        if (file.exists())
        {
            StringBuilder text = new StringBuilder();

            try
            {
                BufferedReader br = new BufferedReader(new FileReader(Path));
                String line;

                while ((line = br.readLine()) != null)
                {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, " Error reading " + Path, e);
            }
            read = text.toString();
        }
        else
        {
            Log.e(TAG, " Error this file " + Path + " doesn't exist");
        }
        return read;
    }

    public static String Read(String Path)
    {
        String string = "0";
        BufferedReader reader = null;
        File file = new File(Path);

        if (file.exists())
        {
            try
            {
                reader = new BufferedReader(new FileReader(Path), 512);
                string = reader.readLine();
            }
            catch (FileNotFoundException e)
            {
                Log.w(TAG, "No such file " + Path + " for reading", e);
            }
            catch (IOException e)
            {
                Log.e(TAG, "Impossible read file " + Path, e);
            }
            finally
            {
                try
                {
                    if (reader != null)
                    {
                        reader.close();
                    }
                }
                catch (IOException e)
                {
                }
            }
        }
        else
        {
            Log.e(TAG, " Error this file " + Path + " doesn't exist");
        }
        return string;
    }

    public static void PutInt(Context context, String key, int value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("CKM_int",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int GetInt(Context context, String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("CKM_int",MODE_PRIVATE);
        int value = sharedPref.getInt(key, 0);
        sharedPref.edit().putInt(key, value).apply();
        return value;
    }

    public static void PutString(Context context, String key, String string)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("CKM_string",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, string);
        editor.apply();
    }

    public static String GetString(Context context, String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("CKM_string",MODE_PRIVATE);
        String string = sharedPref.getString(key, "");
        sharedPref.edit().putString(key, string).apply();
        return string;
    }
}
