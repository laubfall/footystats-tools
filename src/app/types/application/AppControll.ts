/**
 * Types that are used to control the application. For example chosing a country
 * maybe restricts visible leagues and matches.
 *
 * These types can also be used to identify needed stats.
 */

export type Country = {
  name: string;
  leagues?: League[];
};

export type League = {
  name: string;
  seasons: Season[];
};

export type Season = {
  name: string;
  yearFrom: string;
  yearTo: string;
};
