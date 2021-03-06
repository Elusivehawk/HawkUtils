
package com.elusivehawk.util.task;

import java.io.File;
import java.util.List;
import com.elusivehawk.util.io.IOHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskReadFile extends Task
{
	private final File txt;
	private List<String> fin = null;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskReadFile(File file, ITaskListener tlis)
	{
		super(tlis);
		
		txt = file;
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		this.fin = IOHelper.readText(this.txt);
		
		return true;
	}
	
	public List<String> getText()
	{
		return this.fin;
	}
	
}
