package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationUtils.*;

import java.util.ArrayList;
import java.util.List;

public class CObject extends CSCore {

	public static final byte CONTAINER_TYPE = ContinerType.OBJECT; // data storage type(field, array, object)
	private short fieldCount;
	public List<CField> fields = new ArrayList<CField>();
	private short stringCount;
	public List<CString> strings = new ArrayList<CString>();
	private short arrayCount;
	public List<CArray> arrays = new ArrayList<CArray>();

	private CObject() {
	}

	public CObject(String name) {
		size += 1 + 2 + 2 + 2;
		setName(name);
	}

	public void addField(CField field) {
		fields.add(field);
		size += field.getSize();
		fieldCount = (short) fields.size();

	}

	public void addString(CString string) {
		strings.add(string);
		size += string.getSize();
		stringCount = (short) strings.size();

	}

	public void addArray(CArray array) {
		arrays.add(array);
		size += array.getSize();
		arrayCount = (short) arrays.size();

	}

	public int getSize() {
		return size;
	}

	public CField findField(String name) {
		for (CField field : fields) {
			if (field.getName().equals(name))
				return field;
		}
		return null;
	}

	public CString findString(String name) {
		for (CString string : strings) {
			if (string.getName().equals(name))
				return string;
		}
		return null;
	}

	public CArray findArray(String name) {
		for (CArray array : arrays) {
			if (array.getName().equals(name))
				return array;
		}
		return null;
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);

		pointer = writeBytes(dest, pointer, fieldCount);
		for (CField field : fields) {
			pointer = field.getBytes(dest, pointer);
		}

		pointer = writeBytes(dest, pointer, stringCount);
		for (CString string : strings) {
			pointer = string.getBytes(dest, pointer);
		}

		pointer = writeBytes(dest, pointer, arrayCount);
		for (CArray array : arrays) {
			pointer = array.getBytes(dest, pointer);
		}

		return pointer;

	}

	public static CObject Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert (containerType == CONTAINER_TYPE);

		CObject result = new CObject();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;

		result.size = readInt(data, pointer);
		pointer += 4;

//		not needed any more: pointer += result.size - sizeOffset - result.nameLength;

		result.fieldCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.fieldCount; i++) {
			CField field = CField.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}

		result.stringCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.stringCount; i++) {
			CString string = CString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}

		result.arrayCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.arrayCount; i++) {
			CArray array = CArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}

		return result;

	}

}
