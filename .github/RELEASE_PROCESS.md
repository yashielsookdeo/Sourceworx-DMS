# 🚀 Release Process for Sourceworx-DMS

This document outlines the automated release process for the Sourceworx-DMS Android application.

## 📋 Overview

The CI/CD pipeline automatically builds, signs, and publishes APK releases when:
- A new version tag is pushed (e.g., `v1.0.0`)
- A GitHub release is created
- Manual workflow dispatch is triggered

## 🔄 Automated Workflows

### 1. **Build and Test** (`.github/workflows/build.yml`)
- **Triggers**: Push to main/master/develop, Pull Requests
- **Actions**: 
  - Run unit tests
  - Code quality checks (lint)
  - Build debug APK
  - Security scanning
- **Artifacts**: Debug APK, test results, lint reports

### 2. **Release** (`.github/workflows/release.yml`)
- **Triggers**: Tag push (`v*.*.*`), GitHub release creation, manual dispatch
- **Actions**:
  - Run tests
  - Build signed release APK
  - Create GitHub release
  - Upload APK as release asset
  - Generate release notes

## 🏷️ Version Management

### Semantic Versioning
Follow semantic versioning (SemVer) for releases:
- **Major** (v2.0.0): Breaking changes
- **Minor** (v1.1.0): New features, backward compatible
- **Patch** (v1.0.1): Bug fixes, backward compatible

### Version Update Process
1. Update version in `app/build.gradle.kts`:
   ```kotlin
   defaultConfig {
       versionCode = 2
       versionName = "1.1.0"
   }
   ```

2. Commit the version change:
   ```bash
   git add app/build.gradle.kts
   git commit -m "chore: bump version to 1.1.0"
   ```

## 🚀 Release Methods

### Method 1: Tag-based Release (Recommended)
```bash
# 1. Update version and commit
git add app/build.gradle.kts
git commit -m "chore: bump version to 1.1.0"

# 2. Create and push tag
git tag v1.1.0
git push origin main --tags

# 3. GitHub Actions automatically creates release
```

### Method 2: GitHub Release UI
1. Go to GitHub repository → Releases
2. Click "Create a new release"
3. Choose or create tag (e.g., `v1.1.0`)
4. Fill in release title and description
5. Click "Publish release"
6. GitHub Actions automatically builds APK

### Method 3: Manual Workflow Dispatch
1. Go to GitHub repository → Actions
2. Select "🚀 Build and Release APK" workflow
3. Click "Run workflow"
4. Enter version number and options
5. Click "Run workflow"

## 📱 APK Signing

### Keystore Configuration
- **File**: `keystore/release.keystore`
- **Environment Variables**: 
  - `KEYSTORE_PASSWORD`
  - `KEY_ALIAS` 
  - `KEY_PASSWORD`
  - `KEYSTORE_BASE64`

### Setup GitHub Secrets
Run the setup script to configure secrets:
```bash
./.github/setup-secrets.sh
```

Then add the displayed secrets to GitHub repository settings.

## 📦 Release Assets

Each release includes:
- **Signed APK**: `sourceworx-dms-v{version}-release.apk`
- **Release Notes**: Auto-generated from commits
- **Installation Instructions**: Included in release description

## 🔍 Monitoring Releases

### GitHub Actions
- Monitor workflow execution in the "Actions" tab
- Check build logs for any issues
- Verify APK is uploaded to releases

### Release Verification
```bash
# Download and verify APK signature
wget https://github.com/your-org/Sourceworx-DMS/releases/download/v1.1.0/sourceworx-dms-v1.1.0-release.apk

# Verify signature (requires Android SDK)
apksigner verify --verbose sourceworx-dms-v1.1.0-release.apk
```

## 🐛 Troubleshooting

### Common Issues

1. **Build Failure**
   - Check GitHub Actions logs
   - Verify all secrets are configured
   - Ensure keystore is valid

2. **Signing Issues**
   - Verify keystore secrets in GitHub
   - Check keystore file integrity
   - Confirm passwords are correct

3. **Release Creation Failed**
   - Check GitHub token permissions
   - Verify tag format (must start with 'v')
   - Ensure release workflow has write permissions

### Debug Steps
1. Check workflow logs in GitHub Actions
2. Verify secrets are set correctly
3. Test keystore locally:
   ```bash
   export KEYSTORE_PASSWORD="your_password"
   export KEY_ALIAS="your_alias"
   export KEY_PASSWORD="your_key_password"
   ./gradlew assembleRelease
   ```

## 📋 Release Checklist

Before creating a release:

- [ ] Update version code and name in `app/build.gradle.kts`
- [ ] Test the app thoroughly
- [ ] Update CHANGELOG.md (if exists)
- [ ] Commit all changes
- [ ] Verify GitHub secrets are configured
- [ ] Create and push version tag
- [ ] Monitor GitHub Actions workflow
- [ ] Verify APK is uploaded to release
- [ ] Test downloaded APK on device

## 🔒 Security Considerations

- **Keystore Security**: Never commit keystore files to git
- **Secret Management**: Use GitHub secrets for sensitive data
- **Access Control**: Limit who can create releases
- **APK Verification**: Always verify APK signatures

## 📞 Support

For issues with the release process:
1. Check GitHub Actions logs
2. Review this documentation
3. Verify keystore setup using `.github/KEYSTORE_SETUP.md`
4. Test locally before creating releases

---

**🎯 Goal**: Streamlined, automated releases with minimal manual intervention while maintaining security and quality standards.
