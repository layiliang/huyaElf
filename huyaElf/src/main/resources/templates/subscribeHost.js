window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}

subscribeHost= async function(){
	await sleep(2000);
	$("#yyliveRk_game_newsBut").click();
	console.log("aaa")
	await sleep(4000);
	console.log("bbb")
	$("#yyliveRk_game_newsBut").click();
	await sleep(2000);
	$($(".subscribe-layer-cancel-control span")[0]).click();
}

subscribeHost();