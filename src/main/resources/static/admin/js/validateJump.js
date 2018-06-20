
var errorsBegin = "<div class='alert alert-error alert-block alerts'><a class='close' data-dismiss='alert' href='#'>×</a><p>";
var errorsEnd = "</p></div>";


	$('#errors').css("display","none");
	$('#msg').remove();
	$('.alerts').remove();


/**
 * 判断是否为空
 * @param name  控件名称
 * @param value 控件value
 * @returns {Number}
 */
function required(name,value){
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
		if(!value){
			$("#errors").append(errorsBegin + "<span id='msg'>"+name+"不能为空"+"</span>" + errorsEnd);
			$("#errors").css("display","");
			return 1;
	}
		
}

/**
 * 判断是否为数字
 * @param name  控件名称
 * @param value 控件value
 * @returns {Number}
 */
function mask(name,value){
	var reg = /^\d+$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"只能为数字"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}

/**
 * 判断是否为min长度
 * @param name  控件名称
 * @param value 控件value
 * @param minlength 控件最少长度
 * @returns {Number}
 */
function minlength(name,value,minlength){
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(value.length<minlength){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"必须输入"+minlength+"位数字"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}

/**
 * 判断是否为max长度
 * @param name  控件名称
 * @param value 控件value
 * @param maxlength 控件最多长度
 * @returns {Number}
 */
function maxlength(name,value,maxlength){
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(value.length>=maxlength){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"至多只能输入"+maxlength+"位数字"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}

/**
 * 判断是checkbox 是否被选中
 * @param name  控件名称
 * @param value 控件value
 * @param maxlength 控件最多长度
 * @returns {Number}
 */
function checkbox(action){
	var a = true;
	$("input[type=checkbox]").each(function(event){
		if(this.checked){
			a = false;
		}
	});
	if(a){
		$("#errors").css("display","none");
		$('#msg').remove();
		$('.alerts').remove();
		alert("请选择数据...");
		$("#errors").append(errorsBegin + "<span id='msg'>请选择数据...</span>" + errorsEnd);
		$("#errors").css("display","");
		
		return false;
	}else{
		$('Form')[0].action = action + ".do";
		$('Form')[0].submit();
	}
}
/**
 * 判断是否为非负浮点数
 * 
 * @param name
 *            控件名称
 * @param value
 *            控件value
 */
function numCheck(name, value) {
	var reg=/^\d+(\.\d+)?$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"格式错误"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}

/**
 * 判断只能输入数字和字母
 * @param name  控件名称
 * @param value 控件value
 */
function usernamecheck(name,value){
	var reg=/^\w+$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"格式错误"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}

/**
 * 判断是否为正确网址
 * @param name  控件名称
 * @param value 控件value
 */
function websitecheck(name,value){
	var reg=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"格式错误"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}


/**
 * 判断是否为emial格式
 * @param name  控件名称
 * @param value 控件value
 */
function email(name,value){
	var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"不是邮箱格式"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}

/**
 * 判断是否为手机格式
 * @param name  控件名称
 * @param value 控件value
 */
function phone(name,value){
	var reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"不是手机格式"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}
/**
 * 判断是否为qq格式
 * @param name  控件名称
 * @param value 控件value
 */
function qq(name,value){
	var reg = /^\d{5,10}$/;
	$("#errors").css("display","none");
	$('#msg').remove();
	$('.alerts').remove();
	if(!reg.test(value)){
		$("#errors").append(errorsBegin + "<span id='msg'>"+name+"不是qq格式"+"</span>" + errorsEnd);
		$("#errors").css("display","");
		return 1;
	}
}
/**
 * 判断是否相等
 * @param name  第一个值
 * @param value 第二个值
 */
function checkConfirm(val1,val2) {
	if(val1!=val2) return 1;
}

/**
 * 判断是否为正浮点数
 * @param num 输入值
 * @returns {number} 1代表错误
 */
function checkNum(num) {
	var reg=/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/
	if(!reg.test(num)) return 1;
}


function confirmDel(str){ 
	return confirm(str);
}