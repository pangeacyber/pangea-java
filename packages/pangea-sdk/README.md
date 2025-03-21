<a href="https://pangea.cloud?utm_source=github&utm_medium=java-sdk" target="_blank" rel="noopener noreferrer">
  <img src="https://pangea-marketing.s3.us-west-2.amazonaws.com/pangea-color.svg" alt="Pangea Logo" height="40" />
</a>

<br />

[![Documentation](https://img.shields.io/badge/documentation-pangea-blue?style=for-the-badge&labelColor=551B76)][Documentation]

# Pangea Java SDK

A Java SDK for integrating with Pangea services. Supports Java 11, 17, and 21.

## Installation

#### GA releases

Via Gradle:

```gradle
implementation("cloud.pangea:pangea-sdk:4.1.1")
```

Via Maven:

```xml
<dependency>
  <groupId>cloud.pangea</groupId>
  <artifactId>pangea-sdk</artifactId>
  <version>4.1.1</version>
</dependency>
```

<a name="beta-releases"></a>

#### Beta releases

Pre-release versions may be available with the `beta` denotation in the version
number. These releases serve to preview Beta and Early Access services and APIs.
Per Semantic Versioning, they are considered unstable and do not carry the same
compatibility guarantees as stable releases. [Beta changelog][].

Via Gradle:

```gradle
implementation("cloud.pangea:pangea-sdk:4.2.0-beta-1")
```

Via Maven:

```xml
<dependency>
  <groupId>cloud.pangea</groupId>
  <artifactId>pangea-sdk</artifactId>
  <version>4.2.0-beta-1</version>
</dependency>
```

## Usage

- [Documentation][]
- [GA Examples][]
- [Beta Examples][]

General usage would be to create a token for a service through the
[Pangea Console][] and then construct an API client for that respective service.
The below example shows how this can be done for [Secure Audit Log][] to log a
simple event:

```java
import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.models.StandardEvent;
import cloud.pangeacyber.pangea.audit.models.LogConfig;
import cloud.pangeacyber.pangea.audit.responses.LogResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

// ...

// Load client configuration from environment variables `PANGEA_AUDIT_TOKEN` and
// `PANGEA_URL_TEMPLATE`.
Config cfg = null;
try {
    cfg = Config.fromEnvironment(AuditClient.serviceName);
} catch (ConfigException e){
    // Handle error.
}

// Create a Secure Audit Log client.
var client = new AuditClient.Builder(cfg).build();

// Create a basic event.
var event = new StandardEvent.Builder("Hello, World!")
    .action("Login")
    .actor("Terminal")
    .build();

// Optional configuration.
var logConfig = new LogConfig.Builder().build();

// Log the event.
LogResponse response = null;
try {
    response = client.log(event, logConfig);
} catch (Exception e) {
    // Handle error.
}
```

   [Documentation]: https://pangea.cloud/docs/sdk/java/
   [GA Examples]: https://github.com/pangeacyber/pangea-java/tree/main/examples
   [Beta Examples]: https://github.com/pangeacyber/pangea-java/tree/beta/examples
   [Pangea Console]: https://console.pangea.cloud/
   [Secure Audit Log]: https://pangea.cloud/docs/audit
   [Beta changelog]: https://github.com/pangeacyber/pangea-java/blob/beta/CHANGELOG.md
