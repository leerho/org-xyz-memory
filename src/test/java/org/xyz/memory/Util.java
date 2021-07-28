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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

public class Util {
  public static final String LS = System.getProperty("line.separator");
  public final static String JDK_VERSION_STRING = System.getProperty("java.version");
  public final static int JDK_MAJOR_VERSION;
  
  static {
    JDK_MAJOR_VERSION = getJavaMajorVersion(JDK_VERSION_STRING);
  }

  private static int getJavaMajorVersion(final String jdkVersion) {
    int[] verArr = parseJavaVersion(jdkVersion); 
    return (verArr[0] == 1) ? verArr[1] : verArr[0];  
  }
  
  /**
   * Returns first two number groups of the java version string.
   * @param jdkVersion the java version string from System.getProperty("java.version").
   * @return first two number groups of the java version string.
   */
  private static int[] parseJavaVersion(final String jdkVersion) {
    final int p0, p1;
    try {
      String[] parts = jdkVersion.trim().split("[^0-9\\.]");//grab only number groups and "."
      parts = parts[0].split("\\."); //split out the number groups
      p0 = Integer.parseInt(parts[0]); //the first number group
      p1 = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0; //2nd number group, or 0
    } catch (final NumberFormatException | ArrayIndexOutOfBoundsException  e) {
      throw new IllegalArgumentException("Improper Java -version string: " + jdkVersion + "\n" + e);
    }
    //checkJavaVersion(JDK_VERSION_STRING, p0, p1); //Optional to omit this.
    return new int[] {p0, p1};
  }
  
  @Test
  public void checkJavaMajorVer() {
    println(JDK_MAJOR_VERSION);
  }
  
  @Test
  public void checkGetResource() throws Exception {
    ClassLoader cl = getClass().getClassLoader();
    URL url = cl.getResource("LoremIpsum.txt"); //"GettysburgAddress.txt"
    if (url == null) {
      println("URL is null!");
      return;
    }
    URI uri = url.toURI();
    String path = "No Path";
    path = uri.getPath(); //decodes any special characters
    println(path);
  }
  
  /**
   * Gets the absolute path of the given resource file's shortName.
   *
   * <p>Note that the ClassLoader.getResource(shortName) returns a URL,
   * which can have special characters, e.g., "%20" for spaces. This method
   * obtains the URL, converts it to a URI, then does a uri.getPath(), which
   * decodes any special characters in the URI path. This is required to make
   * obtaining resources operating-system independent.</p>
   *
   * @param clazz the relevant class from which to get the correct ClassLoader.
   * @param shortFileName the last name in the pathname's name sequence.
   * @return the absolute path of the given resource file's shortName.
   */
  public static String getResourcePath(final Class<?> clazz, final String shortFileName) {
    try {
      final URL url = clazz.getClassLoader().getResource(shortFileName);
      final URI uri = url.toURI();
      final String path = uri.getPath(); //decodes any special characters
      return path;
    } catch (final NullPointerException | URISyntaxException e) {
      throw new IllegalArgumentException("Cannot find resource: " + shortFileName + LS + e);
    }
  }

  /**
   * Gets the file defined by the given resource file's shortFileName.
   * @param clazz the relevant class from which to get the correct ClassLoader.
   * @param shortFileName the last name in the pathname's name sequence.
   * @return the file defined by the given resource file's shortFileName.
   */
  public static File getResourceFile(final Class<?> clazz, final String shortFileName) {
    return new File(getResourcePath(clazz, shortFileName));
  }
  
  /**
   * Returns a byte array of the contents of the file defined by the given resource file's
   * shortFileName.
   * @param clazz the relevant class from which to get the correct ClassLoader.
   * @param shortFileName the last name in the pathname's name sequence.
   * @return a byte array of the contents of the file defined by the given resource file's
   * shortFileName.
   */
  public static byte[] getResourceBytes(final Class<?> clazz, final String shortFileName) {
    try {
      return Files.readAllBytes(Paths.get(getResourcePath(clazz, shortFileName)));
    } catch (final IOException e) {
      throw new IllegalArgumentException("Cannot read resource: " + shortFileName + LS + e);
    }
  }
  
  static void println(Object obj) { System.out.println(obj.toString()); }
  
}


