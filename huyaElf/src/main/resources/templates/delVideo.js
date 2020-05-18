
window.setType = function(){
	$($(".player-videotype-list li")[$(".player-videotype-list li").length-1]).click();//清晰度设为最低
	$("#player-sound-btn").click()//声音关闭
	$("#player-btn").click();//暂停播放
}

//删除播放窗口
window.del = function(){
	setInterval(function(){
		$("#player-video").empty();
	},5000);
}
setTimeout(del,12000);
setTimeout(setType,10000);


