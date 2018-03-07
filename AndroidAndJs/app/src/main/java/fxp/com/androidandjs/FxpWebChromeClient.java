package fxp.com.androidandjs;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Js调用Android（自定义WebChromeClient）
 * 方式：通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（）消息
 * 一般拦截JS的输入框，即重写prompt（）方法
 * 因为只有prompt（）可以返回任意类型的值，操作最全面方便、更加灵活；alert（）对话框没有返回值；confirm（）对话框只能返回两种状态（确定 / 取消）两个值
 * 优点：不存在漏洞问题
 * 缺点：使用复杂（需要进行协议约束）
 * 适用场景：能满足大多数需要互调的场景
 * Created by fxp on 2018/3/7.
 */

public class FxpWebChromeClient extends WebChromeClient {

    private String TAG = "FxpWebChromeClient";

    private Context context;

    // 协议格式
    private String PROTOCOL_SCHEME = "js";

    // 协议名称
    private String PROTOCOL_AUTHORITY = "webview";

    public FxpWebChromeClient(Context context) {
        this.context = context;
    }

    /**
     * 拦截输入框
     *
     * @param view
     * @param url
     * @param message      promt（）的内容
     * @param defaultValue
     * @param result       输入框的返回值
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.i(TAG, "onJsPrompt message -" + message);
        // 解析message
        Uri uri = Uri.parse(message);
        // 判断scheme是否符合js约定协议格式
        if (uri.getScheme().equals(PROTOCOL_SCHEME)) {
            // 判断authority是否为预先约定协议名称
            if (uri.getAuthority().equals(PROTOCOL_AUTHORITY)) {
                // 执行JS所需要调用的逻辑
                Log.i(TAG, "执行JS所需要调用的逻辑");
                // 获取url参数，根据参数进行相应逻辑处理
                String paramStr = uri.getQuery();
                Log.i(TAG, "paramStr-" + paramStr);
                // TODO 对应逻辑处理


                // 设置消息框的返回值(输入值)
                result.confirm("js调用Android成功");
            }
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    /**
     * 拦截JS的警告框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.i(TAG, "onJsAlert message -" + message);

        return super.onJsAlert(view, url, message, result);
    }

    /**
     * 拦截JS的确认框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    /*
    * 通知页面标题变化
    */
//    nReceivedTitle(WebView view, String title)

    /*
    * 通知当前页面网站新图标
    */
//    onReceivedIcon(WebView view, Bitmap icon)

    /*
    * 通知主程序图标按钮URL
    */
//    onReceivedTouchIconUrl(WebView view, String url, boolean precomposed)

    /*
    * 通知主程序当前页面将要显示指定方向的View，该方法用来全屏播放视频。
    */
/*    public interface CustomViewCallback {
        // 通知当前页面自定义的View被关闭
        public void onCustomViewHidden();
    }*/

//    onShowCustomView(View view, CustomViewCallback callback)

    /*
    * 与onShowCustomView对应，通知主程序当前页面将要关闭Custom View
    */
//    onHideCustomView()

    /**
     * 请求主程序创建一个新的Window，如果主程序接收请求，返回true并创建一个新的WebView来装载Window，然后添加到View中，发送带有创建的WebView作为参数的resultMsg的给Target。如果主程序拒绝接收请求，则方法返回false。默认不做任何处理，返回false
     */
//    onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)

    /*
    * 显示当前WebView，为当前WebView获取焦点。
    */
//    onRequestFocus(WebView view)

    /*
    * 通知主程序关闭WebView，并从View中移除，WebCore停止任何的进行中的加载和JS功能。
    */
//    onCloseWindow(WebView window)

    /**
     * 告诉客户端显示离开当前页面的导航提示框。如果返回true，由客户端处理确认提示框，调用合适的JsResult方法。如果返回false，则返回默认值true给javascript接受离开当前页面的导航。默认：false。JsResult设置false，当前页面取消导航提示，否则离开当前页面。
     */
//    onJsBeforeUnload(WebView view, String url, String message, JsResult result)

    /**
     * 通知主程序web内容尝试使用定位API，但是没有相关的权限。主程序需要调用调用指定的定位权限申请的回调。更多说明查看GeolocationPermissions相关API。
     */
//    onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback)

    /*
     * 通知程序有定位权限请求。如果onGeolocationPermissionsShowPrompt权限申请操作被取消，则隐藏相关的UI界面。
     */
//    onGeolocationPermissionsHidePrompt()

    /**
     * 通知主程序web内容尝试申请指定资源的权限（权限没有授权或已拒绝），主程序必须调用PermissionRequest#grant(String[])或PermissionRequest#deny()。如果没有覆写该方法，默认拒绝。
     */
//    onPermissionRequest(PermissionRequest request)

    /**
     * 通知主程序相关权限被取消。任何相关UI都应该隐藏掉。
     */
//    onPermissionRequestCanceled(PermissionRequest request)

    /**
     * 通知主程序 执行的Js操作超时。客户端决定是否中断JavaScript继续执行。如果客户端返回true，JavaScript中断执行。如果客户端返回false，则执行继续。注意：如果继续执行，重置JavaScript超时计时器。如果Js下一次检查点仍没有结束，则再次提示。
     */
//    onJsTimeout()

    /**
     * 当停止播放，Video显示为一张图片。默认图片可以通过HTML的Video的poster属性标签来指定。如果poster属性不存在，则使用默认的poster。该方法允许ChromeClient提供默认图片。
     */
//    getDefaultVideoPoster()

    /**
     * 当用户重放视频，在渲染第一帧前需要花费时间去缓冲足够的数据。在缓冲期间，ChromeClient可以提供一个显示的View。如：可以显示一个加载动画。
     */
//    getVideoLoadingProgressView()

    /**
     * 获取访问历史Item，用于链接颜色。
     */
//    getVisitedHistory(ValueCallback callback)

    /**
     * 通知客户端显示文件选择器。用来处理file类型的HTML标签，响应用户点击选择文件的按钮操作。调用filePathCallback.onReceiveValue(null)并返回true取消请求操作。
     * FileChooserParams参数的枚举列表：
     * MODE_OPEN 打开
     * MODE_OPEN_MULTIPLE 选中多个文件打开
     * MODE_OPEN_FOLDER 打开文件夹（暂不支持）
     * MODE_SAVE 保存
     */
//    onShowFileChooser(WebView webView, ValueCallback filePathCallback, FileChooserParams fileChooserParams)

    /**
     * 解析文件选择Activity返回的结果。需要和createIntent一起使用。
     */
//    parseResult(int resultCode, Intent data)

    /**
     * 创建Intent对象来启动文件选择器。Intent支持可访问的简单类型文件资源。不支持高级文件资源如live media capture媒体快照。如果需要访问这些资源或其他高级文件类型资源可以自己创建Intent对象。
     */
//    createIntent()

    /**
     * 返回文件选择模式
     */
//    getMode()

    /**
     * 返回可访问MIME类型数组，如audio/*，如果没有指定可访问类型，数组返回为null
     */
//    getAcceptTypes()

    /**
     * 返回优先的媒体快照类型值如Camera、Microphone。true：允许快照。false，禁止快照。使用getAcceptTypes方法确定合适的capture设备。
     */
//    isCaptureEnabled()

    /**
     * 返回文件选择器的标题。如果为null，使用默认名称。
     */
//    getTitle()

    /**
     * 指定默认选中的文件名或为null
     */
//    getFilenameHint()

}
