package se.miun.algi1701.dt031g.dialer;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private boolean writeAccess = false;
    private WebView webView;
    private WebViewClient webViewClient;
    private DownloadListener downloadListener;
    private ProgressDialog progressDialog;

    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        final Bundle extras = getIntent().getExtras();
        this.url = extras.getString("url");

        this.webView = findViewById(R.id.web_view_id);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);

        checkAccess();
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                new DownloadFile().execute(url, URLUtil.guessFileName(url, contentDisposition, mimetype));

            }

        });


    }

    private void checkAccess(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted!

                    writeAccess = true;

                }
                else{
                    // Permission denied!
                    Toast.makeText(this, R.string.no_write_ex_storage, Toast.LENGTH_SHORT).show();
                    writeAccess = false;
                }

                break;

            }
        }
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, String>{

        private String fileName = "";
        private String destPath = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WebActivity.this);
            progressDialog.setMessage("Downloading, Please Wait!");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int fileLength = connection.getContentLength();
                destPath = Constants.EXTERNAL_STORAGE_PATH;
                fileName = strings[1];
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(destPath + "/" + fileName);

                byte data[] = new byte[1024];
                long total = 0;
                int count = 0;
                while((count = input.read(data)) != -1){
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            progressDialog = null;

            ZIP.decompress(destPath + "/" + fileName, destPath);


        }
    }


}



