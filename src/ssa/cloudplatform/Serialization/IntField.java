package ssa.cloudplatform.Serialization;
import static ssa.cloudplatform.Serialization.SerializationWriter.*;

public class IntField extends Field {
	
	public IntField(String name, int value) {
		setName(name);
		type = Type.INT;
		data = new byte[Type.getZize(Type.INT)];
		writeBytes(data,0,value);
	}

}
