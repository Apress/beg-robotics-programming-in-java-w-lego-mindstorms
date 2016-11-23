//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		ch13p2.java
//This program demonstrates a threaded song/counter program
//****************************************************************************

import lejos.nxt.Button;

public class ch13p2 {
	private static Music mThread;
	private static Counter cThread;

	public static void main(String[] args) {
		mThread = new Music();
		cThread = new Counter();

		mThread.start();
		cThread.start();
		try {
			while (!Button.ESCAPE.isPressed()) {
				Thread.yield();
			}
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
