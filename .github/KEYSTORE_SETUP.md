# 🔐 Android Keystore Setup for CI/CD

This document explains how to set up Android keystore signing for automated APK releases.

## 📋 Prerequisites

1. Android keystore file (`release.keystore`)
2. Keystore password
3. Key alias name
4. Key password
4. GitHub repository with admin access

## 🔧 Step 1: Prepare Keystore

### Option A: Use Existing Keystore
If you already have a keystore file in the `keystore/` directory:

```bash
# Navigate to keystore directory
cd keystore

# Encode keystore to base64
base64 -i release.keystore -o keystore_base64.txt

# Copy the content of keystore_base64.txt
cat keystore_base64.txt
```

### Option B: Create New Keystore
If you need to create a new keystore:

```bash
# Create keystore directory
mkdir -p keystore
cd keystore

# Generate new keystore
keytool -genkey -v -keystore release.keystore -alias sourceworx_key -keyalg RSA -keysize 2048 -validity 10000

# Encode to base64
base64 -i release.keystore -o keystore_base64.txt
```

## 🔒 Step 2: Configure GitHub Secrets

Go to your GitHub repository → Settings → Secrets and variables → Actions

Add the following repository secrets:

### Required Secrets:

1. **KEYSTORE_BASE64**
   - Value: Content from `keystore_base64.txt`
   - Description: Base64 encoded keystore file

2. **KEYSTORE_PASSWORD**
   - Value: Password used to create the keystore
   - Description: Keystore password

3. **KEY_ALIAS**
   - Value: Alias name (e.g., `sourceworx_key`)
   - Description: Key alias in the keystore

4. **KEY_PASSWORD**
   - Value: Password for the specific key
   - Description: Key password (often same as keystore password)

## 📱 Step 3: Update app/build.gradle.kts

Ensure your `app/build.gradle.kts` has the signing configuration:

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../keystore/release.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

## 🚀 Step 4: Test the Setup

### Manual Test:
```bash
# Set environment variables
export KEYSTORE_PASSWORD="your_keystore_password"
export KEY_ALIAS="your_key_alias"
export KEY_PASSWORD="your_key_password"

# Build release APK
./gradlew assembleRelease

# Verify APK is signed
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

### GitHub Actions Test:
1. Create a test tag: `git tag v0.1.0-test && git push origin v0.1.0-test`
2. Check GitHub Actions tab for workflow execution
3. Verify APK is built and uploaded to releases

## 🔍 Troubleshooting

### Common Issues:

1. **"Keystore was tampered with, or password was incorrect"**
   - Check KEYSTORE_PASSWORD secret
   - Verify base64 encoding is correct

2. **"Alias does not exist"**
   - Check KEY_ALIAS secret
   - List aliases: `keytool -list -keystore release.keystore`

3. **"Cannot recover key"**
   - Check KEY_PASSWORD secret
   - Ensure key password is correct

4. **"No such file or directory: keystore/release.keystore"**
   - Verify keystore file path in build.gradle.kts
   - Check if keystore is properly decoded in CI

### Debug Commands:

```bash
# List keystore contents
keytool -list -v -keystore keystore/release.keystore

# Verify APK signature
apksigner verify --verbose app/build/outputs/apk/release/app-release.apk

# Check APK info
aapt dump badging app/build/outputs/apk/release/app-release.apk
```

## 📋 Security Best Practices

1. **Never commit keystore files to git**
2. **Use strong passwords (12+ characters)**
3. **Backup keystore securely**
4. **Rotate keys periodically**
5. **Use different keystores for debug/release**
6. **Limit access to GitHub secrets**

## 🔄 Workflow Triggers

The release workflow triggers on:

1. **Tag Push**: `git tag v1.0.0 && git push origin v1.0.0`
2. **GitHub Release**: Create release through GitHub UI
3. **Manual**: Use "Run workflow" button in Actions tab

## 📦 Release Process

1. Update version in `app/build.gradle.kts`
2. Commit changes: `git commit -am "chore: bump version to 1.0.0"`
3. Create tag: `git tag v1.0.0`
4. Push: `git push origin main --tags`
5. GitHub Actions will automatically build and release APK

## 📞 Support

If you encounter issues:
1. Check GitHub Actions logs
2. Verify all secrets are set correctly
3. Test keystore locally first
4. Review this documentation

---

**⚠️ Important**: Keep your keystore and passwords secure. Loss of keystore means you cannot update your app on Google Play Store.
