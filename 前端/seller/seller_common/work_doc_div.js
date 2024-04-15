function other_div(msg) {
    var other1 = "";
    other1 += "<div class=\"media\">";
    other1 += "                            <div class=\"media-body\">";
    other1 += "                                <h5 class=\"mt-0\">买家（UID：" + msg.create_uid + "）&nbsp;&nbsp;&nbsp;<span style=\"color: A8A8A8; font-size:10px;\">" + date2String("YYYY-mm-dd HH:MM", msg.create_time) + "<\/span><\/h5>";
    other1 += "                                <p style=\"width: 70%;\">" + msg.wd_contant + "<\/p>";
    if (msg.wd_img != null) {
        other1 += "                                <img style=\"width: 10rem;height: 10rem;\" src=\"..\/img\/workDoc\/" + msg.wd_img + "\" \/>";
    }
    other1 += "                            <\/div>";
    other1 += "                        <\/div>";
    other1 += "                        <hr \/>";
    return other1;
}

function mine_div(msg) {
    var mine1 = "";
    mine1 += "<div class=\"media\">";
    mine1 += "                            <div class=\"media-body\">";
    mine1 += "                                <h5 class=\"mt-0\" style=\"margin-left: 55%;width: 80%;\"><span style=\"color: A8A8A8; font-size:10px;\">" + date2String("YYYY-mm-dd HH:MM", msg.create_time) + "<\/span>&nbsp;&nbsp;&nbsp;我（UID：" + msg.create_uid + "）<\/h5>";
    mine1 += "                                <p style=\"width: 70%;margin-left: 30%;\">" + msg.wd_contant + "<\/p>";
    if (msg.wd_img != null) {
        mine1 += "                                <img style=\"margin-left: 30%;width: 10rem;height: 10rem;\" src=\"..\/img\/workDoc\/" + msg.wd_img + "\" \/>";
    }
    mine1 += "                            <\/div>";
    mine1 += "                        <\/div>";
    mine1 += "                        <hr \/>";
    return mine1;
}