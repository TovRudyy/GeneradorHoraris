SRC_DIR=src
PRSNT_DIR=presentacio
DMN_DIR=domain
PERS_DIR=persistencia
DRIVERS_DIR=testsClasses/Drivers
EXTRNL_LIB_DIR=lib/json-simple-1.1.jar
BIN_DIR=src/classes

JFLAGS= -d $(BIN_DIR) -sourcepath $(SRC_DIR) -cp $(EXTRNL_LIB_DIR)

JC=javac
JVR=java

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
	$(SRC_DIR)/$(DRIVERS_DIR)/driverAssignacio.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverAssignatura.java	\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverAula.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverClasse.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverHorari.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverPlaEstudis.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverRestriccioCorrequisit.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverRestriccioSubgrup.java	\
	$(SRC_DIR)/$(DRIVERS_DIR)/driverTipus_Aula.java		\
	$(SRC_DIR)/$(DRIVERS_DIR)/Lector_Drivers_JSON.java	\
	$(SRC_DIR)/$(DRIVERS_DIR)/MainDriver.java		\
	$(SRC_DIR)/Main.java					\


MAIN=Main

default: classes

classes: $(CLASSES:.java=.class)

run: $(SRC_DIR)/Main.java
	$(JVR) -cp $(SRC_DIR)/classes/:$(EXTRNL_LIB_DIR) $(MAIN)

clean:
	$(RM) $(BIN_DIR)/presentacio/*.class $(BIN_DIR)/domain/*.class $(BIN_DIR)/persistencia/*.class 

