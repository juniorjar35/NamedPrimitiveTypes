package juniorjar35.NPT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NamedPrimitiveTypes {
	
	private NamedPrimitiveTypes() {throw new RuntimeException("Cannot instantiate!");};
	
	public static NPTBatch copy(NPTBatch c) {
		NPTBatch cpy = new NPTBatch();
		cpy.data.putAll(c.data);
		return cpy;
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
