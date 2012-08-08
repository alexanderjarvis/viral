package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models._
import helpers._

import com.mongodb.casbah.Imports._

object SignUp extends Controller {

  val signupForm = Form(
    mapping(
      "email" -> email,
      "refcode" -> optional(text)
    )(UserForm.apply)(UserForm.unapply)
  )
  
  def form(fb_ref: Option[String]) = Action {
    Ok(html.signup.form(signupForm, fb_ref))
  }

  def formWithCode(refcode: String) = Action {
    Ok(html.signup.form(signupForm, Some(refcode)))
  }
  
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      errors => BadRequest(html.signup.form(errors, None)),
      userform => {
        val existingEmail = User.findOne(MongoDBObject("email" -> userform.email))
        existingEmail match {
          case Some(user) => Ok(html.signup.success(user.code))
          case None => {
            val formUser = User(
	          email = userform.email,
	          code = uniqueCode(5),
	          refcode = userform.refcode
	        )
            User.insert(formUser)
            Ok(html.signup.success(formUser.code))
          }
        }
      }
    )
  }
  
  def uniqueCode(length: Int) : String = {
    val code = RandomString.random(length)
    User.findOne(MongoDBObject("code" -> code)) match {
      case Some(user) => uniqueCode(length)
      case None => code
    }
  }

}