# RateMyTreater
Optimal candy collection!
## Inspiration
Have you ever wondered whether you should bother knocking on that one door for Halloween candy? Is Old Man Jenkins giving out floss again this year? As children, this was the only thing that bothered us in Halloween. However, with "RateMyTreater" you can now avoid wasting time knocking on doors of people who are pretending they aren't at home and you can instead spend more time knocking on the houses with candy and loot!  
## What it does
What this app does is it first finds your location, with your permission, by using your wi-fi or cell service to pinpoint where you are. If one is not connected to the internet, then their last recorded location will be used. After finding you, the app displays nearby houses on your street by using your coordinates to go through the OpenDataCage API and database. Here, you can select any house and find out what treats they're giving out and what review they have based of the information provided by other users. In addition, you can also share the love by contributing your own information on treats. 
## How we built it
This app was built in android studio. Using android native functions, the app tracks the user's location (with permission) by first using the user's wifi to estimate the location of the user. If that doesn't work, the app uses cell phone towers to estimate the user's location. If that still doesn't work, the app will refer back to the user's previous location. The app then enters the user's coordinates into the OpenCageData API and returns a JSON file with nearby houses. This JSON file is then read and manipulated into displaying the closest homes on the user's street. Using FireBase, we then store any of the information inputted by the users. This database is then used to retrieve information to display to other users of the app. Information includes what treats are available and what rating people gave the house.
## Challenges we ran into
The first challenge we ran into was displaying the information provided by the JSON file from the OpenCageData database. This was a problem as Android Studio doesn't allow the use of IO. This was then solved by reading and processing the information in the "background" of Android Studio by doing it under a class that extended the Async class. Other challenges we ran into were displaying everything in a card view, retrieving information from the FireBase database, and displaying images onto the card view.
## Accomplishments that we're proud of
One accomplishment we're proud is being able to effectively use FireBase in order to store our information and retrieve it when needed. Another major accomplishment was being able to display all the needed information and images onto the card views for a cleaner look. In addition to this, we're also proud of being able to use android studio to pinpoint the user's location and retrieve information from APIs online. However, our biggest accomplishment that we are proud of is being able to make an app of this complexity in under 12 hours. 
## What we learned
What we learned today was how to use FireBase and display information in a card view. In addition, we also learned to read information online in a different way and to track the user's location.
## What's next for RateMyTreater
Improvements that are planned for this includes finding houses in a certain radius as indicated by the user, improve the use of photos in this app, and to be able to let the user provide more information on the treats and give more descriptive reviews.

CREDIT
Look at the awesome logo I made at: <a href='https://onlinelogomaker.com' title='Online Logo Maker'>onlinelogomaker.com</a>
