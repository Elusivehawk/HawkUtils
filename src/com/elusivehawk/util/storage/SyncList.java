
package com.elusivehawk.util.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * 
 * A list created specifically for multithreaded programming.
 * <p>
 * Take note that:
 * <br>This only synchronizes the {@link List} it's been given; It does *NOT* act as a second buffer.
 * <br>You can iterate through this list at any point in time; The iterator is specifically built for it.
 * <br>In fact, iterating through this is safer, since it prevents {@link ConcurrentModificationException}s from cropping up, thanks to synchronization.
 * <br>If you don't have a {@link List}, it will use a new {@link ArrayList}.
 * <br>Unlike {@link Vector}, only the methods that modify the backed {@link List} are synchronized.
 * 
 * @author Elusivehawk
 */
public class SyncList<T> implements List<T>
{
	protected final List<T> l;
	
	public SyncList()
	{
		this(16);
		
	}
	
	public SyncList(int limit)
	{
		this(new ArrayList<T>(limit));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SyncList(List<T> list)
	{
		l = list;
		
	}
	
	@Override
	public synchronized boolean add(T arg0)
	{
		return this.l.add(arg0);
	}
	
	@Override
	public synchronized void add(int arg0, T arg1)
	{
		this.l.add(arg0, arg1);
		
	}
	
	@Override
	public synchronized boolean addAll(Collection<? extends T> arg0)
	{
		return this.l.addAll(arg0);
	}
	
	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1)
	{
		return this.l.addAll(arg0, arg1);
	}
	
	@Override
	public synchronized void clear()
	{
		this.l.clear();
		
	}
	
	@Override
	public boolean contains(Object arg0)
	{
		return this.l.contains(arg0);
	}
	
	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return this.l.containsAll(arg0);
	}
	
	@Override
	public T get(int arg0)
	{
		return this.l.get(arg0);
	}
	
	@Override
	public int indexOf(Object arg0)
	{
		return this.l.indexOf(arg0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return this.l.isEmpty();
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return this.listIterator();
	}
	
	@Override
	public int lastIndexOf(Object arg0)
	{
		return this.l.lastIndexOf(arg0);
	}
	
	@Override
	public ListIterator<T> listIterator()
	{
		return new SyncListItr<T>(this);
	}
	
	@Override
	public ListIterator<T> listIterator(int arg0)
	{
		return new SyncListItr<T>(this, arg0);
	}
	
	@Override
	public synchronized boolean remove(Object arg0)
	{
		return this.l.remove(arg0);
	}
	
	@Override
	public synchronized T remove(int arg0)
	{
		return this.l.remove(arg0);
	}
	
	@Override
	public synchronized boolean removeAll(Collection<?> arg0)
	{
		return this.l.removeAll(arg0);
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		return this.l.retainAll(arg0);
	}
	
	@Override
	public synchronized T set(int arg0, T arg1)
	{
		return this.l.set(arg0, arg1);
	}
	
	@Override
	public int size()
	{
		return this.l.size();
	}
	
	@Override
	public List<T> subList(int arg0, int arg1)
	{
		return this.l.subList(arg0, arg1);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray()
	{
		return (T[])this.l.toArray();
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return this.l.toArray(arg0);
	}
	
	public static <T> SyncList<T> newList()
	{
		return new SyncList<T>();
	}
	
	public static class SyncListItr<T> implements ListIterator<T>
	{
		protected final SyncList<T> l;
		protected int i = 0;
		
		@SuppressWarnings("unqualified-field-access")
		public SyncListItr(SyncList<T> list)
		{
			l = list;
			
		}
		
		@SuppressWarnings("unqualified-field-access")
		public SyncListItr(SyncList<T> list, int b)
		{
			l = list;
			i = b;
			
		}
		
		@Override
		public void add(T arg0)
		{
			this.l.add(arg0);
			
		}
		
		@Override
		public boolean hasNext()
		{
			return this.i < this.l.size();
		}
		
		@Override
		public boolean hasPrevious()
		{
			return this.i > 0;
		}
		
		@Override
		public T next()
		{
			return this.l.get(this.i++);
		}
		
		@Override
		public int nextIndex()
		{
			return this.i + 1;
		}
		
		@Override
		public T previous()
		{
			return this.l.get(--this.i);
		}
		
		@Override
		public int previousIndex()
		{
			return this.i - 1;
		}
		
		@Override
		public void remove()
		{
			this.l.remove(this.i);
			
		}
		
		@Override
		public void set(T arg0)
		{
			this.l.set(this.i, arg0);
			
		}
		
	}
	
}
