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
    var newmsg = msg.split(">")[1];
    newmsg = newmsg.substring(1);
    console.log("this is splitMsg:"+newmsg);
    return newmsg;
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
    //TODO add delete func
    externalDiv.id = msg;
    //TODO END
    div.appendChild(externalDiv);
}

//添加从服务器获取的对话框右边
function TotaladdRight(username,msg) {
    var originMsg = msg;
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
    //TODO add delete func
    externalDiv.id = originMsg;
    //TODO END
    div.appendChild(externalDiv);
}
//添加对话框左边
function addLeft(username,msg) {
    var originMsg = msg;
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
    //TODO add delete func
    externalDiv.id = originMsg;
    //TODO END
    div.appendChild(externalDiv);
}

//TODO SOCKET
//获得发送方的用户名

var principal = getCookie();
//url地址
var socketURL = 'ws://' + window.location.host
    + '/websocket/'+principal;
var websocket = $.websocket(socketURL, {
    open : function() {
        // when the socket opens
        //alert("open");
        console.log("服务器准备就绪");
    },
    close : function() {
        // when the socket closes
        //alert("close");
        console.log("服务器已经关闭");
    },
    //收到服务端推送的消息处理
    events : {
        'radio' : function(event) {
            var res = $.parseJSON(event.data);
            //var notimeMsg = "<> "+res.content;
            //addLeft(res.fromuser,notimeMsg);
            addLeft(res.fromuser,res.content);
        },
        'notice' : function(event) {
            console.info($.parseJSON(event.data));
            var res = $.parseJSON(event.data);
            var id = res.id;
            var deleteParent = document.getElementById(id);
            deleteParent.parentNode.removeChild(deleteParent);
            // if(res.needrefresh == true){
            //     $.ajax({
            //         type:"post",
            //         url:"onlinechat/reflush",
            //         data:{
            //
            //         },
            //         success:function (response) {
            //             //却有奇效
            //             location.replace(location);
            //             console.log("now already reflush");
            //         },
            //         error:function(error){
            //             console.log("ERROR Local +-- onlinechat Func:global-events --+");
            //         }
            //     })
            // }
        },
        //... more custom type of message
    }
});
// 监听窗口关闭事件,当窗口关闭时,主动去关闭websocket连接,防止连接还没断开就关闭窗口,server端会抛异常.
window.onbeforeunload = function() {
    websocket.close();
}

// 监听浏览器回退事件
window.onpagehide = function(event) {
    websocket.close();
}
//TODO
function submit() {

    //全路径
    var path = document.getElementById("upload").value;
    //获得发送信息
    var sendMsg = document.getElementById("msg").value;
    //获得当前登陆用户名
    var nowuser = getCookie();
    //获得发送对象
    var radios = document.getElementsByName("username");
    var val = null;
    for (i in radios) {
        if (radios[i].checked) {
            val = radios[i].value;
            break;
        }
    }

    $.ajax({
        type:"post",
        url:"/onlinechat/save",
        data:{
            fromuser:nowuser,
            touser:val,
            msg:sendMsg
        },
        success:function (response) {
            console.log("SUCCESS Local:onlinechat Func:submit");
            response = JSON.parse(response);
            if(path===""){ //上传文本信息
                TotaladdRight(nowuser,response.resMsg);
                //由于addRigtht不包含时间信息，因此淘汰
                //addRight(nowuser);
            }else{ //上传图片信息
                //开始进行样式的添加
                addRigthFileMsg(nowuser,response.resMsg);
                //添加点击事件
                commitButton =  document.getElementById("filecommit");
                commitButton.click();
            }
            websocket.send(val,response.resMsg);
            document.getElementById("msg").value= "";
            document.getElementById("upload").value=null;

        },
        error:function(err){
            console.log("ERROR Local:onlinechat Func:submit");
        }
    });
}

//TODO DELETE FUNCTION
function msgdelete() {
    var radios = document.getElementsByName("delete");
    var parent;
    for (var i in radios) {
        if (radios[i].checked) {
            parent = radios[i].parentNode;
            parent = parent.parentNode;
            console.log(parent.id);
            break;
        }
    }

    //TODO SEND RADIO
    var sendRadios = document.getElementsByName("username");
    var val = null;
    for (var i in sendRadios) {
        if (sendRadios[i].checked) {
            val = sendRadios[i].value;
            break;
        }
    }
    //获得了parent信息
    var obj = val +"&"+parent.id;
    $.ajax({
       type:"post",
       url:"onlinechat/recall",
       data:{
           msg:parent.id
       },
        success:function (response) {
           parent.parentNode.removeChild(parent);
           websocket.send(obj);
        },
        error:function (err) {

        }
    });
}

//TODO 文件上传
function showFile(){
    var path = document.getElementById("upload").value;
    index = path.lastIndexOf("\\");
    path = path.substring(index+1);
    document.getElementById("msg").value = path;
}

//添加从服务器获取的对话框右边
function addRigthFileMsg(username,msg) {

    var originMsg = msg;
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
    //TODO add delete func
    externalDiv.id = originMsg;
    //TODO END
    div.appendChild(externalDiv);
}
// TODO END