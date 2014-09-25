document.observe('dom:loaded', initMenu);

function initMenu() {
	var titles = $$('.title');
	titles.each( insertLink );
	//Effect.BlindDown('contents');
	$$('img.logo').each(function(e){e.style.maxHeight = Element.getHeight($$('#contents').first())+"px";});
}

var idCount = 0;
function insertLink(title) {
	var contents = $$('#contents')[0];
	
	var id = title.id;
	if (id == '') {
		title.id = idCount;
		var id = idCount++;
	}
	var a = new Element('a', {'href': '#'+id});
	a.insert(title.textContent);
	
	contents.insert(a);
	contents.insert('<br>');
	
	insertTopLink(title);
}

function insertTopLink(title) {
	var a = new Element('a', {'href': '#contents', 'class': 'back-to-top'});
	a.insert('➟');
	a.setAttribute('title', 'Back to top');
	title.insert('&nbsp;');
	title.insert(a);
}

var visible = new Array();
function switchExpand(id) {
	if (visible[id]) {
		Effect.BlindUp(id, {duration: 0.5});
		visible[id] = false;
	} else {
		Effect.BlindDown(id, {duration: 0.5});
		visible[id] = true;
	}
	
	//var tag = document.getElementById(id);
	//if (tag.style.display == "none") {
	//	tag.style.display = "block";
	//} else {
	//	tag.style.display = "none";
	//}
}