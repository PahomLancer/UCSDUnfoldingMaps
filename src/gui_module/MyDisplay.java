package gui_module;

import processing.core.PApplet;

public class MyDisplay extends PApplet {
	
	public void setup()
	{
		size(400, 400, OPENGL);
		background(123, 44, 66);
	}
	
	public void draw()
	{
		fill(245, 124, 90);
		ellipse(200, 200, 200, 200);
		fill(123, 255, 50);
		ellipse(150, 150, 50, 50);
		ellipse(250, 150, 50, 50);
		noFill();
		arc(200, 250, 75, 75, 0, PI);
	}
}
