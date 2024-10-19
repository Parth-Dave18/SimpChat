Ad googleservices.json file in SimpChat/app folder.

To get a Google Services JSON file from Firebase, you can do the following: 
Sign in to Firebase 
Go to Project settings 
In the Your apps card, select the app's package name 
Click google-services.json 
Move the config file to the app's module (app-level) directory 
You can also add the Google Services Gradle plugin (google-services) to make the values in the config file accessible. 
The Google Services JSON file contains important keys and URLs that should not be leaked as it may tamper with the security of your app. To reduce the chances of tampering, you can store the keys in your app and then delete the Google Services JSON file.

Then run the project
