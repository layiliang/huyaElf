
var rate1 = 
var rate2 = 
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
		},1);
	}
},1);


