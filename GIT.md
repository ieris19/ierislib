# GitHub Strategy
As the library and my knowledge of the tools grow larger, implementing a clear
strategy for how to use GitHub becomes more and more useful. This document will
contain all the information about the vision for how to use git in this project,
GitHub releases and packages, and other related topics.

## Commits
### Commit Titles
Commit titles should be short and concise, but still descriptive enough to
understand what the commit is about. The title should be written in the present
tense, as if it was a command. For example, "Add new module" or "Fix bug"

### Commit Messages
Commit messages should be longer and more descriptive than the title. The
message should explain what the commit does, why it does it, and how it does
it. The message should be written impersonally, preferably in the passive voice.
For example, "The new module is added to the project" or "The bug is fixed 
by..."

### Commit Frequency
Commits should be small, concise, and frequent. A commit should only contain the
smallest amount of changes that can be considered a complete unit. For example,
a bad commit would include a new feature, some bugfixes and all spread over 
different modules, while a good commit would include concise changes that can be
easily described in a single sentence.

If this rule isn't followed during development, it is possible to do multiple
partial commits of the current changes to achieve a similar result.

## Branches
### Branch Strategy
The branch strategy is as follows:
 * A main branch where code is in a working state at all times, and where
   releases are made from.
 * A release branch for each release, where the code is in a working state and
   each commit corresponds to a release of the software.
 * Smaller development branches for individual features, bugfixes, etc. These
   branches are merged into the main branch when they are finished in the way of
   a squash merge following a pull request.

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
The releases for each module are decided  by the Semantic Versioning standard 
that can be found [here](https://semver.org/). Each major/minor version of the
module will be merged into the release branch, but only as iterations. Patches
will be merged into main, but not into the release branch until the next 
major/minor version of the module is released.
