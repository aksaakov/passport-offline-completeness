import bbc.dpub.PassportOfflineCompleteness.HttpConfig
import bbc.dpub.PassportOfflineCompleteness.{Passport, Results, Teams, Weighting}
import org.json4s.{DefaultFormats}
import org.json4s.jackson.JsonMethods._

import scala.io.Source._

object PassportOfflineCompleteness extends HttpConfig {

  implicit val formats = DefaultFormats
  val usedPredicates = List()
  val weightingsJson = parse(fromResource("weightings.json").reader()).extract[Teams]

  def main(args: Array[String]) = {
    val locator = args(0)
    val completeness = getCompleteness(getPassport(locator))
    println(s"Completeness of $locator = $completeness")
  }

  def getPassport(locator: String) = {
    def get(url: String) = fromURL(url).mkString
    val url = s"https://passport-store.int.api.bbci.co.uk/passport?locator=$locator"
    parse(get(url)).extract[Results].results.head
  }

  def getCompleteness(passport: Passport) = {
    val team = weightingsJson.teams.filter(team => team.teamName == passport.home).head
    var passportPredicates = passport.taggings.map(tagging => tagging.predicate).distinct
    if (!passport.language.getOrElse("").isEmpty) passportPredicates = "Language" :: passportPredicates

    val passportWeightSum = passportPredicates.map(predicate =>
      team.predicates.find(weighting => weighting.predicate == predicate) match {
        case Some(weighting) => weighting.weight
        case None => {
          println(s"No weighting defined for predicate $predicate")
          0
        }
      }
    ).sum
    val totalWeightSum = team.predicates.map(predicate => predicate.weight).sum
    passportWeightSum / totalWeightSum
  }
}
