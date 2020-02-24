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
window.idName=new Array();
window.result=new Array();
window.title=new Array();
window.createtime=new Array();
window.startedFlg=new Array();
window.guessMainBoxsSize = $('.guess-main-box').length;
console.log('size'+guessMainBoxsSize);
window.buildGuessDatas= function(i){
	guessDatas[i] = new Array();
	guessData = new Array();
	index[i]=0;
	interval[i]=0;
	startedFlg[i]=false;
	guessDataInterval[i]=setInterval(function(){
		interval[i]++;
		resultFlg1[i] = $($('.guess-unit span')[i*2]).attr('class')?true:false;
		resultFlg2[i] = $($('.guess-btn')[i*2]).text()=='流局'?true:false;
		endFlg[i] = $($('.guess-btn')[i*2]).text()=='结束种豆'?true:false;
		console.log($($('.guess-btn')[i*2]).text());
		if((resultFlg1[i] || resultFlg2[i]) && startedFlg[i]){
				if(resultFlg1[i]){
					result[i] = $($('.guess-unit span')[i*2]).attr('class');
				}else{
					result[i] = '流局';
				}
				console.log(result[i]);
				idName[i] = 'guessResult'+i;
				$("#msg_send_bt").append("<p class ='guessResult' id ='"+ idName[i]+"'>"+result[i] + "</p>");
				clearInterval(guessDataInterval[i]);
		}
		if(!endFlg[i] && interval[i]%20==0 &&!(resultFlg1[i] || resultFlg2[i])){
			startedFlg[i]=true;
			rate1[i] = $($('.guess-btn')[i*2]).text();
			rate2[i] = $($('.guess-btn')[i*2+1]).text();
			num1[i] = $($('.process-num')[i*2]).text();
			num2[i] = $($('.process-num')[i*2+1]).text();
			name1[i] = $($('.process-name')[i*2]).text();
			name2[i] = $($('.process-name')[i*2+1]).text();
			result[i] = $($('.guess-unit span')[i*2]).attr('class');
			createtime[i] = (new Date()).toTimeString();
			title[i] = $($('.box-title .name')[i]).text();
			guessData[i] = { 'rate1': rate1[i],'rate2': rate2[i],'num1': num1[i],'num2': num2[i],
					'name1': name1[i],'name2': name2[i],'title': title[i],'result1': result[i],'createtime': createtime[i]};
			guessDatas[i].push(guessData[i]);
			index[i]++
		}
	},50);
};
for(let j = 0 ; j < guessMainBoxsSize;j++){
buildGuessDatas(j);
};
		
		
