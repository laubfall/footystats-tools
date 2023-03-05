# Footystats-Tools
Footystats-Tools is a bet prediction web application that does its predictions based on footystats (footystats.org) csv-files. To use this software you need an account at footystats.org in order to download the necessary csv-files and deploy this application for your own on your private network. There are no plans to host this application on the public internet!

# Project-Setup
Actually no build binaries exist so if you want to deploy the application or just want to code with me (PRs welcome :) ) you need to do some initial setup. 

## Requirements
* Java 17 JDK
* Node.js 18
* Maven
* Docker
* An IDE (like Intellij or Visual Studio Code)

# Usage-Manual
## Setup a standalone server
## Generating the Download-CSV
For some bet predictions footystats-tools use Team-/League- and Player-Stats csv-files from footystats.org. Cause there are a lot of them footystats-tools has a mechanism to automatically download these files. That this is possible you have to provide a special csv file that you must create. This file contains the download-ids given by footystats.org so footystats-tools can create the download-link and download the file. This section describes the steps to create the download csv file.

Use this little JQuery script to print the downloadlinks and country and league to console:
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
Save the console output to a file. Then its time to fire a up any good text editor to do some search and replace.

Regex for finding the season, we use this to get rid of the '-' between country and season. With any good text editor you can replace the '-' with second regex group (the season).
```
( - )(\d*/?\d*)
```

Regex to find and remove the dl URL part, we only want the download id:
```
/c-dl.php\?type=\w*\&comp=
```

Then add the following header:
```country,season,league,footyStatsDlId,downloadBitmask```

Now fill the fifth column with the desired bitmask. If you don't have excel you can do this again with regex search and replacement:
```(\d{4})$```
This regex matches the download id, replace it with the first regex group (the download id) and the download bitmask.
