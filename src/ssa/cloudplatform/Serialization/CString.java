package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationWriter.writeBytes;

public class CString {

	public static final byte CONTAINER_TYPE = ContinerType.STRING; // data storage type(field, array, object)
	public short nameLenght;
	public byte[] name;
	public int size = 1 + 2 + 4 + 4;
	public int count;
	private char[] characters;

	private CString() {
	}

	public void setName(String name) {
		assert (name.length() < Short.MAX_VALUE);

		if (this.name != null)
			size -= this.name.length;

		nameLenght = (short) name.length();
		this.name = name.getBytes();
		size += nameLenght;
	}

	private void updateSize() {
		size += getDataSize();
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLenght);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		pointer = writeBytes(dest, pointer, count);
		pointer = writeBytes(dest, pointer, characters);
		return pointer;
	}

	public int getSize() {
		return size;
	}

	public int getDataSize() {
		return characters.length * Type.getSize(Type.CHAR);
	}

	public static CString Create(String name, String data) {
		CString string = new CString();
		string.setName(name);
		string.count = data.length();
		string.characters = data.toCharArray();
		string.updateSize();
		return string;
	}

}
