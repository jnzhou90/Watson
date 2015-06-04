# Find Your Love With Watson

To Use
================================================================================

```
cf create-service cloudantNoSQLDB Shared talent-manager-db
cf create-service personality_insights "IBM Watson Personality Insights Monthly Plan" personality-insights-talent-manager
cf push myappname
```

Replace myapp name with the name of your app (ex. findyourlove)

or click the button below

[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy)

# About
## Why Create This APP
Tradtional love app can not answer the question:
"what will happen if there is a love app which can help you find a person who have the same personality characteristics,need,and values with you besindes his outlooking?"
* Personality characteristics describing how a person engages with the world.
* Needs describe which aspects of a product will resonate with a person.
* Values describe motivating factors that influence a person's decision making.

> If you are a single boy, you want to find a person who is beatuful, have sex body and so on. But besides these outlooking, you really want find a girl who have similer personality characteristics, needs and values with you. 
> The same to girls who want to find her future real love, because 物以类聚，人以群分.

## Idea / Solution
* Social Data: Visualizes emotional properties in tweets
* Machine learning trains language models :uses linguistic analytics to infer individuals' intrinsic personality characteristics from digital communications that they make available.
**“he is a greeableness person”
**"she is taking pleasure in life"
Displays Rank Result Persons Matching Your Personal Insights
Our Phase 1 prototype is WORKING!

# Architecture 
TODO

# Technologies
## Watson APIs
 Personality Insights API
* Rerieve personality traits
..* POST `/v2/profile`

## Cloudant
NoSQL DB Store Socail Data

## Bluemix
* [Liberty for Java Profile](https://ace.ng.bluemix.net/#/store/cloudOEPaneId=store&appTemplateGuid=javawebstarter)

## Other
* Angular.js

## Pre-req's
* [Install the Eclipse EE](https://ibm.biz/hackathon-eclipse).
* [Install Java 1.7 JDK](https://ibm.biz/hackathon-java).
* [Install the cf command-line tool](https://ibm.biz/hackathon-cf).
* [Sign up for an IBM Bluemix account](http://bluemix.net).

