
package com.elusivehawk.util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.zip.CRC32;

/**
 * 
 * Enables for easy hashing.
 * 
 * @author Elusivehawk
 */
public final class HashGen
{
	private static final HashGen INSTANCE = new HashGen();
	
	private final MessageDigest
				md5 = newDigest("MD5"),
				sha1 = newDigest("SHA-1"),
				sha256 = newDigest("SHA-256");
	private final CRC32 crc32 = new CRC32();
	
	private HashGen(){}
	
	private static MessageDigest newDigest(String name)
	{
		MessageDigest ret = null;
		
		try
		{
			ret = MessageDigest.getInstance(name);
			
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		return ret;
	}
	
	public static synchronized long crc32(byte[]... in)
	{
		CRC32 crc = INSTANCE.crc32;
		
		for (byte[] bs : in)
		{
			crc.update(bs);
			
		}
		
		long ret = crc.getValue();
		
		crc.reset();
		
		return ret;
	}
	
	public static synchronized long crc32(ByteBuffer... in)
	{
		CRC32 crc = INSTANCE.crc32;
		
		for (ByteBuffer bb : in)
		{
			crc.update(bb);
			
		}
		
		long ret = crc.getValue();
		
		crc.reset();
		
		return ret;
	}
	
	public static byte[] md5(byte[]... in)
	{
		return hash(INSTANCE.md5, in);
	}
	
	public static byte[] sha1(byte[]... in)
	{
		return hash(INSTANCE.sha1, in);
	}
	
	public static byte[] sha256(byte[]... in)
	{
		return hash(INSTANCE.sha256, in);
	}
	
	public static synchronized byte[] hash(MessageDigest md, byte[]... in)
	{
		if (md == null)
		{
			return new byte[0];
		}
		
		for (byte[] bs : in)
		{
			md.update(bs);
			
		}
		
		return md.digest();
	}
	
}
