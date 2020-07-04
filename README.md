# Holograms
A Bukkit plugin that allows easy creation and management of text based Holograms

### Resources

* [Resource Page](https://www.spigotmc.org/resources/holograms.4924/)

### Building

To successfully build Holograms using Maven, you must first run Spigot's BuildTools for several versions in order to compile.

```
java -jar BuildTools.jar --rev 1.16
java -jar BuildTools.jar --rev 1.15.2
java -jar BuildTools.jar --rev 1.14
java -jar BuildTools.jar --rev 1.13.2
java -jar BuildTools.jar --rev 1.13
java -jar BuildTools.jar --rev 1.12
java -jar BuildTools.jar --rev 1.11
java -jar BuildTools.jar --rev 1.10
java -jar BuildTools.jar --rev 1.9.4
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

Hologram creation is made easy with our API. Get a reference to the HologramManager and you're set.

```java
private HologramManager hologramManager;

@Override
public void onEnable() {
    this.hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
}
```

##### Creating and Modifying Holograms

Once you have the manager reference, you can easily work your way around the APIs offerings:

```java
public void createHologram(String id, Location location) {
    Hologram hologram = new Hologram(id, location);
    hologramManager.addActiveHologram(hologram); // Tells the plugin a new Hologram was added
}
```

Adding lines is easy as well:

```java
public void addTextLine(Hologram hologram, String text) {
    HologramLine line = new TextLine(hologram, text);
    hologram.addLine(line);
}

public void addItemLine(Hologram hologram, ItemStack itemstack) {
    HologramLine line = new ItemLine(hologram, itemstack);
    hologram.addLine(line);
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


## License ##
This software is available under the following licenses:

* MIT

Special Thanks To:
-------------

![YourKit-Logo](https://www.yourkit.com/images/yklogo.png)

YourKit supports open source projects with its full-featured Java Profiler. YourKit, LLC is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/) and [YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/), innovative and intelligent tools for profiling Java and .NET applications.
