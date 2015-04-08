package multiproxy

import akka.actor.Actor

class ActorTwo extends Actor {

  override def preStart {

    println(s"Started ${self.path}")
  }

   override def receive = {
     case i: Int => println(s"TWO got $i")
   }
 }


