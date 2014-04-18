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
	GOING_TO = 2,
	FOLLOWING = 3,
	ATTACKING = 4,
	LANDING = 5,
	AT_AIRPORT = 6,
	DEAD = 7
}

struct PlaneBasicData { // These are the basic information of a plane
	1: int plane_id,
	2: CoordData posit,
	3: int ai_id,
	4: double health,
	5: bool canAttack,
	6: int planeTypeId
}

struct PlaneFullData { // This is when the plane is owned
	1: PlaneBasicData basic_info,
	2: int base_id,
	3: double remainingGaz, 
	4: PlaneStateData state,
	5: double militarResourceCarried,
	6: double fuelResourceCarried
}

struct BaseBasicData { // These are the basic information of a base
	1: int base_id,
	/* marked as neutral (= 0) if the base is neither neutral nor owned */
	2: int ai_id
}

struct BaseFullData { // This is when the base is owned
	1: BaseBasicData basic_info,
	2: double militarRessource,
	3: double fuelRessource
}

struct BaseInitData {
	1: int base_id,
	2: CoordData posit
}

struct ProgressAxisInitData {
	1: int id,
	2: int base1_id,
	3: int base2_id
}

struct ProgressAxisData {
	/* These are percentage, visible by anyone which see one of the two bases linked */
	1: int id,
	2: double progressBase1, 
	3: double progressBase2 
}

struct RequestData {
	1: int requestId,
	2: double timeBeforePlaneBuilt,
	3: int planeTypeId
}

struct CountryInitData {
	1: int country_id,
	2: CoordData country
}

struct ConnectionData {
	1: int con_id,
	2: int player_id
}

struct InitData {
	1: list<BaseInitData> bases,
	2: double mapWidth,
	3: double mapHeight,
	4: list<ProgressAxisInitData> progressAxis,
	5: CountryInitData myCountry,
	6: list<CountryInitData> othersCountry
}

struct Data {
	1: int numFrame,
	2: list<PlaneFullData> owned_planes,
	3: list<PlaneBasicData> not_owned_planes,
	4: list<BaseFullData> owned_bases,
	5: list<BaseFullData> not_owned_visible_bases,
	6: list<BaseBasicData> not_owned_not_visible_bases,
	7: list<ProgressAxisData> progressAxis,
	8: list<RequestData> productionLine
}

# Bridge is like a bridge on the network, between PlaneSimProxy.proxy.Proxy (Bridge.Client) 
# and PlaneSimServer.network.Server (Bridge.Processor)
# The client calls these methods, and the server handles each call 
# the way it wants (BridgeHandler)
# This service will be called synchronously

service Bridge { 
	ConnectionData connect(1: string nom),
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

struct LandCommandData {
	1: PlaneCommandData pc,
	2: int idBase
}

struct AttackCommandData {
	1: PlaneCommandData pc,
	2: int idTarget
}

struct FollowCommandData {
	1: PlaneCommandData pc,
	2: int idTarget
}

struct DropMilitarsCommandData {
	1: PlaneCommandData pc,
	2: int base_id,
	3: double quantity
}

struct FillFuelTankCommandData {
	1: PlaneCommandData pc,
	2: double quantity
}

struct ExchangeResourcesCommandData {
	1: PlaneCommandData pc,
	2: double militar_quantity,
	3: double fuel_quantity,
	4: bool deleteResources
}

struct BuildPlaneCommandData {
	1: CommandData c,
	2: int planeTypeId
}

struct CancelBuildRequestCommandData {
	1: CommandData c,
	2: int id_request
}

struct Response {
	1: int code, # 0 : Success, -1 : Error on command, -2 : Error timeout
	2: string message # empty if success
}

service CommandReceiver {
	Response sendMoveCommand(1: MoveCommandData cmd, 2: int idConnection),
	Response sendWaitCommand(1: WaitCommandData cmd, 2: int idConnection),
	Response sendLandCommand(1: LandCommandData cmd, 2: int idConnection),
	Response sendFollowCommand(1: FollowCommandData cmd, 2: int idConnection),
	Response sendAttackCommand(1: AttackCommandData cmd, 2: int idConnection),
	Response sendDropMilitarsCommand(1: DropMilitarsCommandData cmd, 2: int idConnection),
	Response sendFillFuelTankCommand(1: FillFuelTankCommandData cmd, 2: int idConnection),
	Response sendExchangeResourcesCommandData(1: ExchangeResourcesCommandData cmd, 2: int idConnection),
	Response sendBuildPlaneCommand(1: BuildPlaneCommandData cmd, 2: int idConnection),
	Response sendCancelBuildRequestCommandData(1: CancelBuildRequestCommandData cmd, 2: int idConnection)
}

# Type dispo

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
