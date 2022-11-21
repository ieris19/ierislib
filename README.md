# IerisLib

A home for all the code that I might want to reuse in future projects
Most of this code is made for fun and practice and most of it is not really ever
to be used as there are usually much better alternatives to my own packages, but
it's always good to have software do *EXACTLY* what you want it to do and *JUST*
how you want it to do it.

It is also worth mentioning that most of this code has been written along my
journey through my software engineering degree, meaning that some of this code I
wrote when I barely knew anything at all, while other pieces of code could have
been written at different stages of my journey.

Overall, this repository should host many small pet projects in order for me to
be able to reuse or just as pure practice. Occasionally some might be built to
showcase specific skills or features.

The following sections detail the project structure and the different projects
included in them. Each level is ordered chronologically in order of inception.

* [IerisLib](#ierislib)
  * [Game Utilities](#game-utilities)
    * [Included Modules](#included-modules)
  * [General Utilities](#general-utilities)
    * [Included Modules](#included-modules)
  * [Common Dependencies](#common-dependencies)
    * [Included Modules](#included-modules)
  * [External Resource Management](#external-resource-management)
    * [Included Modules](#included-modules)
  * [User Interface Scaffolding](#user-interface-scaffolding)
    * [Included Modules](#included-modules)
  * [Archimedes Project](#archimedes-project)
    * [Included Modules](#included-modules)
  * [WIP](#wip)

## Game Utilities

As an avid gamer, I have always wanted to make my own games. This section
includes all the code that I have written in order to make my own games. It is
not much, but it includes some components that I have found useful developing
my own mini-games, mostly text-based and for the purpose of learning.

### Included Modules

* Game Cards (ierislib.games.cards)
* Game Dice (ierislib.games.dice)
* Economy (ierislib.games.economy)

--------------------------------------------------------------------------------

## General Utilities

As a programmer, I've run into many situations where I needed to do something
that was not really a part of the core functionality of the project, but was
needed nonetheless. This section includes all the code that I have written in
order to make my life easier as a programmer. Most of this code is not really
useful for anyone else, but it is still fun to write. Thus, I've created these
simple APIs that I can implement into my programs repeatedly.

### Included Modules

* Command Console (ierislib.util.console)
* Scoreboard (ierislib.util.scoreboard)
* IerisLog (ierislib.util.log)

--------------------------------------------------------------------------------

## Common Dependencies

As I write more and more code for this library, I have found that I had a few
classes that I was using in multiple projects, or that were very generic tiny
utilities that I needed to use across multiple projects.

### Included Modules

* Common Dependencies (ierislib.common)

--------------------------------------------------------------------------------

## External Resource Management

As projects get more complex, they tend to require more external resources.
This section includes all the code that I have written in order to manage
external resources, such as configs, asset files, etc.

### Included Modules

* Property Handler (ierislib.files.config)
* Assets Handler (ierislib.files.assets)

--------------------------------------------------------------------------------

## User Interface Scaffolding

Just like external resources, user interfaces tend to get more complex as the
project grows. This is the reason I decided to develop a simple UI framework
that builds upon the JavaFX library. This framework is not meant to be a
comprehensive, all-in-one solution for UI development, but rather a simple
scaffolding to help me build my own User Interfaces faster by bundling some
boilerplate generified code I would otherwise have to write every time

### Included Modules

* IerisFX (ierislib.ui)

--------------------------------------------------------------------------------

## Archimedes Project

This project is honestly something that started as assignments for my Discrete
Maths and Algorithms class in college. I simply cut out some useful operations
and put them in this module. It is not much, but I just can't bring myself to
delete it.

### Included Modules

* Math Library (ierislib.math.archimedes)

--------------------------------------------------------------------------------

## Reflection Utilities

Java Reflection is a powerful tool that can be used to do many things. This
module is a collection of utilities that I have found useful when working with
Java Reflection. It is just a small playground more than something really useful
for anyone else, but it is still fun to write. Meta-programming is fun.

### Included Modules

* Reflection Utils (ierislib.reflection.utils)
* Extensible Annotation Processor (ierislib.reflection.annotations)

--------------------------------------------------------------------------------
## WIP

This library is still a work in progress. I will be adding more modules as I
need them or as I get new inspiration and ideas.

Finally, here is a list of ideas I have for future modules:

* Security Handler (ierislib.crypto)
* Database Handler (ierislib.files.db)
* Network Handler (ierislib.network.utils)
* SLF4J Logger (ierislib.util.slf4j)
* Logger UI Service (ierislib.network.log)

--------------------------------------------------------------------------------

Thanks for reading! I hope you enjoy this library as much as I do.  
Feel free to contribute to this project if you want and remember that this is
licensed under the Apache 2.0 License, so you can use it however you want. Feel
free to fork it too!