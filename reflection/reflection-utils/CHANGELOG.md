# Changelog

This file is used to list all changes between the versions of the module.

## [Unreleased]
No changes are currently being worked on and unreleased.

## [2.0.0] - TODO: Set release date
General improvements to the reflection utilities have been made.

### Added

- Added Access Utilities to the module. These utilities allow permanently
  modifying accessors of fields, methods, and constructors, as well as providing
  a shorthand for retrieving private elements in an accessible state.
- Added `NoSuchPackageException` to the module.

### Changed

- `ClassUtils#getClassesInPackage()` now throws a `NoSuchPackageException` when
  a package cannot be found. Previously, it threw a `ClassNotFoundException`.
- `ClassUtils#getClassesInPackage()` now returns a set that is guaranteed to not
  contain null elements. Previously, it returned a set that could contain null if
  the package contained an element that was not a class.
- All methods now forward `ReflectiveOperationException` instead of silently
  catching it and throwing a `RuntimeException`. This explicitly tells the
  caller to be aware that an exception can be thrown.
- Execute methods in `ClassUtils` have been moved to `MethodUtils` to better
  reflect their purpose.

## [1.0.0] - 2023-05-07
This is the first public release of the library. It is the accumulation of all
changes since the project was started in 2021. The project hasn't kept track of
changes until now, so this is the first changelog entry.

### Added
This is the first public release of the module.

### Changed
Nothing has been changed in this version.

### Deprecated
Nothing has been deprecated in this version.

### Removed
Nothing has been removed in this version

### Fixed
No fixes have been made in this version.

### Security
No security issues have been fixed in this version.