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

class Strings {

    static String INFO_TEMP = "sensor list";

    static String APPLY_ON_BOOT_GESTURE = "apply_on_boot_gesture";
    static String GESTURE_DT2W = "dt2w";
    static String GESTURE_S2W = "s2w";
    static String GESTURE_S2S = "s2s";
    static String GESTURE_VIBE = "gesture_vibe";

    static String APPLY_ON_BOOT_DISPLAY = "apply_on_boot_display";
    static String DISPLAY_PROFILE = "display_profile";
    static String DISPLAY_RED = "display_red";
    static String DISPLAY_GREEN = "display_green";
    static String DISPLAY_BLUE = "display_blue";
    static String DISPLAY_BRIGHTNESS = "display_brightness";
    static String DISPLAY_CONTRAST = "display_contrast";
    static String DISPLAY_SATURATION = "display_saturation";

    static String APPLY_ON_BOOT_AUDIO = "apply_on_boot_audio";
    static String AUDIO_MIC = "audio_mic";
    static String AUDIO_SPEAKER = "audio_speaker";
    static String AUDIO_LEFT_A = "audio_left_a";
    static String AUDIO_RIGHT_A = "audio_rigth_a";
    static String AUDIO_LINK_A = "audio_link_a";
    static String AUDIO_LEFT_D = "audio_left_d";
    static String AUDIO_RIGHT_D = "audio_rigth_d";
    static String AUDIO_LINK_D = "audio_link_d";

    static String INFO_POSSIBLE_CPUS_PATH = "/sys/devices/system/cpu/possible";
    static String INFO_CPU_PATH = "/sys/devices/system/cpu/";
    static String INFO_KERNEL_PATH = "/proc/version";
    static String INFO_TEMP_PATH = "/sys/devices/virtual/thermal";
    static String INFO_RAM_PATH = "/proc/meminfo";
    static String INFO_GPU_PATH = "/sys/class/kgsl/kgsl-3d0/devfreq/cur_freq";

    static String GESTURE_DT2W_PATH = "/sys/android_touch/doubletap2wake";
    static String GESTURE_S2W_PATH = "/sys/android_touch/sweep2wake";
    static String GESTURE_S2S_PATH = "/sys/android_touch/sweep2sleep";
    static String GESTURE_VIBE_PATH = "/sys/android_touch/vib_strength";

    static String DISPLAY_RGB_PATH = "/sys/devices/platform/kcal_ctrl.0/kcal";
    static String DISPLAY_BRIGHTNESS_PATH = "/sys/devices/platform/kcal_ctrl.0/kcal_val";
    static String DISPLAY_CONTRAST_PATH = "/sys/devices/platform/kcal_ctrl.0/kcal_cont";
    static String DISPLAY_SATURATION_PATH = "/sys/devices/platform/kcal_ctrl.0/kcal_sat";

    static String AUDIO_MIC_PATH = "/sys/kernel/sound_control/mic_gain";
    static String AUDIO_SPEAKER_PATH  = "/sys/kernel/sound_control/speaker_gain";
    static String AUDIO_D_PATH  = "/sys/kernel/sound_control/headphone_gain";
    static String AUDIO_A_PATH  = "/sys/kernel/sound_control/headphone_pa_gain";
}
