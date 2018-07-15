package ssa.cloudplatform.Binary;
import static ssa.cloudplatform.Serialization.SerializationWriter.*;

public class Main {

	public static void printHex(int value) {
		System.out.printf("%x\n", value);
	}

	public static void printBin(int value) {
		System.out.printf(Integer.toBinaryString(value) + "\n");
	}
	
	public static void printBytes(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.printf("0x%x ", data[i]);
		}
		System.out.println();
	}

	public static void main(String[] args) {

		byte[] data = new byte[32];
//		long  number = 0x12345678;
		int pointer = 0;
		
		String me = "Somar";
		pointer = writeBytes(data, pointer, 505);
		
		printBytes(data);
		
		System.out.println( readInt(data, 0));
		printBytes(data);
		
//		#Ep - 04
//		int color = 0xff00ff;
//		int r = (color & 0xff0000) >> 16;
//		int g = (color & 0x00ff00) >> 8;
//		int b = (color & 0xff) >> 0;
//		printHex(color);
//		printHex(r);
//		printHex(g);
//		printHex(b);
//		int shuffle = 0x000000;
//		shuffle = (b << 16) | (r << 8) | g;
//		printHex(shuffle);
		
	}

}
