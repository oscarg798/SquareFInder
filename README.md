# SquareFInder

this as is built using clean architecture,for the presentation layer we use a MVP as it could be much simpler than MVVM for this scenario.

As the project was built on modules we use deeplink navigation to connect them without make any one of them aware of the existence of the other modules.

All the modules has tests that you can find under the test folder of each of them 

## Modules 

### Core
Here we have some interfaces definitions to be used on other modules such as the Interactor (use cases) Interface, Base view and presenter.
Also we have some extensions used on the project. 

### Map 
Here we can find everything related with the Map where we show the user location and the neer coffee shops. 
On this module you can find unit and UI Test, that were built using Roboelectric and JUnit.

### Detail
Here we can find everything related with the Coffee Shop detail, we show here the shop name and formatted address.
Here we can some UI Test built with Espresso.

### App 
Here you can find the splashscreen and the deep link handler that serves as router for the application
