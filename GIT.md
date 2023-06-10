# Version Control Strategy

A well-structured repository needs a good structure to be followed. While this
project did not necessarily have that from the beginning, adding one later on is
always better than nothing. That is the reason of this document, and it should
help clear any doubts about the desired conventions to be followed by this project.

Some of these guidelines are not strictly enforced, but it is strongly encouraged
to follow them as tightly as possible.

These guidelines are not set in stone, and they are subject to change.
If you have any suggestions, please open an issue or a pull request.
After all, the goal of this document is to help the project and improve
the development experience.

## General Considerations

The chosen tool for version control in for this project is Git.
For more information, please refer to the [official website](https://git-scm.com).

Additionally, the repository is hosted on [GitHub](https://github.com/Ieris19/IerisLib),
where the "origin" remote is located. Further references to origin will refer
to the repository hosted there, as opposed to local repositories where developers
make their changes.

Finally, although usual, the official language of this project is English, and
as such, it should be the natural language used across the project, specially
within the version control system.

## Commits

### Commit Goals

Commits are supposed to be units of changes. As such, they should contain the
smallest possible number of changes individually. In general, if your commit
can't be described with a single verb (eg. add, fix, delete, etc.) then it is
likely the commit could be broken down into multiple.

### Commit Titles

Commit titles should be short and concise, no longer than a few words, but
ideally no longer than ~30 characters. While short, they should still be
descriptive enough to understand what the goal of the commit is.

The title should be written in the present tense, as
if it was a command. For example, "Add new module" or "Fix bug".

### Commit Messages

Commit messages should be longer and more descriptive than the title.  
Small, concise commits can get away with having no message whatsoever,
for example, it wouldn't be necessary to further elaborate a commit
called "Updated [Dependency Name]".

For all other commits, the message should explain at least:

* What the commit does, the files or sections it modifies...
* Why the changes, changes are supposed to be meaningfully contributing towards
  a specific goal.
* Any other special considerations to be taken, such as changes to watch out for

The message should be written impersonally, preferably in the passive voice.
For example, "The new module is added to the project" or "The bug is fixed
by..."

## Branches

### Naming Convention

Git branches can be named anything really. 
However, in order to stay consistent, a convention must be established:

**Main Branch** 
The branch where the most up to date, stable and approved code lives is called
"prime", it is named such because, like prime numbers, and it is the base of 
all other branches

**Branching Changes** 
Branches where new changes are taking place should follow this pattern:  
*{module-affected}/{overarching-branch-goal}*  
This pattern ensures the context is always clear. As far as the contents,
the module name should match the artifactId of the module being modified, while
the overarching goal should define a general direction for the branch. For
example, commons/2.0.0 could be a good branch name if the goal was to completely
rewrite the common module, or for example, ieris-log/exception-handling would
be a branch that aims to modify/improve/create exception handling in the ieris-log
module

Additionally, for non-module specific changes, another descriptive scope should
be used. A short list of the commonly recurring scopes is as follows

- **document/** for those branches that only modified documents
- **maven/** for those branches that work on dependencies and/or maven infrastructure
- **hotfix/** for those branches that include minute changes or urgent bugfixes

Consider breaking the overarching goal into further categories if necessary, such
as document/ieris-log/javadoc, for example

## Releases

The word release can mean multiple things depending on the context:

* Library releases are published as GitHub Releases.
* Module releases are published as GitHub Packages.

Additionally, other changes might be considered smaller releases, that alter the
naming scheme of the releases, but are not necessarily published in the same way.

### Library Releases

IerisLib releases are numbered X.Y.Z, where X is the major version, Y is the
minor version, and Z is the current iteration.

The major version is incremented when the library is re-structured or
rewritten in any major way. This means that different major versions of the
library should be widely different from each other, and that they might not be
compatible with each other. These vast changes are not expected to happen often,
but they are a possibility. Usually, they are accompanied by a new structure
for the packages and namespaces, but individual modules might be compatible with
a simple refactor. This is not guaranteed, however.

The minor version is updated when new modules are released. Alternatively, when
a significant number of changes are made to the numerous modules, it can also be
a reason to increase the minor version, since the library would be measurably
different.

The iteration is updated when a new module changes major or minor versions,
however, while the major and minor versions receive a new GitHub release, the
iterations will only receive a new commit in the release branch.

### Module Releases

The releases for each module are decided by the Semantic Versioning standard
that can be found [here](https://semver.org/). Each major/minor version of the
module will be merged into the release branch, but only as iterations. Patches
will be merged into main, but not into the release branch until the next
major/minor version of the module is released.
