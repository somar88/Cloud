package ssa.cloudplatform.Main;

public class PlayGround {
	
	public static void printHex(int value) {
		System.out.printf("%x\n", value);
	}
	
	public static void printBin(int value) {
		System.out.printf(Integer.toBinaryString(value) + "\n");		
	}
	
	public static void main(String[] args) {
		
		int color = 0xff00ff;
		
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0x00ff00) >> 8;
		int b = (color & 0xff) >> 0;
		
		printHex(color);
		printHex(r);
		printHex(g);
		printHex(b);
		
		int shuffel = 0x000000;
		
		shuffel = (b << 16) | (r << 8) | g;
		printHex(shuffel);
	}

}
