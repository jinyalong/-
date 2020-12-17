package com.example.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ChangepassActivity extends AppCompatActivity implements  View.OnClickListener {

    private EditText t1;
    private EditText t4;
    private EditText t5;
    private EditText t6;

    private TimerTask tt;
    private Timer tm;
    private EditText et_phonenum;
    private Button btn_check;
    private EditText et_checkecode;
    private Button btn_sure;
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s,我们前端会有差异的建议设置90，100或者120
    public String country="86";//这是中国区号，如果需要其他国家列表，可以使用getSupportedCountries();获得国家区号
    private String phone;
    private static final int CODE_REPEAT = 1; //重新发送


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_changepass);
        t1  = (EditText)findViewById(R.id.username);
        t4  = (EditText)findViewById(R.id.teleno);
        t5  = (EditText)findViewById(R.id.pw);
        t6  = (EditText)findViewById(R.id.pw1);

        MobSDK.init(this, "31ac66c2aa660", "aaa75b5ca307554244989864e01bbd37");
        SMSSDK.registerEventHandler(eh); //注册短信回调（记得销毁，避免泄露内存）
        initView();
    }
    public void back_to(View view) {   //注册按键的响应按键
        Intent intent = new Intent(ChangepassActivity.this, MainActivity.class);
        startActivity(intent);
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CODE_REPEAT) {
                btn_check.setEnabled(true);
                btn_sure.setEnabled(true);
                tm.cancel();//取消任务
                tt.cancel();//取消任务
                TIME = 60;//时间重置
                btn_check.setText("重新发送验证码");
            }else {
                btn_check.setText(TIME + "重新发送验证码");
            }
        }
    };
    //回调
    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    String username  = t1.getText().toString();
                    String teleno = t4.getText().toString();
                    String pw1  = t5.getText().toString();
                    new SignUpProcess().execute(username,teleno,pw1);

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){       //获取验证码成功
                    toast("获取验证码成功");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                    //返回支持发送验证码的国家列表
                }
            }else{//错误等在这里（包括验证失败）
                //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                ((Throwable)data).printStackTrace();
                String str = data.toString();
                toast(str);
            }
        }
    };
    //吐司的一个小方法
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangepassActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initView() {
        et_phonenum = (EditText) findViewById(R.id.teleno);
        btn_check = (Button) findViewById(R.id.btn_check);
        et_checkecode = (EditText) findViewById(R.id.yzm);
        btn_sure = (Button) findViewById(R.id.changepass);
        btn_check.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check://响应获取验证码
                phone = et_phonenum.getText().toString().trim().replaceAll("/s","");
                if (check_in()&&!TextUtils.isEmpty(phone)) {
                    //定义需要匹配的正则表达式的规则
                    String REGEX_MOBILE_SIMPLE =  "[1][358]\\d{9}";
                    //把正则表达式的规则编译成模板
                    Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
                    //把需要匹配的字符给模板匹配，获得匹配器
                    Matcher matcher = pattern.matcher(phone);
                    // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
                    if (matcher.find()) {//匹配手机号是否存在
                        alterWarning();

                    } else {
                        toast("手机号格式错误");
                    }
                } else {
                    toast("请先输入手机号");
                }
                break;
            case R.id.changepass:
                //获得用户输入的验证码
                if(check_in()) {
                    String code = et_checkecode.getText().toString().replaceAll("/s", "");
                    if (!TextUtils.isEmpty(code)) {//判断验证码是否为空
                        //验证
                        SMSSDK.submitVerificationCode(country, phone, code);
                    } else {//如果用户输入的内容为空，提醒用户
                        toast("请输入验证码后再提交");
                    }
                }
                break;
        }
    }


    //弹窗确认下发
    private void alterWarning() {
        //构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示"); //设置标题
        builder.setMessage("我们将要发送到" + phone + "验证"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                //通过sdk发送短信验证（请求获取短信验证码，在监听（eh）中返回）
                SMSSDK.getVerificationCode(country, phone);
                //做倒计时操作
                Toast.makeText(ChangepassActivity.this, "已发送" + which, Toast.LENGTH_SHORT).show();
                btn_check.setEnabled(false);
                btn_sure.setEnabled(true);
                tm = new Timer();
                tt = new TimerTask() {
                    @Override
                    public void run() {
                        hd.sendEmptyMessage(TIME--);
                    }
                };
                tm.schedule(tt,0,1000);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ChangepassActivity.this, "已取消" + which, Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    //销毁短信注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调接口registerEventHandler必须和unregisterEventHandler配套使用，否则可能造成内存泄漏。
        SMSSDK.unregisterEventHandler(eh);

    }
    private class SignUpProcess extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String teleno = params[1];
            String password = params[2];
            String result = "";
            String s_url = "http://42.194.181.52:8080/lab4/Change_password?username="+username+"&teleno="+teleno+"&password="+password;
            System.out.println(s_url);
            try {
                URL url = new URL(s_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setUseCaches(false);
                conn.connect();
                InputStream is = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(is, "UTF-8");
                int temp;
                while((temp=reader.read()) != -1) {
                    result += (char)temp;
                }
            } catch(Exception e) {
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
                    SignInError();
                } else {
                    SignInSuccess();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void SignInError() {
        Toast.makeText(this,"修改密码失败", Toast.LENGTH_SHORT).show();

    }
    private void SignInSuccess() {
        Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }

    private boolean check_in(){
        String username  = t1.getText().toString();
        String teleno = t4.getText().toString();
        String pw1  = t5.getText().toString();
        String pw2  = t6.getText().toString();
        boolean flag = true;
        if(username.isEmpty()){
            toast("用户名不能为空");
            flag = false;
        }
        //检查密码
        if(!pw1.equals(pw2)){
            toast("两次密码不一致");
            flag = false;
        }else{
            if(pw1.length() < 6 || pw2.length() > 12){
                toast("密码必须6-12位");
                flag = false;
            }
            else{
                String pattern6 = "[a-zA-Z\\d_]*";
                boolean match6 = Pattern.matches(pattern6, pw1);
                if(!match6) {
                    toast("密码应由英文字母数字和_组成");
                    flag = false;
                }
            }
        }
        return flag;
    }
}





