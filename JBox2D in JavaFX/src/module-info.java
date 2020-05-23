module executor {
	
	requires transitive jbox2d.library;
	requires transitive javafx.graphics;
	
	exports application;
	opens application;
	
}