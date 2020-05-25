window.sleep = function(ms) {
  	return new Promise(resolve => setTimeout(resolve, ms));
}

window.clickGuessBtn = function(index,message){
	$('.left-bean').text('beforeClick');
	
	//console.log("种豆数：" + $('.guess-plan input').val())

	$($(".guess-btn")[index]).click();
	$($(".guess-btn")[index]).click();
	$($(".guess-btn")[index]).click();	
	console.log(message + "btnclick" + index)
	//while($('.choice-color').text()!=name1[i]){
}
window.closeFlg=false;
window.betingFlg=false;
window.guessDatas=new Array();
window.index=new Array();
window.interval=new Array();
window.guessDataInterval=new Array();
window.resultFlg1=new Array();
window.resultFlg2=new Array();
window.endFlg=new Array();
window.rate1=new Array();
window.rate2=new Array();
window.num1=new Array();
window.num2=new Array();
window.name1=new Array();
window.name2=new Array();
window.result=new Array();
window.title=new Array();
window.startTime=new Array();
window.endTime=new Array();
window.durationTime=new Array();
window.recordTime=new Array();
window.startedFlg=new Array();
window.beanPlan1=new Array();
window.beanPlan2=new Array();
window.leftBean=new Array();
window.leftBean1=new Array();
window.leftBean2=new Array();
window.buildGuessDatas= async function(i){
	guessDatas[i] = new Array();
	guessData = new Array();
	index[i]=0;
	interval[i]=0;
	startedFlg[i]=false;
	title[i] = $($('.box-title .name')[i]).text();
	name1[i] = $($('.process-name')[i*2]).text();
	name2[i] = $($('.process-name')[i*2+1]).text();
	while(true){
		await sleep(50);
		interval[i]++;
		if(!$($('.guess-btn')[i*2]).text()){
			return;
		}
		resultFlg1[i] = $($('.guess-unit span')[i*2]).attr('class')?true:false;
		resultFlg2[i] = $($('.guess-btn')[i*2]).text()=='流局'?true:false;
		endFlg[i] = $($('.guess-btn')[i*2]).text()=='结束种豆'?true:false;
		//console.log($($('.guess-btn')[i*2]).text());
		if((resultFlg1[i] || resultFlg2[i]) && startedFlg[i]){
			if(resultFlg1[i]){
				result[i] = $($('.guess-unit span')[i*2]).attr('class');
			}else{
				result[i] = '流局';
			}
			console.log(result[i]);
			endTime[i] = new Date().getTime();
			$("#msg_send_bt").append("<p class ='guessResult' id ='guessResult"+ i +"'>"+startTime[i] + "," +endTime[i] + "," + result[i] + "," + name1[i] + "," + name2[i] + "," + title[i] + "</p>");
			break;
		}
		rate1[i] = $($('.guess-btn')[i*2]).text();
		rate2[i] = $($('.guess-btn')[i*2+1]).text();
		rate1[i] = rate1[i].substring(rate1[i].length-3, rate1[i].length);
		rate2[i] = rate2[i].substring(rate2[i].length-3, rate2[i].length);
		if(isNaN(rate1[i])){
			rate1[i] = 0;
		}
		if(isNaN(rate2[i])){
			rate2[i] = 0;
		}
		//console.log(rate1[i] + ',' + rate2[i]);
		//if(rate1[i]*rate2[i]>1.108 && betingFlg==false){
		/*if(rate1[i] =="上开种" || rate2[i] =="上开种"){
			return;
		}
		if(rate1[i]*rate2[i]<1.108 && rate1[i] !="上开种" && rate2[i] !="上开种" && betingFlg==false){
			var start = new Date();
			console.log(start);
			betingFlg=true;
			await sleep(2);
			clickGuessBtn(i*2,'第1次');
			while($('.left-bean').text()=='beforeClick' || $('.choice-color').text()!=name1[i]){
				console.log("$('.left-bean').text()" + $('.left-bean').text() + " & " + "$('.choice-color').text()" + $('.choice-color').text());
				await sleep(2);
			}
			console.log('第1次:' + "$('.left-bean').text()" + $('.left-bean').text() + " & " + "$('.choice-color').text()" + $('.choice-color').text())
			//debugger;
			leftBean1[i] = $($(".left-bean")[0]).text().substring(5);
			console.log("leftBean1"+leftBean1[i]);
			//await sleep(5222);
			clickGuessBtn(i*2+1,'第2次');
			while($('.left-bean').text()=='beforeClick' || $('.choice-color').text()!=name2[i]){
				console.log("$('.left-bean').text()" + $('.left-bean').text() + " & " + "$('.choice-color').text()" + $('.choice-color').text());
				await sleep(2);
			}
			console.log('第2次:' + "$('.left-bean').text()" + $('.left-bean').text() + " & " + "$('.choice-color').text()" + $('.choice-color').text())
			leftBean2[i] = $($(".left-bean")[0]).text().substring(5);
			console.log("leftBean2"+leftBean2[i]);
			//await sleep(5222);
			
			var myBean = $('.my-bean').text();
			myBean = myBean.substring(5,myBean.length-2);
			console.log("myBean"+myBean);
			console.log("rate1[i]"+rate1[i]);
			console.log("rate2[i]"+rate2[i]);
			
			beanPlan1[i] = Math.floor(myBean/(1+(0.95*rate1[i] + 1)/(0.95*rate2[i] + 1)));//竞猜1最多能压的豆子数
			console.log(beanPlan1[i]);
			beanPlan2[i] = Math.floor(myBean-beanPlan1[i]);//竞猜2最多能压的豆子数
			console.log(beanPlan2[i]);
			if(Math.floor(leftBean1[i]*(0.95*rate1[i] + 1)/(0.95*rate2[i] + 1))<leftBean2[i]){//竞猜1剩豆不足，竞猜2剩豆充足，以竞猜1剩豆为准（leftBean1）

				console.log("a");
				if(leftBean1[i]<beanPlan1[i]){//竞猜1剩豆不足，以竞猜1剩豆为准
					console.log("aa");
					beanPlan1[i] = leftBean1[i];
					beanPlan2[i] = Math.floor(beanPlan1[i]*(0.95*rate1 + 1)/(0.95*rate2 + 1));
				}
			}else{//以竞猜2剩豆为准（leftBean2）
				console.log("b");
				if(leftBean2[i]<beanPlan2[i]){//竞猜2剩豆不足，以竞猜2剩豆为准
					console.log("bb");
					beanPlan2[i] = leftBean2[i];
					beanPlan1[i] = Math.floor(beanPlan2[i]*(0.95*rate2[i] + 1)/(0.95*rate1[i] + 1))
				}
			}
			console.log("beanPlan"+beanPlan1[i] + ',' + beanPlan2[i])
			beanPlan1[i] = 1;
			beanPlan2[i] = 1;
			await sleep(2);
			//种beanPlan2，确认是否成功种豆，如果成功，种beanPlan1，如果不成功，重新判断r1*r2
			$('.guess-plan input').val(beanPlan2[i]);
			while($('.win-bean') != '幸运可得0'){
				await sleep(2);
			}
			//点击确认种豆按钮
			$('.guess-plan button').click();
			//console.log("btn2click,bet2")
			for(var j = 0 ; j < 200 ; j ++){
				await sleep(5);
				if($('.rspTips').css('display')=='block'){//种豆成功
					clickGuessBtn(i*2,'第3次');
					while($('.left-bean').text()=='beforeClick' || $('.choice-color').text()!=name1[i]){
						console.log("$('.left-bean').text()" + $('.left-bean').text() + " & " + "$('.choice-color').text()" + $('.choice-color').text());
						await sleep(20);
					}
					console.log('第3次:' + "$('.left-bean').text()" + $('.left-bean').text() + " & " + "$('.choice-color').text()" + $('.choice-color').text())
					await sleep(2);
					//console.log("leftBean1的真实值：  "+leftBean1[i]);
					$('.guess-plan input').val(beanPlan1[i]);
					while($('.win-bean') != '幸运可得0'){
						await sleep(2);
					}
					//点击确认种豆按钮
					$('.guess-plan button').click();
					betingFlg=false;
					//判断是否种豆成功
					await sleep(2);

					console.log("time" + (new Date()-start));
					break;
				}
				if($('#player-panel-alert').css('display')=='block'){//种豆不成功
					
				}
			}
			await sleep(2);
			break;
		}*/
		if(!endFlg[i] && !resultFlg1[i] && !resultFlg2[i] && interval[i]%20==0){
			if(index[i]==0){
				startTime[i] = new Date().getTime();
				$("#msg_send_bt").append("<p class ='startTime' id ='startTime"+ i+"'>"+startTime[i] + "</p>");
			}
			startedFlg[i]=true;
			num1[i] = $($('.process-num')[i*2]).text();
			num2[i] = $($('.process-num')[i*2+1]).text();
			recordTime[i] = new Date().getTime();
			guessData[i] = { 'rate1': rate1[i],'rate2': rate2[i],'num1': num1[i],'num2': num2[i],
					'recordTime': recordTime[i]};
			guessDatas[i].push(guessData[i]);
			index[i]++;
		}
		if(interval[i]%1200==0){
			
		}
	}	
}
window.guessMainBoxsSize = $('.guess-main-box').length;
for(let i = 0 ; i < guessMainBoxsSize;i++){
	buildGuessDatas(i);
}