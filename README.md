# Farm App

## Overview

Farm App is a basic application designed to demonstrate the principles of clean architecture. The app features a well-structured codebase with distinct layers: domain, data, and UI. It utilizes Dagger Hilt for dependency injection and Room for local database management.

## Features

- Register farmers along with their crop types.
- View a list of all registered farmers.

## Architecture

The application is structured in three main layers:

1. **Domain Layer**: Contains business logic and application rules. It defines use cases and models.
2. **Data Layer**: Manages data sources, including Room database and any remote data sources.
3. **UI Layer**: Responsible for presenting data to the user and handling user interactions.

## Technologies Used

- **Kotlin**: Programming language for Android development.
- **Dagger Hilt**: Dependency injection framework for Android.
- **Room**: SQLite database for local data storage.
- **Coroutines**: For asynchronous programming.

## Tests Covered

The application includes unit tests to ensure the functionality of critical components:

- **Validation Tests**: Verifying that input validation logic for farmer registration works correctly.
- **ViewModel Tests**: Testing the behavior of the `FarmersViewModel` in response to various states and user inputs.
- **Interactor Tests**: Ensuring that the `RegisterFarmerInteractor` correctly validates farmer registration data and integrates with the repository.

## Getting Started

### Prerequisites

- Android Studio
- Kotlin SDK
- Gradle

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/jayhymn/farmapp.git
