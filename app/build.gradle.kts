
import java.util.Properties
import java.io.File
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize") // needed only for non-primitive classes

    id("kotlin-kapt") // Use id("kotlin-kapt") instead of kotlin("kapt")
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.google.services) // Add this line

}

android {
    namespace = "com.example.projectmanagement"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.projectmanagement"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        val properties = Properties()
        val localProperties = File(project.rootProject.file("local.properties").path)

        // Load the local.properties file if it exists
        if (localProperties.exists()) {
            properties.load(localProperties.inputStream())
        }



        // Use the safe-call operator (`?.`) to get the key and provide a default empty string if it's missing
        val imgbbApiKey: String = properties.getProperty("imgbb.api.key") ?: ""
        val excelSheetIdApiKey: String = properties.getProperty("google.sheets.spreadsheet.id") ?: ""

        // Service Account Configuration
        val serviceAccountType: String = properties.getProperty("google.service.account.type") ?: ""
        val serviceAccountProjectId: String = properties.getProperty("google.service.account.project_id") ?: ""
        val serviceAccountPrivateKeyId: String = properties.getProperty("google.service.account.private_key_id") ?: ""
        val serviceAccountPrivateKey: String = properties.getProperty("google.service.account.private_key") ?: ""
        val serviceAccountClientEmail: String = properties.getProperty("google.service.account.client_email") ?: ""
        val serviceAccountClientId: String = properties.getProperty("google.service.account.client_id") ?: ""
        val serviceAccountAuthUri: String = properties.getProperty("google.service.account.auth_uri") ?: ""
        val serviceAccountTokenUri: String = properties.getProperty("google.service.account.token_uri") ?: ""
        val serviceAccountAuthProviderCertUrl: String = properties.getProperty("google.service.account.auth_provider_x509_cert_url") ?: ""
        val serviceAccountClientCertUrl: String = properties.getProperty("google.service.account.client_x509_cert_url") ?: ""


        // Add the property to BuildConfig
        buildConfigField("String", "IMGBB_API_KEY", "\"$imgbbApiKey\"")
        buildConfigField("String", "EXCEL_API_KEY", "\"$excelSheetIdApiKey\"")

        buildConfigField("String", "SERVICE_ACCOUNT_TYPE", "\"$serviceAccountType\"")
        buildConfigField("String", "SERVICE_ACCOUNT_PROJECT_ID", "\"$serviceAccountProjectId\"")
        buildConfigField("String", "SERVICE_ACCOUNT_PRIVATE_KEY_ID", "\"$serviceAccountPrivateKeyId\"")
        buildConfigField("String", "SERVICE_ACCOUNT_PRIVATE_KEY", "\"$serviceAccountPrivateKey\"")
        buildConfigField("String", "SERVICE_ACCOUNT_CLIENT_EMAIL", "\"$serviceAccountClientEmail\"")
        buildConfigField("String", "SERVICE_ACCOUNT_CLIENT_ID", "\"$serviceAccountClientId\"")
        buildConfigField("String", "SERVICE_ACCOUNT_AUTH_URI", "\"$serviceAccountAuthUri\"")
        buildConfigField("String", "SERVICE_ACCOUNT_TOKEN_URI", "\"$serviceAccountTokenUri\"")
        buildConfigField("String", "SERVICE_ACCOUNT_AUTH_PROVIDER_CERT_URL", "\"$serviceAccountAuthProviderCertUrl\"")
        buildConfigField("String", "SERVICE_ACCOUNT_CLIENT_CERT_URL", "\"$serviceAccountClientCertUrl\"")


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/*.properties"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17) // or 11 depending on your setup
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //shared-preferences and security
    implementation(libs.androidx.security.crypto)

    // Google Sheets API
    implementation("com.google.api-client:google-api-client-android:2.0.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20220927-2.0.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
    implementation("com.google.api-client:google-api-client-gson:2.0.0")
    implementation("com.google.http-client:google-http-client-jackson2:1.42.3")
    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging.ktx)
//    implementation(libs.firebase.storage.ktx)

//    implementation ("com.google.firebase:firebase-appcheck-playintegrity")
//    implementation ("com.google.firebase:firebase-appcheck-debug")

    //coil
    implementation(libs.coil.compose)

    //google selected files
    implementation(libs.accompanist.permissions)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.hilt.navigation)
    implementation(libs.hilt.navigation.compose)

    implementation((libs.hilt.android))
    ksp((libs.hilt.compiler))

    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter)
    implementation(libs.okhttp.logging)

}