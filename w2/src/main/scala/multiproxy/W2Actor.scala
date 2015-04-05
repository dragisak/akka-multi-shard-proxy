package multiproxy

import akka.actor.Actor

class W2Actor extends Actor {

  override def preStart {

    println(s"Started ${self.path}")
  }

   override def receive = {
     case ("W2", i: Int) => println(s"W2 got $i")
   }
 }


