# Photography Techniques App

## 📱 Application Overview
Android application that displays various photography techniques with their details. Features three main screens:
- **Login Screen**: Authenticates users
- **Dashboard Screen**: Shows list of photography techniques
- **Details Screen**: Displays comprehensive technique information

## 🛠 Technical Specifications
- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 31 (Android 12)
- **Language**: Java
- **Architecture**: MVC (Model-View-Controller)

## 🔌 Dependencies
```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.recyclerview:recyclerview:1.3.1'
    implementation 'com.squareup.okhttp3:okhttp:4.9.3'
    implementation 'com.google.code.gson:gson:2.8.9'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:3.12.4'
}
```

Project STructure:
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/nit3213app/nit3213finalproject/
│   │   │   ├── MainActivity.java        # Login screen
│   │   │   ├── DashboardActivity.java   # Techniques list
│   │   │   └── DetailsActivity.java     # Technique details
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_dashboard.xml
│   │   │   │   └── activity_details.xml
│   │   │   │   └── item_entity.xml
│   ├── test/                           # Unit tests
│   └── androidTest/                    # Instrumentation tests
```

Authentication:

```
// Valid credentials:
username = "Nischal"
password = "s8187282"

// Successful login returns:
keypass = "photography"
```

🌐 API Integration

Base URL: https://nit3213api.onrender.com/

Endpoints:

    POST /footscray/auth - User authentication

    GET /dashboard/{keypass} - Retrieves photography techniques

🚀 Features

    Login Screen:

        Validates user credentials

        Makes API authentication call

        Navigates to Dashboard on success

    Dashboard Screen:

        Displays techniques in RecyclerView

        Shows technique name and equipment

        Click navigates to Details screen

    Details Screen:

        Complete technique information

        Includes description, photographer, and year

🧪 Testing

Run tests with:

```
./gradlew test
```

Test Coverage:

    JSON parsing

    Data model validation

    RecyclerView functionality

📝 Notes

    Handles basic error cases

    Explicit text styling for all modes

    Includes core unit tests
