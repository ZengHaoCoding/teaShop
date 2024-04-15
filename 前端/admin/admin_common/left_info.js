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

function toUserManager() {
    window.location.replace("./userManager.html");
}
function toAllOrder() {
    window.location.replace("./allorder.html");
}
function toAllGoods() {
    window.location.replace("./allGoods.html");
}
function toApplySellerList() {
    window.location.replace("./applySellerList.html");
}
function toBalanceWithdrawal() {
    window.location.replace("./balanceWithdrawal.html");
}
function toSetHomePage() {
    window.location.replace("./setHomePage.html");
}

function islogin_div() {
    var user_info = get_user_info().data[0];
    var seller_info = get_seller_info().data[0];
    var islogin = "";
    islogin += "<div id=\"per_info2\">";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">　　&nbsp;&nbsp;UID：" + user_info.uid + "<\/span>";
    islogin += "        <br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">用户权限：" + printUserRole(user_info.role);
    islogin += "        <\/span><br \/>";
    islogin += "        <span style=\"margin: 0 auto 0 5%;white-space:nowrap;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;余额：" + seller_info.balance + "<\/span>";
    islogin += "        <br \/>";
    islogin += "    <\/div>";
    islogin += "    <div style=\"width: 100%; height: 100%; margin-left: 0%; margin-top: 5%\">";
    islogin += "<ul style=\"width: 100%;\" class=\"list-group\">";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toUserManager()\">用户管理<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toAllOrder()\">全部订单记录<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toAllGoods()\">全部商品信息<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toApplySellerList()\">卖家权限审核<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toBalanceWithdrawal()\">卖家提现审核<\/li>";
    islogin += "  <li class=\"list-group-item\" style=\"cursor: pointer;\" onClick=\"toSetHomePage()\">首页推荐页设置<\/li>";
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
