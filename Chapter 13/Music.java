//****************************************************************************
//Wei Lu Java Robotics Programming with Lego EV3/NXT2.0 		Music.java
//Play a "song" consisting of predefined beeps on a thread
//****************************************************************************

import lejos.nxt.Sound;

class Music extends Thread {
	public void run() {
		short Low_G = 392, B_Flat = 470, D = 588, C = 523, E_Flat = 627, F = 697, G = 784, A_Flat = 830;

		short[] note = { C, 600, G, 100, F, 100, G, 400, C, 400, 0, 600,
				A_Flat, 100, G, 100, A_Flat, 200, G, 200, F, 400, B_Flat, 600,
				F, 100, E_Flat, 100, F, 400, B_Flat, 400, Low_G, 600, G, 100,
				F, 100, G, 200, F, 200, E_Flat, 200, D, 200, E_Flat, 600, D,
				100, E_Flat, 100, F, 600, E_Flat, 100, F, 100, G, 200, F, 200,
				E_Flat, 200, D, 200, C, 400, A_Flat, 400, G, 1400, A_Flat, 100,
				G, 100, F, 100, G, 1400

		};

		for (int i = 0; i < note.length; i += 2) {
			short w = note[i + 1];
			Sound.playTone(note[i], w);
			Sound.pause(w);
		}
	}
}