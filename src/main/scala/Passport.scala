package main.scala

case class Taggings(
                     predicate: String,
                     value: String
                   )
case class Passport(
                     passportId: String,
                     language: String,
                     locator: String,
                     home: String,
                     taggings: List[Taggings],
                     availability: String
                   )
case class Results(
                    results: List[Passport]
                  )

case class Weightings(
                       predicate: String,
                       weight: Double
                     )
case class Team(
                       teamName: String,
                       predicates: List[Weightings]
                     )
case class Teams(
                           teams: List[Team]
                         )