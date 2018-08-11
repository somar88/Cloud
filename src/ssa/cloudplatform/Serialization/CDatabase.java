package ssa.cloudplatform.Serialization;


import static ssa.cloudplatform.Serialization.SerializationWriter.*;
import static ssa.cloudplatform.Serialization.SerializationWriter.readString;
import static ssa.cloudplatform.Serialization.SerializationWriter.writeBytes;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
	
	private CDatabase() {
	}

	public void setName(String name) {
		assert (name.length() < Short.MAX_VALUE);

		if (this.name != null)
			size -= this.name.length;

		nameLenght = (short) name.length();
		this.name = name.getBytes();
		size += nameLenght;
	}
	
	public String getName() {
		return new String(name, 0, name.length);
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

	public static CDatabase Deserialize(byte[] data) {
		int pointer = 0;
		// String header = readString(data, pointer, 4);
		// System.out.print(header);	

		assert (readString(data, pointer, HEADER.length).equals(HEADER));
		pointer += HEADER.length;
		
		byte containerType = readByte(data, pointer++);
		assert(containerType == CONTAINER_TYPE);
		
		CDatabase result = new CDatabase();
		result.nameLenght = readShort(data, pointer);
		pointer +=2;
		result.name = readString(data, pointer, result.nameLenght).getBytes();
		pointer +=result.nameLenght;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		result.objectCount = readShort(data, pointer);
		pointer +=2;
		
		return result;
	}

	public static CDatabase DeseializeFromFile(String path) {
		byte[] buffer = null;
		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
			buffer = new byte[stream.available()];
			stream.read(buffer);
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Deserialize(buffer);
	}

}
