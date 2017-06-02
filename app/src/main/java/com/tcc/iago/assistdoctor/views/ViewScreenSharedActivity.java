package com.tcc.iago.assistdoctor.views;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tcc.iago.assistdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iago Maracajá on 01/06/17.
 */

public class ViewScreenSharedActivity extends Activity {

    @BindView(R.id.vss_webview)
    protected WebView mWebView;

    private static final String URL = "https://assist-doctor.herokuapp.com/demos/screen-sharing.html#28aoyq6kesx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_screen_shared);
        ButterKnife.bind(this);

        openWebVieW();
    }

    public void openWebVieW(){

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);

         mWebView.loadUrl(URL);


        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(ViewScreenSharedActivity.this, "ErrorCode = " + errorCode, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
