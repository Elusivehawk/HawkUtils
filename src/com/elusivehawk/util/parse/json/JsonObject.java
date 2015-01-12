
package com.elusivehawk.util.parse.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonObject extends JsonValue<Map<String, Object>>
{
	protected final Map<String, Object> objs = new HashMap<String, Object>();
	
	public JsonObject(){}
	
	public JsonObject(IPopulator<JsonObject> pop)
	{
		pop.populate(this);
		
	}
	
	@Override
	public Map<String, Object> getValue()
	{
		return this.objs;
	}
	
	@Override
	public String toJson(int tabs)
	{
		StringBuilder b = new StringBuilder();
		
		for (int c = 0; c < tabs; c++)
		{
			b.append("\t");
			
		}
		
		tabs++;
		
		b.append("{");
		
		boolean prev = false;
		
		for (Entry<String, Object> entry : this.objs.entrySet())
		{
			if (prev)
			{
				b.append(", \n");
				
			}
			else prev = true;
			
			for (int c = 0; c < tabs; c++)
			{
				b.append("\t");
				
			}
			
			Object obj = entry.getValue();
			
			b.append(String.format("\"%s\": %s", entry.getKey(), obj instanceof IJsonSerializer ? ((IJsonSerializer)obj).toJson(tabs) : obj instanceof String ? String.format("\"%s\"", obj) : obj));
			
		}
		
		b.append("\n}");
		
		return b.toString();
	}
	
	public boolean add(String name, Object data)
	{
		if (this.objs.containsKey(name))
		{
			return false;
		}
		
		this.objs.put(name, data);
		
		return true;
	}
	
	public Object getValue(String name)
	{
		return this.objs.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String name, Class<T> clazz)
	{
		Object obj = this.getValue(name);
		
		if (obj == null)
		{
			return null;
		}
		
		if (!clazz.isInstance(obj))
		{
			throw new ClassCastException(String.format("Cannot convert %s to %s", obj.getClass(), clazz));
		}
		
		return (T)obj;
	}
	
	public boolean hasKey(String name)
	{
		return this.objs.containsKey(name);
	}
	
}
