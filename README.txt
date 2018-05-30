Ready to deploy struckture can be found in the "Vislab" sub-directory

Launching the Vislab java program:
                                                                                                                                    All commands assume that your location is the folder where VislabController.jar is.



                                                                                                                                    CONFIG:
                                                                                                                                    There are config options available in the application.properties file:
                                                                                                                                    "MYSQL_ROOT_PASSWORD"  is the password for the user "root" in the mysql database
                                                                                                                                    "MYSQL_DATABASE" is the name of the database
                                                                                                                                    "MYSQL_USER" is the name of the user our program will use to access data and tables.
                                                                                                                                    "MYSQL_PASSWORD" is the password for the user our program uses.
                                                                                                                                    "SQL_ADDRESS" is the address for the database to use. When launched with docker compose, a container name is a valid address
                                                                                                                                    "SQL_PORT" the port for the database.

                                                                                                                                    SSL config. Replace fields and file ass neccesary.

                                                                                                                                    "server.ssl.key-alias" name for the keystore to use
                                                                                                                                    "server.ssl.key-password" password for the keystore
                                                                                                                                    "server.ssl.key-store" location of file. (IMPORTANT: must be prefixed with "file:")
                                                                                                                                    "server.ssl.key-store-provider" who provided the keystore
                                                                                                                                    "server.ssl.key-store-type" what type the keystore is


                                                                                                                                    STARTING
                                                                                                                                    Docker compose is configured  to work out-of-the-box, but changes are HIGHLY recommended, as username, passwords and databasename are weak and non descriptive.
                                                                                                                                    The first startup will use the config file to define what should be used later on.
                                                                                                                                    The MySql database conatiner and the java program is on a private network that can only be accessed through the open ports the docker-compose.yml,
                                                                                                                                    the containers can use this network to communicate and thus dont need to open the database to external access.


                                                                                                                                    "docker-compose up -d" will build  and start two containers, one with our java program and one MySql 5.7 database.

                                                                                                                                    STOPPING
                                                                                                                                    "docker-compose down" will stop both containers
                                                                                                                                    "docker rmi opt1:ver" will remove the given images (replace “opt1” with appropriate image name and “ver” with appropriate version.
                                                                                                                                    The MySql database container stores its info in a Docker Volume so information is not lost when rebuilding the image/container.
                                                                                                                                    "docker volume rm VOLUME_NAME" can be used to remove the volume to get a fresh database. ("docker volume ls" to see all the volumes.
