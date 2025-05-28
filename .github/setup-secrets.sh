#!/bin/bash

# 🔐 GitHub Secrets Setup Script for Sourceworx-DMS
# This script helps encode the keystore and provides the values needed for GitHub secrets

set -e

echo "🔐 Sourceworx-DMS GitHub Secrets Setup"
echo "======================================"
echo ""

# Check if keystore exists
KEYSTORE_PATH="keystore/release.keystore"
if [ ! -f "$KEYSTORE_PATH" ]; then
    echo "❌ Error: Keystore not found at $KEYSTORE_PATH"
    echo "Please ensure the keystore file exists before running this script."
    exit 1
fi

echo "✅ Found keystore at: $KEYSTORE_PATH"
echo ""

# Encode keystore to base64
echo "🔄 Encoding keystore to base64..."
KEYSTORE_BASE64=$(base64 -i "$KEYSTORE_PATH")

echo "✅ Keystore encoded successfully!"
echo ""

# Display the secrets that need to be added to GitHub
echo "📋 GitHub Repository Secrets to Add:"
echo "===================================="
echo ""
echo "Go to: GitHub Repository → Settings → Secrets and variables → Actions"
echo ""
echo "Add these repository secrets:"
echo ""

echo "1. KEYSTORE_BASE64"
echo "   Description: Base64 encoded keystore file"
echo "   Value:"
echo "   $KEYSTORE_BASE64"
echo ""

echo "2. KEYSTORE_PASSWORD"
echo "   Description: Keystore password"
echo "   Value: surescan123"
echo ""

echo "3. KEY_ALIAS"
echo "   Description: Key alias in the keystore"
echo "   Value: surescan"
echo ""

echo "4. KEY_PASSWORD"
echo "   Description: Key password"
echo "   Value: surescan123"
echo ""

# Save to file for easy copying
SECRETS_FILE=".github/secrets-values.txt"
echo "💾 Saving secrets to: $SECRETS_FILE"
cat > "$SECRETS_FILE" << EOF
GitHub Repository Secrets for Sourceworx-DMS
===========================================

KEYSTORE_BASE64:
$KEYSTORE_BASE64

KEYSTORE_PASSWORD:
surescan123

KEY_ALIAS:
surescan

KEY_PASSWORD:
surescan123

Setup Instructions:
1. Go to GitHub Repository → Settings → Secrets and variables → Actions
2. Click "New repository secret"
3. Add each secret with the name and value above
4. Ensure all 4 secrets are added before running the release workflow

EOF

echo "✅ Secrets saved to $SECRETS_FILE"
echo ""

# Verify keystore
echo "🔍 Verifying keystore..."
if command -v keytool &> /dev/null; then
    echo "Keystore contents:"
    keytool -list -keystore "$KEYSTORE_PATH" -storepass surescan123 2>/dev/null || echo "⚠️  Could not verify keystore (this is normal if keytool is not available)"
else
    echo "⚠️  keytool not found - cannot verify keystore contents"
fi

echo ""
echo "🚀 Next Steps:"
echo "1. Add the 4 secrets to your GitHub repository"
echo "2. Test the workflow by creating a tag: git tag v1.0.0 && git push origin v1.0.0"
echo "3. Check GitHub Actions tab for workflow execution"
echo ""
echo "📖 For detailed instructions, see: .github/KEYSTORE_SETUP.md"
echo ""
echo "✅ Setup complete!"
