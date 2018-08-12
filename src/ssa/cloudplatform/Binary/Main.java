package ssa.cloudplatform.Binary;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import ssa.cloudplatform.Serialization.CArray;
import ssa.cloudplatform.Serialization.CDatabase;
import ssa.cloudplatform.Serialization.CField;
import ssa.cloudplatform.Serialization.CObject;
import ssa.cloudplatform.Serialization.CString;

public class Main {

	static Random random = new Random();

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

	private static void saveToFiel(String path, byte[] data) {
		try {
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void serializationTest() {
		int[] data = new int[10000];
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt();
		}

		CDatabase database = new CDatabase("Database");
		CArray array = CArray.Integer("RandomNumbers", data);
		CField field = CField.Integer("Integer", 8);
		CField positionx = CField.Short("xpos", (short) 2);
		CField positiony = CField.Short("ypos", (short) 43);

		CObject object = new CObject("Entity");
		object.addArray(array);
		object.addArray(CArray.Char("String", "Hello World!".toCharArray()));
		object.addField(field);
		object.addField(positionx);
		object.addField(positiony);
		object.addString(CString.Create("Example String", "Testing our RCString class!"));

		database.addObject(object);
		CObject obj01 = new CObject("Cherno1");
		database.addObject(obj01);


		byte[] stream = new byte[database.getSize()];
		database.getBytes(stream, 0);
		saveToFiel("Test.cdb", stream);
	}

	public static void deserializationTest() {
		CDatabase database = CDatabase.DeseializeFromFile("Test.cdb");
		System.out.println("Database: " + database.getName());
		for (int i = 0; i < database.objects.size(); i++) {
			System.out.println("\t" + database.objects.get(i).getName());
		}
	}

	public static void main(String[] args) {

		serializationTest();
		deserializationTest();

	}

}
