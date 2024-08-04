# Changelog

This file is used to list all changes between the versions of the module.

## [Unreleased]
### Planned
- Add SPI support for the `ConfigManager` class so that it can be loaded dynamically
  from the classpath.

## [3.0.0] - 2025-08-3
This release breaks the public API of the module. In doing so, it establishes a
new API that is much more flexible and easier to use. It should NOT be a problem
to upgrade to this version, as the changes are minimal and the module is still
mostly backwards compatible. The only breaking change is the moving of the
`loadProperties()`, `save()` and `getPropertiesPath()` and the packages that
have been reorganized.

### Added
- Added a new `ConfigManager` class! `MemoryConfigManager` stores config in a Hashmap, with
  all the convenience of the `ConfigManager` API.

### Changed
- The `loadProperties()`, `save()` and `getPropertiesPath()` methods have been
  moved to the `ConfigManager` class.
- The packages have been reorganized to make more sense and reduce module API.
- The `ConfigFactory` class has been refactored to use the new configuration
  system.
- The `ConfigFormat` now registers a key type for the `ConfigFactory`, which 
  serves as both a unique identifier and a parameter for the `ConfigFactory`
- The `BaseConfigManager` class has been refactored, the methods that are now
  in the `FileConfigManager` class have been moved to `BaseFileConfigManager`

## [2.0.0] - 2023-05-13
This release is a major release that changes completely the public API of the
module. The module has been refactored to use a new configuration system that
is more flexible and less cumbersome to use.

### Added
- Added a new configuration system that is more flexible and less cumbersome to
  use.

### Changed
- The module has been refactored to use the new configuration system.
- Optional values are now returned over raising an exception.

### Removed
- File, Dynamic and Global properties have been removed prefering instead to
  use URI based configuration locations.

## [1.0.0] - 2023-05-07 / 2023-05-14
This is the first public release of the library. It is the accumulation of all
changes since the project was started in 2021. The project hasn't kept track of
changes until now, so this is the first changelog entry.

### Added
This is the first public release of the module.

### Changed
Nothing has changed in this version.

### Deprecated
Nothing has been deprecated in this version.

### Removed
Nothing has been removed in this version

### Fixed
No fixes have been made in this version.

### Security
No security issues have been fixed in this version.