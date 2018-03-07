package fxp.com.androidandjs;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Js调用Android（桥梁类）
 * 方式：通过WebView的addJavascriptInterface（）进行对象映射
 * 优点：使用简单，将Android对象和JS对象映射即可
 * 缺点：Android 4.2下存在严重的漏洞问题，漏洞参考https://www.jianshu.com/p/3a345d27cd42
 * Created by fxp on 2018/3/7.
 */

public class JsInterface {

    private String TAG = "JsInterface";

    private Context context;

    private CameraListener cameraListener = null;

    public JsInterface(Context context) {
        this.context = context;
    }

    /**
     * Js调用Android
     * 由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
     *
     * @param json 自定json义格式字符串 {"action":"","params":""}，其中action为要调用的java方法名，params为参数
     * @throws JSONException
     */
    @JavascriptInterface
    public void invokeAndroid(String json) throws JSONException {
        Log.i(TAG, "invokeAndroid-" + json);
        JSONObject jsJSON = new JSONObject(json);
        String action = jsJSON.optString("action");
        String params = jsJSON.optString("params");
        switch (action) {
            case "showToast":
                showToast(params);
                break;
            case "takePicture":
                takePicture();
                break;
            default:
                break;
        }
    }

    public void setCameraListener(CameraListener listener) {
        this.cameraListener = listener;
    }

    private void showToast(String str) {
        Log.i(TAG, "showToast-" + str);
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    private void takePicture() {
        Log.i(TAG, "takePicture");
        cameraListener.takePicture();
    }
}
