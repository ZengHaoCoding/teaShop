var imgUrl = "./img/goods/";
function goods_card(goods, login_status) {
    var goodsCard = "";
    goodsCard += "<div class=\"card\" style=\"float:left; width: 30%; margin-left: 1%; margin-top: 5px;padding: 1%;\">";
    goodsCard += "                        <a href=\"goods.html?item=" + goods.goods_id + "\">";
    goodsCard += "                            <div style=\"width: 100%;height: 17vw;\"><img src=\"" + imgUrl + goods.goods_img + "\" class=\"card-img-top\"";
    goodsCard += "                                    alt=\"goodsCard\" \/><\/div>";
    goodsCard += "                            <div class=\"card-body\">";
    goodsCard += "                                <h5 class=\"card-title\">￥" + goods.goods_money + "<\/h5>";
    goodsCard += "                                <p class=\"card-text\"";
    goodsCard += "                                    style=\"display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;overflow: hidden;\">";
    goodsCard += goods.goods_name;
    goodsCard += "                                <\/p>";
    goodsCard += "                            <\/div>";
    goodsCard += "                        <\/a>";
    if (login_status) {
        goodsCard += "                        <div class=\"card-body\">";
        goodsCard += "                            <button name=\"btn1\" value=\"" + goods.goods_id + "\" type=\"submit\" class=\"btn btn-outline-primary add_cart\">加入购物车<\/button>";
        goodsCard += "                            <button name=\"btn2\" type=\"button\"";
        goodsCard += "                                    class=\"btn btn-outline-info\">立即购买<\/button>";
        goodsCard += "                        <\/div>";
    }
    goodsCard += "                    <\/div>";
    return goodsCard;
}

