package controllers

import play.api._
import play.api.mvc._
import play.api.json._

object Application extends Controller {
    object WineColor extends Enumeration {
     type WineColor = Value
     val WHITE, RED, ROSE = Value
    }
    
    import WineColor._

  	case class Producer(name:String, place: String)

  	case class Wine( 
      title:String, 
      year:Int, 
      alcohol:Float, 
      producer:Producer, 
      grapes: Map[String, Float], 
      nose:List[String],
      color:WineColor)


    implicit object ProducerFormat extends Format[Producer] {
      def reads(json: JsValue): Producer = Producer(
        (json \ "name").as[String],
        (json \ "place").as[String]
      )
      def writes(p: Producer): JsValue = JsObject(Map(
        "name" -> JsString(p.name),
        "place" -> JsString(p.place)
        )
      )      
    }

    implicit object WineFormat extends Format[Wine] {
      def reads(json: JsValue): Wine = new Wine(
        (json \ "title").as[String],
        (json \ "year").as[Int],
        (json \ "alcohol").as[Float],
        (json \ "producer").as[Producer],
        (json \ "grapes").as[Map[String,Float]],
        (json \ "nose").as[List[String]],
        WineColor.withName((json \ "color").as[String])
      )

      def writes(p: Wine): JsValue = JsObject(Map(
        "title" -> JsString(p.title),
        "year" -> JsNumber(BigDecimal(p.year)),
        "alcohol" -> JsNumber(BigDecimal(p.alcohol)),
        "producer" -> toJson(p.producer),
        "grapes" -> JsObject(p.grapes.map{ case (k,v) => (k, JsNumber(BigDecimal(v)))}),
        "nose" -> JsArray(p.nose.map(JsString(_))),
        "color" -> JsString(p.color.toString)
        )
      )
      
    }
  	val bourgogne_castagnier = 
  		Wine(
	  		"Bourgogne", 2009, 12.5F, 
  			Producer("Castagnier", "Morey Saint Denis"),
  			grapes = Map("Pinot Noir" -> 100),
  			nose = List("cherry", "red fruits", "very light vanilla"),
        WHITE
  		)

  	val chateauneuf_mercier = 
  		Wine(
	  		"Chateau du Pape", 2007, 15.5F, 
  			Producer("Mercier", "Courthézon"),
  			grapes = Map("Grenache" -> 80, "Syrah" -> 20),
  			nose = List("red fruits", "cafe", "woods", "leather", "pepper"),
        RED
  		)

    val vin_jaune_letoile = 
      Wine(
        "Vin Jaune", 1982, 13F, 
        Producer("L'Etoile", "Arbois"),
        grapes = Map("savagnin" -> 100),
        nose = List("nuts", "dried fruits", "stone"),
        WHITE
      )  


    val wines = List(bourgogne_castagnier, chateauneuf_mercier, vin_jaune_letoile)

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


    	//Ok(views.html.index("Your new application is ready."))
    	Ok(toJson(bourgogne_castagnier))
  	}
  
}