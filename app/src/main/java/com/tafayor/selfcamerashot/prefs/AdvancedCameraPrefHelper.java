/*
 * Copyright (C) 2015 Ouadban Youssef(tafayor.dev@gmail.com)
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
 */


package com.tafayor.selfcamerashot.prefs;

import android.content.Context;


import com.tafayor.selfcamerashot.App;
import com.tafayor.selfcamerashot.camera.CameraParameters;
import com.tafayor.selfcamerashot.taflib.helpers.BasePrefsHelper;
import com.tafayor.selfcamerashot.taflib.helpers.DisplayHelper;
import com.tafayor.selfcamerashot.taflib.helpers.LogHelper;
import com.tafayor.selfcamerashot.taflib.types.Size;



public class AdvancedCameraPrefHelper extends BasePrefsHelper
{
    public static String TAG = AdvancedCameraPrefHelper.class.getSimpleName() ;


    public static String SHARED_PREFERENCES_NAME = TAG;


    public static String KEY_PREF_PREVIEW_SIZE= "prefPreviewSize";
    public static String KEY_PREF_ID_PREVIEW_SIZE= "prefIdPreviewSize";
    public static String KEY_PREF_ISO = "prefIso";
    public static String KEY_PREF_ID_ISO = "prefIdIso";
    public static String KEY_PREF_ENABLE_TOUCH_TO_fOCUS = "prefEnableTouchToFocus";
    public static String KEY_PREF_ENABLE_SCREEN_FLASH = "prefEnableScreenFlash";

    public static String KEY_PREF_ENABLE_LOCATION_RECORDING= "prefEnableLocationRecording";
    public static String KEY_PREF_DEFAULTS_LOADED = "prefDefaultsLoaded";


    public static String KEY_PREF_CUSTOM_STORAGE = "prefCustomStorage";
    public static String KEY_PREF_ENABLE_CUSTOM_STORAGE = "prefEnableCustomStorage";






    private static AdvancedCameraPrefHelper sInstance;
    public static synchronized  AdvancedCameraPrefHelper i(Context ctx)
    {
        if(sInstance == null) sInstance = new AdvancedCameraPrefHelper(ctx);
        return sInstance;
    }


    public AdvancedCameraPrefHelper(Context context)
    {
        super(context);
    }


    @Override
    protected String getSharedPreferencesName()
    {
        return SHARED_PREFERENCES_NAME;
    }


    //==================================================================================================

    public void loadDefaultPrefs()
    {


        setEnableScreenFlash(DefaultPrefs.ENABLE_SCREEN_FLASH);
        setEnableTouchToFocus(DefaultPrefs.ENABLE_TOUCH_TO_fOCUS);
        setEnableCustomStorage(DefaultPrefs.ENABLE_CUSTOM_STORAGE);


    }



    public void loadDefaultPreviewSizePref(CameraParameters camParams)
    {
        Size screenSize = DisplayHelper.getScreenSize(getContext());
        Size picSize = App.getCameraPrefHelper().getXCamPictureSize(camParams.getCameraId());
      //  int orientationAngle = DisplayHelper.getScreenOrientationAngle(mContext);
        int displayOrientation = camParams.determineDisplayOrientation(mContext);
        int cameraOrientation = camParams.getCameraOrientation();

        LogHelper.log("displayOrientation : " + displayOrientation);
       // LogHelper.log("cameraOrientation : " + cameraOrientation);
        boolean swapSize = (displayOrientation % 180 !=0);
        Size previewSize = camParams.getOptimalPreviewSize(screenSize, picSize, swapSize);

        setPreviewSize(camParams.getCameraId(), previewSize);
    }


    public void loadDefaultIsoPref(CameraParameters camParams)
    {
        String iso = IsoValues.AUTO;
        setIso(camParams.getCameraId(), iso);
    }



    //==================================================================================================




    public boolean getEnableCustomStorage()
    {
        return getBoolean(KEY_PREF_ENABLE_CUSTOM_STORAGE, DefaultPrefs.ENABLE_CUSTOM_STORAGE);
    }

    public void setEnableCustomStorage(boolean value)
    {
        putBoolean(KEY_PREF_ENABLE_CUSTOM_STORAGE, value);
        commit();
    }



    //***********

    public String getCustomStorage()
    {
        return mSharedPrefs.getString(KEY_PREF_CUSTOM_STORAGE, "");
    }

    public void setCustomStorage(String value)
    {
        mPrefsEditor.putString(KEY_PREF_CUSTOM_STORAGE, value);
        mPrefsEditor.commit();
    }


    //***********

    public boolean getEnableLocationRecording()
    {
        return getBoolean(KEY_PREF_ENABLE_LOCATION_RECORDING, DefaultPrefs.ENABLE_LOCATION_RECORDING);
    }

    public void setEnableLocationRecording(boolean value)
    {
        putBoolean(KEY_PREF_ENABLE_LOCATION_RECORDING, value);
        commit();
    }



    //***********

    public boolean getEnableScreenFlash()
    {
        return mSharedPrefs.getBoolean(KEY_PREF_ENABLE_SCREEN_FLASH, DefaultPrefs.ENABLE_SCREEN_FLASH);
    }

    public void setEnableScreenFlash(boolean value)
    {
        mPrefsEditor.putBoolean(KEY_PREF_ENABLE_SCREEN_FLASH, value);
        mPrefsEditor.commit();
    }


    //***********

    public boolean getEnableTouchToFocus()
    {
        return mSharedPrefs.getBoolean(KEY_PREF_ENABLE_TOUCH_TO_fOCUS, DefaultPrefs.ENABLE_TOUCH_TO_fOCUS);
    }

    public void setEnableTouchToFocus(boolean value)
    {
        mPrefsEditor.putBoolean(KEY_PREF_ENABLE_TOUCH_TO_fOCUS, value);
        mPrefsEditor.commit();
    }

    //***********


    public String getIso()
    {
        return getString(KEY_PREF_ISO, DefaultPrefs.ISO);
    }

    public void setIso(String value)
    {
        putString(KEY_PREF_ISO, value);
        mPrefsEditor.commit();
    }


    public String getIso(int id)
    {
        return getString(KEY_PREF_ID_ISO,""+id, DefaultPrefs.ISO);
    }

    public void setIso(int id, String value)
    {
        putString(KEY_PREF_ID_ISO, ""+id, value);
        mPrefsEditor.commit();
    }

    public boolean isIsoSet(int id)
    {
        String iso = getIso(id);
        return (!iso.equals(IsoValues.UNSET));

    }


    //***********



    public Size getPreviewSize(int id)
    {
        String strSize = getString(KEY_PREF_ID_PREVIEW_SIZE, ""+id, DefaultPrefs.PREVIEW_SIZE.toString());

        return Size.fromString(strSize);
    }

    public void setPreviewSize(int id, Size value)
    {
        String strVal = value.toString();
        putString(KEY_PREF_ID_PREVIEW_SIZE, ""+id, strVal);

    }

    public boolean isPreviewSizeSet(int camId)
    {
        Size size = getPreviewSize(camId);
        return !size.isZero();
    }

    public Size getPreviewSize()
    {

        String strSize = mSharedPrefs.getString(KEY_PREF_PREVIEW_SIZE, DefaultPrefs.PREVIEW_SIZE.toString());
        return Size.fromString(strSize);
    }

    public void setPreviewSize(Size value)
    {
        String strVal = value.toString();
        mPrefsEditor.putString(KEY_PREF_PREVIEW_SIZE, strVal);
        mPrefsEditor.commit();
    }










    //==============================================================================================
    // Implementation
    //==============================================================================================






}
