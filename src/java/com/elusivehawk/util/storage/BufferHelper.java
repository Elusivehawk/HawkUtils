
package com.elusivehawk.util.storage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Collection;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.io.Serializers;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * Helper class for creating NIO buffers.
 * <p>
 * If you just need something that emulates a buffer without the finite size, consider {@link Buffer} instead.
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("boxing")
public final class BufferHelper
{
	private BufferHelper(){}
	
	public static ByteBuffer createByteBuffer(int size)
	{
		if (size <= 0)
		{
			throw new RuntimeException(String.format("Cannot make a ByteBuffer with a size of %s", size));
		}
		
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}
	
	public static CharBuffer createCharBuffer(int size)
	{
		return createByteBuffer(size << 1).asCharBuffer();
	}
	
	public static DoubleBuffer createDoubleBuffer(int size)
	{
		return createByteBuffer(size << 3).asDoubleBuffer();
	}
	
	public static FloatBuffer createFloatBuffer(int size)
	{
		return createByteBuffer(size << 2).asFloatBuffer();
	}
	
	public static IntBuffer createIntBuffer(int size)
	{
		return createByteBuffer(size << 2).asIntBuffer();
	}
	
	public static LongBuffer createLongBuffer(int size)
	{
		return createByteBuffer(size << 3).asLongBuffer();
	}
	
	public static ShortBuffer createShortBuffer(int size)
	{
		return createByteBuffer(size << 1).asShortBuffer();
	}
	
	public static ByteBuffer createWrapper(byte[] bs)
	{
		return ByteBuffer.wrap(bs);
	}
	
	public static CharBuffer createWrapper(char[] bs)
	{
		return CharBuffer.wrap(bs);
	}
	
	public static DoubleBuffer createWrapper(double[] bs)
	{
		return DoubleBuffer.wrap(bs);
	}
	
	public static FloatBuffer createWrapper(float[] bs)
	{
		return FloatBuffer.wrap(bs);
	}
	
	public static IntBuffer createWrapper(int[] bs)
	{
		return IntBuffer.wrap(bs);
	}
	
	public static LongBuffer createWrapper(long[] bs)
	{
		return LongBuffer.wrap(bs);
	}
	
	public static ShortBuffer createWrapper(short[] bs)
	{
		return ShortBuffer.wrap(bs);
	}
	
	public static ByteBuffer expand(ByteBuffer buf, int count)
	{
		return (ByteBuffer)createByteBuffer(buf.capacity() + count).put(((ByteBuffer)buf.rewind())).rewind();
	}
	
	public static CharBuffer expand(CharBuffer buf, int count)
	{
		return (CharBuffer)createCharBuffer(buf.capacity() + count).put((CharBuffer)buf.rewind()).rewind();
	}
	
	public static DoubleBuffer expand(DoubleBuffer buf, int count)
	{
		return (DoubleBuffer)createDoubleBuffer(buf.capacity() + count).put((DoubleBuffer)buf.rewind()).rewind();
	}
	
	public static FloatBuffer expand(FloatBuffer buf, int count)
	{
		return (FloatBuffer)createFloatBuffer(buf.capacity() + count).put((FloatBuffer)buf.rewind()).rewind();
	}
	
	public static IntBuffer expand(IntBuffer buf, int count)
	{
		return (IntBuffer)createIntBuffer(buf.capacity() + count).put((IntBuffer)buf.rewind()).rewind();
	}
	
	public static LongBuffer expand(LongBuffer buf, int count)
	{
		return (LongBuffer)createLongBuffer(buf.capacity() + count).put((LongBuffer)buf.rewind()).rewind();
	}
	
	public static ShortBuffer expand(ShortBuffer buf, int count)
	{
		return (ShortBuffer)createShortBuffer(buf.capacity() + count).put((ShortBuffer)buf.rewind()).rewind();
	}
	
