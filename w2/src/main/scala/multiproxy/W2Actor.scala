package multiproxy

import akka.actor.Actor

class W2Actor extends Actor {

   override def receive = {
     case i: Int => println(s"W2 got $i")
   }
 }


