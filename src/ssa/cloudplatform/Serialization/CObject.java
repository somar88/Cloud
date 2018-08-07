package ssa.cloudplatform.Serialization;

import static ssa.cloudplatform.Serialization.SerializationWriter.writeBytes;

import java.util.ArrayList;
import java.util.List;

public class CObject {

	public final byte CONTAINER_TYPE = ContinerType.OBJECT; // data storage type(field, array, object)
	public short nameLenght;
	public byte[] name;
	private short fieldCount;
	private List<CField> fields = new ArrayList<CField>();
	private short arrayCount;
	private List<CArray> arrays = new ArrayList<CArray>();

	private int size = 1 + 2 + 2 + 2;
	
	
	public CObject(String name) {
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

	public void addField(CField field) {
		fields.add(field);
		size += field.getSize();
		fieldCount = (short) fields.size();

	}

	public void addArray(CArray array) {
		arrays.add(array);
		size += array.getSize();
		arrayCount = (short) arrays.size();

	}

	public int getSize() {
		return size;
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLenght);
		pointer = writeBytes(dest, pointer, name);
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for (CField field : fields) {
			pointer = field.getBytes(dest, pointer);
		}
		

		pointer = writeBytes(dest, pointer, arrayCount);
		for(CArray array : arrays) {
			pointer = array.getBytes(dest, pointer);
		}
		
		return pointer;
		
	}

}
