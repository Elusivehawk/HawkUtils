
package com.elusivehawk.util.math;

/**
 * 
 * Compatibility class for porting over math libraries.
 * <p>
 * NOTICE: Always deprecated; Refactor your code to use more flexible {@link VectorF}s!
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Vector3f extends Vector2f
{
	public float z = 0f;
	
	public Vector3f()
	{
		this(3);
		
	}
	
	public Vector3f(int size)
	{
		super(size);
		
	}
	
	public Vector3f(float a, float b, float c)
	{
		this();
		
		set(a, b, c);
		
	}
	
	public Vector3f(Vector3f vec)
	{
		super(vec);
		
	}
	
	@Override
	public float get(int pos)
	{
		if (pos == 2)
		{
			return this.z;
		}
		
		return super.get(pos);
	}
	
	@Override
	public Vector3f set(int pos, float f)
	{
		if (pos == 2)
		{
			this.z = f;
			
		}
		else
		{
			super.set(pos, f);
			
		}
		
		return this;
	}
	
	public void set(float a, float b, float c)
	{
		super.set(a, b);
		
		this.z = c;
		
	}
	
}
