# Thrift file, to generate CodINSA final client/server architecture.
# Nicolas Vailliet & Timothé Viot
# 08/02/2014

#Types and structures definition

namespace java genbridge
namespace cpp genbridge
namespace py genbridge

typedef i32 int

struct Coord{
 1: double latid, // = y
 2: double longit // = x
}

struct Plane {
  1: int plane_id,
  2: Coord posit,
  3: int ai_id,
  4: int energy,
  5: int gaz
}

struct Base {
  1: int base_id,
  2: Coord posit
}

struct Data {
1: int numFrame,
	2: list<Plane> planes,
	3: list<Base> bases
}

enum Command{
	MOVE_PLANE = 1,
	BUILD_PLANE = 2,
	WAIT_PLANE = 3
}

struct Action{
  1: int numFrame,
  2: Command cmd,
  3: int arg1,
  4: double arg2, # -1 si non utilsé
  5: double arg3 # -1 is non utilisé
}

struct Response {
  1: int code, # 0 : Success, -1 : Error on command, -2 : Error timeout
  2: string message # empty if success
}

# Bridge is like a bridge on the network, between PlaneSimProxy.proxy.Proxy (Bridge.Client) 
# and PlaneSimServer.network.Server (Bridge.Processor)
# The client calls these methods, and the server handles each call 
# the way it wants (BridgeHandler)
# This service will be called synchronously

service Bridge { 
	int connect(1: string nom),
Data retrieveData(1: int idConnection),
Response addActionToPerform(1: Action act, 2: int idConnection)
}


# Tuto

/**
 *  bool    	Boolean, one byte
 *  byte    	Signed byte
 *  i16     	Signed 16-bit integer
 *  i32     	Signed 32-bit integer
 *  i64     	Signed 64-bit integer
 *  double  	64-bit floating point value
 *  string  	String
 *  binary  	Blob (byte array)
 *  map<t1,t2>  Map from one type to another
 *  list<t1>	Ordered list of one type
 *  set<t1> 	Set of unique elements of one type
 */
