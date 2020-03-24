dig = function(){
	setInterval(
			function(){
				//console.log('digdig: ' + new Date())
			$(".btn-wrap span").click();
			}
			,500)
}
//删除播放窗口
function del(){
	$("#player-video").empty();
}
close = function(){
	closeInterval = setInterval(function(){
		if(!$(".liveRoom_treasureChest").length){
			$("body").append("<p class ='closeFlg'>closeWindow<p/>");
			clearInterval(closeInterval);
		}
	},1000)
}
dig();
setTimeout(close,30000);
setTimeout(del,10000);
$("body").append("<p class ='digBeanFlg'>digBeaning<p/>");
window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
logTreasure = async function(){
	while(true){
		await sleep(1111);
		if($(".huyayihao").length>0){
			console.log($(".huyayihao").html());
		}
		if($(".player-banner-gift").length>0){
			//debugger
			console.log($(".player-banner-gift").html());
			//console.log($("body").html());
			if($(".player-banner-gift").html().indexOf("藏宝图") || $(".player-banner-gift").html().indexOf("战神号")){
				//$(".player-banner-gift").click();
			}
		}
	}
}
logTreasure();


