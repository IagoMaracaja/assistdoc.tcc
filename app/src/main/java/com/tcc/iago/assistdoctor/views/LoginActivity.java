package com.tcc.iago.assistdoctor.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.iago.assistdoctor.R;
import com.tcc.iago.assistdoctor.util.Constants;

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
        this.configElementsOfView();
    }

    @OnClick(R.id.login_btn_enjoy)
    public void callScreenShared() {
        if (isValidToken(mEdToken.getText().toString())) {
            Intent viewScreenShared = new Intent(LoginActivity.this, ViewScreenSharedActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(TOKEN, mEdToken.getText().toString());
            viewScreenShared.putExtras(bundle);
            startActivity(viewScreenShared);
        } else {
            showMessageError(true, getString(R.string.invalid_token));
        }
    }

    public void configElementsOfView() {
        mEdToken.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(MAX_LENGTH_EDITTEXT)
        });
        this.addTokenImeOption();
    }

    @OnClick(R.id.tv_token_help)
    public void onClickHelp() {
        showTokenHelper();
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

    private void addTokenImeOption() {
        mEdToken.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    callScreenShared();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Check if the password has 6 characters (Letters and Numbers)
     *
     * @param token
     * @return
     */
    public boolean isValidToken(final String token) {
        String cleanPassword = token.trim();
        boolean returnValue;
        if (cleanPassword.length() < Constants.PASSWORD_MAX_LENGTH || cleanPassword.length() > Constants.PASSWORD_MAX_LENGTH) {
            returnValue = false;
        } else if (!cleanPassword.matches(Constants.CODE_PASSWORD_REGEX)) {
            returnValue = false;
        } else {
            returnValue = true;
        }
        return returnValue;
    }

    /**
     * Show message error
     *
     * @param showMessageError
     * @param messageError
     */
    public void showMessageError(boolean showMessageError, String messageError) {
        LinearLayout errorContainer = (LinearLayout) findViewById(R.id.ll_error_container);
        TextView errorMessage = (TextView) findViewById(R.id.tv_message_error);

        if (showMessageError) {
            errorContainer.setVisibility(View.VISIBLE);
            errorMessage.setText(messageError);
        } else {
            errorContainer.setVisibility(View.GONE);
        }
    }

}
