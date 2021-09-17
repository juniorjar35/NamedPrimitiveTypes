package juniorjar35.NPT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NPTBatch {
	
	public NPTBatch() {};
	
	protected Map<String, Object> data = new HashMap<String, Object>();
	
	private void readObj(Map<String, Object> out, DataInput in) throws IOException {
		String name = in.readUTF();
		byte sig = in.readByte();
		if (sig == 't') out.put(name, in.readInt());
		if (sig == 'e') out.put(name, in.readLong());
		if (sig == 's') out.put(name, in.readShort());
		if (sig == 'l') out.put(name, in.readFloat());
		if (sig == 'a') out.put(name, in.readDouble());
		if (sig == 'm') out.put(name, in.readBoolean());
		if (sig == '3') out.put(name, in.readChar());
		if (sig == 'T') {int c = in.readInt(); int[] a = new int[c]; for (int i = 0; i < c; i++) a[i] = in.readInt(); out.put(name, c);};
		if (sig == 'E') {int c = in.readInt(); long[] a = new long[c]; for (int i = 0; i < c; i++) a[i] = in.readLong(); out.put(name, c);};
		if (sig == 'S') {int c = in.readInt(); short[] a = new short[c]; for (int i = 0; i < c; i++) a[i] = in.readShort(); out.put(name, c);};
		if (sig == 'L') {int c = in.readInt(); float[] a = new float[c]; for (int i = 0; i < c; i++) a[i] = in.readFloat(); out.put(name, c);};
		if (sig == 'A') {int c = in.readInt(); double[] a = new double[c]; for (int i = 0; i < c; i++) a[i] = in.readDouble(); out.put(name, c);};
		if (sig == 'M') {int c = in.readInt(); boolean[] a = new boolean[c]; for (int i = 0; i < c; i++) a[i] = in.readBoolean(); out.put(name, c);};
		if (sig == 'X') {int c = in.readInt(); char[] a = new char[c]; for (int i = 0; i < c; i++) a[i] = in.readChar(); out.put(name, c);};
		if (sig == '#') out.put(name, in.readUTF());
	}
	
	public void read(DataInput in) throws IOException {
		int count = in.readInt();
		for (int i = 0; i < count; i++) {
			readObj(data, in);
		}
	}
	
	private void writeObj(String name, Object v, DataOutput out) throws IOException {
		if (v instanceof Integer) {out.writeUTF(name); out.writeByte('t'); out.writeInt((Integer)v);};
		if (v instanceof Long) {out.writeUTF(name); out.writeByte('e'); out.writeLong((Long)v);};
		if (v instanceof Short) {out.writeUTF(name); out.writeByte('s'); out.writeShort((Short)v);};
		if (v instanceof Float) {out.writeUTF(name); out.writeByte('l'); out.writeFloat((Float)v);};
		if (v instanceof Double) {out.writeUTF(name); out.writeByte('a'); out.writeDouble((Double)v);};
		if (v instanceof Boolean) {out.writeUTF(name); out.writeByte('m'); out.writeBoolean((Boolean)v);};
		if (v instanceof Character) {out.writeUTF(name); out.writeByte('3'); out.writeChar((Character)v);};
		if (v instanceof int[]) {out.writeUTF(name); out.writeByte('T'); int[] r = (int[]) v; out.writeInt(r.length); for (int w : r) out.writeInt(w);};
		if (v instanceof long[]) {out.writeUTF(name); out.writeByte('E'); long[] r = (long[]) v; out.writeInt(r.length); for (long w : r) out.writeLong(w);};
		if (v instanceof short[]) {out.writeUTF(name); out.writeByte('S'); short[] r = (short[]) v; out.writeInt(r.length); for (short w : r) out.writeShort(w);};
		if (v instanceof float[]) {out.writeUTF(name); out.writeByte('L'); float[] r = (float[]) v; out.writeInt(r.length); for (float w : r) out.writeFloat(w);};
		if (v instanceof double[]) {out.writeUTF(name); out.writeByte('A'); double[] r = (double[]) v; out.writeInt(r.length); for (double w : r) out.writeDouble(w);};
		if (v instanceof boolean[]) {out.writeUTF(name); out.writeByte('M'); boolean[] r = (boolean[]) v; out.writeInt(r.length); for (boolean w : r) out.writeBoolean(w);};
		if (v instanceof char[]) {out.writeUTF(name); out.writeByte('X'); char[] r = (char[]) v; out.writeInt(r.length); for (char w : r) out.writeChar(w);};
		if (v instanceof String) {out.writeUTF(name); out.writeByte('#'); out.writeUTF((String)v);}
		if (v instanceof NPTBatch) {
			out.writeUTF(name);
			out.write('!');
			NPTBatch o = (NPTBatch) v;
			out.writeInt(o.data.size());
			Set<Map.Entry<String, Object>> entries = o.data.entrySet();
			for (Map.Entry<String, Object> entry : entries) {
				writeObj(entry.getKey(), entry.getValue(), out);
			}
		};
	}
	
	public void write(DataOutput out) throws IOException {
		if (!data.isEmpty()) {
			out.writeInt(data.size());
			Set<Map.Entry<String, Object>> entries = data.entrySet();
			for (Map.Entry<String, Object> entry : entries) {
				writeObj(entry.getKey(), entry.getValue(), out);
			}
		}
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

	public void setLong(String name, short v) {
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
	
	
}
