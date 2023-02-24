# Footystats-Tools
Footystats-Tools is a bet prediction web application that does its predictions based on footystats (footystats.org) csv-files. To use this software you need an account at footystats.org in order to download the necessary csv-files and deploy this application for your own on your private network.

# Usage-Manual
## Generating the Download-CSV
For some bet predictions footystats-tools use Team-/League- and Player-Stats csv-files from footystats.org. Cause there are a lot of them footystats-tools has a mechanism to automatically download these files. That this is possible you have to provide a special csv file that you must create. This file contains the download-ids given by footystats.org so footystats-tools can create the download-link and download the file. This section describes the steps to create the download csv file.

JQuery to print the downloadlinks and country and league to console

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

Regex for finding the season:
```
( - )(\d*/?\d*)
```
