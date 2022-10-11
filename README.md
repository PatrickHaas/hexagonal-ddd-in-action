# ddd-in-action-employees

(work in progress)

A project to illustrate how ddd building blocks can be used to encapsulate your domain logic and parallel
satisfy the rules of a hexagonal architecture.
I tried reaching this goal by putting all code depending on a framework like spring in the infrastructure packages.

# bounded contexts

Each bounded context gets its own package, but it could also be a completely different application / deployment unit -
that's the point of a bounded context really.
For simplicity we're doing it in packages and ensure the architectural rules via [ArchUnit](https://www.archunit.org/).

# tests

Please keep in mind, that this is a sample project and that the coverage rates of this project are not as good as they
could be.