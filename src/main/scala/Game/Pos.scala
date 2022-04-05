package Game
import math.pow
import scala.math.hypot

case class Pos(x: Double, y: Double) {
  def +(other: Pos) = new Pos(x + other.x, y + other.y)

  def -(other: Pos) = new Pos(x - other.x, y - other.y)

  def distance(other : Pos):Double = math.sqrt(math.pow((other.x-x),2) + math.pow((other.y-y),2) )

  def len = math.hypot(x, y)

  def *(vakio : Double) =  new Pos(x * vakio, y * vakio)
  def /(vakio : Double) = new Pos(x / vakio, y / vakio)
}
