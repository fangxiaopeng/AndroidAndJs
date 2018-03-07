# Android 与 Js 交互
***

本项目已实现以下所有交互方式，代码均有详细注释，欢迎交流讨论！

### Android调用Js
 - 通过WebView的loadUrl（）
 - 通过WebView的evaluateJavascript（）


### Js调用Android
 - 通过WebView的addJavascriptInterface（）进行对象映射
 - 通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
 - 通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息

欢迎star or issue

***

###### 参考资料：
- [WebView使用解析（一）之基本用法](http://blog.csdn.net/huaxun66/article/details/73179187)
- [ WebView使用解析（二）之WebViewClient/WebChromeClient](http://blog.csdn.net/huaxun66/article/details/73252592)
- [最全面总结 Android WebView与 JS 的交互方式](https://www.jianshu.com/p/345f4d8a5cfa)
