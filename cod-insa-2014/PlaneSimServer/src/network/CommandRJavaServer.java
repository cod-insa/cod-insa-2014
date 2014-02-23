package network;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import genbridge.*;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

/**
 * BridgeJavaServer is the server communicating over IP
 * with all clients. Single and thread.
 * @author Nicolas Vailliet
 */
public class CommandRJavaServer extends Thread{

	private static CommandRJavaServer instance = null;
	private CommandReceiverHandler CommandHandler;
	private CommandReceiver.Processor commandProcessor;
	private TServer server;
	private int port = NetworkSettings.port+1;

	private CommandRJavaServer(CommandReceiverHandler crh)
	{
		this.CommandHandler = crh;
		commandProcessor = new CommandReceiver.Processor(crh);
	}

	/**
	 * 
	 * @param port
	 * @return the unique instance of BridgeJavaServer
	 */
	public static CommandRJavaServer startServer(CommandReceiverHandler comHandler)
	{
		if(instance == null)
			instance = new CommandRJavaServer(comHandler);
		if(!instance.isAlive())
			instance.start();
		return instance;
	}

	/**
	 * Run function
	 * The handler handles all requests from clients
	 * The processor, coming from Thrift, allows communication
	 *  Simple multithreaded server (not secure version)
	 */
	@Override
	public void run() {
		super.run();
		try {
			TNonblockingServerTransport serverTransport2 = new TNonblockingServerSocket(this.port);
	        server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport2).processor(commandProcessor));
			System.out.println("Starting server thread to manage incoming command, "+NetworkSettings.ip_address+":"+this.port+"...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop the server
	 */
	public void stopServer()
	{
		System.out.println("Stopping the server...");
		if(server.isServing())
			server.stop();
	}
	
	/**
	 * Restart server
	 */
	public void restartServer()
	{
		System.out.println("Restarting the server...");
		if(server.isServing())
			server.stop();
		server.serve();
	}
}
