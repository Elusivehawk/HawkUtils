
package com.elusivehawk.util.storage;

import com.elusivehawk.util.Dirtable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DirtableStorage<T> extends Dirtable implements IGettable<T>, ISettable<T>
{
	private T obj;
	private boolean sync = false, enableNull = true;
	
	public DirtableStorage()
	{
		this(null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public DirtableStorage(T object)
	{
		obj = object;
		
	}
	
	@Override
	public T get()
	{
		return this.obj;
	}
	
	@Override
	public boolean set(T object)
	{
		if (object == null && !this.enableNull)
		{
			return false;
		}
		
		if (object == null ? this.obj != object : !object.equals(this.obj))
		{
			if (this.sync)
			{
				synchronized (this)
				{
					this.obj = object;
					
				}
				
			}
			else
			{
				this.obj = object;
				
			}
			
			this.setIsDirty(true);
			
			return true;
		}
		
		return false;
	}
	
	public DirtableStorage<T> setEnableNull(boolean b)
	{
		this.enableNull = b;
		
		return this;
	}
	
	public DirtableStorage<T> setSync()
	{
		this.sync = true;
		
		return this;
	}
	
}
