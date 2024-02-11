# Footystats-Tools

Footystats-Tools is a bet prediction web application that does its predictions based on footystats (footystats.org) csv-files. To use this software
you need an account at footystats.org in order to download the necessary csv-files and deploy this application for your own on your private network.
There are no plans to host this application on the public internet!

## Project-Setup

Actually no build binaries exist so if you want to deploy the application or just want to code with me (PRs welcome :) ) you need to do some initial
setup.

### Requirements

* Java 21 JDK
* Node.js 18
* Maven
* Docker
* An IDE (like Intellij or Visual Studio Code)
* Openapi-Generator installed globally as a npm package.

### Development Environment

First switch to footystats-tool-backend/docker/mongo directory and execute the script start-mongo-container.sh. This scripty fires up docker-compose
which creates a mongodb and mongo-express container. If this is done the script prepares the mongodb 'converting' it into a replicaset. This is
necessary in order to make Spring Transactions work with the mongodb and it is the only reason why a shell script is needed to create and start the
containers. So if the container exists you can start them via docker-compose start.

Next step is starting backend and frontend. This is fairly simple, create a run configuration for both applications (steps may vary depending on your
IDE) and start these. For the backend application make sure to activate the Spring Profile 'dev'.

## Development

### Changing Rest-API

Changing the Rest Controller means that these changes need to be made inside the frontend code (footystats-tools-frontend) as well. Building the
backend code with maven and profile 'openapi' generated a swagger file that is used by openapi generator that creates or rebuild the frontend code.
The build command is like this (make sure the mongodb is up and running, s. chapter Developement Environment):

```mvn install -DskipTests -P openapi```

If maven executed without errors a swagger openapi file was generated an all the frontend code is generated.

## Usage-Manual

### Automatic Footystats CSV File Download

Footystats provides besides the MatchStats CSV Files a lot ofher files with useful soccer stats. For example League, Team and Player CSV Files. The
MatchStats CSV can easily downloaded by Footystats-Tools cause the URL is build upon a unix timestamp, sadly that is not the case for the other files,
these have URLs with a non-predictable identifier, we have to do some manual work so automatic download is possible. If you only want the MatchStats
just save your footystats credentials in footystats-tools via web-gui.

#### Download Times

MatchStats CSV Files are downloaded at 00.00 and 12.00. All other files are downloaded only if configured in the download csv file (s. next section)
and if when footystats-tools does match prediction. E.g. match prediction is calculated for a german Bundesliga match, then footystats-tools checks if
the german Bundesliga Team CSV Files are available and if not it downloads them.

#### Generating the Download-CSV-Config for non-MatchStats-Csv-Files

For some bet predictions footystats-tools use Team-/League- and Player-Stats csv-files from footystats.org. Cause there are a lot of them
footystats-tools has a mechanism to automatically download these files. That this is possible you have to provide a special csv file that you must
create. This file contains the download-ids given by footystats.org so footystats-tools can create the download-link and download the file. This
section describes the steps to create the download csv file.

First go to the CSV download page of footystats.org (https://footystats.org/download-stats-csv) and select the desired season. Important: choose a
season before exectuing the following steps. Doing these steps for 'current season' will result in a useless download config file.

Now open the browsers developer tools and use this little JQuery script to print the downloadlinks and country and league to console:

```
$('h5.semi-bold').each((iT, eT) => {
	const country = $(eT).text();
	$(eT).siblings().find('tr').each((i, eTr) => {
			const league = $(eTr).find('td:first p').text();
			if(league != ''){
				const href = $(eTr).find('td a').filter(':first').attr('href')
				console.log(country+ "," +league + "," + href);
			}
		})
	})
```

Save the console output to a file with utf-8. Make sure the file name starts with 'download_config', otherwise fileupload into footystats-tools is not
possible. Then it is time to fire up any good text editor to do some search and replace.

Regex for finding the season, we use this to get rid of the '-' between country and season. With any good text editor you can replace the '-' with
second regex group (the season). Search with this regex....

```
( - )(\d*/?\d*)
```

... and use this as replacement:

```    ,$2```

Now its time to get the download id. Use this Regex to find and remove the dl URL part:

```
/c-dl.php\?type=\w*\&comp=
```

As replacement use simply a comma.

Then add the following header to the file:
```country,season,league,footyStatsDlId,downloadBitmask```

Now fill the fifth column with the desired bitmask. If you don't have excel you can do this again with regex search and replacement:
```(\d{4})$```
This regex matches the download id, replace it with the first regex group (the download id) and the download bitmask. Bitmask is defined as:

* 1: League CSV
* 2: Team CSV
* 4: Team 2 CSV
* 8: Player CSV
* 16: Match CSV

Setting the bits as described will result in a download of the csv file configured for the chosen season. If you want to download all files set the
bitmask to 31. If you want to download only the league csv file set the bitmask to 1. If you want to download the league and team csv file set the
bitmask to 3 and so on. If you don't want to download any file set the bitmask to 0 or leave it empty.

## Setup a standalone server

### Requirements

* Java 21 JDK
* Node.js 18
* MongoDB Replica Set
* Footystats-Tools Build (Frontend / Backend)
	* application-prod.properties (Customize this file to your needs, based on application.properties)
