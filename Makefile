RM=rm -rf

SRC_DIR=src
PRSNT_DIR=presentacio
DMN_DIR=domain
PERS_DIR=persistencia
TEST_DIR=testsClasses
DRIVERS_DIR=Drivers
EXTRNL_LIB_DIR=lib/json-simple-1.1.jar
DOCS_DIR=docs
BIN_DIR=bin

JFLAGS= -d $(BIN_DIR) -sourcepath $(SRC_DIR) -cp $(EXTRNL_LIB_DIR)

JC=javac
JV=java
JR=/opt/java/jdk1.8.0_191/bin/jar
JD=/opt/java/jdk1.8.0_191/bin/javadoc

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES=\
	$(SRC_DIR)/$(PERS_DIR)/Lector_Aules_JSON.java		\
	$(SRC_DIR)/$(PERS_DIR)/Lector_Pla_JSON.java		\
	$(SRC_DIR)/$(PERS_DIR)/ControladorPersistencia.java	\
	$(SRC_DIR)/$(DMN_DIR)/assignacio.java			\
	$(SRC_DIR)/$(DMN_DIR)/assignatura.java			\
	$(SRC_DIR)/$(DMN_DIR)/Aula.java				\
	$(SRC_DIR)/$(DMN_DIR)/Aula_Exception.java		\
	$(SRC_DIR)/$(DMN_DIR)/Classe.java			\
	$(SRC_DIR)/$(DMN_DIR)/ControladorAules.java		\
	$(SRC_DIR)/$(DMN_DIR)/ControladorPlaEstudis.java	\
	$(SRC_DIR)/$(DMN_DIR)/DiaSetmana.java			\
	$(SRC_DIR)/$(DMN_DIR)/grup.java				\
	$(SRC_DIR)/$(DMN_DIR)/Horari.java 			\
	$(SRC_DIR)/$(DMN_DIR)/PlaEstudis.java			\
	$(SRC_DIR)/$(DMN_DIR)/Restriccio.java			\
	$(SRC_DIR)/$(DMN_DIR)/Tipus_Aula.java			\
	$(SRC_DIR)/$(PRSNT_DIR)/ControladorPresentacioMenuPrincipal.java	\
	$(SRC_DIR)/$(PRSNT_DIR)/ControladorPresentacioPlaEstudis.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverAssignacio.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverAssignatura.java	\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverAula.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverClasse.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverHorari.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverPlaEstudis.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverRestriccioCorrequisit.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverRestriccioSubgrup.java	\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/driverTipus_Aula.java		\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/Lector_Drivers_JSON.java	\
	$(SRC_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/MainDriver.java		\
	$(SRC_DIR)/Main.java					\


MAIN=Main
DRIVER=MainDriver

help : 
	@echo "make {build|run|rundriver|jar|runjar|doc|clean|cleanall}"

default : build

build : $(CLASSES:.java=.class)

run : $(BIN_DIR)/$(MAIN).class
	$(JV) -cp $(BIN_DIR):$(EXTRNL_LIB_DIR) $(MAIN)

rundriver : $(BIN_DIR)/$(TEST_DIR)/$(DRIVERS_DIR)/$(DRIVER).class
	$(JV) -cp $(BIN_DIR):$(EXTRNL_LIB_DIR) $(TEST_DIR).$(DRIVERS_DIR).$(DRIVER)

jar :
	$(JR) cvfe GeneradorHoraris.jar Main bin 

runjar :
	$(JV) -jar GeneradorHoraris.jar

doc : 
	$(JD) -encoding UTF-8 -d  $(DOCS_DIR)/javadoc -exclude org -sourcepath $(SRC_DIR) -subpackages .

clean :
	$(RM) $(BIN_DIR)/*

cleanall :
	$(RM) *.jar $(BIN_DIR)/* $(DOCS_DIR)/javadoc
	
