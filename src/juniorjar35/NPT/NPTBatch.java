package juniorjar35.NPT;

import static juniorjar35.NPT.NamedPrimitiveTypes.readString;
import static juniorjar35.NPT.NamedPrimitiveTypes.writeString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NPTBatch {
	
	public NPTBatch() {};
	
	public static final short NPT_VERSION = 0x0102;
	private NPTBatch parent = null;
	protected Map<String, Object> data = new HashMap<String, Object>();
	
	private void readObj(Map<String, Object> out, DataInput in) throws IOException {
		String name = readString(in);
		byte sig = in.readByte();
		if (sig == 'I') out.put(name, in.readInt());
		if (sig == 'L') out.put(name, in.readLong());
		if (sig == 'S') out.put(name, in.readShort());
		if (sig == 'F') out.put(name, in.readFloat());
		if (sig == 'D') out.put(name, in.readDouble());
		if (sig == 'B') out.put(name, in.readBoolean());
		if (sig == 'S') out.put(name, in.readChar());
		if (sig == 'i') {int c = in.readInt(); int[] a = new int[c]; for (int i = 0; i < c; i++) {a[i] = in.readInt();}; out.put(name, a);};
		if (sig == 'l') {int c = in.readInt(); long[] a = new long[c]; for (int i = 0; i < c; i++) {a[i] = in.readLong();}; out.put(name, a);};
		if (sig == 's') {int c = in.readInt(); short[] a = new short[c]; for (int i = 0; i < c; i++) {a[i] = in.readShort();}; out.put(name, a);};
		if (sig == 'f') {int c = in.readInt(); float[] a = new float[c]; for (int i = 0; i < c; i++) {a[i] = in.readFloat();}; out.put(name, a);};
		if (sig == 'd') {int c = in.readInt(); double[] a = new double[c]; for (int i = 0; i < c; i++) {a[i] = in.readDouble();}; out.put(name, a);};
		if (sig == 'b') {int c = in.readInt(); boolean[] a = new boolean[c]; for (int i = 0; i < c; i++) {a[i] = in.readBoolean();}; out.put(name, a);};
		if (sig == 'c') {int c = in.readInt(); char[] a = new char[c]; for (int i = 0; i < c; i++) {a[i] = in.readChar();}; out.put(name, a);};
		if (sig == 'b') {int c = in.readInt(); byte[] a = new byte[c]; for (int i = 0; i < c; i++) {a[i] = in.readByte();}; out.put(name, a);};
		if (sig == 'X') out.put(name, in.readUTF());
		if (sig == 'J') {
			NPTBatch b = new NPTBatch();
			int c = in.readInt();
			for (int i = 0; i < c; i++) {
				readObj(b.data, in);
			}
			out.put(name, b);
		}
	}
	
	public void read(DataInput in) throws IOException {
		read(in,false);
	}
	
	public void read(DataInput in, boolean ignoreVersionCheck) throws IOException {
		short version = in.readShort();
		if (version != NPT_VERSION && !ignoreVersionCheck) throw new IOException("Expected version: " + Integer.toHexString(NPT_VERSION) + "|| Got: " + Integer.toHexString(version));
		int count = in.readInt();
		for (int i = 0; i < count; i++) {
			readObj(data, in);
		}
	}
	
	private void writeObj(String name, Object v, DataOutput out) throws IOException {
		writeString(name, out);
		if (v instanceof Integer) {out.writeByte('I'); out.writeInt((Integer)v);};
		if (v instanceof Long) {out.writeByte('L'); out.writeLong((Long)v);};
		if (v instanceof Short) {out.writeByte('S'); out.writeShort((Short)v);};
		if (v instanceof Float) {out.writeByte('F'); out.writeFloat((Float)v);};
		if (v instanceof Double) {out.writeByte('D'); out.writeDouble((Double)v);};
		if (v instanceof Boolean) {out.writeByte('B'); out.writeBoolean((Boolean)v);};
		if (v instanceof Character) {out.writeByte('C'); out.writeChar((Character)v);};
		if (v instanceof int[]) {out.writeByte('i'); int[] r = (int[]) v; out.writeInt(r.length); for (int w : r) out.writeInt(w);};
		if (v instanceof long[]) {out.writeByte('l'); long[] r = (long[]) v; out.writeInt(r.length); for (long w : r) out.writeLong(w);};
		if (v instanceof short[]) {out.writeByte('s'); short[] r = (short[]) v; out.writeInt(r.length); for (short w : r) out.writeShort(w);};
		if (v instanceof float[]) {out.writeByte('f'); float[] r = (float[]) v; out.writeInt(r.length); for (float w : r) out.writeFloat(w);};
		if (v instanceof double[]) {out.writeByte('d'); double[] r = (double[]) v; out.writeInt(r.length); for (double w : r) out.writeDouble(w);};
		if (v instanceof boolean[]) {out.writeByte('b'); boolean[] r = (boolean[]) v; out.writeInt(r.length); for (boolean w : r) out.writeBoolean(w);};
		if (v instanceof char[]) {out.writeByte('c'); char[] r = (char[]) v; out.writeInt(r.length); for (char w : r) out.writeChar(w);};
		if (v instanceof byte[]) {out.writeByte('b'); byte[] r = (byte[]) v; out.writeInt(r.length); for (byte w : r) out.writeByte(w);}
		if (v instanceof String) {out.writeByte('X'); out.writeUTF((String)v);}
		if (v instanceof NPTBatch) {
			out.write('J');
			NPTBatch o = (NPTBatch) v;
			out.writeInt(o.data.size());
			Set<Map.Entry<String, Object>> entries = o.data.entrySet();
			for (Map.Entry<String, Object> entry : entries) {
				writeObj(entry.getKey(), entry.getValue(), out);
			}
		};
	}
	
	public void write(DataOutput out) throws IOException {
		out.writeShort(NPT_VERSION);
		out.writeInt(data.size());
		Set<Map.Entry<String, Object>> entries = data.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			writeObj(entry.getKey(), entry.getValue(), out);
		} 
	}
	
	public boolean hasKey(String name) {
		return data.containsKey(name);
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	public Class<?> getKeyType(String name){
		return data.get(name).getClass();
	}
	
	public int getSize() {
		return data.size();
	}
	
	public void setShort(String name, short v) {
		data.put(name, v);
	}
	
	public short getShort(String name) {
		return (short) data.get(name);
	}
	
	public void setInteger(String name, int v) {
		data.put(name, v);
	}
	
	public int getInteger(String name) {
		return (int) data.get(name);
	}

	public void setLong(String name, long v) {
		data.put(name, v);
	}
	
	public long getLong(String name) {
		return (long) data.get(name);
	}
	
	public void setFloat(String name, float v) {
		data.put(name, v);
	}
	
	public float getFloat(String name) {
		return (float) data.get(name);
	}
	
	public void setDouble(String name, double v) {
		data.put(name, v);
	}
	
	public double getDouble(String name) {
		return (double) data.get(name);
	}
	
	public void setBoolean(String name, boolean v) {
		data.put(name, v);
	}
	
	public boolean getBoolean(String name) {
		return (boolean) data.get(name);
	}
	
	public void setCharacter(String name, char v) {
		data.put(name, v);
	}
	
	public char getCharacter(String name) {
		return (char) data.get(name);
	}
	
	public void setByte(String name, byte v) {
		data.put(name, v);
	}
	
	public byte getByte(String name) {
		return (byte) data.get(name);
	}
	
	public void setByteArray(String name, byte[] array) {
		data.put(name, array);
	}
	
	public byte[] getByteArray(String name) {
		return (byte[]) data.get(name);
	}
	
	public void setIntegerArray(String name, int[] v) {
		data.put(name, v);
	}
	
	public int[] getIntegerArray(String name) {
		return (int[]) data.get(name);
	}
	
	public void setFloatArray(String name, float[] v) {
		data.put(name, v);
	}
	
	public float[] getFloatArray(String name) {
		return (float[]) data.get(name);
	}
	
	public void setShortArray(String name, short[] v) {
		data.put(name, v);
	}
	
	public short[] getShortArray(String name) {
		return (short[]) data.get(name);
	}
	
	public void setDoubleArray(String name, double[] v) {
		data.put(name, v);
	}
	
	public double[] getDoubleArray(String name) {
		return (double[]) data.get(name);
	}
	
	public void setLongArray(String name, long[] v) {
		data.put(name, v);
	}
	
	public long[] getLongArray(String name) {
		return (long[]) data.get(name);
	}
	
	public void setCharacterArray(String name, char[] v) {
		data.put(name, v);
	}
	
	public char[] getCharacterArray(String name) {
		return (char[]) data.get(name);
	}
	
	public void setBooleanArray(String name, boolean[] v) {
		data.put(name, v);
	}
	
	public boolean[] getBooleanArray(String name) {
		return (boolean[]) data.get(name);
	}
	
	public void setString(String name, String v) {
		data.put(name, v);
	}
	
	public String getString(String name) {
		return (String) data.get(name);
	}
	
	public void setBatch(String name, NPTBatch v) {
		if (parent == v) throw new IllegalArgumentException("Cannot add parent as child!"); else v.parent = this;
		if (v == this) throw new IllegalArgumentException("Cannot add self!");
		data.put(name, v);
	}
	
	public NPTBatch getBatch(String name) {
		return (NPTBatch) data.get(name);
	}
	
}
