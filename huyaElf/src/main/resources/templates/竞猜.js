
//var rate1 = 
//var rate2 = 
	var leftBean1;
	var leftBean2;
	var leftBean = $($(".left-bean")[0]).text().substring(5);
	$($(".guess-btn")[0]).click();
	var interval1 = setInterval(function(){
		leftBean1 = $($(".left-bean")[0]).text().substring(5);
		console.log("leftBean1的初始值：  "+leftBean1);
		if(leftBean1!=leftBean){
			console.log("leftBean1的真实值：  "+leftBean1);
			clearInterval(interval1);
			$($(".guess-btn")[1]).click();
			var interval2 = setInterval(function(){
				leftBean2 = $($(".left-bean")[0]).text().substring(5);
				if(leftBean1!=leftBean2){
					clearInterval(interval2);
					if(leftBean1>leftBean2){
						
					}else{
						
					}
					console.log("leftBean1，2的真实值分别是：  "+leftBean1 + "," + leftBean2)
				}
			},2);
		}
	},2);
	
	

	
	
function sleep(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}

(async function() {
	for(var i = 0 ; i < 10 ; i ++){
		debugger
		console.log(i);
		var leftBean1;
		var leftBean2;
		var leftBean = $($(".left-bean")[0]).text().substring(5);
		await sleep(20);
		$($(".guess-btn")[0]).click();
		while(true){
			leftBean1 = $($(".left-bean")[0]).text().substring(5);
			console.log("leftBean1的初始值：  "+leftBean1);
			if(leftBean1 != leftBean){
				console.log("leftBean1的真实值：  "+leftBean1);
				break;
			}
			await sleep(2);
		}
		await sleep(20);
		$($(".guess-btn")[1]).click();
		while(true){
			leftBean2 = $($(".left-bean")[0]).text().substring(5);
			console.log("leftBean2的初始值：  "+leftBean2);
			if(leftBean2 != leftBean1){
				console.log("leftBean2的真实值：  "+leftBean2);
				break;
			}
			await sleep(2);
		}
		console.log("leftBean1，2的真实值分别是：  "+leftBean1 + "," + leftBean2)
		await sleep(2);
	}
})()
