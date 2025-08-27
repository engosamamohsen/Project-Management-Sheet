# Project Management Bug Reporting System

A modern Android application built with **Jetpack Compose** and **MVVM architecture** for managing bug reports with **Google Sheets integration** and **cloud image uploading**.

---

## 🚀 Features

- **Dynamic Excel Integration**: Automatically creates and manages headers in Google Sheets.
- **Image Upload**: Upload single or multiple images to ImgBB cloud storage.
- **Image Sharing Integration**: Receive images from other apps via Android's share mechanism.
- **Real-time Progress**: Track upload and submission progress.
- **Bug List View**: Fetch and display reported bugs directly from Google Sheets.
- **MVVM Architecture**: Clean, maintainable, testable, and lifecycle-aware.
- **Dynamic Header Management**: Enum-based column system with auto Excel synchronization.
- **Demo Video**: Walkthrough included in `assets/video.webm`.

---

### Network Flow

1. **Configuration** → `NetworkConfigImpl` loads credentials from BuildConfig
2. **Service Creation** → `AppModule` provides configured network services
3. **Repository** → `NetworkRepository` abstracts network operations
4. **API Calls** → Services handle Google Sheets and ImgBB operations
5. **Data Mapping** → Models structure data for UI consumption

### Network Structure Documentation
1. **Shows your actual file organization** based on the code excerpts
2. **Explains the purpose** of each package and file
3. **Highlights key architectural decisions** (Repository pattern, DI, etc.)
4. **Demonstrates the network flow** from configuration to API calls
5. **Provides usage examples** for developers

### Network Components

**🔧 Dependency Injection (`di/`)**
- `AppModule.kt` - Main DI module providing network dependencies (Retrofit, OkHttp, Google Sheets)

**🌐 API Layer (`network/api/`)**
- `ApiService.kt` - Retrofit interface for ImgBB image upload API

**⚙️ Configuration (`network/config/`)**
- `NetworkConfig.kt` - Interface for network configuration
- `NetworkConfigImpl.kt` - Implementation using BuildConfig for secure credential management

**🛠️ Services (`network/di/`)**
- `GoogleSheetsService.kt` - Google Sheets API integration with credential management
- Handles sheet creation, header management, and data operations

**📋 Models (`network/model/`)**
- `BugReport.kt` - Data class for bug report structure with Excel mapping

**🗂️ Repository (`network/repository/`)**
- `NetworkRepository.kt` - Repository pattern abstracting network operations
- `GoogleSheetsRepository.kt` - Google Sheets specific repository implementation


## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** for clear separation of concerns.

### Benefits
- ✅ Separation of UI, business logic, and data layers
- ✅ Testable components
- ✅ Maintainable & extendable code
- ✅ Lifecycle awareness
- ✅ Reusable ViewModels & UseCases

---

## 📸 Image Sharing Integration

The app supports receiving images from other applications through Android's built-in **sharing intent system**, making bug reporting seamless.

### Supported Sources
- Gallery / Photos (single or multiple images)
- Camera (share photos immediately after capture)
- Screenshots
- WhatsApp / Messenger
- Chrome & other browsers
- File managers
- Any app with image sharing support

### Workflow
1. Select an image in any app (Gallery, Camera, WhatsApp, etc.).
2. Tap **Share** → Select **Project Management**.
3. App opens the **Bug Submission** screen with the shared image(s) pre-loaded.

### Technical Implementation
```xml
<!-- AndroidManifest.xml - Intent filters for image sharing -->
<intent-filter>
    <action android:name="android.intent.action.SEND" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="image/*" />
</intent-filter>

<intent-filter>
    <action android:name="android.intent.action.SEND_MULTIPLE" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="image/*" />
</intent-filter>
```

### Key Features
- ✅ Single image sharing (`ACTION_SEND`)
- ✅ Multiple image sharing (`ACTION_SEND_MULTIPLE`)
- ✅ Works whether app is closed or running
- ✅ Automatic navigation to bug submission screen
- ✅ Toast notifications for user feedback
- ✅ State management across navigation

### Usage Examples
- **Quick Bug Report from Screenshot**
    - Take a screenshot → Share → Project Management → Add description → Submit
- **Multiple Images**
    - Select multiple images in Gallery → Share → Project Management → Pre-loaded in bug form

---

## 📋 Dynamic Header Management

The app uses an **enum-based system** for Google Sheets headers:

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
    NEW_FIELD // ← Add new column here
}
```

New columns are auto-created in Google Sheets when added to the enum.

---

## ⚙️ Configuration

### 1. Local Properties Setup
Create a `local.properties` file:


```properties
# Android SDK Location
sdk.dir=YOUR_ANDROID_SDK_PATH

# ImgBB API Configuration
imgbb.api.key=YOUR_IMGBB_API_KEY

# Google Sheets Configuration
google.sheets.spreadsheet.id=YOUR_SPREADSHEET_ID

