# Gakusei-Kanji-Client
A simple client to demonstrate usage of the kanji api of the gakusei project.
*This application is merely a demonstration of the `kanji API` of the gakusei project. In order to make a complete application that can keep track of the user and persist the data, more APIs need to be used. Below is a walkthrough of the necessary steps to build a complete kanji application.*

### How to make a complete kanji application

To make a complete kanji application, you need to use some of the APIs that the gakusei project provides in addition to what this application demonstrates.
First, you need to use the login API which is documented in another example application and can be found under `doc/examples/gakusei-client` in the gakusei project. The benefits of using the login API are:
* Restrict usage to only registered and logged in users.
* User credentials are necessary to fully make use of all APIs in the gakusei project.
* Log a user related event.
* Persist and keep track of user progress.

After you have implemented the login part of your application and fetch a question, you may want to display the kanji sign. To do that, you need to fetch the svg file from the server. The url endpoint for the svg files is `img/kanji/kanjivg/0{signInHexCharcode}.svg` where {signInHexCharcode} is the japanese sign to be shown, converted to hex charcode. E.g.:
* `https://staging.daigaku.se/img/kanji/kanjivg/0828b.svg` where `828b`is the hex charcode representation of the japanese sign (kanji) that is viewed.

In addition to persisting the kanji drawing made by the user, which is demonstrated in the Gakusei-Kanji-Client, you may want to persist the user's answers and log events as well, which are done by using the `event-controller API`. Refer to the swagger API documentation on the gakusei website to see how you can use the `event-controller API` to persist the user's answers and log events. 

If you wish to use the spaced repetition mode in your application, you can set the parameter `spacedRepetition` to 'true' when fetching questions from the gakusei application. It is set to 'false' by default. The retention can be updated with the `event-controller API` when persisting a user answer.