# Contributing

Currently, the setup scripts only have support for Mac/ZSH environments.
Future support is incoming.

To install our linters, simply run `./dev/setup_repo.sh`.
These linters will run on every `git commit` operation.

## Install Java JDK

https://www.digitalocean.com/community/tutorials/install-maven-mac-os

Download JDK from https://jdk.java.net/19/ and then uncompress and move to your preferred folder

```bash
$ tar -xvf openjdk-19.0.1_macos-aarch64_bin.tar.gz
$ sudo mv jdk-19.jdk/ /Library/Java/JavaVirtualMachines/
```

Open ``.bash_profile`` and add the following entries at the end of it.

```bash
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home"
PATH="${JAVA_HOME}/bin:${PATH}"
export PATH
```

Open the Terminal and run `java -version`` command.

```bash
$ java -version
openjdk version "19.0.1" 2022-10-18
OpenJDK Runtime Environment (build 19.0.1+10-21)
OpenJDK 64-Bit Server VM (build 19.0.1+10-21, mixed mode, sharing)
```

### Install Maven

Download .bin.tar.gz from https://maven.apache.org/download.cgi
After downloading, extract it:

```bash
$ tar -xvf apache-maven-3.8.6-bin.tar.gz
$ sudo mv apache-maven-3.8.6 /Library/Java/
```

Set environment variables in `.zshrc`

```bash
export M2_HOME="/Library/Java/apache-maven-3.8.6"
PATH="${M2_HOME}/bin:${PATH}"
export PATH
```

Check install
```bash
$ mvn -version
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /Library/Java/apache-maven-3.8.6
Java version: 19.0.1, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-19.0.1.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "13.0.1", arch: "aarch64", family: "mac"
```

## Compile

```bash
$ mvn clean && mvn compile
```

## Contributors

- Andrés Tournour (andres.tournour@gmail.com). Code.
- Glenn Gallien (glenn.gallien@pangea.cloud). Code and docs.
- David Wayman (david.wayman@pangea.cloud). Code and docs.
