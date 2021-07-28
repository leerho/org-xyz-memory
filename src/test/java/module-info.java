module org.xyz.memory {
  requires org.apache.datasketches.memory;
  requires org.testng;
  opens org.xyz.memory to org.testng;
}