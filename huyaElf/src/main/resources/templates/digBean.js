setInterval(
function(){
	console.log('digdig: ' + new Date())
$(".btn-wrap span").click();
if(!$(".liveRoom_treasureChest").length){
	window.close();
}
}
,222)