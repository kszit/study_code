package com.kszit.stu_log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Hello world!
 * 
 * 
 * 
 * <li>可以使用slf4j-api和slf4j-simple,classpath下的log4j.properties不起作用；
 * 若无log4j.properties时，后台有错误提示。</li>
 * <dependency>
    	<groupId>org.slf4j</groupId>
    	<artifactId>slf4j-api</artifactId>
    	<version>1.7.12</version>
    	<type>jar</type>
    	<scope>compile</scope>
    </dependency>
    
    <dependency>
    	<groupId>org.slf4j</groupId>
    	<artifactId>slf4j-simple</artifactId>
    	<version>1.7.12</version>
    	<type>jar</type>
    	<scope>compile</scope>
    </dependency>
 * 
 * <li>log4j 实现</li>
 * 		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.12</version>
			<type>jar</type>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.7.12</version>
		</dependency>
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>
 * 
 * 
 * <li>jdk log</li>
 * 		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.12</version>
			<type>jar</type>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-jdk14</artifactId>
			<version>1.7.12</version>
		</dependency>
		
 * 
 * <li>logback</li>
 * 
 *
 */
public class A
{
	private static Logger log = LoggerFactory.getLogger(A.class);
	
    public static void main( String[] args )
    {
        log.info("slf4j");
    }
}
