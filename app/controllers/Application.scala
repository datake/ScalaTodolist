package controllers

// Required by default
import play.api._
import play.api.mvc._

// Required for form helpers
import play.api.data._
import play.api.data.Forms._

// Required to be able to import model definitions
import models._

object Application extends Controller {
  
  // Form helper with validation to require non-empty field
  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }
  
  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }
  
  //Add new Task
  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }
  //delete task
  def deleteTask(id: Long) = Action { implicit request =>
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }

/*JSON {"label": "raberu"}*/
/*Form <input type="text" name="label" xvalue="raberu"> */

  def updateTask(id: Long) = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest,
      label => {//JSON
         Task.update(id, label)
         Ok
      }
    )
  }
   def checkTask(id: Long) = Action { implicit request =>
    Task.check(id)
    Redirect(routes.Application.tasks)
  }

    def uncheckTask(id: Long) = Action { implicit request =>
    Task.uncheck(id)
    Redirect(routes.Application.tasks)
  }

/*
  def sortTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest,
      seq => {//JSON
         Task.sort(task)
         Ok
      }
    )
  }
*/
  
}