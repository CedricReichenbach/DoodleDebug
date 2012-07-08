/* Add code at the end of the html body. */
function addCode(code) {
	document.body.innerHTML += code;
}

/* Renders given code inside a lightbox (creates one if necessary). */
function showInLightbox(code) {
	var lightbox = document.getElementById('lightbox');
	lightbox.innerHTML = code;
	updateLightbox();
	placeCloseButton();
	showLightbox();
}

function showLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'visible';
	document.getElementById('lightboxWrapper').style.height = 'auto';
}

function hideLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'hidden';
	document.getElementById('lightboxWrapper').style.height = '0';
}

function placeCloseButton() {
	var closeButton = document.getElementById("closeButton");
	var lightbox = document.getElementById("lightbox");
	closeButton.style.top = lightbox.style.top;
	closeButton.style.left = (parseFloat(lightbox.style.left,10) + lightbox.getWidth())+"px";
}