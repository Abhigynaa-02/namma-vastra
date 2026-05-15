# Namma Vastra

Namma Vastra is a native Android application designed to empower handloom weavers by providing them with a suite of digital tools. Built using Java and Android Studio, the application aims to help weavers track trends, manage their saree gallery, calculate fair pricing, and share their unique craftsmanship stories.

## Features

- **Home Dashboard:** Provides a quick overview of the weaver's profile and quick access to core functionalities.
- **Trend Board:** Displays the latest trending colors and weaving patterns to help weavers stay competitive in the market.
- **Loom Gallery:** Allows weavers to capture, upload, and maintain a digital portfolio of their sarees. Users can seamlessly inquire about available stock directly via WhatsApp.
- **Price Calculator:** A dedicated tool that computes a fair retail price based on yarn cost, saree length, border complexity, and zari work. It provides transparent breakdowns of material and labour costs.
- **Weaver Story:** A profile management section where weavers can establish their digital identity and share their craft's story. It also features historical insights into traditional weaving styles such as Ilkal Silk and Molakalmuru Silk.

## Tech Stack

- **Language:** Java
- **Minimum SDK:** 24
- **Target SDK:** 34
- **Architecture:** MVVM concepts leveraging Jetpack Components
- **Local Storage:** Room Database for persistent storage of gallery items and weaver profiles.
- **UI Components:** AndroidX, Material Design Components (MDC), ConstraintLayout, and Navigation Component.
- **Permissions Required:** Camera, Read External Storage, Read Media Images, and Internet.

## Project Structure

- `db/` - Contains Room Database setup, entities (`WeaverEntity`, `GalleryEntity`), and DAOs.
- `fragments/` - Houses the UI controllers for each primary feature (e.g., `HomeFragment`, `LoomGalleryFragment`, etc.).
- `adapters/` - Contains RecyclerView adapters for lists and galleries.
- `models/` - Data models for dynamic UI rendering.
- `utils/` - Utility classes, including the pricing calculation logic.
- `res/` - Android resources, including layouts, drawables, navigation graphs, and localized strings.

## Build and Installation

The project uses Gradle as its build system. You can compile and run the application either through Android Studio or via the command line using the provided `Makefile`.

### Makefile Targets

- `make build` - Builds the debug APK.
- `make install` - Installs the debug APK onto a connected Android device or emulator.
- `make build-install` - Sequentially builds and installs the application.
- `make clean` - Cleans the project build directories.
- `make lint` - Runs Android Lint to check for structural issues.
- `make run` - Launches the main activity on the connected device.

Example usage:
```bash
make build-install
```

## Setup Instructions

1. Clone the repository or open the source directory in Android Studio.
2. Ensure that your Android SDK is installed. By default, the Makefile expects the SDK at `/home/gk/Android/Sdk`. You can override this by setting the `ANDROID_HOME` environment variable.
3. Sync the project with Gradle files.
4. Run the application on an emulator or physical device.

## License

This project is part of the Namma Platform ecosystem.
