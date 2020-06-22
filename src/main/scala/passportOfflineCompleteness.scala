import bbc.cps.vivostreamnotificationbridge.util.HttpConfig
import main.scala.{Passport, Results, Teams}
import org.json4s.{DefaultFormats}
import org.json4s.jackson.JsonMethods._

import scala.io.Source._

object passportOfflineCompleteness extends HttpConfig {

  implicit val formats = DefaultFormats
  val usedPredicates = List()
  val weightingsJson = parse(fromResource("weightings").reader()).extract[Teams]

  def main(args: Array[String]) = {
    def get(url: String) = fromURL(url).mkString
    val url = "https://passport-store.int.api.bbci.co.uk/passport?locator=urn:bbc:cps:curie:asset:21312412"
    val passport = parse(get(url)).extract[Results].results.head
    getCompleteness(passport, weightingsJson)
  }

  def getCompleteness(passport: Passport, weightingsJson: Teams) = {
    val team = weightingsJson.teams.filter(team => team.teamName == passport.home).head
    val weightSum = team.predicates.map(predicate => predicate.weight).sum
    println(weightSum)
  }
}


