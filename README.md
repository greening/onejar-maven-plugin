# unojar-maven-plugin

Build an executable jar containing all dependencies as internal, intact jar files. This means you can ship one jar file with all the libraries included.

There are various alternatives out there, including
* [maven-shade-plugin](https://maven.apache.org/plugins/maven-shade-plugin/), which unpacks libraries and includes them in an uber-jar. By doing this, you lose the modularity of the jars.
* [Capsule](https://medium.com/testable-io/packaging-java-applications-beware-of-one-jar-a2f5ee116f48) is now defunct, but the linked article has a useful critique of One-Jar
* [SlimFast](https://product.hubspot.com/blog/the-fault-in-our-jars-why-we-stopped-building-fat-jars) (for deploying jar artifacts on AWS S3)
* [Spring Boot](https://www.baeldung.com/deployable-fat-jar-spring-boot) ... if you are using Spring.
* Messing with the maven-assembly-plugin, but that will just unpack all dependencies together with your classes in one directory and then repack that directory into a new jar. Doing it that way means files will overwrite each other if they have the same names in the same path, which is quite common with resources such as log4.properties and even other more important files.

With unojar-maven-plugin, you'll instead get a nice clean super jar with the dependency jars inside.

Get started by following the Usage [instructions](http://one-jar.sourceforge.net/index.php%3Fpage%3Dgetting-started)!

The fork packages a patched one-jar 0.97 from [iceberg901](https://github.com/iceberg901/sbt-onejar/commit/0fb0f829941aac4074be47cec4899b8b5a86d961) and uses that version  by default. The original 0.97 is still intact and can be used by specifying the bootfile configuration option in the maven pom.xml.

### Example pom.xml plugin configuration

```
<build>
    <plugins>
        <plugin>
            <groupId>org.greening</groupId>
            <artifactId>unojar-maven-plugin</artifactId>
            <version>1.5.0</version>
            <executions>
                <execution>
                    <goals>
                        <goal>unojar</goal>
                    </goals>
                    <configuration>
                        <mainClass>my.package.Foo</mainClass>
                        <attachToBuild>true</attachToBuild>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
# Plugin Developers
If you'd like to make changes I'm happy to review and merge pull requests. The intention is that each release should make the source cleaner, so please emphasize smaller code, exploit new features in OpenJava 11 (the latest LTS release), and add clear documentation for whatever you might provide.

## Challenges
I'll just mention here things that didn't work well for me, so you can either avoid them or think of them as "for the mightiest," such as you.
* I believe because the the org.apache.maven:plugin-testing source code is not being maintained, I could not get [Maven Plugin Test Harness](https://github.com/apache/maven-plugin-testing) to work. There is a test Mojo in the unojar-maven-plugin source code that is useful.
* The MANIFEST.MF file in the fatjar has extra stuff in it. There's probably a way to figure out whether you should put out a One-Jar-Main-Class entry (only when you use one-jar) or a Uno-Jar-Main-Class entry (when you use uno-jar), but at present this version provides both in MANIFEST.MF. If you tackle this, remember that a user can supply the boot file separately, so you might want to look in the boot file manifest to figure out which one to use.
* There's an ImplementationVersion and an Implementation-Version entry in the MANIFEST.MF file. Do we need both?

## References

* [Plugin Parameter Expressions](https://maven.apache.org/ref/3.6.3/maven-core/apidocs/org/apache/maven/plugin/PluginParameterExpressionEvaluator.html)
* [Uno-Jar github repository](https://github.com/nsoft/uno-jar)

# Plugin Deployers (only Dan at the moment)


