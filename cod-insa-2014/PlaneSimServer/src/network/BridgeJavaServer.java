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

import java.util.ArrayList;
import java.util.List;

import genbridge.*;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * BridgeJavaServer is the server communicating over IP
 * with all clients. Single and thread.
 * @author Nicolas Vailliet
 */
public class BridgeJavaServer extends Thread{

	private static BridgeJavaServer instance = null;
	private BridgeHandler dataHandler;
	private Bridge.Processor dataProcessor;
	private TServer server;
	private int port = 9090;
	
	/////////////////// FIXME SHOULD BE ELSEWHERE //////////////////
	private static List<Integer> authorizedIDs = new ArrayList<Integer>();
	
	public static List<Integer> getAuthorizedIDs() {
		return authorizedIDs;
	}

	public static void addAuthorizedID(int authorizedIDs) {
		BridgeJavaServer.authorizedIDs.add(authorizedIDs);
	}
	/////////////////// SHOULD BE ELSEWHERE //////////////////

	private BridgeJavaServer(int port, BridgeHandler handler)
	{
		this.port = port;
		this.dataHandler = handler;
		dataProcessor = new Bridge.Processor(handler);
	}

	/**
	 * 
	 * @param port
	 * @return the unique instance of BridgeJavaServer
	 */
	public static BridgeJavaServer startServer(int port, BridgeHandler handler)
	{
		if(instance == null)
			instance = new BridgeJavaServer(port,handler);
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
			TServerTransport serverTransport = new TServerSocket(port);
			server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(dataProcessor));
			
			System.out.println("Starting servers at localhost:"+port+" and localhost:"+(port+1)+"...");
			System.out.println("Waiting for players to connect...");
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
