package com.tcc.iago.assistdoctor.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.tcc.iago.assistdoctor.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iago Maracajá on 30/05/17.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.login_btn_enjoy)
    public void callScreenShared(){
        Intent viewScreenShared = new Intent(LoginActivity.this, ViewScreenSharedActivity.class);
        startActivity(viewScreenShared);
    }
}
