var LIGHTBOX_STACK_OFFSET = -10;
var LIGHTBOX_CLOSE = -1;
var EXECUTE_JS_TYPE = 'executejs';
var HISTORY_REQUEST = 'dd:gethistory';
var PING = 'ping';
var PONG = 'pong';

var webSocket;

document.observe('dom:loaded', function() {
	initWebsocket();
	initLightswitch();
});

function initWebsocket() {
	if (typeof isWelcomePage !== 'undefined' && isWelcomePage)
		return;

	var wsUrl = ((window.location.protocol === "https:") ? "wss://" : "ws://")
			+ window.location.host;
	webSocket = new WebSocket(wsUrl);
	webSocket.onmessage = function(event) {
		decodeMessage(event.data);
	};
	webSocket.onopen = function() {
		webSocket.send(HISTORY_REQUEST);
	};
	webSocket.onclose = function() {
	};
	// prevent timeout
	setInterval(function() {
		webSocket.send(PING);
	}, 30000);
	webSocket.onerror = function(error) {
		alert('An error occured' + (error ? error : ''));
	};
	webSocket.onclose = function(closeEvent) {
		if (!closeEvent.wasClean)
			alert('Server connection closed uncleanly. Code: ' + closeEvent.code);
	};
}

function decodeMessage(message) {
	if (PONG == message)
		return;

	var delimiterPos = message.indexOf(':');
	var type = message.substring(0, delimiterPos);
	var content = message.substring(delimiterPos + 1);
	switch (type) {
	case EXECUTE_JS_TYPE:
		eval(content);
		break;
	default:
		break;
	}
}

function initLightswitch() {
	// FIXME: Not working after doodles have been inserted
	$('lightswitch').observe('click', function(){
		$$('body')[0].toggleClassName('dark');
	});
}

/* Add code at the end of the html body. */
function addCode(code) {
	$$('body')[0].insert(code);
	scrollToLast();
	loadImages();
}

function loadImages() {
	$$('img.not-loaded').each(function(item) {
		imageLoadMessage($(item).readAttribute('data-image-id'));
		$(item).removeClassName('not-loaded').addClassName('loading');
	});
}

function imageLoadMessage(id) {
	webSocket.send('loadimage:' + id);
}

function insertImgSrc(id, base64) {
	var img = $$('img.loading[data-image-id="' + id + '"]')[0];
	if (img) {
		img.writeAttribute('src', 'data:image/gif;base64,' + base64);
		img.removeClassName('loading');
		img.setAttribute('alt', 'image fully loaded');
	}
}

/** Add CSS to current one */
function addCSS(css) {
	var style = document.createElement('style');
	style.setAttribute('type', 'text/css');
	style.innerHTML = css;
	document.head.appendChild(style);
}

/** Renders given code inside a lightbox (creates one if necessary). */
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
	window.onresize = function(event) {
		adjustLightboxHeight()
	};
}

function hideLightbox() {
	document.getElementById('lightboxWrapper').style.visibility = 'hidden';
	document.getElementById('lightboxWrapper').style.height = '0';
	lightboxCloseMessage();
}

function adjustLightboxHeight() {
	var lightboxContentWrapper = document
			.getElementById('lightboxContentWrapper');
	lightboxContentWrapper.style.height = (window.innerHeight - 68) + 'px';
}

function renderObjectInLightbox(id) {
	messageToJavaPlugin(id);
}

function breadcrumbsBack(depth) {
	if (depth > 0) {
		// navigate backwards (up in nesting tree)
		messageToJavaPlugin(LIGHTBOX_STACK_OFFSET - depth * 2);
	} else {
		// navigate forward (back into a last opened object)
		messageToJavaPlugin(LIGHTBOX_STACK_OFFSET + depth * 2 - 1);
	}
}

function lightboxCloseMessage() {
	messageToJavaPlugin(LIGHTBOX_CLOSE);
}

/**
 * Send a message to DoodleDebug plugin. Message should be a number.
 */
function messageToJavaPlugin(message) {
	webSocket.send('doodledebug:' + message);
}

function scrollToLast() {
	if (!atBottom())
		return;
	var elements = document.getElementsByClassName('printOutWrapper');
	var last = elements[elements.length - 1];
	last.scrollTo();
}

function atBottom() {
	var elements = document.getElementsByClassName('printOutWrapper');
	if (elements.length < 2)
		return true;
	var oldLast = elements[elements.length - 2];
	var last = elements[elements.length - 1];
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
	if (keyPressed(code)) {
		event.preventDefault();
	}
}

/**
 * Checks code and initiates actions if necessary. Returns true if custom action
 * was triggered and false if default should be executed.
 */
function keyPressed(code) {
	var j = 106;
	var k = 107;
	var esc = 27;
	var backspace = 8;

	switch (code) {
	case j:
		toNextElement();
		break;
	case k:
		toPrevElement();
		break;
	case esc:
		hideLightbox();
		break;
	case backspace:
		breadcrumbsBack(1);
		break;
	default:
		return false;
	}
	return true;
}

/**
 * Scroll to next object element in rendering output. Returns true if any
 * scrolling was done and false if not.
 */
function toNextElement() {
	var blocks = document.getElementsByClassName('printOutWrapper');
	for (i = 0; i < blocks.length; i++) {
		block = blocks[i];
		var offset = block.offsetTop;
		if (window.pageYOffset < offset) {
			block.scrollTo();
			return true;
		}
	}
	return false;
}

/**
 * Scroll to last object element in rendering output. Returns true if any
 * scrolling was done and false if not.
 */
function toPrevElement() {
	var blocks = document.getElementsByClassName('printOutWrapper');
	for (i = blocks.length - 1; i >= 0; i--) {
		var block = blocks[i];
		var offset = block.offsetTop;
		if (window.pageYOffset > offset) {
			block.scrollTo();
			return true;
		}
	}
	return false;
}

document.onkeypress = keyPressEvent;