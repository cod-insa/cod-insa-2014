# Thrift file, to generate CodINSA final client/server architecture.
# Nicolas Vailliet & Timothé Viot
# 08/02/2014

#Types and structures definition

namespace java genbridge
namespace cpp genbridge
namespace py genbridge

typedef i32 int

# Utils data

struct CoordData {
1: double x,
2: double y
}

# Data used for the client to retrieve the Data from the server

enum PlaneStateData {
	IDLE = 1,
	MOVING = 2,
	ATTACKING = 3,
	AT_AIRPORT = 4
}

struct PlaneData {
	1: int plane_id,
	2: CoordData posit,
	3: int ai_id,
	4: int energy,
	5: int gaz,
	6: PlaneStateData state
}

struct BaseData {
	1: int base_id
}

struct BaseInitData {
	1: int base_id,
	2: CoordData posit
}

struct InitData {
	1: list<BaseInitData> bases
}

struct Data {
	1: int numFrame,
	2: list<PlaneData> planes,
	3: list<BaseData> bases
}

# Bridge is like a bridge on the network, between PlaneSimProxy.proxy.Proxy (Bridge.Client) 
# and PlaneSimServer.network.Server (Bridge.Processor)
# The client calls these methods, and the server handles each call 
# the way it wants (BridgeHandler)
# This service will be called synchronously

service Bridge { 
	int connect(1: string nom),
	InitData retrieveInitData(1: int idConnection),
	Data retrieveData(1: int idConnection)
}

# Structs used to send Commands to the server

# Some generic commands
struct CommandData {
	1: int numFrame
}

struct PlaneCommandData {
	1: CommandData c,
	2: int idPlane
}

# Specific commands

struct MoveCommandData {
	1: PlaneCommandData pc,
	2: CoordData posDest
}

struct WaitCommandData {
	1: PlaneCommandData pc
}

struct TakeOffCommandData {
	1: PlaneCommandData pc
}

struct LandCommandData {
	1: PlaneCommandData pc,
	2: int idBase
}

struct Response {
	1: int code, # 0 : Success, -1 : Error on command, -2 : Error timeout
	2: string message # empty if success
}

service CommandReceiver {
	Response sendMoveCommand(1: MoveCommandData cmd, 2: int idConnection),
	Response sendWaitCommand(1: WaitCommandData cmd, 2: int idConnection),
	Response sendTakeOffCommand(1: WaitCommandData cmd, 2: int idConnection),
	Response sendLandCommand(1: WaitCommandData cmd, 2: int idConnection)
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
