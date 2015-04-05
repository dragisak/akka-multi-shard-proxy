package multiproxy

import akka.actor.Actor

class W1Actor extends Actor {

   override def receive = {
     case i: Int => println(s"W1 got $i")
   }
 }


