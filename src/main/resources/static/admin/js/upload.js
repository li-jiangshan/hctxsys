// url 上传地址

function uploadimg(url,upid,veiwid){

        var uploader__upload_4 = WebUploader.create({
            withCredentials: true,                                                             // 跨域请求提供凭证
            auto: true,                                                                        // 选完文件后，是否自动上传
            duplicate: true,                                                                   // 同一文件是否可以重复上传
            swf: '/Public/libs/lyui/dist/swf/uploader.swf',                          // swf文件路径
            server: url, // 文件接收服务端
            pick: '#_upload_4',                                       // 选择文件的按钮
            resize: false,                                                                     // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            //fileNumLimit: 1,                                                                 // 验证文件总数量, 超出则不允许加入队列
            fileSingleSizeLimit:2*1024*1024, // 验证单个文件大小是否超出限制, 超出则不允许加入队列
            // 文件过滤
            accept: {
                title: 'Images',
                extensions: "gif,jpg,jpeg,bmp,png",
                mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
            }
        });

        // 文件上传过程中创建进度条实时显示。
        uploader__upload_4.on( 'uploadProgress', function(file, percentage ) {
            $('#_upload_preview_4').removeClass('hidden');
            var $li = $( '#_upload_preview_4'),
                $percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<div class="progress"><div class="progress-bar"></div></div>')
                        .appendTo( $li )
                        .find('.progress-bar');
            }
            $percent.css('width', percentage * 100 + '%');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader__upload_4.on('uploadComplete', function(file) {
            $( '#_upload_preview_4' ).find('.progress').remove();
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader__upload_4.on('uploadSuccess', function(file , response) {
            $('#_upload_preview_4').addClass('upload-state-done');
            if (eval('response').status == 0) {
                $.alertMessager(response.message);
            } else {
                $( '#_upload_input_4').attr('value', response.id);
                $( '#_upload_preview_4 img').attr('src', response.url);
            }
        });

        // 上传错误
        uploader__upload_4.on("error",function (type){
            if (type=="Q_TYPE_DENIED") {
                $.alertMessager('该文件格式不支持');
            } else if(type=="F_EXCEED_SIZE") {
                $.alertMessager("文件大小不允许超过2MB");
            } else if(type=="Q_EXCEED_NUM_LIMIT") {
                $.alertMessager("超过允许的文件数量");
            } else {
                $.alertMessager(type);
            }
        });

        // 文件上传失败，显示上传出错。
        uploader__upload_4.on('uploadError', function(file) {
            $.alertMessager('error');
            var $li = $('#_upload_preview_4'),
                $error = $li.find('div.error');
            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }
            $error.text('上传失败');
        });

        // 删除图片
        $(document).on('click', '#_upload_list_4 .remove-picture', function() {
            $('#_upload_input_4' ).val('') //删除后覆盖原input的值为空
            $('#_upload_preview_4').addClass('hidden');
        });
}




/*
 * 上传图片 后台专用
 * @access  public
 * @null int 一次上传图片张图
 * @elementid string 上传成功后返回路径插入指定ID元素内
 * @path  string 指定上传保存文件夹,默认存在public/upload/temp/目录
 * @callback string  回调函数(单张图片返回保存路径字符串，多张则为路径数组 )
 */
function GetUploadify(upurl)
{       
    // var upurl ='/index.php/admin/Uploadify/upload/num/'+num+'/input/'+elementid+'/path/'+path+'/func/'+callback;
   
    layer.open({
        type: 2,
        title: '上传图片',
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['50%', '60%'],
        content: upurl
     });
}


/*
 * 删除组图input
 * @access   public
 * @val  string  删除的图片input
 */
function ClearPicArr(val)
{
    $("li[rel='"+ val +"']").remove();
    $.get(
        "{:U('Admin/Uploadify/delupload')}",{action:"del", filename:val},function(){}
    );
}
/*
 * 删除组图input
 * @access   public
 * @val  string  删除的图片input
 */
function ClearPicArr2(val)
{
    $("li[rel='"+ val +"']").remove();
    $.get(
        "{:U('Home/Uploadify/delupload')}",{action:"del", filename:val},function(){}
    );
}