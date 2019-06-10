package com.example.preparelectures;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import androidx.annotation.Nullable;

public class preference extends PreferenceFragmentCompat {
    private static String sync_type="sync_type";
    private SharedPreferences.OnSharedPreferenceChangeListener changeListener;
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);
        changeListener= new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
               if(key.equals(sync_type))
               {
                   Preference pref=findPreference(key);
                   pref.setSummary(sharedPreferences.getString(key,""));
               }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(changeListener);
        Preference pref=findPreference(sync_type);
        pref.setSummary(getPreferenceScreen().getSharedPreferences().getString(sync_type,""));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(changeListener);
    }
}
