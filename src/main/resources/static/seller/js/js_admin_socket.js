var notifyServer;
getApiHost = function (data) {
    return 'http://127.0.0.1:2120';
}
log = function (s) {
    try {
        console.log(s);
    } catch (e) {

    }
}
function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
}
function startNotiry(data) {
    data.notiry_host = data.notiry_host || getApiHost(data);
    notifyServer = notify = io.connect(data.notiry_host);
    // notifyServer = notify = io.connect('http://127.0.0.1:52888');
    notify.emit('login', data);
    notify.on('login', function (o) { //登录
        log(o);
        if (!o)return;
        if (o && o.status >= 1) { //登录成功
            notifyServer.started = true;
            play('通知服务已开启');
        } else {
            //登陆失败
        }
    });
    notify.on('new_pay', function (o) { //登录
        log('新的支付');
        log(o);
        var payTypeName = '支付宝';
        if (o.type == 3) {
            payTypeName = '微信'
        } else if (o.type == 2) {
            payTypeName = 'QQ钱包'
        }
        var msg=payTypeName + '到账' + o.money + '元 请注意查收';
        console.log(payTypeName + '到账' + o.money + '元 请注意查收')
        $.bigBox({
            title : "您有新的到账",
            content : "支付方式："+payTypeName+'<br> 账号：'+o.pay_id+' 付款金额：'+o.money+'元<br>流水号：'+o.pay_no,
            color : "#739E73",
            //timeout: 8000,
            icon : "icon-volume-up",
            number : getLocalTime(o.pay_time)
        }, function() {
//                closedthis();
        });
        play(msg)
    })

    notify.on('loginOut', function (o) {//服务器断开 无法通知
        notifyServer.started = false
        notifyServer.disconnect();
    });
    notify.on('disconnect', function (o) {//服务器断开 无法通知
        log(o);
        if (notifyServer.started) {
            notify.emit('login', data);
        }else{
            play('通知服务已断开');
        }

    });

}
var audio=document.createElement('audio');  //生成一个audio元素
var play = function (s) {
    var URL = 'https://fanyi.baidu.com/gettts?lan=zh&text=' + encodeURIComponent(s) + '&spd=5&source=web'

    if(!audio){
        audio.controls = false  //这样控件才能显示出来
        audio.src = URL  //音乐的路径
        document.body.appendChild(audio)  //把它添加到页面中
    }
    audio.src = URL  //音乐的路径
    audio.play();
    //
    // try {
    //     document.getElementById('newMessageDIV').innerHTML = '<audio autoplay="autoplay"><source src="' + URL + '"/></audio>';
    //     // console.log(document.getElementById('newMessageDIV').innerHTML)
    // } catch (e) {
    //     try{
    //         document.getElementById('newMessage').innerHTML = '<audio autoplay="autoplay"><source src="' + URL + '"/></audio>';
    //     } catch (e) {
    //         document.getElementById('newMessageDIV').innerHTML = '<embed src="/sound/system.wav"/>';
    //     }
    //     log(e);
    // }
}
var pageNotify=function (data) {
    var notify_login=window.login_data;
    if (notify_login && notify_login.id && notify_login.password) { //登录
        startNotiry(notify_login)
    }
}
loadScript("/public/static/SmartAdmin/js/plugin/bootstrap-progressbar/bootstrap-progressbar.js",pageNotify);







