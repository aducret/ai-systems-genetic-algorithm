# Genetic Algorithm

The objective of this practical work was to implement a genetic algorithm which calculates the best solution to a problem, in our case the problem was determinate the best build of a RPG character.

For more details, statement of the practical work can be found at `doc/statement/Sistemas_de_Inteligencia_Artificial__TPE3.pdf`.

# Setup

## Bootstrap project
```
git clone git@bitbucket.org:itba/sia-2016-08.git
cd sia-2016-08
```
## Build JAR
```
mvn clean package
```
Move the jar where you want and copy the tsv and txt files (configuration files, multipliers, heights and items).
```
cp target/ai_systems_genetic_algorithm-0.0.1-SNAPSHOT-jar-with-dependencies.jar ~/
cp target/*.txt ~/
cp target/*.tsv ~/
```
## Run JAR
The first argument indicates the directory where the tsv and txt files are. The default value is `./` if you are running a jar  or `doc/data/` if you are running in eclipse.

```
cd ~
java -jar ./ai_systems_genetic_algorithm-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
```

## Configuration file
In the configuration file you can indicate the parameters for the algorithm. A example configuration file is added in `doc/data/configuration.txt` and the default configuration will be in  `doc/data/defaultConfiguration.txt`. In case a parameter is missing, it will be loaded from the default configuration file.

The implementation approach of this problem is generic, so you can change the character multiplayers from the `doc/data/multipliers.txt` file.

### Collaborators

- [Ducret, Argentino](https://github.com/aducret)
- [Gutierrez, Ignacio](https://github.com/goodengineer)
- [Prudhomme, Franco](https://github.com/francoprud)
