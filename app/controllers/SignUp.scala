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
  
  def form = Action {
    Ok(html.signup.form(signupForm, None))
  }

  def formWithCode(refcode: String) = Action {
    Ok(html.signup.form(signupForm, Some(refcode)))
  }
  
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      errors => BadRequest(html.signup.form(errors, None)),
      userform => {
        val user = User(
          email = userform.email,
          code = RandomString.random(5),
          refcode = userform.refcode
        )
        User.save(user)
        Ok(html.signup.success(user.code))
      }
    )
  }

}