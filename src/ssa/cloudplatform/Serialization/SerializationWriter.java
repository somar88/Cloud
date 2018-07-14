package ssa.cloudplatform.Serialization;

public class SerializationWriter {

	public static final byte[] HEADER = "SC".getBytes(); // Serlialized CVloud
	public static final short VERSION = 0x0100; // big endian
	public static final byte flags = 0x0;
	
	public static int writeBytes(byte[] dest, int pointer, byte value) {
		dest[pointer++] = value;
		return pointer;
	}
	public static int writeBytes(byte[] dest, int pointer, short value) {
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	public static int writeBytes(byte[] dest, int pointer, char value) {
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	public static int writeBytes(byte[] dest, int pointer, int value) {
		dest[pointer++] = (byte) ((value >> 24) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	public static int writeBytes(byte[] dest, int pointer, long value) {
		dest[pointer++] = (byte) ((value >> 56) & 0xff); // 1_2 3_4 0000_0000 0000_0000  
		dest[pointer++] = (byte) ((value >> 48) & 0xff);
		dest[pointer++] = (byte) ((value >> 40) & 0xff);
		dest[pointer++] = (byte) ((value >> 32) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	public static int writeBytes(byte[] dest, int pointer, double value) {
		dest[pointer++] = (byte) ((value >> 56) & 0xff); // 1_2 3_4 0000_0000 0000_0000  
		dest[pointer++] = (byte) ((value >> 48) & 0xff);
		dest[pointer++] = (byte) ((value >> 40) & 0xff);
		dest[pointer++] = (byte) ((value >> 32) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	

}
