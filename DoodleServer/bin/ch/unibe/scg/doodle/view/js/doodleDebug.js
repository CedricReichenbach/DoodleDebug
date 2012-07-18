var LIGHTBOX_STACK_OFFSET = -10;
var LIGHTBOX_CLOSE = -1;

/* Add code at the end of the html body. */
function addCode(code) {
	document.body.innerHTML += code;
}

/* Add CSS to current one */
function addCSS(css) {
	var style = document.createElement('style');
	style.setAttribute('type','text/css');
	style.innerHTML = css;
	document.head.appendChild(style);
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
	document.getElementById('lightboxWrapper').style.height = 'auto';
}

function hideLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'hidden';
	document.getElementById('lightboxWrapper').style.height = '0';
	lightboxCloseMessage();
}

function renderObjectInLightbox(id) {
	messageToJavaPlugin(id);
}

function breadcrumbsBack(depth) {
	messageToJavaPlugin(LIGHTBOX_STACK_OFFSET - depth);
}

function lightboxCloseMessage() {
	messageToJavaPlugin(LIGHTBOX_CLOSE);
}

/**
 * Send a message to DoodleDebug plugin. Message should be a number.
 */
function messageToJavaPlugin(message) {
	window.location = 'doodledebug:'+message;
}