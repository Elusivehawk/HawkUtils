
package com.elusivehawk.util.storage;

import com.elusivehawk.util.IDirty;

/**
 * 
 * Stores an object, which if modified will set off the {@link #isDirty()} flag.
 * 
 * @author Elusivehawk
 */
public class DirtableStorage<T> implements IDirty, IStorage<T>
{
	protected T obj;
	protected boolean dirty = false, enableNull = true;
	
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
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
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
			this.obj = object;
			
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
	
}
