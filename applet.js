var attr = {
	code: "components.DynamicTreeApplet",
	width: screen.availWidth,
	height: screen.availHeight
};
var params = {
	jnlp_href: "applet.jnlp"
};
deployJava.runApplet(attr, params, "1.8");
