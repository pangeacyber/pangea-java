# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# Added

- Defang examples
- Intel User breached password full example
- Vault docs

# Changed

- Update commons-codec package to fix vulnerability

# Fixed

- Vault Pangea token store method


## [1.9.0] - 2023-05-25

# Added

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

###

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


[unreleased]: https://github.com/pangeacyber/pangea-java/compare/v1.9.1...main
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
