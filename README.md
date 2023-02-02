This project is just a place for me to test Android use cases and use as a template for new projects.

Its written in Kotlin. Actual setup:

- MVVM as the main architecture
  - Where each screen is essentially a package inside `\modules` containing a Fragment, a DataSource, a Repository and a ViewModel
- Retrofit for network calls at `\data`
- Hilt for dependency injection at `\di`
- RxJava for LiveData lifecycle-aware observable objects
- Timber for logging
- Single activity navigation with Fragments and Jetpack's Navigation component 

It's supposed to be a continuous project. Still has a lot to be done :)

Coming next:
- Samples on android test module
- Samples on unit test module
- Samples on creating Ui with Jetpack Compose
- Samples on handling both local and network data sources inside the repository
- Creating CI/CD scripts with github actions
