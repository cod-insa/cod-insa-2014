THRIFT_VER =thrift-0.9.1
USR_DIR    =/usr
THRIFT_DIR =${USR_DIR}/${THRIFT_VER}
INCS_DIRS  =-I${USR_DIR}/include -I${THRIFT_DIR}/include/thrift
LIBS_DIRS  =-L${USR_DIR}/lib -L${USR_DIR}/${THRIFT_VER}/lib
CPP_DEFS   =-D=HAVE_CONFIG_H
CPP_OPTS   =-Wall  -std=c++0x
LIBS       =-lthrift

GEN_SRC    = ./gen-cpp/Bridge.cpp ./gen-cpp/CommandReceiver.cpp ./gen-cpp/network_constants.cpp ./gen-cpp/network_types.cpp
GEN_INC    = -I./gen-cpp

default: client

client: main.cpp
	g++ ${CPP_OPTS} ${CPP_DEFS} -o ia ${GEN_INC} ${INCS_DIRS} main.cpp ${GEN_SRC} ${LIBS_DIRS} ${LIBS}

clean:
	$(RM) -r ia
