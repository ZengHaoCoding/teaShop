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

function printMsgStatus2(status) {
    switch (status) {
        case "0": return "已处理";
            break;
        case "1": return "待处理";
            break;
    }
}