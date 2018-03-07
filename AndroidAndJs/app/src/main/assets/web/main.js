
var main = function(){

    var androidInput,showToastBtn,TakePicBtn;

    var init = () =>{
        console.log("init");

        initDOM();

        initViews();

        bindEvent("on");
    }

    var initDOM = () =>{
        console.log("initDOM");
        androidInput = document.getElementById("android_input_txt");
        showToastBtn = document.getElementById("show_toast");
        TakePicBtn = document.getElementById("take_picture");
    }

    var initViews = () => {
        console.log("initViews");

    }

    var bindEvent = method =>{
        console.log("bindEvent-" + method);
        if(method == "on"){
            showToastBtn.addEventListener("click", showToast, false);
            TakePicBtn.addEventListener("click", takePicture, false);
        }else if(method == "off"){
            showToastBtn.removeEventListener("click", showToast, false);
            TakePicBtn.removeEventListener("click", takePicture, false);
        }else{

        }
    }

    var inputAndroidDataToWebView = (str) =>{
        console.log("inputAndroidDataToWebView-" + str);
        androidInput.value = str;
    }

    var showToast = () =>{
        console.log("showToast");

        // 约定的url协议为：js://webview?action=showToast&params=""
        document.location = "js://webview?action=showToast&params=" + androidInput.value;

        try {
            let json = {
                "action":"showToast",
                "params":androidInput.value
            }
            // window.映射字符串.java方法名
            window.fxp.invokeAndroid(JSON.stringify(json));
        } catch (error) {
            console.log(error);
        }
    }

    var takePicture = () =>{
        console.log("takePicture");
        try {
            let json = {
                "action":"takePicture",
                "params":""
            }
            window.fxp.invokeAndroid(JSON.stringify(json));
        } catch (error) {
            console.log(error);
        }
    }

    return{
        init: init,
        inputData:inputAndroidDataToWebView
    }
}();

main.init();