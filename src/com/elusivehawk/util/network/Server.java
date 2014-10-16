
package com.elusivehawk.util.network;

import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.List;
import java.util.UUID;
import com.google.common.collect.Lists;

/**
 * 
 * Primary class for server-sided interfacing.
 * 
 * @author Elusivehawk
 */
public class Server implements IHost
{
	protected final INetworkMaster master;
	protected final ThreadJoinListener listener;
	protected final ThreadNetwork network;
	protected final int port, maxPlayers;
	
	protected final List<Connection> clients;
	
	protected boolean paused = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Server(INetworkMaster m, int gameport, int players)
	{
		assert m != null;
		assert players > 0;
		
		master = m;
		port = gameport;
		listener = new ThreadJoinListener(this, gameport);
		network = new ThreadNetwork(this, gameport);
		
		maxPlayers = players;
		clients = Lists.newArrayList();
		
	}
	
	@Override
	public UUID connect(AbstractSelectableChannel ch)
	{
		assert ch != null;
		
		if (this.clients.size() == this.maxPlayers)
		{
			return null;
		}
		
		Connection next = new HSConnection(this, ch, this.master.getEncryptionBitCount());
		
		this.clients.add(next);
		
		return next.getId();
	}
	
	@Override
	public void beginComm()
	{
		this.listener.start();
		
	}
	
	@Override
	public Side getSide()
	{
		return Side.SERVER;
	}
	
	@Override
	public void onDisconnect(Connection connect)
	{
		if (this.clients.isEmpty())
		{
			return;
		}
		
		if (connect == null)
		{
			return;
		}
		
		this.clients.remove(connect);
		
	}
	
	@Override
	public void close()
	{
		this.listener.stopThread();
		this.network.stopThread();
		
		this.clients.clear();
		
	}
	
	@Override
	public void setPaused(boolean pause)
	{
		this.network.setPaused(pause);
		this.paused = pause;
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public void forEveryConnection(IConnectionUser user)
	{
		for (Connection client : this.clients)
		{
			if (!user.processConnection(client))
			{
				break;
			}
			
		}
		
	}
	
	@Override
	public int getMaxPlayerCount()
	{
		return this.maxPlayers;
	}
	
	@Override
	public int getPlayerCount()
	{
		return this.clients.size();
	}
	
	@Override
	public void onHandshake(Connection con, Packet pkt)
	{
		if (con == null)
		{
			return;
		}
		
		if (this.clients.isEmpty())
		{
			return;
		}
		
		if (!this.clients.contains(con))
		{
			return;
		}
		
		if (this.master.handshake(con, pkt))
		{
			this.clients.remove(con);
			this.clients.add(new Connection(con));
			
		}
		
	}
	
}
