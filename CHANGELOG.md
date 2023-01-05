# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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


[unreleased]: https://github.com/pangeacyber/pangea-java/compare/v1.1.0...main
[1.1.0]: https://github.com/pangeacyber/pangea-java/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/pangeacyber/pangea-java/releases/tag/v1.0.0
