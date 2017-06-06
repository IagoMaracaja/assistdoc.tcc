package com.tcc.iago.assistdoctor.views;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tcc.iago.assistdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iago Maracajá on 30/05/17.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TOKEN = "token";
    private static final int MAX_LENGTH_EDITTEXT = 6;
    @BindView(R.id.et_token_main)
    protected TextView mEdToken;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configElementsOfView();
    }

    @OnClick(R.id.login_btn_enjoy)
    public void callScreenShared(){
        Intent viewScreenShared = new Intent(LoginActivity.this, ViewScreenSharedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN, mEdToken.getText().toString());
        viewScreenShared.putExtras(bundle);
        startActivity(viewScreenShared);
    }

    public void configElementsOfView() {
        mEdToken.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(MAX_LENGTH_EDITTEXT)
        });
    }
    @OnClick(R.id.tv_token_help)
    public void onClickHelp(){
        showTokenHelper();
    }


    /**
     * Open a dialog for help information about Access Token.
     */
    private void CallDialogForHelp() {
        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.alert_dialog_help, null);
        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mAlertDialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }


    /**
     * Open a dialog to show a error about any validation.
     */
    private void showTokenHelper() {
        LayoutInflater li = getLayoutInflater();

        if (mAlertDialog == null) {

            View view = li.inflate(R.layout.alert_dialog_help, null);
            view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                        mAlertDialog.dismiss();
                        mAlertDialog = null;

                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            if (!isFinishing()) {
                mAlertDialog = builder.create();
                mAlertDialog.setCancelable(false);
                mAlertDialog.show();
            }

        }
    }


}
