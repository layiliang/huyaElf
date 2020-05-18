window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
window.sendGift = async function(){
	$($(".player-face-gift")[GIFT_INDEX]).click();
	await sleep(2000);
	$("#player-gift-dialog .confirm").click();
}
sendGift();


