package org.stu_dataTransfer.camel.file;

import org.apache.camel.Main;
import org.apache.camel.builder.RouteBuilder;

public class CamelTest {

	public static void main(String args[]) throws Exception {
        Main camelMain = new Main();
        camelMain.enableHangupSupport(); //ctrl-c shutdown
        camelMain.addRouteBuilder(new RouteBuilder() {
               public void configure() {

from(
"file:e://data/inbox?delay=3000" )
      .to("file:e://data/outbox");

               }
       });

       camelMain.run(); //Camel will keep running indefinitely
    }

}
