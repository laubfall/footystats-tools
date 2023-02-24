# Footystats-Tools


# Usage-Manual
## Generating the Download-CSV
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
