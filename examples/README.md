# Pangea Java SDK examples

This is a collection of examples demonstrating how to use the Pangea Java SDK.

## Setup

Set up the environment variables ([Instructions](https://pangea.cloud/docs/redact#set-your-environment-variables)) `PANGEA_<SERVICE>_TOKEN` and `PANGEA_URL_TEMPLATE` with your project token configured on the Pangea User Console (token should have access to `<service>` [Instructions](https://pangea.cloud/docs/admin-guide/tokens)) and with your Pangea URL template.

## Run

To run examples, move to example folder you would like to run, e.g. if we want to run audit log example:

```bash
$ cd audit/log
```

and then run example with:

```bash
$ mvn clean && mvn compile
$ mvn exec:java -Dexec.mainClass=cloud.pangeacyber.examples.App
```
