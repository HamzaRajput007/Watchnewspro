package pro.watchnews.watchnewspro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Myaccount extends AppCompatActivity {
    private ProgressBar progressBar;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        WebView browser = (WebView) findViewById(R.id.webview2);
        browser.setWebViewClient(new Myaccount.MyBrowser());
        browser.loadUrl("https://www.watchnews.pro/account");
        // enable / disable javascript
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.show();

        browser.getSettings().setJavaScriptEnabled(true);
        browser.canGoBack();
        browser.canGoForward();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pDialog.dismiss();
        }
    }
}
