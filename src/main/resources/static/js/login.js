function login() {
    var username = document.getElementById("username").value;
    $.ajax({
       type:"POST",
       url:"http://localhost:8080/uselogin",
        data:{
            name : username
        },
        success:function(response){
           console.log(response);
           document.cookie ="username="+username+";path=/;";
           console.log("SUCCESS  +-- Local:login.js func login --+");
           if(response == false){
               alert("登陆失败，请确认账号/密码");
           }else{
               window.location.href="/home";
           }
        },
        error:function (err) {
           console.log("ERROR   +-- Local:login.js func login --+");
        }

    });
}