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

  	trait Wine {
      def title:String
      def year:Int
      def alcohol:Float
      def producer:Producer
      def grapes: Map[String, Float]
      def nose:List[String]
      def color:WineColor
    }

    case class WhiteWine(
      override val title:String, 
      override val year:Int, 
      override val alcohol:Float, 
      override val producer:Producer, 
      override val grapes: Map[String, Float], 
      override val nose:List[String]) extends Wine {
      
      override val color=WHITE
    }    

    case class RedWine(
      override val title:String, 
      override val year:Int, 
      override val alcohol:Float, 
      override val producer:Producer, 
      override val grapes: Map[String, Float], 
      override val nose:List[String]) extends Wine {
      
      override val color=RED
    }

    case class RoseWine(
      override val title:String, 
      override val year:Int, 
      override val alcohol:Float, 
      override val producer:Producer, 
      override val grapes: Map[String, Float], 
      override val nose:List[String]) extends Wine {
      
      override val color=ROSE
    }

  	val bourgogne_castagnier = 
  		WhiteWine(
	  		"Bourgogne", 2009, 12.5F, 
  			Producer("Castagnier", "Morey Saint Denis"),
  			grapes = Map("Pinot Noir" -> 100),
  			nose = List("cherry", "red fruits", "very light vanilla")
  		)

  	val chateauneuf_mercier = 
  		RedWine(
	  		"Chateau du Pape", 2007, 15.5F, 
  			Producer("Mercier", "Courthézon"),
  			grapes = Map("Grenache" -> 80, "Syrah" -> 20),
  			nose = List("red fruits", "cafe", "woods", "leather", "pepper")
  		)

    val vin_jaune_letoile = 
      WhiteWine(
        "Vin Jaune", 1982, 13F, 
        Producer("L'Etoile", "Arbois"),
        grapes = Map("savagnin" -> 100),
        nose = List("nuts", "dried fruits", "stone")
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
  
    implicit object ProducerFormat extends Format[Producer] {
      def reads(json: JsValue): Producer = Producer(
        (json \ "name").as[String],
        (json \ "place").as[String]
      )
      def writes(p: Producer): JsValue = JsObject(Map(
        "name" -> JsNumber(BigDecimal(p.name)),
        "place" -> JsString(p.place)
        )
      )
      
    }

  	def strToJson = Action {
  		import play.api.json._

  		val json = parseJson(jsonStr)
    	//Ok(views.html.index("Your new application is ready."))
   		Ok(json)
  	}

    def structureToJson = Action {
      import play.api.json._

      val bourgAsMap = Map(
        "name" -> "Bourgogne",
        "year" -> 2009, 
        "alcoohol" -> 12.5F, 
        "producer" -> Map("name" -> "Castagnier", "place" -> "Morey Saint Denis"),
        "grapes" -> Map("Pinot Noir" -> 100),
        "nose" -> List("cherry", "red fruits", "very light vanilla")
      )
      //Ok(views.html.index("Your new application is ready."))
      Ok(toJson(bourgAsMap))
    }

  	def classToJson = Action {
  		import play.api.json._


  		val json = toJson[WhiteWine](bourgogne_castagnier)
    	//Ok(views.html.index("Your new application is ready."))
    	Ok("test")
  	}
  
}