
package com.elusivehawk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.elusivehawk.util.string.StringHelper;

/**
 * 
 * Helper class for handling {@link File}s.
 * 
 * @author Elusivehawk
 */
public final class FileHelper
{
	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final FilenameFilter NATIVE_NAME_FILTER = ((file, name) ->
	{
		String ext = StringHelper.getSuffix(name, ".");
		
		if (ext == null)
		{
			return false;
		}
		
		ext = ext.toLowerCase();
		
		switch (ext)
		{
			case "dll":
			case "so":
			case "jnilib":
			case "dylib": return true;
			default: return false;
		}
		
	});
	public static final FileFilter NATIVE_FILTER = new FilenameFilterWrapper(NATIVE_NAME_FILTER);
	
	private FileHelper(){}
	
	public static File createFile(String path)
	{
		return new File(fixPath(path));
	}
	
	public static File createFile(String src, String path)
	{
		return new File(fixPath(src), fixPath(path));
	}
	
	public static File createFile(File src, String path)
	{
		return new File(src, fixPath(path));
	}
	
	public static File getRootResDir()
	{
		try
		{
			String urlpath = FileHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			
			if (urlpath != null)
			{
				return new File(urlpath);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static File getResource(String path)
	{
		File root = getRootResDir();
		
		if (root != null)
		{
			return new File(root, path);
		}
		
		return null;
	}
	
	public static InputStream getResourceStream(String path)
	{
		if (CompInfo.BUILT)
		{
			return FileHelper.class.getResourceAsStream(path);
		}
		
		File file = createFile(CompInfo.JAR_DIR.getParentFile(), path);
		
		return createInStream(file);
	}
	
	public static FileInputStream createInStream(File file)
	{
		if (!canRead(file))
		{
			return null;
		}
		
		FileInputStream ret = null;
		
		try
		{
			ret = new FileInputStream(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileReader createReader(File file)
	{
		if (!canRead(file))
		{
			return null;
		}
		
		FileReader ret = null;
		
		try
		{
			ret = new FileReader(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileOutputStream createOutStream(File file, boolean create)
	{
		if (file == null)
		{
			return null;
		}
		
		if (!file.exists() && create)
		{
			try
			{
				if (!file.createNewFile())
				{
					return null;
				}
				
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
		
		if (!file.isFile())
		{
			return null;
		}
		
		if (!file.canWrite())
		{
			return null;
		}
		
		FileOutputStream ret = null;
		
		try
		{
			ret = new FileOutputStream(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileWriter createWriter(File file, boolean create)
	{
		return createWriter(file, create, false);
	}
	
	public static FileWriter createWriter(File file, boolean create, boolean append)
	{
		if (file == null)
		{
			return null;
		}
		
		if (!file.isFile())
		{
			return null;
		}
		
		if (!file.exists() && create)
		{
			try
			{
				if (!file.createNewFile())
				{
					return null;
				}
				
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
		
		if (!file.canWrite())
		{
			return null;
		}
		
		FileWriter ret = null;
		
		try
		{
			ret = new FileWriter(file, append);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static boolean isReal(File file)
	{
		return file != null && file.exists();
	}
	
	public static boolean canRead(File file)
	{
		return isReal(file) && file.isFile() && file.canRead();
	}
	
	public static String fixPath(String path)
	{
		return path.replace("/", FILE_SEP);
	}
	
	public static String makePathGeneric(String path)
	{
		return path.replace(FILE_SEP, "/");
	}
	
	public static List<File> getFiles(File file)
	{
		return getFiles(file, null);
	}
	
	public static List<File> getFiles(File file, FileFilter filter)
	{
		List<File> ret = new ArrayList<File>();
		
		scanForFiles(file, ((f) ->
		{
			if (filter.accept(f))
			{
				ret.add(f);
				
			}
			
			return true;
		}));
		
		return ret;
	}
	
	public static String getExtensionlessName(File file)
	{
		return StringHelper.getPrefix(file.getName(), ".");
	}
	
	public static String getExtension(File file)
	{
		String ret = StringHelper.getSuffix(file.getName(), ".");
		
		if (ret == null)
		{
			return null;
		}
		
		return ret.toLowerCase();
	}
	
	public static File getChild(String name, File folder)
	{
		File[] children = folder.listFiles();
		
		if (children == null || children.length == 0)
		{
			return null;
		}
		
		for (File file : children)
		{
			if (file.getName().equals(name))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
	public static File getChild(String name, List<File> files)
	{
		if (files == null || files.isEmpty())
		{
			return null;
		}
		
		for (File file : files)
		{
			if (file.getPath().endsWith(name))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
	public static void scanForFiles(File file, IFileScanner sc)
	{
		assert isReal(file);
		
		File[] files = file.listFiles();
		
		if (files == null || files.length == 0)
		{
			return;
		}
		
		for (File f : files)
		{
			if (f.isDirectory())
			{
				scanForFiles(f, sc);
				
			}
			
			if (!sc.scan(f))
			{
				return;
			}
			
		}
		
	}
	
	public static void readZip(File file, IZipScanner sc)
	{
		if (!canRead(file))
		{
			return;
		}
		
		if (file.isDirectory())
		{
			return;
		}
		
		String ext = getExtension(file);
		
		ZipFile zip = null;
		
		try
		{
			if ("zip".equals(ext))
			{
				zip = new ZipFile(file);
				
			}
			else if ("jar".equals(ext))
			{
				zip = new JarFile(file);
				
			}
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
		}
		
		if (zip != null)
		{
			Enumeration<? extends ZipEntry> entries = zip.entries();
			ZipEntry entry;
			
			while (entries.hasMoreElements())
			{
				entry = entries.nextElement();
				
				if (entry.isDirectory())
				{
					continue;
				}
				
				sc.take(zip, entry, entry.getName());
				
			}
			
			try
			{
				zip.close();
				
			}
			catch (Exception e)
			{
				Logger.log().err(e);
				
			}
			
		}
		
	}
	
	public static byte[] readBytes(File file)
	{
		return readBytes(createInStream(file), true);
	}
	
	public static byte[] readBytes(InputStream is)
	{
		return readBytes(is, true);
	}
	
	public static byte[] readBytes(InputStream is, boolean close)
	{
		if (is == null)
		{
			return new byte[0];
		}
		
		BufferedInputStream in = (is instanceof BufferedInputStream) ? (BufferedInputStream)is : new BufferedInputStream(is);
		byte[] ret = null;
		int off = 0;
		int i = 0;
		
		try
		{
			ret = new byte[in.available()];
			
			while ((i = in.read(ret, off, Math.min(1024, ret.length - off))) > 0)
			{
				off += i;
				
			}
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
		}
		finally
		{
			if (close)
			{
				try
				{
					in.close();
					
				}
				catch (Exception e)
				{
					Logger.log().err(e);
					
				}
				
			}
			
		}
		
		if (ret == null)
		{
			return new byte[0];
		}
		
		return ret;
	}
	
	public static boolean write(byte[] bytes, File dest)
	{
		FileOutputStream fos = createOutStream(dest, true);
		
		if (fos == null)
		{
			return false;
		}
		
		BufferedOutputStream out = new BufferedOutputStream(fos);
		
		try
		{
			out.write(bytes);
			out.flush();
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
			return false;
		}
		
		return true;
	}
	
	public static class FilenameFilterWrapper implements FileFilter
	{
		private final FilenameFilter filter;
		
		@SuppressWarnings("unqualified-field-access")
		public FilenameFilterWrapper(FilenameFilter f)
		{
			filter = f;
			
		}
		
		@Override
		public boolean accept(File file)
		{
			return this.filter.accept(file, file.getName());
		}
		
	}
	
	@FunctionalInterface
	public static interface IFileScanner
	{
		boolean scan(File file);
		
	}
	
	public static interface IZipScanner
	{
		void take(ZipFile zip, ZipEntry entry, String name);
		
	}
	
}
