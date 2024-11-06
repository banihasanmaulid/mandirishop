# 👜 Mandiri Store

This is an **Android Product Online Shopping Application** designed to provide users with an intuitive experience for browsing and purchasing products. Built using **Java**, this app incorporates best practices in **Clean Architecture**, **MVVM Design Pattern**, and **Dependency Injection** with **Dagger 2**. The app also includes **Retrofit** for network requests and **Room** for local data persistence.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Usage](#usage)
- [Code Structure](#code-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Product List**: View a list of products fetched from a [FakeStore API](https://fakestoreapi.com).
- **Product Detail**: View detailed information about a selected product.
- **Add to Cart**: Users can add items to their cart for future checkout.
- **Cart Management**: Update item quantities and remove items from the cart.
- **Checkout**: View the total items and price, and proceed with checkout.
- **Offline Support**: Data is cached locally using Room Database for offline access.

## Tech Stack

- **Language**: Java
- **API**: [FakeStore API](https://fakestoreapi.com) (for simulated data)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Network**: Retrofit for API calls
- **Dependency Injection**: Dagger 2
- **Local Database**: Room for offline data persistence
- **Other**: Glide for image loading, LiveData for reactive programming

## Installation

### Prerequisites

- **Android Studio** - Ensure you have the latest version installed.
- **Java 8** - This app uses Java 8 features, so make sure your project is configured correctly.

### Steps

1. **Clone the repository**:

   ```bash
   git clone https://github.com/banihasanmaulid/mandirishop.git

2. **Open the project in Android Studio.**
3. **Sync dependencies by allowing Gradle to resolve all dependencies. Dagger, Retrofit, and Room dependencies are managed in the** build.gradle **files.**
4. **Run the app on an Android emulator or a physical device**

### Usage

*Upon launching the app:*
1. **Browse Products - View a list of products retrieved from the FakeStore API.**
2. **Select a Product - Tap on any product to view detailed information.**
3. **Add to Cart - Use the "Add to Cart" button to add the product to your shopping cart.**
4. **Manage Cart - Go to the cart section to update quantities or remove items.**
5. **Checkout - Tap on "Checkout" to view the total price and complete the purchase.**






