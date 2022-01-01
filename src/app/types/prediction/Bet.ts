export enum Bet {
  OVER_ZERO_FIVE,
  OVER_ONE_FIVE,
}

export enum BetType {
  OVER,
  UNDER,
}

export interface BetTypeDefinition {
  betType: BetType;
  bets: Bet[];
}

export const OVER: BetTypeDefinition = {
  betType: BetType.OVER,
  bets: [Bet.OVER_ZERO_FIVE, Bet.OVER_ONE_FIVE],
};
