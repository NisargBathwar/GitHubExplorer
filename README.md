# 🚀 GitHub Explorer

A modern Android application built with **Jetpack Compose** that lets users search GitHub profiles, view detailed user information, and explore public repositories using the GitHub REST API.

---

## 📸 Screenshots

### Home Screen
![Home](screenshots/android%20photo/Screenshot_20260625_114705.png)

### Search Results
![Search](screenshots/android%20photo/Screenshot_20260625_114751.png)

### User Details
![Details](screenshots/android%20photo/Screenshot_20260625_114801.png)

### Repositories
![Repositories](screenshots/android%20photo/Screenshot_20260625_114811.png)

---

## ✨ Features

- 🔍 Search GitHub users by username
- 👤 View detailed GitHub user profiles
- 📂 Browse user repositories
- ♾️ Infinite scrolling (pagination)
- ⚡ Modern Jetpack Compose UI
- 🌙 Material 3 Design
- 🏗️ MVVM Architecture
- 💉 Dependency Injection with Hilt
- 🌐 Retrofit for API communication
- 🖼️ Coil for image loading
- 📱 Responsive UI with loading and error states

---

# 🏛️ Architecture

```
Presentation
     │
 ViewModel
     │
 Repository
     │
 ├── Remote (Retrofit)
 └── Local (Room)
```

Architecture Pattern:

- MVVM
- Repository Pattern
- Clean Separation of Concerns

---

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM Architecture
- Hilt
- Retrofit
- Gson
- Kotlin Coroutines & Flow
- Coil
- Navigation Compose

---

# 📦 Libraries

- Retrofit
- Hilt
- Coil
- Navigation Compose
- Material 3

---

# 📂 Project Structure

```
app
│
├── data
│   ├── local
│   ├── repository
│
├── domain
│   ├── model
│
├── di
│
├── presentation
│   ├── ui
│   ├── viewmodel
│
└── MainActivity
```

---

# 🔑 GitHub API Setup

Create a Personal Access Token from GitHub.

Create a `local.properties` file (or update the existing one):

```properties
GITHUB_TOKEN=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
```

Sync the project and run.

---

# 🚀 Installation

```bash
git clone https://github.com/yourusername/GitHubExplorer.git
```

Open the project in Android Studio.

Add your GitHub Personal Access Token.

Run the application.

---

# 👨‍💻 Author

**Nisarg Bathwar**

- GitHub: https://github.com/yourusername
- LinkedIn: https://linkedin.com/in/yourprofile

---

## ⭐ If you found this project useful, consider giving it a star!
