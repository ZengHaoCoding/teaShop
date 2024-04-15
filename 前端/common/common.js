//全局js
$(document).ready(function () {


});

function login(username, password) {
    var request_status = false;
    var settings = {
        "url": "http://127.0.0.1:8085/user/login",
        "method": "POST",
        "timeout": 0,
        "dataType": "json",
        "data": JSON.stringify({
            "username": username,
            "password": password
        }),
        "async": false,
        "crossDomain": true,
        "headers": {
            "Content-Type": "application/json"
        }
    };

    $.ajax(settings).done(function (response) {
        if (response.msg == "success") {
            window.localStorage.setItem("token", response.data.token);
            window.localStorage.setItem("role", response.data.role);
            request_status = true;
        }
    });
    return request_status;
}

function register(username, password, contact_info) {
    var request_status = false;
    var settings = {
        "url": "http://127.0.0.1:8085/user/register",
        "method": "POST",
        "timeout": 0,
        "data": JSON.stringify({
            "username": username,
            "password": password,
            "contact_info": contact_info
        }),
        "async": false,
        "crossDomain": true,
        "headers": {
            "Content-Type": "application/json"
        }
    };

    $.ajax(settings).done(function (response) {
        if (response.msg == "success") {
            if (login(username, password)) {
                request_status = true;
            }
        }
    });
    return request_status;
}


function get_user_info() {
    var settings = {
        "url": "http://127.0.0.1:8085/user/getUserInfo",
        "method": "GET",
        "dataType": "json",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
        "headers": {
            "token": window.localStorage.getItem("token")
        },
    };

    $.ajax(settings).done(function (response) {
        console.log(response);
    });
}


function get_address_info() {
    var r = "";
    var settings = {
        "url": "http://127.0.0.1:8085/user/getAddress",
        "method": "GET",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
        "headers": {
            "token": window.localStorage.getItem("token")
        },
    };

    $.ajax(settings).done(function (response) {
        r = response;
    });
    return r;
}


function get_goods_info(goods_id) {
    var r = "";
    var settings = {
        "url": "http://127.0.0.1:8085/goods/" + goods_id,
        "method": "GET",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
    };

    $.ajax(settings).done(function (response) {
        r = response;
    });
    return r;
}


function get_goods_conment(goods_id) {
    var r = "";
    var settings = {
        "url": "http://127.0.0.1:8085/goods/comment/" + goods_id,
        "method": "GET",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
    };

    $.ajax(settings).done(function (response) {
        r = response;
    });
    return r;
}


function printStart(num) {
    var starts = ""
    for (let index = 0; index < num; index++) {
        starts += "⭐";
    }
    return starts;
}


function pay_order(order_id) {
    var status = false;
    var settings = {
        "url": "http://127.0.0.1:8085/order/payOrder",
        "method": "POST",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
        "data": JSON.stringify(order_id),
        "headers": {
            "token": window.localStorage.getItem("token"),
            "Content-Type": "application/json"
        },
    };

    $.ajax(settings).done(function (response) {
        status = true;
    });
    return status;
}

function cancel_order(order_id) {
    var status = false;
    var settings = {
        "url": "http://127.0.0.1:8085/order/cancelOrder",
        "method": "POST",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
        "data": JSON.stringify(order_id),
        "headers": {
            "token": window.localStorage.getItem("token"),
            "Content-Type": "application/json"
        },
    };

    $.ajax(settings).done(function (response) {
        status = true;
    });
    return status;
}


function printOrderName(order) {
    var result = "";
    for (let index = 0; index < order.length; index++) {
        result += "                                <p class=\"card-text\"";
        result += "                                    style=\"display: -webkit-box;-webkit-line-clamp: 1;-webkit-box-orient: vertical;overflow: hidden;\">";
        result += order[index].goods_name;
        result += "                                <\/p>";
    }
    return result;
}

function printOrderNumber(order) {
    var result = "";
    for (let index = 0; index < order.length; index++) {
        result += "                                <p class=\"card-text\"";
        result += "                                    style=\"display: -webkit-box;-webkit-line-clamp: 1;-webkit-box-orient: vertical;overflow: hidden;\">";
        result += order[index].number;
        result += "                                <\/p>";

    }
    return result;
}


function printOrderStatus(status) {
    switch (status) {
        case "0": return "待支付";
            break;
        case "1": return "待发货";
            break;
        case "2": return "已发货";
            break;
        case "3": return "已签收";
            break;
        case "4": return "订单取消";
            break;
    }
}

function printWithdrawStatus(status) {
    switch (status) {
        case "0": return "处理中";
            break;
        case "1": return "通过审核";
            break;
        case "2": return "未通过，钱已退";
            break;
    }
}


function date2String(fmt, dateString) {

    const date = new Date(dateString);
 
   let ret;
   const opt = {
     "Y+": date.getFullYear().toString(),        // 年
     "m+": (date.getMonth() + 1).toString(),     // 月
     "d+": date.getDate().toString(),            // 日
     "H+": date.getHours().toString(),           // 时
     "M+": date.getMinutes().toString(),         // 分
     "S+": date.getSeconds().toString()          // 秒
     // 有其他格式化字符需求可以继续添加，必须转化成字符串
   };
   for (let k in opt) {
     ret = new RegExp("(" + k + ")").exec(fmt);
     if (ret) {
       fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
     };
   };
   return fmt;
 }
