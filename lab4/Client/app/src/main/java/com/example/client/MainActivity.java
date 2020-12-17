package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import  android.widget.Button;
import android.os.Bundle;
import android.view.*;
import android.content.*;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView err;
    private Button b1;//注册按钮
    private Button b2;//登录按钮
    private Button b3;//忘记密码按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        err = (TextView) findViewById(R.id.message);
        b1 = (Button) findViewById(R.id.register);
        b2 = (Button) findViewById(R.id.login);
        b3 = (Button) findViewById(R.id.login_error);
    }

    public void sign(View view) {    //登录按键的响应按键
        String user = username.getText().toString();
        String pass = password.getText().toString();
        if (user.isEmpty() || pass.isEmpty()) {
            err.setText("登录失败，用户名或密码不能为空");
            err.setVisibility(View.VISIBLE);
        } else {
            err.setText(null);
            err.setVisibility(View.INVISIBLE);
            new SignInProcess().execute(user,pass);
        }
    }

    public void signUp(View view) {   //注册按键的响应按键
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forget(View view) {   //注册按键的响应按键
        Intent intent = new Intent(MainActivity.this, ChangepassActivity.class);
        startActivity(intent);
    }

    private class SignInProcess extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String result = "";
            String s_url = "http://42.194.181.52:8080/lab4/Sign?username="+username+"&password="+password;
            System.out.println(s_url);
            try {
                URL url = new URL(s_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setUseCaches(false);
                conn.connect();
                InputStream is = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(is, "UTF-8");//获取服务器数据实现从字节流到字符流转换
                int temp;
                while((temp=reader.read()) != -1) {
                    result += (char)temp;
                }
            } catch(Exception e) {
                err.setText("登录失败，网络错误");
                err.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
            System.out.println(result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject result_json = new JSONObject(result);
                if(result_json.has("error")) {
                    String error_code;
                    error_code = result_json.getString("error");
                    err.setText(error_code);
                    err.setVisibility(View.VISIBLE);
                    password.setText(null);
                } else {
                    SignInSuccess(result_json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void SignInSuccess(JSONObject info) {
        Intent intent=new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this,WelcomeActivity.class);

        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        try {
            intent.putExtra("username", info.getString("username"));
            intent.putExtra("name", info.getString("name"));
            intent.putExtra("age", info.getString("age"));
            intent.putExtra("teleno", info.getString("teleno"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }
}
