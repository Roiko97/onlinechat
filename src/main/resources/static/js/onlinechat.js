//TODO 设置socket相关内容
//获得发送方的用户名

var principal = getCookie();
//url地址
var socketURL = 'ws://' + window.location.host
    + '/websocket/'+principal;
var websocket = $.websocket(socketURL, {
    open : function() {
        // when the socket opens
        console.log("服务器准备就绪");
    },
    close : function() {
        // when the socket closes
        console.log("服务器已经关闭");
    },
    //收到服务端推送的消息处理
    events : {
        'radio' : function(event) {
            var res = $.parseJSON(event.data);
            addLeft(res.fromuser,res.content,event.type);

            //滚动条自动跟随
            var div_chat = document.getElementById("onlinechat");
            div_chat.scrollTop = div_chat.scrollHeight;
        },
        'notice' : function(event) {
            console.info($.parseJSON(event.data));
            var res = $.parseJSON(event.data);
            var id = res.id;
            var deleteParent = document.getElementById(id);
            deleteParent.parentNode.removeChild(deleteParent);

            /**
             * 新增提醒，提醒内容：对方撤回了一条内容
             */
            var parent_div = document.getElementById("onlinechat");
            var class_tip = document.createElement("div");
            class_tip.className="tip dialog1";
            class_tip.style.margin="20px 125px";
            var span_cellTip = document.createElement("span");
            span_cellTip.className="cellTip";
            span_cellTip.textContent="对方撤回了一条内容";
            class_tip.appendChild(span_cellTip);
            parent_div.appendChild(class_tip);
            // 完成对提示内容的增加

            //滚动条自动跟随
            parent_div.scrollTop = parent_div.scrollHeight;

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
//TODO END

//TODO 获得Cookie函数
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
//TODO END

//TODO 将字符串进行切割函数
//示例<Date> message ===> message
function splitMsg(msg){
    var newmsg = msg.split("}")[1];
    newmsg = newmsg.substring(1);
    return newmsg;
};
//TODO END

//TODO 当页面加载，就读取相应的数据
window.onload = function () {

    //根据Cookie获取当前登陆的用户
    var nowuser = getCookie();

    //该radios对象为界面最上方的聊天对象
    //下述代码段的目的是为了确定【在显示功能上】选择发送对象
    var radios = document.getElementsByName("username");
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
    //根据上面radios功能，获取发送对象的名称，保存在val中
    var val = null;
    for (i in radios) {
        if (radios[i].checked) {
            val = radios[i].value;
            break;
        }
    }


    //向服务器发送请求
    $.ajax({
        type: "post",
        url: "/onlinechat/select/",
        data: {
            fromuser: nowuser,
            touser: val
        },
        success: function (response) {

            //将查询到的内容进行格式化操作（返回是list）
            response = JSON.parse(response);
            var info = response.result;

            //通过判断当前用户，实现判断内容添加左边还是右边
            //注意：这里添加的内容是含有时间戳的
            for(var i in info){
                if(info[i].fromUser === nowuser){
                    addRight(info[i].fromUser,info[i].message,info[i].type);
                }else{
                    addLeft(info[i].fromUser,info[i].message,info[i].type)
                }

                //滚动条自动跟随
                var div_chat = document.getElementById("onlinechat");
                div_chat.scrollTop = div_chat.scrollHeight;
            }
        },
        error: function (err) {
            console.log("ERROR +-- Local:onlinechat func:onload-ajax");
        }
    });
}
//TODO END

//TODO 对话框右侧内容进行增加
function addRight(username,msg,type) {
    console.log("now type = "+type);
    var originMsg = msg; //原始信息，含有时间戳
    msg = splitMsg(msg); //去除时间戳的信息，将用于显示
    /*
        页面结构：

        <div class="dialog2">
            <div class="word2">
                 {2020-07-11 21:43:01} 李四你好，我是张三
            </div>
            <div class="avatar2">张三
                    <br />
                <input type="radio" name="delete">
            </div>
        </div>
    */
    var div = document.getElementById("onlinechat");

    var externalDiv = document.createElement("div");
    externalDiv.className = "dialog2";
    externalDiv.id = originMsg;

    var internalDiv = document.createElement("div");
    internalDiv.className = "word2";
    //如果文本类型是文件，则需要附加点击事件
    if(type ==="file"){
        var a = document.createElement("a");
        a.href = "onlinechat/download?id="+encodeURI(originMsg);
        a.innerText=msg;
        a.style.textDecoration="none";
        internalDiv.appendChild(a);
        //internalDiv.setAttribute("onclick","download()");
    }else{
        internalDiv.textContent = msg;
    }


    var showNameDiv = document.createElement("div");
    showNameDiv.className = "avatar2";
    showNameDiv.textContent = username;

    var br = document.createElement("br");

    var radio = document.createElement("input");
    radio.type = "radio";
    radio.name = "delete";

    showNameDiv.append(br);
    showNameDiv.append(radio);

    externalDiv.appendChild(internalDiv);
    externalDiv.appendChild(showNameDiv);

    div.appendChild(externalDiv);
}
//TODO END

//TODO 对话框左侧内容进行添加
//添加对话框左边
function addLeft(username,msg,type) {
    console.log("now type = "+type);
    var originMsg = msg;
    msg = splitMsg(msg);

    /*
        页面结构：

        <div class="dialog1">
            <div class="avatar1">李四</div>
            <div class="word1">
                    李四你好
            </div>
        </div>
    */
    var div = document.getElementById("onlinechat");

    var externalDiv = document.createElement("div");
    externalDiv.className = "dialog1";
    externalDiv.id = originMsg;

    var showNameDiv = document.createElement("div");
    showNameDiv.className = "avatar1";
    showNameDiv.textContent = username;

    var internalDiv = document.createElement("div");
    internalDiv.className = "word1";
    if(type ==="file"){ //如果文本类型是文件，则添加点击事件
        var a = document.createElement("a");
        a.href = "onlinechat/download?id="+encodeURI(originMsg);
        a.innerText=msg;
        a.style.textDecoration="none";
        internalDiv.appendChild(a);
        //internalDiv.setAttribute("onclick","download()");
    }else{
        internalDiv.textContent = msg;
    }

    externalDiv.appendChild(showNameDiv);
    externalDiv.appendChild(internalDiv);

    div.appendChild(externalDiv);
}
//TODO END

//TODO 提交按钮
function submit() {

    //获得文件的全路径（路径+文件名）（可能该值为"")
    var path = document.getElementById("upload").value;
    //获得将要发送的信息
    var sendMsg = document.getElementById("msg").value;
    //获得当前登陆的用户名
    var nowuser = getCookie();
    //获取接收信息的对象名（存值在val处）
    var radios = document.getElementsByName("username");
    var val = null;
    for (i in radios) {
        if (radios[i].checked) {
            val = radios[i].value;
            break;
        }
    }
    var type;
    if(path ===""){
        type="text";
    }else{
        type = "file";
    }

    $.ajax({
        type:"post",
        url:"/onlinechat/save",
        data:{
            fromuser:nowuser,
            touser:val,
            msg:sendMsg,
            type:type
        },
        success:function (response) {

            //将文本内容进行JSON序列化
            response = JSON.parse(response);
            console.log(response);
            addRight(nowuser,response.resMsg,response.type);
            if(response.type === "file"){
                //将文件发送至服务端，进行文件存储
                commitButton =  document.getElementById("filecommit");
                commitButton.click();
            }

            //让下拉滚动条自动跟随
            var div_chat = document.getElementById("onlinechat");
            div_chat.scrollTop = div_chat.scrollHeight;

            //websocket.send(val,response.resMsg);
            var myjson = {
                msg:response.resMsg,
                type:response.type
            };
            websocket.send(val,JSON.stringify(myjson));
            //websocket.send(val,"{'张三':18,'李四':20}");
            document.getElementById("msg").value= "";
            document.getElementById("upload").value=null;

        },
        error:function(err){
            console.log("ERROR Local:onlinechat Func:submit");
        }
    });
}
//TODO END

//TODO 撤销函数，将用户发送的信息进行撤回
function msgdelete() {

    //获取所有的按钮,并且判断是哪个按钮被点击的，找到其值
    var radios = document.getElementsByName("delete");
    var parent;
    for (var i in radios) {
        if (radios[i].checked) {
            parent = radios[i].parentNode;
            parent = parent.parentNode;
            break;
        }
    }

    //获取接收对象的姓名
    var sendRadios = document.getElementsByName("username");
    var val = null;
    for (var i in sendRadios) {
        if (sendRadios[i].checked) {
            val = sendRadios[i].value;
            break;
        }
    }
    //记录将要撤回的内容，为后续重新编辑准备 <此处内容已经进行格式化>
    var recall_msg = splitMsg(parent.id);
    $.ajax({
        type:"post",
        url:"onlinechat/recall",
        data:{
            msg:parent.id
        },
        success:function (response) {
            //将该内容进行删除
            parent.parentNode.removeChild(parent);

            /*
            * 增加输出提示，并且允许用户重新编辑
            * */
            var parent_onlinechat = document.getElementById("onlinechat");
            var div_tip = document.createElement("div");
            div_tip.className="tip dialog2";
            div_tip.style.width="190px";
            var span_cellTip = document.createElement("span");
            span_cellTip.className="cellTip";
            span_cellTip.textContent="我撤回了一条内容";
            var label_re_edit = document.createElement("label");
            label_re_edit.className="re_edit";
            label_re_edit.setAttribute("onclick","re_edit(this)");
            label_re_edit.textContent="重新编辑"
            //注意，这里存储的是name，不是id，注意与其他标签进行区别.
            //注意：这里设置name的方式和id的方式不同
            label_re_edit.setAttribute("name",recall_msg);

            div_tip.appendChild(span_cellTip);
            div_tip.appendChild(label_re_edit);
            parent_onlinechat.appendChild(div_tip);
            //重新编辑功能结束

            //让下拉滚动条自动跟随
            parent_onlinechat.scrollTop = parent_onlinechat.scrollHeight;

            var myjson = {
                msg:parent.id,
                type:"recall"
            };
            websocket.send(val,JSON.stringify(myjson));
        },
        error:function (err) {

        }
    });
}
//TODO END

//TODO 将文件的全路径进行截断，保留文件名，并且显示在发送栏中
function showFile(){
    var path = document.getElementById("upload").value;
    index = path.lastIndexOf("\\");
    path = path.substring(index+1);
    document.getElementById("msg").value = path;
}
//TODO END

//TODO 将重新编辑的内容显示在textare内，提供用户重新编辑其文本
function re_edit(obj) {
    //这里的obj实际为整个label信息，需要通过getAttribute来获取，其中参数代表标签的属性
    var re_edit_msg = obj.getAttribute("name");
    console.log(re_edit_msg);
    document.getElementById("msg").value = re_edit_msg;
}
//TODO END

//TODO 键盘的回车事件，发送消息
document.onkeydown = function (event) {
    var e = event || window.event;
    if(!e.shiftKey && e.keyCode ==13){
        $("#send_msg").click();
    }
};
//TODO END

//TODO 显示emojo的表格
function showTable(){
    var table = document.getElementById("emojo");
    table.style.display="block";
}
//TODO END

//TODO 按键事件，当点击事件不在表格/图片上时，emojo的表格自动关闭
$(document).click(function(e){
    var target = e?e.target:window.event.srcElement;
    var name = target.getAttribute("name");
    if(target.id !="emojo" && name !="emojos" && target.id!="emojoTip"){
        var table = document.getElementById("emojo");
        table.style.display="none";
    }
});
//TODO END

//TODO 往聊天框中添加emojo表情
function addEmojo(obj){
    var img = obj.getAttribute("value");
    document.getElementById("msg").value +=img;
    var table = document.getElementById("emojo");
    table.style.display="none";
}
//TODO END