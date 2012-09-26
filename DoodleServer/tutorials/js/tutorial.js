document.observe("dom:loaded", initMenu);

function initMenu() {
	var titles = $$('.title');
	titles.each( insertLink );
	//Effect.BlindDown('contents');
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
	var a = new Element('a', {'href': '#contents'});
	a.insert('&uarr;');
	title.insert('&nbsp;');
	title.insert(a);
}

var visible = false;
function switchExpand(id) {
	if (visible) {
		Effect.BlindUp(id);
		visible = false;
	} else {
		Effect.BlindDown(id);
		visible = true;
	}
	
	//var tag = document.getElementById(id);
	//if (tag.style.display == "none") {
	//	tag.style.display = "block";
	//} else {
	//	tag.style.display = "none";
	//}
}