# FileMover
 Release - https://github.com/Ambrotus/FileMover/releases
 
This just recursively goes through your directories matching file extensions and moves them to another location. There are very few safeguards implemented to stop you from ruining your operating system.
This was a personal project to move a couple hundred thousand pictures from one hard drive to another while creating the original directory tree in the new location.
I tried to add some safeguards to stop you from using the root directory as a initial starting directory to stop you from removing any critical files. 

Java sdk seems to be required to run the executable rather than the java runtime library, I am assuming due to it being a fat jar.
It should be compatible with mac, linux, and windows.
