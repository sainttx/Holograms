plugins {
    java
    id("maven-publish")
}

group = "me.kingtux"
var build = "0"
var branch = ""
if (hasProperty("buildNumber")) {
    build = properties.get("buildNumber").toString();
}if (hasProperty("branch")) {
    branch = properties.get("branch").toString();
}

version = me.kingtux.holograms.Version.getHologramsVersion(build, branch);


subprojects {
    repositories {
        mavenLocal()
        maven("https://repo.maven.apache.org/maven2")
        maven("https://repo.codemc.org/repository/maven-public")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
}
