package models

// Required for Anorm mapping capability
import anorm._
import anorm.SqlParser._

// Required for the Play db functionality
import play.api.db._
import play.api.Play.current

case class Task(id: Long,label: String,progress:Long)
/* message from saitoh about ~
case class ~(a: String, b: Long)

object TestObj {
	val joined = "a" ~ 123

	joined match {
		case a ~ b => println(a, b)
		//case ~ a b=>println(a,b)
	}
}
*/
object Task {
	// Parser for mapping JDBC ResultSet to a single entity of Task model
	val task = {
		//id,label,progressというデータをつなげる。上に解説
		get[Long]("id") ~
		get[String]("label") ~
		get[Long]("progress") map {
			case id ~ label ~progress => Task(id, label,progress)
		}
		//get[String]("label") ~


		//get[Long]("progress") ~
		//http://dev-setsulla.hatenablog.jp/entry/2013/02/08/095302
		/*get[Long]("id") ~
		get[String]("label") map {
			case id ~ label  => Task(id,label)
		}*/
	}

	def all(): List[Task] = DB.withConnection { implicit c =>
		SQL("select * from task").as(task *)
	}
	
	def create(label: String) {
		DB.withConnection { implicit c =>
			SQL("insert into task (label) values ({label})").on(
				'label -> label
			).executeUpdate()
		}
	}
	
	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from task where id = {id}").on(
				'id -> id
			).executeUpdate()
		}
	}	

	def update(id: Long,label:String) {
		DB.withConnection { implicit c =>
			SQL("update task set label={label} where id = {id}").on(
				'id -> id,
				'label -> label
			).executeUpdate()
		}
	}		
}
