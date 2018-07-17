package ssa.cloudplatform.Serialization;
import static ssa.cloudplatform.Serialization.SerializationWriter.*;

public class Field {

	public final byte 	CONTAINER_TYPE = ContinerType.FIELD; // data storage type(field, array, object)
	public short 		nameLenght;
	public byte[] 		name;
	public byte 		type; // int - 4 bits
	public byte[] 		data;

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
}
