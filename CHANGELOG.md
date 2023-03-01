# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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


[unreleased]: https://github.com/pangeacyber/pangea-java/compare/v1.4.0...main
[1.4.0]: https://github.com/pangeacyber/pangea-java/compare/v1.4.0...v1.4.0
[1.3.0]: https://github.com/pangeacyber/pangea-java/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/pangeacyber/pangea-java/compare/v1.1.2...v1.2.0
[1.1.2]: https://github.com/pangeacyber/pangea-java/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/pangeacyber/pangea-java/compare/v1.1.0...v1.1.1
[1.1.0]: https://github.com/pangeacyber/pangea-java/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/pangeacyber/pangea-java/releases/tag/v1.0.0
