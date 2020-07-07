# Top Stories Ticker

<image align="right" src="https://github.com/kaushiknsanji/TopStoriesTicker/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="25%"/>

![GitHub](https://img.shields.io/github/license/kaushiknsanji/TopStoriesTicker)  ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/kaushiknsanji/TopStoriesTicker)  ![GitHub repo size](https://img.shields.io/github/repo-size/kaushiknsanji/TopStoriesTicker)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/kaushiknsanji/TopStoriesTicker)  ![GitHub All Releases](https://img.shields.io/github/downloads/kaushiknsanji/TopStoriesTicker/total) ![GitHub search hit counter](https://img.shields.io/github/search/kaushiknsanji/TopStoriesTicker/News%20Ticker%20App) ![Minimum API level](https://img.shields.io/badge/API-21+-yellow)

This App has been developed as part of the **[30 Days of Kotlin with Google Developers](https://eventsonair.withgoogle.com/events/kotlin)**. App simulates a News Ticker. Its source is the [Guardian News API](https://open-platform.theguardian.com/documentation/) which loads only the Editor-Picked News Items one-by-one (ticker).

---

## App Compatibility

Android device running with Android OS 5.0 (API Level 21) or above. Best experienced on Android Nougat 7.1 and above. Designed for Phones and NOT for Tablets.

---

## Getting Started

* Register for the Developer API Key from the [Guardian Open Platform](https://open-platform.theguardian.com/access/). Registration is free of cost.
* Create a Properties File named **credentials.properties** in the Project's root folder.
* Define a property named **GUARDIAN_API_KEY_VAL** and assign it the value of the API Key obtained from the Registration process.
* If the above API Key is not defined, then the App will use the default **"test"** API Key which is heavily rate-limited. Whenever the rate-limit is hit, the app may crash or not display proper results.
* The Developer API Key is also rate-limited, but not as limited as the default **"test"** API Key.

---

## Video Preview

[![Video of Complete App Flow](https://i.ytimg.com/vi/LD0MLimm2_8/maxresdefault.jpg)](https://youtu.be/LD0MLimm2_8)

---

## Built With

* [Kotlin](https://kotlinlang.org/)
	* [Android KTX](https://developer.android.com/kotlin/ktx)
	* Sealed Classes, Data Classes
	* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and Coroutines Flow
	* Scope functions
	* Extensions
	* Singleton Objects, Companion Objects
	* Higher Order functions
	* Safe access, Type cast, Safe cast
	* Inline functions
	* Annotations
	* Generics
	* Late Initialization
	* String templates
* [Constraint Layout library](https://developer.android.com/training/constraint-layout/index.html) to make layouts for News Article Items.
* [Material Design library for Card](https://material.io/develop/android/components/cards/) layouts of News Articles Items.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to maintain the Activity's state, handle user actions and communicate updates done to the Repository.
* [Dagger](https://github.com/google/dagger) for dependency injection of required services to the clients.
* [Glide](https://bumptech.github.io/glide/) to load Images.
* [Retrofit](https://square.github.io/retrofit/) and [OkHttp](https://square.github.io/okhttp/) to communicate with the [Guardian News API](https://open-platform.theguardian.com/documentation/) via REST calls.
* [GSON](https://github.com/google/gson) to deserialize JSON to Java Objects.

### Architecture

* App follows **MVVM with Repository pattern** as proposed by **MindOrks** in their [course](https://mindorks.com/android-app-development-online-course-for-professionals).
* Coroutines Flow has been used to emit the Articles one at a time to simulate a News ticker. Exceptions are handled using its `CoroutineExceptionHandler`, `onCompletion` block and `onCatch` block. Flow is switched to work in IO Dispatcher to avoid blocking the UI.
* **Top Stories** news feed that shows a list of **Editor-Picked News** Items is retrieved from the [International](http://content.guardianapis.com/international) endpoint. Content is only affected by the start date (`from-date` query parameter) of the News feed mentioned in the [Request URL](https://content.guardianapis.com/international?show-editors-picks=true&from-date=2020-06-01&show-fields=trailText,byline,thumbnail&api-key=test).

---

## Branches in this Repository

* **[master](https://github.com/kaushiknsanji/TopStoriesTicker)**
	* This is the main branch.
	* Contains the code submitted for the event. This is till and including this [commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/2df0f61d8c4ccb5332668c3dae460f286dd9da78).
	* Following fixes were added after submission -
		* Expanding/Collapsing the News Article Item - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/220050d88f83c5a28f05fb8d6d92d062240e8cbe) and [commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/98a785686cff5b0db31cf4c0b782036c69cf36a5))
		* Download progress indication - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/4af0900897d671d7f90675833aab5bf70f61bdca))
		* Checking Network Connectivity - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/838402c41aeb2bb75de90487d0c69c89326bd105))
		* Restore RecyclerView Scroll position - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/fb2980f9a3a8f59d44aa03703c5c6b718f7f2c19))
	* Updates from other branches merged into this.
* **[release_v1.0](https://github.com/kaushiknsanji/TopStoriesTicker/tree/release_v1.0)**
	* Bumped versions of dependencies to their latest version and tested - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/5db4e0951d4501b778ab2b353337dce1c2a20b85))
	* Added Proguard Rules to keep the Model classes "AS IS" - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/1a9c5f72bcaaf648445122f2e4183f16c1dd0878))
	* Other minor changes to prepare the app for release - ([commit](https://github.com/kaushiknsanji/TopStoriesTicker/commit/1a9c5f72bcaaf648445122f2e4183f16c1dd0878))

---

## Icon credits

App Icon is from [Icons8](https://icons8.com).

---

## Participation Certificate

<a href="https://drive.google.com/file/d/1cwojVFygUYmzjzxEaKpOtqydMwLKThyA/view?usp=sharing">
<img alt="Participation Certificate" src="https://github.com/kaushiknsanji/TopStoriesTicker/blob/master/artwork/30_Days_of_Kotlin.png" width="50%">
</a>

---

## License

```
Copyright 2020 Kaushik N. Sanji

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
   
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
