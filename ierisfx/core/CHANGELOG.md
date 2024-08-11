# Changelog

This file is used to list all changes between the versions of the module.

## [Unreleased]
### Proposed:
- Use reflection to automatically load views from a package.
- Dynamically update FxConfig class and documentation to include new settings.

## [3.2.3 - 3.3.0] - 2024-08-11
This release adds new features to the configuration manager and fixes bugs 
pertaining the window styles. Additionally, this release deprecates the old
configuration format and introduces a new format that is recommended to use
from now on.

This release coincides with the launch of a new ierisFx module. The 
`ierisFx-components` module is a new library of cool components that can be 
included into any JavaFx applications. They aim to provide functionality 
over design, and they are designed to be easily customizable. The components
are still WIP, but they are already usable and can be included in any JavaFx
project.

### Changed
- The configuration manager has been updated, now the properties are distributed
  across the `ierislib` and `ierislib.extras` sections, the first are mandatory
  and the second are optional. The `FXConfigurer` class has been updated to
  reflect this change. Until version 4.0.0, it will be possible to use the old
  configuration format, but it is recommended to update to the new format as soon
  as possible.
- When opening a new view with a different size to the currently active view, 
  the `ViewManager` now ensures the views stay centered. The `ViewManager` now
  calculate the difference between the sizes and reposition the views to 
  ensure the center stays the same. This prevents the view from getting off 
  center when changing the view size, while still allowing users to 
  reposition windows as needed because it doesn't recenter on the screen at 
  every view change.

### Added
- The configuration manager now has its own documentation that is an easily 
  viewable HTML file. The documentation is hand written, and all necessary 
  resources (CSS & JS) are embedded directly into the HTML file.
- Scene fill color can now be set in the configuration manager. This setting
  allows the user to set the color of the scene fill, which is essentially 
  the color that JavaFx will use to fill the scene (behind the root node). 
  This now fixes and enables support for fully transparent scenes, 
  non-rectangular windows and much more that was broken in the previous
  versions.

### Fixed
- Thanks to the SceneFill property, TRANSPARENT window style now works 
  correctly. Check the configuration documentation for further information 
  about how the two interact.

### Deprecated
- The configuration manager now has a new format for the configuration file.
  The old format is still supported, but it is recommended to update to the
  new format as soon as possible.

## [3.2.2] - 2024-08-05
This release fixes bugs that were found in the previous version.

### Fixed
- The `FXConfigurer` class was not properly setting the configuration values
  when the `load` method was called, and it was reading different keys than the
  ones that were documented. It should now properly read configuration values
  as documented.
- The way that the `ViewManager` would set the new properties resulted in a 
  conflict between fullscreen and windowStyle. The `ViewManager` now sets the
  `StageStyle` conditionally based on whether the default style is used or not,
   as such, setting the config value to `DECORATED` will now work as expected
   when combined with fullscreen mode. This bug was observed on Gnome 46

## [3.2.1] - 2024-08-04
This release bumps the version of the `ierislib.commons` package from `1.0.0`

## [3.2.0] - 2024-08-04
This release changes the way the configuration manager works and improves the
way that configuration is handled and the way that the configuration is used.
The changes are slightly breaking, but keeping the major version the same since
the configuration package is somewhat internal. The main change required to
update is to rename the `FXConfig` class to `FXConfigurer` unless your application
uses ierisFx in non-standard ways.

### Changed
- The `FXConfig` class has been revamped!
  - The name has changed to `FXConfigurer`.
  - The class is now a builder class that can be used to load config files, alter
    the values and export to the new `FXConfiguration` class.
  - The `FXConfigurer` class now has getters and setters for all the configuration
    values.
- The `FXConfiguration` class is now a class that holds the configuration values
  and can be used to get the values from the configuration.

### Fixed
- There were several leftover snippets that were not removed from the codebase
  when the configuration manager was updated in the previous release. These have
  been removed in this release.

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