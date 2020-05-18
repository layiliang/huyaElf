window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
window.chestResult = new Array();
window.openChests = async function(){
	await sleep(2000);
	$("#player-box").css("display","block");
	await sleep(2000);
	var chestArr = $(".player-box-stat3");
	for(let i = 0 ; i < chestArr.length;i++){
		await sleep(3000);
		$(chestArr[i]).click();
	}

	await sleep(1000);
	var chests = $(".player-box-list ul li");
	for(var i = 0 ; i < chests.length;i++){
		var type = $(chests[i]).find("i").attr("class");
		var num ;
		//手气不佳 player-box-icon-0
		if(type =="player-box-icon-8"){
			type = "银豆"
			num = $(chests[i]).find(".player-box-stat4").text().substr(1);
		}
		if(type =="player-box-icon-4"){
			type = "虎粮"
			num = $(chests[i]).find(".player-box-stat4").text().substr(1);
		}
		if(type =="player-box-icon-0"){
			type = "运气不佳"
			num = 1;
		}
		chestResult[i] = {"type":type,"num":num};
	}
}
openChests();