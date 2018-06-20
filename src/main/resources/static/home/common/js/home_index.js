//公共提示框
function msg_alert(message,status,url){
  if(url){
    if(status==1){
        //提示
        layer.open({
           style: 'top:0'
          ,content: message
          ,skin: 'msg'
          ,time: 1.5 //2秒后自动关闭
          ,end:function(index){
            window.location.href=url;
          }
        });
    }else{
        //提示
        layer.open({
           style: 'top:0'
          ,content: message
          ,skin: 'msg'
          ,time: 1.5 //2秒后自动关闭
          ,end:function(index){
            location.reload();
          }
        });
    }
  }else{
      // layer.open({
      //   content: message
      //   ,btn: '我知道了'
      //   ,time: 2 //2秒后自动关闭
      // });
      //提示
      layer.open({
        style: 'top:0'
        ,content: message
        ,skin: 'msg'
        ,time: 2 //2秒后自动关闭
      });
  }
  
}


//用户登录
function login(){

	  var thisform=document.forms['formlogin'];
	  var account=thisform.account.value;
	  var password=thisform.password.value;
	  // var verify=thisform.verify.value;

	  if(account=='' || account==null){
	  	msg_alert('账号不能为空');
      return false;
	  }
    if(password=='' || password==null){
      msg_alert('密码不能为空');
      return false;
    }
    // if(verify=='' || verify==null){
    //   msg_alert('验证码不能为空');
    //   return false;
    // }


    var post_url = $("form[name='formlogin']").attr('action');
    var post_data= $("form[name='formlogin']").serialize();
    $.ajax({
         type: "POST",
         url: post_url,
         data:post_data,
         dataType: "json",
         success: function(data){
            if(data.status==1){
                window.location.href=data.url;
            }else{
                // $('#code').trigger('click');
                msg_alert(data.info);
            }      
          }     
    });
}

//
//$(function(){
//
//  //公共post提交 陶  2017-11-21
//  $('.ajax-post').click(function(){
//      var datafrom=$(this).attr('target-from');
//      var post_url = $("."+datafrom).attr('action');
//      var post_data= $("."+datafrom).serialize();
//      if(post_url){
//          $.ajax({
//             type: "POST",
//             url: post_url,
//             data:post_data,
//             dataType: "json",
//             success: function(data){
//                if(data.status==1){
//                    if((data.info=='' || data.info==null) && data.url){
//                       window.location.href=data.url;
//                       return;
//                    }
//                    if(data.url)
//                      msg_alert(data.info,1,data.url);
//                    else
//                      msg_alert(data.info,0,'11'); 
//                }else{
//                    msg_alert(data.info);
//                }      
//              }    
//        });
//      }
//  });
//
//
//})


//上传图片
function uploadFile(eid,url) { 
  //正在加载
  var index=layer.open({
    type: 2
    ,content: '上传中'
  });
 
  $.ajaxFileUpload({
  
      url:url,
      secureuri:false ,
      fileElementId:eid,
      dataType: 'text',
      success: function (data,status)  
      {
        layer.close(index);//关闭加载
        var data = $.parseJSON(data);
         if(data.status){
           $('#'+eid).prev('div').find('img').attr('src',data.path);
           $("input[name='"+$('#'+eid).attr('data')+"']").val(data.path);
         }else{
           msg_alert(data.error);
         }
        
      },
      error: function (data, status, e)
      {
        alert(e);
      }
    });
  return false;

}


//删除数据
function deletedata(obj){
  if(!confirm('确实要删除？')){
    return false;
  }
  var id=$(obj).attr('data');
  var url=$(obj).attr('url');
  if(id && url){
          $.ajax({
             type: "GET",
             url: url,
             data:{id:id},
             dataType: "json",
             success: function(data){
                if(data.status==1){
                    if(data.url)
                      msg_alert(data.info,1,data.url);
                    else
                      msg_alert(data.info,0,'11'); 
                }else{
                    msg_alert(data.info);
                }      
              }    
        });
      }
}