function sendAjax(){
	var site = $("#url").val();
	$.ajax({
        //请求方式
        type : "POST",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url : "http://192.168.50.77:8080/forward",
        //数据，json字符串
        data : site,
        //请求成功
        success : function(result) {
        	debugger
			alert("打开虎牙成功");
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });
}