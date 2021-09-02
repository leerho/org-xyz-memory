# Open terminal in org-xyz-memory root
# RUN: scripts/compileRunMod9.sh

JH=$JAVA9_HOME
JAVAC=$JH/bin/javac
JAVA=$JH/bin/java
JAR=$JH/bin/jar

patha=no-maven

# Add the module-info.java file
cp module-info.txt $patha/src/test/java/module-info.java

cd $patha
# echo PWD:$(pwd)
# echo $JH
rm -rf target
mkdir -p target/test-classes

echo
echo "--- CLEAN & COMPILE ---"

$JAVAC\
  -d target/test-classes\
  -p libs\
  $(find . -name '*.java')

echo "---- RUN ----"

$JAVA\
  --add-exports java.base/jdk.internal.misc=org.apache.datasketches.memory\
  --add-exports java.base/jdk.internal.ref=org.apache.datasketches.memory\
  --add-opens java.base/java.nio=org.apache.datasketches.memory\
  --add-opens java.base/sun.nio.ch=org.apache.datasketches.memory\
  -cp src/test/resources\
  -p target/test-classes:libs\
  -m org.xyz.memory/org.xyz.memory.RunMain

$JAVA\
  --add-opens java.base/sun.nio.ch=org.apache.datasketches.memory\
  -cp src/test/resources\
  -p target/test-classes:libs\
  -m org.xyz.memory/org.xyz.memory.RunMain2

# output the directory structure
tree

# remove the module-info.java file
rm -f src/test/java/module-info.java

#echo "--- JAR ---"
# $JAR \
#    --create \
#    --file libs/org.xyz.memory.jar \
#    --main-class org.xyz.memory.CheckJava9Plus \
#    -C target/test-classes .