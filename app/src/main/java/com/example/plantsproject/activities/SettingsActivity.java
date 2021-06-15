package com.example.plantsproject.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.plantsproject.R;
import com.example.plantsproject.notifications.AlarmReceiver;
import com.example.plantsproject.notifications.NotificationScheduler;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            Intent i = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(i);
        });

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);


            Preference langPreference = getPreferenceScreen().findPreference("language");
            Preference.OnPreferenceChangeListener languageChangeListener = (preference, newValue) -> {
                switch (newValue.toString()) {
                    case "en":
                        setLocale("en");
                        break;
                    case "ru":
                        setLocale("ru");
                        break;
                }
                return true;
            };
            langPreference.setOnPreferenceChangeListener(languageChangeListener);


            Preference notifsPreference = getPreferenceScreen().findPreference("notifications");
            notifsPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if(newValue.equals(false)) {
                    NotificationScheduler.cancelReminder(getContext(), AlarmReceiver.class);
                    Toast.makeText(getContext(), getString(R.string.disable_notifications), Toast.LENGTH_SHORT).show();
                } else {
                    return true;
                }
                return true;
            });


        }

         void setLocale(String lang) {
            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);

        }
    }
}