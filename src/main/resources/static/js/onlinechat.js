function getCookie() {
    var cookiesKey = "username";
    var cookie = document.cookie;
    var arr = cookie.split(";");
    console.log(arr)
    for (var i = 0; i < arr.length; i++) {
        var res = arr[i].split("=")[0];
        res = res.replace(/\s+/g, "");
        if (res === cookiesKey) {
            return arr[i].split("=")[1];
        } else {
            console.log("ERORR  +-- Local://ToDoing Func:getCookie");
        }
    }
};
function splitMsg(msg){
    var arr = msg.split(">")[1];
    arr = arr.substring(1);
    return arr;
};
//一访问页面,就加载数据
window.onload = function () {
    var nowuser = getCookie();
    var radios = document.getElementsByName("username");

    //TODO 仅DEMO使用
    if(nowuser==="张三"){
        for(var i in radios){
            if(radios[i].value !="张三"){
                radios[i].checked = true;
            }
        }
    }
    if(nowuser==="李四"){
        for(var i in radios){
            if(radios[i].value !="李四"){
                radios[i].checked = true;
            }
        }
    }
    //TODO END

    var val = null;
    for (i in radios) {
        if (radios[i].checked) {
            val = radios[i].value;
            break;
        }
    }
    //代表没有选择
    if (val == null) {
        console.log("ERROR  +-- Local:onlinechat func:onload");
        val = "李四"; //测试用
    }
    $.ajax({
        type: "post",
        url: "/onlinechat/select/",
        data: {
            fromuser: nowuser,
            touser: val
        },
        success: function (response) {
            console.log(response);
            response = JSON.parse(response);
            var info = response.result;
            console.log(info);
            for(var i in info){
                if(info[i].fromUser === nowuser){
                    TotaladdRight(info[i].fromUser,info[i].message);
                }else{
                    addLeft(info[i].fromUser,info[i].message)
                }
            }
        },
        error: function (err) {
            console.log("ERROR +-- Local:onlinechat func:onload-ajax");
        }
    });
}

function submit() {

}

//添加对话框右边
function addRight(username) {
    var msg = document.getElementById("msg").value;
    console.log(msg);
    var div = document.getElementById("onlinechat");

    var externalDiv = document.createElement("div");
    externalDiv.className = "dialog2";
    var internalDiv = document.createElement("div");
    internalDiv.className = "word2";

    var showNameDiv = document.createElement("div");
    showNameDiv.textContent = username;
    showNameDiv.className = "avatar2";

    var br = document.createElement("br");
    var radio = document.createElement("input");
    radio.type = "radio";
    radio.name = "delete";
    showNameDiv.append(br);
    showNameDiv.append(radio);
    internalDiv.textContent = msg;

    externalDiv.appendChild(internalDiv);
    externalDiv.appendChild(showNameDiv);
    div.appendChild(externalDiv);
}

//添加从服务器获取的对话框右边
function TotaladdRight(username,msg) {
    msg = splitMsg(msg);
    var div = document.getElementById("onlinechat");

    var externalDiv = document.createElement("div");
    externalDiv.className = "dialog2";
    var internalDiv = document.createElement("div");
    internalDiv.className = "word2";

    var showNameDiv = document.createElement("div");
    showNameDiv.textContent = username;
    showNameDiv.className = "avatar2";

    var br = document.createElement("br");
    var radio = document.createElement("input");
    radio.type = "radio";
    radio.name = "delete";
    showNameDiv.append(br);
    showNameDiv.append(radio);
    internalDiv.textContent = msg;

    externalDiv.appendChild(internalDiv);
    externalDiv.appendChild(showNameDiv);
    div.appendChild(externalDiv);
}
//添加对话框左边
function addLeft(username,msg) {
    msg = splitMsg(msg);
    //var div = document.getElementsByClassName("chat");
    var div = document.getElementById("onlinechat");

    var externalDiv = document.createElement("div");
    externalDiv.className = "dialog1";
    var internalDiv = document.createElement("div");
    internalDiv.className = "word1";

    var showNameDiv = document.createElement("div");
    showNameDiv.textContent = username;
    showNameDiv.className = "avatar1";

    internalDiv.textContent = msg;

    externalDiv.appendChild(showNameDiv);
    externalDiv.appendChild(internalDiv);
    div.appendChild(externalDiv);
}
