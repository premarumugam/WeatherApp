# WeatherApp
Weather 

![Potrait Mode](https://github.com/premarumugam/WeatherApp/blob/master/Weather_potrait.jpeg)
![Landscape Mode](https://github.com/premarumugam/WeatherApp/blob/master/Weather_Landscape.jpeg)


# Libraries used:

*	RxAndroid

*	Retrofit

*	OkHttp

*	Dagger2

*	Mockito

*	Butter knife



# About Project :
The project is build using MVP pattern where the presenters are completely tested using Mockito libraires. Butterknife is used in this project to lnject the UI views into the fragment / activity. The classes for making service calls / presenters are injected by Dagger 2; dependency injection framework. 

Retrofit with Rx is used for making service calls and observable pattern is used for rendering the UI or presenting error in the fragment.
The application works in offline mode by leveraging file cache available in okHttp libraries.The app is completely coverd by Unit test cases by making use of Mockito library.

## Development:
Download the repo :
git clone https://github.com/premarumugam/WeatherApp.git

File -> open -> Android studio project

Build test cases:
./gradlew build

The application is build using Java8.
