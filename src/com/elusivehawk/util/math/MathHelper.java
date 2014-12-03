
package com.elusivehawk.util.math;

import com.elusivehawk.util.RNG;
import static com.elusivehawk.util.math.MathConst.*;

/**
 * 
 * Convenience class for math functions.
 * 
 * @author Elusivehawk
 */
public final class MathHelper
{
	public static final float PI = 3.141592653589793238f;
	
	private MathHelper(){}
	
	public static boolean bounds(float f, float min, float max)
	{
		return f >= min && f <= max;
	}
	
	public static boolean bounds(int i, int min, int max)
	{
		return i >= min && i <= max;
	}
	
	public static boolean bounds(long l, long min, long max)
	{
		return l >= min && l <= max;
	}
	
	public static boolean bounds(Vector v, Vector min, Vector max)
	{
		int size = min(v.size(), min.size(), max.size());
		
		for (int c = 0; c < size; c++)
		{
			if (!bounds(v.get(c), min.get(c), max.get(c)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public static Vector calcNormal(Vector one, Vector two, Vector three)
	{
		return (Vector)cross(one.sub(two, false), one.sub(three, false)).normalize();
	}
	
	public static float clamp(float f, float min, float max)
	{
		return Math.min(max, Math.max(f, min));
	}
	
	public static int clamp(int i, int min, int max)
	{
		return Math.min(max, Math.max(i, min));
	}
	
	public static long clamp(long l, long min, long max)
	{
		return Math.min(max, Math.max(l, min));
	}
	
	public static Vector clamp(Vector v, Vector min, Vector max)
	{
		int size = min(v.size(), min.size(), max.size());
		
		Vector ret = new Vector(size);
		
		for (int c = 0; c < size; c++)
		{
			ret.set(c, clamp(v.get(c), min.get(c), max.get(c)), false);
			
		}
		
		return ret;
	}
	
	public static Vector cross(Vector one, Vector two)
	{
		Vector ret = new Vector();
		
		cross(ret, one, two);
		
		return ret;
	}
	
	public static void cross(Vector ret, Vector one, Vector two)
	{
		assert one.size() <= 3 && two.size() <= 3 && ret.size() <= 3;
		
		float ax = one.get(X);
		float ay = one.get(Y);
		float az = one.get(Z);
		
		float bx = two.get(X);
		float by = two.get(Y);
		float bz = two.get(Z);
		
		ret.set(0, (ay * bz) - (az * by), false);
		ret.set(1, (az * bx) - (ax * bz), false);
		ret.set(2, (ax * by) - (ay * bx));
		
	}
	
	public static float dist(Vector from, Vector to)
	{
		return (float)Math.sqrt(distSquared(from, to));
	}
	
	public static float distSquared(Vector from, Vector to)
	{
		int size = Math.min(from.size(), to.size());
		float ret = 0f;
		
		for (int c = 0; c < size; c++)
		{
			ret += square(to.get(c) - from.get(c));
			
		}
		
		return ret;
	}
	
	public static float dot(Vector one, Vector two)
	{
		int size = Math.min(one.size(), two.size());
		float ret = 0f;
		
		for (int c = 0; c < size; c++)
		{
			ret += one.get(c) * two.get(c);
			
		}
		
		return ret;
	}
	
	public static float interpolate(float one, float two)
	{
		return interpolate(one, two, 1f);
	}
	
	public static float interpolate(float one, float two, float factor)
	{
		assert bounds(factor, 0.0f, 1.0f);
		
		return ((two * factor) + ((1f - factor) * one));
	}
	
	public static Vector interpolate(Vector one, Vector two, float factor)
	{
		return interpolate(one, two, factor, new Vector(Math.min(one.size(), two.size())));
	}
	
	public static Vector interpolate(Vector one, Vector two, float factor, Vector dest)
	{
		for (int c = 0; c < dest.size(); c++)
		{
			dest.set(c, interpolate(one.get(c), two.get(c), factor), false);
			
		}
		
		return dest;
	}
	
	public static boolean isOdd(int i)
	{
		return (i & 1) == 1;
	}
	
	public static float length(IMathArray<Float> m)
	{
		float ret = 0f;
		
		for (int c = 0; c < m.size(); c++)
		{
			ret += square(m.get(c));
			
		}
		
		return ret;
	}
	
	public static int percent(int i, int max)
	{
		return (int)(((float)i / max) * 100);
	}
	
	public static float pow(float f, int pow)
	{
		float ret = f;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= f;
			
		}
		
		return ret;
	}
	
	public static int pow(int i, int pow)
	{
		int ret = i;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= i;
			
		}
		
		return ret;
	}
	
	public static boolean rollDice(float weight)
	{
		return weight > RNG.rng().nextFloat();
	}
	
	public static float square(float f)
	{
		return f * f;
	}
	
	public static int square(int i)
	{
		return i * i;
	}
	
	public static float toDegrees(float radian)
	{
		return (radian * 180) / PI;
	}
	
	public static float toRadians(float degree)
	{
		return (degree * PI) / 180;
	}
	
	public static Vector toRadians(Vector vec)
	{
		Vector ret = new Vector(vec);
		
		for (int c = 0; c < ret.size(); c++)
		{
			ret.set(c, toRadians(ret.get(c)), false);
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	public static int max(int... is)
	{
		int ret = Integer.MIN_VALUE;
		
		for (int i : is)
		{
			ret = Math.max(ret, i);
			
		}
		
		return ret;
	}
	
	public static int min(int... is)
	{
		int ret = Integer.MAX_VALUE;
		
		for (int i : is)
		{
			ret = Math.min(ret, i);
			
		}
		
		return ret;
	}
	
}
