package ssa.cloudplatform.Main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import ssa.cloudplatform.Binary.Sandbox;
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
		
		database.serializeToFiel("Test.cdb");
	}

	public static void deserializationTest() {
		CDatabase database = CDatabase.DeseializeFromFile("Test.cdb");
		System.out.println("Database: " + database.getName());
		for (CObject object : database.objects) {
			System.out.println("\t" + object.getName());
			for (CField field : object.fields) {
				System.out.println("\t\t" + field.getName());
			}
			System.out.println();
			for (CString string: object.strings) {
				System.out.println("\t\t" + string.getName() + " = " + string.getString());
			}
			System.out.println();
			for (CArray array : object.arrays) {
				System.out.println("\t\t" + array.getName());
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {

//		serializationTest();
//		deserializationTest();

		Sandbox sandBox = new Sandbox();
		sandBox.play();
	}

}
