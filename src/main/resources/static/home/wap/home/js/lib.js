$(document).ready(function($) {
    
    // 栏目列表

    function reCol(){

        var num = $('.colname-list a').length;

        $('.colname-list a').css('width', 100 / num + '%');

        var w = $(window).width();

        if(w <= 768){

            $('.colname-list a').css('width', '48%');

        }

    }

    reCol();

    $(window).resize(function(event) {

        /* Act on the event */

        reCol();

    });

    // 返回顶部

    $(window).scroll(function(event) {

        /* Act on the event */

        var h = $(window).scrollTop();

        if(h > 100){

            $('.goTop').addClass('show');

        }else{

            $('.goTop').removeClass('show');

        }

    });

    $('.goTop').click(function(event) {

        /* Act on the event */

        $('body,html').animate({'scrollTop': '0'}, 500);

    });

   

})