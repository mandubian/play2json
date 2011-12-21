package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  	case class Producer(name:String, place: String)
  	case class Wine(title:String, year:Int, alcohol:Float, producer:Producer, grapes: Map[String, Float], nose:List[String]) 

  	val bourgogne_castagnier = 
  		Wine(
	  		"Bourgogne", 2009, 12.5F, 
  			Producer("Castagnier", "Morey Saint Denis"),
  			grapes = Map("Pinot Noir" -> 100),
  			nose = List("cherry", "red fruits", "very light vanilla")
  		)

  	val chateauneuf_mercier = 
  		Wine(
	  		"Chateau du Pape", 2007, 15.5F, 
  			Producer("Mercier", "Courthézon"),
  			grapes = Map("Grenache" -> 80, "Syrah" -> 20),
  			nose = List("red fruits", "cafe", "woods", "leather", "pepper")
  		)

  	val jsonStr = """{
  		"wines": [
	  		{
	  			"title" : "Bourgogne",
	  			"year" : 2009,
	  			"alcohol" : 12.5,
	  			"producer" : {
		  			"name": "Castagnier",
		  			"place": "Morey Saint Denis"
		  		},
	  			"grapes" : { "Pinot Noir" : 100 },
	  			"nose" : [ "cherry", "red fruits", "very light vanilla" ]
	  		},
	  		{
	  			"title" : "Chateauneuf du Pape",
	  			"year" : 2007,
	  			"alcohol" : 15.5,
	  			"producer" : {
		  			"name": "Mercier",
		  			"place": "Courthézon"
		  		},
	  			"grapes" : { "Grenache": 80, "Syrah": 20 },
	  			"nose" : [ "red fruits", "cafe", "woods", "leather", "pepper" ]
	  		}
  		]
  	}
  	"""	
  
  	def strToJson = Action {
  		import play.api.json._

  	

  		val json = parseJson(jsonStr)
    	//Ok(views.html.index("Your new application is ready."))
   		Ok(json)
  	}

  	def classToJson = Action {
  		import play.api.json._


  		//val json = parseJson(jsonStr)
    	//Ok(views.html.index("Your new application is ready."))
    	Ok("")
  	}
  
}