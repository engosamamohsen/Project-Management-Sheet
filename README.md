
# Project Management Bug Reporting System

A modern Android application built with **Jetpack Compose** and **MVVM architecture** for managing bug reports with automatic Excel sheet integration and image uploading capabilities.

## 🚀 Features

- **Dynamic Excel Integration**: Automatically creates and manages headers in Google Sheets
- **Image Upload**: Upload multiple images to ImgBB cloud storage
- **Real-time Progress**: Track upload and submission progress
- **MVVM Architecture**: Clean, maintainable code structure
- **Dynamic Header Management**: Enum-based column management with automatic Excel synchronization
- **Bug List View**: Fetch and display reported bugs directly from Google Sheets on the home screen
- **Demo Video**: Walkthrough available in `assets/video.webm`

## 🏗️ Architecture

This project follows **MVVM (Model-View-ViewModel)** architecture pattern for clean separation of concerns:

### Benefits of MVVM Implementation:
- **Separation of Concerns**: UI, business logic, and data handling are separated
- **Testability**: Each layer can be tested independently
- **Maintainability**: Changes in one layer don't affect others
- **Reusability**: ViewModels and UseCases can be reused across different UI components
- **Lifecycle Awareness**: ViewModels survive configuration changes

## 📋 Dynamic Header Management

### How It Works

The application uses an **enum-based system** for dynamic Excel header creation:

```kotlin
enum class EXCEL_COLUMN {
    TIMESTAMP,
    DESCRIPTION,
    IMAGE_URLS,
    STATUS,
    PRIORITY,
    DEVICE,
    REPORTER,
    CATEGORY,
    NEW_FIELD // ← Add your new column here
}
```

**Headers Auto-Creation**: The system automatically detects new enum values and creates corresponding headers in your Google Sheet.

**Usage in Code**:
```kotlin
excelSheetViewModel.submitBugReport(
    mapOf(
        EXCEL_COLUMN.DESCRIPTION.name to "Bug description",
        EXCEL_COLUMN.NEW_FIELD.name to "New field value"
    )
)
```

---

## ⚙️ Configuration

### 1. Local Properties Setup
Create/update `local.properties` file in your project root:

```properties
# Local configuration - DO NOT commit to version control
sdk.dir=YOUR_ANDROID_SDK_PATH

# ImgBB API Key for image uploading
imgbb.api.key=YOUR_IMGBB_API_KEY

# Google Sheets Spreadsheet ID
google.sheets.spreadsheet.id=YOUR_SPREADSHEET_ID
```

### 2. Google Sheets Setup
- Create a new Google Spreadsheet
- Get Spreadsheet ID from URL:
  ```
  https://docs.google.com/spreadsheets/d/[SPREADSHEET_ID]/edit
  ```
- Create a **Google Service Account** (JSON key file)
- Place `service-account-key.json` inside **`app/src/main/assets/`** (⚠️ don’t commit this file to GitHub)
- Share the spreadsheet with the service account email

