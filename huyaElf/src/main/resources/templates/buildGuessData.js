
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
window.buildGuessDatas= function(i){
	guessDatas[i] = new Array();
	guessData = new Array();
	index[i]=0;
	interval[i]=0;
	startedFlg[i]=false;
	title[i] = $($('.box-title .name')[i]).text();
	name1[i] = $($('.process-name')[i*2]).text();
	name2[i] = $($('.process-name')[i*2+1]).text();
	guessDataInterval[i]=setInterval(function(){
		interval[i]++;
		if(!$($('.guess-btn')[i*2]).text()){
			return;
		}
		resultFlg1[i] = $($('.guess-unit span')[i*2]).attr('class')?true:false;
		resultFlg2[i] = $($('.guess-btn')[i*2]).text()=='流局'?true:false;
		endFlg[i] = $($('.guess-btn')[i*2]).text()=='结束种豆'?true:false;
		console.log($($('.guess-btn')[i*2]).text());
		if((resultFlg1[i] || resultFlg2[i]) && startedFlg[i]){$("#msg_send_bt").click((function(){$(this).hasClass("enable")&&e.send()})),
				if(resultFlg1[i]){
					result[i] = $($('.guess-unit span')[i*2]).attr('class');
				}else{
					result[i] = '流局';
				}
				console.log(result[i]);
				endTime[i] = new Date().getTime();
				$("#msg_send_bt").append("<p class ='guessResult' id ='guessResult"+ i +"'>"+startTime[i] + "," +endTime[i] + "," + result[i] + "," + name1[i] + "," + name2[i] + "," + title[i] + "</p>");
				clearInterval(guessDataInterval[i]);
		}
		if(!endFlg[i] && !resultFlg1[i] && !resultFlg2[i] && interval[i]%20==0){
			if(index[i]==0){
				startTime[i] = new Date().getTime();
				$("#msg_send_bt").append("<p class ='startTime' id ='startTime"+ i+"'>"+startTime[i] + "</p>");
			}
			startedFlg[i]=true;
			recordTime[i] = new Date().getTime();
			rate1[i] = $($('.guess-btn')[i*2]).text();
			rate2[i] = $($('.guess-btn')[i*2+1]).text();
			num1[i] = $($('.process-num')[i*2]).text();
			num2[i] = $($('.process-num')[i*2+1]).text();
			guessData[i] = { 'rate1': rate1[i],'rate2': rate2[i],'num1': num1[i],'num2': num2[i],
					'recordTime': recordTime[i]};
			guessDatas[i].push(guessData[i]);
			index[i]++;
		}
	},50);
};
window.guessMainBoxsSize = $('.guess-main-box').length;
console.log('size'+guessMainBoxsSize);
for(let i = 0 ; i < guessMainBoxsSize;i++){
	buildGuessDatas(i);
}