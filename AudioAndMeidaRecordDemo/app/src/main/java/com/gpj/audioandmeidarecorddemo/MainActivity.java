package com.gpj.audioandmeidarecorddemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSIONS_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toMediaRecorder(View view) {
        Intent intent = new Intent(this,FileActivity.class);
        startActivity(intent);
    }

    public void toAudioRecord(View view) {
        Intent intent = new Intent(this,StreamActivity.class);
        startActivity(intent);
    }

    public void getPermisision(View view) {
;        if(isGrantExternalRW(this)){
            Toast.makeText(this,"权限已经获取",Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&( activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||activity.checkSelfPermission(
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
            }, PERMISSIONS_CODE);

            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        //授权成功后的逻辑
                        Toast.makeText(this,"授权成功",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,"拒绝授权",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
