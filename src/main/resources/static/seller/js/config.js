//form
Do.add('form', {
    path: duxConfig.baseDir + 'form/Validform.min.js'
});

Do.add('layerCss', {
    path: duxConfig.baseDir + 'layer/theme/default/layer.css',
    type : 'css'
});
//dialog
Do.add('dialog',
{
    path : duxConfig.baseDir + 'layer/layer.js',
	requires : ['layerCss']
}
);
//notify
Do.add('notifyCss', {
    path: duxConfig.baseDir + 'Lobibox/css/Lobibox.min.css',
    type : 'css'
});
Do.add('notify', {
    path: duxConfig.baseDir + 'Lobibox/js/lobibox.min.js',
    requires : ['tipsCss']
});
//tip
Do.add('tipsCss', {
    path: duxConfig.baseDir + 'tips/toastr.css',
    type : 'css'
});
Do.add('tips', {
    path: duxConfig.baseDir + 'tips/toastr.min.js',
    requires : ['tipsCss']
});

//time
Do.add('timeCss', {
    path: duxConfig.baseDir + 'time/jquery.datetimepicker.css',
    type: 'css'
});
Do.add('time', {
    path: duxConfig.baseDir + 'time/jquery.datetimepicker.js',
    requires: ['timeCss']
});

//webuploader
Do.add('webuploaderCss', {
    path: duxConfig.baseDir + 'webuploader/webuploader.css',
    type: 'css'
});
Do.add('webuploader', {
    path: duxConfig.baseDir + 'webuploader/webuploader.withoutimage.min.js',
    requires: ['webuploaderCss']
});

//sortable
Do.add('sortable', {
    path: duxConfig.baseDir + 'sortable/jquery.sortable.js'
});


//调试函数
function debug(obj) {
    if (typeof console != 'undefined') {
        console.log(obj);
    }
}