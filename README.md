# Multi-module Project Movie App

| Module name        | Type                 | Description                                                      |
| -------------      | -------------        | -------------                                                    |
| [app](/app/)                | Android Application  | MainActivity, BaseApplication, Theme, Hilt setup.                |
| [core](/core/)               | Java/Kotlin Library  | Core business models and classes.                                |
| [datasource](/movie/datasource/)    | Java/Kotlin Library  | Data-sources (network and cache) for the movie Module.            |
| [datasource-test](/movie/datasource-test/)    | Java/Kotlin Library  | Data-source test fakes.            |
| [domain  ](/movie/domain/)        | Java/Kotlin Library  | Domain models and classes for the movie Module.                   |
| [interactors ](/movie/interactors/)   | Java/Kotlin Library  | Use-cases for the movie Module.                                   |
| [ui-list](/movie/ui-list/)        | Android Library      | UI components for the MovieList screen.                           |
| [ui-detail](/movie/ui-detail/)      | Android Library      | UI components for the MovieDetail screen.                         | |
| [components](/components/)         | Android Library      | Common Composables.                                              |
