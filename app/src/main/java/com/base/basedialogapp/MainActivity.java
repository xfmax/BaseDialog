package com.base.basedialogapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.base.basedialog.BaseDialog;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private int TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog baseDialog = BaseDialog.getInstance(TYPE,baseLisenter);
                baseDialog
                        .bulidBackground(Color.WHITE)
                        .bulidTitle("我的标题",Color.BLUE)
                        .bulidContent("我的内容",Color.GREEN)
                        .bulidIcon(R.mipmap.ic_launcher)
                        .bulidButtonOk(Color.RED,"我的ok",Color.WHITE)
                        .bulidButtonCancel(Color.BLUE,"我的cancel", Color.WHITE);
                baseDialog.show(getSupportFragmentManager(), "");
            }
        });
    }

    BaseDialog.BaseDialogListener baseLisenter = new BaseDialog.BaseDialogListener() {

        @Override
        public void ok(int type, int resultCode) {
            Toast.makeText(getApplicationContext(), "ok!", Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void cancel(int type, int resultCode) {
            Toast.makeText(getApplicationContext(), "cancel!", Toast.LENGTH_LONG)
                    .show();
        }
    };
}
