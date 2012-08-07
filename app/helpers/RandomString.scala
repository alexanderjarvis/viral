package helpers

import scala.util.Random

object RandomString {

	val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('1' to '9')

	def random(length: Int) : String = {
		(1 to length).map(x => chars(Random.nextInt(chars.length))).mkString
	}

}