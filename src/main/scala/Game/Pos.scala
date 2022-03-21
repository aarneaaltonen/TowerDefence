package Game

case class Pos(x: Int, y: Int) {
  def +(other: Pos) = (x + other.x, y + other.y)

  def -(other: Pos) = (x - other.x, y - other.y)

  def len = math.hypot(x, y)
}