# Google Service Account Configuration (from your JSON file)
google.service.account.type=service_account
google.service.account.project_id=YOUR_PROJECT_ID
google.service.account.private_key_id=YOUR_PRIVATE_KEY_ID
google.service.account.private_key=-----BEGIN PRIVATE KEY-----\nYOUR_PRIVATE_KEY_CONTENT\n-----END PRIVATE KEY-----\n
google.service.account.client_email=YOUR_SERVICE_ACCOUNT_EMAIL@YOUR_PROJECT.iam.gserviceaccount.com
google.service.account.client_id=YOUR_CLIENT_ID
google.service.account.auth_uri=https://accounts.google.com/o/oauth2/auth
google.service.account.token_uri=https://oauth2.googleapis.com/token
google.service.account.auth_provider_x509_cert_url=https://www.googleapis.com/oauth2/v1/certs
google.service.account.client_x509_cert_url=https://www.googleapis.com/robot/v1/metadata/x509/YOUR_SERVICE_ACCOUNT_EMAIL%40YOUR_PROJECT.iam.gserviceaccount.com
```

### 2. Getting Google Sheets Spreadsheet ID
- Go to Google Sheets in `https://docs.google.com/spreadsheets/u/0/`
- Click "+ Blank" to create a new spreadsheet
- Give your spreadsheet a meaningful name (e.g., "Bug Reports - Project Management")
- Extract the Spreadsheet ID
  - Look at the URL of your spreadsheet:
    https://docs.google.com/spreadsheets/d/17xQ9xMggCMVXSmO2Ww9EBu1fBIXQG4G8tsTlELCXD9Q/edit#gid=0
  - The Spreadsheet ID is the long string between /d/ and /edit:
    17xQ9xMggCMVXSmO2Ww9EBu1fBIXQG4G8tsTlELCXD9Q
  - Copy this ID and add it to your local.properties:
    google.sheets.spreadsheet.id=17xQ9xMggCMVXSmO2Ww9EBu1fBIXQG4G8tsTlELCXD9Q

### 2. Google Sheets Setup
- Go to Google Cloud Console `https://console.cloud.google.com/`
- Create a new project or select existing one
- Enable Google Sheets API:
  - Go to APIs & Services → Library
  - Search for "Google Sheets API"
  - Click Enable
- Click Create and Continue
- Skip role assignment (click Continue)
- Click Done

### 3. Generate JSON Key
- Find your service account in the credentials list
- Click on the service account email
- Go to Keys tab
- Click Add Key → Create New Key
- Select JSON format
- Click Create - JSON file will download
  - "client_email": "EMAIL_SHARE_WITH_GOOGLE_SHEETS", // → google.service.account.client_email will be share into excel sheet

### 4. Generate JSON Key
- Open your Google Spreadsheet
- Click Share (top-right corner)
- Add the service account email as an editor:"EMAIL_SHARE_WITH_GOOGLE_SHEETS"
- Set permission to Editor
- Click Send


### 5. ImgBB Setup
- Register at [ImgBB](https://imgbb.com)
- Add API key to `local.properties`

---

## 🔗 Third-Party Integrations
- **Google Sheets API** → for dynamic Excel sheet management
- **ImgBB API** → for cloud image hosting

---

## 📦 Dependencies

```kotlin
// Google Sheets
implementation("com.google.api-client:google-api-client-android:2.0.0")
implementation("com.google.apis:google-api-services-sheets:v4-rev20220927-2.0.0")
implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
implementation("com.google.api-client:google-api-client-gson:2.0.0")

// Retrofit & OkHttp
implementation("com.squareup.retrofit2:retrofit:3.0.0")
implementation("com.squareup.retrofit2:converter-gson:3.0.0")
implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

// Dependency Injection
implementation("com.google.dagger:hilt-android:2.57.1")
ksp("com.google.dagger:hilt-compiler:2.57.1")

// Compose & Navigation
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

## 📱 Usage Flow

1. Select or share image(s)
2. Add bug description
3. Submit → App automatically:
    - Uploads images to ImgBB
    - Creates/updates headers in Google Sheets
    - Submits bug data

### Bug List View
- Home screen fetches bugs from Google Sheets
- Displays details: description, status, priority, images, etc.

---

## 📹 Demo
▶️ [Watch Demo](./app/src/main/assets/video.webm)

---

## 🛠️ Build Config

```kotlin
android {
    defaultConfig {
        val properties = Properties()
        val localProperties = File(project.rootProject.file("local.properties").path)
        if (localProperties.exists()) {
            properties.load(localProperties.inputStream())
        }

        val imgbbApiKey: String = properties.getProperty("imgbb.api.key") ?: ""
        val excelSheetId: String = properties.getProperty("google.sheets.spreadsheet.id") ?: ""

        buildConfigField("String", "IMGBB_API_KEY", "\"$imgbbApiKey\"")
        buildConfigField("String", "EXCEL_API_KEY", "\"$excelSheetId\"")
    }

    buildFeatures {
        buildConfig = true
    }
}
```

---

## 📁 Project Structure

```
app/
├── src/main/
│   ├── assets/
│   │   ├── service-account-key.json (DO NOT COMMIT)
│   │   └── video.webm (Demo video)
│   ├── java/com/example/projectmanagement/
│   │   ├── data/ (repositories)
│   │   ├── di/ (dependency injection)
│   │   ├── domain/bug/ (use cases)
│   │   ├── screens/auth/bug/ (UI + ViewModel)
│   │   └── model/
│   └── res/
├── build.gradle.kts
└── local.properties (local config)
```

---

## 🧪 Testing

```kotlin
fun testConnection() {
    viewModelScope.launch {
        val isConnected = excelSheetUseCase.checkConnection()
        Log.d(TAG, "Google Sheets connection: $isConnected")
    }
}
```

---

## 🔒 Security Best Practices
- Never commit `local.properties` or JSON keys
- Use `BuildConfig` for API key injection
- Provide `.gitignore` template
- Different keys per environment

---

## 📝 License
This project is licensed under the **MIT License**.

---
