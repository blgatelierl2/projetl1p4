all: Puissance4.class

Puissance4.class: Puissance4.java
	javac $<

run: Puissance4.class
	java Puissance4

clean:
	rm -f *.class *~
