
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

    return{
        init: init
    }
}();

main.init();