package fxp.com.androidandjs;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Iterator;
import java.util.Set;

/**
 * Js调用Android（自定义WebViewClient）
 * 方式：通过 WebViewClient 的方法shouldOverrideUrlLoading ()回调拦截 url
 * 步骤1：Android通过 WebViewClient 的回调方法shouldOverrideUrlLoading ()拦截 url
 * 步骤2：解析该 url 的协议
 * 步骤3：如果检测到是预先约定好的协议，就调用相应方法
 * 优点：不存在漏洞问题
 * 缺点：使用复杂（需要进行协议约束、往js回调值比较繁琐）
 * 适用场景：不需要返回值
 * Created by fxp on 2018/3/7.
 */

public class FxpWebViewClient extends WebViewClient {

    private String TAG = "FxpWebViewClient";

    private Context context;

    // 协议格式
    private String PROTOCOL_SCHEME = "js";

    // 协议名称
    private String PROTOCOL_AUTHORITY = "webview";

    public FxpWebViewClient(Context context) {
        this.context = context;
    }

    /**
     * 在开始加载网页时会回调
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i(TAG, "onPageStarted url-" + url);

    }

    /**
     * 在结束加载网页时会回调
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "onPageFinished url-" + url);

    }

    /**
     * 拦截 url 跳转,在里边添加点击链接跳转或者操作
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i(TAG, "shouldOverrideUrlLoading url-" + url);
        // 解析url
        Uri uri = Uri.parse(url);
        // 判断scheme是否符合js约定协议格式
        if (uri.getScheme().equals(PROTOCOL_SCHEME)) {
            // 判断authority是否为预先约定协议名称
            if (uri.getAuthority().equals(PROTOCOL_AUTHORITY)) {
                // 执行JS所需要调用的逻辑
                Log.i(TAG, "执行JS所需要调用的逻辑");
                // 获取url参数，根据参数进行相应逻辑处理
                String paramStr = uri.getQuery();
                Log.i(TAG, "paramStr-" + paramStr);

                Set<String> collection = uri.getQueryParameterNames();
                Iterator<String> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    String param = iterator.next();
                    Log.i(TAG, "param-" + param);

                }
            }
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Log.i(TAG, "shouldOverrideUrlLoading description-" + description);

    }

    /**
     * 当接收到https错误时，会回调此函数，在其中可以做错误处理
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // 当出现SSL错误时，WebView默认是取消加载当前页面，只有去掉onReceivedSslError的默认操作，然后添加SslErrorHandler.proceed()才能继续加载出错页面
        // 当HTTPS传输出现SSL错误时，错误会只通过onReceivedSslError回调传过来，不会执行onReceivedError
        //super.onReceivedSslError(view, handler, error);
        handler.proceed();

        Log.i(TAG, "onReceivedSslError");

    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.i(TAG, "shouldInterceptRequest url-" + url);

        return null;
    }

    /**
     * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
     */
//    public void onLoadResource(WebView view, String url)

    /**
     *  (WebView发生改变时调用)
     *  可以参考http://www.it1352.com/191180.html的用法
     */
//    public void onScaleChanged(WebView view, float oldScale, float newScale)

    /**
     * 重写此方法才能够处理在浏览器中的按键事件。
     * 是否让主程序同步处理Key Event事件，如过滤菜单快捷键的Key Event事件。
     * 如果返回true，WebView不会处理Key Event，
     * 如果返回false，Key Event总是由WebView处理。默认：false
     */
//    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event)

    /**
     * 是否重发POST请求数据，默认不重发。
     */
//    onFormResubmission(WebView view, Message dontResend, Message resend)

    /**
     * 更新访问历史
     */
//    doUpdateVisitedHistory(WebView view, String url, boolean isReload)

    /**
     * 通知主程序输入事件不是由WebView调用。是否让主程序处理WebView未处理的Input Event。
     * 除了系统按键，WebView总是消耗掉输入事件或shouldOverrideKeyEvent返回true。
     * 该方法由event 分发异步调用。注意：如果事件为MotionEvent，则事件的生命周期只存在方法调用过程中，
     * 如果WebViewClient想要使用这个Event，则需要复制Event对象。
     */
//    onUnhandledInputEvent(WebView view, InputEvent event)

    /**
     * 通知主程序执行了自动登录请求。
     */
//    onReceivedLoginRequest(WebView view, String realm, String account, String args)

    /**
     * 通知主程序：WebView接收HTTP认证请求，主程序可以使用HttpAuthHandler为请求设置WebView响应。默认取消请求。
     */
//    onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)

    /**
     * 通知主程序处理SSL客户端认证请求。如果需要提供密钥，主程序负责显示UI界面。
     * 有三个响应方法：proceed(), cancel() 和 ignore()。
     * 如果调用proceed()和cancel()，webview将会记住response，
     * 对相同的host和port地址不再调用onReceivedClientCertRequest方法。
     * 如果调用ignore()方法，webview则不会记住response。该方法在UI线程中执行，
     * 在回调期间，连接被挂起。默认cancel()，即无客户端认证
     */
//    onReceivedClientCertRequest(WebView view, ClientCertRequest request)

}
