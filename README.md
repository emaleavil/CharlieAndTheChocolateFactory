# Charlie and the chocolate factory 

This project is created to exercise the Oompa Loompa´s API. This API has got two endpoint:

- Oompa Loompa´s list: Retrieve a list of Oompa Loompas.
    - Request url: https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/oompa-loompas
- Oompa Loompa detail: Retrieve specific information for one Oompa Loompa by id.
    - Request Url: https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/oompa-loompas/{id}

## Project structure:

This project contains two modules:
- App: This module contains the visual part of the project whose UI is created using Jetpack Compose.
- Data: This module retrieves data from network, store it locally, and so on.

## Libraries
- Hilt: As dependency injection library.
- Compose: To represent UI.
- Accompanist: To help with animations.
- Navigation: To navigate between screens.
- Retrofit: To make HTTP request.
- Room: To create/use sqlite databases.
- Paging: To work with paginated responses from server or local storage.
- Coil: To work with images.

## Extras
- Github Actions in order to create a CI pipeline
- Ktlint to check/format code style.
