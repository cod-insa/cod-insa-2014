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

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * BridgeJavaServer is the server communicating over IP
 * with all clients. Single and thread.
 * @author Nicolas Vailliet
 */
public class BridgeJavaServer extends Thread{

	private static BridgeJavaServer instance = null;
	private BridgeHandler handler;
	private Bridge.Processor processor;
	private TServer server;
	private int port = 9090;
	
	private BridgeJavaServer(int port, BridgeHandler handler)
	{
		this.port = port;
		this.handler = handler;
		processor = new Bridge.Processor(handler);
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
			server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			System.out.println("Starting the server at localhost:"+port+"...");
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
		if(server.isServing())
			server.stop();
	}
	
	/**
	 * Restart server
	 */
	public void restartServer()
	{
		if(server.isServing())
			server.stop();
		server.serve();
	}
}
