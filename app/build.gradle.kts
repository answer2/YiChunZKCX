
plugins {
    id("com.android.application")
    
}

android {
    namespace = "dev.answer.yichunzkcx"
    compileSdk = 33


    defaultConfig {
        applicationId = "dev.answer.yichunzkcx"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        
    }
    
}

dependencies {


    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    //OkHttp and Okio Library
    implementation("com.squareup.okio:okio:3.3.0")
    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")
    
    //json
    implementation("com.google.code.gson:gson:2.10.1")
    
    //Excel处理
    implementation ("org.apache.poi:poi:4.0.1")
    implementation ("org.apache.poi:poi-ooxml:4.0.1")
    implementation ("javax.xml.stream:stax-api:1.0-2")
    implementation ("pull-parser:pull-parser:2.1.10")
  
}
