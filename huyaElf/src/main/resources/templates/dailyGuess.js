window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
window.clicktime = 0;
window.guessNum;
dailyGuess = async function(){
	await sleep(2000);
	$("#player-fullscreen-btn").click();
	await sleep(2000);
	$("#player-subscribe-wap").click();
	await sleep(2000);
	$(".guess-icon").click();
	await sleep(2000);

	var guessBtnArr = $(".guess-btn");
	for(var i = 0 ; i < guessBtnArr.length;i++){
		if((i%2==0 &&  $(guessBtnArr[i]).text().indexOf("1得")>=0 && $(guessBtnArr[i+1]).text().indexOf("1得")>=0) ||
				(i%2==1 &&  $(guessBtnArr[i]).text().indexOf("1得")>=0 && $(guessBtnArr[i-1]).text().indexOf("1得")>=0)){
			$(guessBtnArr[i]).click();
			await sleep(2000);
			$(".guess-plan input").val(1);
			await sleep(2000);
			clicktimes = 0;
			while(true){
				if(clicktimes <=3){
					$(".guess-plan button").click();
					clicktimes++;
					await sleep(2000);
					if($('.rspTips').css('display')=='block'){//种豆成功
						break
					}else{
						$('.player-panel-close').click();
						await sleep(2000);
					}
				}else{
					break;
					clicktime--;
				}
				
			}
			clicktime++;
			console.log(clicktime)
		}
		await sleep(2000);
	}
	if(clicktime%2==0){
		guessNum = clicktime/2;
	}else{
		guessNum = (clicktime-1)/2;
	}
	console.log(guessNum)
}
dailyGuess();
