package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationUtils.*;
import static ssa.cloudplatform.Serialization.SerializationUtils.readString;
import static ssa.cloudplatform.Serialization.SerializationUtils.writeBytes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CDatabase extends CSCore{

	public static final byte[] HEADER = "RCDB".getBytes();
	public static final short VERSION = 0x0100; // big Indian
	public static final byte CONTAINER_TYPE = ContinerType.DATABASE; // data storage type(field, array, object)
	private short objectCount;
	public List<CObject> objects = new ArrayList<CObject>();

	public CDatabase(String name) {
		size += HEADER.length + 2 + 1 + 2;
		setName(name);
	}

	private CDatabase() {
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
		pointer = writeBytes(dest, pointer, VERSION);
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
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

		if(readShort(data, pointer) != VERSION){
			System.err.println("Invalid CDB  Version!");
			return null;
		}
		pointer += 2;

		byte containerType = readByte(data, pointer++);
		assert (containerType == CONTAINER_TYPE);

		CDatabase result = new CDatabase();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;

		result.size = readInt(data, pointer);
		pointer += 4;

		result.objectCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.objectCount; i++) {
			CObject obj = CObject.Deserialize(data, pointer);
			result.objects.add(obj);
			pointer += obj.getSize();
		}

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

	public void serializeToFiel(String path) {
		byte[] data = new byte[getSize()];
		getBytes(data, 0);
		try {
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
