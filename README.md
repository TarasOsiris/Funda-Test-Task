# Funda-Test-Task

Android Test Task for Funda Makelaars

* It might look a bit over engineered at first glance because I got carried away a bit:) Here are some explanations:
* It is an Android app implemented using MVP pattern
* Ui options to choose are in the drawer on the left
* Classes to look at for API communication are MakelaarsRemoteDataSource and MakelaarsPresenter for most business logic.
* The project has two build flavours: "mock" and "prod", mock build flavour is reading the text from json file from resources and prod is calling the api, choose the "prodDebug" Build variant
* Some things are simplified and some UI corner cases are not handled for the sake of simplicity (cancelling current calculations UI e.g.)
