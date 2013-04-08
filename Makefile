compile-all: Main.class

Main.class: 
	javac -d "bin" -cp "src" -Xlint:unchecked src/com/foran/Main.java

clean:
	rm bin/com -rf