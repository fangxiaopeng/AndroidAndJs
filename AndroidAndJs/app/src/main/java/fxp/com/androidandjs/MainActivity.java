package fxp.com.androidandjs;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, CameraListener {

    private String TAG = "MainActivity";

    private Context context;

    private Toolbar toolbar;

    private DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;

    private NavigationView navigationView;

    private WebView webView;

    private EditText editText;

    private TextView commitBtn;

    private JsInterface jsInterface;

    private FxpWebViewClient fxpWebViewClient;

    private FxpWebChromeClient fxpWebChromeClient;

    public static final String IMAGE_PATH = "fxpFiles";

    /* 相机请求码 */
    private static final int REQUEST_CAMERA = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();

        findViews();

        initViews();

        initListeners();

        solveExceptionByVmPolicy();
    }

    private void initDatas() {
        context = getApplicationContext();
        jsInterface = new JsInterface(MainActivity.this);
        fxpWebViewClient = new FxpWebViewClient(MainActivity.this);
        fxpWebChromeClient = new FxpWebChromeClient(MainActivity.this);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        webView = (WebView) findViewById(R.id.webview);
        editText = (EditText) findViewById(R.id.edit_text);
        commitBtn = (TextView) findViewById(R.id.commit_btn);
    }

    private void initViews() {
        // 显示actionBar
        setSupportActionBar(toolbar);

        initNavigationView();

        initWebView();
    }

    private void initListeners() {
        navigationView.setNavigationItemSelectedListener(this);
        commitBtn.setOnClickListener(this);
        jsInterface.setCameraListener(this);
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        // 允许JS自动打开窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 开启javascript支持
        webSettings.setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.loadUrl("file:///android_asset/web/index.html");
        // Js调用Android方式一：通过WebView的addJavascriptInterface（）进行对象映射
        webView.addJavascriptInterface(jsInterface, "fxp");
        // Js调用Android方式二：通过 WebViewClient 的方法shouldOverrideUrlLoading()回调拦截url
        webView.setWebViewClient(fxpWebViewClient);
        // Js调用Android方式三：过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（）消息
        webView.setWebChromeClient(fxpWebChromeClient);
    }

    private void initNavigationView() {
        // 左上角打开左侧栏按钮
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_btn:
                inputAndroidDataToWebView();
                break;
            default:
                break;
        }
    }

    /**
     * Android调用Js-将Android输入框内容填写到WebView中html
     * 使用evaluateJavascript()和 loadUrl()两种方式实现
     * evaluateJavascript()方式-效率高，可直接获取JS返回值，但只支持Android4.4以上
     * loadUrl()方式-方便简洁，但效率低，获取js返回值麻烦，适用于不需要获取JS返回值或对性能要求较低的场景
     */
    private void inputAndroidDataToWebView() {
        String str = editText.getText().toString();
        // 判断系统版本是否高于或等于4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // evaluateJavascript()方式
            webView.evaluateJavascript("javascript:main.inputData(" + str + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    // js返回结果处理

                }
            });
        } else {
            // loadUrl()方式
            webView.loadUrl("javascript:main.inputData(" + str + ")");
        }
    }

    @Override
    public void takePicture() {
        Toast.makeText(context, "takePicture", Toast.LENGTH_SHORT).show();

        //判断是否有权限
        if (hasPermission(Manifest.permission.CAMERA, Manifest.permission.CAMERA)) {
            //有权限
            takePhoto();
        } else {
            //没权限，进行权限请求
            requestPermission(REQUEST_CAMERA, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case REQUEST_CAMERA:
                if (res == RESULT_OK) {
                    Log.i(TAG, "拍照成功");

                } else {
                    Log.i(TAG, "拍照失败");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 调用系统相机拍照
     */
    private void takePhoto() {
        try {
            String imgName = System.currentTimeMillis() + ".jpg";
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri(IMAGE_PATH, imgName));
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取照片Uri
     *
     * @param path 保存照片文件夹名称
     * @param name 照片名称
     * @return
     */
    private Uri getPhotoUri(String path, String name) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            file.mkdir();
        }
        File output = new File(file, name);
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 解决Android7.0相机权限问题方法1 - onCreate中调用solveExceptionByVmPolicy()
        Uri photoURI = Uri.fromFile(output);

        return photoURI;
    }

    /**
     * 判断是否拥有权限
     *
     * @param permissions 形参String...的效果其实就和数组一样，这里的实参可以写多个String
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * 请求权限
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
        Toast.makeText(context, "如果拒绝授权,会导致应用无法正常使用", Toast.LENGTH_SHORT).show();
    }

    /**
     * 申请权限的回调
     *
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults 多个权限一起返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "获取相机权限成功");
                takePhoto();
            } else {
                Log.i(TAG, "获取相机权限失败");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * android 7.0系统解决拍照的问题
     * Android 7.0开始，一个应用提供自身文件给其它应用使用时，如果给出一个file://格式的URI的话，应用会抛出FileUriExposedException
     * onCreate中调用此方法即可
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void solveExceptionByVmPolicy() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /***
     * 防止WebView加载内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }
}
