package ssa.cloudplatform.Serialization;

import java.util.ArrayList;
import java.util.List;

public class CObject {
	
	public final byte CONTAINER_TYPE = ContinerType.OBJECT; // data storage type(field, array, object)
	public short nameLenght;
	public byte[] name;

	private List<CField> fields = new ArrayList<CField>();
	private List<CArray> arrays= new ArrayList<CArray>();
	
	public CObject(String name) {
	setName(name);		
	}


	public void setName(String name) {
		assert (name.length() < Short.MAX_VALUE);
		nameLenght = (short) name.length();
		this.name = name.getBytes();
	}

}
