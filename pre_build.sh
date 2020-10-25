
wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -jar BuildTools.jar --rev 1.16
java -jar BuildTools.jar --rev 1.15.2
java -jar BuildTools.jar --rev 1.14
java -jar BuildTools.jar --rev 1.13.2
java -jar BuildTools.jar --rev 1.13