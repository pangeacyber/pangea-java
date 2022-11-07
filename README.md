# Java Pangea SDK

## compile

Move to java-pangea/pangea and run:

```
mvn compile
```

## unit tests
```
mvn test
```

## Integration tests

```
$ export PANGEA_TOKEN=...
$ export PANGEA_EMBARGO_CONFIG_ID=...
$ export PANGEA_DOMAIN=...
$ mvn verify
```


## Develope
### Install Java JDK

https://www.digitalocean.com/community/tutorials/install-maven-mac-os


Download JDK from https://jdk.java.net/19/ and then uncompress and move to your prefered folder

```
tar -xvf openjdk-19_macos-aarch64_bin.tar.gz
sudo mv jdk-19.jdk/ /Library/Java/JavaVirtualMachines/
```

Open .bash_profile and add the following entries at the end of it.
```
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home"
PATH="${JAVA_HOME}/bin:${PATH}"
export PATH
```

Open the Terminal and run java -version command.
```
java -version
```

It should show the following output:
```
openjdk version "13.0.1" 2019-10-15
OpenJDK Runtime Environment (build 13.0.1+9)
OpenJDK 64-Bit Server VM (build 13.0.1+9, mixed mode, sharing)
```

### Install Maven
Download .bin.tar.gz from https://maven.apache.org/download.cgi
After downloading, extract it:

```
tar -xvf apache-maven-3.6.3-bin.tar.gz
```

Set environment variables in .zshrc

```
export M2_HOME="/Users/pankaj/Downloads/apache-maven-3.6.3"
PATH="${M2_HOME}/bin:${PATH}"
export PATH
```

Check install
```
mvn -version
```

It should show the following output:
```
Maven home: /Users/andres/Documents/Java/apache-maven-3.8.6
Java version: 19, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home
Default locale: en_AR, platform encoding: UTF-8
OS name: "mac os x", version: "12.6", arch: "aarch64", family: "mac"
```
