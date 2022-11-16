plugins {
    kotlin("multiplatform") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("com.android.library")
    id("maven-publish")
    signing
}

group = "com.metaplex.kborsh"
version = "development"
val libraryVersion = System.getenv("GITHUB_REF")?.split('/')?.last() ?: "development"

repositories {
    google()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    android {
        publishLibraryVariants("release")
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.8")
            }
        }
        val jvmMain by getting {
            kotlin.srcDir("src/commonJvmAndroid/kotlin")
        }
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val androidMain by getting {
            kotlin.srcDir("src/commonJvmAndroid/kotlin")
            dependencies {
                implementation("androidx.core:core-ktx:1.9.0")
            }
        }
//        val androidTest by getting {
//            kotlin.srcDir("src/jvmTest/kotlin")
//        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
    namespace = "$group.${rootProject.name}"
}

System.getenv("GITHUB_REPOSITORY")?.let {

    val publishedGroupId = "com.metaplex"

    project.group = publishedGroupId
    project.version = libraryVersion

    publishing {
        publications.withType(MavenPublication::class) {
            groupId = publishedGroupId
            version = libraryVersion

            artifact(tasks["javadocJar"])
        }

        repositories {
            maven {
                name = "github"
                url = uri("https://maven.pkg.github.com/$it")
                credentials(PasswordCredentials::class)
            }
        }
    }
}

signing {
    sign(publishing.publications)
}