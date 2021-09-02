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

import static org.xyz.memory.Util.*;

public class RunMain2 {
  public final static String JDK_VERSION_STRING = System.getProperty("java.version");
  public final static int JDK_MAJOR_VERSION;
  
  static {
    JDK_MAJOR_VERSION = Util.getJavaMajorVersion(JDK_VERSION_STRING);
  }
  
  public static void printJDK() {
    println("JDK Full Version : " + JDK_VERSION_STRING);
    println("JDK Major Version: " + JDK_MAJOR_VERSION);
    println("");
  }
  
  public static void main(final String[] args) throws Exception {
    printJDK();
    CheckByteBuffer.checkByteBuffer();
  }
  
  /**********************/
  
  private static void println(Object obj) { System.out.println(obj.toString()); }
}