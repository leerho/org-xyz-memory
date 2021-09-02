# Open terminal in org-xyz-memory root
# RUN: scripts/compileRunNoMod9.sh

JH=$JAVA9_HOME
JAVAC=$JH/bin/javac
JAVA=$JH/bin/java
JAR=$JH/bin/jar

patha=no-maven

cd $patha
# echo PWD:$(pwd)
# echo $JH
rm -rf target
mkdir -p target/test-classes

echo
echo "--- CLEAN & COMPILE ---"

$JAVAC\
  -d target/test-classes\
  -cp libs/*\
  $(find . -name '*.java')

echo "---- RUN ----"

$JAVA\
  --add-exports java.base/jdk.internal.misc=ALL-UNNAMED\
  --add-exports java.base/jdk.internal.ref=ALL-UNNAMED\
  --add-opens java.base/java.nio=ALL-UNNAMED\
  --add-opens java.base/sun.nio.ch=ALL-UNNAMED\
  -cp libs/*:target/test-classes:src/test/resources\
  org.xyz.memory.RunMain

$JAVA\
  -cp libs/*:target/test-classes:src/test/resources\
  org.xyz.memory.RunMain2

# output the directory structure
tree

#echo "--- JAR ---"
# $JAR \
#    --create \
#    --file libs/org.xyz.memory.jar \
#    --main-class org.xyz.memory.CheckJava9Plus \
#    -C target/test-classes .