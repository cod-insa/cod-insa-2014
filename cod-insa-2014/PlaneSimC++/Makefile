CC = clang
CXX = clang++
CFLAGS = -Wall
CXXFLAGS = -Wall -std=c++0x
EXEC_NAME = ia
INCLUDES =
LIBS = /usr/local/include/thrift
OBJ_FILES = main.o gen-cpp/Bridge.o gen-cpp/Bridge_server.skeleton.o gen-cpp/CommandReceiver.o gen-cpp/CommandReceiver_server.skeleton.o gen-cpp/network_constants.o gen-cpp/network_types.o
INSTALL_DIR = /usr/bin

all : $(EXEC_NAME)

clean :
	  rm $(EXEC_NAME) $(OBJ_FILES)

$(EXEC_NAME) : $(OBJ_FILES)
	  $(CXX) -o $(EXEC_NAME) $(OBJ_FILES) $(LIBS)

%.o: %.cpp
	  $(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ -c $<

%.o: %.cc
	  $(CXX) $(CXXFLAGS) $(INCLUDES) -o $@ -c $<

%.o: %.c
	  $(CC) $(CFLAGS) $(INCLUDES) -o $@ -c $<

install :
	  cp $(EXEC_NAME) $(INSTALL_DIR)
