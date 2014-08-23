var ids = [ 'appLogSelector', 'welcomeTitle', 'shortTut', 'tutorial', 'info' ];

function appearWelcome() {
	for ( var i = 0; i < ids.length; i++) {
		try {
		$(ids[i]).hide();
		} catch (e) {
			continue;
		}
		setTimeout("appear('" + ids[i] + "')", 500 * i);
	}
}

function appear(name) {
	var element = document.getElementById(name);
	new Effect.Grow(element);
}