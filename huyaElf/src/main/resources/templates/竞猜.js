
setInterval(function(){
	$($(".guess-btn")[0]).click();
	setTimeout(function(){
		var leftBean1 = $($(".left-bean")[0]).text().substring(5);
		$($(".guess-btn")[1]).click();
		setTimeout(function(){
			var leftBean2 = $($(".left-bean")[0]).text().substring(5);
			console.log(leftBean1 + "                     aaa                " + leftBean2);
		},200);
	},200);
},2000);


var leftBean = $($(".left-bean")[0]).text().substring(5);
$($(".guess-btn")[0]).click();
while(true){
	var leftBean1 = $($(".left-bean")[0]).text().substring(5);
	if(leftBean1!=leftBean){
		leftBean = leftBean1;
		while(true){
			var leftBean2 = $($(".left-bean")[1]).text().substring(5);
			if(leftBean2!=leftBean){
				console.log(leftBean1 + "     aaa     " + leftBean2)
				break;
			}
		}
		break;
	}
}




var leftBean = $($(".left-bean")[0]).text().substring(5);
$($(".guess-btn")[1]).click();
var interval1 = setInterval(function(){
	var leftBean1 = $($(".left-bean")[0]).text().substring(5);
	console.log("aa   "+leftBean1);
	if(leftBean1!=leftBean){
		console.log(leftBean1);
		clearInterval(interval1);
	}
},1);