	public static ByteBuffer makeByteBuffer(byte... data)
	{
		return (ByteBuffer)createByteBuffer(data.length).put(data).flip();
	}
	
	public static ByteBuffer makeByteBuffer(Collection<Byte> data)
	{
		ByteBuffer ret = createByteBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(Number... data)
	{
		ByteBuffer ret = createByteBuffer(data.length);
		
		for (Number n : data)
		{
			ret.put(n.byteValue());
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(ByteBuffer buf, int offset, int count)
	{
		ByteBuffer ret = createByteBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(IByteReader r)
	{
		return makeByteBuffer(r.remaining(), r);
	}
	
	public static ByteBuffer makeByteBuffer(int length, IByteReader r)
	{
		ByteBuffer ret = createByteBuffer(length);
		
		try
		{
			for (int c = 0; c < length; c++)
			{
				ret.put(r.read());
				
			}
			
			ret.flip();
			
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(ByteBuffer... bufs)
	{
		int length = 0;
		
		for (ByteBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		ByteBuffer ret = createByteBuffer(length);
		
		for (ByteBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(char... data)
	{
		return (CharBuffer)createCharBuffer(data.length).put(data).flip();
	}
	
	public static CharBuffer makeCharBuffer(Collection<Character> data)
	{
		CharBuffer ret = createCharBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(CharBuffer buf, int offset, int count)
	{
		CharBuffer ret = createCharBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(CharBuffer... bufs)
	{
		int length = 0;
		
		for (CharBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		CharBuffer ret = createCharBuffer(length);
		
		for (CharBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(double... data)
	{
		return (DoubleBuffer)createDoubleBuffer(data.length).put(data).flip();
	}
	
	public static DoubleBuffer makeDoubleBuffer(Collection<Double> data)
	{
		DoubleBuffer ret = createDoubleBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(Number... data)
	{
		DoubleBuffer ret = createDoubleBuffer(data.length);
		
		for (Number n : data)
		{
			ret.put(n.doubleValue());
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(DoubleBuffer buf, int offset, int count)
	{
		DoubleBuffer ret = createDoubleBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(DoubleBuffer... bufs)
	{
		int length = 0;
		
		for (DoubleBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		DoubleBuffer ret = createDoubleBuffer(length);
		
		for (DoubleBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(float... data)
	{
		return (FloatBuffer)createFloatBuffer(data.length).put(data).flip();
	}
	
	public static FloatBuffer makeFloatBuffer(Collection<Float> data)
	{
		FloatBuffer ret = createFloatBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(Number... data)
	{
		FloatBuffer ret = createFloatBuffer(data.length);
		
		for (Number n : data)
		{
			ret.put(n.floatValue());
			
		}
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(FloatBuffer buf, int offset, int count)
	{
		FloatBuffer ret = createFloatBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(FloatBuffer... bufs)
	{
		int length = 0;
		
		for (FloatBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		FloatBuffer ret = createFloatBuffer(length);
		
		for (FloatBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(int... data)
	{
		return (IntBuffer)createIntBuffer(data.length).put(data).flip();
	}
	
	public static IntBuffer makeIntBuffer(Collection<Integer> data)
	{
		IntBuffer ret = createIntBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(Number... data)
	{
		IntBuffer ret = createIntBuffer(data.length);
		
		for (Number n : data)
		{
			ret.put(n.intValue());
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(IntBuffer buf, int offset, int count)
	{
		IntBuffer ret = createIntBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(IntBuffer... bufs)
	{
		int length = 0;
		
		for (IntBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		IntBuffer ret = createIntBuffer(length);
		
		for (IntBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(long... data)
	{
		return (LongBuffer)createLongBuffer(data.length).put(data).flip();
	}
	
	public static LongBuffer makeLongBuffer(Collection<Long> data)
	{
		LongBuffer ret = createLongBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(Number... data)
	{
		LongBuffer ret = createLongBuffer(data.length);
		
		for (Number n : data)
		{
			ret.put(n.longValue());
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(LongBuffer buf, int offset, int count)
	{
		LongBuffer ret = createLongBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(LongBuffer... bufs)
	{
		int length = 0;
		
		for (LongBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		LongBuffer ret = createLongBuffer(length);
		
		for (LongBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(short... data)
	{
		return (ShortBuffer)createShortBuffer(data.length).put(data).flip();
	}
	
	public static ShortBuffer makeShortBuffer(Collection<Short> data)
	{
		ShortBuffer ret = createShortBuffer(data.size());
		
		data.forEach(ret::put);
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(Number... data)
	{
		ShortBuffer ret = createShortBuffer(data.length);
		
		for (Number n : data)
		{
			ret.put(n.shortValue());
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(ShortBuffer buf, int offset, int count)
	{
		ShortBuffer ret = createShortBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(ShortBuffer... bufs)
	{
		int length = 0;
		
		for (ShortBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		ShortBuffer ret = createShortBuffer(length);
		
		for (ShortBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeVecFloatBufferf(Collection<VectorF> data)
	{
		int size = 0;
		
		for (VectorF vec : data)
		{
			size += vec.size();
			
		}
		
		FloatBuffer ret = createFloatBuffer(size);
		
		data.forEach(((vec) ->
		{
			for (int c = 0; c < vec.size(); c++)
			{
				ret.put(vec.get(c));
				
			}
			
		}));
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeVecFloatBufferf(VectorF... data)
	{
		int size = 0;
		
		for (VectorF vec : data)
		{
			size += vec.size();
			
		}
		
		FloatBuffer ret = createFloatBuffer(size);
		
		for (VectorF vec : data)
		{
			for (int c = 0; c < vec.size(); c++)
			{
				ret.put(vec.get(c));
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer toByteBuffer(DoubleBuffer buf)
	{
		ByteBuffer ret = createByteBuffer(buf.remaining() * 8);
		
		for (int c = buf.position(); c < buf.capacity(); c++)
		{
			Serializers.DOUBLE.toBytes(buf.get(c), ((bs) ->
			{
				ret.put(bs);
				
				return 8;
			}));
			
		}
		
		return ret;
	}
	
	public static ByteBuffer toByteBuffer(FloatBuffer buf)
	{
		ByteBuffer ret = createByteBuffer(buf.remaining() * 4);
		
		for (int c = buf.position(); c < buf.capacity(); c++)
		{
			Serializers.FLOAT.toBytes(buf.get(c), ((bs) ->
			{
				ret.put(bs);
				
				return 4;
			}));
			
		}
		
		return ret;
	}
	
	public static ByteBuffer toByteBuffer(IntBuffer buf)
	{
		ByteBuffer ret = createByteBuffer(buf.remaining() * 4);
		
		for (int c = buf.position(); c < buf.capacity(); c++)
		{
			Serializers.INTEGER.toBytes(buf.get(c), ((bs) ->
			{
				ret.put(bs);
				
				return 4;
			}));
			
		}
		
		return ret;
	}
	
	public static ByteBuffer toByteBuffer(LongBuffer buf)
	{
		ByteBuffer ret = createByteBuffer(buf.remaining() * 8);
		
		for (int c = buf.position(); c < buf.capacity(); c++)
		{
			Serializers.LONG.toBytes(buf.get(c), ((bs) ->
			{
				ret.put(bs);
				
				return 4;
			}));
			
		}
		
		return ret;
	}
	
	public static ByteBuffer toByteBuffer(ShortBuffer buf)
	{
		ByteBuffer ret = createByteBuffer(buf.remaining() * 2);
		
		for (int c = buf.position(); c < buf.capacity(); c++)
		{
			Serializers.SHORT.toBytes(buf.get(c), ((bs) ->
			{
				ret.put(bs);
				
				return 4;
			}));
			
		}
		
		return ret;
	}
	
}
