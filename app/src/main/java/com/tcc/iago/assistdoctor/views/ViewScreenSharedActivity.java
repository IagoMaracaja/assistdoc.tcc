package com.tcc.iago.assistdoctor.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.tcc.iago.assistdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iago Maracajá on 01/06/17.
 */

public class ViewScreenSharedActivity extends Activity {

    private static final String URL = "http://doctor-assistance.herokuapp.com/view?hash=";

    @BindView(R.id.vss_webview)
    protected WebView mWebView;
    @BindView(R.id.tv_webview_status)
    protected TextView mTvWebviewStatus;

    private String mHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_screen_shared);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        mHash = getIntent().getExtras().getString(LoginActivity.TOKEN);

        newOpenWebVieW(mHash);
        super.onResume();
    }

    public void openWebVieW(String hash) {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(ViewScreenSharedActivity.this, "ErrorCode = " + errorCode, Toast.LENGTH_SHORT).show();

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mTvWebviewStatus.setText("Page Started");

                Log.d("WebView", "onPageStarted " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mTvWebviewStatus.setText("Page Finished");
                Log.d("WebView", "onPageFinished " + url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mTvWebviewStatus.setText("Error: "+error.toString());
                super.onReceivedError(view, request, error);
            }
        });

        mWebView.loadUrl(URL + hash);
    }

    WebChromeClient webChromeClient = new WebChromeClient();
    WebViewClient webViewClient = new WebViewClient();

    public void newOpenWebVieW(String hash){

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.loadUrl(URL + hash);
        mTvWebviewStatus.setText(hash);

    }

    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // TODO Auto-generated method stub
            super.onShowCustomView(view, callback);
            if (view instanceof FrameLayout) {
                FrameLayout frame = (FrameLayout) view;
                if (frame.getFocusedChild() instanceof VideoView) {
                    VideoView video = (VideoView) frame.getFocusedChild();
                    frame.removeView(video);
                    video.start();
                }
            }

        }
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}

