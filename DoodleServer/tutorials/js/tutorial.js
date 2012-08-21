
function switchExpand(id) {
	var tag = document.getElementById(id);
	if (tag.style.display == "none") {
		tag.style.display = "block";
	} else {
		tag.style.display = "none";
	}
}