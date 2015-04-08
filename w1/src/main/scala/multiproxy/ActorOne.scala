package multiproxy

import akka.actor.Actor

class ActorOne extends Actor {

  override def preStart {

    println(s"Started ${self.path}")
  }


   override def receive = {
     case i: Int => println(s"ONE got $i")
   }
 }


