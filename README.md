# Challenge Interview for Adjoe 

The following is the description for the presented challenge and the repository with the solution.

## Task: Java android is your friend - 2h task

Please install on your computer: Android studio and an Android emulator Please write Java code (no Kotlin). The app should be able to run on every device from Android 5.0 on.

### Part 1. Native android

  - 1.1 Create an activity which loads data from this URL https://jsonplaceholder.typicode.com/albums (Please do not use libraries like Retrofit, OkHttp or GSON for the network code, only classes from the java.*, android.* or org.json.* packages.)
  - 1.2 Find a way to render the album titles sorted by ID into a view, which is scrollable (even if they are already sorted) . You can use libraries like RecyclerView for this.

### Part 2. Services
  
  - 2.1 Build a BroadcastReceiver that listens to the Intent with the action USER_PRESENT. Please note: there is no need to create an Intent with action USER_PRESENT, please use the existing one.
  - 2.2 Implement a background task (for example: JobScheduler or background service) that runs periodically
  - 2.3 Every time the background task runs, create a push notification that shows the user how long the phone was active since the Intent was triggered (the time should be updated every few seconds)
  
## Solution

Given the constraint in time and requirement some tradeoff need it to be made across the application development the most obvious is the usage of ExecutorService with a solely purpose of handling *HttpRequest* on a real application this type of solution would require a more sophisticated mechanism of cancel/trigger/retry that is currently missing. Another aspect of the code that is obviously missing is some string have been hardcoded including the service url, the timing for notification and some other ID message that may live better within the scope of the gradle file rather, where they can be tweak per flavor. 

Overall the time required for this exercise was a total of **3 HOURS** an aditional hour was included in order to finish part 2 the notification section.
