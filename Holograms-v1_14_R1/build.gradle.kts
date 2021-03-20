plugins {
    java
    `java-library`
    `maven-publish`
    signing

}

group = "me.kingtux.holograms"
var build = "0"
var branch = ""
if (hasProperty("buildNumber")) {
    build = properties.get("buildNumber").toString();
}if (hasProperty("branch")) {
    branch = properties.get("branch").toString();
}

version = me.kingtux.holograms.Version.getHologramsVersion(build, branch);
val artifactName = "Holograms-v1_14_R1"

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
            artifact(tasks["jar"])
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



repositories {
    maven("https://repo.maven.apache.org/maven2/")
    mavenLocal()
    jcenter()
    maven("https://repo.kingtux.me/storages/maven/kingtux-repo")
    maven("https://repo.kingtux.me/storages/maven/ryandw11")
    maven("https://jitpack.io")
    maven("https://repo.ryandw11.com/repository/maven-snapshots/")
}

dependencies {
    compileOnly("org.spigotmc", "spigot", "1.14-R0.1-SNAPSHOT")
    compileOnly(project(":Holograms-API"))
}

