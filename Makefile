SRC_DIR=src
PRSNT_DIR=presentacio
DMN_DIR=domain
PERS_DIR=persistencia
TEST_DIR=testsClasses
DRIVERS_DIR=Drivers
EXTRNL_LIB_DIR=lib/json-simple-1.1.jar
DOCS_DIR=docs
BIN_DIR=bin


JAVAC=javac
JAVA=java
JAR=/opt/java/jdk1.8.0_191/bin/jar
JAVADOC=/opt/java/jdk1.8.0_191/bin/javadoc
export CLASSPATH=$(BIN_DIR):$(EXTRNL_LIB_DIR)

JFLAGS= -d $(BIN_DIR) -sourcepath $(SRC_DIR)
RM=rm -rf

MAIN=Main
DRIVER=MainDriver
JUNIT=MainJUnit

help : 
	@echo "make {build|run|jar|runjar|doc|clean|cleanall}"

default : build

build : 
	$(JAVAC) $(JFLAGS) $(SRC_DIR)/*/*.java $(SRC_DIR)/Main.java


run : $(BIN_DIR)/$(MAIN).class
	$(JAVA) $(MAIN)

jar :
	cd bin && cp ../lib/json* . && $(JAR) cvfm GeneradorHoraris.jar ../Manifest.txt . && rm -f json* 

runjar :
	 $(JAVA) -jar bin/GeneradorHoraris.jar

doc : 
	$(JAVADOC) -encoding UTF-8 -d  $(DOCS_DIR)/javadoc -exclude org -sourcepath $(SRC_DIR) -subpackages .

clean :
	$(RM) $(BIN_DIR)/*

cleanall :
	$(RM) *.jar $(BIN_DIR)/* $(DOCS_DIR)/javadoc
	
