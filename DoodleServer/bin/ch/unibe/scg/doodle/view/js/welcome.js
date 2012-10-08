function appearWelcome() {
	var title = document.getElementById('welcomeTitle');
	var e = new Effect.Grow(title);
	setTimeout("appear('shortTut')", 500);
	setTimeout("appear('tutorial')", 1000);
	setTimeout("appear('info')", 1500);
}

function appear(name) {
	var element = document.getElementById(name);
	new Effect.Grow(element);
}