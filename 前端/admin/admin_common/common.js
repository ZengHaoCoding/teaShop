function printGoodsStatus(status) {
    switch (status) {
        case "0": return "<font color=\"red\">下架<\/font>";
            break;
        case "1": return "<font color=\"green\">上架<\/font>";
            break;
    }
}

function printGoodsStatus2(status) {
    switch (status) {
        case "0": return "上架";
            break;
        case "1": return "下架";
            break;
    }
}

function printBalance(user) {
    if (user.role != "3") {
        return user.balance;
    } else {
        return "-"
    }
}

function get_address_info_by_uid(uid) {
    var r = false;
    var settings = {
        "url": "http://127.0.0.1:8085/admin/selectAddressByUid",
        "method": "POST",
        "timeout": 0,
        "async": false,
        "crossDomain": true,
        "data": JSON.stringify({
            "uid": uid
        }),
        "headers": {
            "token": window.localStorage.getItem("token"),
            "Content-Type": "application/json"
        },
    };

    $.ajax(settings).done(function (response) {
        r = response;
    });
    return r;
}

function printUserRole2(user) {
    switch (user.role) {
        case "1": return "管理员（" + user.seller_name + "）";
            break;
        case "2": return "卖家（" + user.seller_name + "）";
            break;
        case "3": return "买家";
            break;
    }

}

function printSellerStatus(seller) {
    switch (seller.status) {
        case "0": return "待审核";
            break;
        case "1": return "已通过";
            break;
        case "2": return "未通过";
            break;
    }

}
