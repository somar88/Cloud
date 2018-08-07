package ssa.cloudplatform.Binary;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import ssa.cloudplatform.Serialization.CArray;
import ssa.cloudplatform.Serialization.CField;
import ssa.cloudplatform.Serialization.CObject;

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
		int[] data = new int[10000];
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt();
		}
		CArray array = CArray.Integer("RandomNumber", data);
		CField field = CField.Integer("Integer", 8);
		
		CObject object = new CObject("entity");
		object.addArray(array);
		object.addField(field);

		byte[] stream = new byte[object.getSize()];
		object.getBytes(stream, 0);
		saveToFiel("Test.cdb", stream);
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
