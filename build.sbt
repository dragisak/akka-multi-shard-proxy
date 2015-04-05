name := "multi-proxy-root"


lazy val root = project.in(file(".")).aggregate(client, w1, w2)

lazy val w1 = project.in(file("w1"))

lazy val w2 = project.in(file("w2"))

lazy val client = project.in(file("client"))
