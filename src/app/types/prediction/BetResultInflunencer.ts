import BetPredictionContext from './BetPredictionContext';

export enum NotExecutedCause {
  DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET,
  EXCEPTION,
}

export interface BetInfluencerCalculation {
  // Influence for the chosen bet. Higher value means more influence
  amount: number;
  // when set the influencer did not calculated the influece for the chosen bet.
  notExecutedCause?: NotExecutedCause;
}

export default interface BetResultInfluencer {
  calculateInfluence(ctx: BetPredictionContext): BetInfluencerCalculation;
}
