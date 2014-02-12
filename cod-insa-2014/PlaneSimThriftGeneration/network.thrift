# Thrift file, to generate CodINSA final client/server architecture.
# Nicolas Vailliet - Timothe Viot
# 08/02/2014

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
  4: int arg2,		 	# Voir comment tester les arguments optionnels ou pas optionnel et on envoie -1
  5: int arg3 
}

struct Response {
  1: int code,		 	# 0 : Success, -1 : Error on command, -2 : Error timeout
  2: string message 		# empty if success
}

# Bridge is like a bridge on the network, between PlaneSimProxy.proxy.Proxy (Bridge.Client) 
# and PlaneSimServer.network.Server (Bridge.Processor)
# The client calls these methods, and the server handles each call 
# the way it wants (BridgeHandler)
# connect and retrieveData are called synchronously
# addActionToPerform is asynchronous


service Bridge { 
    	int connect(1: string nom),
	Data retrieveData(1: int idConnection),
	Response addActionToPerform(1: int idConnection, 2: Action act)
}

