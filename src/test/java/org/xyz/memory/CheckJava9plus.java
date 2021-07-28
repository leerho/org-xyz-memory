/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.xyz.memory;

import java.io.File;

import org.apache.datasketches.memory.MapHandle;
import org.apache.datasketches.memory.Memory;
import org.apache.datasketches.memory.WritableHandle;
import org.apache.datasketches.memory.WritableMemory;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

public class CheckJava9plus {
  
  @BeforeTest
  static void printJDK() {
    println("JDK Full Version : " + Util.JDK_VERSION_STRING);
    println("JDK Major Version: " + Util.JDK_MAJOR_VERSION);
    println("");
  }
  
  @Test
  public void checkHeapWritableMemory() {
    String str = "Heap WritableMemory Successful";
    WritableMemory mem = WritableMemory.allocate(2 * str.length());
    writeReadAndPrintString(mem, str);
  }
  
  @Test
  public void checkAllocateDirect() throws Exception {
    String str = "Allocate Direct Successful";
    WritableHandle wh = WritableMemory.allocateDirect(2 * str.length());
    WritableMemory wmem = wh.getWritable();
    writeReadAndPrintString(wmem, str);
    wh.close();
  }
  
  @Test
  public void checkMap() throws Exception {
    File file = Util.getResourceFile(getClass(), "LoremIpsum.txt");
    MapHandle mh = Memory.map(file);
    Memory mem = mh.get();
    println("File: LoremIpsum.txt:");
    printMappedFile(mem);
    mh.close();
  }
  
  /**********************/
  
  private static void writeReadAndPrintString(WritableMemory wmem, String str) {
    int len = str.length();
    char[] cArr1 = str.toCharArray();
    wmem.putCharArray(0, cArr1, 0, len);
    char[] cArr2 = new char[len];
    wmem.getCharArray(0, cArr2, 0, len);
    String s2 = String.valueOf(cArr2);
    println(s2);
    println("");
  }
  
  private static void printMappedFile(Memory mem) {
    StringBuilder sb = new StringBuilder();
    mem.getCharsFromUtf8(0, (int)mem.getCapacity(), sb);
    println(sb.toString());
  }
  
  public static void main(final String[] args) throws Exception {
    CheckJava9plus check = new CheckJava9plus();
    printJDK();
    check.checkHeapWritableMemory();
    check.checkAllocateDirect();
    check.checkMap();
    
  }
  
  static void println(Object obj) { System.out.println(obj.toString()); }
}
