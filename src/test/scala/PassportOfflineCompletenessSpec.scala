import org.scalatest.FunSuite
import bbc.dpub.PassportOfflineCompleteness.{Results}
import org.json4s.{DefaultFormats}
import org.json4s.jackson.JsonMethods._

import scala.io.Source._

class PassportOfflineCompletenessSpec extends FunSuite {

  implicit val formats = DefaultFormats

  test("Gives a score of 1 for a complete passport") {
    val passport = parse(fromResource("completePassport.json").reader()).extract[Results].results.head
    val completeness = PassportOfflineCompleteness.getCompleteness(passport)
    assert(f"$completeness%1.2f" == "1.00")
  }

  test("Gives a partial for a partially complete passport") {
    val passport = parse(fromResource("partialPassport.json").reader()).extract[Results].results.head
    val completeness = PassportOfflineCompleteness.getCompleteness(passport)
    assert(f"$completeness%1.2f" == "0.44")
  }

  test("Handle a passport with no language") {
    val passport = parse(fromResource("noLanguagePassport.json").reader()).extract[Results].results.head
    val completeness = PassportOfflineCompleteness.getCompleteness(passport)
    assert(f"$completeness%1.2f" == "0.24")
  }

  test("Handle a passport with no taggings") {
    val passport = parse(fromResource("noTaggingsPassport.json").reader()).extract[Results].results.head
    val completeness = PassportOfflineCompleteness.getCompleteness(passport)
    assert(f"$completeness%1.2f" == "0.20")
  }

  test("Handle a passport with nothing added") {
    val passport = parse(fromResource("nothingPassport.json").reader()).extract[Results].results.head
    val completeness = PassportOfflineCompleteness.getCompleteness(passport)
    assert(f"$completeness%1.2f" == "0.00")
  }

  test("Handle a passport containing a predicate with no defined weighting") {
    val passport = parse(fromResource("undefinedPredicatePassport.json").reader()).extract[Results].results.head
    val completeness = PassportOfflineCompleteness.getCompleteness(passport)
    assert(f"$completeness%1.2f" == "0.20")
  }
}
