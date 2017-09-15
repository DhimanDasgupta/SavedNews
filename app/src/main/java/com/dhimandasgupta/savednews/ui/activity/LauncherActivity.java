package com.dhimandasgupta.savednews.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dhimandasgupta.savednews.db.ArticleDatabaseCreator;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ArticleDatabaseCreator creator = ArticleDatabaseCreator.getInstance(this);
        creator.createDb(this);
        creator.isDatabaseCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }
}
