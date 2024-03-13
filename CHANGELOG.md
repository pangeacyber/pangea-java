# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Sanitize service support

## 3.8.0-beta-1 - 2024-03-19

### Added

- Audit /download_results endpoint support
- Secure Share support.

### Fixed

- Put to presigned url. It should just put file in raw, not in form format.


## [3.7.0] - 2024-02-26

### Added 

- Vault service. Post quantum signing algorithms support

### Changed

- Now targets Java 17 and 21.
- Rewrote `README.md`.


## [3.6.0] - 2024-01-11

### Added

- Vault encrypt structured support.

## [3.5.0] - 2023-12-18

### Added

- File Intel /v2/reputation support
- IP Intel /v2/reputation, /v2/domain, /v2/proxy, v2/vpn and /v2/geolocate support
- URL Intel /v2/reputation support
- Domain Intel /v2/reputation support
- User Intel /v2/user/breached and /v2/password/breached support


## [3.4.0] - 2023-12-07

### Changed

- 202 result format

### Removed

- accepted_status in 202 result

### Added

- put_url, post_url, post_form_data fields in 202 result


## [3.3.0] - 2023-11-28

### Added

- Authn unlock user support
- Redact multiconfig support
- File Scan post-url and put-url support


## [3.2.0] - 2023-11-15

### Added

- Support for audit /v2/log and /v2/log_async endpoints


## [3.1.0] - 2023-11-09

### Added

- Presigned URL upload support on FileScan service
- Folder settings support in Vault service

## [3.0.0] - 2023-10-23

### Added

- AuthN v2 support

### Removed

- AuthN v1 support


## [2.3.0] - 2023-09-26

### Added

- FileScan Reversinglabs provider example
- Domain WhoIs endpoint support
- AuthN Filters support

### Changed

- Deprecated config_id in PangeaConfig. Now is set in service initialization.

### Fixed

- HashType supported in File Intel


## [2.2.0] - 2023-09-05

### Added

- Redact rulesets field support
- FileScan service support


## [2.1.0] - 2023-07-14

### Added

- Vault /folder/create endpoint support

### Fixed

- Update SDK version on audit examples


## [2.0.0] - 2023-07-06

### Changed

- Audit service allow to setup CustomSchema class on builder
- Audit.Log(), .Search() and .Results() method's signature changed to support CustomSchema and be scalable
- Reorganize service folders on /requests, /results, /responses and /models
- Rename IpIntelClient to IPIntelClient
- Rename UrlIntelClient to URLIntelClient
- Rename all builders from <ClassName>Builder to just Builder

### Removed

- All deprecated methods of Intel services


## [1.10.0] - 2023-06-26

### Added

- Multiconfig support
- Instructions to setup token and domain in examples


## [1.9.1] - 2023-06-09

### Added

- Defang examples
- Intel User breached password full example
- Vault docs

### Changed

- Update commons-codec package to fix vulnerability

### Fixed

- Vault Pangea token store method


## [1.9.0] - 2023-05-25

### Added

- New algorithm support in Vault Service
- Algorithm field support in Audit Service
- Cymru IP Intel provider examples
- Support full url as domain in config for local use


## [1.8.0] - 2023-05-05

### Added

- AuthN support
- Add retry settings on 500 errors

### Fixed

- Vault pangea token store without type


## [1.7.0] - 2023-04-10

### Added

- Audit-Vault signing integration support
- Intel User support
- Redact Service return_result field support
- Set custom user agent by config

## [1.6.0] - 2023-03-27

### Added

- Algorithm support in Vault Service

### Changed

- Algorithm name in Vault Service

## [1.5.0] - 2023-03-20

### Added

- Vault service support
- Count field support in Redact service
- Internal server exception support

### Changed

- Update services examples
- Improve docs

## [1.4.0] - 2023-03-01

### Added

- IP service add /geolocate, /vpn, /domain and /proxy endpoints support

