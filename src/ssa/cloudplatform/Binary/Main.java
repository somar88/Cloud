package ssa.cloudplatform.Binary;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import ssa.cloudplatform.Serialization.Array;
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

	static Random random = new Random();

	public static void main(String[] args) {

		// Test of Field
		System.out.println("Test of Field: ");

		Field field = Field.Integer("test", 10);
		byte[] fdata = new byte[field.getSize()];
		field.getBytes(fdata, 0);
		printBytes(fdata);

		// Test of Array
		System.out.println("Test of array: ");

		int[] data = new int[10000];
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt();
		}

//		byte[] adata = new byte[] { 1, 2, 3, 4, 5 };
		System.out.println(data.length + " - " + data[5000]);
		Array array = Array.Integer("Test", data);

		byte[] stream = new byte[array.getSize()];
		array.getBytes(stream, 0);
		saveToFiel("Test.cdb", stream);
//		printBytes(stream);
	}

	private static void saveToFiel(String path, byte[] data) {
		try {
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
