# Make file pro projekt SIN 2016/2017
# Autor:  Roman Vais xvaisr00@stud.fit.vutbr.cz
# Datum:  2016/12/12
#
# Pro pouziti upravte/doplnte jmeno projektu, jmeno archivu, nazev souboru
# dokumentace (odstrante/nechte prazdne pokud dokumentace chybi) a pripadne
# pozmnente priznaky pro gcc a argumenty pro spusteni programu. Makefile bude
# predpokladat ze jmeno souboru s funkci majn je stejne jako nazev projektu
# a ze kazdy dalsi soubor se zdrojovim kodem ma svoji stejnojmenou hlavicku.
# Vsechny zdrojove soubory vcetne hlavicek by meli byt ve stejnem adresari
# jako tento makefile.
#
# Jednotlive (dodatecne) akce Make:
#	run - probehne spusteni programu
#	install - stahne se repozitar se zdrojovimi soubory a zkompilovanym JAR souborem
#	compile - vypise hlaseni ze kompilovat bez IDE neumime 
#   tar | pack - dokumentaci a makefilem do archivu
#   clear | clean - odstrani objektove a pomocne soubory
#
# tyto dodatecne vsechny prikazi v techto sekcich jsou "verbose" takze bude
# vypsano co se stalo a jakych souboru se prikaz dotkl. Pokud si nejste jisti
# jestli prikaz neovlivni nektere soubory, ktere nechcete, prekopirujte je jako
# zalohu do libovolneho podadresrare. 


# nazev zkompilovaneho JAR archivu
JarFile=simulation.jar
# nazev vetve ktera se ma stahnout
Branch=master
# URL repozitare
URL=https://github.com/xvaisr/SINproject.git
# Main class
MainClass=simulator.Simulator
# nazev souboru archivu
Archive=2-xbluch00-xrepik00-xvaisr00-08-44-44
# nazev souboru (muze byt vice) dokumentace
Dokumentace=Dokumentace.pdf

run: $(JarFile)
	java -classpath ./$(JarFile) ${MainClass}

compile:
	@echo "Zdrojove soubory je nutne zkompilovat nejlepe pomoci IDE. (viz. dokumentace)"

$(JarFile): install

install:
	@command -v git >/dev/null 2>&1 || { echo >&2 "I require foo but it's not installed.  Aborting."; exit 1; }
	@echo "Downloading all necesary files from Github repository ..."
	@mkdir -p repository
	@git clone --branch ${Branch} ${URL} repository
	@echo "Coppying '${JarFile}' from repository ..."
	@cp -t ./ ./repository/${JarFile}

# zabali do taru
tar: $(AllFiles) $(Dokumentace) makefile
	tar cvf $(Archive).tar $(Dokumentace) makefile

# zabali a zkomprimuje
pack: $(AllFiles) $(Dokumentace) makefile
	tar cvzf $(Archive).tar.gz  $(Dokumentace) makefile

# uklid v projektu
clean: clear
clear: 
	rm -f -v -r *.log ./repository
