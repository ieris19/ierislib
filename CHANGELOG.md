# Changelog

This file is used to list all changes between the versions of the library.
Each module has their own changelog, and as such, this file will only refer to
project-wide changes, or indicate when a module has been updated.

## [Unreleased]
No changes are currently being worked on and unreleased.

## [2.1.0] - 2023-05-14
Released the packages through GitHub Packages. 
This is the first release that is available through GitHub Packages, 
and the structure of the library has changed. Although these changes would 
normally be considered major, being the first time the packages are available 
online, the version change will instead be minor.

### Added
No new features have been added in this version.

### Changed
Downgraded the Java version to 17.
This is to ensure that the library is compiled in the latest version of Java 
that has long-term support(LTS). The library can now be compatible with recent 
versions of Java, but also is compiled in a version of Java that is supported
for a long time, as projects don't always update to the latest version of Java.

The following modules have changed locators in this version:

- com.ieris19.lib.common:common:1.0.0 -> 
  com.ieris19.lib:commons:1.0.0

- com.ieris19.lib.economy:currency:1.0.0 ->
  com.ieris19.lib:currency-handler:1.0.0

- com.ieris19.lib.economy:gamified:1.0.0 ->
  com.ieris19.lib.economy:game-economy:1.0.0

- com.ieris19.lib.files:assets:1.0.0 ->
  com.ieris19.lib.files:assets-handler:1.0.0

- com.ieris19.lib.files:config:1.0.0 ->
  com.ieris19.lib.files:config-manager:1.0.0

- com.ieris19.lib.util:console:1.0.0 ->
  com.ieris19.lib.util:command-console:1.0.0

### Deprecated
No modules have been deprecated in this version:


### Removed
The following modules have been deprecated in this version:

* Extensible Annotation Processor (ierislib.reflection.annotations)

### Fixed
No fixes have been made in this version.

### Security
No security issues have been fixed in this version.

## [2.0.0] - 2023-05-07
This is the first public release of the library. It is the accumulation of all
changes since the project was started in 2021. The project hasn't kept track of
changes until now, so this is the first changelog entry.

### Added
The following modules have been added in this version, in order of inception 
(oldest first). The order might be slightly off, as I am listing from memory, 
but it should be close enough.

* Game Cards (ierislib.games.cards)
* Command Console (ierislib.util.console)
* Common Dependencies (ierislib.common)
* Scoreboard (ierislib.util.scoreboard)
* IerisLog Core (ierislib.log.core)
* Property Manager (ierislib.files.config)
* IerisLog Custom (ierislib.log.custom)
* IerisFX (ierislib.ui)
* Assets Handler (ierislib.files.assets
* Reflection Utils (ierislib.reflection.utils)
* Game Dice (ierislib.games.dice)
* IerisLog SLF4J (ierislib.log.slf4j)

### Changed
No changes have been made in this version.

### Deprecated
The following modules have been deprecated in this version:

* Extensible Annotation Processor (ierislib.reflection.annotations)

### Removed
No modules have been removed in this version.

### Fixed
No fixes have been made in this version.

### Security
No security issues have been fixed in this version.