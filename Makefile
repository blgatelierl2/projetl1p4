all: Puissance4.class

%.class: %.java
	javac $<

Puissance4.class: Puissance4.java IHM.class

IHM.class: Plateau.class

run: Puissance4.class
	java Puissance4

clean:
	rm -f *.class *~
