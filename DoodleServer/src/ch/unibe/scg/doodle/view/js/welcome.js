function appearWelcome() {
	var title = document.getElementById('welcomeTitle');
	var e = new Effect.Grow(title);
	setTimeout("appearInfo()", 500);
}

function appearInfo() {
	var info = document.getElementById('info');
	new Effect.Grow(info);
	setTimeout("infoVisible()", 50);
}

function infoVisible() {
	info.style.visibility = "visible";
}