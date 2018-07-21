package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationWriter.writeBytes;

public class Array {

	public final byte CONTAINER_TYPE = ContinerType.ARRAY; // data storage type(field, array, object)
	public short nameLenght;
	public byte[] name;
	public byte type; // element type
	public int count; // element count
	public byte[] data;

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

	public static Array Byte(String name, int[] data) {
		Array array = new Array();
		array.setName(name);
		array.type = Type.INTEGER;
		array.data = new byte[Type.getSize(Type.INTEGER)];
		writeBytes(array.data, 0, data);
		return array;
	}
}
