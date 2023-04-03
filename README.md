# Simple Batch Monitoring App
A simple console based batch monitoring system app for an educational Institute.

- The components of this abstract organizations are:
  - batch
  - Faculty
  - Faculty User
- Each batch has some assigned faculties to it
- Each faculty has some assigned batches to it
- In this organization, there is only one admin

The authorization rights of the admin is absolute.
The authorization rights the faculty member is limited to only their respective faculties.

This app aims to simplify the process of managing the batches and faculties in that organization. Soly build with Java programming language and under the hood, it uses a filesystem as it's database.

We can configure where the database root directory should be with the help of database.conf file. A root directory consists of different partitions (which are actually sub directory) and each partition has two things:
- A record.ser file: Mainly used to store the unique ids of the resources in that partition.
- A directory named "data": It contains multiple files and each file is one resource

For more information,visit the docs page: <a href="https://delicate-douhua-3a5b78.netlify.app/">Documentation</a>

This docs were build with the help of Java Documentation comments
