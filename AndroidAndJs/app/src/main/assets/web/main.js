
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
            TakePicBtn.addEventListener("click", TakePicture, false);
        }else if(method == "off"){
            showToastBtn.removeEventListener("click", showToast, false);
            TakePicBtn.removeEventListener("click", TakePicture, false);
        }else{

        }
    }

    var inputAndroidDataToWebView = (str) =>{
        console.log("inputAndroidDataToWebView-" + str);
        androidInput.value = str;
    }

    var showToast = () =>{
        console.log("showToast");

    }

    var TakePicture = () =>{
        console.log("TakePicture");

    }

    return{
        init: init,
        inputData:inputAndroidDataToWebView
    }
}();

main.init();