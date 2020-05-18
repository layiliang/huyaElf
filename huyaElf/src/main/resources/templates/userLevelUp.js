window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
userLevelUp = async function(){
	var missionArr = $(".task-status a");
	for(var i = 0 ; i < missionArr.length ; i++){
		$(missionArr[i]).click();
		await sleep(2000);
		$(".box_bot a").click();
		await sleep(1000);
	}
}

userLevelUp()