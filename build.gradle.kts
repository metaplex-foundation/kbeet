plugins {
    kotlin("multiplatform") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("com.android.library")
    id("maven-publish")
    signing
}

group = "com.metaplex.borsh"
version = "0.1.0"

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
        val jsTest by getting
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "metaplex"
            from(components["kotlin"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Borsh Kotlin Serialization Format")
                description.set("A kotlinx-serialization format for the Borsh specification")
                url.set("http://www.github.com/metaplex-foundation/kborsh")
//                properties.set(mapOf(
//                    "myProp" to "value",
//                    "prop.with.dots" to "anotherValue"
//                ))
//                licenses {
//                    license {
//                        name.set("The Apache License, Version 2.0")
//                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                    }
//                }
                developers {
                    developer {
                        id.set("Funkatronics")
                        name.set("Marco Martinez")
                        email.set("marco@metaplex.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/metaplex-foundation/kborsh.git")
                    developerConnection.set("scm:git:ssh://github.com/metaplex-foundation/kborsh.git")
                    url.set("http://github.com/metaplex-foundation/kborsh")
                }
            }
        }
    }
    repositories {
        maven("https://maven.pkg.github.com/metaplex-foundation/kborsh") {
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}