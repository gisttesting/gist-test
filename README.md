# gist-test

Before running the tests put your token here: `api-tests/src/main/resources/config.properties`

To run the tests use `./gradlew clean test allureReport` command

Allure reports will be stored in `api-tests/build/reports/allure-report`

All operations can be checked indepentently or inside of a CRUD cycle. Run `getCRUD()` test if you want to have smoke check of gist CRUD.
