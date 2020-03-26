setInterval(
function(){
$(".btn-wrap span").click();
if(!$(".liveRoom_treasureChest").length){
	window.close();
}
}
,222)


<div id="J_treasureChestContainer" class="treasureChestContainer"><div class="liveRoom_treasureChest"><div class="box"><img src="https://huyaimg.msstatic.com/cdnimage/actprop/12_1_0_prop_web_countdown_1553669145.png" alt="宝箱"></div><i class="num" style="display: none;">1</i><p class="btn-wrap"><span class="btn">00:10</span></p><div class="waitTips" style="display: none;"><p class="tit"><span class="nick">炜爵爷【观X团】</span><span class="aName">的藏宝图</span></p><div class="cont"><p>领取还未开始，请耐心等待~</p><p>多发弹幕可提高中奖概率哦！</p></div><i class="arrow"></i></div></div></div>

<div id="J_treasureChestContainer" class="treasureChestContainer"></div>

setInterval(function(){
	console.log($(".treasureChestContainer").html());
	},100);

setInterval(
function(){
$(".btn-wrap span").click();
}
,222)