## [1.3.0] - 2023-02-28

### Added

- Tenant ID support in Audit Service

## [1.2.0] - 2023-02-03

### Added

- Rules parameter support in Redact service
- Intel IP service sample apps

## [1.1.2] - 2023-01-27

### Changed
- Intel Domain, URL and File add reputation endpoint that will replace lookup endpoint

## [1.1.1] - 2023-01-25

### Changed

- Add /reputation endpoint in IP intel and mark /lookup as deprecated
- Change User-Agent format

### Added

- Count field in redact result

## [1.1.0] - 2023-01-05

### Added

- This CHANGELOG
- Samples apps to show how should be used each client
- SDK logo to show in Maven Central
- Intel File function to calculate SHA256 hash directly from path file
- Intel add IP/URL service lookup endpoint

### Fixed

- Make response classes public
- Vulnerabilities on external libs
- Function to get token and domain now receive environment (PROD/DEV/STG)

### Changed

- Unify token env var name on integration tests

### Removed

- Unreleased services functions


## [1.0.0] - 2022-12-01

### Added

- Audit client
- Embargo client
- File Intel client
- Domain Intel client
- Redact client

[unreleased]: https://github.com/pangeacyber/pangea-java/compare/v3.7.0...main
[3.7.0]: https://github.com/pangeacyber/pangea-java/compare/v3.6.0...v3.7.0
[3.6.0]: https://github.com/pangeacyber/pangea-java/compare/v3.5.0...v3.6.0
[3.5.0]: https://github.com/pangeacyber/pangea-java/compare/v3.4.0...v3.5.0
[3.4.0]: https://github.com/pangeacyber/pangea-java/compare/v3.3.0...v3.4.0
[3.3.0]: https://github.com/pangeacyber/pangea-java/compare/v3.2.0...v3.3.0
[3.2.0]: https://github.com/pangeacyber/pangea-java/compare/v3.1.0...v3.2.0
[3.1.0]: https://github.com/pangeacyber/pangea-java/compare/v3.0.0...v3.1.0
[3.0.0]: https://github.com/pangeacyber/pangea-java/compare/v2.3.0...v3.0.0
[2.2.0]: https://github.com/pangeacyber/pangea-java/compare/v2.2.0...v2.3.0
[2.2.0]: https://github.com/pangeacyber/pangea-java/compare/v2.1.0...v2.2.0
[2.1.0]: https://github.com/pangeacyber/pangea-java/compare/v2.0.0...v2.1.0
[2.0.0]: https://github.com/pangeacyber/pangea-java/compare/v1.10.0...v2.0.0
[1.10.0]: https://github.com/pangeacyber/pangea-java/compare/v1.9.1...v1.10.0
[1.9.1]: https://github.com/pangeacyber/pangea-java/compare/v1.9.0...v1.9.1
[1.9.0]: https://github.com/pangeacyber/pangea-java/compare/v1.8.0...v1.9.0
[1.8.0]: https://github.com/pangeacyber/pangea-java/compare/v1.7.0...v1.8.0
[1.7.0]: https://github.com/pangeacyber/pangea-java/compare/v1.6.0...v1.7.0
[1.6.0]: https://github.com/pangeacyber/pangea-java/compare/v1.5.0...v1.6.0
[1.5.0]: https://github.com/pangeacyber/pangea-java/compare/v1.4.0...v1.5.0
[1.4.0]: https://github.com/pangeacyber/pangea-java/compare/v1.3.0...v1.4.0
[1.3.0]: https://github.com/pangeacyber/pangea-java/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/pangeacyber/pangea-java/compare/v1.1.2...v1.2.0
[1.1.2]: https://github.com/pangeacyber/pangea-java/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/pangeacyber/pangea-java/compare/v1.1.0...v1.1.1
[1.1.0]: https://github.com/pangeacyber/pangea-java/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/pangeacyber/pangea-java/releases/tag/v1.0.0
