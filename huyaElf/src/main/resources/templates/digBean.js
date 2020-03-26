console.log("digging");
$("body").append("<p class ='digBeanFlg'>digBeaning<p/>");
window.closeFlg=0;
window.harvest = new Array();
var i = 0;
window.digResultRecordFlg = false;//是否已经记录领取结果
window.dig = function(){
	digInterval = setInterval(
			function(){
				$(".btn-wrap span").click();
				digResult = $(".treasureChest-tips").text();//领取结果
				if(digResult){//出现领取结果
					if(!digResultRecordFlg){//还没有记录
						harvest[i] = { 'host': $(".host-name").text(),'digResult': digResult,'time': new Date()};
						i++;
					}
					digResultRecordFlg=true;//记录完毕
				}else{//领取结果消失
					digResultRecordFlg = false;//没有记录
				}
			}
			,500)
}
//删除播放窗口
window.del = function(){
	setIntetval(function(){
		$("#player-video").empty();
	},5000);
}
window.close = function(){
	closeInterval = setInterval(function(){
		if($(".liveRoom_treasureChest").length){
			//有宝藏，
			closeFlg=0;
		}else{
			//没有宝藏
			closeFlg=1;
		}
	},1000)
}
dig();
setTimeout(close,30000);
setTimeout(del,10000);
window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
window.chat = async function(){
	while(true){
		$("#pub_msg_input").val("挖宝大军驾临");
		await sleep(500);
		$("#msg_send_bt").addClass("enable");
		await sleep(500);
		$("#msg_send_bt").click();
		await sleep(5000);
	}
}
chat();


