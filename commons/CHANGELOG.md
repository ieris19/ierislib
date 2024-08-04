# Changelog

This file is used to list all changes between the versions of the module.

## [Unreleased]
No changes are currently being worked on and unreleased.

## [3.2.0] - 2024-08-05
### Added
- Added functions to the StringUtils class:
  - `StringUtils#capitalize(String)`: Capitalizes the first letter of a string
  - `StringUtils#removeMatching(String, String)`: Removes all occurrences of a substring from a string
  - `StringUtils#keepMatching(String, String)`: Removes all non-matching characters from a string

## [3.1.0] - 2024-07-28
### Added
- Added the math utilities to the commons module. For now, it includes a hexadecimal
  helper class that can convert byte arrays to Strings and vice versa.

## [3.0.0] - 2024-07-28
A reworked version of the Commons library that now contains further utilities for
text manipulation and Null safety. Major release since renaming and reworkig the
existing API is not backwards compatible.

## [2.0.0] - 2023-06-02
A reworked version of the Commons dependencies, which now contains mostly text utilities (and a Script interface).

### Changed
- TextColor class has been moved to the commons module


## [1.0.0] - 2023-05-07 / 2023-05-14
This is the first public release of the library. It is the accumulation of all
changes since the project was started in 2021. The project hasn't kept track of
changes until now, so this is the first changelog entry.

### Added
This is the first public release of the module.

### Changed
The locator has been changed and thus the version released twice
- com.ieris19.lib.common:common:1.0.0 ->
  com.ieris19.lib:commons:1.0.0

### Deprecated
Nothing has been deprecated in this version.

### Removed
Nothing has been removed in this version

### Fixed
No fixes have been made in this version.

### Security
No security issues have been fixed in this version.