package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationWriter.writeBytes;

import java.util.ArrayList;
import java.util.List;

public class CDatabase {

	public static final byte[] HEADER = "RCDB".getBytes();
	public static final byte CONTAINER_TYPE = ContinerType.DATABASE; // data storage type(field, array, object)
	public short nameLenght;
	public byte[] name;
	private int size = HEADER.length + 1 + 2 + 4 + 2;
	private short objectCount;
	private List<CObject> objects = new ArrayList<CObject>();

	public CDatabase(String name) {
		setName(name);
	}

	public void setName(String name) {
		assert (name.length() < Short.MAX_VALUE);

		if (this.name != null)
			size -= this.name.length;

		nameLenght = (short) name.length();
		this.name = name.getBytes();
		size += nameLenght;
	}

	public void addObject(CObject object) {
		objects.add(object);
		size += object.getSize();

		objectCount = (short) objects.size();

	}

	public int getSize() {
		return size;
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, HEADER);
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLenght);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);

		pointer = writeBytes(dest, pointer, objectCount);
		for (CObject object : objects) {
			pointer = object.getBytes(dest, pointer);
		}

		return pointer;

	}

}
