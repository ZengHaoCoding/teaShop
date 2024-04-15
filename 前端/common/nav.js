// 原来蓝色导航栏颜色代码#e3f2fd
// 棕色1 #C88160
// 棕色2 #854630
var url = "http://172.17.28.46/phpShop/"
var role = window.localStorage.getItem("role");
var navStart = "";
navStart += "<nav class=\"navbar navbar-dark bg-primary\" style=\"background-color: #e3f2fd\">";
navStart += "                    <a class=\"navbar-brand\" href=\"index.html\"> <img src=\"img\/logo.png\" width=\"30\" height=\"30\"";
navStart += "                            class=\"d-inline-block align-top\" alt=\"\" \/>清饮吧<\/a>";
navStart += "";
navStart += "                    <ul class=\"nav justify-content-end\">";
navStart += "                        <li class=\"nav-item\">";
navStart += "                            <a class=\"nav-link active\" style=\"color: #ffffff\" href=\"index.html\"><img src=\"img\/main.svg\"";
navStart += "                                    alt=\"Bootstrap\" width=\"16\" height=\"16\">首页<\/a>";
navStart += "                        <\/li>";

var navBuyer = "";
navBuyer += "                        <li class=\"nav-item\">";
navBuyer += "                            <a class=\"nav-link\" style=\"color: #ffffff\" href=\"cart.html\"><img src=\"img\/shoppingCar.svg\"";
navBuyer += "                                    alt=\"Bootstrap\" width=\"16\" height=\"16\">购物车<\/a>";
navBuyer += "                        <\/li>";
navBuyer += "                        <li class=\"nav-item\">";
navBuyer += "                            <a class=\"nav-link\" style=\"color: #ffffff\" href=\"order.html\"><img src=\"img\/order.svg\"";
navBuyer += "                                    alt=\"Bootstrap\" width=\"16\" height=\"16\">我的订单<\/a>";
navBuyer += "                        <\/li>";

var navSeller = "";
navSeller += "                        <li class=\"nav-item\">";
navSeller += "                            <a class=\"nav-link\" style=\"color: #ffffff\" href=\".\/seller\/index.html\"><img src=\"img\/admin.svg\"";
navSeller += "                                    alt=\"Bootstrap\" width=\"16\" height=\"16\">卖家端<\/a>";
navSeller += "                        <\/li>";

var navAdmin = "";
navAdmin += "                        <li class=\"nav-item\">";
navAdmin += "                            <a class=\"nav-link\" style=\"color: #ffffff\" href=\".\/admin\/index.html\"><img src=\"img\/admin.svg\"";
navAdmin += "                                    alt=\"Bootstrap\" width=\"16\" height=\"16\">管理端<\/a>";
navAdmin += "                        <\/li>";

var navLogOut = "";
navLogOut += "                        <li class=\"nav-item\">";
navLogOut += "                            <a class=\"nav-link\" style=\"color: #ffffff\" href=\"user\/log_out.html?action=1\"><img";
navLogOut += "                                    src=\"img\/logOut.svg\" alt=\"Bootstrap\" width=\"16\" height=\"16\">退出登入<\/a>";
navLogOut += "                        <\/li>";

var navEnd = "";
navEnd += "                    <\/ul>";
navEnd += "";
navEnd += "                <\/nav><hr />";


var nav = navStart;
if (role != null) {
    if (role <= "3") {
        nav += navBuyer;
    }
    if (role <= "2") {
        nav += navSeller;
    }
    if (role == "1") {
        nav += navAdmin;
    }
    nav += navLogOut;
}
nav += navEnd;
document.writeln(nav);
