function UplodeImgCallBack(fileurl_tmp)
{
    $("#imagetext").val(fileurl_tmp);
    $("#cover_img").attr('src', fileurl_tmp);
}