package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationWriter.*;

public class CField {

	public final byte CONTAINER_TYPE = ContinerType.FIELD; // data storage type(field, array, object)
	public short nameLenght;
	public byte[] name;
	public byte type; // int - 4 bits
	public byte[] data;
	
	private CField() {
		
	}

	public void setName(String name) {
		assert (name.length() < Short.MAX_VALUE);
		nameLenght = (short) name.length();
		this.name = name.getBytes();
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLenght);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, data);
		return pointer;
	}

	public int getSize() {
		assert ( data.length == Type.getSize(type)); // to make sure that it is correct
		return 1 + 2 + name.length + 1 + data.length;
	}

	public static CField Byte(String name, byte value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.BYTE;
		field.data = new byte[Type.getSize(Type.BYTE)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Short(String name, short value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.SHORT;
		field.data = new byte[Type.getSize(Type.SHORT)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Char(String name, char value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.CHAR;
		field.data = new byte[Type.getSize(Type.CHAR)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Integer(String name, int value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.INTEGER;
		field.data = new byte[Type.getSize(Type.INTEGER)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Long(String name, long value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.LONG;
		field.data = new byte[Type.getSize(Type.LONG)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Float(String name, float value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.FLOAT;
		field.data = new byte[Type.getSize(Type.FLOAT)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Double(String name, double value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.DOUBLE;
		field.data = new byte[Type.getSize(Type.DOUBLE)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static CField Boolean(String name, boolean value) {
		CField field = new CField();
		field.setName(name);
		field.type = Type.BOOLEAN;
		field.data = new byte[Type.getSize(Type.BOOLEAN)];
		writeBytes(field.data, 0, value);
		return field;
	}
}
