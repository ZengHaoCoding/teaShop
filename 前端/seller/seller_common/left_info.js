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

function get_seller_info() {
    var response = "";
    var settings = {
        "url": "http://127.0.0.1:8085/seller/getSellerInfo",
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

function toSellOrder() {
    window.location.replace("./sellOrder.html");
}
function toGoods() {
    window.location.replace("./goods.html");
}
function toWorkerDoc() {
    window.location.replace("./workdocsystem.html");
}
function toWithdrawal() {
    window.location.replace("./withdrawal.html");
}

function islogin_div() {
    var user_info = get_user_info().data[0];
    var seller_info = get_seller_info().data[0];
    if (window.localStorage.getItem("role") != user_info.role) {
        window.localStorage.setItem("role", user_info.role);
    }
    var islogin = "";
    islogin += "<div id=\"per_info2\">";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">　　&nbsp;&nbsp;UID：" + user_info.uid + "<\/span>";
    islogin += "        <br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">店铺名称：" + seller_info.seller_name + "<\/span>";
    islogin += "        <br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">用户权限：" + printUserRole(user_info.role);
    if (user_info.role == "3") {
        islogin += "&nbsp;&nbsp;<span id=\"toSeller\" onClick=\"toSeller()\" class=\"badge badge-warning\">成为卖家</span>";
    }
    islogin += "        <\/span><br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;余额：" + seller_info.balance + "<\/span>";
    islogin += "        <br \/>";
    islogin += "    <\/div>";
    islogin += "    <div style=\"width: 100%; height: 100%; margin-left: 0%; margin-top: 5%\">";
    islogin += "<ul style=\"width: 100%;\" class=\"list-group\">";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toSellOrder()\">销售记录<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toWorkerDoc()\">工单系统<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toGoods()\">我的商品<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toWithdrawal()\">提现余额<\/li>";
    islogin += "<\/ul>";
    islogin += "    <\/div>";
    return islogin;
}


var unlogin = "";
unlogin += "<p class=\"text-center\" style=\"margin-top: 5%;\">状态：未登入<\/p>";
unlogin += "    <hr \/>";
unlogin += "    <div style=\"width:60%; height:100%; margin-left:20%;\">";
unlogin += "        <a href=\"..\/user\/login.html\"><button type=\"button\" class=\"btn btn-primary btn-lg btn-block\">登入<\/button><\/a>";
unlogin += "        <br \/>";
unlogin += "        <a href=\"..\/user\/register.html\"><button type=\"button\" class=\"btn btn-secondary btn-lg btn-block\">注册<\/button><\/a>";
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
