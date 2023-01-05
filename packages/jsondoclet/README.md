# Pangea JsonDoclet

A custom javadoc doclet to take javadoc comments and output them to a JSON file

## Install locally

```
mvn clean install
```

## Add the doclet to your Maven project

```
<build>
    <plugins>
    ...

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>3.4.1</version>
        <configuration>
          <doclet>cloud.pangea.JsonDoclet</doclet>
          <docletArtifact>
            <groupId>cloud.pangea</groupId>
            <artifactId>jsondoclet</artifactId>
            <version>1.0.0-SNAPSHOT</version>
          </docletArtifact>
          <useStandardDocletOptions>false</useStandardDocletOptions>
          <tags>
            <tag>
              <name>pangea.description</name>
              <placement>a</placement>
              <head>Description:</head>
            </tag>
            <tag>
              <name>pangea.code</name>
              <placement>a</placement>
              <head>Example:</head>
            </tag>
          </tags>
        </configuration>
      </plugin>

    ...
    </plugins>
<build>
```

## Run javadoc from Maven

```
mvn javadoc:javadoc
```

After running, a `docs.json` is created under `target/site/apidocs/docs.json`
