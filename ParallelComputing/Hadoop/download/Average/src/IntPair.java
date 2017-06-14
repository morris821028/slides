import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class IntPair implements Writable {
	private int first = 0;
	private int second = 0;
	
	public IntPair() {
	}
	
	public IntPair(int first, int second) {
		this.set(first, second);
	}
	
	public void set(int first, int second) {
		this.first = first;
		this.second = second;
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getSecond() {
		return second;
	}
	/**
	 * Read the two integers
	 */
	@Override
	public void readFields(DataInput in) throws IOException {
		first = in.readInt();
		second = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(first);
		out.writeInt(second);
	}
	
	@Override
	public String toString() {
		return first + "\t" + second;
	}	
}

