# Top Stories Ticker

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
