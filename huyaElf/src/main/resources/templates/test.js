window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}
test = async function(){
	for(var i = 0;i<5;i++){
		await sleep(2222);
		$('.guess-plan input').val(1);
		await sleep(2222);
		//点击确认种豆按钮
		$('.guess-plan button').click();
	}
}
test();