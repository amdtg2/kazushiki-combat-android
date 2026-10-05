# Kazushiki Combat Android

Native Android version of Kazushiki Combat built with Kotlin + Jetpack Compose.

## Architecture

- Android UI: Jetpack Compose
- Android package: `com.kazushiki.combat`
- Shared AI backend: `https://kazushiki-api.gudtymeskazushiki.workers.dev`
- AI Coach: Cloudflare -> OpenAI
- AI Video Review: Cloudflare -> Gemini with fallback handling

## First milestone

This repository starts with a production-shaped Android shell for the four main app areas:

- Home
- Train
- Coach
- Progress

The Coach screen is wired to the shared Kazushiki API so the Android emulator can exercise the same backend as iOS.

## Local setup

1. Install the latest stable Android Studio.
2. Install Android SDK 36.
3. Open this repository as an Android Studio project.
4. Use JDK 17.
5. Create an Android 16 emulator (Pixel profile is fine).
6. Run the `app` configuration.

## Release plan

The Android port will mirror the stable iOS behavior rather than redesigning the product. Google Play Billing, video frame extraction, subscription verification, Play Integrity, and production rate limiting will be added before public release.
