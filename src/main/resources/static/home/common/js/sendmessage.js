//点击发送短信
function sendMessage(url,type){
   var p=$(".sendmsg").attr('data');
   if(p==1){
    return false;
   }
   var mobile = '';
   if(type)
     mobile = $('.'+type).val();
 
   $.post(url,{'type':type,'mobile':mobile},function(data){
        if(data.status==1){
          layer.open({
            style: 'top:0'
            ,content: data.message
            ,skin: 'msg'
            ,time: 4 //2秒后自动关闭
          }); 
          RemainTime();
        }else{
          msg_alert(data.message);
        }
   });
}

var intime=$(".sendmsg").attr('settime');
var timenow =$(".sendmsg").attr('nowtime');  
var bet=(parseInt(intime)+60)-parseInt(timenow);
$(document).ready(function(){
    if(bet>0){
        RemainTime();
    }
});
var iTime = 59;
var Account;
if(bet>0){
    iTime=bet;
}


//倒计时 
function RemainTime(){
    $(".sendmsg").attr('data',1);
    var iSecond,sSecond="",sTime="";
    if (iTime >= 0){
        iSecond = parseInt(iTime%60);
        iMinute = parseInt(iTime/60)
        if (iSecond >= 0){
            if(iMinute>0){
                sSecond = iMinute + "分" + iSecond + "秒";
            }else{
                sSecond = iSecond + "秒";
            }
        }
        sTime=sSecond;
        if(iTime==0){
            clearTimeout(Account);
            sTime='获取验证码';
            iTime = 59;
            $(".sendmsg").attr('data',0);
        }else{
            Account = setTimeout("RemainTime()",1000);
            iTime=iTime-1;
        }
    }else{
        sTime='没有倒计时';
    }
    $('.sendmsg').text(sTime);
}
