export JAVA_HOME=$JAVA8_HOME
export JAVAC=$JAVA_HOME/bin/javac
export JAR=$JAVA_HOME/bin/jar
export JAVA=$JAVA_HOME/bin/java
patha=/Users/lrhodes/dev/NewMemory/org-nomvn-memory8

cd $patha
echo PWD:$(pwd)
echo $JAVA_HOME
echo "--- CLEAN & COMPILE ---"
rm -rf target
mkdir target
mkdir target/classes
mkdir target/test-classes

$JAVAC -d target/test-classes/  -cp "$patha/libs/*"  $(find . -name '*.java')

echo "---- RUN ----"
cd target/test-classes/
echo PWD:$(pwd)

$JAVA -cp $patha/target/test-classes:$patha/libs/*:$patha/src/test/resources/ org.xyz.memory.CheckJava9plus

#echo "--- JAR ---"
# $JAR \
#    --create \
#    --file libs/org.xyz.memory.jar \
#    --main-class org.xyz.memory.CheckJava9Plus \
#    -C target/test-classes .