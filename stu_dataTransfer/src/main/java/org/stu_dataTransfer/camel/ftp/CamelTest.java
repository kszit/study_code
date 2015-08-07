package org.stu_dataTransfer.camel.ftp;

import org.apache.camel.Main;
import org.apache.camel.builder.RouteBuilder;

public class CamelTest {

	public static void main(String args[]) throws Exception {
        Main camelMain = new Main();
        camelMain.enableHangupSupport(); //ctrl-c shutdown
        camelMain.addRouteBuilder(new RouteBuilder() {
               public void configure() {

from(
"ftp://172.31.201.169/001/22?username=put&password=genertech&delay=3000" )
      .to("file:e://data/outbox");

               }
       });

       camelMain.run(); //Camel will keep running indefinitely
    }

}
