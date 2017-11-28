# Genetic Working Space

# Setup

## Bootstrap project
```
git clone git@bitbucket.org:itba/genetic-working-space.git
cd genetic-working-space
```
## Build JAR
You need to install maven and then build the jar.
```
sudo apt-get -y install maven
mvn clean package
```
Move the jar where you want and copy the tsv and txt files (configuration files, multipliers, heights and items).
```
cp target/ai_systems_genetic_algorithm-0.0.1-SNAPSHOT-jar-with-dependencies.jar ~/
cp doc/gapa/*.txt ~/
cp doc/gapa/*.tsv ~/
```
## Run JAR
The first argument indicates the directory where the tsv and txt files are. The default value is `./` if you are running a jar  or `doc/gapa/` if you are running in eclipse.

```
cd ~
java -jar ./ai_systems_genetic_algorithm-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Configuration file
In the configuration file you can indicate the parameters for the algorithm. A example configuration file is added in `doc/configuration.txt` and the default configuration will be in  `doc/defaultConfiguration.txt`. In case a parameter is missing, it will be loaded from the default configuration file.

### Collaborators

- [Ducret, Argentino](https://github.com/aducret)
- [Gutierrez, Ignacio](https://github.com/goodengineer)
