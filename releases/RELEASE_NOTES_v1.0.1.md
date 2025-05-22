# SureScan v1.0.1 Release Notes

## Release Date: May 22, 2025

### Bug Fixes

- **Fixed APK Installation Issue**: Resolved the "App not installed as package appears to be invalid" error that users were experiencing when trying to install the app.

### Technical Changes

1. **SDK Version Updates**:
   - Changed compileSdk from 35 to 34
   - Changed targetSdk from 35 to 34
   - Maintained minSdk at 24 (Android 7.0 Nougat)

2. **Build Tool Updates**:
   - Updated Gradle version from 8.11.1 to 8.4
   - Updated Android Gradle Plugin from 8.10.0 to 8.2.0

3. **APK Signing**:
   - Implemented proper APK signing with a dedicated keystore
   - Added secure signing configuration to the build process

### Compatibility

- **Minimum Android Version**: Android 7.0 (Nougat) or higher
- **Target Android Version**: Android 14

### Installation Instructions

1. Download the APK file to your Android device
2. Enable installation from unknown sources in your device settings if not already enabled
3. Tap on the downloaded APK file to install
4. Follow the on-screen instructions to complete installation

### Known Issues

- None

### Support

For any issues or questions, please contact the development team.
