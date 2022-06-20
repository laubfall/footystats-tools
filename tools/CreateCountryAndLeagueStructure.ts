import { Country } from '../src/app/types/application/AppControll';

const cals = `
Albania,Superliga
Albania,First Division
Albania,Second Division
Algeria,Ligue 2
Algeria,Ligue 1
Andorra,Segona Divisió
Angola,Girabola
Argentina,Prim B Nacional
Argentina,Primera División
Argentina,Reserve League
Argentina,Prim B Metro
Armenia,First League
Aruba,Division di Honor
Australia,Northern NSW Reserve League
Australia,Victoria NPL Youth League
Australia,Victoria NPL 2 Youth
Australia,Capital Territory NPL Youth League
Australia,New South Wales NPL Youth League
Australia,South Australia State League 1 Reserves
Australia,A-League
Australia,Capital Territory NPL
Australia,Capital Territory NPL 2
Australia,Victoria NPL 2
Australia,Victoria NPL 3
Australia,Victoria NPL Women
Australia,Northern NSW NPL
Australia,Northern NSW State League 1
Australia,Victoria NPL
Australia,New South Wales NPL
Australia,South Australia NPL
Australia,South Australia State League 1
Australia,Queensland NPL Youth League
Australia,New South Wales NPL 2
Australia,Queensland NPL
Australia,Queensland Premier League 2
Australia,Queensland Premier League
Australia,Queensland NPL Women
Australia,New South Wales NPL 3
Austria,2. Liga
Austria,Regionalliga
Austria,Landesliga Kärnten
Austria,Landesliga Vorarlberg
Austria,Landesliga Tirol
Austria,Bundesliga
Azerbaijan,Premyer Liqası
Bahrain,Bahraini Premier League
Bangladesh,Bangladesh Football Premier League
Belarus,Vysheyshaya Liga
Belgium,1st National Women
Belgium,First Division B
Belgium,Second Amateur Division : VFV B
Belgium,Pro League
Belgium,First Amateur Division
Belgium,Second Amateur Division : VFV A
Belgium,Second Amateur Division : ACFF
Benin,Championnat National
Bermuda,Bermudian Premier Division
Bolivia,LFPB
Bosnia and Herzegovina,Premier League of Bosnia
Bosnia and Herzegovina,First League RS
Bosnia and Herzegovina,First League FBiH
Botswana,Botswana Premier League
Brazil,Capixaba
Brazil,Serie B
Brazil,Carioca U20
Brazil,Serie C
Brazil,Paulista A3
Brazil,Brasiliense
Brazil,Piauiense
Brazil,Paulista A2
Brazil,Sergipano
Brazil,Alagoano
Brazil,Serie A
Brazil,Rondoniense
Brazil,Acreano
Brazil,Roraimense
Bulgaria,Second League
Bulgaria,Womens League
Bulgaria,Third League Northeast
Bulgaria,Elite U19
Bulgaria,Third League Northwest
Bulgaria,Third League Southwest
Bulgaria,Third League Southeast
Bulgaria,First League
Burkina Faso,Burkinabé Premier League
Burundi,Burundi Premier League
Canada,Canadian Premier League
Chile,Primera B
Chile,Primera División
Colombia,Categoria Primera B
Colombia,Categoria Primera A
Congo,Congo Premier League
Costa Rica,Primera División FPD
Croatia,Prva HNL Juniori
Croatia,3. HNL West
Croatia,3. HNL North
Croatia,Druga HNL
Croatia,3. HNL South
Croatia,3. HNL Center
Croatia,3. HNL East
Croatia,Prva HNL
Cyprus,Second Division
Cyprus,3. Division
Cyprus,First Division
Czech Republic,4. Liga Division D
Czech Republic,3. Liga MSFL CFL
Czech Republic,1. Liga U19
Czech Republic,4. Liga Division B
Czech Republic,4. Liga Division A
Czech Republic,4. Liga Division C
Czech Republic,FNL
Czech Republic,4. Liga Division F
Czech Republic,4. Liga Division E
Czech Republic,First League
Denmark,Denmark Series Group 2
Denmark,U19 Ligaen
Denmark,Denmark Series Group 1
Denmark,Denmark Series Group 3
Denmark,Denmark Series Group 4
Denmark,1st Division
Denmark,3. Division
Denmark,2nd Division
Djibouti,Division 1
Ecuador,Primera Categoría Serie A
Egypt,Egyptian Premier League
El Salvador,Reserve League
England,Youth Alliance
England,U18 Premier League
England,Premier League
England,Championship
England,Isthmian League South Central Division
England,EFL League Two
England,EFL League One
England,National League North and South
England,National League
England,Isthmian Premier Division
England,Isthmian League Division One North
England,Southern League Division One Central
England,Northern Premier League
England,Southern League Premier Central
England,Southern League Premier South
England,Isthmian League South East Division
England,Northern East Division One
England,Northern Midlands Division One
England,Northern West Division One
England,Southern South Division One
England,Counties Leagues Essex Senior League
England,Counties Leagues Combined Counties Premier North
England,Counties Leagues Combined Counties Premier South
England,Counties Leagues Eastern Counties League
England,Counties Leagues Midland League Premier
England,Counties Leagues Northern Counties East
England,Counties Leagues Northern League One
England,Counties Leagues Southern Combination League Premier
Esports,Esoccer Liga Pro
Esports,Esoccer Battle
Estonia,Esiliiga
Estonia,Esiliiga B
Estonia,Meistriliiga
Faroe Islands,2. Deild
Faroe Islands,1. Deild
Faroe Islands,Faroe Islands Premier League
Fiji,National Football League
Finland,A Junior League
Finland,Finnish Cup
Finland,Veikkausliiga
France,Championnat National U19
France,National 3 Group D
France,Ligue 2
France,National 3 Group C
France,National 3 Group F
France,National 3 Group I
France,National 2 Group C
France,National 3 Group M
France,National 3 Group J
France,Ligue 1
France,National 2 Group B
France,National 3 Group L
France,National 3 Group B
France,National
France,National 2 Group A
France,National 2 Group D
France,National 3 Group E
France,National 3 Group K
France,National 3 Group A
France,National 3 Group H
FYR Macedonia,Second Football League
Gambia,GFA League
Germany,U19 Bundesliga
Germany,Regionalliga Nordost
Germany,Oberliga Bremen
Germany,Oberliga Nordost Nord
Germany,Regionalliga Nord
Germany,2. Bundesliga
Germany,Regionalliga Bayern
Germany,3. Liga
Germany,Oberliga Bayern Nord
Germany,Oberliga Bayern Süd
Germany,Oberliga Nordost Süd
Germany,Oberliga Baden Wurttemberg
Germany,Regionalliga Südwest
Germany,Regionalliga West
Germany,Bundesliga
Ghana,Ghana Premier League
Gibraltar,Premier Division
Greece,Super League K19
Greece,Super League 2
Greece,Gamma Ethniki Group 5
Greece,Super League
Guadeloupe,Guadeloupe Division of Honor
Guatemala,Primera División de Ascenso
Guatemala,Liga Nacional de Fútbol de Guatemala
Hong Kong,Sapling Cup
Hungary,U19 League
Hungary,U19 Playoffs
Hungary,U19 Alap
Hungary,NB I
Hungary,NB III Kozep
Hungary,NB III Nyugati
Iceland,Iceland Cup
India,I-League
Iran,Hazfi Cup
Israel,U19 Elite Division
Israel,Israeli Premier League
Italy,Campionato Primavera 1
Italy,Campionato Primavera 2
Italy,Campionato Primavera 4
Italy,Serie B
Italy,Serie C Group B
Italy,Serie C Group C
Italy,Campionato Primavera 3
Italy,Serie A
Italy,Serie D Group C
Italy,Serie D Group B
Italy,Serie D Group G
Italy,Primavera Women
Ivory Coast,Ivory Coast Ligue 1
Japan,J2 League
Japan,Japan Football League
Japan,Nadeshiko League 1
Japan,Nadeshiko League 2
Japan,J1 League
Jordan,Jordanian Pro League
Kazakhstan,Kazakhstan Premier League
Kenya,Kenyan Premier League
Kosovo,Superleague of Kosovo
Kuwait,Kuwait Premier League
Lebanon,Lebanese Premier League
Lesotho,Lesotho Premier League
Lithuania,A Lyga
Malaysia,Malaysia Premier League
Malaysia,Super League
Maldives,Dhivehi Premier League
Malta,First Division
Malta,Gozo First Division
Malta,Maltese Premier League
Mauritania,Super D1
Mexico,Liga MX
Mexico,Liga Premier Serie A
Mexico,U20 League
Mexico,Liga Premier Serie B
Moldova,Moldovan National Division
Moldova,Division A
Mongolia,Mongolian Premier League
Montenegro,Montenegrin Second League
Morocco,Botola 2
Morocco,Division 1 Féminin
Netherlands,U21 Divisie 4
Netherlands,U21 Divisie 3
Netherlands,U21 Divisie 1
Netherlands,Tweede Divisie
Netherlands,Derde Divisie - Zaterdag
Netherlands,U21 Divisie 2
Netherlands,Eredivisie
Nicaragua,Liga Primera U20
Nicaragua,Primera Division
Northern Ireland,Premiership Development League
Northern Ireland,Premier Intermediate League
Northern Ireland,NIFL Championship
Northern Ireland,NIFL Premiership
Norway,3. Division Group 1
Norway,3. Division Group 2
Norway,3. Division Group 6
Norway,2. Division
Norway,3. Division Group 3
Norway,3. Division Group 4
Norway,3. Division Group 5
Norway,Eliteserien
Norway,First Division
Palestine,West Bank League
Panama,Liga Prom
Panama,LPF
Paraguay,Division Intermedia
Paraguay,Division Profesional
Peru,Primera División
Poland,3. Liga
Poland,Central Youth League
Poland,1. Liga
Poland,Ekstraklasa
Poland,2. Liga
Portugal,LigaPro
Portugal,U19 League
Portugal,Liga 3
Portugal,Liga NOS
Republic of Ireland,First Division
Republic of Ireland,Premier Division
Romania,U19 League
Romania,Liga II
Romania,3. Liga Series 5
Romania,3. Liga Series 4
Romania,3. Liga Series 1
Romania,3. Liga Series 2
Romania,3. Liga Series 7
Romania,3. Liga Series 6
Romania,3. Liga Series 9
Romania,3. Liga Series 10
Romania,3. Liga Series 8
Romania,3. Liga Series 3
Romania,Liga I
Russia,Russian Premier League
San Marino,Campionato Sammarinese
Saudi Arabia,Youth League
Scotland,Feeder Leagues
Scotland,League Two
Scotland,League One
Scotland,Championship
Scotland,Premiership
Scotland,Highland / Lowland Football Leagues
Senegal,Senegal Premier League
Serbia,Srpska Liga Belgrade
Serbia,U19 League
Serbia,Srpska Liga West
Serbia,SuperLiga
Serbia,Prva Liga
Serbia,Srpska Liga Vojvodina
Serbia,Srpska Liga East
Sierra Leone,Sierra Leone National Premier League
Singapore,S.League
Slovakia,3. Liga
Slovakia,U19 League
Slovakia,2. Liga
Slovakia,Super Liga
Slovenia,2. SNL
Slovenia,PrvaLiga
Slovenia,3. SNL
South Africa,South African Cup
South Korea,K4 League
South Korea,K League 1
South Korea,K3 League
South Korea,K League 2
Spain,División de Honor Juvenil
Spain,Tercera Group 4
Spain,Tercera Group 9
Spain,Tercera Group 10
Spain,Tercera Group 7
Spain,La Liga
Spain,Segunda División
Spain,Segunda División RFEF
Spain,Tercera Group 3
Spain,Tercera Group 6
Spain,Tercera Group 11
Spain,Tercera Group 15
Spain,Segunda División B
Spain,Tercera Group 1
Spain,Tercera Group 2
Spain,Tercera Group 5
Spain,Tercera Group 8
Spain,Tercera Group 12
Spain,Tercera Group 13
Spain,Tercera Group 18
Spain,Tercera Group 14
Spain,Tercera Group 16
Spain,Tercera Group 17
Sudan,Sudani Premier League
Sweden,Division 2: Norrland
Sweden,Division 2: Vastra Gotaland
Sweden,U19 League
Sweden,Superettan
Sweden,Division 1
Sweden,Division 2: Norra Gotaland
Sweden,Division 2: Norra Svealand
Sweden,Division 2: Sodra Gotaland
Sweden,Division 2: Sodra Svealand
Sweden,Elitettan Women
Sweden,Allsvenskan
Switzerland,1. Liga Promotion
Switzerland,1. Liga Classic
Switzerland,Challenge League
Switzerland,Super League
Syria,Syrian Premier League
Tahiti,Tahitian Ligue 1
Tajikistan,Vysshaya Liga
Thailand,Thai League 2
Thailand,Thai League T1
Turkey,U19 Süper Lig
Turkey,U19 1. Lig
Turkey,3. Lig Group 2
Turkey,Süper Lig
Turkey,1. Lig
Turkey,2 Lig Kirmizi
Turkey,2 Lig Beyaz
UAE,Division One
Uganda,Uganda Premier League
Ukraine,Persha Liga
Uruguay,Primera División
USA,USL League One
USA,MLS
USA,USL Championship
Venezuela,Primera División
Vietnam,Vietnam Cup
Wales,FAW Championship
Wales,Welsh Premier League
Wales,Welsh Premier Womens League
Zambia,Super League
Zimbabwe,Premier Soccer League`;

const result: Country[] = [];

cals.split('\n').forEach((s) => {
  const countryAndLeague = s.split(',');

  let country = result.find((c) => c.name === countryAndLeague[0]);
  if (!country) {
    country = {
      name: countryAndLeague[0],
      leagues: [
        {
          name: countryAndLeague[1],
        },
      ],
    };
    result.push(country);
  } else {
    country.leagues?.push({ name: countryAndLeague[1] });
  }
});

console.log(JSON.stringify(result));
