<p>
  <br />
  <a href="https://pangea.cloud?utm_source=github&utm_medium=node-sdk" target="_blank" rel="noopener noreferrer">
    <img src="https://pangea-marketing.s3.us-west-2.amazonaws.com/pangea-color.svg" alt="Pangea Logo" height="40" />
  </a>
  <br />
</p>

<p>
<br />

[![documentation](https://img.shields.io/badge/documentation-pangea-blue?style=for-the-badge&labelColor=551B76)](https://pangea.cloud/docs/sdk/java/)
[![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)](https://pangea.cloud/join-slack/)

<br />
</p>

# Pangea Java SDK

## Compile

```
mvn clean && mvn compile
```

## Installation

To add Pangea Java SDK to your project, you need to add to your project `pom.xml` file next lines:

```
  <dependencies>
  ...
    <dependency>
      <groupId>cloud.pangea</groupId>
      <artifactId>pangea-sdk</artifactId>
      <version>1.9.1</version>
    </dependency>
  ...
  </dependencies>
```

## Usage

For samples apps look at [/examples](https://github.com/pangeacyber/pangea-java/tree/main/examples) folder in this repository. There you will find basic samples apps for each services supported on this SDK. Each service folder has a README.md with intructions to install, setup and run.


## Reporting issues and new features

If faced some issue using or testing this SDK or a new feature request feel free to open an issue [clicking here](https://github.com/pangeacyber/pangea-java/issues).
We would need you to provide some basic information like what SDK's version you are using, stack trace if you got it, framework used, and steps to reproduce the issue.
Also feel free to contact [Pangea support](mailto:support@pangea.cloud) by email or send us a message on [Slack](https://pangea.cloud/join-slack/)


## Contributing

Currently, the setup scripts only have support for Mac/ZSH environments.
Future support is incoming.

To install our linters, simply run `./dev/setup_repo.sh`
These linters will run on every `git commit` operation.

### Send a PR

If you would like to [send a PR](https://github.com/pangeacyber/pangea-java/pulls) including a new feature or fixing a bug in code or an error in documents we will really appreciate it and after review and approval you will be included in our [contributors list](./CONTRIBUTING.md)


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
