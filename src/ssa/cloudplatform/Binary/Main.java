package ssa.cloudplatform.Binary;

import ssa.cloudplatform.Serialization.Field;

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
		Field field = Field.Integer("test", 10);

		byte[] data = new byte[field.getSize()];
		field.getBytes(data, 0);
		printBytes(data);

	}

}
