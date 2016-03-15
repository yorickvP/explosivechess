source $stdenv/setup

set -e
unpackPhase
cd leJOS*/build/
ant
cd ../../
mkdir -p $out/opt
mv leJOS* $out/opt/nxj
mkdir -p $out/bin
for i in $out/opt/nxj/bin/nxj*; do
makeWrapper $i $out/bin/`basename $i` --set JAVA_HOME $JDK_PATH
done
patchShebangs $out/opt/nxj/bin/
