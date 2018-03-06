
var main = function(){

    var init = () =>{
        console.log("init");

        bindEvent("on");
    }

    var bindEvent = method =>{
        if(method == "on" || method == "off"){
            console.log("bindEvent-" + method);
        }
    }

    var inputAndroidDataToWebView = (str) =>{
        document.getElementById("android_input_txt").value = str;
        alert(str);
    }

    return{
        init: init,
        inputData:inputAndroidDataToWebView
    }
}();

main.init();