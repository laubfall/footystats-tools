/**
 * Types that are used to control the application. For example chosing a country
 * maybe restricts visible leagues and matches.
 *
 * These types can also be used to identify needed stats.
 */

export type Country = {
  name: string;
};

export type League = {
  name: string;
  country: Country;
};

export type Season = {
  name: string;
  league: League;
  yearFrom: string;
  yearTo: string;
};
