#!/bin/bash

# Script to generate a keystore for signing Android APKs

# Create the keystore directory if it doesn't exist
mkdir -p keystore

# Generate the keystore
keytool -genkey -v -keystore keystore/surescan.keystore -alias surescan -keyalg RSA -keysize 2048 -validity 10000 -storepass surescan123 -keypass surescan123 -dname "CN=SureScan, OU=Development, O=Skyner, L=City, S=State, C=ZA"

echo "Keystore generated successfully at keystore/surescan.keystore"
echo "Keystore password: surescan123"
echo "Key alias: surescan"
echo "Key password: surescan123"
