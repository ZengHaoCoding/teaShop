function get_user_info() {
    var response = "";
    var settings = {
        "url": "http://127.0.0.1:8085/user/getUserInfo",
        "method": "GET",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
        "dataType": "json",
        "headers": {
            "token": window.localStorage.getItem("token")
        },
    };
    
    $.ajax(settings).done(function (r) {
        response = r;
    });
    return response;
}

function printUserRole(role) {
    switch (role) {
        case "0": return "超级管理员";
            break;
        case "1": return "管理员";
            break;
        case "2": return "卖家";
            break;
        case "3": return "买家";
            break;
    }
}

function toSeller() {
    window.location.replace("./toSeller.html");
}

function islogin_div() {
    var user_info = get_user_info().data[0];
    if (window.localStorage.getItem("role") != user_info.role) {
        window.localStorage.setItem("role", user_info.role);
    }
    var islogin = "";
    islogin += "<div id=\"per_info\">";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">　　&nbsp;&nbsp;UID：" + user_info.uid + "<\/span>";
    islogin += "        <br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">&nbsp;&nbsp;&nbsp;&nbsp;用户名：" + user_info.username + "<\/span>";
    islogin += "        <br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">用户权限：" + printUserRole(user_info.role);
    if (user_info.role == "3") {
        islogin += "&nbsp;&nbsp;<span id=\"toSeller\" onClick=\"toSeller()\" class=\"badge badge-warning\">成为卖家</span>";
    }
    islogin += "        <\/span><br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">联系方式：" + user_info.contact_info + "<\/span>";
    islogin += "        <br \/>";
    islogin += "        <a href=\"user\/address_manage.html\"><p style=\"margin: 0 auto 0 5%\" class=\"text-right\">&gt;&gt;地址管理<\/p><\/a>";
    islogin += "    <\/div>";
    islogin += "    <hr \/>";
    islogin += "    <div style=\"width: 60%; height: 100%; margin-left: 20%; margin-top: 10%\">";
    islogin += "        <a href=\"cart.html\"><button type=\"button\" class=\"btn btn-success btn-lg btn-block\">购物车<\/button><\/a>";
    islogin += "        <br \/>";
    islogin += "        <a href=\"order.html\"><button type=\"button\" class=\"btn btn-info btn-lg btn-block\">我的订单<\/button><\/a>";
    islogin += "        <br \/>";
    islogin += "    <\/div>";
    return islogin;
}


var unlogin = "";
unlogin += "<p class=\"text-center\" style=\"margin-top: 5%;\">状态：未登入<\/p>";
unlogin += "    <hr \/>";
unlogin += "    <div style=\"width:60%; height:100%; margin-left:20%;\">";
unlogin += "        <a href=\"user\/login.html\"><button type=\"button\" class=\"btn btn-primary btn-lg btn-block\">登入<\/button><\/a>";
unlogin += "        <br \/>";
unlogin += "        <a href=\"user\/register.html\"><button type=\"button\" class=\"btn btn-secondary btn-lg btn-block\">注册<\/button><\/a>";
unlogin += "        <br \/>";
unlogin += "    <\/div>";

var div = "";
div += "<div style=\"";
div += "          float: left;";
div += "          width: 20%;";
div += "          margin-top: 5px;";
div += "          overflow: hidden;";
div += "          position: fixed;";
div += "          border:1px solid rgba(0,0,0,.125);";
div += "          border-radius:.25rem";
div += "          \">";


if (window.localStorage.getItem("token") != null) {
    div += islogin_div();
} else {
    div += unlogin;
}
div += "</div>"
document.writeln(div);
