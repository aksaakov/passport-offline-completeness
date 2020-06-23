package bbc.dpub.PassportOfflineCompleteness

case class Taggings(
  predicate: String,
  value: String
)
case class Passport(
  passportId: String,
  language: Option[String],
  locator: String,
  home: String,
  taggings: List[Taggings],
  availability: String
)
case class Results(
  results: List[Passport]
)

case class Weighting(
  predicate: String,
  weight: Double
)
case class Team(
  teamName: String,
  predicates: List[Weighting]
)
case class Teams(
  teams: List[Team]
)
