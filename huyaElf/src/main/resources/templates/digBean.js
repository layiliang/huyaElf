(function(){
	try{
		if(digBeanFlg){
			return;
		}
	}catch(err){
	}
	console.log("digging");
	//$("body").append("<p class ='digBeanFlg'>digBeaning<p/>");
	window.digBeanFlg=1;
	window.closeFlg=0;
	window.harvest = new Array();
	window.leftTreasureSize=1;
	window.digResultRecordFlg = false;//是否已经记录领取结果
	window.treasureNum;
	window.dig = function(){
		var i = 0;
		digInterval = setInterval(
				function(){
					leftTreasureSize = $("#J_treasureChestContainer .num").text();
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
		setInterval(function(){
			$("#player-video").empty();
			
		},5000);
	}
	window.close = function(){
		closeInterval = setInterval(function(){
			if($(".liveRoom_treasureChest").length){
				//有宝藏，
				closeFlg=0;
				treasureNum = $(".liveRoom_treasureChest .num").text();
			}else{
				//没有宝藏
				closeFlg=1;
			}
		},1000)
	}
	dig();
	setTimeout(close,30000);
	setTimeout(del,10000);
	window.chat = function(){
		for(var i = 0 ; i < 1;i++){
			setTimeout(function(){
				$("#pub_msg_input").val("哈哈哈");
				$("#msg_send_bt").addClass("enable");
				$("#msg_send_bt").click();},10000);
		}
	}
	chat();
})()



