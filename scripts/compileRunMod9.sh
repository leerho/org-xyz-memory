export JAVA_HOME=$JAVA9_HOME
export JAVAC=$JAVA_HOME/bin/javac
export JAR=$JAVA_HOME/bin/jar
export JAVA=$JAVA_HOME/bin/java
patha=nomvn-mod-jdk9

cd $patha
echo PWD:$(pwd)
echo $JAVA_HOME
echo "--- CLEAN & COMPILE ---"
rm -rf target
mkdir target
mkdir target/classes
mkdir target/test-classes

$JAVAC -d target/test-classes -cp "libs/*" -p mods $(find . -name '*.java')

echo "---- RUN ----"
echo PWD:$(pwd)

$JAVA -cp target/test-classes:"/libs/*":src/test/resources -p mods org.xyz.memory.CheckJava9plus

#echo "--- JAR ---"
# $JAR \
#    --create \
#    --file libs/org.xyz.memory.jar \
#    --main-class org.xyz.memory.CheckJava9Plus \
#    -C target/test-classes .