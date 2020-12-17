package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import  android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.content.*;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private Button b1;//监听编辑资料
    private Button b2;//监听修改密码
    private Button b3;//监听退出登录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        t1 = (TextView)findViewById(R.id.username);
        t2 = (TextView)findViewById(R.id.name);
        t3 = (TextView)findViewById(R.id.age);
        t4 = (TextView)findViewById(R.id.teleno);
        t1.setText(intent.getStringExtra("username"));
        t2.setText(intent.getStringExtra("name"));
        t3.setText(intent.getStringExtra("age"));
        t4.setText(intent.getStringExtra("teleno"));
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
    }
    public void Login_out(View view){
        Intent intent=new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }
}
