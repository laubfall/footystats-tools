/**
 * Types that are used to control the application. For example chosing a country
 * maybe restricts visible leagues and matches.
 *
 * These types can also be used to identify needed stats.
 */

export interface Country {
  name: string;
}

export interface League {
  name: string;
  country: Country;
}

export interface Season {
  name: string;
  league: League;
}
