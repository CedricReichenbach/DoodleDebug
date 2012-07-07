/* Add code at the end of the html body. */
function addCode(code) {
	document.body.innerHTML += code;
}

/* Renders given code inside a lightbox (creates one if necessary). */
function showInLightbox(code) {
	var lightbox = document.getElementById('lightbox');
	lightbox.innerHTML = code;
	updateLightbox();
	showLightbox();
}

function showLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'visible';
}

function hideLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'hidden';
}