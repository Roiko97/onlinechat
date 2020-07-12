var principal = getCookie();
console.log("principal = "+principal);
var socketURL = 'ws://' + window.location.host
    + '/websocket/'+principal;

websocket = $.websocket(socketURL, {
    open : function() {
        // when the socket opens
        alert("open");
    },
    close : function() {
        // when the socket closes
        alert("close");
    },
    //收到服务端推送的消息处理
    events : {
        'radio' : function(event) {
            console.info($.parseJSON(event.data));
        },
        'notice' : function(event) {
            console.info($.parseJSON(event.data));
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

//发送消息
function send() {
    websocket.send("李四","你好李四，我是张三");
}