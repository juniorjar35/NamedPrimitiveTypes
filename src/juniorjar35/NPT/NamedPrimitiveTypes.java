package juniorjar35.NPT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NamedPrimitiveTypes {
	
	private NamedPrimitiveTypes() {throw new RuntimeException("Cannot instantiate!");};
	
	public static NPTBatch copy(NPTBatch c) {
		NPTBatch cpy = new NPTBatch();
		cpy.data.putAll(c.data);
		return cpy;
	}
	
	public static void writeString(String data, DataOutput out) throws IOException {
		if (data.length() > Byte.MAX_VALUE) throw new IllegalArgumentException("string length higher than 127!");
		out.writeByte((byte) data.length());
		out.write(data.getBytes(StandardCharsets.US_ASCII));
	}
	
	public static String readString(DataInput in) throws IOException {
		int len = in.readByte();
		byte[] a = new byte[len];
		in.readFully(a, 0, len);
		return new String(a,StandardCharsets.US_ASCII);
	}
	
	public static NPTBatch read(DataInput in) throws IOException {
		NPTBatch out = new NPTBatch();
		out.read(in);
		return out;
	}
	
	public static void write(NPTBatch data, DataOutput out) throws IOException {
		data.write(out);
	}
	
}
