##orkut-community-archive

###Register your application
- Before you can access Google APIs, you need to set up a project on the [Google Developers Console](https://console.developers.google.com). For instructions on setting up your credentials properly, see the [Google Developers Console Help](https://developers.google.com/console/help/new/)
- Under APIs & auth section of the newly created project, enable the following API
    -   Orkut REST API
- Follow the instructions in [this page](https://developers.google.com/console/help/new/#generatingoauth2) to create a new client Id for authourization using OAuth2
- Under APIs & auth\Consent Screen, ensure that you put your email address
- Download the JSON for newly created clientId and paste into src/main/resources client_secrets.json

###Run the program
1. Ensure that Maven points to Java 1.7 version as JAVA_HOME
2. mvn compile
3. mvn exec:java


###Known issues

1. Throughout the code, the pagination logic has not been implemented. The current implementation is to fetch a max of 100 results for any API call.
    This would fail, if any API call would return more than 100 results. To get all results, pagination logic must be implemented
