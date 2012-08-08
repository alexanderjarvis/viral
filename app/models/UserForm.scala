package models

case class UserForm(
  email: String,
  refcode: Option[String]
)