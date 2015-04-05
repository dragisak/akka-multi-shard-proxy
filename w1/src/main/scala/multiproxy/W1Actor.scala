package multiproxy

import akka.actor.Actor

class W1Actor extends Actor {

  override def preStart {

    println(s"Started ${self.path}")
  }


   override def receive = {
     case ("W1", i: Int) => println(s"W1 got $i")
   }
 }


