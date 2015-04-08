name := "multi-proxy-root"


lazy val root = project.in(file(".")).aggregate(client, one, two)

lazy val one = project.in(file("one"))

lazy val two = project.in(file("two"))

lazy val client = project.in(file("client"))
