.DELETE_ON_ERROR:

%.class: %.java
	nxjpcc $<

run: Chess.class
	sudo nxjpc Chess

.PHONY: clean run

clean:
	rm -f Chess.class