### 3. ImgBB Setup
- Register at [ImgBB](https://imgbb.com)
- Get your API key from the API page
- Add it to `local.properties` as `imgbb.api.key`

---

## 🔗 Third-Party Integrations
- **Google Sheets API** → for dynamic Excel sheet management
- **ImgBB API** → for cloud image hosting

---

## 📦 Dependencies Used

```kotlin
// Google Sheets API
implementation("com.google.api-client:google-api-client-android:2.0.0")
implementation("com.google.apis:google-api-services-sheets:v4-rev20220927-2.0.0")
implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
implementation("com.google.api-client:google-api-client-gson:2.0.0")
implementation("com.google.http-client:google-http-client-jackson2:1.42.3")

// HTTP Client & Image Upload
implementation("com.squareup.retrofit2:retrofit:3.0.0")
implementation("com.squareup.retrofit2:converter-gson:3.0.0")
implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

// Dependency Injection
implementation("com.google.dagger:hilt-android:2.57.1")
ksp("com.google.dagger:hilt-compiler:2.57.1")

// UI & Navigation
implementation("androidx.compose.ui:ui")
implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
implementation("androidx.navigation:navigation-compose:2.9.3")

// Image Loading
implementation("io.coil-kt:coil-compose:2.7.0")

// Permissions
implementation("com.google.accompanist:accompanist-permissions:0.37.3")

// Security
implementation("androidx.security:security-crypto:1.1.0")
```

---

## 🛠️ Build Configuration

```kotlin
// In app/build.gradle.kts
android {
    defaultConfig {
        val properties = Properties()
        val localProperties = File(project.rootProject.file("local.properties").path)

        if (localProperties.exists()) {
            properties.load(localProperties.inputStream())
        }

        val imgbbApiKey: String = properties.getProperty("imgbb.api.key") ?: ""
        val excelSheetId: String = properties.getProperty("google.sheets.spreadsheet.id") ?: ""

        buildConfigField("String", "IMGBB_API_KEY", ""$imgbbApiKey"")
        buildConfigField("String", "EXCEL_API_KEY", ""$excelSheetId"")
    }

    buildFeatures {
        buildConfig = true
    }
}
```

---

## 📱 Usage

**Basic Bug Report Flow**
1. Select images from gallery
2. Add description
3. Submit → App automatically:
    - Uploads images to ImgBB
    - Creates/updates Excel headers
    - Submits data to Google Sheets

**Bug List View**
- Home screen fetches bug reports from Google Sheets
- Displays them with details (description, status, priority, images, etc.)

---

## 📹 Demo
You can preview the app in action with the included demo video:

[▶️ Watch Demo](./app/src/main/assets/video.webm)

---

## 🔒 Security Benefits of `local.properties`

- API keys are **not committed** to version control
- Supports different keys per developer
- Easy switching between environments
- Compile-time injection via `BuildConfig`

**Best Practices**:
- ✅ Add `local.properties` to `.gitignore`
- ✅ Use `BuildConfig` for compile-time safety
- ✅ Provide example template for teammates

---

## 🚀 Getting Started

```bash
git clone https://github.com/engosamamohsen/Project-Management-Sheet.git
cd Project-Management-Sheet

# Add your local.properties file
# Add service-account-key.json to app/src/main/assets/
./gradlew build
```

**Example local.properties**:
```properties
sdk.dir=YOUR_ANDROID_SDK_PATH
imgbb.api.key=YOUR_IMGBB_API_KEY
google.sheets.spreadsheet.id=YOUR_SPREADSHEET_ID
```

# ========================================
# PROJECT CONFIGURATION TEMPLATE
# ========================================
# 1. Copy this file to "local.properties"
# 2. Fill in your actual values
# 3. DO NOT commit local.properties to git

# Android SDK Path
sdk.dir=YOUR_ANDROID_SDK_PATH

# ImgBB API Configuration
imgbb.api.key=YOUR_IMGBB_API_KEY

# Google Sheets Configuration
google.sheets.spreadsheet.id=YOUR_SPREADSHEET_ID

# Google Service Account Configuration
# (Get these from your downloaded service account JSON file)
google.service.account.type=service_account
google.service.account.project_id=YOUR_PROJECT_ID
google.service.account.private_key_id=YOUR_PRIVATE_KEY_ID
google.service.account.private_key=-----BEGIN PRIVATE KEY-----\nYOUR_PRIVATE_KEY\n-----END PRIVATE KEY-----\n
google.service.account.client_email=YOUR_SERVICE_ACCOUNT@YOUR_PROJECT.iam.gserviceaccount.com
google.service.account.client_id=YOUR_CLIENT_ID
google.service.account.auth_uri=https://accounts.google.com/o/oauth2/auth
google.service.account.token_uri=https://oauth2.googleapis.com/token
google.service.account.auth_provider_x509_cert_url=https://www.googleapis.com/oauth2/v1/certs
google.service.account.client_x509_cert_url=YOUR_CERT_URL


---

## 📁 Project Structure

```
app/
├── src/main/
│   ├── assets/
│   │   ├── service-account-key.json (Add your Google service account key, DO NOT COMMIT)
│   │   └── video.webm (Demo video)
│   ├── java/com/example/projectmanagement/
│   │   ├── data/
│   │   │   ├── ExcelSheetRepository.kt
│   │   │   └── UploadImagesRepository.kt
│   │   ├── di/
│   │   │   ├── GoogleSheetsService.kt
│   │   │   ├── ApiService.kt
│   │   │   └── RetrofitModule.kt
│   │   ├── domain/bug/
│   │   │   └── ExcelSheetUseCase.kt
│   │   ├── screens/auth/bug/
│   │   │   ├── add/AddBugScreenUI.kt
│   │   │   └── viewModel/ExcelSheetViewModel.kt
│   │   └── model/
│   └── res/
├── build.gradle.kts
└── local.properties (Create this file)
```

---

## 🧪 Testing

```kotlin
// In your ViewModel
fun testConnection() {
    viewModelScope.launch {
        val isConnected = excelSheetUseCase.checkConnection()
        Log.d(TAG, "Google Sheets connection: $isConnected")
    }
}
```

---

## 📝 License
This project is licensed under the **MIT License** – see the LICENSE file for details.
