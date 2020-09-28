Personajes de Marvel App
=========================

La aplicación usa Clean Architecture basada en MVVM y el patron Repository. 

Se ha implementado siguiendo lo recomendado por Google [Guide to app architecture](https://developer.android.com/jetpack/docs/guide).

![Guide to app architecture](screenshots/guide-to-app-architecture.png "Guide to app architecture")

La aplicacion esta escrita en Kotlin.

Se usa Android Jetpack.

Para la ejecucion de peticiones HTTP se usa Retrofit, OkHttp y GSON. Los datos son guardados en una Base de datos usando Room,
el cual sirve como unica fuente de informacion, y ayuda para el soporte del app sin internet.

Paging library es usada para la paginacion online and offline.

Se usa Kotlin Coroutines.

Navigation component gestiona la navegacion.

Dagger 2 is usada para la injeccion de dependencias.

Glide es usado para cargar imagenes y Timber para el logging.

Se ha usado lottie para la animacion de loading [https://lottiefiles.com/](https://lottiefiles.com/)

Se hace uso del API de Marvel [https://developer.marvel.com/docs](https://developer.marvel.com/docs)

Se tiene 2 pantallas: 

 1. Listado de personajes 
    
    https://gateway.marvel.com/v1/public/characters?apikey=xx&hash=xx&ts=1&offset=20
    
    Con paginacion usando Paging.
    
 2. Detalle de un personaje 
 
    https://gateway.marvel.com/v1/public/characters/1009149?apikey=xxx&hash=xxxx&ts=1


Se ha usado Android Studio 4.0

Se ha creado Test usando JUnit y Mockito, MockWebServer

Librerias usadas
--------------
El app usa principalmente Android Jetpack Components

![Android Jetpack](screenshots/jetpack_donut.png "Android Jetpack Components")

y tambien algunas librerias de terceros segun:

* [Foundation]:
  * [AppCompat] - Para compatibilidad con version anteriores de Android.
  * [Android KTX] - Para escribir codigo mas conciso en Kotlin.
* [Architecture] - 
  * [Data Binding] - Para enlazar(bind) datos Observables con UI.
  * [Lifecycles] - Para gestionar mejor el ciclo de vida (lifecycle).
  * [LiveData] - Son objetos de datos que notifican a la Vista(View) cuando hay un cambio en el origen de datos.
  * [Navigation] - Se usa para gestionar la navegacion entre fragments.
  * [Room] - Para gestionar la base de datos interna.
  * [ViewModel] - Guarda la informcion de la vista y se conserva durante los cambio en el lifecycle.
  * [Paging] - Se esta usando para cargar los personales por grupos de 20.
* [UI]
  * [Fragment]
  * [Layout]
  * [Material] - Material Components.
* Third party
  * [Kotlin Coroutines] para gestionar hilos del back y simplificar el codigo reduciendo el usa de Callbacks.
  * [Dagger 2] Para la inyeccion de dependencias.
  * [Retrofit] - Para la conexion a los servicios REST
  * [OkHttp 3] Cliente HTTP.
  * [GSON] Conversor JSON.
  * [Glide] Para cargar imagenes.
  * [Timber] para el Looging.


Pantallazos
-----------

![CharactersFragment](screenshots/Screenshot_1601325140.png "Personajes en Lista")
![CharactersFragment](screenshots/Screenshot_1601325144.png "Personajes en Grilla")
![CharacterFragment](screenshots/Screenshot_1601325150.png "Detalle del personaje")


