.DELETE_ON_ERROR:
ExplosiveChess.nxj: ExplosiveChess.class
%.nxj:
	nxjlink -o $@ $(<:.class=)

%.class: %.java
	nxjc $<

.PHONY: clean

clean:
	rm -f ExplosiveChess.class ExplosiveChess.nxj

run: ExplosiveChess.nxj
	sudo nxjupload -r ExplosiveChess
