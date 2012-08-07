package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

import models._

import com.mongodb.casbah.Imports._

object SignUp extends Controller {

  val signupForm = Form(
  	mapping(
  	  "email" -> email
  	)(UserForm.apply)(UserForm.unapply)
  )
  
  def form = Action {
    Ok(html.signup.form(signupForm))
  }
  
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
  	  errors => BadRequest(html.signup.form(errors)),
  	  userform => {
  	  	val user = User(
  	  		email = userform.email
	  	)
  	  	User.save(user)
  	  	Ok("success")
  	  }
  	)
  }

}