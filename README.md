# Holograms
A Bukkit plugin that allows easy creation and management of text based Holograms

### Resources

* [Resource Page](https://www.spigotmc.org/resources/holograms.4924/)

### Building

To successfully build Holograms using Maven, you must first run Spigot's BuildTools for several versions in order to compile.

```
java -jar BuildTools.jar --rev 1.9
java -jar BuildTools.jar --rev 1.8.8
java -jar BuildTools.jar --rev 1.8.3
java -jar BuildTools.jar --rev 1.8
```

Then use the following command to build using Maven
```
mvn clean install
```

The resulting jar files will be found in the /target folder for each module.

### Using Holograms

To use Holograms in your plugins, add the Holograms-API module to your build path. Then add Holograms as a dependency in your plugin.yml file:

```yml
depend: [Holograms]
```

You can also add Holograms as a Maven dependency:

```xml
<repositories>
  <repository>
    <id>sainttx-repo</id>
    <url>http://repo.sainttx.com/artifactory/libs-release-local/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.sainttx.holograms</groupId>
    <artifactId>holograms-api</artifactId>
    <version>1.9b</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

Hologram creation is made easy with our API. Get a reference to the HologramManager and you're set.

```java
private HologramManager hologramManager;

@Override
public void onEnable() {
    this.hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
}
```

##### Creating and Modifying Holograms

Once you have the reference, you can easily work your way around the APIs offerings:

```java
public void createHologram(String id, Location location, String... text) {
    Hologram hologram = new Hologram(id, location, text); 
    hologram.refresh(); // Force spawns all of the lines at the defined location
    hologramManager.addActiveHologram(hologram); // Tells the plugin a new Hologram was added
}
```

Adding lines is easy as well:

```java
public void addLine(Hologram hologram, String text) {
    HologramLine line = new TextLine(hologram, text);
    hologram.addLine(line);
    hologram.refresh();
}
```

##### Removing Holograms

You can permanently remove a hologram (incl. persistence) by doing the following:

```java
public void deleteHologram(Hologram hologram) {
    hologramManager.deleteHologram(hologram);
}
```

Or if you want to temporarily hide a persistent hologram until the server restarts:

```java
public void hideHologram(Hologram hologram) {
    hologram.despawn();
    hologramManager.removeActiveHologram(hologram);
}
```

### In-game commands
The subcommands for this plugin are as follows:

* `/holograms addline <hologramName> <textToAdd>`
* `/holograms create <hologramName> <initialText>`
* `/holograms delete <hologramName>`
* `/holograms import <plugin>`
* `/holograms info <hologramName>`
* `/holograms insertline <hologramName> <index> <textToAdd>`
* `/holograms list`
* `/holograms movehere <hologramName>`
* `/holograms near <radius>`
* `/holograms removeline <hologramName> <index>`
* `/holograms refresh`
* `/holograms setline <hologramName> <index> <text>`

