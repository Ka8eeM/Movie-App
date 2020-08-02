package com.ka8eem.testtmdb.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ka8eem.testtmdb.R;
import com.ka8eem.testtmdb.ui.fragments.SortOptionDialog;

public class SettingActivity extends AppCompatActivity implements SortOptionDialog.SingleChoiceListener {

    // finals
    public static final String SHARD_PREF = "SHARED_PREF";

    // widget
    RelativeLayout relativeLayout1, relativeLayout2;
    TextView txtSort, txtView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //vars
    private boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        relativeLayout1 = findViewById(R.id.relative1);
        txtSort = findViewById(R.id.txt_view_sort_options);
        txtView = findViewById(R.id.txt_view_layout_options);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.setting_));
        preferences = getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        String temp = preferences.getString("SORT_BY", "NOT");
        if (!temp.equals("NOT"))
            txtSort.setText(temp);
        temp = preferences.getString("LAYOUT_VIEW", "NOT");
        if (!temp.equals("NOT"))
            txtView.setText(temp);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                final String[] list = getResources().getStringArray(R.array.sort_option_value);
                DialogFragment fragment = new SortOptionDialog(list);
                fragment.setCancelable(false);
                fragment.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });
        relativeLayout2 = findViewById(R.id.relative2);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                final String[] list = getResources().getStringArray(R.array.view_layout_option);
                DialogFragment fragment = new SortOptionDialog(list);
                fragment.setCancelable(false);
                fragment.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int pos) {
        if (flag) {
            txtSort.setText(list[pos]);
            editor.putString("SORT_BY", list[pos]);
        } else {
            txtView.setText(list[pos]);
            editor.putString("LAYOUT_VIEW", list[pos]);
        }
        editor.commit();
        editor.apply();
    }

    @Override
    public void onNegativeButtonClicked() {
        Toast.makeText(this, "Choice Canceled", Toast.LENGTH_SHORT).show();
    }
}