# Table of contents
1. [GraphQL?](#why-graphql)
   - [Android integration](#android-integration)
     - [Apollo Kotlin](#apollo-kotlin)
     - [Apollo vs Retrofit](#apollo-vs-retrofit)
   - [Is your project ready for the change? (In case of the business needs it, and Backend is ready)](#is-your-project-ready-for-the-change-in-case-of-the-business-needs-it-and-backend-is-ready)
   - [GraphQLZero](#graphqlzero)
   - [Testing](#testing)
2. [Paging Library (Paging 3)](#paging-library-paging-3)
   - [How to test it?](#how-to-test-it)

# GraphQL?
GraphQL is a query language for APIs and a runtime for fulfilling those queries with your existing data. GraphQL provides a complete and understandable description of the data in your API, gives clients the power to ask for exactly what they need and nothing more, makes it easier to evolve APIs over time, and enables powerful developer tools.

For more info, please visit: https://graphql.org/

# Android integration

## Apollo Kotlin
https://github.com/apollographql/apollo-kotlin

Apollo Kotlin (formerly known as Apollo Android) is a GraphQL client that generates Kotlin and Java models from GraphQL queries.

Apollo Kotlin executes queries and mutations against a GraphQL server and returns results as query-specific Kotlin types. This means you don't have to deal with parsing JSON, or passing around Maps and making clients cast values to the right type manually. You also don't have to write model types yourself, because these are generated from the GraphQL definitions your UI uses.

[Apollo GraphQL](https://www.apollographql.com/) is not just a library to use for the Android integration part. They have a platform that offers many libraries for most of technologies starting from Backend to all clients.

## Apollo vs Retrofit
They are totally different. As you all know, Retrofit is a type-safe HTTP client for Android and Java and Apollo is a GraphQl client for Android (in our case).

Retrofit's integration is built mainly on the Façade design pattern, you write a function in some interface to get the data you expect, and usually convert the responses using gsonConverterFactory. Quite easy, isn't it?

For Apollo it's quite different, you need to define the schema.json file (don't worry, you only need to extract it from the server using 1 command :D) to let it know about the data models, queries, mutations, and subscriptions.
Query? It replaces the GET request in REST.
Mutation? It replaces all of other REST requests.
Subscription? This makes the difference, better to read more about it -> https://www.apollographql.com/docs/react/data/subscriptions/
After defining the schema, you need to write every query, mutation, or subscription you'll call. It generates all of the models for you ;) But wait, don't be so happy! Those models need some adjustment, so it's better to map them to your models.

So the change is not so difficult, it's just more about your project itself. Is it ready?

## Is your project ready for the change? (In case of the business needs it, and Backend is ready)
In case your project is not modularized, you're not safe. So before considering this, I'd suggest to start modularizing your project as soon as possible (for many reasons, not only GraphQL).
If your project is written in modules. Do you have separate modules for the networking? If yes, cool.. you're safe! Check this [module](https://github.com/MohamedGamalElsayed/graphql-zero/tree/master/zerographql) and you'll understand that it's not a big deal.

# GraphQLZero
GraphQLZero is powered by JSONPlaceholder and serves the same dataset in the form of a GraphQL API. Six different types of entities exist: users, posts, comments, todos, albums, and photos. These entities are also related to each other; for example, a user has many posts, a photo belongs to an album, etc.

Don't worry, it's for free :D You can learn, and integrate using it. For more info: https://graphqlzero.almansi.me/#example-top

# Testing
This is a 2-parts topic. 

The first one is about your architecture itself. If it's good, it's easy! For example in this sample I had only to test this [method](https://github.com/MohamedGamalElsayed/graphql-zero/blob/master/zerographql/src/main/java/sample/mohamed/zerographql/data/base/ApolloCallHelper.kt) because I rebased all of my services on it, which means that I didn't need to test a service like [this](https://github.com/MohamedGamalElsayed/graphql-zero/blob/master/zerographql/src/main/java/sample/mohamed/zerographql/service/PostServices.kt). 

The second part, if you wanna go for more, you can check Apollo Test builders: https://www.apollographql.com/docs/kotlin/testing/test-builders/

# Paging Library (Paging 3)
Paging 3 is one if the best libraries for pagination — how not if it gives you all what you need to implement paging with doing the lowest efforts not only, but also getting your data from different data sources (api/local database).

## How to test it?
Easy, you'll find the [integration](https://github.com/MohamedGamalElsayed/graphql-zero/blob/master/app/src/main/java/sample/mohamed/newsfeed/features/timeline/posts/network/PostsPagingSource.kt) and the [unit tests](https://github.com/MohamedGamalElsayed/graphql-zero/blob/master/app/src/test/java/sample/mohamed/newsfeed/features/timeline/posts/network/PostsPagingSourceTest.kt) here in this sample, and don't forget to read my article about this topic:
https://medium.com/@mohamed.gamal.elsayed/android-how-to-test-paging-3-pagingsource-433251ade028

### Because you read all the content, you deserve this gem.. 
Check Espresso UI Tests, using [Robot Pattern](https://github.com/MohamedGamalElsayed/graphql-zero/tree/master/app/src/androidTest/java/sample/mohamed/newsfeed/features/timeline/posts), it really makes life much easier!

