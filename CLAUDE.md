# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Multi-module Android app (`rootProject.name = "Meeting"`, package `com.cws.meeting`). The long-term target is a Zoom/Meet-style video calling app, but the **primary goal is learning Android modern-app architecture** — real video streaming is out of scope. The media layer is meant to be implemented as swappable fakes behind the `:core:service` / `:datasource` interfaces; do not pull in LiveKit, libwebrtc, Agora, etc. without explicit user request.

Jetpack Compose + Material 3, Hilt, Navigation Compose (type-safe via kotlinx.serialization), Coroutines + Flow.

## Toolchain

- **Kotlin 2.2.10**, **AGP 9.1.1**, **KSP 2.2.10-2.0.2**, **Hilt 2.56.2**, **Compose BOM 2026.02.01**
- `compileSdk = 36`, `minSdk = 24`, `targetSdk = 36`, Java 11 source/target, Gradle daemon on JDK 21
- All versions/aliases live in `gradle/libs.versions.toml`. Add libraries there and reference via `libs.*`. Never hardcode versions in module build files.

## Module graph

Eight production modules plus `build-logic` (composite build for convention plugins).

```
:app  ──┬──→ :feature
        ├──→ :core:domain
        ├──→ :core:service          (Hilt binding aggregation)
        ├──→ :core:model
        ├──→ :common:designsystem
        └──→ :common:analytics

:feature
        ├──→ :core:domain           (primary path — UseCases)
        ├──→ :core:service          (★ allowed ONLY for simple reads — convention, not Gradle-enforced)
        ├──→ :core:model
        ├──→ :common:designsystem
        └──→ :common:analytics

:core:domain  ──→ :core:service, :core:model, :common:analytics
:core:service ──→ :datasource, :core:model, :common:analytics
:datasource   ──→ :core:model, :common:analytics
:common:designsystem   (Compose only, no project deps)
:common:analytics      (open to any module)
:core:model   (pure Kotlin — no Android deps)
```

**Rules to enforce in code review:**

1. **`:datasource` has exactly one consumer: `:core:service`.** No other module may declare a `:datasource` dependency. Datasource handles platform/server integration + persistence; everything outside the service layer must go through service.
2. **`:feature` → `:core:service` is allowed only for simple reads.** Anything with business logic / composition / side effects goes through a `:core:domain` UseCase. This is a convention; Gradle can't enforce it — watch for it in PRs.
3. **Dependency direction follows Android modern-app architecture, not Clean Architecture.** `:core:domain` depends on `:core:service` (not the reverse). Repository *interfaces* live in `:core:service` alongside their implementations; UseCases in `:core:domain` consume them.
4. **`:common:analytics` is open to every module** (including `:core:domain`, `:core:service`, `:datasource`). Log wherever it makes sense.

## Convention plugins (`build-logic/`)

`build-logic` is an included build; `pluginManagement.includeBuild("build-logic")` in root `settings.gradle.kts` substitutes the five plugin IDs below. Apply them in each module via `alias(libs.plugins.cws.*)`.

| Plugin ID | Applies | Used by |
|---|---|---|
| `cws.android.application` | AGP app + kotlin-android + compile/min/target SDK, Java 11 | `:app` |
| `cws.android.library` | AGP lib + kotlin-android + same SDK/Java defaults | all android modules except `:app` and `:core:model` |
| `cws.kotlin.library` | kotlin-jvm + Java 11 | `:core:model` |
| `cws.android.compose` | kotlin-compose plugin + `buildFeatures.compose = true` + Compose BOM/UI/Material3/tooling deps | `:app`, `:feature`, `:common:designsystem` |
| `cws.android.hilt` | KSP + Hilt plugin + `hilt-android` / `hilt-compiler` deps | every module with DI (everything except `:core:model` and `:common:designsystem`) |

Compose convention plugin auto-detects whether the module is app or library via `findByType<ApplicationExtension>()` / `findByType<LibraryExtension>()`, so it composes with either application or library plugins.

When adding a new module, apply the minimal set of convention plugins needed — don't add `cws.android.hilt` unless the module actually has `@Inject` / `@Module` / `@HiltViewModel` code.

## Common commands

Windows bash. On PowerShell use `gradlew.bat` instead of `./gradlew`.

```bash
./gradlew :app:assembleDebug              # build debug APK
./gradlew :app:installDebug               # install to connected device/emulator
./gradlew :app:testDebugUnitTest          # host-side unit tests for :app
./gradlew :feature:testDebugUnitTest      # host-side unit tests for a specific module
./gradlew :app:connectedDebugAndroidTest  # instrumented tests (device required)
./gradlew lint                            # lint across all modules
./gradlew build                           # full build incl. all modules' lint + unit tests
```

Run a single test:
```bash
./gradlew :app:testDebugUnitTest --tests "com.cws.meeting.ExampleUnitTest.addition_isCorrect"
```

`local.properties` holds `sdk.dir` and is git-ignored — don't commit.

## Things to watch for

- **Type-safe project accessors are enabled** (`enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")` in `settings.gradle.kts`). Use `projects.core.domain` syntax in dependency blocks, not `project(":core:domain")`.
- **Navigation Compose uses type-safe destinations** (kotlinx.serialization). Destinations should be `@Serializable` classes/objects.
- **`:common:designsystem` is Compose-only and has no project dependencies.** Keep it free of domain models — features compose designsystem + model together, not the other way around.
- **`:core:model` is pure Kotlin** (JVM module, not Android library). Don't import `android.*` into it — doing so forces it to become an android library and cascades Android deps into every consumer.
- **Hilt aggregation reaches modules transitively** via `:app`'s dependency closure. `:app` doesn't need to explicitly depend on `:datasource` — Hilt modules inside it are picked up through `:core:service`.
