package juniorjar35.NPT;

import static juniorjar35.NPT.NamedPrimitiveTypes.readString;
import static juniorjar35.NPT.NamedPrimitiveTypes.writeString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NPTBatch {
	
	public NPTBatch() {};
	
	public static final short NPT_SAVE_VERSION = 0x0103;
	private NPTBatch parent = null;
	protected Map<String, Object> data = new HashMap<String, Object>();
	
	/*
	 
	 Data Signatures
	 
	 B = Byte
	 I = Integer
	 L = Long
	 S = Short
	 F = Float
	 D = Double
	 Z = Boolean
	 C = Character
	 
	 i = Integer array
	 l = Long array
	 s = Short array
	 f = Float array
	 d = Double array
	 z = Boolean array
	 c = Character array
	 b = Byte array
	 
	 X = UTF String
	 J = NPTBatch
	 
	 */
	
	private void readObj(Map<String, Object> out, DataInput in) throws IOException {
		String name = readString(in);
		byte sig = in.readByte();
		if (sig == 'B') {
			out.put(name, in.readByte());
			return;
		}
		if (sig == 'I') {
			out.put(name, in.readInt());
			return;
		}
		if (sig == 'L') {
			out.put(name, in.readLong());
			return;
		}
		if (sig == 'S') {
			out.put(name, in.readShort());
			return;
		}
		if (sig == 'F') {
			out.put(name, in.readFloat());
			return;
		}
		if (sig == 'D') {
			out.put(name, in.readDouble());
			return;
		}
		if (sig == 'Z') {
			out.put(name, in.readBoolean());
			return;
		}
		if (sig == 'C') {
			out.put(name, in.readChar());
			return;
		}
		if (sig == 'i') {
			int c = in.readInt();
			int[] a = new int[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readInt();
			}; 
			out.put(name, a);
			return;
		};
		if (sig == 'l') {
			int c = in.readInt();
			long[] a = new long[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readLong();
			}; 
			out.put(name, a);
			return;
		};
		if (sig == 's') {
			int c = in.readInt();
			short[] a = new short[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readShort();
			};
			out.put(name, a);
			return;
		};
		if (sig == 'f') {
			int c = in.readInt();
			float[] a = new float[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readFloat();
			};
			out.put(name, a);
			return;
		};
		if (sig == 'd') {
			int c = in.readInt();
			double[] a = new double[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readDouble();
			};
			out.put(name, a);
			return;
		};
		if (sig == 'z') {
			int c = in.readInt();
			boolean[] a = new boolean[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readBoolean();
			};
			out.put(name, a);
			return;
		};
		if (sig == 'c') {
			int c = in.readInt();
			char[] a = new char[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readChar();
			}; 
			out.put(name, a);
			return;
		};
		if (sig == 'b') {
			int c = in.readInt();
			byte[] a = new byte[c];
			for (int i = 0; i < c; i++) {
				a[i] = in.readByte();
			}; 
			out.put(name, a);
			return;
		};
		if (sig == 'X') {
			out.put(name, in.readUTF());
			return;
		}
		if (sig == 'J') {
			NPTBatch b = new NPTBatch();
			int c = in.readInt();
			for (int i = 0; i < c; i++) {
				readObj(b.data, in);
			}
			out.put(name, b);
			return;
		}
	}
	
	public void read(DataInput in) throws IOException {
		read(in,false);
	}
	
	public void read(DataInput in, boolean ignoreVersionCheck) throws IOException {
		short version = in.readShort();
		if (version != NPT_SAVE_VERSION && !ignoreVersionCheck) throw new IOException("Expected version: " + Integer.toHexString(NPT_SAVE_VERSION) + "|| Got: " + Integer.toHexString(version));
		int count = in.readInt();
		for (int i = 0; i < count; i++) {
			readObj(data, in);
		}
	}
	
	private void writeObj(String name, Object v, DataOutput out) throws IOException {
		if (v == null) return;
		writeString(name, out);
		if (v instanceof Byte) {out.writeByte('B'); out.writeInt((Byte)v); return;};
		if (v instanceof Integer) {out.writeByte('I'); out.writeInt((Integer)v); return;};
		if (v instanceof Long) {out.writeByte('L'); out.writeLong((Long)v); return;};
		if (v instanceof Short) {out.writeByte('S'); out.writeShort((Short)v); return;};
		if (v instanceof Float) {out.writeByte('F'); out.writeFloat((Float)v); return;};
		if (v instanceof Double) {out.writeByte('D'); out.writeDouble((Double)v); return;};
		if (v instanceof Boolean) {out.writeByte('Z'); out.writeBoolean((Boolean)v); return;};
		if (v instanceof Character) {out.writeByte('C'); out.writeChar((Character)v); return;};
		if (v instanceof int[]) {out.writeByte('i'); int[] r = (int[]) v; out.writeInt(r.length); for (int w : r) out.writeInt(w); return;};
		if (v instanceof long[]) {out.writeByte('l'); long[] r = (long[]) v; out.writeInt(r.length); for (long w : r) out.writeLong(w); return;};
		if (v instanceof short[]) {out.writeByte('s'); short[] r = (short[]) v; out.writeInt(r.length); for (short w : r) out.writeShort(w); return;};
		if (v instanceof float[]) {out.writeByte('f'); float[] r = (float[]) v; out.writeInt(r.length); for (float w : r) out.writeFloat(w); return;};
		if (v instanceof double[]) {out.writeByte('d'); double[] r = (double[]) v; out.writeInt(r.length); for (double w : r) out.writeDouble(w); return;};
		if (v instanceof boolean[]) {out.writeByte('z'); boolean[] r = (boolean[]) v; out.writeInt(r.length); for (boolean w : r) out.writeBoolean(w); return;};
		if (v instanceof char[]) {out.writeByte('c'); char[] r = (char[]) v; out.writeInt(r.length); for (char w : r) out.writeChar(w); return;}
		if (v instanceof byte[]) {out.writeByte('b'); byte[] r = (byte[]) v; out.writeInt(r.length); for (byte w : r) out.writeByte(w); return;}
		if (v instanceof String) {out.writeByte('X'); out.writeUTF((String)v); return;}
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
		out.writeShort(NPT_SAVE_VERSION);
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
	
	public String[] getAllKeys() {
		return data.keySet().toArray(new String[0]);
	}
	
	public Set<Map.Entry<String, Object>> getAllEntries(){
		return Collections.unmodifiableSet(data.entrySet());
	}
	
	private Object get(String name) {
		return data.get(name);
	}
	
	public Class<?> getKeyType(String name){
		return get(name).getClass();
	}
	
	public boolean isNull(String name) {
		return get(name) == null;
	}
	
	public int getSize() {
		return data.size();
	}
	
	public void setShort(String name, short v) {
		data.put(name, v);
	}
	
	public short getShort(String name) {
		return this.getShort(name, (short) 0);
	}
	
	public short getShort(String name, short defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Short) return (short) v; else return defaultValue;
	}
	
	public void setInteger(String name, int v) {
		data.put(name, v);
	}
	
	public int getInteger(String name) {
		return this.getInteger(name, 0);
	}
	
	public int getInteger(String name, int defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Integer) return (int) v; else return defaultValue;
	}
	
	public void setLong(String name, long v) {
		data.put(name, v);
	}
	
	public long getLong(String name) {
		return this.getLong(name, 0L);
	}
	
	public long getLong(String name, long defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Long) return (long) v; else return defaultValue;
	}
	
	public void setFloat(String name, float v) {
		data.put(name, v);
	}
	
	public float getFloat(String name) {
		return this.getFloat(name, 0.0f);
	}
	
	public float getFloat(String name, float defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Float) return (float) v; else return defaultValue;
	}
	
	public void setDouble(String name, double v) {
		data.put(name, v);
	}
	
	public double getDouble(String name) {
		return this.getDouble(name, 0.0D);
	}
	
	public double getDouble(String name, double defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Double) return (double) v; else return defaultValue;
	}
	
	public void setBoolean(String name, boolean v) {
		data.put(name, v);
	}
	
	public boolean getBoolean(String name) {
		return this.getBoolean(name, false);
	}
	
	public boolean getBoolean(String name, boolean defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Boolean) return (boolean) v; else return defaultValue;
	}
	
	public void setCharacter(String name, char v) {
		data.put(name, v);
	}
	
	public char getCharacter(String name) {
		return this.getCharacter(name, (char) 0);
	}
	
	public char getCharacter(String name, char defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Character) return (char) v; else return defaultValue;
	}
	
	public void setByte(String name, byte v) {
		data.put(name, v);
	}
	
	public byte getByte(String name) {
		return this.getByte(name, (byte) 0);
	}
	
	public byte getByte(String name, byte defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof Byte) return (byte) v; else return defaultValue;
	}
	
	public void setByteArray(String name, byte[] array) {
		data.put(name, array);
	}
	
	public byte[] getByteArray(String name) {
		return this.getByteArray(name, null);
	}
	
	public byte[] getByteArray(String name, byte[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof byte[]) return (byte[]) v; else return defaultValue;
	}
	
	public void setIntegerArray(String name, int[] v) {
		data.put(name, v);
	}
	
	public int[] getIntegerArray(String name) {
		return this.getIntegerArray(name, null);
	}
	
	public int[] getIntegerArray(String name, int[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof int[]) return (int[]) v; else return defaultValue;
	}
	
	public void setFloatArray(String name, float[] v) {
		data.put(name, v);
	}
	
	public float[] getFloatArray(String name) {
		return this.getFloatArray(name, null);
	}
	
	public float[] getFloatArray(String name, float[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof float[]) return (float[]) v; else return defaultValue;
	}
	
	public void setShortArray(String name, short[] v) {
		data.put(name, v);
	}
	
	public short[] getShortArray(String name) {
		return this.getShortArray(name, null);
	}
	
	public short[] getShortArray(String name, short[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof short[]) return (short[]) v; else return defaultValue;
	}
	
	public void setDoubleArray(String name, double[] v) {
		data.put(name, v);
	}
	
	public double[] getDoubleArray(String name) {
		return this.getDoubleArray(name, null);
	}
	
	public double[] getDoubleArray(String name, double[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof double[]) return (double[]) v; else return defaultValue;
	}
	
	public void setLongArray(String name, long[] v) {
		data.put(name, v);
	}
	
	public long[] getLongArray(String name) {
		return this.getLongArray(name, null);
	}
	
	public long[] getLongArray(String name, long[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof long[]) return (long[]) v; else return defaultValue;
	}
	
	public void setCharacterArray(String name, char[] v) {
		data.put(name, v);
	}
	
	public char[] getCharacterArray(String name) {
		return this.getCharacterArray(name, null);
	}
	
	public char[] getCharacterArray(String name, char[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof char[]) return (char[]) v; else return defaultValue;
	}
	
	public void setBooleanArray(String name, boolean[] v) {
		data.put(name, v);
	}
	
	public boolean[] getBooleanArray(String name) {
		return this.getBooleanArray(name, null);
	}
	
	public boolean[] getBooleanArray(String name, boolean[] defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof boolean[]) return (boolean[]) v; else return defaultValue;
	}
	
	public void setString(String name, String v) {
		data.put(name, v);
	}
	
	public String getString(String name) {
		return this.getString(name, null);
	}
	
	public String getString(String name, String defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof String) return (String) v; else return defaultValue;
	}
	
	public void setBatch(String name, NPTBatch v) {
		if (parent == v) throw new IllegalArgumentException("Cannot add parent as child!"); else v.parent = this;
		if (v == this) throw new IllegalArgumentException("Cannot add self!");
		data.put(name, v);
	}
	
	public NPTBatch getBatch(String name) {
		return this.getBatch(name, null);
	}
	
	public NPTBatch getBatch(String name, NPTBatch defaultValue) {
		Object v = get(name);
		if (v != null && v instanceof NPTBatch) return (NPTBatch) v; else return defaultValue;
	}
	
}
