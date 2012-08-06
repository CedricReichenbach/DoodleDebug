var LIGHTBOX_STACK_OFFSET = -10;
var LIGHTBOX_CLOSE = -1;

/* Add code at the end of the html body. */
function addCode(code) {
	document.body.innerHTML += code;
	scrollToLast();
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
	adjustLightboxHeight();
	window.onresize = function(event) { adjustLightboxHeight() };
}

function hideLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'hidden';
	document.getElementById('lightboxWrapper').style.height = '0';
	lightboxCloseMessage();
}

function adjustLightboxHeight() {
	var lightboxContentWrapper = document.getElementById('lightboxContentWrapper');
	lightboxContentWrapper.style.height = (window.innerHeight - 68)+'px';
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

function scrollToLast() {
	if (!atBottom())
		return;
	var elements = document.getElementsByClassName('printOutWrapper');
	var last = elements[elements.length-1];
	last.scrollTo();
}

function atBottom() {
	var elements = document.getElementsByClassName('printOutWrapper');
	if (elements.length < 2)
		return true;
	var oldLast = elements[elements.length-2];
	var last = elements[elements.length-1];
	// for small elements
	if (window.pageYOffset + window.innerHeight >= last.offsetTop)
		return true;
	// for big elements
	return window.pageYOffset >= oldLast.offsetTop;
}

/* key navigation */

function keyPressEvent(event) {
	if (!event)
		event = window.event;
	if (event.which) {
		var code = event.which;
	} else if (event.keyCode) {
    	var code = event.keyCode;
	}
	keyPressed(code);
}

function keyPressed(code) {
	var j = 106;
	var k = 107;
	
	if (code == j) {
		toNextElement();
	} else if (code == k) {
		toPrevElement();
	}
}

function toNextElement() {
	// TODO
	alert('next');
}

function toPrevElement() {
	// TODO
	alert('prev');
}

document.onkeypress = keyPressEvent;