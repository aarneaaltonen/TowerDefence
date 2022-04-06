package Game

import java.awt.{Color, Graphics2D}


/**
 *
 * @param healthPoints
 * @param speed speed goes from 1 to 100. 1 being very slow and 100 being really fast
 * @param path
 */

abstract class Enemy(var healthPoints : Int, var speed : Int,val path : List[Pos], var coinReward : Int) {
  var position = path.head
  var maxHealth = healthPoints
  private var path_pos = 0
  private var move_count = 0.0
  private var move_dis = 0
  var reachedEnd = false


  def move() = {


    var pos1 = path(path_pos) //viimeinen Pos

    if (path_pos  >= path.length -1) {
      reachedEnd = true
    }
    var pos2 = path((path_pos +1) % path.length)

    var dirn = (pos2 - pos1)
    val length = math.sqrt(math.pow(dirn.x,2) + math.pow(dirn.y,2))
    dirn = dirn / length
    position = position + (dirn * (speed/10.0))

    //move to next point
    if ( dirn.x >= 0) {
      if (dirn.y >= 0) { // oikeelle alas
        if (position.x >= pos2.x || position.y >= pos2.y) {
          path_pos += 1
        }
      } else { // oikeelle ylös
        if (position.x >= pos2.x || position.y <= pos2.y) {
          path_pos += 1
        }
      }
    } else { // vasemmalle alas
      if (dirn.y >= 0) {
        if (position.x <= pos2.x && position.y >= pos2.y) {
          path_pos += 1
        }
      } else {
        if (position.x <= pos2.x && position.y >= pos2.y) {
          path_pos += 1
        }
      }
    }
  }

  def draw(g : Graphics2D) : Unit

  def takeDamage(amount : Int) = {
    healthPoints = healthPoints - amount
  }

}
