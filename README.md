# IerisLib

IerisLib is a passion project for me, it is a collection of code that I have 
written over the course of my studies, as part of my projects and assignments,
that I have decided to extract from said projects and turn into reusable modules

As such, this library is not perfect, it was developed as a learning experience,
where I learned how to write better code, how to use tools such as Git and Maven

All I hope is that looking back at this library in the future, I can see how 
much I have grown as a programmer through my studies and my career. And maybe, 
just maybe, someone else can find this library useful for their own projects

Until then, I will keep adding to this library as I learn new things that I want
to show off or that I want to reuse in future projects. Some of this code might
be useful, some of it is only developed for fun, but all of it is code that I am
proud of

Even though the project has been through many restructuring phases, I have tried
list all the modules that are included in order of inception (oldest first) at 
least inside each category. I have also tried to include a short description of
each module.

* [IerisLib](#ierislib)
  * [Game Utilities](#game-utilities)
  * [General Utilities](#general-utilities)
  * [Common Dependencies](#common-dependencies)
  * [External Resource Management](#external-resource-management)
  * [IerisFx](#IerisFX)

## Game Utilities

Game development is a complex field, and it is one that I have always had an
interest for. However, Java is not the language with the best support for game
development, so I have made little progress in this field. Nonetheless, I have
created a few modules that aid the creation of (mostly text-based) games in Java

These modules are mostly a pet project, but they were the origin of this project
even before I had knowledge of what git was. 

### Included Modules

* Game Cards (ierislib.games.cards) (Since 2.0.0)
* Game Dice (ierislib.games.dice) (Since 2.0.0)

--------------------------------------------------------------------------------

## General Utilities

Projects comes in all shapes and sizes, but there are some things that are
common to many projects. Things such as logging are always necessary. This 
module includes several submodules of varying usefulness that I have created.

The project I am most proud of in this section is my logging framework, Ierislog
which is its own little library that I have used in many projects. 

### Included Modules

* Command Console (ierislib.util.console) (Since 2.0.0)
* Scoreboard (ierislib.util.scoreboard) (Since 2.0.0)
### IerisLog (ierislib.util.log)

IerisLog is a logging framework that I have developed for use in my projects. It
is a simple logging framework that is meant to be easy to use and to integrate
into any project. It started as a little assignment to practice design patterns
such as singleton and multiton, but it has become so much more as I use it and
add more features to it.

Currently, IerisLog is split into multiple modules:

* IerisLog Core (ierislib.log.core) - Contains the core functionality (Since 2.0.0)
* IerisLog Custom (ierislib.log.custom) - Allows for customizing logger output (Since 2.0.0)
* IerisLog SLF4J (ierislib.log.slf4j) - Allows for using IerisLog with SLF4J (Since 2.0.0)

--------------------------------------------------------------------------------

## Common Dependencies

Coding best practices include a principle called DRY (Don't Repeat Yourself). 
Following this principle means that sometimes, I find myself with small classes
that I use in multiple projects. This is the reason I created this module, to
contain all the code that I find myself reusing in multiple projects, but is not
big enough to warrant its own module.

### Included Modules

* Common Dependencies (ierislib.common) (Since 2.0.0)

--------------------------------------------------------------------------------

## External Resource Management

Accessing and managing resources and properties is a task that is somewhat 
complex and having a good framework to manage them is always useful. This is the
reason I created this module, to manage configuration and assets files in a way
that is familiar to me and simple to use.

### Included Modules

* Property Manager (ierislib.files.config) (Since 2.0.0)
* Assets Handler (ierislib.files.assets) (Since 2.0.0)

--------------------------------------------------------------------------------

## IerisFX

User Interfaces are a complex system that is often cumbersome and difficult to
manage. During my studies I found each an every one of my projects requiring the
same similar code to create a simple UI. This is the reason I decided to
design my own abstraction of those common classes so that they can be reused in
any kind of project. This is not meant to be comprehensive, one-size-fits-all,
but rather a simplified version of the way I have personally structured my UIs

It is important to note that this scaffold is not a UI framework, it is simply
an addition to JavaFX that I have found useful in my projects.

Since 2.0.0

--------------------------------------------------------------------------------

## Reflection Utilities

Java Reflection is a powerful tool that can be used to do many things. This
module is a collection of utilities that I have found useful when working with
Java Reflection. It is just a small playground more than something really useful
for anyone else, but it is still fun to write. Meta-programming is fun.

### Included Modules

* Reflection Utils (ierislib.reflection.utils) (Since 2.0.0)
* Extensible Annotation Processor (ierislib.reflection.annotations) (Since 2.0.0)

--------------------------------------------------------------------------------

Thanks for reading! I hope you enjoy this library as much as I do.  
Feel free to contribute to this project if you want and remember that this is
licensed under the Apache 2.0 License, so you can use it however you want. Feel
free to fork it too!