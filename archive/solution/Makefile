all: Puissance4.class

%.class: %.java
	javac $<

Puissance4.class: Puissance4.java IHM.class IA.class

IHM.class: Plateau.class

run: Puissance4.class
	java Puissance4

run8: Puissance4.class
	java Puissance4 8

jar: Puissance4.class
	jar cvfe Puissance4.jar Puissance4 *.class img

clean:
	rm -f *.class *.jar *~
