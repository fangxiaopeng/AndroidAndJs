package fxp.com.androidandjs;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Context context;

    private Toolbar toolbar;

    private DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;

    private NavigationView navigationView;

    private WebView webView;

    private EditText editText;

    private TextView commitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();

        findViews();

        initViews();

        initListeners();
    }

    private void initDatas() {
        context = getApplicationContext();

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
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        // 允许JS自动打开窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 开启javascript支持
        webSettings.setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.loadUrl("file:///android_asset/web/index.html");
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

                break;
            default:
                break;
        }
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
}
