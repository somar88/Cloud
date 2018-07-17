package ssa.cloudplatform.Binary;

import ssa.cloudplatform.Serialization.Field;
import ssa.cloudplatform.Serialization.IntField;
import sun.net.www.content.audio.basic;

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
		byte[]	data = new byte[50];
		
		Field field = new IntField("Test", 8);
		field.getBytes(data, 0);
		printBytes(data);
		
	}

}
