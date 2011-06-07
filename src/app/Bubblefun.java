package app;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;

import visualization.D;

public class Bubblefun {
	
	ArrayList<Bubble> bubbles;

	public Bubblefun() {
		bubbles = new ArrayList<Bubble>();
		for (int i = 0; i < 10; i++){
			try {
				bubbles.add(new Bubble());
				new Robot().delay(100);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		D.raw(bubbles);
	}


}
