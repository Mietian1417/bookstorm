$(function () {

//	订单页面  修改地址
    $(".edit").click(function () {
        $(".mask").show();
        $(".adddz").show();
    });
    $(".addUser123").click(function () {
        $(".mask").show();
        $(".adddz").show();
    });

    $(".bc>input").click(function () {
        if ($(this).val() == "保存") {
            $(".mask").hide();
            $(".adddz").hide();
            $(".bj").hide();
            $(".xg").hide();
            $(".remima").hide();
            $(".pj").hide();
            $(".chak").hide();
        } else {
            $(".mask").hide();
            $(".adddz").hide();
            $(".bj").hide();
            $(".xg").hide();
            $(".remima").hide();
            $(".pj").hide();
            $(".chak").hide();
        }
    });

    // 订单导航栏展示
    $("a[orderStatus]").click(function () {
        // 移除所有导航链接的选中状态
        $("a[orderStatus]").removeClass("selected");
        let orderStatus = $(this).attr("orderStatus");
        if ('all' === orderStatus) {
            $('table[orderStatus]').show();
        } else {
            $('table[orderStatus]').hide();
            $('table[orderStatus=' + orderStatus + ']').show();
        }
        // 添加选中状态到当前链接
        $(this).addClass("selected");
    });

//	评价 tab切换
    $(".sx div:gt(0)").hide();
    $(".sx div").each(function (i) {
        if ($(this).html() == '') {
            var str = $("#pro li").eq(i).find("a").text();
            var txt = '';
            txt = '<div class="noz">当前没有' + str + '。</div>';
            $(this).html(txt);
        }
    });
    $("#pro li").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
        var index = $(this).index();
        $(".sx > div").eq(index).show().siblings().hide();
    });


// 评价打心
    $(".sx dl dd").find("a").click(function () {
        if ($(this).text() == "评价") {
            $(".mask").show();
            $(".pj").show();
        } else if ($(this).text() == "查看评价") {
            $(".mask").show();
            $(".chak").show();
        } else {
            $(".mask").hide();
            $(".pj").hide();
            $(".chak").hide();
        }

    });
//	评价打心
    $("#xin").each(function (i) {
        $("#xin").eq(i).children("a").click(function () {
            var index = $(this).index();
            for (var j = 0; j < 5; j++) {
                if (j <= index) {
                    $("#xin").eq(i).find("a").eq(j).find("img").attr("src", "img/hxin.png");
                } else {
                    $("#xin").eq(i).find("a").eq(j).find("img").attr("src", "img/xin.png");
                }

            }
        })
    })

//	个人信息 编辑
    $("#edit1").click(function () {
        $(".mask").show();
        $(".bj").show();
    });
    $("#edit2").click(function () {
        $(".mask").show();
        $(".xg").show();
    });

//修改头像
    $("#avatar").click(function () {
        $(".mask").show();
        $(".avatar").show();
    });

//	弹框关闭按钮
    $(".gb").click(function () {
        $(".mask").hide();
        $(".bj").hide();
        $(".xg").hide();
        $(".remima").hide();
        $(".avatar").hide();
        $(".pj").hide();
        $(".chak").hide();
    });

//	address
    $(".xiugai").click(function () {
        $(".mask").show();
        $(".readd").show();
    });
    // 打开新增地址弹窗
    $("#addxad").click(function () {
        $(".mask").show();
        $(".adddz").show();
    });
    $(".readddd").click(function () {
        $(".mask").show();
        $(".readddd").show();
    });

    $(".dizhi").hover(function () {
        var txt = "";
        txt = '<p class="addp"><a href="#"  class="readdn">修改</a><a href="/delete" class="deladd">删除</a></p>'
        $(this).append(txt);
        $(".readdn").click(function () {
            $(".mask").show();
            $(".readd").show();
        });
        $(".deladd").click(function () {
            $(this).parents(".dizhi").remove();
        });
    }, function () {
        $(".bc>input").click(function () {
            if ($(this).val() == "保存") {
                $(".mask").hide();
                $(".readd").hide();
            } else {
                $(".mask").hide();
                $(".readd").hide();
            }
        });
        $(".addp").remove();
    });

//	查看物流
    $(".vewwl").hover(function () {
        $(this).children(".wuliu").fadeIn(100);
    }, function () {
        $(this).children(".wuliu").fadeOut(100);
    });

})
