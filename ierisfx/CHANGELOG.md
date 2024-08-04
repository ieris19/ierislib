# Changelog

This file is used to list all changes between the versions of the module.

## [Unreleased]
No changes are currently being worked on and unreleased.

## [3.1.0] - 2024-08-04
This release increases the configuration manager capabilities and adds several
customization options. More information can be found in the new documentation
found at `ierisfx-config.html`

### Added
- The framework now supports extra configuration settings for the application.
  These settings can be used to customize the application further.
- The configuration has been documented in the new `ierisfx-config.html` file.
- You can now set the `StageStyle` of the application window.
- You can now set applications to start in fullscreen mode.
- You can now set the application to be always on top of other windows.

## [3.0.1] - ????-??-??
Undocumented bugfix release. This release was made to fix a bug that was found
in the previous version, but the changes were not documented.

## [3.0.0] - 2024-05-18
This major version reworks from the ground how JavaFX works

### Added
- The IerisFXBuilder class now handles setup for the project and allows to check
  for errors configuring before the application is launched. It aditionally 
  gathers the setup stage of a IerisFx into a single place, everything that can
  be configured is done through IerisFXBuilder.

### Changed
- UIComponents have been renamed to Views

## [2.0.0] - 2023-05-07 / 2023-05-14
This is the first public release of the library. It is the accumulation of all
changes since the project was started in 2021. The project hasn't kept track of
changes until now, so this is the first changelog entry.

### Added
This is the first public release of the module.

### Changed
Downgraded the Java version to 17.
This is to ensure that the library is compiled in the latest version of Java
that has long-term support(LTS). The library can now be compatible with recent
versions of Java, but also is compiled in a version of Java that is supported
for a long time, as projects don't always update to the latest version of Java.

### Deprecated
Nothing has been deprecated in this version.

### Removed
Nothing has been removed in this version

### Fixed
No fixes have been made in this version.

### Security
No security issues have been fixed in this version.