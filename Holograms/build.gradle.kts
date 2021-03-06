plugins {
    java
    `java-library`
    `maven-publish`
    signing
    id("com.github.johnrengelman.shadow") version "6.1.0"

}

group = "me.kingtux.holograms"
var build = "0"
var branch = ""
if (hasProperty("buildNumber")) {
    build = properties.get("buildNumber").toString();
}
if (hasProperty("branch")) {
    branch = properties.get("branch").toString();
}

version = me.kingtux.holograms.Version.getHologramsVersion(build, branch);
val artifactName = "Holograms"

java {
    withJavadocJar()
    withSourcesJar()
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {

            artifactId = artifactName
            artifact(tasks["shadowJar"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(artifactName)

            }
        }
    }
    repositories {
        maven {

            val releasesRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            val snapshotsRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)

        }
        mavenLocal()
    }
}


tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveClassifier.set("");
        dependencies {
            relocate("org.bstats.bukkit", "me.kingtux.holograms.bstats")
        }
    }
    "jar"{
        enabled = false

    }

    "assemble"{
        dependsOn(shadowJar)

    }
}

repositories {
    maven("https://repo.maven.apache.org/maven2/")
    mavenLocal()
    jcenter()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
}

dependencies {
    compileOnly("org.spigotmc", "spigot-api", "1.13-R0.1-SNAPSHOT")
    implementation("org.bstats", "bstats-bukkit", "2.2.1")
    implementation(project(":Holograms-v1_13_R1"))
    implementation(project(":Holograms-v1_13_R2"))
    implementation(project(":Holograms-v1_14_R1"))
    implementation(project(":Holograms-v1_15_R1"))
    implementation(project(":Holograms-v1_16_R1"))
    implementation(project(":Holograms-v1_16_R2"))
    implementation(project(":Holograms-v1_16_R3"))
    implementation(project(":Holograms-API"))

}

