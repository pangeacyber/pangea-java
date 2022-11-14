<p>
  <br />
  <a href="https://pangea.cloud?utm_source=github&utm_medium=node-sdk" target="_blank" rel="noopener noreferrer">
    <img src="https://pangea-marketing.s3.us-west-2.amazonaws.com/pangea-color.svg" alt="Pangea Logo" height="40">
  </a>
  <br />
</p>

<p>
<br />

[![documentation](https://img.shields.io/badge/documentation-pangea-blue?style=for-the-badge&labelColor=551B76)](https://pangea.cloud/docs/sdk/python/)
[![Discord](https://img.shields.io/discord/1017567751818182786?color=%23551b76&label=Discord&logo=discord&logoColor=%23FFFFFF&style=for-the-badge)](https://discord.gg/z7yXhC7cQr)

<br />
</p>

# Pangea Java SDK

## compile

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


## Develop
### Install Java JDK

https://www.digitalocean.com/community/tutorials/install-maven-mac-os


Download JDK from https://jdk.java.net/19/ and then uncompress and move to your prefered folder

```
tar -xvf openjdk-19.0.1_macos-aarch64_bin.tar.gz
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
openjdk version "19.0.1" 2022-10-18
OpenJDK Runtime Environment (build 19.0.1+10-21)
OpenJDK 64-Bit Server VM (build 19.0.1+10-21, mixed mode, sharing)
```

### Install Maven
Download .bin.tar.gz from https://maven.apache.org/download.cgi
After downloading, extract it:

```
tar -xvf apache-maven-3.8.6-bin.tar.gz
sudo mv apache-maven-3.8.6 /Library/Java/
```

Set environment variables in .zshrc

```
export M2_HOME="/Library/Java/apache-maven-3.8.6"
PATH="${M2_HOME}/bin:${PATH}"
export PATH
```

Check install
```
mvn -version
```

It should show the following output:
```
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /Library/Java/apache-maven-3.8.6
Java version: 19.0.1, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-19.0.1.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "13.0.1", arch: "aarch64", family: "mac"
```
