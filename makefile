
JC = javac
.SUFFIXES: .java .class
Decrypt.class:
	$(JC) ./src/Decrypt.java
clean:
	$(RM) ./src/*.class

