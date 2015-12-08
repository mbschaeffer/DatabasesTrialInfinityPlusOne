package com.kiskiarea.databasestrialinfinityplusone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.first_screen);
            Button b = (Button) findViewById(R.id.beginLearning);

            b.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PeriodicActivity.class));
                }
            });
        }
}



