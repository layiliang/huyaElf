log = function(){
	setInterval(function(){
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
		}},1000);
}
log();

