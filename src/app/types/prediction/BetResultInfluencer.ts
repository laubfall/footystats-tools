import BetPredictionContext from './BetPredictionContext';

export enum NotExecutedCause {
  // The current BetResult Influencer misses some information inside the BetPredictionContext
  // in order to do a proper calculation.
  NOT_ENOUGH_INFORMATION,
  // The current BetResult Influencer has no algorithm for the chosen bet
  DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET,
  // Error while doing calculation
  EXCEPTION,
}

export type BetInfluencerCalculation = {
  // Influence for the chosen bet. Higher value means more influence
  amount: number;
  // when set the influencer did not calculated the influece for the chosen bet.
  notExecutedCause?: NotExecutedCause;
};

export type PreCheckReturn = NotExecutedCause | undefined;
export interface BetResultInfluencer {
  preCheck(ctx: BetPredictionContext): PreCheckReturn;
  calculateInfluence(ctx: BetPredictionContext): BetInfluencerCalculation;
}

export default BetResultInfluencer;
